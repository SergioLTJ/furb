package labirinto;

import lejos.robotics.subsumption.Behavior;

public class MapearGrafo implements Behavior {

	private Robo robo;
	private Grafo grafo;

	public MapearGrafo(Grafo grafo, int xAtual, int yAtual, Robo robo) {
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