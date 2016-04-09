package labirinto;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import lejos.nxt.LightSensor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.UltrasonicSensor;
import mapeamentos.MapeamentoDirecoes;

public class Robo {

	private NXTRegulatedMotor motor1;
	private NXTRegulatedMotor motor2;

	private NXTRegulatedMotor motorSensor;

	private UltrasonicSensor sensorDistancia;
	private LightSensor sensorLuz;

	private Direcao orientacao;

	private Stack<Movimento> movimentos;
	private Movimento ultimoComPosicaoAberta;
	
	private int x;
	private int y;
	
	public Robo(NXTRegulatedMotor motor1, NXTRegulatedMotor motor2, NXTRegulatedMotor motorSensor, UltrasonicSensor sensorDistancia, LightSensor sensorLuz, Direcao orientacao) {
		super();
		this.motor1 = motor1;
		this.motor2 = motor2;
		this.motorSensor = motorSensor;
		this.sensorDistancia = sensorDistancia;
		this.sensorLuz = sensorLuz;
		this.orientacao = orientacao;
		
		this.movimentos = new Stack<>();
		this.x = 0;
		this.y = 0;
	}

	public Movimento mapearNodosAtuais(Grafo grafo) {
		List<Movimento> movimentosPossiveis = new ArrayList<>();
		
		this.mapearMovimentoNaDirecao(grafo, Direcao.Norte, movimentosPossiveis);
		
		motorSensor.rotate(95);
		this.mapearMovimentoNaDirecao(grafo, Direcao.Leste, movimentosPossiveis);
		
		motorSensor.rotate(-190);
		this.mapearMovimentoNaDirecao(grafo, Direcao.Oeste, movimentosPossiveis);
		
		motorSensor.rotate(95);
		
		if (movimentosPossiveis.size() > 0) {
			if (movimentosPossiveis.size() == 1) {
				grafo.getMatriz()[this.x][this.y].setEstado(EstadoNodo.FECHADO);
			}
			return movimentosPossiveis.get(0);
		}
		
		grafo.getMatriz()[this.x][this.y].setEstado(EstadoNodo.FECHADO);
		return this.movimentos.pop().reverter();
	}

	public void mapearMovimentoNaDirecao(Grafo grafo, Direcao direcao, List<Movimento> movimentos) {
		int distancia = sensorDistancia.getDistance();
		if (distancia > 30) {
			Nodo nodoAtual = grafo.getMatriz()[this.x][this.y];
			Direcao direcaoNova = MapeamentoDirecoes.obterNovaDirecao(this.orientacao, direcao);
			Nodo novo = ControladorMovimentacao.obterNodo(grafo, new Point(this.x, this.y), direcaoNova);
			
			if (novo != null && novo.getEstado() != EstadoNodo.FECHADO) {
				nodoAtual.addVizinho(novo);
				novo.setEstado(EstadoNodo.ENCONTRADO);

				Movimento movimento = this.definirMovimento(direcaoNova);
				
				movimentos.add(movimento);
			}
		}
	}
	
	public Movimento definirMovimento(Direcao direcaoNovoNodo) {
		if (this.orientacao == direcaoNovoNodo) {
			return new Movimento(TipoMovimento.DESLOCAMENTO, Direcao.Norte);
		} else {
			Direcao direcaoRotacao = MapeamentoDirecoes.definirDirecaoRotacao(this.orientacao, direcaoNovoNodo);
			return new Movimento(TipoMovimento.ROTACAO, direcaoRotacao);
		}
	}

	public void executarMovimento(Movimento movimento, Grafo grafo) {
		if (movimento.getTipoMovimento() == TipoMovimento.ROTACAO) {
			this.executarRotacao(movimento.getDirecao());
		} else {
			this.executarDeslocamento(movimento.getDirecao(), grafo, grafo != null);
		}
		
		this.movimentos.push(movimento);
	}
	
	public void executarRotacao(Direcao direcaoRotacao) {
		if (direcaoRotacao == Direcao.Leste) {
			motor1.rotate(190, true);
			motor2.rotate(-190);
		} else {
			motor1.rotate(-190, true);
			motor2.rotate(190);
		}
		
		this.orientacao = MapeamentoDirecoes.obterNovaDirecao(this.orientacao, direcaoRotacao);
	}

	public void executarDeslocamento(Direcao direcao, Grafo grafo, boolean procurarFinal) {
		if (direcao == Direcao.Sul) {
			motor1.rotate(-480, true);
			motor2.rotate(-480);
		} else {
			motor1.rotate(480, true);
			motor2.rotate(480);	
		}
		
		atualizarPosicao(direcao);
		
		if (procurarFinal) {
			verificarNodoFinal(grafo);	
		}
	}

	private void atualizarPosicao(Direcao direcaoDeslocamento) {
		Direcao direcaoNoGrafo = MapeamentoDirecoes.obterNovaDirecao(this.orientacao, direcaoDeslocamento);
		Point novaPosicao = ControladorMovimentacao.definirNovaPosicao(new Point(this.x, this.y), direcaoNoGrafo);
		
		this.x = novaPosicao.x;
		this.y = novaPosicao.y;
		
	}

	private void verificarNodoFinal(Grafo grafo) {
		int nivelLuz = this.sensorLuz.getLightValue();
		if (nivelLuz < 60) {
			grafo.getMatriz()[this.x][this.y].setFinal(true);
		}
	}

	public Movimento reverterUltimoMovimento() {
		return this.movimentos.pop().reverter()	;
	}

	public void voltarAoPontoInicial() {
		while (!this.movimentos.isEmpty()) {
			Movimento movimento = this.movimentos.pop().reverter();
			this.executarMovimento(movimento, null);
		}
	}
	
	public void movimentarEntreNodos(List<Nodo> nodos) {
		for (int i = 0; i < nodos.size() - 1; i++) {
			Direcao direcaoMovimento = ControladorMovimentacao.definirDirecaoEntreNodos(nodos.get(i), nodos.get(i + 1));
			Movimento movimento = this.definirMovimento(direcaoMovimento);
			this.executarMovimento(movimento, null);
		}
	}
} 
