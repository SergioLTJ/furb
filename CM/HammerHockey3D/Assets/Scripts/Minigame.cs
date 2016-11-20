using UnityEngine;
using System.Collections;

public class Minigame : MonoBehaviour {

    public float distanciaEncaixe = 1.0f;
    public Color corEncaixe;

    public GameObject cabeca;
    public GameObject torso;
    public GameObject bracoDireito;
    public GameObject bracoEsquerdo;
    public GameObject pernaDireita;
    public GameObject pernaEsquerda;

    private Vector3 posicaoCabeca;
    private Vector3 posicaoTorso;
    private Vector3 posicaoBracoDireito;
    private Vector3 posicaoBracoEsquerdo;
    private Vector3 posicaoPernaDireita;
    private Vector3 posicaoPernaEsquerda;

    private Color corNormal;

    // Funcionalidades
    public void MostraMinigame(bool mostrar)
    {
        cabeca       .SetActive(mostrar);
        torso        .SetActive(mostrar);
        bracoDireito .SetActive(mostrar);
        bracoEsquerdo.SetActive(mostrar);
        pernaDireita .SetActive(mostrar);
        pernaEsquerda.SetActive(mostrar);
    }

    public void MoveCabeca(Vector2 v)
    {
        Vector3 posicao = ToPosicaoLocal(v);

        if (Vector3.Distance(posicao, posicaoCabeca) < distanciaEncaixe)
        {
            posicao = posicaoCabeca;
            cabeca.GetComponent<Renderer>().material.color = corEncaixe;
        }
        else
        {
            cabeca.GetComponent<Renderer>().material.color = corNormal;
        }

        cabeca.transform.position = posicao;
    }

    public void MoveTorso(Vector2 v)
    {
        Vector3 posicao = ToPosicaoLocal(v);

        if (Vector3.Distance(posicao, posicaoTorso) < distanciaEncaixe)
        {
            posicao = posicaoCabeca;
            torso.GetComponent<Renderer>().material.color = corEncaixe;
        }
        else
        {
            torso.GetComponent<Renderer>().material.color = corNormal;
        }

        torso.transform.position = posicao;
    }

    public void MoveBracoDireito(Vector2 v)
    {
        Vector3 posicao = ToPosicaoLocal(v);

        if (Vector3.Distance(posicao, posicaoBracoDireito) < distanciaEncaixe)
        {
            posicao = posicaoCabeca;
            bracoDireito.GetComponent<Renderer>().material.color = corEncaixe;
        }
        else
        {
            bracoDireito.GetComponent<Renderer>().material.color = corNormal;
        }

        bracoDireito.transform.position = posicao;
    }

    public void MoveBracoEsquerdo(Vector2 v)
    {
        Vector3 posicao = ToPosicaoLocal(v);

        if (Vector3.Distance(posicao, posicaoBracoEsquerdo) < distanciaEncaixe)
        {
            posicao = posicaoCabeca;
            bracoEsquerdo.GetComponent<Renderer>().material.color = corEncaixe;
        }
        else
        {
            bracoEsquerdo.GetComponent<Renderer>().material.color = corNormal;
        }

        bracoEsquerdo.transform.position = posicao;
    }

    public void MovePernaDireita(Vector2 v)
    {
        Vector3 posicao = ToPosicaoLocal(v);

        if (Vector3.Distance(posicao, posicaoPernaDireita) < distanciaEncaixe)
        {
            posicao = posicaoCabeca;
            pernaDireita.GetComponent<Renderer>().material.color = corEncaixe;
        }
        else
        {
            pernaDireita.GetComponent<Renderer>().material.color = corNormal;
        }

        pernaDireita.transform.position = posicao;
    }

    public void MovePernaEsquerda(Vector2 v)
    {
        Vector3 posicao = ToPosicaoLocal(v);

        if (Vector3.Distance(posicao, posicaoPernaEsquerda) < distanciaEncaixe)
        {
            posicao = posicaoCabeca;
            pernaEsquerda.GetComponent<Renderer>().material.color = corEncaixe;
        }
        else
        {
            pernaEsquerda.GetComponent<Renderer>().material.color = corNormal;
        }


        pernaEsquerda.transform.position = posicao;
    }

	// Use this for initialization
	void Start () {
        posicaoCabeca        = cabeca       .transform.position;
        posicaoTorso         = torso        .transform.position;
        posicaoBracoDireito  = bracoDireito .transform.position;
        posicaoBracoEsquerdo = bracoEsquerdo.transform.position;
        posicaoPernaDireita  = pernaDireita .transform.position;
        posicaoPernaEsquerda = pernaEsquerda.transform.position;

        corNormal = torso.GetComponent<Renderer>().material.color;

        MostraMinigame(false);
	}
	
	// Update is called once per frame
	void Update () {
	
	}

    private Vector3 ToPosicaoLocal(Vector2 v)
    {
        return new Vector3(v.x + posicaoTorso.x, posicaoTorso.y, v.y + posicaoTorso.z);
    }
}
