package Tela;

/// \file Exemplo_N2_Jogl_Eclipse.java
/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

import Graficos.Mundo;
import Graficos.ObjetoGrafico;
import Graficos.Ponto4D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {

	public static void trace(String str) {
		System.out.println(str);
	}

	// CLASSES GL
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;

	// DESENHO
	private float limiteEsquerda = -400.0f;
	private float limiteDireita = 400.0f;
	private float limiteBaixo = -400.0f;
	private float limiteCima = 400.0f;
	private float locomocao = 10.0f;

	// MUNDO
	Mundo mundo;
	boolean modoConstrucao;

	@Override
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		trace("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		mundo = new Mundo();
		modoConstrucao = false;
	}

	// exibicaoPrincipal
	@Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(limiteEsquerda, limiteDireita, limiteBaixo, limiteCima);

		SRU();

		mundo.display(gl);

		gl.glFlush();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		trace(" --- keyPressed ---");
		switch (e.getKeyCode()) {
		case KeyEvent.VK_EQUALS:
			incrementarZoom();
			break;
		case KeyEvent.VK_MINUS:
			limiteEsquerda -= locomocao;
			limiteDireita += locomocao;
			limiteBaixo -= locomocao;
			limiteCima += locomocao;
			glDrawable.display();
			break;
		case KeyEvent.VK_RIGHT:
			limiteEsquerda -= locomocao;
			limiteDireita -= locomocao;
			glDrawable.display();
			break;
		case KeyEvent.VK_LEFT:
			limiteEsquerda += locomocao;
			limiteDireita += locomocao;
			glDrawable.display();
			break;
		case KeyEvent.VK_UP:
			limiteCima -= locomocao;
			limiteBaixo -= locomocao;
			glDrawable.display();
			break;
		case KeyEvent.VK_DOWN:
			limiteCima += locomocao;
			limiteBaixo += locomocao;
			glDrawable.display();
			break;
		}
	}

	private void incrementarZoom() {
		// S�o todos incrementados/decrementados em conjunto
		// ent�o s� � necess�rio checar um deles.
		if (limiteEsquerda < -locomocao) {
			limiteEsquerda += locomocao;
			limiteDireita -= locomocao;
			limiteBaixo += locomocao;
			limiteCima -= locomocao;
			glDrawable.display();
		}
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		trace(" --- reshape ---");
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		trace(" --- displayChanged ---");
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		trace(" --- keyReleased ---");
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		trace(" --- keyTyped ---");
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

	@Override
	public void mouseClicked(MouseEvent e) {
		Ponto4D ponto = new Ponto4D(e.getX(), e.getY());
		ponto.setX(ponto.getX() + 200);
		ponto.setY((-ponto.getY()) + 200);
		
		if (modoConstrucao) {
			ObjetoGrafico selecao = mundo.getSelecao();
			int index = selecao.indexPonto(ponto);

			// Valida clique em ponto existente
			if (index >= 0) {
				// Valida fechamento
				mundo.finalizaConstrucaoObjeto(index);
				modoConstrucao = false;
			} else {
				// Adiciona vertice
				mundo.avancaConstrucaoObjeto(ponto);
			}
		} else {
			// Valida selecao de poligonos existentes

			// Cria novo poligono
			mundo.iniciaObjeto(ponto);
			modoConstrucao = true;
		}

		glDrawable.display();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Ponto4D ponto = new Ponto4D(e.getX(), e.getY());
		ponto.setX(ponto.getX() + 200);
		ponto.setY((-ponto.getY()) + 200);
		mundo.atualizaConstrucaoObjeto(ponto);
		glDrawable.display();
	}
}
