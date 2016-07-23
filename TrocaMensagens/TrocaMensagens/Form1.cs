﻿using System;
using System.Collections.Generic;
using System.Drawing;
using System.Threading;
using System.Windows.Forms;
using TrocaMensagens.Commands;
using TrocaMensagens.Data;
using TrocaMensagens.Threads;

namespace TrocaMensagens
{
    public partial class frmTrocaMensagens : Form
    {
        private Dictionary<TabPage, Color> _tabColors = new Dictionary<TabPage, Color>();
        private BlackjackController _controller;
        private PlayersThread _playersThread;

        public frmTrocaMensagens()
        {
            InitializeComponent();

            _controller = new BlackjackController();

            this.Text += " - " + Configuration.UserId;

            this.txbMessage.KeyPress += txbMessage_KeyPress;

            var usersThread = new UsersThread();
            usersThread.UsersUpdated += UsersThread_UsersUpdated;
            new Thread(usersThread.Monitor).Start();

            _playersThread = new PlayersThread();
            _playersThread.PlayersUpdated += PlayersThread_PlayersUpdated;
            new Thread(_playersThread.Monitor).Start();

            var messagesThread = new MessagesThread();
            messagesThread.NewMessage += MessagesThread_NewMessage;
            new Thread(messagesThread.Monitor).Start();

            var publicTab = new TabPage("Geral") { Name = "0" };
            publicTab.Controls.Add(this.CreateDefaultTextBox());
            _tabColors.Add(publicTab, Color.White);
            tbcChats.TabPages.Add(publicTab);

            this.tbcChats.DrawMode = TabDrawMode.OwnerDrawFixed;
            this.tbcChats.DrawItem += tbcChats_DrawItem;
            this.tbcChats.Selected += tbcChats_Selected;

            _controller.StateChanged += _controller_StateChanged;
            _controller.NewCard += _controller_NewCard;
            _controller.RoundEnded += RoundEnded;

            this.grdUsuarios.CellMouseDoubleClick += GrdUsuarios_CellMouseDoubleClick;
        }

        private Control CreateDefaultTextBox()
        {
            return new RichTextBox() 
            { 
                Dock = DockStyle.Fill, 
                ReadOnly = true, 
                BackColor = Color.White,
                ForeColor = Color.Black,
                Font = SystemFonts.DialogFont,
            };
        }

        void tbcChats_Selected(object sender, TabControlEventArgs e)
        {
            var currentTab = this.tbcChats.SelectedTab;
            if (_tabColors[currentTab] == Color.Yellow)
            {
                _tabColors[currentTab] = Color.White;
                this.tbcChats.Invalidate();
            }
        }

        void tbcChats_DrawItem(object sender, DrawItemEventArgs e)
        {
            using (var br = new SolidBrush(_tabColors[this.tbcChats.TabPages[e.Index]]))
            {
                e.Graphics.FillRectangle(br, e.Bounds);
                SizeF sz = e.Graphics.MeasureString(this.tbcChats.TabPages[e.Index].Text, e.Font);
                e.Graphics.DrawString(this.tbcChats.TabPages[e.Index].Text, e.Font, Brushes.Black, e.Bounds.Left + (e.Bounds.Width - sz.Width) / 2, e.Bounds.Top + (e.Bounds.Height - sz.Height) / 2 + 1);

                Rectangle rect = e.Bounds;
                rect.Offset(0, 1);
                rect.Inflate(0, -1);
                e.Graphics.DrawRectangle(Pens.White, rect);
                e.DrawFocusRectangle();
            }
        }

        private void GrdUsuarios_CellMouseDoubleClick(object sender, DataGridViewCellMouseEventArgs e)
        {
            var row = this.grdUsuarios.Rows[e.RowIndex];
            var userId = row.Cells[0].Value.ToString();
            this.FindUserPage(userId, true);
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

        private void PlayersThread_PlayersUpdated(PlayerList players)
        {
            this.Invoke(new PlayersUpdatedHandler(UpdatePlayers), players);
        }

        private void UpdatePlayers(PlayerList players)
        {
            if (players.IsValid)
            {
                grdPlayers.Rows.Clear();
                foreach (var player in players)
                {
                    grdPlayers.Rows.Add(new[] { player.Id.ToString(), player.Status });
                }
            }
        }

        private void MessagesThread_NewMessage(Data.Message message)
        {
            this.Invoke(new NewMessageHandler(AddMessage), message);
        }

        private void AddMessage(Data.Message message)
        {
            var senderId = message.UserId.ToString();

            if (senderId == Configuration.UserId) { return; }

            var userPanel = this.FindUserPage(senderId);
            ((RichTextBox)userPanel.Controls[0]).Text += "[" + senderId + "]: " + message.Content;
        }

        private TabPage FindUserPage(string userId, bool selectPage = false)
        {
            TabPage userPage = null;
            foreach (TabPage page in this.tbcChats.TabPages)
            {
                if (page.Name == userId)
                {
                    userPage = page;
                    break;
                }
            }

            if (userPage == null)
            {
                userPage = new TabPage(userId);
                userPage.Name = userId;
                userPage.Controls.Add(this.CreateDefaultTextBox());
                _tabColors.Add(userPage, Color.White);
                this.tbcChats.TabPages.Add(userPage);
            }

            if (selectPage)
            {
                this.tbcChats.SelectedTab = userPage;
            }

            if (this.tbcChats.SelectedTab != userPage)
            {
                _tabColors[userPage] = Color.Yellow;
                this.tbcChats.Invalidate();
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

            new Thread(() => new SendMessageCommand().Send(message, user)).Start();

            var currentUser = Configuration.UserId;

            txbMessage.Clear();

            var messageBox = ((RichTextBox)this.tbcChats.SelectedTab.Controls[0]);
            messageBox.Text += "[" + currentUser + "]: " + message;
        }

        private void _controller_StateChanged(bool playing, string message)
        {
            this.Invoke(new GameStateChangedHandler(ChangeGameState), playing, message);
        }

        private void ChangeGameState(bool playing, string message)
        {
            btnStartStop.Text = playing ? "Parar" : "Iniciar";

            btnGetCard.Enabled = playing;
            btnFinishRound.Enabled = playing;

            txbGameLog.Text += message + "\n";
        }

        private void _controller_NewCard(Card newCard, int newScore, bool matchEnded)
        {
            this.Invoke(new NewCardHandler(AddNewCard), newCard, newScore, matchEnded);
        }

        private void AddNewCard(Card newCard, int newScore, bool matchEnded)
        {
            if (newCard.IsValid)
            {
                this.txbGameLog.Text += String.Format("Carta retornada: {0} de {1}\n", newCard.Symbol, newCard.Suit);
                this.txbGameLog.Text += String.Format("Placar atual: [{0}]\n", newScore);
                if (matchEnded)
                {
                    this.txbGameLog.Text += ("O placar passou de 21, rodada finalizada.\n");
                    this.btnFinishRound_Click(null, null);
                }
            }
            else
            {
                this.txbGameLog.Text += String.Format("Você não pode pegar uma carta neste momento.\n");
            }
        }

        private void RoundEnded()
        {
            this.Invoke(new RoundEnded(() => { this.txbGameLog.Text += "Rodada finalizada com sucesso.\n"; }));
        }

        private void btnStartStop_Click(object sender, EventArgs e)
        {
            txbGameLog.Text += "Processando...\n";
            new Thread(() => { _controller.SwitchState(); }).Start();
        }

        private void btnGetCard_Click(object sender, EventArgs e)
        {
            txbGameLog.Text += "Processando...\n";
            new Thread(() => { _controller.GetCard(); }).Start();
        }

        private void btnFinishRound_Click(object sender, EventArgs e)
        {
            txbGameLog.Text += "Processando...\n";
            new Thread(() => { _controller.EndRound(); }).Start();
        }
    }
}
