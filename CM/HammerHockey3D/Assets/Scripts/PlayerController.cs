using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class PlayerController : MonoBehaviour {

    public float speed;
    public Text scoreText;
    public Text winText;

    private Rigidbody rigidBody;
    private int score;
    
    void Start()
    {
        rigidBody = GetComponent<Rigidbody>();
        score = 0;
        SetScoreText();
    }

    void FixedUpdate()
    {
		float moveHorizontal = Input.GetAxis("Horizontal");
        float moveVertical = Input.GetAxis("Vertical");

        rigidBody.AddForce(new Vector3(moveHorizontal, 0, moveVertical) * speed);
    }

    void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.CompareTag("PickUp"))
        {
            other.gameObject.SetActive(false);
            ++score;
            SetScoreText();
        }
    }

    void SetScoreText()
    {
        scoreText.text = "SCORE: " + score.ToString();
        winText.text = score > 20 ? "A WINNER IS YOU" : "";
    }
}
