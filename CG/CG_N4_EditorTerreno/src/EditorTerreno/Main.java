package EditorTerreno;

/// \file Exemplo_N2_Jogl_Eclipse.java
/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

import com.sun.opengl.util.GLUT;
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
        private GLUT glut;
	private GLAutoDrawable glDrawable;
	private Ponto4D pto1 = new Ponto4D(  0.0,   0.0, 0.0, 1.0);
	private Ponto4D pto2 = new Ponto4D(200.0, 200.0, 0.0, 1.0);

        private Camera camera;
        private Terreno terreno;
        
        @Override
	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
                glut = new GLUT();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
                
                // -----
                
                initLightning();
                
                gl.glEnable(GL.GL_CULL_FACE);
                gl.glEnable(GL.GL_DEPTH_TEST);
                
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
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
                
                camera.perspective(glu);
                camera.lookAt(glu);
                
                //gl.glLightf(GL.GL_LIGHT0, GL.GL_SPOT_EXPONENT, 0);
                
		SRU();
		
                // -----
                
                gl.glColor3f(0, 0, 0);
                gl.glTranslated(5, 50, 10);
                glut.glutSolidCube(10.0f);
                
                terreno.display(gl);
                
                // -----
                
                gl.glFlush();
	}	

        @Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
                    // WASD: Translacao camera
                    case KeyEvent.VK_W:
                        camera.pan(new Ponto4D(0,0,-10,1));
                        break;
                    case KeyEvent.VK_A:
                        camera.pan(new Ponto4D(-10,0,0,1));
                        break;
                    case KeyEvent.VK_S:
                        camera.pan(new Ponto4D(0,0,10,1));
                        break;
                    case KeyEvent.VK_D:
                        camera.pan(new Ponto4D(10,0,0,1));
                        break;    
                        
                    // Q/E: Rotacao camera  
                    case KeyEvent.VK_Q:
                        camera.rotate(-10.0);
                        break;
                    case KeyEvent.VK_E:
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
                        
                    // UP/DOWN/LEFT/RIGHT: Aplicar pincel
                    case KeyEvent.VK_UP:
                        terreno.aplicaPincelPontoSelecionado(Pincel.PINCEL_QUADRADO, false);
                        break;
                    case KeyEvent.VK_DOWN:
                        terreno.aplicaPincelPontoSelecionado(Pincel.PINCEL_QUADRADO, true);
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                        terreno.aplicaPincelNivelamentoPontoSelecionado(Pincel.PINCEL_QUADRADO);
                        break;
                        
                    // SPACE: Criar edificio
                    case KeyEvent.VK_SPACE:
                        terreno.criaEdificioPontoSelecionado();
                        break;
		}
                
                terreno.selecionaPontoProximo(camera.getPontoFoco());
                glDrawable.display();
	}

        @Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
            gl.glViewport(0, 0, width, height);
	}

        @Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		
	}

        @Override
	public void keyReleased(KeyEvent arg0) {
		
	}

        @Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	public void SRU() {
                gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glLineWidth(1.0f);
		gl.glBegin( GL.GL_LINES );
		gl.glVertex3f( -200.0f, 0.0f , 0.0f );
		gl.glVertex3f(  200.0f, 0.0f , 0.0f );
		gl.glEnd();
		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin( GL.GL_LINES);
		gl.glVertex3f(  0.0f, -200.0f, 0.0f );
		gl.glVertex3f(  0.0f, 200.0f , 0.0f );
                gl.glEnd();
                gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin( GL.GL_LINES);
		gl.glVertex3f(  0.0f, 0.0f , -200.0f);
		gl.glVertex3f(  0.0f, 0.0f , 200.0f );
                gl.glEnd();
	}

}
