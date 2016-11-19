using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Assets.Classes
{
    public class GrafoTabuleiro
    {
        public VerticeTabuleiro inicio;
        public VerticeTabuleiro fim;

        public GrafoTabuleiro()
        {
            inicio = new VerticeTabuleiro(TipoVertice.normal);
            fim = inicio;
        }

        public void AdicionaVertice(TipoVertice tipo, DirecaoAvanco direcaoPai)
        {
            VerticeTabuleiro novo = new VerticeTabuleiro(tipo, fim, direcaoPai);
            fim = novo;
        }

        public void FechaCircuito()
        {
            fim.proximo = inicio;
        }
    }
}
