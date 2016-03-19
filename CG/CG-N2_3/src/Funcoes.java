
public class Funcoes {

	public static double RetornaX(double angulo, double raio) {
		return (raio * Math.cos(Math.PI * angulo / 180.0));
	}

	public static double RetornaY(double angulo, double raio) {
		return (raio * Math.sin(Math.PI * angulo / 180.0));
	}
}
