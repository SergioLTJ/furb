using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using UnityEngine;

namespace Assets.Classes
{
    public enum TipoVertice
    {
        inicio,
        intermediario,
        final,
    }

    public enum DirecaoAvanco
    {
        cima,
        baixo,
        direita,
        esquerda,
        nenhum,
    }

    public class VerticeTabuleiro
    {
        public const float TRANSLACAO = 2.2f;

        public VerticeTabuleiro proximo { get; private set; }
        public DirecaoAvanco direcao { get; private set; }
 
        public TipoVertice tipo { get; private set; }

        public VerticeTabuleiro(TipoVertice tipo)
        {
            this.tipo = tipo;
            direcao = DirecaoAvanco.nenhum;
        }

        public VerticeTabuleiro(TipoVertice tipo, VerticeTabuleiro pai, DirecaoAvanco direcaoPai)
        {
            this.tipo = tipo;

            pai.direcao = direcaoPai;
            pai.proximo = this;
        }

        public Vector3 GetTranslacao()
        {
            float x = 0;
            float y = 0;
            float z = 0;

            switch (direcao)
            {
                case DirecaoAvanco.cima:
                    z -= TRANSLACAO;
                    break;
                case DirecaoAvanco.baixo:
                    z += TRANSLACAO;
                    break;
                case DirecaoAvanco.direita:
                    x -= TRANSLACAO;
                    break;
                case DirecaoAvanco.esquerda:
                    x += TRANSLACAO;
                    break;
            }

            return new Vector3(x, y, z);
        }
    }
}
