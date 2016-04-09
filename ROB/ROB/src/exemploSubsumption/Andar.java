package exemploSubsumption;

import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class Andar implements Behavior {

	NXTRegulatedMotor motor1;
	NXTRegulatedMotor motor2;
	
	public Andar(NXTRegulatedMotor motor1, NXTRegulatedMotor motor2) {
		super();
		this.motor1 = motor1;
		this.motor2 = motor2;
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		motor1.forward();
		motor2.forward();
	}

	@Override
	public void suppress() {
		motor1.stop(true);
		motor2.stop();
	}

}
