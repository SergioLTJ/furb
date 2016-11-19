using System.Runtime.Serialization;

namespace TesteSocket.Dados
{
    [DataContract]
    public class PerguntaPorJogador
    {
        [DataMember]
        public Pergunta pergunta { get; set; }

        [DataMember]
        public int jogador { get; set; }
    }
}