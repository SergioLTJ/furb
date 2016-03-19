import java.awt.Color;

import javax.media.opengl.GL;

public abstract class GlDesenhavel {

	protected Color cor;
	protected int primitivaGl;
	protected int larguraLinha;
	protected int tamanhoPontos;
	
	protected GlDesenhavel(Color cor, int primitivaGl, int larguraLinha, int tamanhoPontos) {
		this.cor = cor;
		this.primitivaGl = primitivaGl;
		this.larguraLinha = larguraLinha;
		this.tamanhoPontos = tamanhoPontos;
	}

	public void desenhar(GL gl) {
		this.desenhar(gl, false);
	}
	
	public void desenhar(GL gl, boolean desenharVertices) {
		gl.glColor3f(this.cor.getRed(), this.cor.getGreen(), this.cor.getBlue());
		gl.glLineWidth(this.larguraLinha);
		gl.glPointSize(this.tamanhoPontos);
		
		desenharEspecifico(gl, desenharVertices);
	}

	protected abstract void desenharEspecifico(GL gl, boolean desenharVertices);
	
}