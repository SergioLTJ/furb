package labirinto;

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
	
	public Robo(NXTRegulatedMotor motor1, NXTRegulatedMotor motor2, NXTRegulatedMotor motorSensor, UltrasonicSensor sensorDistancia, ColorSensor sensorCor, Direcao orientacao) {
		super();
		this.motor1 = motor1;
		this.motor2 = motor2;
		this.motorSensor = motorSensor;
		this.sensorDistancia = sensorDistancia;
		this.sensorCor = sensorCor;
		this.orientacao = orientacao;
	}
	
	public Nodo[][] mapearNodosAtuais(Nodo[][] grafo, int x, int y) {
		int xLocal = x;
		int yLocal = y;
		
		int distanciaFrente = sensorDistancia.getDistance();
		if (distanciaFrente > 30) {
			if (MapearGrafo.estaInbound(yLocal + 1) && orientacao == Direcao.Sul) {
				grafo[xLocal][++yLocal].setEstado(EstadoNodo.ENCONTRADO);
			}
			if (MapearGrafo.estaInbound(yLocal - 1) && orientacao == Direcao.Norte) {
				grafo[xLocal][--yLocal].setEstado(EstadoNodo.ENCONTRADO);
			}
			if (MapearGrafo.estaInbound(xLocal + 1) && orientacao == Direcao.Leste) {
				grafo[++xLocal][yLocal].setEstado(EstadoNodo.ENCONTRADO);
			}
			if (MapearGrafo.estaInbound(xLocal - 1) && orientacao == Direcao.Oeste) {
				grafo[--xLocal][yLocal].setEstado(EstadoNodo.ENCONTRADO);
			}
		}
		
		xLocal = x;
		yLocal = y;
		
		motorSensor.rotate(95);
		int distanciaEsquerda = sensorDistancia.getDistance();
		if (distanciaEsquerda > 30) {
			if (MapearGrafo.estaInbound(xLocal + 1) && orientacao == Direcao.Sul) {
				grafo[++xLocal][yLocal].setEstado(EstadoNodo.ENCONTRADO);
			}
			if (MapearGrafo.estaInbound(xLocal - 1) && orientacao == Direcao.Norte) {
				grafo[--xLocal][yLocal].setEstado(EstadoNodo.ENCONTRADO);
			}
			if (MapearGrafo.estaInbound(yLocal - 1) && orientacao == Direcao.Leste) {
				grafo[xLocal][--yLocal].setEstado(EstadoNodo.ENCONTRADO);
			}
			if (MapearGrafo.estaInbound(yLocal + 1) && orientacao == Direcao.Oeste) {
				grafo[xLocal][++yLocal].setEstado(EstadoNodo.ENCONTRADO);
			}
		}
		
		xLocal = x;
		yLocal = y;
		
		motorSensor.rotate(-190);
		int distanciaDireita = sensorDistancia.getDistance();
		if (distanciaDireita > 30) {
			if (MapearGrafo.estaInbound(xLocal - 1) && orientacao == Direcao.Sul) {
				grafo[--xLocal][yLocal].setEstado(EstadoNodo.ENCONTRADO);
			}
			if (MapearGrafo.estaInbound(xLocal + 1) && orientacao == Direcao.Norte) {
				grafo[++xLocal][yLocal].setEstado(EstadoNodo.ENCONTRADO);
			}
			if (MapearGrafo.estaInbound(yLocal + 1) && orientacao == Direcao.Leste) {
				grafo[xLocal][++yLocal].setEstado(EstadoNodo.ENCONTRADO);
			}
			if (MapearGrafo.estaInbound(yLocal - 1) && orientacao == Direcao.Oeste) {
				grafo[xLocal][--yLocal].setEstado(EstadoNodo.ENCONTRADO);
			}
		}
	
		motorSensor.rotate(95);
		
		return grafo;
	}

	public Movimento definirMovimento(Direcao direcaoNovoNodo) {
		if (this.orientacao.ehOrientacaoOposta(direcaoNovoNodo)) {
			Direcao direcaoRotacao = definirDirecaoRotacao(direcaoNovoNodo);
			return new Movimento(TipoMovimento.ROTACAO, direcaoRotacao);
		} else {
			return new Movimento(TipoMovimento.DESLOCAMENTO, Direcao.Norte);
		}
	}
	
	public Direcao definirDirecaoRotacao(Direcao direcaoNovoNodo) {
		switch (direcaoNovoNodo) {
		case Oeste:
			return this.orientacao == Direcao.Norte ?
					Direcao.Leste :
					Direcao.Oeste;
		case Leste:
			return this.orientacao == Direcao.Norte ?
					Direcao.Oeste :
					Direcao.Leste;
		case Norte:
			return this.orientacao == Direcao.Oeste ?
					Direcao.Leste :
					Direcao.Oeste;
		case Sul:
			return this.orientacao == Direcao.Oeste ?
					Direcao.Oeste :
					Direcao.Leste;
		}
		
		return Direcao.Norte;
	}

	public void executarRotacao(Direcao direcaoRotacao) {
		if (direcaoRotacao == direcaoRotacao.Leste) {
			motor1.rotate(190, true);
			motor2.rotate(-190);
			if (this.orientacao == Direcao.Leste) {
				this.orientacao = Direcao.Norte;
			}
			if (this.orientacao == Direcao.Norte) {
				this.orientacao = Direcao.Oeste;
			}
			if (this.orientacao == Direcao.Sul) {
				this.orientacao = Direcao.Leste;
			}
			if (this.orientacao == Direcao.Oeste) {
				this.orientacao = Direcao.Sul;
			}
		} else {
			motor1.rotate(-190, true);
			motor2.rotate(190);
			if (this.orientacao == Direcao.Leste) {
				this.orientacao = Direcao.Sul;
			}
			if (this.orientacao == Direcao.Norte) {
				this.orientacao = Direcao.Leste;
			}
			if (this.orientacao == Direcao.Sul) {
				this.orientacao = Direcao.Oeste;
			}
			if (this.orientacao == Direcao.Oeste) {
				this.orientacao = Direcao.Norte;
			}
		}
	}

	public void executarDeslocamento(Direcao direcao) {
		if (direcao == Direcao.Sul) {
			motor1.rotate(-480, true);
			motor2.rotate(-480);
			return;
		}
		motor1.rotate(480, true);
		motor2.rotate(480);
	}
}

