package EditorTerreno;

/// \file Exemplo_N2_Jogl_Eclipse.java
/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

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
	private Ponto4D pto1 = new Ponto4D(  0.0,   0.0, 0.0, 1.0);
	private Ponto4D pto2 = new Ponto4D(200.0, 200.0, 0.0, 1.0);

        private Camera camera;
        private Terreno terreno;
        
        @Override
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
                
                // -----
                
                //initLightning();
                
                terreno = new Terreno(50, 50);
                camera = new Camera();
	}
	
        private void initLightning() {
            float posLight[] = { 5.0f, 50.0f, 10.0f, 0.0f };
	    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posLight, 0);
	    gl.glEnable(GL.GL_LIGHT0);
            
            gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
        }
        
	//exibicaoPrincipal
        @Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
                
                camera.perspective(glu);
                camera.lookAt(glu);
                
                gl.glLightf(GL.GL_LIGHT0, GL.GL_SPOT_EXPONENT, 0);
                
		SRU();
		
                // -----
                
                terreno.display(gl);
                
                // -----
                
                gl.glFlush();
	}	

        @Override
	public void keyPressed(KeyEvent e) {
		System.out.println(" --- keyPressed ---");
		switch (e.getKeyCode()) {
                    // WASD: Translacao camera
                    case KeyEvent.VK_W:
                        camera.pan(new Ponto4D(0,0,10,1));
                        break;
                    case KeyEvent.VK_A:
                        camera.pan(new Ponto4D(10,0,0,1));
                        break;
                    case KeyEvent.VK_S:
                        camera.pan(new Ponto4D(0,0,-10,1));
                        break;
                    case KeyEvent.VK_D:
                        camera.pan(new Ponto4D(-10,0,0,1));
                        break;    
                        
                    // Setas direita/esquerda: Rotacao camera  
                    case KeyEvent.VK_LEFT:
                        camera.rotate(-10.0);
                        break;
                    case KeyEvent.VK_RIGHT:
                        camera.rotate(10.0);
                        break;
                        
                    // G: Exibir grid/terreno
                    case KeyEvent.VK_G:
                        terreno.toggleExibirGrid();
                        break;
                        
                    // +/-: Alterar elevacao do terreno    
                    case KeyEvent.VK_EQUALS:
                        terreno.alteraElevacaoPontoSelecionado(5.0);
                        break;
                    case KeyEvent.VK_MINUS:
                        terreno.alteraElevacaoPontoSelecionado(-5.0);
                        break;
                        
                    // SPACE: Aplicar pincel
                    case KeyEvent.VK_SPACE:
                        terreno.aplicaPincelPontoSelecionado(Pincel.PINCEL_QUADRADO);
                        break;
		}
                
                terreno.selecionaPontoProximo(camera.getPontoFoco());
                glDrawable.display();
	}

        @Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		System.out.println(" --- reshape ---");
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
	}

        @Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		System.out.println(" --- displayChanged ---");
	}

        @Override
	public void keyReleased(KeyEvent arg0) {
		System.out.println(" --- keyReleased ---");
	}

        @Override
	public void keyTyped(KeyEvent arg0) {
		System.out.println(" --- keyTyped ---");
	}
	
	public void SRU() {
                gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glLineWidth(1.0f);
		gl.glBegin( GL.GL_LINES );
		gl.glVertex2f( -200.0f, 0.0f );
		gl.glVertex2f(  200.0f, 0.0f );
		gl.glEnd();
		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin( GL.GL_LINES);
		gl.glVertex2f(  0.0f, -200.0f);
		gl.glVertex2f(  0.0f, 200.0f );
                gl.glEnd();
	}

}
