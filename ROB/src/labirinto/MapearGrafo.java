package labirinto;

import java.util.LinkedList;

import lejos.nxt.ColorSensor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class MapearGrafo implements Behavior {

	private NXTRegulatedMotor motor1;
	private NXTRegulatedMotor motor2;
	
	private NXTRegulatedMotor motorSensor;
	
	private UltrasonicSensor sensorDistancia;
	private ColorSensor sensorCor;
	
	private Direcao orientacao;
	
	private Robo robo;
	private Nodo[][] grafo;
	private int xAtual;
	private int yAtual;
	
	private LinkedList<Movimento> movimentos;
	
	public MapearGrafo(Nodo[][] grafo, int xAtual, int yAtual, Robo robo) {
		super();
		this.grafo = grafo;
		this.robo = robo;
		this.xAtual = xAtual;
		this.yAtual = yAtual;
		
		this.movimentos = new LinkedList<>();
	}
	
	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		this.grafo = robo.mapearNodosAtuais(grafo, xAtual, yAtual);
		Movimento movimentoAtual = definirMovimento();

		this.movimentos.add(0, movimentoAtual);
		this.executarMovimento(movimentoAtual);
	}

	private void executarMovimento(Movimento movimento) {
		if (movimento.getTipoMovimento() == TipoMovimento.ROTACAO) {
			robo.executarRotacao(movimento.getDirecao());
		} else {
			robo.executarDeslocamento(movimento.getDirecao());
		}
	}

	private Movimento definirMovimento() {
		if (estaInbound((xAtual + 1))) {
			if (grafo[(xAtual + 1)][yAtual].getEstado() == EstadoNodo.ENCONTRADO) {
				return robo.definirMovimento(Direcao.Leste);
			}
		}
		if (estaInbound((xAtual - 1))) {
			if (grafo[(xAtual - 1)][yAtual].getEstado() == EstadoNodo.ENCONTRADO) {
				return robo.definirMovimento(Direcao.Oeste);
			}
		}
		if (estaInbound((yAtual + 1))) {
			if (grafo[xAtual][(yAtual + 1)].getEstado() == EstadoNodo.ENCONTRADO) {
				return robo.definirMovimento(Direcao.Sul);
			}
		}
		if (estaInbound((yAtual - 1))) {
			if (grafo[xAtual][(yAtual - 1)].getEstado() == EstadoNodo.ENCONTRADO) {
				return robo.definirMovimento(Direcao.Norte);
			}
		}
		
		return movimentos.poll().reverter();
	}

	public static boolean estaInbound(int valor) {
		return valor > -1 && valor < 4;
	}
	
	private boolean naoEstaCompleto() {
		for (int i = 0; i < grafo.length; i++) {
			for (int j = 0; j < grafo[i].length; j++) {
				if (grafo[i][j].getEstado() != EstadoNodo.FECHADO) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void suppress() { }

}