using System;
using System.Net;
using WebSocketSharp;
using WebSocketSharp.Server;

namespace TesteClient
{
    class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("CLIENT");
            using (var ws = new WebSocket("ws://10.0.0.100:8000/Comunicacao"))
            {
                string msg = "1";

                ws.OnMessage += (sender, e) =>
                {
                    Console.WriteLine("Recebeu: " + msg);
                    msg = e.Data;
                };

                while (msg != "9")
                {
                    ws.Connect();
                    ws.Send(msg);
                    Console.ReadKey(true);
                }

                
            }
        }
    }
}

