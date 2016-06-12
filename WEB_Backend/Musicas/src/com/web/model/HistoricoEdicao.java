package com.web.model;

public class HistoricoEdicao {

	private Usuario usuario;
	private Musica musica;
	private String campo;
	private String valorAntigo;
	private String valorNovo;
	private String tipoAlteracao;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Musica getMusica() {
		return musica;
	}
	public void setMusica(Musica musica) {
		this.musica = musica;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getValorAntigo() {
		return valorAntigo;
	}
	public void setValorAntigo(String valorAntigo) {
		this.valorAntigo = valorAntigo;
	}
	public String getValorNovo() {
		return valorNovo;
	}
	public void setValorNovo(String valorNovo) {
		this.valorNovo = valorNovo;
	}
	public String getTipoAlteracao() {
		return tipoAlteracao;
	}
	public void setTipoAlteracao(String tipoAlteracao) {
		this.tipoAlteracao = tipoAlteracao;
	}
	
}
