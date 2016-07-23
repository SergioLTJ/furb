using System;
using System.Windows.Forms;

namespace TrocaMensagens
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            var form = new Login();
            //var form = new frmTrocaMensagens();
            form.Closed += (s, args) => Environment.Exit(0);

            Application.Run(form);            

            //Configuration.UserId = "5143";
            //Configuration.Password = "behbd";

            //new SendMessageCommand().Send("Teste", "3451");

            //Configuration.UserId = "3451";
            //Configuration.Password = "msduu";

            //new MessagesThread().Monitor();
        }
    }
}
