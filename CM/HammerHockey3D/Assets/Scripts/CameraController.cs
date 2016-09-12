using UnityEngine;
using System.Collections;

public class CameraController : MonoBehaviour {

    public GameObject playerObject;

    private Vector3 offset;

	void Start () {
        offset = transform.position - playerObject.transform.position;
	}
	
	void LateUpdate () {
        transform.position = playerObject.transform.position + offset;
	}
}
