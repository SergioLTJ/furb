package labirinto;

import labirinto.acoes.ExecutarMenorCaminho;
import labirinto.acoes.MapearGrafo;
import labirinto.dados.Direcao;
import labirinto.dados.Grafo;
import labirinto.dados.Nodo;
import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class RoboMenorCaminho {

	public static void main(String[] args) {
		Nodo[][] nodos = new Nodo[][] {
			{ new Nodo(0, 0), new Nodo(0, 1), new Nodo(0, 2), new Nodo(0, 3) },
			{ new Nodo(1, 0), new Nodo(1, 1), new Nodo(1, 2), new Nodo(1, 3) },
			{ new Nodo(2, 0), new Nodo(2, 1), new Nodo(2, 2), new Nodo(2, 3) },
			{ new Nodo(3, 0), new Nodo(3, 1), new Nodo(3, 2), new Nodo(3, 3) }
		};
		
		Grafo grafo = new Grafo(nodos);
		
		UltrasonicSensor ultrasonico = new UltrasonicSensor(SensorPort.S4);
		ColorSensor cor = new ColorSensor(SensorPort.S1);
		Robo robo = new Robo(Motor.A, Motor.C, Motor.B, ultrasonico, cor, Direcao.Sul);
		
		MapearGrafo mapear = new MapearGrafo(grafo, robo);
		ExecutarMenorCaminho executar = new ExecutarMenorCaminho(grafo, robo, grafo.getMatriz()[0][0]);
		
		Arbitrator arb = new Arbitrator(new Behavior[] { mapear, executar });
		arb.start();
	}
}
