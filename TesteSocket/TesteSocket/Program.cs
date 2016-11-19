using Newtonsoft.Json.Linq;
using System;
using System.IO;
using System.Runtime.Serialization.Json;
using TesteSocket.Dados;
using WebSocketSharp;
using WebSocketSharp.Server;

namespace TesteSocket
{
    public class Comunicacao : WebSocketBehavior
    {
        protected override void OnMessage(MessageEventArgs e)
        {
            var json = JObject.Parse(e.Data);
            var data = (JValue)json["tipo"];
            var tipoDado = (TipoDado)data.Value<int>();

            switch (tipoDado)
            {
                case TipoDado.MOVEU_JOGADOR:
                    MoveuJogador(Deserializar<MoveuJogador>(e.RawData));
                    break;
                case TipoDado.SELECIONOU_ITEM_PERGUNTA:
                    SelecionouItemPergunta(Deserializar<SelecionouItemPergunta>(e.RawData));
                    break;
                case TipoDado.CRIOU_TELA_PERGUNTA:
                    CriouTelaPergunta(Deserializar<CriouTelaPergunta>(e.RawData));
                    break;
                case TipoDado.CLICOU_CONFIRMAR_TELA_PERGUNTA:
                    ClicouConfirmarTelaPergunta(Deserializar<ClicouConfirmarTelaPergunta>(e.RawData));
                    break;
                case TipoDado.ABRIU_TELA_MONTAR_CORPO:
                    AbriuTelaMontarCorpo(Deserializar<AbriuTelaMontarCorpo>(e.RawData));
                    break;
                case TipoDado.MOVEU_PARTE:
                    MoveuParte(Deserializar<MoveuParte>(e.RawData));
                    break;
            }
        }

        private void MoveuParte(MoveuParte moveuParte)
        {
            Console.WriteLine("MoveuParte");
        }

        private void AbriuTelaMontarCorpo(AbriuTelaMontarCorpo abriuTelaMontarCorpo)
        {
            Console.WriteLine("AbriuTelaMontarCorpo");
        }

        private void ClicouConfirmarTelaPergunta(ClicouConfirmarTelaPergunta clicouConfirmarTelaPergunta)
        {
            Console.WriteLine("ClicouConfirmarTelaPergunta");
        }

        private void CriouTelaPergunta(CriouTelaPergunta criouTelaPergunta)
        {
            Console.WriteLine("CriouTelaPergunta");
        }

        private void SelecionouItemPergunta(SelecionouItemPergunta selecionouItemPergunta)
        {
            Console.WriteLine("SelecionouItemPergunta");
        }

        private void MoveuJogador(MoveuJogador moveuJogador)
        {
            Console.WriteLine("MoveuJogador");
        }

        private T Deserializar<T>(byte[] dadosJson)
        {
            var serializador = new DataContractJsonSerializer(typeof(T));
            using (var stream = new MemoryStream(dadosJson))
            {
                return (T)serializador.ReadObject(stream);
            }
        }
    }

    class Program
    {
        static void that()
        {
            var wssv = new WebSocketServer("ws://0.0.0.0");
            wssv.AddWebSocketService<Comunicacao>("/Comunicacao");
            wssv.Start();
        }

        static void Main(string[] args)
        {
            that();
            Console.ReadKey(true);
        }
    }
}

