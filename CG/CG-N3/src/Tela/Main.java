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

        // DEBUG
        public static final boolean modoDebug = true;
    
	public static void trace(String str) {
            if (modoDebug)
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
	private Mundo mundo;
	private boolean modoConstrucao;

        Ponto4D cursor;
        
        @Override
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		trace("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

                cursor = new Ponto4D();
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

                gl.glColor3d(0, 0, 0);
                gl.glPointSize(5);
                gl.glBegin(GL.GL_POINTS);
                gl.glVertex2d(cursor.getX(), cursor.getY());
                gl.glEnd();
                
		mundo.display(gl);

		gl.glFlush();
	}

	@Override
	public void keyPressed(KeyEvent e) {
            /* CONTROLES
            WASD: Translação da viewport
            +-: Zoom da viewport
            Setas: Translação do objeto
            */
            
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
			break;
                        
                // Translacao camera        
		case KeyEvent.VK_D:
			limiteEsquerda -= locomocao;
			limiteDireita -= locomocao;
			break;
		case KeyEvent.VK_A:
			limiteEsquerda += locomocao;
			limiteDireita += locomocao;
			break;
		case KeyEvent.VK_W:
			limiteCima -= locomocao;
			limiteBaixo -= locomocao;
			break;
		case KeyEvent.VK_S:
			limiteCima += locomocao;
			limiteBaixo += locomocao;
			break;
                        
                // Translacao objeto
                case KeyEvent.VK_RIGHT:
                    if (!modoConstrucao)
                        mundo.moveObjeto(10, 0, 0);
                    break;
                case KeyEvent.VK_LEFT:
                    if (!modoConstrucao)
                        mundo.moveObjeto(-10, 0, 0);
                    break;
                case KeyEvent.VK_UP:
                    if (!modoConstrucao)
                        mundo.moveObjeto(0, 10, 0);
                    break;
                case KeyEvent.VK_DOWN:
                    if (!modoConstrucao)
                        mundo.moveObjeto(0, -10, 0);
                    break;
                    
                // Escala objeto
                case KeyEvent.VK_PAGE_UP: 
                    if (!modoConstrucao)
                        mundo.escalaObjeto(1.5);
                    break;
                case KeyEvent.VK_PAGE_DOWN: 
                    if (!modoConstrucao)
                        mundo.escalaObjeto(0.5);
                    break;
                    
                // Rotação objeto
                case KeyEvent.VK_COMMA: 
                    if (!modoConstrucao)
                        mundo.rotacaoObjeto(15);
                    break;
                case KeyEvent.VK_PERIOD: 
                    if (!modoConstrucao)
                        mundo.rotacaoObjeto(-15);
                    break;
                    
                // Outras funções objeto
                case KeyEvent.VK_C:
                    if (mundo.possuiSelecao())
                        mundo.getSelecao().proximaCor();
                    break;
		
                case KeyEvent.VK_DELETE:
                    if (mundo.possuiSelecao())
                        mundo.deletaSelecao();
                    break;
		}
                
                glDrawable.display();
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
		
                boolean rightClick = e.getButton() == MouseEvent.BUTTON3;
                
		if (modoConstrucao) {
                    if (rightClick) {
                        if (mundo.regressaConstrucaoObjeto())
                            modoConstrucao = false;
                    } else {
			ObjetoGrafico constr = mundo.getConstrucao();
			int index = constr.indexPonto(ponto);

			// Valida clique em ponto existente
			if (index >= 0) {
				// Valida fechamento
				mundo.finalizaConstrucaoObjeto(index);
				modoConstrucao = false;
			} else {
				// Adiciona vertice
				mundo.avancaConstrucaoObjeto(ponto);
			}
                    }
		} else {
                    if (rightClick) {
                        mundo.removeSelecao();
                    } else
                    {
			// Valida selecao de poligonos existentes
                        if (!mundo.selecionaObjeto(ponto)) {
                            // Cria novo poligono
                            mundo.iniciaObjeto(ponto);
                            modoConstrucao = true;
                        }
                    }
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
            if (cursor != null) {
                cursor.setX(e.getX() + 200);
                cursor.setY((-e.getY()) + 200);
            }
                
            if (modoConstrucao)
                mundo.atualizaConstrucaoObjeto(cursor);
                
            if (glDrawable != null)
                glDrawable.display();
	}
        
}
