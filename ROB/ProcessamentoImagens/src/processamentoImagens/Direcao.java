package processamentoImagens;

public enum Direcao {
	Norte(360),
	Sul(180),
	Leste(90),
	Oeste(260);
	
	private int rotacaoRobo;
	
	Direcao(int robo) {
		this.rotacaoRobo = robo;
	}
	
	public int getRotacaoRobo() {
		return this.rotacaoRobo;
	}
	
	public boolean ehOrientacaoOposta(Direcao outra) {
		return (this == Direcao.Norte || this == Direcao.Sul) ?
				outra == Direcao.Oeste || outra == Direcao.Leste :
				outra == Direcao.Norte || outra == Direcao.Sul;
	}
}
