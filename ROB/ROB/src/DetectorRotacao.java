import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class DetectorRotacao {

	static int rotacao = 400;
	static NXTRegulatedMotor motor1 = Motor.A;
	static NXTRegulatedMotor motor2 = Motor.C;
	
	
	public static void main(String[] args) {
		Button.RIGHT.addButtonListener(new ButtonListener() {
			
			@Override
			public void buttonReleased(Button b) {
				rotacao += 10;
				System.out.println(rotacao);
			}
			
			@Override
			public void buttonPressed(Button b) {
			}
		});
		
		Button.LEFT.addButtonListener(new ButtonListener() {
			
			@Override
			public void buttonReleased(Button b) {
				rotacao -= 10;
				System.out.println(rotacao);
			}
			
			@Override
			public void buttonPressed(Button b) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button.ENTER.addButtonListener(new ButtonListener() {
			
			@Override
			public void buttonReleased(Button b) {
				motor1.rotate(rotacao, true);
				motor2.rotate(rotacao);
				System.out.println(rotacao);
			}
			
			@Override
			public void buttonPressed(Button b) {
				// TODO Auto-generated method stub
				
			}
		});
		
		while (true) {}
	}
	
}
