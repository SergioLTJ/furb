/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorTerreno;

import java.awt.Color;
import java.util.Random;
import javax.media.opengl.GL;

/**
 *
 * @author Avell
 */
public class Edificio {
    
    private static Color COR_EDIFICIO = new Color(50,50,50);
    
    public Edificio(Ponto4D pontoBase) {
        this.pontoBase = pontoBase;
        
        Random rnd = new Random();
        altura = 10 + (rnd.nextDouble() * 90);
        largura = 5 + (rnd.nextDouble() * 5);
        
        atualizaPontosDesenho();
    }
    
    public void display(GL gl) {
        float cor[] = new float[3];
        COR_EDIFICIO.getColorComponents(cor);
        
        //gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, cor, 0);
        
        gl.glEnable(GL.GL_COLOR_MATERIAL);
	gl.glColor3f(cor[0],cor[1],cor[2]);
        
        desenhaFaces(gl);
        
        gl.glEnable(GL.GL_LIGHTING);
        
    }
    
    public void atualizaPontosDesenho() {
        pontosDesenho = new Ponto4D[8];
        
        double desvioBase = largura / 2;
        
        for (int i = 0; i < pontosDesenho.length; ++i)
            pontosDesenho[i] = new Ponto4D(pontoBase);
        
        /*
        4---7
        |\  |\
        | 5-+-6
        0-+-3 |
         \|  \|
          1---2
        */

        pontosDesenho[0].translacaoPonto(-desvioBase, 0, -desvioBase);
        pontosDesenho[1].translacaoPonto(-desvioBase, 0,  desvioBase);
        pontosDesenho[2].translacaoPonto( desvioBase, 0,  desvioBase);
        pontosDesenho[3].translacaoPonto( desvioBase, 0, -desvioBase);
        
        pontosDesenho[4].translacaoPonto(-desvioBase, altura, -desvioBase);
        pontosDesenho[5].translacaoPonto(-desvioBase, altura,  desvioBase);
        pontosDesenho[6].translacaoPonto( desvioBase, altura,  desvioBase);
        pontosDesenho[7].translacaoPonto( desvioBase, altura, -desvioBase);
    }
    
    private void desenhaFaces(GL gl) {
        Color cor = COR_EDIFICIO;
        gl.glColor3ub((byte)cor.getRed(), (byte)cor.getGreen(), (byte)cor.getBlue());
        
        // Fundo: 0-1-2-3
        gl.glBegin(GL.GL_POLYGON);
        
        gl.glVertex3d(pontosDesenho[0].getX(), pontosDesenho[0].getY(), pontosDesenho[0].getZ());
        gl.glVertex3d(pontosDesenho[1].getX(), pontosDesenho[1].getY(), pontosDesenho[1].getZ());
        gl.glVertex3d(pontosDesenho[2].getX(), pontosDesenho[2].getY(), pontosDesenho[2].getZ());
        gl.glVertex3d(pontosDesenho[3].getX(), pontosDesenho[3].getY(), pontosDesenho[3].getZ());
        
        gl.glEnd();
        
        // Lado 12h: 0-3-7-4
        gl.glBegin(GL.GL_POLYGON);
        
        gl.glVertex3d(pontosDesenho[0].getX(), pontosDesenho[0].getY(), pontosDesenho[0].getZ());
        gl.glVertex3d(pontosDesenho[3].getX(), pontosDesenho[3].getY(), pontosDesenho[3].getZ());
        gl.glVertex3d(pontosDesenho[7].getX(), pontosDesenho[7].getY(), pontosDesenho[7].getZ());
        gl.glVertex3d(pontosDesenho[4].getX(), pontosDesenho[4].getY(), pontosDesenho[4].getZ());
        
        gl.glEnd();
        
        // Lado 3h: 3-2-6-7
        gl.glBegin(GL.GL_POLYGON);
        
        gl.glVertex3d(pontosDesenho[3].getX(), pontosDesenho[3].getY(), pontosDesenho[3].getZ());
        gl.glVertex3d(pontosDesenho[2].getX(), pontosDesenho[2].getY(), pontosDesenho[2].getZ());
        gl.glVertex3d(pontosDesenho[6].getX(), pontosDesenho[6].getY(), pontosDesenho[6].getZ());
        gl.glVertex3d(pontosDesenho[7].getX(), pontosDesenho[7].getY(), pontosDesenho[7].getZ());
        
        gl.glEnd();
        
        // Lado 6h: 1-5-6-2
        gl.glBegin(GL.GL_POLYGON);
        
        gl.glVertex3d(pontosDesenho[1].getX(), pontosDesenho[1].getY(), pontosDesenho[1].getZ());
        gl.glVertex3d(pontosDesenho[5].getX(), pontosDesenho[5].getY(), pontosDesenho[5].getZ());
        gl.glVertex3d(pontosDesenho[6].getX(), pontosDesenho[6].getY(), pontosDesenho[6].getZ());
        gl.glVertex3d(pontosDesenho[2].getX(), pontosDesenho[2].getY(), pontosDesenho[2].getZ());
        
        gl.glEnd();
        
        // Lado 9h: 0-4-5-1
        gl.glBegin(GL.GL_POLYGON);
        
        gl.glVertex3d(pontosDesenho[0].getX(), pontosDesenho[0].getY(), pontosDesenho[0].getZ());
        gl.glVertex3d(pontosDesenho[4].getX(), pontosDesenho[4].getY(), pontosDesenho[4].getZ());
        gl.glVertex3d(pontosDesenho[5].getX(), pontosDesenho[5].getY(), pontosDesenho[5].getZ());
        gl.glVertex3d(pontosDesenho[1].getX(), pontosDesenho[1].getY(), pontosDesenho[1].getZ());
        
        gl.glEnd();
        
        // Topo 6h: 4-5-6-7
        gl.glBegin(GL.GL_POLYGON);
        
        gl.glVertex3d(pontosDesenho[4].getX(), pontosDesenho[4].getY(), pontosDesenho[4].getZ());
        gl.glVertex3d(pontosDesenho[5].getX(), pontosDesenho[5].getY(), pontosDesenho[5].getZ());
        gl.glVertex3d(pontosDesenho[6].getX(), pontosDesenho[6].getY(), pontosDesenho[6].getZ());
        gl.glVertex3d(pontosDesenho[7].getX(), pontosDesenho[7].getY(), pontosDesenho[7].getZ());
        
        gl.glEnd();
    }
    
    private Ponto4D pontoBase;
    private double altura;
    private double largura;
    
    private Ponto4D pontosDesenho[];
}
