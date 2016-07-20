using System;
using System.Net.Sockets;

namespace TrocaMensagens.Commands
{
    public class SendMessageCommand
    {
        public void Send(string message, string user)
        {
            using (var socket = new SocketWrapper(Main.SERVER, 1011))
            {
                var data = String.Format("SEND MESSAGE {0}:{1}:{2}:{3}", Main.UserId, Main.Password, user, message);
                socket.Send(data);
            }
        }
    }
}
