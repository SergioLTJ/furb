using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using WebSocketSharp.Server;

using HammerHockey3D;
using UnityEngine;
using WebSocketSharp;
using Classes;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

//using System.Runtime.Serialization.Json;
//using Newtonsoft.Json.Linq;

namespace Assets.Classes
{
    public class Comunicacao : WebSocketBehavior
    {
        public HammerHockey3D.GameLoop gameLoop;

        protected override void OnOpen()
        {
            Debug.Log("Conectado");
        }

        protected override void OnMessage(MessageEventArgs e)
        {
            Debug.Log("Mensagem");

            var json = JObject.Parse(e.Data);
            var data = (int)json["tipo"];

            switch ((TipoDado)data)
            {
                case TipoDado.MOVEU_JOGADOR:
                    MoveuJogador(Deserializar<MoveuJogador>(e.Data));
                    break;
                case TipoDado.SELECIONOU_ITEM_PERGUNTA:
                    SelecionouItemPergunta(Deserializar<SelecionouItemPergunta>(e.Data));
                    break;
                case TipoDado.CRIOU_TELA_PERGUNTA:
                    CriouTelaPergunta(Deserializar<CriouTelaPergunta>(e.Data));
                    break;
                case TipoDado.CLICOU_CONFIRMAR_TELA_PERGUNTA:
                    ClicouConfirmarTelaPergunta(Deserializar<ClicouConfirmarTelaPergunta>(e.Data));
                    break;
                case TipoDado.ABRIU_TELA_MONTAR_CORPO:
                    AbriuTelaMontarCorpo(Deserializar<AbriuTelaMontarCorpo>(e.Data));
                    break;
                case TipoDado.MOVEU_PARTE:
                    MoveuParte(Deserializar<MoveuParte>(e.Data));
                    break;
                case TipoDado.ENCAIXOU_PARTE:
                    EncaixouParte(Deserializar<EncaixouParte>(e.Data));
                    break;
                case TipoDado.FIM_QUEBRA_CABECA:
                    FimQuebraCabeca();
                    break;
            }
        }

        private void MoveuParte(MoveuParte moveuParte)
        {
            if (moveuParte.jogador == 0 || moveuParte.jogador == 1)
                moveuParte.offsetX *= -1;

            if (moveuParte.jogador == 2 || moveuParte.jogador == 3)
                moveuParte.offsetY *= -1;

            gameLoop.MoveParteCorpo(moveuParte.jogador, moveuParte.parte, new Vector2((float)(moveuParte.offsetX / 2), (float)(moveuParte.offsetY * 2)));
        }

        private void AbriuTelaMontarCorpo(AbriuTelaMontarCorpo abriuTelaMontarCorpo)
        {
            for (int i = 0; i < abriuTelaMontarCorpo.partesPorJogador.Length; ++i)
            {
                Debug.Log("Index " + i);
                gameLoop.AbriuTelaMontarCorpo(abriuTelaMontarCorpo.partesPorJogador[i].jogador, abriuTelaMontarCorpo.partesPorJogador[i].partesCorpo);
            }
        }

        private void ClicouConfirmarTelaPergunta(ClicouConfirmarTelaPergunta clicouConfirmarTelaPergunta)
        {
            Debug.Log("Confirmar");
            gameLoop.MostraErroAcerto(clicouConfirmarTelaPergunta.jogador, clicouConfirmarTelaPergunta.estaCorreto);

            if (gameLoop.DeveFinalizarPerguntas())
            {
                Debug.Log("Finalizando perguntas...");
                System.Threading.Thread.Sleep(4000);
                gameLoop.FinalizaPergunta();
            }
        }

        private void CriouTelaPergunta(CriouTelaPergunta criouTelaPergunta)
        {
            Debug.Log("CriouTelaPergunta");
            for (int i = 0; i < criouTelaPergunta.perguntasPorJogador.Length; ++i)
            {
                PerguntaPorJogador pergunta = criouTelaPergunta.perguntasPorJogador[i];
                gameLoop.MostraPergunta(pergunta.jogador, pergunta.pergunta.enunciado, pergunta.pergunta.respostas[0], pergunta.pergunta.respostas[1], pergunta.pergunta.respostas[2]);              
            }
        }

        private void SelecionouItemPergunta(SelecionouItemPergunta selecionouItemPergunta)
        {
            gameLoop.SelecionaResposta(selecionouItemPergunta.jogador, selecionouItemPergunta.indiceResposta + 1);
        }

        private void MoveuJogador(MoveuJogador moveuJogador)
        {
            Debug.Log("Jogador " + moveuJogador.indice + " andou " + moveuJogador.quantidade);
            gameLoop.AvancaPlayer(moveuJogador.indice, moveuJogador.quantidade);
        }

        private void EncaixouParte(EncaixouParte encaixouParte)
        {
            gameLoop.EncaixaParte(encaixouParte.jogador, encaixouParte.parte);
        }

        private void FimQuebraCabeca()
        {
            System.Threading.Thread.Sleep(4000);
            gameLoop.FimTelaMontarCorpo();            
        }


        private T Deserializar<T>(string dadosJson)
        {
            return JsonConvert.DeserializeObject<T>(dadosJson);

            //var serializador = new DataContractJsonSerializer(typeof(T));
            //using (var stream = new MemoryStream(dadosJson))
            //{
            //    return (T)serializador.ReadObject(stream);
            //}
        }
    }      
}
