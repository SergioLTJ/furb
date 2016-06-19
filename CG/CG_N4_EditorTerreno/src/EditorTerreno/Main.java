package EditorTerreno;

/// \file Exemplo_N2_Jogl_Eclipse.java
/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.TextureData;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener {
    private static final float LIGHT_POSITION[] = { 0.0f, 100.0f, 500.0f, 1.0f };
    private static final String TEXTURE_FILES[] = { "01.png" , "02.png" , "03.png" , "04.png" , "05.png" };
    
    public static Textura texturas[] = null;
    
        public static boolean iluminar = false;
    
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
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                
                initLightning();
                initTexture();
                
                gl.glEnable(GL.GL_CULL_FACE);
                gl.glEnable(GL.GL_DEPTH_TEST);
                
                terreno = new Terreno(50, 50);
                camera = new Camera();
	}
	
        private void initLightning() {
            if (iluminar) {
                
                gl.glShadeModel(GL.GL_SMOOTH);
            
                gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, LIGHT_POSITION, 0);
                gl.glEnable(GL.GL_LIGHT0);
            
                gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
            }
        }
        
        private void initTexture() {
            gl.glTexParameteri(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_MIN_FILTER,GL.GL_LINEAR);	
	    gl.glTexParameteri(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
            
            //gl.glTexParameteri(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_S ,GL.GL_REPEAT);
            //gl.glTexParameteri(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_WRAP_T ,GL.GL_REPEAT);
            
            int textureCount = TEXTURE_FILES.length;
            int ids[] = new int[textureCount + 1];
            
            texturas = new Textura[textureCount];
            
            gl.glGenTextures(0, ids, textureCount);
            
            for (int i = 0; i < textureCount; ++i)
            {
                // Tenta carregar o arquivo		
                BufferedImage image = null;
                try {
                    image = ImageIO.read(new File("src\\Images\\" + TEXTURE_FILES[i]));
                }
                catch (IOException e) {
                    System.out.println("Erro na leitura do arquivo");
                }
            
                texturas[i] = new Textura();
            
                texturas[i].ID = ids[i];
                // Obtem largura e altura
                texturas[i].width  = image.getWidth();
                texturas[i].height = image.getHeight();
                // Gera uma nova TextureData...
                TextureData td = new TextureData(0,0,false,image);
                // ...e obtem um ByteBuffer a partir dela
                texturas[i].byteBuffer = (ByteBuffer) td.getBuffer();
            }
        }
        
	//exibicaoPrincipal
        @Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
                
                gl.glLightf(GL.GL_LIGHT0, GL.GL_SPOT_EXPONENT, 0);
                
                camera.perspective(glu);
                camera.lookAt(glu);
                
                initLightning();
                
		SRU();
		
                // -----
                
                terreno.display(gl);
                terreno.displayText(gl, glut);
                
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
                        terreno.aplicaPincelPontoSelecionado(false);
                        break;
                    case KeyEvent.VK_DOWN:
                        terreno.aplicaPincelPontoSelecionado(true);
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                        terreno.aplicaPincelNivelamentoPontoSelecionado();
                        break;
                        
                    // SPACE: Criar edificio
                    case KeyEvent.VK_SPACE:
                        terreno.criaEdificioPontoSelecionado();
                        break;
                        
                    // </>: Rotacao edificio 
                    case KeyEvent.VK_COMMA:
                        terreno.rotacionaEdificioPontoSelecionado(15.0);
                        break;
                    case KeyEvent.VK_PERIOD:
                        terreno.rotacionaEdificioPontoSelecionado(-15.0);
                        break;
                        
                    // Z: Altera pincel
                    case KeyEvent.VK_Z:
                        terreno.proximoPincel();
                        break;
                        
                    case KeyEvent.VK_L:
                        iluminar = !iluminar;
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
