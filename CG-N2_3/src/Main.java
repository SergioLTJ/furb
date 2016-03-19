/// \file Exemplo_N2_Jogl_Eclipse.java
/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	
	private float limiteEsquerda = -400.0f;
	private float limiteDireita = 400.0f;
	private float limiteBaixo = -400.0f;
	private float limiteCima = 400.0f;
	private float locomocao = 10.0f;
	
	private Circulo circulo1;
	private Circulo circulo2;
	private Circulo circulo3;
	private Triangulo triangulo;
	
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		this.circulo1 = new Circulo(Color.BLACK, new Ponto4D(100, 100), 100, 72, GL.GL_LINE_LOOP, 2, 2);
		this.circulo2 = new Circulo(Color.BLACK, new Ponto4D(-100, 100), 100, 72, GL.GL_LINE_LOOP, 2, 2);
		this.circulo3 = new Circulo(Color.BLACK, new Ponto4D(0, -100), 100, 72, GL.GL_LINE_LOOP, 2, 2);
		
		Ponto4D ponto1 = new Ponto4D(-100, 100);
		Ponto4D ponto2 = new Ponto4D(100, 100);
		Ponto4D ponto3 = new Ponto4D(0, -100);
		this.triangulo = new Triangulo(ponto1, ponto2, ponto3, Color.CYAN, GL.GL_LINE_LOOP, 1);
	}

	// exibicaoPrincipal
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(limiteEsquerda, limiteDireita, limiteBaixo, limiteCima);

		SRU();

		// seu desenho ...
		gl.glColor3f(0, 0, 0);
		gl.glLineWidth(2);

		this.circulo1.desenhar(gl);
		this.circulo2.desenhar(gl);
		this.circulo3.desenhar(gl);
		
		this.triangulo.desenhar(gl);
		
		gl.glFlush();
	}

	public void keyPressed(KeyEvent e) {
		System.out.println(" --- keyPressed ---");
		switch (e.getKeyCode()) {
		case KeyEvent.VK_I:
			incrementarZoom();
			break;
		case KeyEvent.VK_O:
			limiteEsquerda -= locomocao;
			limiteDireita += locomocao;
			limiteBaixo -= locomocao;
			limiteCima += locomocao;
			glDrawable.display();
			break;
		case KeyEvent.VK_E:
			limiteEsquerda -= locomocao;
			limiteDireita -= locomocao;
			glDrawable.display();
			break;
		case KeyEvent.VK_D:
			limiteEsquerda += locomocao;
			limiteDireita += locomocao;
			glDrawable.display();
			break;
		case KeyEvent.VK_B:
			limiteCima -= locomocao;
			limiteBaixo -= locomocao;
			glDrawable.display();
			break;
		case KeyEvent.VK_C:
			limiteCima += locomocao;
			limiteBaixo += locomocao;
			glDrawable.display();
			break;
		}
	}

	private void incrementarZoom() {
		// São todos incrementados/decrementados em conjunto 
		// então só é necessário checar um deles.
		if (limiteEsquerda < -locomocao) {
			limiteEsquerda += locomocao;
			limiteDireita -= locomocao;
			limiteBaixo += locomocao;
			limiteCima -= locomocao;
			glDrawable.display();
		}
	}
	
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		System.out.println(" --- reshape ---");
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {
		System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
		System.out.println(" --- keyTyped ---");
	}

	public void SRU() {
		// eixo x
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glLineWidth(1.0f);
		gl.glBegin(GL.GL_LINES);
		{
			gl.glVertex2f(-200.0f, 0.0f);
			gl.glVertex2f(200.0f, 0.0f);
		}
		gl.glEnd();

		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		{
			gl.glVertex2f(0.0f, -200.0f);
			gl.glVertex2f(0.0f, 200.0f);
		}
		gl.glEnd();
	}
}
