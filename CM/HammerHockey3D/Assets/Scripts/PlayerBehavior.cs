using UnityEngine;
using UnityEngine.UI;
using System.Collections;

using Assets.Classes;

public class PlayerBehavior : MonoBehaviour {

    VerticeTabuleiro casaAtual;

    Vector3 translacao;
    Vector3 velocidade = Vector3.zero;
    Vector3 deslocamento;

    public float velocidadeAnimacao;
    public int numeroJogador;

    float alturaPlayer;

    public int avancarPlayer = 0;

    // Use this for initialization
    void Start()
    {
        translacao = transform.position;
        deslocamento = transform.position;
        alturaPlayer = translacao.y;
	}
	
	void FixedUpdate()
    {
        if (translacao != transform.position)
        {
            transform.position = Vector3.SmoothDamp(transform.position, translacao, ref velocidade, velocidadeAnimacao);
        }
        else if (avancarPlayer > 0)
        {
            translacao = casaAtual.GetPosicao() + deslocamento;
            translacao.y = alturaPlayer;
            casaAtual = casaAtual.proximo;

            avancarPlayer--;
        }
    }

    void LateUpdate()
    {
    }

    public void SetCasaInicial(VerticeTabuleiro vertice)
    {
        casaAtual = vertice.proximo;
    }
}
