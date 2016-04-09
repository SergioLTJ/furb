package labirinto;

import java.util.HashSet;
import java.util.Set;

public class Nodo {

	private EstadoNodo estado;
	private boolean ehFinal;
	private Set<Nodo> vizinhos;
	private int x;
	private int y;
	
	public Nodo(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.estado = EstadoNodo.NAO_VISITADO;
		this.vizinhos = new HashSet<>();
		this.ehFinal = false;
	}
	
	public EstadoNodo getEstado() {
		return estado;
	}
	
	public void setEstado(EstadoNodo estado) {
		this.estado = estado;
	}

	public Set<Nodo> getVizinhos() {
		return vizinhos;
	}
	
	public void addVizinho(Nodo nodo) {
		this.vizinhos.add(nodo);
	}

	public void setVizinhos(Set<Nodo> vizinhos) {
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
