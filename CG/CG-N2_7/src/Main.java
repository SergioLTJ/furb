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
	
	private Circulo circuloInterno;
	private Circulo circuloExterno;
	private BoundingBox boundingBox;
	
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		circuloInterno = new Circulo(new Ponto4D(200, 200), 50, 1, 1);
		circuloExterno = new Circulo(new Ponto4D(200, 200), 150, 1, 1);
		
		double menorX = 200 + Funcoes.RetornaX(225, 150);
		double menorY = 200 + Funcoes.RetornaY(225, 150);
		double maiorX = 200 + Funcoes.RetornaX(45, 150);
		double maiorY = 200 + Funcoes.RetornaY(45, 150);
		boundingBox = new BoundingBox(menorX, menorY, 1d, maiorX, maiorY, 1d);
		
		boundingBox.setLarguraLinha(1);
	}

	// exibicaoPrincipal
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(limiteEsquerda, limiteDireita, limiteBaixo, limiteCima);

		SRU();

		if(validarBoundingBox()) {
			boundingBox.setCor(Color.MAGENTA); 
		} else {
			boundingBox.setCor(Color.YELLOW);
		}
		
		circuloInterno.desenhar(gl);
		boundingBox.desenharOpenGLBBox(gl);
		circuloExterno.desenhar(gl);
		
		Color cor = Color.BLACK;
		gl.glPointSize(5);
		gl.glColor3f(cor.getRed(), cor.getGreen(), cor.getBlue());
		Ponto4D centro = circuloInterno.getCentro();
		gl.glBegin(GL.GL_POINTS);
		{
			gl.glVertex2d(centro.obterX(), centro.obterY());
		}
		gl.glEnd();
		
		gl.glFlush();
	}

	private boolean validarBoundingBox() {
		Ponto4D centroInterno = circuloInterno.getCentro();
		
		if (centroInterno.obterX() < boundingBox.obterMenorX()) return false;
		if (centroInterno.obterX() > boundingBox.obterMaiorX()) return false;
		if (centroInterno.obterY() < boundingBox.obterMenorY()) return false;
		if (centroInterno.obterY() > boundingBox.obterMaiorY()) return false;
		
		return true;
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
	public void mouseReleased(MouseEvent e) {
		Ponto4D centroExterno = circuloExterno.getCentro();
		circuloInterno.setCentro(new Ponto4D(centroExterno.obterX(), centroExterno.obterY()));
		
		glDrawable.display();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Ponto4D centroInterno = circuloInterno.getCentro();
		
		int movtoX = e.getX() - antigoX;
	    int movtoY = e.getY() - antigoY;
	    
	    double novoX = centroInterno.obterX() + movtoX;
	    double novoY = centroInterno.obterY() - movtoY;
	    Ponto4D novoCentroInterno = new Ponto4D(novoX, novoY);
	    
	    double distancia = circuloExterno.getCentro().calcularDistancia(novoCentroInterno);
	    
	    if (distancia < 22500) {
	    	circuloInterno.setCentro(novoCentroInterno);
	    
	    	antigoX = e.getX();
			antigoY = e.getY();
	    }

		glDrawable.display();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}
}
