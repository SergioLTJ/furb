using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class PerguntaBehavior : MonoBehaviour {

    public GameObject fundo;
    public GameObject separador;

    public Text pergunta;
    public Text alt1;
    public Text alt2;
    public Text alt3;
    public Text alt4;

    public GameObject selecao;

    public void MostraPergunta(string pergunta, string alt1, string alt2, string alt3, string alt4)
    {
        this.pergunta.text = pergunta;

        this.alt1.text = "1) " + alt1;
        this.alt2.text = "2) " + alt2;
        this.alt3.text = "3) " + alt3;
        this.alt4.text = "4) " + alt4;

        AtivaDesativaPergunta(true);
    }

    public void SelecionaResposta(int indice)
    {
        switch (indice)
        {
            case 1:
                selecao.transform.position = alt1.transform.position;
                break;
            case 2:
                selecao.transform.position = alt2.transform.position;
                break;
            case 3:
                selecao.transform.position = alt3.transform.position;
                break;
            case 4:
                selecao.transform.position = alt4.transform.position;
                break;
        }

        AtivaDesativaSelecao(true);
    }

    public void FinalizaPergunta()
    {
        AtivaDesativaPergunta(false);
        AtivaDesativaSelecao(false);
    }

	// Use this for initialization
	void Start () {
        AtivaDesativaPergunta(false);
        AtivaDesativaSelecao(false);
	}
	
	// Update is called once per frame
	void Update () {
	    
	}

    void AtivaDesativaPergunta(bool ativar)
    {
        fundo.SetActive(ativar);
        separador.SetActive(ativar);

        pergunta.gameObject.SetActive(ativar);
        alt1.gameObject.SetActive(ativar);
        alt2.gameObject.SetActive(ativar);
        alt3.gameObject.SetActive(ativar);
        alt4.gameObject.SetActive(ativar);        
    }

    void AtivaDesativaSelecao(bool ativar)
    {
        selecao.SetActive(ativar);
    }
}
