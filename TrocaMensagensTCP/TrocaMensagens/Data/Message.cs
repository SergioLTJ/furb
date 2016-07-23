using System;

namespace TrocaMensagens.Data
{
    public class Message
    {
        public Message(string response)
        {
            this.ProcessResponse(response);
        }

        public bool IsLast { get; set; }
        public int UserId { get; set; }
        public string Content { get; set; }

        private void ProcessResponse(string response)
        {
            if (response.StartsWith(".") ||
                response.StartsWith(":\r\n"))
            {
                this.IsLast = true;
                return;
            }

            var tokens = response.Split(':');
            this.UserId = Convert.ToInt32(tokens[0]);
            this.Content = tokens[1];
        }
    }
}
