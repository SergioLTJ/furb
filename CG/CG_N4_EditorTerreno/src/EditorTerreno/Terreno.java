/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorTerreno;

import java.awt.Color;
import javax.media.opengl.GL;

/**
 * Representa a malha de pontos que forma o relevo do terreno.
 * Os pontos inicialmente formam um plano representado pelos eixos X e Z e centralizado na origem.
 * Cada ponto da malha pode ter sua elevacao (eixo Y) alterada, criando diferencas de altura que formam o relevo.
 * @author Avell
 */
public class Terreno {
    
    private static final double TAMANHO_CELULA = 100.0;
    
    /**
     * Cria um terreno com malha de tamanho comprimento * largura e elevacao 0 em todos os pontos
     * @param comprimento Comprimento da malha, analogo ao eixo X
     * @param largura Largura da malha, analoga ao eixo Z
     */
    public Terreno(int comprimento, int largura) {
        this.comprimento = comprimento;
        this.largura = largura;
        
        malhaTerreno = new Ponto4D[this.comprimento][this.largura];

        criaMalhaTerreno();
    }
    
    public void display(GL gl) {
        // TODO: Desenhar malha
        
        
        gl.glColor3f(Color.GREEN.getRed(), Color.GREEN.getGreen(), Color.GREEN.getBlue());
        gl.glLineWidth(2);
        
        for (int x = 0; x < comprimento - 1; ++x) {
            for (int z = 0; z < largura - 1; ++z) {
                desenhaCelula(gl, x, z);
            }
        }
    }
    
    public void aplicaAlteracao(int x, int z) {
        // TODO: Aplica um pincel sobre a area
    }
    
    
    public double getElevacaoPonto(int x, int z) {
        return malhaTerreno[x][z].getY();
    }
    
    
    /**
     * Inicializa a malha do terreno.
     * Os pontos sao inicializados a partir das coordenadas de X e Z mais baixas, incrementando-se o tamanho da celula ate completar a malha.
     */
    private void criaMalhaTerreno() {
        // Coordenadas de desenho X e Z do canto inicial
        double contX = -((comprimento * TAMANHO_CELULA) / 2);
        double contZ = -((largura * TAMANHO_CELULA) / 2);
        
        // Loop de inicializacao, incrementando o tamanho da célula para cada ponto subsequente
        for (int x = 0; x < comprimento; ++x) {
            double tempZ = contZ;
            for (int z = 0; z < largura; ++z) {
                malhaTerreno[x][z] = new Ponto4D(contX, 0, tempZ, 1);
                tempZ += TAMANHO_CELULA;
            }
            contX += TAMANHO_CELULA;
        }
    }
    
    private void desenhaCelula(GL gl, int x, int z) {
        gl.glBegin(GL.GL_LINE_LOOP);
        
        Ponto4D ponto = malhaTerreno[x][z];
        gl.glVertex3d(ponto.getX(), ponto.getY(), ponto.getZ());
        
        ponto = malhaTerreno[x+1][z];
        gl.glVertex3d(ponto.getX(), ponto.getY(), ponto.getZ());
        
        ponto = malhaTerreno[x+1][z+1];
        gl.glVertex3d(ponto.getX(), ponto.getY(), ponto.getZ());
        
        ponto = malhaTerreno[x][z+1];
        gl.glVertex3d(ponto.getX(), ponto.getY(), ponto.getZ());
        
        gl.glEnd();
    }
    
    
    private final int comprimento;
    private final int largura;
    private final Ponto4D malhaTerreno[][];
}
