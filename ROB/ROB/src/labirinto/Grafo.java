package labirinto;

public class Grafo {

	private Nodo[][] matriz;

	public Grafo(Nodo[][] matriz) {
		super();
		this.matriz= matriz;
	}
	
	public Nodo[][] getMatriz() {
		return this.matriz;
	}
	
	public static boolean estaNoGrafo(int posicao) {
		return posicao < 4 && posicao > -1;
	}
	
	public boolean estaCompleto() {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				if (matriz[i][j].getEstado() != EstadoNodo.FECHADO) {
					return false;
				}
			}
		}
		
		return true;
	}
	
}
