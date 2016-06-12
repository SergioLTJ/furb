package com.web.model;

import java.util.List;

public class Usuario {

	private int idUsuario;
	private String nome;
	private String email;
	private String imagemPerfil;
	private List<Musica> favoritas;
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImagemPerfil() {
		return imagemPerfil;
	}
	public void setImagemPerfil(String imagemPerfil) {
		this.imagemPerfil = imagemPerfil;
	}
	public List<Musica> getFavoritas() {
		return favoritas;
	}
	public void setFavoritas(List<Musica> favoritas) {
		this.favoritas = favoritas;
	}
	
}
