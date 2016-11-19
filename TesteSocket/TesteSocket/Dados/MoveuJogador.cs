using System.Runtime.Serialization;

namespace TesteSocket.Dados
{
    [DataContract]
    public class MoveuJogador
    {
        [DataMember]
        public TipoDado tipo { get; set; }

        [DataMember]
        public int indice { get; set; }

        [DataMember]
        public int quantidade { get; set; }
    }
}