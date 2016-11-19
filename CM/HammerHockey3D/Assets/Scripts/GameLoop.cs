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
        public PerguntaBehavior UIPergunta;

        private ArrayList tabuleiro;
        public PlayerBehavior[] players = new PlayerBehavior[4];

        public Color corMinigames;
        public Color corPergunta;
        public Color corInicio;

        // Funcionalidades
        public void AvancaPlayer(int index)
        {
            players[index].avancarPlayer = true;
        }

        public void MostraPergunta(string pergunta, string alt1, string alt2, string alt3, string alt4)
        {
            UIPergunta.MostraPergunta(pergunta, alt1, alt2, alt3, alt4);
        }

        public void SelecionaResposta(int index)
        {
            UIPergunta.SelecionaResposta(index);
        }

        public void FinalizaPergunta()
        {
            UIPergunta.FinalizaPergunta();
        }

        // Use this for initialization
        void Start()
        {
            StartServidor();

            VerticeTabuleiro.ESPACAMENTO = espacamentoTabuleiro;

            tabuleiroCreator = new TabuleiroCreator();
            tabuleiro = new ArrayList();

            GerarTabuleiro();

            players[0] = GameObject.Find("Jogador01").GetComponent<PlayerBehavior>();
            players[1] = GameObject.Find("Jogador02").GetComponent<PlayerBehavior>();
            players[2] = GameObject.Find("Jogador03").GetComponent<PlayerBehavior>();
            players[3] = GameObject.Find("Jogador04").GetComponent<PlayerBehavior>();

            for (int i = 0; i < players.Length; ++i)
                players[i].SetCasaInicial(tabuleiroCreator.GetInicio());
        }

        void StartServidor()
        {
            servidor = new WebSocketServer("ws://10.0.0.100:8000");
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