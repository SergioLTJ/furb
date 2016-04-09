package exemploSubsumption;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class Desviar implements Behavior {

	private UltrasonicSensor sensorDistancia;
	private NXTRegulatedMotor motor1;
	private NXTRegulatedMotor motor2;
	
	public Desviar(UltrasonicSensor sensorDistancia, NXTRegulatedMotor motor1, NXTRegulatedMotor motor2) {	
		this.sensorDistancia = sensorDistancia;
		this.motor1 = motor1;
		this.motor2 = motor2;
	}
	
	@Override
	public boolean takeControl() {
		return sensorDistancia.getDistance() < 10;
	}

	@Override
	public void action() {
		motor1.rotate(190, true);
		motor2.rotate(-190);
	}

	@Override
	public void suppress() { }

}
