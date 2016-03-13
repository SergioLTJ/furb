/// \file Exemplo_N2_Jogl_Eclipse.java
/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private ArrayList<Ponto4D> pontos;
	
	private float angulo = 45;
	private float raio = 100;
	private Ponto4D posicao = new Ponto4D(0, 0, 1, 1);
	private float incrementoRaio = 5;
	private float incrementoAngulo = 5;
	
	private float limiteEsquerda = -400.0f;
	private float limiteDireita = 400.0f;
	private float limiteBaixo = -400.0f;
	private float limiteCima = 400.0f;
	private float incrementoCamera = 10.0f;
	
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		carregarPontos();
	}

	private void carregarPontos() {
		pontos = new ArrayList<>();
		
		for (int angulo = 0; angulo < 360; angulo += 5) {
			double x = RetornaX(angulo, 100);
			double y = RetornaY(angulo, 100);
			Ponto4D ponto = new Ponto4D(x, y, 1, 1);
			pontos.add(ponto);
		}
	}

	// exibicaoPrincipal
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(limiteEsquerda, limiteDireita, limiteBaixo, limiteCima);

		SRU();

		gl.glColor3f(0, 0, 0);
		gl.glLineWidth(3);
		gl.glPointSize(4);

		double x = posicao.obterX();
		double y = posicao.obterY();
		
		Ponto4D segundoPonto = new Ponto4D(x + RetornaX(angulo, raio),
										   y + RetornaY(angulo, raio), 1, 1);
		
		int[] primitivas = {GL.GL_POINTS, GL.GL_LINES};
		
		for (int primitiva : primitivas) {
			gl.glBegin(primitiva);
			{
				gl.glVertex2d(x, y);
				gl.glVertex2d(segundoPonto.obterX(), segundoPonto.obterY());
			}
			gl.glEnd();
		}
		
		gl.glFlush();
	}

	public void keyPressed(KeyEvent e) {
		System.out.println(" --- keyPressed ---");
		switch (e.getKeyCode()) {
		case KeyEvent.VK_I:
			incrementarZoom();
			break;
		case KeyEvent.VK_O:
			limiteEsquerda -= incrementoCamera;
			limiteDireita += incrementoCamera;
			limiteBaixo -= incrementoCamera;
			limiteCima += incrementoCamera;
			glDrawable.display();
			break;
		case KeyEvent.VK_E:
			limiteEsquerda -= incrementoCamera;
			limiteDireita -= incrementoCamera;
			glDrawable.display();
			break;
		case KeyEvent.VK_D:
			limiteEsquerda += incrementoCamera;
			limiteDireita += incrementoCamera;
			glDrawable.display();
			break;
		case KeyEvent.VK_B:
			limiteCima -= incrementoCamera;
			limiteBaixo -= incrementoCamera;
			glDrawable.display();
			break;
		case KeyEvent.VK_C:
			limiteCima += incrementoCamera;
			limiteBaixo += incrementoCamera;
			glDrawable.display();
			break;
		case KeyEvent.VK_Q:
			posicao.atribuirX(posicao.obterX() - incrementoCamera);
			glDrawable.display();
			break;
		case KeyEvent.VK_W:
			posicao.atribuirX(posicao.obterX() + incrementoCamera);
			glDrawable.display();
			break;
		case KeyEvent.VK_A:
			if (raio > 0) {
				raio -= incrementoRaio;
				glDrawable.display();
			}
			break;
		case KeyEvent.VK_S:
			raio += incrementoRaio;
			glDrawable.display();
			break;
		case KeyEvent.VK_Z:
			if (angulo == 360) {
				angulo = incrementoAngulo;
			} else {
				angulo += incrementoAngulo;
			}
			glDrawable.display();
			break;
		case KeyEvent.VK_X:
			if (angulo == 0) {
				angulo = 360;
			} else {
				angulo -= incrementoAngulo;
			}
			glDrawable.display();
			break;
		}
	}

	private void incrementarZoom() {
		// São todos incrementados/decrementados em conjunto 
		// então só é necessário checar um deles.
		if (limiteEsquerda < -incrementoCamera) {
			limiteEsquerda += incrementoCamera;
			limiteDireita -= incrementoCamera;
			limiteBaixo += incrementoCamera;
			limiteCima -= incrementoCamera;
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

	public double RetornaX(double angulo, double raio) {
		return (raio * Math.cos(Math.PI * angulo / 180.0));
	}

	public double RetornaY(double angulo, double raio) {
		return (raio * Math.sin(Math.PI * angulo / 180.0));
	}

}
