using System;
using System.Windows.Forms;

namespace TrocaMensagens
{
    public partial class Login : Form
    {
        public Login()
        {
            InitializeComponent();
        }

        private void btnStart_Click(object sender, EventArgs e)
        {
            Configuration.UserId = this.txbUser.Text;
            Configuration.Password = this.txbPassword.Text;

            this.Hide();
            var form2 = new frmTrocaMensagens();
            form2.Closed += (s, args) => this.Close();
            form2.Show();
        }
    }
}
