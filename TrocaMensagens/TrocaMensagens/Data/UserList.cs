using System;
using System.Collections;
using System.Collections.Generic;

namespace TrocaMensagens.Data
{
    public class UserList : IEnumerable<User>
    {
        private List<User> _internalList;

        public UserList(string response)
        {
            _internalList = new List<User>();
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

            for(int i = 0; i < tokens.Length; i += 3)
            {
                if (String.IsNullOrEmpty(tokens[i]))
                {
                    break;
                }

                var id = tokens[i];
                var name = tokens[i + 1];
                var wins = tokens[i + 2];

                _internalList.Add(new User(Convert.ToInt64(id), Convert.ToInt32(wins), name));
            }

            this.IsValid = true;
            this.ResponseMessage = response;
        }

        public bool IsValid { get; set; }
        public string ResponseMessage { get; set; }

        public IEnumerator<User> GetEnumerator()
        {
            return _internalList.GetEnumerator();
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return _internalList.GetEnumerator();
        }

        public User this[int index]
        {
            get { return _internalList[index]; }
            set { _internalList[index] = value; }
        }
    }
}
