package labirinto;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import labirinto.constantes.ConstantesRobo;
import labirinto.dados.Direcao;
import labirinto.dados.EstadoNodo;
import labirinto.dados.Grafo;
import labirinto.dados.Movimento;
import labirinto.dados.Nodo;
import labirinto.dados.TipoMovimento;
import labirinto.mapeamentos.ControladorMovimentacao;
import labirinto.mapeamentos.MapeamentoDirecoes;
import lejos.nxt.ColorSensor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.UltrasonicSensor;

public class Robo {

	private NXTRegulatedMotor motor1;
	private NXTRegulatedMotor motor2;

	private NXTRegulatedMotor motorSensor;

	private UltrasonicSensor sensorDistancia;
	private ColorSensor sensorCor;

	private Direcao orientacao;

	private Stack<Movimento> movimentos;
	private Stack<Point> posicoesAbertas;
	
	private int x;
	private int y;
	
	public Robo(NXTRegulatedMotor motor1, NXTRegulatedMotor motor2, NXTRegulatedMotor motorSensor, UltrasonicSensor sensorDistancia, ColorSensor sensorCor, Direcao orientacao) {
		super();
		this.motor1 = motor1;
		this.motor2 = motor2;
		this.motorSensor = motorSensor;
		this.sensorDistancia = sensorDistancia;
		this.sensorCor = sensorCor;
		this.orientacao = orientacao;
		
		this.movimentos = new Stack<>();
		this.posicoesAbertas = new Stack<>();
		this.x = 0;
		this.y = 0;
	}

	public Movimento mapearNodosAtuais(Grafo grafo) {
		List<Movimento> movimentosPossiveis = mapearMovimentos(grafo);
		
		if (movimentosPossiveis.size() > 0) {
			if (movimentosPossiveis.size() == 1) {
				grafo.getMatriz()[this.x][this.y].setEstado(EstadoNodo.FECHADO);
			} else {
				// Adiciona a posição atual do robô como sendo um nodo ainda aberto,
				// será usado para permitir que o robô volte para esse nodo mais
				// facilmente mais tarde.
				this.posicoesAbertas.push(new Point(this.x, this.y));
			}
			
			return movimentosPossiveis.get(0);
		}
		
		grafo.getMatriz()[this.x][this.y].setEstado(EstadoNodo.FECHADO);
		return new Movimento(TipoMovimento.VOLTAR_POSICAO_ABERTA);
	}

	public List<Movimento> mapearMovimentos(Grafo grafo) {
		List<Movimento> movimentosPossiveis = new ArrayList<>();
		
		this.mapearMovimentoNaDirecao(grafo, Direcao.Norte, movimentosPossiveis);
		
		motorSensor.rotate(ConstantesRobo.ROTACAO_SENSOR);
		this.mapearMovimentoNaDirecao(grafo, Direcao.Oeste, movimentosPossiveis);
		
		motorSensor.rotate(-(ConstantesRobo.ROTACAO_SENSOR * 2));
		this.mapearMovimentoNaDirecao(grafo, Direcao.Leste, movimentosPossiveis);
		
		motorSensor.rotate(ConstantesRobo.ROTACAO_SENSOR);
		
		return movimentosPossiveis;
	}
	
	public void mapearMovimentoNaDirecao(Grafo grafo, Direcao direcao, List<Movimento> movimentos) {
		int distancia = sensorDistancia.getDistance();
		if (distancia > ConstantesRobo.DISTANCIA_PAREDE) {
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
			return new Movimento(TipoMovimento.CURVA, direcaoRotacao);
		}
	}

	public void executarMovimento(Movimento movimento, Grafo grafo) {
		switch(movimento.getTipoMovimento()) {
		
		case DESLOCAMENTO:
			this.executarDeslocamento(movimento.getDirecao(), grafo, grafo != null);
			adicionarMovimentoPilha(movimento);
			break;
			
		case CURVA:
			this.executarRotacao(movimento.getDirecao());
			adicionarMovimentoPilha(movimento);
			// Executa um deslocamento depois de cada rotação, para evitar que o
			// robô analise duas vezes os mesmos nodos sempre que executar uma
			// rotação.
			if (!movimento.ehReverso()) {
				Movimento movimentoDeslocamento = new Movimento(TipoMovimento.DESLOCAMENTO, Direcao.Norte);
				this.executarMovimento(movimentoDeslocamento, grafo);				
			}
			break;
		
		case VOLTAR_POSICAO_ABERTA:
			this.voltarUltimaPosicaoAberta(grafo);
			break;
			
		}
	}
	
	private void adicionarMovimentoPilha(Movimento movimento) {
		if (!movimento.ehReverso()) {
			this.movimentos.push(movimento);
		}
	}

	private void voltarUltimaPosicaoAberta(Grafo grafo) {
		Point ultimaPosicaoAberta = this.posicoesAbertas.pop();
		
		while(this.x != ultimaPosicaoAberta.x ||
			  this.y != ultimaPosicaoAberta.y) {
			Movimento movimento = this.movimentos.pop();
			Movimento movimentoR = movimento.reverter();
			this.executarMovimento(movimentoR, grafo);
		}
	}

	public void executarRotacao(Direcao direcaoRotacao) {
		if (direcaoRotacao == Direcao.Leste) {
			motor1.rotate(-ConstantesRobo.ROTACAO, true);
			motor2.rotate(ConstantesRobo.ROTACAO);
		} else {
			motor1.rotate(ConstantesRobo.ROTACAO, true);
			motor2.rotate(-ConstantesRobo.ROTACAO);
		}
		
		this.orientacao = MapeamentoDirecoes.obterNovaDirecao(this.orientacao, direcaoRotacao);
	}

	public void executarDeslocamento(Direcao direcao, Grafo grafo, boolean procurarFinal) {
		if (direcao == Direcao.Sul) {
			motor1.rotate(-ConstantesRobo.DESLOCAMENTO, true);
			motor2.rotate(-ConstantesRobo.DESLOCAMENTO);
		} else {
			motor1.rotate(ConstantesRobo.DESLOCAMENTO, true);
			motor2.rotate(ConstantesRobo.DESLOCAMENTO);	
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
		int nivelLuz = this.sensorCor.getLightValue();
		if (nivelLuz < ConstantesRobo.NIVEL_VERDE) {
			grafo.getMatriz()[this.x][this.y].setFinal(true);
		}
	}

	public void voltarAoPontoInicial() {
		ConstantesRobo.DESLOCAMENTO = 630;
		
		while (!this.movimentos.isEmpty()) {
			Movimento movimento = this.movimentos.pop();
			Movimento movimentoR = movimento.reverter();
			this.executarMovimento(movimentoR, null);
		}
		
		ConstantesRobo.DESLOCAMENTO = 690;
	}
	
	public void movimentarEntreNodos(List<Nodo> nodos) {
		for (int i = 0; i < nodos.size() - 1; i++) {
			Direcao direcaoMovimento = ControladorMovimentacao.definirDirecaoEntreNodos(nodos.get(i), nodos.get(i + 1));
			Movimento movimento = this.definirMovimento(direcaoMovimento);
			this.executarMovimento(movimento, null);
		}
	}
} 
