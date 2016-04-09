package labirinto;

import java.util.List;

import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;

public class ExecutarMenorCaminho implements Behavior{
	
	private Grafo grafo;
	private Robo robo;
	private Nodo nodoInicial;
	private boolean estaNoPontoInicial;

	public ExecutarMenorCaminho(Grafo grafo, Robo robo, Nodo nodoInicial) {
		this.grafo = grafo;
		this.robo = robo;
		this.nodoInicial = nodoInicial;
		this.estaNoPontoInicial = false;
	}

	@Override
	public boolean takeControl() {
		return this.grafo.estaCompleto();
	}

	@Override
	public void action() {
		if (!estaNoPontoInicial) {
			robo.voltarAoPontoInicial();
			estaNoPontoInicial = true;
		}
		
		if (Button.ENTER.isPressed()) {
			resetarEstadoNodos(this.nodoInicial);
			List<Nodo> nodos = new BuscaLargura().executar(nodoInicial);
			this.robo.movimentarEntreNodos(nodos);
		}
	}

	private void resetarEstadoNodos(Nodo nodo) {
		for (Nodo vizinho : nodo.getVizinhos()) {
			if (vizinho.getEstado() != EstadoNodo.NAO_VISITADO) {
				vizinho.setEstado(EstadoNodo.NAO_VISITADO);
			}
			resetarEstadoNodos(vizinho);
		}
	}

	@Override
	public void suppress() { }

}
