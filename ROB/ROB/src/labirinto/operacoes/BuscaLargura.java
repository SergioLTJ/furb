package labirinto.operacoes;

import java.util.LinkedList;
import java.util.List;

import labirinto.dados.Nodo;

public class BuscaLargura {

	public List<Nodo> executar(Nodo nodoInicial) {
		LinkedList<Nodo> nodos = new LinkedList<>();
		
		buscar(nodoInicial, nodos);
		return nodos;
	}

	private boolean buscar(Nodo nodo, LinkedList<Nodo> nodos) {
		if (nodo.ehFinal()) {
			nodos.add(0, nodo);
			return true;
		}
		
		for (Nodo vizinho : nodo.getVizinhos()) {
			if (buscar(vizinho, nodos)) {
				nodos.add(0, vizinho);
				return true;
			}
		}
		
		return false;
	}
}
