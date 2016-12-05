using UnityEngine;
using System.Collections;
using System.Net;

using WebSocketSharp.Server;
using Assets.Classes;

namespace HammerHockey3D
{
    public class GameLoop : MonoBehaviour
    {
        private WebSocketServer servidor;
        public float espacamentoTabuleiro;

        public TabuleiroCreator tabuleiroCreator;

        private ArrayList tabuleiro;
        public PlayerBehavior[] players = new PlayerBehavior[4];
        public PerguntaBehavior[] perguntas = new PerguntaBehavior[4];
        public Minigame[] minigames = new Minigame[4];

        public Color corMinigames;
        public Color corPergunta;
        public Color corInicio;

        // Funcionalidades
        public void AvancaPlayer(int index, int casas)
        {
            players[index].avancarPlayer = casas;
        }

        public void MostraPergunta(int index, string pergunta, string alt1, string alt2, string alt3)
        {
            perguntas[index].MostraPergunta(pergunta, alt1, alt2, alt3);
        }

        public void SelecionaResposta(int index, int resposta)
        {
            perguntas[index].SelecionaResposta(resposta);
        }

        public void MostraErroAcerto(int index, bool acerto)
        {
            perguntas[index].MostraErroAcerto(acerto);
        }

        public void AbriuTelaMontarCorpo(int index, int[] partes)
        {
            minigames[index].mostraMinigame = true;

            MoveParteCorpo(index, partes[3], new Vector2(-550 / 2, 386 * 2)); // Cerebro
            MoveParteCorpo(index, partes[2], new Vector2(-413.5f / 2, 386 * 2)); // Coracao
            MoveParteCorpo(index, partes[1], new Vector2(-253.5f / 2, 386 * 2)); // Pulmao
            MoveParteCorpo(index, partes[0], new Vector2(-117 / 2, 386 * 2)); // Intestino
        }

        public void FimTelaMontarCorpo()
        {
            for (int i = 0; i < minigames.Length; ++i)
            {
                minigames[i].posCerebro = new Vector2(0, 0);
                minigames[i].posPulmao  = new Vector2(0, 0);
                minigames[i].posCoracao = new Vector2(0, 0);
                minigames[i].posIntestino = new Vector2(0, 0);
                minigames[i].mostraMinigame = false;
            }

        }

        public void MoveParteCorpo(int index, int tipoParte, Vector2 offset)
        {
            switch (tipoParte)
            {
                case 0:
                    minigames[index].posCerebro += offset;
                    break;
                case 1:
                    minigames[index].posCoracao += offset;
                    break;
                case 2:
                    minigames[index].posPulmao += offset;
                    break;
                case 3:
                    minigames[index].posIntestino += offset;
                    break;
            }

        }

        public void EncaixaParte(int index, int tipoParte)
        {
            Debug.Log("Encaixando " + tipoParte);

            switch (tipoParte)
            {
                case 0:
                    minigames[index].encaixouCerebro = true;
                    break;
                case 1:
                    minigames[index].encaixouCoracao = true;
                    break;
                case 2:
                    minigames[index].encaixouPulmao = true;
                    break;
                case 3:
                    minigames[index].encaixouIntestino = true;
                    break;
            }
        }

        public bool DeveFinalizarPerguntas()
        {
            bool deve = true;
            for (int i = 0; i < perguntas.Length; ++i)
            {
                if (perguntas[i].status == 1)
                {
                    Debug.Log("TA CERTO");
                    return true;
                }
                else if (perguntas[i].status == 0)
                {
                    Debug.Log("N RESPONDIDA");
                    deve = false;
                }
            }
            Debug.Log("DEVE " + deve);
            return deve;
        }

        public void FinalizaPergunta()
        {
            for (int i = 0; i < perguntas.Length; ++i )
                perguntas[i].FinalizaPergunta();
        }

        // Use this for initialization
        void Start()
        {
            StartServidor();

            VerticeTabuleiro.ESPACAMENTO = espacamentoTabuleiro;

            tabuleiroCreator = new TabuleiroCreator();
            tabuleiro = new ArrayList();

            GerarTabuleiro();

            for (int i = 0; i < players.Length; ++i)
            {
                players[i].SetCasaInicial(tabuleiroCreator.GetInicio());
                minigames[i].SetCorPlayer(players[i].GetComponent<Renderer>().material.color);
            }
        }

        void StartServidor()
        {
            //servidor = new WebSocketServer("wss://0.0.0.0:8000");
            servidor = new WebSocketServer(IPAddress.Any, 8000);
            servidor.AddWebSocketService<Comunicacao>("/Comunicacao", () => new Comunicacao() { gameLoop = this });
            servidor.Start();
        }

        void FixedUpdate()
        {

        }

        // Update is called once per frame
        void Update()
        {

        }

        private void GerarTabuleiro()
        {
            VerticeTabuleiro vertice = tabuleiroCreator.GetInicio();
            Vector3 posicaoAtual = new Vector3(0, 0, 0);

            int sequencia = 1;

            do
            {
                GameObject novaPeca = Instantiate(Resources.Load("CasaTabuleiro"), posicaoAtual, Quaternion.identity) as GameObject;
                Debug.Log("Criado objeto CasaTabuleiro nas coordenadas " + posicaoAtual.x + ", " + posicaoAtual.y + ", " + posicaoAtual.z + "; Tipo " + vertice.tipo + "; Seq " + sequencia);

                CasaBehavior casa = novaPeca.GetComponent<CasaBehavior>();
                casa.SetTexto(sequencia.ToString());

                if (sequencia == 1)
                {
                    casa.corCasa = corInicio;
                }
                else
                {
                    switch (vertice.tipo)
                    {
                        case TipoVertice.minigame:
                            casa.corCasa = corMinigames;
                            break;

                        case TipoVertice.pergunta:
                            casa.corCasa = corPergunta;
                            break;
                    }
                }

                tabuleiro.Add(novaPeca);
                vertice = vertice.proximo;

                sequencia++;
                posicaoAtual = vertice.GetPosicao();

            } while (vertice != tabuleiroCreator.GetInicio());
        }
    }
}