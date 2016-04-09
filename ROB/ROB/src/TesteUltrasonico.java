import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class TesteUltrasonico {
public static void main(String[] args) {
	UltrasonicSensor sensorDistancia = new UltrasonicSensor(SensorPort.S2);
	
	Motor.C.setSpeed(900);
	
	while(true) {
		if (Button.ENTER.isPressed()) {
			System.out.println(sensorDistancia.getDistance());
			Motor.B.rotate(95);
			System.out.println(sensorDistancia.getDistance());
			Motor.B.rotate(-190);
			System.out.println(sensorDistancia.getDistance());
			Motor.B.rotate(95);
		}
	}
}

}
