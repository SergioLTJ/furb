using System.Runtime.Serialization;

namespace Classes
{
    
    public class CriouTelaPergunta
    {
       
        public TipoDado tipo { get; set; }

       
        public PerguntaPorJogador[] perguntasPorJogador { get; set; }
    }
}
