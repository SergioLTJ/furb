using UnityEngine;
using UnityEngine.UI;
using System.Collections;

using Assets.Classes;

public class PlayerBehavior : MonoBehaviour {

    VerticeTabuleiro casaAtual;
    public Text textoFinal;

    Vector3 translacao;
    Vector3 velocidade = Vector3.zero;

    public float velocidadeAnimacao;
    float alturaPlayer;

    public bool avancarPlayer = false;

    // Use this for initialization
    void Start()
    {
        translacao = transform.position;
        alturaPlayer = translacao.y;
	}
	
	void FixedUpdate()
    {
        if (translacao != transform.position)
        {
            transform.position = Vector3.SmoothDamp(transform.position, translacao, ref velocidade, velocidadeAnimacao);
        }
        else if (Input.GetKey(KeyCode.Space) || avancarPlayer)
        {
            //if (casaAtual.tipo == TipoVertice.final)
            //{
            //    textoFinal.text = "FIM!";
            //}
            //else 
            {
                translacao = casaAtual.GetPosicao();
                translacao.y = alturaPlayer;
                casaAtual = casaAtual.proximo;

                avancarPlayer = false;
            }
        }
    }

    void LateUpdate()
    {
    }

    public void SetCasaInicial(VerticeTabuleiro vertice)
    {
        casaAtual = vertice;
    }
}
