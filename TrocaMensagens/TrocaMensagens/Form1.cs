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

            var publicTab = new TabPage("Geral") { Name = "0" };
            publicTab.Controls.Add(new RichTextBox() { Dock = DockStyle.Fill, Enabled = false });
            tbcChats.TabPages.Add(publicTab);
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
            var userPanel = this.FindUserPage(senderId);
            ((RichTextBox)userPanel.Controls[0]).Text += "\n[" + senderId + "]: " + message.Content;
        }

        private TabPage FindUserPage(int userId)
        {
            var userIdString = userId.ToString();

            foreach (TabPage page in this.tbcChats.TabPages)
            {
                if (page.Name == userIdString)
                    return page;
            }

            var userPage = new TabPage(userIdString);
            userPage.Name = userIdString;
            userPage.Controls.Add(new RichTextBox() { Dock = DockStyle.Fill, Enabled = false });
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

            ((RichTextBox)this.tbcChats.Controls[0]).Text += "\n[" + currentUser + "]: " + message;
        }
    }
}
