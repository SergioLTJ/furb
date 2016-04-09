package labirinto;

import java.util.List;

public class Grafo {

	private List<Nodo> nodos;

	public Grafo(List<Nodo> nodos) {
		super();
		this.nodos = nodos;
	}
	
	public boolean estaCompleto() {
		for (Nodo nodo : nodos) {
			if (nodo.getEstado() != EstadoNodo.FECHADO) {
				return false;
			}
		}
		return true;
	}
	
}
