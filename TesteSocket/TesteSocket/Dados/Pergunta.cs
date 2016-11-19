using System.Runtime.Serialization;

namespace TesteSocket.Dados
{
    [DataContract]
    public class Pergunta
    {
        [DataMember]
        public string enunciado { get; set; }

        [DataMember]
        public string[] respostas { get; set; }
    }
}