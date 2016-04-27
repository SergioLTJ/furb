package labirinto.acoes;

import labirinto.Robo;
import labirinto.dados.Grafo;
import labirinto.dados.Movimento;
import lejos.robotics.subsumption.Behavior;

public class MapearGrafo implements Behavior {

	private Robo robo;
	private Grafo grafo;

	public MapearGrafo(Grafo grafo, Robo robo) {
		super();
		this.grafo = grafo;
		this.robo = robo;
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		Movimento movimentoAtual = this.robo.mapearNodosAtuais(this.grafo);
		this.robo.executarMovimento(movimentoAtual, this.grafo);
	}

	@Override
	public void suppress() { }

}