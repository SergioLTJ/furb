import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;

public class SensorLuz {
	public static void main(String[] args) {
		ColorSensor luz = new ColorSensor(SensorPort.S1);
		
//		while(!Button.ENTER.isPressed()) {
//			System.out.println(luz.getLightValue());
//		}
//		
//		luz.setLow(luz.getLightValue());
//		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		while(!Button.ENTER.isPressed()) {
//			System.out.println(luz.getLightValue());
//		}
//
//		luz.setHigh(luz.getLightValue());
		
		while(true) {	
			System.out.println(luz.getLightValue());
		}
	}
}
