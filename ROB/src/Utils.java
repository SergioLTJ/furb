import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class Utils {

	public static void wait(int milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static int calibrarCor(LightSensor luz, String nomeCor) {
		System.out.println("Calibrando " + nomeCor + ": ");

		while(!Button.ENTER.isPressed()) {
			Utils.wait(500);
			System.out.println(luz.getLightValue());
		}
		
		Utils.wait(2000);
		
		return luz.getLightValue();
	}
	
	public static int calibrarDistancia(UltrasonicSensor sensorDistancia) {
		System.out.println("Calibrando distância: ");
		
		while(!Button.ENTER.isPressed()) {
			Utils.wait(500);
			System.out.println(sensorDistancia.getDistance());
		}
		
		Utils.wait(2000);
		
		return sensorDistancia.getDistance();
	}

	public static void main(String[] args) {
		UltrasonicSensor dist = new UltrasonicSensor(SensorPort.S2);
		while(true) {System.out.println(dist.getDistance());}
	}
	
}
