using UnityEngine;
using System.Collections;

namespace Assets.Classes
{
    public class TabuleiroCreator
    {

        private GrafoTabuleiro grafo;

        public TabuleiroCreator()
        {
            grafo = new GrafoTabuleiro();

            GerarGrafo();
        }

        public VerticeTabuleiro GetInicio()
        {
            return grafo.inicio;
        }

        private void GerarGrafo()
        {
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);

            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);

            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);

            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);

            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);

            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);

            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);

            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);

            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);

            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.direita);

            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.cima);

            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.esquerda);

            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.normal, DirecaoAvanco.baixo);

            grafo.FechaCircuito();
        }

    }
}
