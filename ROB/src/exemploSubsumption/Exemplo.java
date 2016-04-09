package exemploSubsumption;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Exemplo {

	public static void main(String[] args) {
		NXTRegulatedMotor motor1 = Motor.B;
		NXTRegulatedMotor motor2 = Motor.A;
		UltrasonicSensor sensorDistancia = new UltrasonicSensor(SensorPort.S2);
		
		Behavior[] comportamentos = new Behavior[]{
				new Andar(motor1, motor2),
				new Desviar(sensorDistancia, motor1, motor2),
				};
		
		Arbitrator arb = new Arbitrator(comportamentos);
		arb.start();
	}
	
}
