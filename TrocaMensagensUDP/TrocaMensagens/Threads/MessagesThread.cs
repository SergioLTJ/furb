using System;
using System.Threading;
using TrocaMensagens.Data;

namespace TrocaMensagens.Threads
{
    public delegate void NewMessageHandler(Message message);

    public class MessagesThread
    {
        public event NewMessageHandler NewMessage;

        public void Monitor()
        {
            while (true)
            {
                using (var socket = new SocketWrapper(Configuration.SERVER, 1012))
                {
                    var data = String.Format("GET MESSAGE {0}\n", Configuration.UserIdentification);
                    var response = socket.SendSync(data);

                    var message = new Message(response);

                    if (!message.IsLast)
                    {
                        NewMessage(message);
                    }
                    else
                    {
                        Thread.Sleep(1000);
                    }
                }
            }
        }
    }
}
