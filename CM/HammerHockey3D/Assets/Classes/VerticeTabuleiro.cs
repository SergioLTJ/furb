using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using UnityEngine;

namespace Assets.Classes
{
    public enum TipoVertice
    {
        normal,
        pergunta,
        minigame,
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
        public static float ESPACAMENTO = 0;

        Vector2 posicao;

        public VerticeTabuleiro proximo { get; set; }
       
        public TipoVertice tipo { get; set; }

        public VerticeTabuleiro(TipoVertice tipo)
        {
            this.tipo = tipo;
            posicao = new Vector2(0, 0);
        }

        public VerticeTabuleiro(TipoVertice tipo, VerticeTabuleiro pai, DirecaoAvanco direcao)
        {
            this.tipo = tipo;
            this.posicao = pai.posicao + Deslocamento(direcao);

            pai.proximo = this;
        }

        public Vector3 GetPosicao()
        {
            return new Vector3(posicao.x * ESPACAMENTO, 0, posicao.y * ESPACAMENTO);
        }

        private Vector2 Deslocamento(DirecaoAvanco direcao)
        {
            float x = 0;
            float z = 0;

            switch (direcao)
            {
                case DirecaoAvanco.cima:
                    z -= ESPACAMENTO;
                    break;
                case DirecaoAvanco.baixo:
                    z += ESPACAMENTO;
                    break;
                case DirecaoAvanco.direita:
                    x -= ESPACAMENTO;
                    break;
                case DirecaoAvanco.esquerda:
                    x += ESPACAMENTO;
                    break;
            }
            return new Vector2(x, z);
        }
    }
}
