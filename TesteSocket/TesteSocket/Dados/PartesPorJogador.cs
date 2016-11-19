using System.Runtime.Serialization;

namespace TesteSocket.Dados
{
    [DataContract]
    public class PartesPorJogador
    {
        [DataMember]
        public TipoParteCorpo[] partesCorpo { get; set; }

        [DataMember]
        public int jogador { get; set; }
    }
}