using System.Runtime.Serialization;

namespace TesteSocket.Dados
{
    [DataContract]
    public class CriouTelaPergunta
    {
        [DataMember]
        public TipoDado tipo { get; set; }

        [DataMember]
        public PerguntaPorJogador[] perguntasPorJogador { get; set; }
    }
}
