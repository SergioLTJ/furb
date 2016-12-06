using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class PerguntaBehavior : MonoBehaviour {

    public GameObject fundo;
    public GameObject separador;

    string stringPergunta;
    string stringAlt1;
    string stringAlt2;
    string stringAlt3;
    string stringAlt4;

    bool bAtivaPergunta = false;
    bool bAtivaSelecao = false;

    int indexSelecao = 0;

    public Text pergunta;
    public Text alt1;
    public Text alt2;
    public Text alt3;
    public Text alt4;

    public GameObject selecao;
    public int status = 0; // 1 = certo; 2 = errado

    public void MostraPergunta(string pergunta, string alt1, string alt2, string alt3)
    {
        stringPergunta = pergunta;

        stringAlt1 = "1) " + alt1;
        stringAlt2 = "2) " + alt2;
        stringAlt3 = "3) " + alt3;

        bAtivaPergunta = true;
    }

    public void MostraErroAcerto(bool acerto)
    {
        stringPergunta = acerto ? "ACERTOU!" : "ERROU!";
        stringAlt1 = "";
        stringAlt2 = "";
        stringAlt3 = "";

        status = acerto ? 1 : 2;
    }

    public void SelecionaResposta(int indice)
    {
        indexSelecao = indice;

        bAtivaSelecao = true;
    }

    public void FinalizaPergunta()
    {
        bAtivaPergunta = false;
        bAtivaSelecao = false;
    }

	// Use this for initialization
	void Start () {
        AtivaDesativaPergunta();
        AtivaDesativaSelecao();
	}
	
	// Update is called once per frame
	void Update () {
        pergunta.text = stringPergunta;
        alt1.text = stringAlt1;
        alt2.text = stringAlt2;
        alt3.text = stringAlt3;
        alt4.text = stringAlt4;

        AtivaDesativaPergunta();
        AtivaDesativaSelecao();

        switch (indexSelecao)
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
	}

    void AtivaDesativaPergunta()
    {
        fundo.SetActive(bAtivaPergunta);
        separador.SetActive(bAtivaPergunta);

        pergunta.gameObject.SetActive(bAtivaPergunta);
        alt1.gameObject.SetActive(bAtivaPergunta);
        alt2.gameObject.SetActive(bAtivaPergunta);
        alt3.gameObject.SetActive(bAtivaPergunta);
        alt4.gameObject.SetActive(bAtivaPergunta);        
    }

    void AtivaDesativaSelecao()
    {
        selecao.SetActive(bAtivaSelecao);
    }
}
