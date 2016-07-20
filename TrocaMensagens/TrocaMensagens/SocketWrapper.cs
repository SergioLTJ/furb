using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace TrocaMensagens
{
    public class SocketWrapper : IDisposable
    {
        private Socket _socket;
        //private ProtocolType _protocol;

        public SocketWrapper(string server, int port)
        {
            //_protocol = protocol;
            //var port = protocol == ProtocolType.Tcp ? 1012 : 1011;
            this.Connect(server, port);
        }

        private void Connect(string server, int port)
        {
            IPHostEntry hostEntry = null;

            hostEntry = Dns.GetHostEntry(server);

            foreach (var address in hostEntry.AddressList)
            {
                var ipe = new IPEndPoint(address, port);
                var tempSocket = new Socket(ipe.AddressFamily, SocketType.Stream, ProtocolType.Unspecified);

                tempSocket.Connect(ipe);

                if (tempSocket.Connected)
                {
                    _socket = tempSocket;
                    break;
                }
                else
                {
                    continue;
                }
            }
        }

        public void Send(string data)
        {
            var bytes = Encoding.ASCII.GetBytes(data);
            _socket.Send(bytes);
        }

        public string SendSync(string data)
        {
            var bytes = Encoding.ASCII.GetBytes(data);
            _socket.Send(bytes);

            var responseBytes = new byte[256];
            _socket.Receive(responseBytes);

            return Encoding.UTF8.GetString(responseBytes);
        }

        public void Dispose()
        {
            _socket.Dispose();
        }
    }
}
