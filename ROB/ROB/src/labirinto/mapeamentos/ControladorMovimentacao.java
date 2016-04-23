package labirinto.mapeamentos;

import java.awt.Point;

import labirinto.dados.Direcao;
import labirinto.dados.Grafo;
import labirinto.dados.Nodo;

public class ControladorMovimentacao {

	public static Nodo obterNodo(Grafo grafo, Point posicao, Direcao direcao) {
		Point novaPosicao = ControladorMovimentacao.definirNovaPosicao(posicao, direcao);
		
		if (!Grafo.estaNoGrafo(novaPosicao.x) ||
			!Grafo.estaNoGrafo(novaPosicao.y))
			return null;
		
		return grafo.getMatriz()[novaPosicao.x][novaPosicao.y];
	}

	public static Point definirNovaPosicao(Point posicao, Direcao direcao) {
		switch (direcao) {
		case Leste:
			return new Point(posicao.x + 1, posicao.y);
		case Oeste:
			return new Point(posicao.x - 1, posicao.y);
		case Norte:
			return new Point(posicao.x, posicao.y - 1);
		case Sul:
			return new Point(posicao.x, posicao.y + 1);
		}
		return posicao;
	}
	
	public static Direcao definirDirecaoEntreNodos(Nodo nodo1, Nodo nodo2) {
		if (nodo1.getX() < nodo2.getX()) {
			return Direcao.Leste;
		} else if (nodo1.getX() > nodo2.getX()) {
			return Direcao.Oeste;
		} else if (nodo1.getY() < nodo2.getY()) {
			return Direcao.Sul;
		} else {
			return Direcao.Norte;
		}
		
	}
	
}
