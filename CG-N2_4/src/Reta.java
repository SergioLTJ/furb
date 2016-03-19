import java.awt.Color;

import javax.media.opengl.GL;

public class Reta extends GlDesenhavel {

	private Ponto4D ponto1;
	private Ponto4D ponto2;

	protected Reta(Ponto4D ponto1, Color cor, int primitivaGl, int larguraLinha, int tamanhoPontos) {
		this(ponto1, new Ponto4D(0, 0), cor, primitivaGl, larguraLinha, tamanhoPontos);
	}
	
	protected Reta(Ponto4D ponto1, Ponto4D ponto2, Color cor, int primitivaGl, int larguraLinha, int tamanhoPontos) {
		super(cor, primitivaGl, larguraLinha, tamanhoPontos);
		
		this.ponto1 = ponto1;
		this.ponto2 = ponto2;
	}

	public Ponto4D getPonto1() {
		return ponto1;
	}

	public void setPonto1(Ponto4D ponto1) {
		this.ponto1 = ponto1;
	}

	public Ponto4D getPonto2() {
		return ponto2;
	}

	public void setPonto2(Ponto4D ponto2) {
		this.ponto2 = ponto2;
	}

	public void atribuirPosicaoPonto2(float angulo, float raio) {
		this.ponto2.atribuirX(this.ponto1.obterX() + Funcoes.RetornaX(angulo, raio));
		this.ponto2.atribuirY(this.ponto1.obterY() + Funcoes.RetornaY(angulo, raio));
	}
	
	@Override
	protected void desenharEspecifico(GL gl, boolean desenharVertices) {
		gl.glBegin(this.primitivaGl);
		{
			gl.glVertex2d(ponto1.obterX(), ponto1.obterY());
			gl.glVertex2d(ponto2.obterX(), ponto2.obterY());
		}
		gl.glEnd();
		
		if (desenharVertices) {
			gl.glPointSize(this.larguraLinha + 1);
			
			gl.glBegin(GL.GL_POINTS);
			{
				gl.glVertex2d(ponto1.obterX(), ponto1.obterY());
				gl.glVertex2d(ponto2.obterX(), ponto2.obterY());
			}
			gl.glEnd();
		}
	}
}
