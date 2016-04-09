package labirinto;

public class Nodo {

	private EstadoNodo estado;
	
	public Nodo(EstadoNodo estado) {
		super();
		this.estado = EstadoNodo.NAO_VISITADO;
	}
	
	public EstadoNodo getEstado() {
		return estado;
	}
	
	public void setEstado(EstadoNodo estado) {
		this.estado = estado;
	}
}
