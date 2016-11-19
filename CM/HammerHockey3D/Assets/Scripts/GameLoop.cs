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
        public PlayerBehavior player;

        public Color corMinigames;
        public Color corPergunta;
        public Color corInicio;

        // Use this for initialization
        void Start()
        {
            StartServidor();

            VerticeTabuleiro.ESPACAMENTO = espacamentoTabuleiro;

            tabuleiroCreator = new TabuleiroCreator();
            tabuleiro = new ArrayList();

            GerarTabuleiro();

            player = GameObject.Find("Jogador").GetComponent<PlayerBehavior>();
            player.SetCasaInicial(tabuleiroCreator.GetInicio());
        }

        void StartServidor()
        {
            servidor = new WebSocketServer("ws://0.0.0.0");
            servidor.AddWebSocketService<Servidor>("/Comunicacao", () => new Servidor() { gameLoop = this });
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