using UnityEngine;
using System.Collections;

public class CasaBehavior : MonoBehaviour {

    private GameObject label;
    private GameObject casa;

    private string textoLabel;
    public Color corCasa;

	// Use this for initialization
	void Start () {
        label = transform.Find("LabelTabuleiro").gameObject;
        casa = transform.Find("PecaTabuleiro").gameObject;

        label.GetComponent<TextMesh>().text = textoLabel;
        casa.GetComponent<Renderer>().material.color = corCasa;
	}
	
	// Update is called once per frame
	void Update () {
	
	}

    public void SetTexto(string texto)
    {
        textoLabel = texto;
    }
}
