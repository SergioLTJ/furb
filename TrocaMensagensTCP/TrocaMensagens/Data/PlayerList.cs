using System;
using System.Collections;
using System.Collections.Generic;

namespace TrocaMensagens.Data
{
    public class PlayerList : IEnumerable<Player>
    {
        private List<Player> _internalList;

        public PlayerList(string response)
        {
            _internalList = new List<Player>();
            this.ParseResponse(response);
        }

        private void ParseResponse(string response)
        {
            if (response.StartsWith("Usuário"))
            {
                this.IsValid = false;
                this.ResponseMessage = response;
                return;
            }

            var tokens = response.Split(':');

            for (int i = 0; i < tokens.Length; i += 3)
            {
                if (tokens[i].StartsWith("\r\n"))
                {
                    break;
                }

                var id = tokens[i];
                var status = this.ConvertStatus(tokens[i + 1]);
                
                _internalList.Add(new Player(Convert.ToInt64(id), status));
            }

            this.IsValid = true;
            this.ResponseMessage = response;
        }

        private string ConvertStatus(string responseStatus)
        {
            switch(responseStatus)
            {
                case "IDLE":
                    return "Aguardando";
                case "PLAYING":
                    return "Jogando - Aguardando vez";
                case "GETTING":
                    return "Jogando - Pedindo cartas";
                case "WAITING":
                    return "Jogando - Aguardando final da rodada";
                default:
                    return "Desconhecido";
            }
        }

        public bool IsValid { get; set; }
        public string ResponseMessage { get; set; }

        public IEnumerator<Player> GetEnumerator()
        {
            return _internalList.GetEnumerator();
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return _internalList.GetEnumerator();
        }

        public Player this[int index]
        {
            get { return _internalList[index]; }
            set { _internalList[index] = value; }
        }
    }
}
