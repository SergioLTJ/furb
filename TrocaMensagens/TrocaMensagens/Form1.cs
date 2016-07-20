using System;
using System.Threading;
using System.Windows.Forms;
using TrocaMensagens.Commands;
using TrocaMensagens.Data;
using TrocaMensagens.Threads;

namespace TrocaMensagens
{
    public partial class frmTrocaMensagens : Form
    {
        public frmTrocaMensagens()
        {
            InitializeComponent();

            Main.UserId = "5143";
            Main.Password = "behbd";

            this.txbMessage.KeyPress += txbMessage_KeyPress;

            var usersThread = new UsersThread();
            usersThread.UsersUpdated += UsersThread_UsersUpdated;
            new Thread(usersThread.Monitor).Start();

            var messagesThread = new MessagesThread();
            messagesThread.NewMessage += MessagesThread_NewMessage;
            new Thread(messagesThread.Monitor).Start();

            var publicTab = new TabPage("Geral") { Name = "0" };
            publicTab.Controls.Add(new RichTextBox() { Dock = DockStyle.Fill, Enabled = false });
            tbcChats.TabPages.Add(publicTab);

            this.grdUsuarios.CellMouseDoubleClick += GrdUsuarios_CellMouseDoubleClick;
        }

        private void GrdUsuarios_CellMouseDoubleClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            var row = this.grdUsuarios.Rows[e.RowIndex];
            var userId = row.Cells[0].Value.ToString();
            this.FindUserPage(userId);
        }

        private void UsersThread_UsersUpdated(UserList users)
        {
            this.Invoke(new UsersUpdatedHandler(UpdateUsers), users);
        }

        private void UpdateUsers(UserList users)
        {
            if (users.IsValid)
            {
                grdUsuarios.Rows.Clear();
                foreach (var user in users)
                {
                    grdUsuarios.Rows.Add(new[] { user.Id.ToString(), user.Name, user.Wins.ToString() });
                }
            }
        }

        private void MessagesThread_NewMessage(Data.Message message)
        {
            this.Invoke(new NewMessageHandler(AddMessage), message);
        }

        private void AddMessage(Data.Message message)
        {
            var senderId = message.UserId;
            var userPanel = this.FindUserPage(senderId.ToString());
            ((RichTextBox)userPanel.Controls[0]).Text += "[" + senderId + "]: " + message.Content;
        }

        private TabPage FindUserPage(string userId, bool selectPage = false)
        {
            foreach (TabPage page in this.tbcChats.TabPages)
            {
                if (page.Name == userId)
                    return page;
            }

            var userPage = new TabPage(userId);
            userPage.Name = userId;
            userPage.Controls.Add(new RichTextBox() { Dock = DockStyle.Fill, Enabled = false });

            this.tbcChats.TabPages.Add(userPage);

            if (selectPage)
            {
                this.tbcChats.SelectedTab = userPage;
            }

            return userPage;
        }

        private void txbMessage_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Enter)
            {
                btnSend_Click(null, null);
                e.Handled = true;
            }
        }

        private void btnSend_Click(object sender, EventArgs e)
        {
            var message = this.txbMessage.Text;
            var user = this.tbcChats.SelectedTab.Name;

            new SendMessageCommand().Send(message, user);

            var currentUser = Main.UserId;

            txbMessage.Clear();

            var messageBox = ((RichTextBox)this.tbcChats.SelectedTab.Controls[0]);
            messageBox.Text += "[" + currentUser + "]: " + message;
        }
    }
}
