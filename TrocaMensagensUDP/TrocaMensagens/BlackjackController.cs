using System;
using System.Net.Sockets;
using System.Text;
using TrocaMensagens.Data;

namespace TrocaMensagens
{
    public delegate void GameStateChangedHandler(bool playing, string message);
    public delegate void NewCardHandler(Card newCard, int newScore, bool matchEnded);
    public delegate void RoundEnded();

    public class BlackjackController
    {
        public event GameStateChangedHandler StateChanged;
        public event NewCardHandler NewCard;
        public event RoundEnded RoundEnded;

        public bool GamePlaying { get; set; }
        public int Score { get; set; }

        public void SwitchState()
        {
            var command = this.GamePlaying ? "QUIT" : "ENTER";
            var request = String.Format("SEND GAME {0}:{1}\n", Configuration.UserIdentification, command);
            this.SendUdpRequest(request);
            this.GamePlaying = !this.GamePlaying;
            var message = String.Format("Jogo {0} com sucesso.", this.GamePlaying ? "iniciado" : "finalizado");
            this.StateChanged(this.GamePlaying, message);
        }

        public void GetCard()
        {
            var request = String.Format("GET CARD {0}\n", Configuration.UserIdentification);
            var response = this.SendTcpRequest(request);
            var card = new Card(response);
            this.AddCard(card);
        }

        public void EndRound()
        {
            var request = String.Format("SEND GAME {0}:STOP\n", Configuration.UserIdentification);
            this.SendUdpRequest(request);
            this.RoundEnded();
        }

        public void SendUdpRequest(string request)
        {
            using (var client = new UdpClient(1011))
            {
                var datagram = Encoding.ASCII.GetBytes(request);
                client.Send(datagram, datagram.Length, Configuration.SERVER, 1011);
            }
        }

        public string SendTcpRequest(string request)
        {
            using (var socket = new SocketWrapper(Configuration.SERVER, 1012))
            {
                return socket.SendSync(request);
            }
        }

        public void AddCard(Card card)
        {
            this.Score += card.Value;

            var gameEnded = this.Score > 21;
            var currentScore = this.Score;

            if (gameEnded)
            {
                this.Score = 0;
            }

            this.NewCard(card, currentScore, gameEnded);
        }
    }
}
