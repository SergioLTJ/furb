import java.awt.Color;

import javax.media.opengl.GL;

public class Triangulo extends GlDesenhavel {

	private Ponto4D ponto1;
	private Ponto4D ponto2;
	private Ponto4D ponto3;
	
	public Triangulo(Ponto4D ponto1, Ponto4D ponto2, Ponto4D ponto3) {
		this(ponto1, ponto2, ponto3, Color.BLACK, GL.GL_LINE_LOOP, 3, 3);
	}

	public Triangulo(Ponto4D ponto1, Ponto4D ponto2, Ponto4D ponto3, Color cor) {
		this(ponto1, ponto2, ponto3, cor, GL.GL_LINE_LOOP, 3, 3);
	}
	
	public Triangulo(Ponto4D ponto1, Ponto4D ponto2, Ponto4D ponto3, Color cor, int primitivaGl) {
		this(ponto1, ponto2, ponto3, cor, primitivaGl, 3, 3);
	}

	public Triangulo(Ponto4D ponto1, Ponto4D ponto2, Ponto4D ponto3, Color cor, int primitivaGl, int larguraLinha) {
		this(ponto1, ponto2, ponto3, cor, primitivaGl, larguraLinha, 3);
	}
	
	public Triangulo(Ponto4D ponto1, Ponto4D ponto2, Ponto4D ponto3, Color cor, int primitivaGl, int larguraLinha, int tamanhoPonto) {
		super(cor, primitivaGl, larguraLinha, tamanhoPonto);
		
		this.ponto1 = ponto1;
		this.ponto2 = ponto2;
		this.ponto3 = ponto3;
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

	public Ponto4D getPonto3() {
		return ponto3;
	}

	public void setPonto3(Ponto4D ponto3) {
		this.ponto3 = ponto3;
	}

	@Override
	protected void desenharEspecifico(GL gl, boolean desenharVertices) {
        gl.glBegin(this.primitivaGl);
        {
            gl.glVertex2d(this.ponto1.obterX(), this.ponto1.obterY());
            gl.glVertex2d(this.ponto2.obterX(), this.ponto2.obterY());
            gl.glVertex2d(this.ponto3.obterX(), this.ponto3.obterY());
        }
        gl.glEnd();

	}

}
