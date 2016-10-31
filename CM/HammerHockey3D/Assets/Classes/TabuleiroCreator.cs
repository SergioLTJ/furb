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
            grafo.AdicionaVertice(TipoVertice.intermediario, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.intermediario, DirecaoAvanco.baixo);
            grafo.AdicionaVertice(TipoVertice.intermediario, DirecaoAvanco.direita);
            grafo.AdicionaVertice(TipoVertice.intermediario, DirecaoAvanco.direita);
            grafo.AdicionaVertice(TipoVertice.intermediario, DirecaoAvanco.direita);
            grafo.AdicionaVertice(TipoVertice.intermediario, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.intermediario, DirecaoAvanco.cima);
            grafo.AdicionaVertice(TipoVertice.final, DirecaoAvanco.direita);
        }

    }
}
