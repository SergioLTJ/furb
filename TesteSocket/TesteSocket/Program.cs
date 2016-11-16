using System;
using System.Net;
using WebSocketSharp;
using WebSocketSharp.Server;

namespace TesteSocket
{
    public class Comunicacao : WebSocketBehavior
    {
        protected override void OnMessage(MessageEventArgs e)
        {
            
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            var wssv = new WebSocketServer("ws://0.0.0.0");
            wssv.AddWebSocketService<Comunicacao>("/Comunicacao");
            wssv.Start();
            Console.ReadKey(true);
            wssv.Stop();
        }
    }
}

