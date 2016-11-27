using UnityEngine;
using System.Collections;

public class Minigame : MonoBehaviour {

    public float distanciaEncaixe = 1.0f;
    public Color corEncaixe;

    public GameObject torso;
    public GameObject cabeca;
    public GameObject bracoD;
    public GameObject bracoE;
    public GameObject pernaD;
    public GameObject pernaE;

    public GameObject cerebro;
    public GameObject coracao;
    public GameObject pulmao;
    public GameObject intestino;

    private Vector3 posicaoCerebro;
    private Vector3 posicaoCoracao;
    private Vector3 posicaoPulmao;
    private Vector3 posicaoIntestino;

    private Color corCerebro;
    private Color corCoracao;
    private Color corPulmao;
    private Color corIntestino;

    // Funcionalidades
    public void MostraMinigame(bool mostrar)
    {
        gameObject.SetActive(mostrar);
    }

    public void MoveCerebro(Vector2 v)
    {
        Vector3 posicao = ToPosicaoLocal(v);

        if (Vector3.Distance(posicao, posicaoCerebro) < distanciaEncaixe)
        {
            posicao = posicaoCerebro;
            cerebro.GetComponent<Renderer>().material.color = corEncaixe;
        }
        else
        {
            cerebro.GetComponent<Renderer>().material.color = corCerebro;
        }

        cerebro.transform.position = posicao;
    }

    public void MoveCoracao(Vector2 v)
    {
        Vector3 posicao = ToPosicaoLocal(v);

        if (Vector3.Distance(posicao, posicaoCoracao) < distanciaEncaixe)
        {
            posicao = posicaoCoracao;
            coracao.GetComponent<Renderer>().material.color = corEncaixe;
        }
        else
        {
            coracao.GetComponent<Renderer>().material.color = corCoracao;
        }

        coracao.transform.position = posicao;
    }

    public void MovePulmao(Vector2 v)
    {
        Vector3 posicao = ToPosicaoLocal(v);

        if (Vector3.Distance(posicao, posicaoPulmao) < distanciaEncaixe)
        {
            posicao = posicaoPulmao;
            pulmao.GetComponent<Renderer>().material.color = corEncaixe;
        }
        else
        {
            pulmao.GetComponent<Renderer>().material.color = corPulmao;
        }

        pulmao.transform.position = posicao;
    }

    public void MoveIntestino(Vector2 v)
    {
        Vector3 posicao = ToPosicaoLocal(v);

        if (Vector3.Distance(posicao, posicaoIntestino) < distanciaEncaixe)
        {
            posicao = posicaoIntestino;
            intestino.GetComponent<Renderer>().material.color = corEncaixe;
        }
        else
        {
            intestino.GetComponent<Renderer>().material.color = corIntestino;
        }


        intestino.transform.position = posicao;
    }

    public void SetCorPlayer(Color cor)
    {
        torso.GetComponent<Renderer>().material.color = cor;
        cabeca.GetComponent<Renderer>().material.color = cor;
        bracoD.GetComponent<Renderer>().material.color = cor;
        bracoE.GetComponent<Renderer>().material.color = cor;
        pernaD.GetComponent<Renderer>().material.color = cor;
        pernaE.GetComponent<Renderer>().material.color = cor;
    }

	// Use this for initialization
	void Start () {
        posicaoCerebro      = cerebro.transform.position;
        posicaoCoracao      = coracao.transform.position;
        posicaoPulmao       = pulmao.transform.position;
        posicaoIntestino    = intestino.transform.position;

        corCerebro   = cerebro.GetComponent<Renderer>().material.color;
        corCoracao   = coracao.GetComponent<Renderer>().material.color;
        corPulmao    = pulmao.GetComponent<Renderer>().material.color;
        corIntestino = intestino.GetComponent<Renderer>().material.color;

        MostraMinigame(false);
	}
	
	// Update is called once per frame
	void Update () {
	
	}

    private Vector3 ToPosicaoLocal(Vector2 v)
    {
        return new Vector3(v.x + posicaoCerebro.x, posicaoCerebro.y, v.y + posicaoCerebro.z);
    }
}
