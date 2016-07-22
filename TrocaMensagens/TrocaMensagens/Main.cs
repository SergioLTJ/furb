using System;

namespace TrocaMensagens
{
    public class Main
    {
        public static readonly string SERVER = "larc.inf.furb.br";
        public static string UserId { get; set; }
        public static string Password { get; set; }

        public static string UserIdentification
        {
            get
            {
                return String.Format("{0}:{1}", Main.UserId, Main.Password);
            }
        }
    }
}
