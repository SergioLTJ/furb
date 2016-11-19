using System.Runtime.Serialization;

namespace TesteSocket.Dados
{
    [DataContract]
    public class ClicouConfirmarTelaPergunta
    {
        [DataMember]
        public TipoDado tipo { get; set; }

        [DataMember]
        public int jogador { get; set; }

        [DataMember]
        public bool estaCorreto { get; set; }
    }
}
