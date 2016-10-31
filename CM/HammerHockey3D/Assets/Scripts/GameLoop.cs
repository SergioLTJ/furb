using UnityEngine;
using System.Collections;

using Assets.Classes;

public class GameLoop : MonoBehaviour {

    public TabuleiroCreator tabuleiroCreator;

    private ArrayList tabuleiro;
    private PlayerBehavior player;

	// Use this for initialization
	void Start () {
        tabuleiroCreator = new TabuleiroCreator();
        tabuleiro = new ArrayList();

        GerarTabuleiro();

        player = GameObject.Find("Jogador").GetComponent<PlayerBehavior>();
        player.SetCasaInicial(tabuleiroCreator.GetInicio());
	}
	
    void FixedUpdate()
    {
        
    }

	// Update is called once per frame
	void Update () {
	
	}

    private void GerarTabuleiro()
    {
        VerticeTabuleiro vertice = tabuleiroCreator.GetInicio();
        Vector3 posicaoAtual = new Vector3(0, 0, 0);

        while (vertice != null)
        {
            GameObject novaPeca = Instantiate(Resources.Load("PecaTabuleiro"), posicaoAtual, Quaternion.identity) as GameObject;
            Debug.Log("Criado objeto PecaTabuleiro nas coordenadas " + posicaoAtual.x + ", " + posicaoAtual.y + ", " + posicaoAtual.z);

            switch (vertice.tipo)
            {
                case TipoVertice.inicio:
                    novaPeca.GetComponent<Renderer>().material.color = Color.blue;
                    break;
                case TipoVertice.final:
                    novaPeca.GetComponent<Renderer>().material.color = Color.red;
                    break;
            }

            posicaoAtual += vertice.GetTranslacao();

            tabuleiro.Add(novaPeca);
            vertice = vertice.proximo;
        }
    }
}
