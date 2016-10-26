using UnityEngine;
using UnityEngine.UI;
using System.Collections;

using Assets.Classes;

public class PlayerBehavior : MonoBehaviour {

    VerticeTabuleiro casaAtual;
    public Text textoFinal;

    // Use this for initialization
    void Start()
    {
	}
	
	void FixedUpdate()
    {
        if (Input.GetKeyDown(KeyCode.Space))
        {
            if (casaAtual.tipo == TipoVertice.final)
            {
                textoFinal.text = "FIM!";
            }
            else 
            {
                transform.position += casaAtual.GetTranslacao();
                casaAtual = casaAtual.proximo;
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
