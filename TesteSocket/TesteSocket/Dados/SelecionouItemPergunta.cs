using System.Runtime.Serialization;

namespace TesteSocket.Dados
{
    [DataContract]
    public class SelecionouItemPergunta
    {
        [DataMember]
        public TipoDado tipo { get; set; }

        [DataMember]
        public int jogador { get; set; }

        [DataMember]
        public int indiceResposta { get; set; }
    }
}
