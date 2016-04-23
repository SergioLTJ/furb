package labirinto.dados;

import java.util.ArrayList;
import java.util.List;

public class Nodo {

	private EstadoNodo estado;
	private boolean ehFinal;
	private List<Nodo> vizinhos;
	private int x;
	private int y;
	
	public Nodo(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.estado = EstadoNodo.NAO_VISITADO;
		this.vizinhos = new ArrayList<>();
		this.ehFinal = false;
	}
	
	public EstadoNodo getEstado() {
		return estado;
	}
	
	public void setEstado(EstadoNodo estado) {
		this.estado = estado;
	}

	public List<Nodo> getVizinhos() {
		return vizinhos;
	}
	
	public void addVizinho(Nodo nodo) {
		if (!this.vizinhos.contains(nodo)) {
			this.vizinhos.add(nodo);
		}
	}

	public void setVizinhos(List<Nodo> vizinhos) {
		this.vizinhos = vizinhos;
	}
	
	public boolean ehFinal() {
		return this.ehFinal;
	}
	
	public void setFinal(boolean valor) {
		this.ehFinal = valor;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
