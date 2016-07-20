using System;
using System.Net.Sockets;
using System.Windows.Forms;
using TrocaMensagens.Data;

namespace TrocaMensagens
{
    public partial class frmTrocaMensagens : Form
    {
        public frmTrocaMensagens()
        {
            InitializeComponent();
            txbRequest.Text = "GET USERS 4123:rsybt\n";
        }

        private void btnTest_Click(object sender, EventArgs e)
        {
            using (var socket = new SocketWrapper("larc.inf.furb.br", ProtocolType.Tcp))
            {
                var returnValue = socket.SendSync(txbRequest.Text);
                txbResponse.Text = returnValue;

                //var users = new UserList(returnValue);
                //if (!users.IsValid)
                //{
                //    txbResponse.Text = users.ResponseMessage;
                //} else
                //{
                //    txbResponse.Text = "Sucesso.";
                //}
            }
        }

        private void TxbRequest_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Enter)
            {
                btnTest_Click(null, null);
                e.Handled = true;
            }
        }
    }
}
