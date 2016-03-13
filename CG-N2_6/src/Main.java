/// \file Exemplo_N2_Jogl_Eclipse.java
/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

import java.awt.Color;
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
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;

	private float limiteEsquerda = -400.0f;
	private float limiteDireita = 400.0f;
	private float limiteBaixo = -400.0f;
	private float limiteCima = 400.0f;
	private float locomocao = 10.0f;

	private int antigoX = 0;
	private int antigoY = 0;
	
	private Ponto4D[] pontos;
	private int pontoSelecionado = 0;
	
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		pontos = new Ponto4D[] {
			new Ponto4D(100, -100, 1, 1),
			new Ponto4D(100, 100, 1, 1),
			new Ponto4D(-100, 100, 1, 1),
			new Ponto4D(-100, -100, 1, 1),
		};
	}

	// exibicaoPrincipal
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(limiteEsquerda, limiteDireita, limiteBaixo, limiteCima);

		SRU();

		// seu desenho ...
		gl.glLineWidth(3);
		
		Color cor = Color.CYAN;
		gl.glColor3f(cor.getRed(), cor.getGreen(), cor.getBlue());
		
		gl.glBegin(GL.GL_LINE_STRIP);
		{
			for (int i = 0; i < pontos.length; i++) {
				gl.glVertex2d(pontos[i].obterX(), pontos[i].obterY());
			}
		}
		gl.glEnd();

		Color amarelo = Color.YELLOW;
		gl.glColor3f(amarelo.getRed(), amarelo.getGreen(), amarelo.getBlue());
		
		gl.glBegin(GL.GL_LINES);
		{
			Ponto4D pontoInicial = pontos[0];
			Ponto4D pontoFinal = pontos[3];
			gl.glVertex2d(pontoInicial.obterX(), pontoInicial.obterY());
			gl.glVertex2d(pontoFinal.obterX(), pontoFinal.obterY());
		}
		gl.glEnd();
		
		gl.glPointSize(7);
		gl.glColor3f(255, 0, 0);
		
		gl.glBegin(GL.GL_POINTS);
		{
			Ponto4D ponto = pontos[pontoSelecionado];
			gl.glVertex2d(ponto.obterX(), ponto.obterY());
		}
		gl.glEnd();
		
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
		case KeyEvent.VK_1:
			pontoSelecionado = 0;
			glDrawable.display();
			break;
		case KeyEvent.VK_2:
			pontoSelecionado = 1;
			glDrawable.display();
			break;
		case KeyEvent.VK_3:
			pontoSelecionado = 2;
			glDrawable.display();
			break;
		case KeyEvent.VK_4:
			pontoSelecionado = 3;
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

	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
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

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		 antigoX = e.getX();
		 antigoY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		Ponto4D ponto = pontos[pontoSelecionado];
		
		int movtoX = e.getX() - antigoX;
	    int movtoY = e.getY() - antigoY;
	    
	    ponto.atribuirX(ponto.obterX() + movtoX); 
	    ponto.atribuirY(ponto.obterY() - movtoY);
	    
	    antigoX = e.getX();
		antigoY = e.getY();

		glDrawable.display();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}
}
