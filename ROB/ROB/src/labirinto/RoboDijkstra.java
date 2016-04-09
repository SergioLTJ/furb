package labirinto;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class RoboDijkstra {

	public static void main(String[] args) {
		Nodo[][] nodos = new Nodo[][] {
			{ new Nodo(0, 0), new Nodo(0, 1), new Nodo(0, 2), new Nodo(0, 3) },
			{ new Nodo(1, 0), new Nodo(1, 1), new Nodo(1, 2), new Nodo(1, 3) },
			{ new Nodo(2, 0), new Nodo(2, 1), new Nodo(2, 2), new Nodo(2, 3) },
			{ new Nodo(3, 0), new Nodo(3, 1), new Nodo(3, 2), new Nodo(3, 3) }
		};
		
		Grafo grafo = new Grafo(nodos);
		
		UltrasonicSensor ultrasonico = new UltrasonicSensor(SensorPort.S2);
		LightSensor luz = new LightSensor(SensorPort.S1);
		Robo robo = new Robo(Motor.A, Motor.C, Motor.B, ultrasonico, luz, Direcao.Sul);
		
		MapearGrafo mapear = new MapearGrafo(grafo, 0, 0, robo);
		ExecutarMenorCaminho executar = new ExecutarMenorCaminho(grafo, robo, grafo.getMatriz()[0][0]);
		
		Arbitrator arb = new Arbitrator(new Behavior[] { executar, mapear });
		arb.start();
	}
}
