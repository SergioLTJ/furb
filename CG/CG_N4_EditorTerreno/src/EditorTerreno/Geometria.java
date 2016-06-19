/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorTerreno;

import javax.media.opengl.GL;

/**
 *
 * @author Avell
 */
public class Geometria {
    public static double rotacaoX(double angulo, double raio) {
	return (raio * Math.cos(Math.PI * angulo / 180.0));
    }

    public static double rotacaoZ(double angulo, double raio) {
        return (raio * Math.sin(Math.PI * angulo / 180.0));
    }
    
    public static double distancia2D(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
    
    public static Ponto4D calculaNormal(Ponto4D p1, Ponto4D p2, Ponto4D p3) {
        Ponto4D pA = new Ponto4D((p2.getX() - p1.getX()), (p2.getY() - p1.getY()), (p2.getZ() - p1.getZ()), 1);
        Ponto4D pB = new Ponto4D((p3.getX() - p1.getX()), (p3.getY() - p1.getY()), (p3.getZ() - p1.getZ()), 1);
        
        Ponto4D normal = new Ponto4D((pA.getY() * pB.getZ()) - (pA.getZ() * pB.getY()), 
                                     (pA.getZ() * pB.getX()) - (pA.getX() * pB.getZ()),
                                     (pA.getX() * pB.getY()) - (pA.getY() * pB.getX()), 1);
        
        return normal;
    }
    
    public static void desenhaCubo(GL gl) {
        gl.glPushMatrix();
		gl.glTranslatef(-30.0f, 0.0f, 0.0f);
		gl.glScalef(16.0f, 16.0f, 16.0f);
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glBegin (GL.GL_QUADS );
			// Especifica a coordenada de textura para cada vertice
			// Face frontal
			gl.glNormal3f(0.0f,0.0f,-1.0f);
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);				
			// Face posterior
			gl.glNormal3f(0.0f,0.0f,1.0f);
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
			// Face superior
			gl.glNormal3f(0.0f,1.0f,0.0f);
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
			// Face inferior
			gl.glNormal3f(0.0f,-1.0f,0.0f);
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
			// Face lateral direita
			gl.glNormal3f(1.0f,0.0f,0.0f);
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
			// Face lateral esquerda
			gl.glNormal3f(-1.0f,0.0f,0.0f);
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
		gl.glEnd();
	gl.glPopMatrix();
    }
}
