using UnityEngine;
using System.Collections;

public class Minigame : MonoBehaviour {

    static float conversaoXY = 0.03926f / 2;
    Vector2 origemRelativa = new Vector2(166.75f, 0); //new Vector2(8.5f, -13);

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

    public bool encaixouCerebro   = false;
    public bool encaixouCoracao   = false;
    public bool encaixouPulmao    = false;
    public bool encaixouIntestino = false;

    public bool mostraMinigame = false;

    public Vector2 posCerebro  ;
    public Vector2 posCoracao  ;
    public Vector2 posPulmao   ;
    public Vector2 posIntestino;

    // Funcionalidades
    public void MostraMinigame()
    {
        torso.SetActive(mostraMinigame);
        cabeca.SetActive(mostraMinigame);
        bracoD.SetActive(mostraMinigame);
        bracoE.SetActive(mostraMinigame);
        pernaD.SetActive(mostraMinigame);
        pernaE.SetActive(mostraMinigame);

        cerebro.SetActive(mostraMinigame);
        coracao.SetActive(mostraMinigame);
        pulmao.SetActive(mostraMinigame);
        intestino.SetActive(mostraMinigame);
    }

    public void MoveCerebro()
    {
        Vector3 posicao = ToPosicaoLocal(posCerebro);

        if (encaixouCerebro)
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

    public void MoveCoracao()
    {
        Vector3 posicao = ToPosicaoLocal(posCoracao);

        if (encaixouCoracao)
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

    public void MovePulmao()
    {
        Vector3 posicao = ToPosicaoLocal(posPulmao);

        if (encaixouPulmao)
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

    public void MoveIntestino()
    {
        Vector3 posicao = ToPosicaoLocal(posIntestino);

        if (encaixouIntestino)
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

        posCerebro   = new Vector2(0, 0);
        posCoracao   = new Vector2(0, 0);
        posPulmao    = new Vector2(0, 0);
        posIntestino = new Vector2(0, 0);

        corCerebro   = cerebro.GetComponent<Renderer>().material.color;
        corCoracao   = coracao.GetComponent<Renderer>().material.color;
        corPulmao    = pulmao.GetComponent<Renderer>().material.color;
        corIntestino = intestino.GetComponent<Renderer>().material.color;

        MostraMinigame();
	}
	
	// Update is called once per frame
	void Update () {
        MostraMinigame();

        MoveCerebro();
        MoveCoracao();
        MovePulmao();
        MoveIntestino();
	}

    private Vector3 ToPosicaoLocal(Vector2 v)
    {
        return new Vector3(transform.position.x + ((v.x + origemRelativa.x) * conversaoXY), posicaoCerebro.y, transform.position.z + ((v.y + origemRelativa.y) * conversaoXY));
    }
}
