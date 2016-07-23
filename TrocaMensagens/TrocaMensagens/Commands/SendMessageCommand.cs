using System;
using System.Net.Sockets;
using System.Text;

namespace TrocaMensagens.Commands
{
    public class SendMessageCommand
    {
        public void Send(string message, string user)
        {
            using (var client = new UdpClient(Configuration.SERVER, 1011))
            {
                var data = String.Format("SEND MESSAGE {0}:{1}:{2}:{3}", Configuration.UserId, Configuration.Password, user, message);
                var datagram = Encoding.ASCII.GetBytes(data);
                client.Send(datagram, datagram.Length);
            }
        }
    }
}
