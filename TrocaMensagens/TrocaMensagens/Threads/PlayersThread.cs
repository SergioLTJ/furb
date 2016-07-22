using System;
using System.Threading;
using TrocaMensagens.Data;

namespace TrocaMensagens.Threads
{
    public delegate void PlayersUpdatedHandler(PlayerList players);

    public class PlayersThread
    {
        public event PlayersUpdatedHandler PlayersUpdated;

        public bool ShouldRun { private get; set; }

        public void Monitor()
        {
            while (true)
            {
                do
                {
                    using (var socket = new SocketWrapper(Main.SERVER, 1012))
                    {
                        var request = String.Format("GET PLAYERS {0}:{1}", Main.UserId, Main.Password);
                        var returnValue = socket.SendSync(request);

                        var players = new PlayerList(returnValue);

                        PlayersUpdated(players);
                    }

                    Thread.Sleep(1000);
                } while (ShouldRun);

                Thread.Sleep(50);
            }
        }
    }
}
