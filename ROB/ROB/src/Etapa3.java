//import lejos.nxt.Button;
//import lejos.nxt.LightSensor;
//import lejos.nxt.Motor;
//import lejos.nxt.SensorPort;
//import lejos.nxt.UltrasonicSensor;
//
//public class Etapa3 {
//	private static int DISTANCIA_INICIAL_ULTIMA_ETAPA  = 0;
//	private static int COR_VERMELHO = 0;s
//	
//	public static void executar() {
//		UltrasonicSensor distancia = new UltrasonicSensor(SensorPort.S2);
//		LightSensor luz = new LightSensor(SensorPort.S1)
//		
//		DISTANCIA_INICIAL_ULTIMA_ETAPA  = calibrarDistancia(distancia);
//		COR_VERMELHO = calibrarCor(luz, "vermelho");
//		
//		boolean parar = false;
//		int controle = 0;
//		int distanciaParede = DISTANCIA_INICIAL_ULTIMA_ETAPA;
//		
//		do {
//			if(controle == 3) {
//				distanciaParede += 3;
//			}
//
//			Motor.A.forward();
//			Motor.B.forward();
//			
//			while(luz.getLightValue() < COR_VERMELHO || distancia.getDistance() > distanciaParede) {}
//			
//			Motor.A.stop(true);
//			Motor.B.stop();
//			
//			if (luz.getLightValue() >= COR_VERMELHO) {
//				parar = true;
//			}
//			
//			controle++;
//		} while (!parar);
//	}
//	
//	private static int calibrarDistancia(UltrasonicSensor sensorDistancia) {
//		System.out.println("Calibrando distância: ");
//		
//		while(!Button.ENTER.isPressed()) {
//			Utils.wait(500);
//			System.out.println(sensorDistancia.getDistance());
//		}
//		
//		return sensorDistancia.getDistance();
//	}
//
//	private static int calibrarCor(LightSensor luz, String nomeCor) {
//		System.out.println("Calibrando " + nomeCor + ": ");
//
//		while(!Button.ENTER.isPressed()) {
//			Utils.wait(500);
//			System.out.println(luz.getLightValue());
//		}
//		
//		return luz.getLightValue();
//	}
//	
//	public static void main(String[] args) {
//		executar();
//	}
//}
