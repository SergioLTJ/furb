package labirinto.dados;

import labirinto.mapeamentos.MapeamentoDirecoes;

public class Movimento {

	private TipoMovimento tipoMovimento;
	private Direcao direcao;
	private boolean ehReverso;
	
	public Movimento(TipoMovimento tipoMovimento) {
		this(tipoMovimento, Direcao.Norte);
	}
	
	public Movimento(TipoMovimento tipoMovimento, Direcao direcao) {
		this.tipoMovimento =tipoMovimento;
		this.direcao = direcao;
		this.ehReverso = false;
	}

	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

	public Direcao getDirecao() {
		return direcao;
	}

	public void setDirecao(Direcao direcao) {
		this.direcao = direcao;
	}

	public void setReverso(boolean ehReverso) {
		this.ehReverso = ehReverso;
	}
	
	public boolean ehReverso() {
		return this.ehReverso;
	}
	
	public Movimento reverter() {
		Direcao nova = MapeamentoDirecoes.obterReverso(this.direcao);
		Movimento novo = new Movimento(this.tipoMovimento, nova);
		novo.ehReverso = true;
		return novo;
	}

}