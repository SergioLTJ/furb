import java.awt.Color;

import javax.media.opengl.GL;

public class Circulo extends GlDesenhavel {

	private Ponto4D centro;
	private int quantidadePontos;
	private double raio;

	public Circulo(Ponto4D centro, double raio) {
		this(Color.BLACK, centro, raio, 72, GL.GL_LINE_LOOP, 3, 3);
	}

	public Circulo(Ponto4D centro, double raio, int tamanhoPontos, int larguraLinha) {
		this(Color.BLACK, centro, raio, 72, GL.GL_LINE_LOOP, tamanhoPontos, larguraLinha);
	}

	public Circulo(Color cor, Ponto4D centro, double raio) {
		this(cor, centro, raio, 72, GL.GL_LINE_LOOP, 3, 3);
	}

	public Circulo(Color cor, Ponto4D centro, double raio, int quantidadePontos, int primitivaGl, int tamanhoPontos, int larguraLinha) {
		super(cor, primitivaGl, larguraLinha, tamanhoPontos);
		
		this.centro = centro;
		this.raio = raio;
		this.quantidadePontos = quantidadePontos;
	}

	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}

	public Ponto4D getCentro() {
		return centro;
	}

	public void setCentro(Ponto4D centro) {
		this.centro = centro;
	}

	public double getRaio() {
		return raio;
	}

	public void setRaio(double raio) {
		this.raio = raio;
	}

	public int getQuantidadePontos() {
		return quantidadePontos;
	}

	public void setQuantidadePontos(int quantidadePontos) {
		this.quantidadePontos = quantidadePontos;
	}

	public int getPrimitivaGl() {
		return primitivaGl;
	}

	public void setPrimitivaGl(int primitivaGl) {
		this.primitivaGl = primitivaGl;
	}

	public int getLarguraLinha() {
		return larguraLinha;
	}

	public void setLarguraLinha(int larguraLinha) {
		this.larguraLinha = larguraLinha;
	}

	public int getTamanhoPontos() {
		return tamanhoPontos;
	}

	public void setTamanhoPontos(int tamanhoPontos) {
		this.tamanhoPontos = tamanhoPontos;
	}

	@Override
	protected void desenharEspecifico(GL gl, boolean desenharVertices) {
		double incremento = 360 / this.quantidadePontos;
		
		gl.glBegin(this.primitivaGl);
		{
			for (int i = 0; i < 360; i += incremento) {
				gl.glVertex2d(this.centro.obterX() + Funcoes.RetornaX(i, this.raio), 
							  this.centro.obterY() + Funcoes.RetornaY(i, this.raio));
			}
		}
		gl.glEnd();
	}
}
