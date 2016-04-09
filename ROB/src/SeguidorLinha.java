import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class SeguidorLinha {

	public static void main(String[] args) throws InterruptedException {
		NXTRegulatedMotor motor1 = Motor.A;
		NXTRegulatedMotor motor2 = Motor.B;

		NXTRegulatedMotor ultimoMotor = motor1;

		UltrasonicSensor distancia = new UltrasonicSensor(SensorPort.S2);
		ColorSensor luz = new ColorSensor(SensorPort.S1);

		boolean parar = false;
		boolean procurandoVermelho = false;
		
		int controleRotacoes = 0;
		int controleVerde = 0;
		int distanciaParede = 9;
		
		int velocidadeRotacao = 10;
		boolean retornoImediato = true;
		
		motor1.setSpeed(200);
		motor2.setSpeed(200);
		while (!parar) {
			if (!procurandoVermelho) {
				int nivel = luz.getLightValue();
				// Para evitar que o robô encontre um verde aleatoriamente na pista e 
				// inicie a etapa 3 por engano.
				if (controleVerde <= 5) {
					if (luz.getColorID() == 1) {
						controleVerde++;
					}
					
					if (nivel < 60) {
						ultimoMotor = motor1;
						motor2.rotate(velocidadeRotacao, retornoImediato);
					} else {
						ultimoMotor = motor2;
						motor1.rotate(velocidadeRotacao, retornoImediato);
					}
				} else {
					procurandoVermelho = true;
					ultimoMotor.rotate(velocidadeRotacao);
					// Rotate forçado no final da rampa para evitar problemas 
					// com o sensor de distância.
					motor1.rotate(400, true);
					motor2.rotate(400);						
				}
			} else {
				// A cada 3 "rotações" diminui a distância da parede que o robô para, o robô 
				// faz uma espiral na área em que está até encontrar a cor vermelha.
				if (controleRotacoes == 3) {
					distanciaParede += 3;
				}

				Motor.A.forward();
				Motor.B.forward();

				while (luz.getColorID() != 0 && distancia.getDistance() > distanciaParede) { }

				Motor.A.stop(true);
				Motor.B.stop();

				if (luz.getColorID() == 0) {
					parar = true;
				}

				if (!parar) {
					Motor.A.rotate(190, true);
					Motor.B.rotate(-190);
				}

				controleRotacoes++;
			}
		}
	}
}
