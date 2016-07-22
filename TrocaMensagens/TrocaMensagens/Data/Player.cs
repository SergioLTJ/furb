namespace TrocaMensagens.Data
{
    public class Player
    {
        public Player(long id, string status)
        {
            this.Id = id;
            this.Status = status;
        }

        public long Id { get; private set; }
        public string Status { get; private set; }
    }
}
