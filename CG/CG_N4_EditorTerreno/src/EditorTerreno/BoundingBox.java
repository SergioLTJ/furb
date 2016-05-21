/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorTerreno;

import java.awt.Color;
import javax.media.opengl.GL;

/**
 *
 * @author Avell
 */
public class BoundingBox {

    private double menorX;
	private double menorY;
	private double menorZ;
	private double maiorX;
	private double maiorY;
	private double maiorZ;
	private Color cor;
	private Ponto4D centro = new Ponto4D();
	private int larguraLinha;


	public BoundingBox() {
		this(0, 0, 0, 0, 0, 0);
        processarCentroBBox();
                
		this.cor = Color.MAGENTA;
	}
	
	public BoundingBox(double smallerX, double smallerY, double smallerZ, double greaterX, double greaterY, double greaterZ) {
		this.menorX = smallerX;
		this.menorY = smallerY;
		this.menorZ = smallerZ;
		this.maiorX = greaterX;
		this.maiorY = greaterY;
		this.maiorZ = greaterZ;
        processarCentroBBox();
                
		this.cor = Color.MAGENTA;
	}
	
    public void atribuirBoundingBox(double smallerX, double smallerY, double smallerZ, double greaterX, double greaterY, double greaterZ) {
		this.menorX = smallerX;
		this.menorY = smallerY;
		this.menorZ = smallerZ;
		this.maiorX = greaterX;
		this.maiorY = greaterY;
		this.maiorZ = greaterZ;
		processarCentroBBox();
        }
		
	public void atualizarBBox(Ponto4D point) {
	    atualizarBBox(point.getX(), point.getY(), point.getZ());
	}

    public void atualizarBBox(double x, double y, double z) {
	    if (x < menorX)
	        menorX = x;
	    else {
	        if (x > maiorX) maiorX = x;
	    }
	    if (y < menorY)
	        menorY = y;
	    else {
	        if (y > maiorY) maiorY = y;
	    }
	    if (z < menorZ)
	        menorZ = z;
	    else {
	        if (z > maiorZ) maiorZ = z;
	    }
	}
	
    /**
     * Calcula o centro da BBox
     */
    public void processarCentroBBox() {
	    centro.setX((maiorX + menorX)/2);
	    centro.setY((maiorY + menorY)/2);
	    centro.setZ((maiorZ + menorZ)/2);
	}

	public void desenharOpenGLBBox(GL gl) {
		gl.glColor3f(cor.getRed(), cor.getGreen(), cor.getBlue());
		gl.glLineWidth(this.larguraLinha);
		
		gl.glBegin (GL.GL_LINE_LOOP);
		gl.glVertex3d (menorX, maiorY, menorZ);
		gl.glVertex3d (maiorX, maiorY, menorZ);
		gl.glVertex3d (maiorX, menorY, menorZ);
		gl.glVertex3d (menorX, menorY, menorZ);
	    gl.glEnd();
		
	    gl.glBegin(GL.GL_LINE_LOOP);
	    gl.glVertex3d (menorX, menorY, menorZ);
	    gl.glVertex3d (menorX, menorY, maiorZ);
	    gl.glVertex3d (menorX, maiorY, maiorZ);
	    gl.glVertex3d (menorX, maiorY, menorZ);
	    gl.glEnd();
		
	    gl.glBegin(GL.GL_LINE_LOOP);
	    gl.glVertex3d (maiorX, maiorY, maiorZ);
	    gl.glVertex3d (menorX, maiorY, maiorZ);
	    gl.glVertex3d (menorX, menorY, maiorZ);
	    gl.glVertex3d (maiorX, menorY, maiorZ);
	    gl.glEnd();
		
	    gl.glBegin(GL.GL_LINE_LOOP);
	    gl.glVertex3d (maiorX, menorY, menorZ);
	    gl.glVertex3d (maiorX, maiorY, menorZ);
	    gl.glVertex3d (maiorX, maiorY, maiorZ);
	    gl.glVertex3d (maiorX, menorY, maiorZ);
    	gl.glEnd();
	}

	/// Obter menor valor X da BBox.
	public double obterMenorX() {
		return menorX;
	}

	/// Obter menor valor Y da BBox.
	public double obterMenorY() {
		return menorY;
	}

	/// Obter menor valor Z da BBox.
	public double obterMenorZ() {
		return menorZ;
	}

	/// Obter maior valor X da BBox.
	public double obterMaiorX() {
		return maiorX;
	}

	/// Obter maior valor Y da BBox.
	public double obterMaiorY() {
		return maiorY;
	}

	/// Obter maior valor Z da BBox.
	public double obterMaiorZ() {
		return maiorZ;
	}
	
	/// Obter ponto do centro da BBox.
	public Ponto4D obterCentro() {
		return centro;
	}

	public void setCor(Color cor) {
		this.cor = cor;	
	}
	
	public void setLarguraLinha(int largura) {
		this.larguraLinha = largura;
	}
        
    /**
     * Testa se o Ponto4D esta dentro do BBox
     * @param ponto Ponto de selecao
     * @return true caso esteja dentro do BBox
     */
    public boolean calcula(Ponto4D ponto) {
        if (ponto.getX() < obterMenorX()) return false;
        if (ponto.getX() > obterMaiorX()) return false;
        if (ponto.getY() < obterMenorY()) return false;
        if (ponto.getY() > obterMaiorY()) return false;

        return true;
    }
        
    /**
     * Desenhar o BoundingBox
     * @param gl Objeto de desenho
     */
    public void display(GL gl) {
        gl.glColor3f(cor.getRed(), cor.getGreen(), cor.getBlue());
        gl.glLineWidth(1);
        gl.glBegin(GL.GL_LINE_LOOP);
        
        gl.glVertex2d(menorX, menorY);
        gl.glVertex2d(menorX, maiorY);
        gl.glVertex2d(maiorX, maiorY);
        gl.glVertex2d(maiorX, menorY);
        
        gl.glEnd();
    }
}

