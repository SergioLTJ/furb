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
    
    private static final double TAMANHO_CELULA = 10.0;
    
    private static final Color COR_TERRENO = new Color(97, 133, 20);
    private static final Color COR_SELECAO = new Color(255, 0, 0);
    
    private static boolean exibirGrid;
    
    /**
     * Cria um terreno com malha de tamanho comprimento * largura e elevacao 0 em todos os pontos
     * @param comprimento Comprimento da malha, analogo ao eixo X
     * @param largura Largura da malha, analoga ao eixo Z
     */
    public Terreno(int comprimento, int largura) {
        this.comprimento = comprimento;
        this.largura = largura;
        
        pontoSelecionado = null;
        
        malhaTerreno = new Ponto4D[this.comprimento][this.largura];

        exibirGrid = false;
        
        criaMalhaTerreno();
    }
    
    public void display(GL gl) {
        gl.glColor3ub((byte)COR_TERRENO.getRed(), (byte)COR_TERRENO.getGreen(), (byte)COR_TERRENO.getBlue());
        gl.glLineWidth(2);
        
        for (int x = 0; x < comprimento - 1; ++x) {
            for (int z = 0; z < largura - 1; ++z) {
                desenhaCelula(gl, x, z);
            }
        }
        
        if (pontoSelecionado != null) {
            gl.glColor3ub((byte)COR_SELECAO.getRed(), (byte)COR_SELECAO.getGreen(), (byte)COR_SELECAO.getBlue());
            gl.glPointSize(5.0f);
            
            gl.glBegin(GL.GL_POINTS);
            gl.glVertex3d(pontoSelecionado.getX(), pontoSelecionado.getY(), pontoSelecionado.getZ());
            gl.glEnd();
        }
    }
    
    public void aplicaAlteracao(int x, int z) {
        // TODO: Aplica um pincel sobre a area
    }
    
    
    public double getElevacaoPonto(int x, int z) {
        return malhaTerreno[x][z].getY();
    }
    
    public void toggleExibirGrid() {
        exibirGrid = !exibirGrid;
    }
    
    
    public boolean selecionaPontoProximo(Ponto4D ponto) {
        Ponto4D pontoDeslocado = new Ponto4D(ponto);
        
        pontoDeslocado.translacaoPonto((TAMANHO_CELULA * comprimento / 2), 0.0, (TAMANHO_CELULA * largura / 2));
        
        int hitX = (int)(Math.round(pontoDeslocado.getX()) / Math.round(TAMANHO_CELULA));
        int hitZ = (int)(Math.round(pontoDeslocado.getZ()) / Math.round(TAMANHO_CELULA));
        
        if (hitX >= 0 && hitX < comprimento && hitZ >= 0 && hitZ < largura) {
            pontoSelecionado = malhaTerreno[hitX][hitZ];
            return true;
        }
        
        return false;
    }
    
    public void alteraElevacaoPontoSelecionado(double alteracao) {
        if (pontoSelecionado != null) {
            pontoSelecionado.translacaoPonto(0.0, alteracao, 0.0);
        }
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
        gl.glBegin(exibirGrid ? GL.GL_LINE_STRIP : GL.GL_POLYGON);
        
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
    
    
    private Ponto4D pontoSelecionado;
    
    private final int comprimento;
    private final int largura;
    private final Ponto4D malhaTerreno[][];
}
