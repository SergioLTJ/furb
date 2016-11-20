using System.Runtime.Serialization;

namespace TesteSocket.Dados
{
    [DataContract]
    public class AbriuTelaMontarCorpo
    {
        [DataMember]
        public PartesPorJogador[] partesPorJogador { get; set; }
    }
}
