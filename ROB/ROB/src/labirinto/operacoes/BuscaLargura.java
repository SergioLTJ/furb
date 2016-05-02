package labirinto.operacoes;

import java.util.ArrayList;
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
		ArrayList<Nodo> fila = new ArrayList<>();
		fila.add(nodo);
		Nodo nodoAtual = null;
		do {
			nodoAtual = fila.get(fila.size() - 1);
			if (nodoAtual.ehFinal()) {
				break;
			}
			for (Nodo viz : nodoAtual.getVizinhos()) {
				fila.add(viz);
			}
		} while(!nodoAtual.ehFinal());
		
		return nodos.addAll(fila);
		
//		for (Nodo vizinho : nodo.getVizinhos()) {
//			if (buscar(vizinho, nodos)) {
//				nodos.add(0, vizinho);
//				return true;
//			}
//		}
//		
//		if (nodo.ehFinal()) {
//			nodos.add(0, nodo);
//			return true;
//		}
//		
//		return false;
	}
}
