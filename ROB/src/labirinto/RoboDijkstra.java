package labirinto;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class RoboDijkstra {

	public static void main(String[] args) {
		Nodo[][] grafo = new Nodo[][] {
			{criarNodo(),criarNodo(),criarNodo(),criarNodo()},
			{criarNodo(),criarNodo(),criarNodo(),criarNodo()},
			{criarNodo(),criarNodo(),criarNodo(),criarNodo()},
			{criarNodo(),criarNodo(),criarNodo(),criarNodo()}
		};
		
		Robo robo = new Robo(Motor.A, Motor.C, Motor.B, new UltrasonicSensor(SensorPort.S2), new ColorSensor(SensorPort.S1), Direcao.Sul);
		
		MapearGrafo mapear = new MapearGrafo(grafo, 0, 0, robo);
		
		Arbitrator arb = new Arbitrator(new Behavior[] {mapear});
		arb.start();
	}
	
	public static Nodo criarNodo() {
		return new Nodo(EstadoNodo.NAO_VISITADO);
	}
	
}
