/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorTerreno;

import com.sun.opengl.util.GLUT;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import javax.media.opengl.GL;

/**
 * Representa a malha de pontos que forma o relevo do terreno.
 * Os pontos inicialmente formam um plano representado pelos eixos X e Z e centralizado na origem.
 * Cada ponto da malha pode ter sua elevacao (eixo Y) alterada, criando diferencas de altura que formam o relevo.
 * @author Avell
 */
public class Terreno {
    
    private static final double TAMANHO_CELULA = 10.0;
    private static final double ALTURA_MINIMA = 0.0;
    private static final double ALTURA_MAXIMA = 200.0;
    
    private static final Color COR_TERRENO_BAIXO = new Color(97, 133, 20);
    private static final Color COR_TERRENO_ALTO = new Color(18, 94, 55);

    private static final Color COR_SELECAO = new Color(255, 0, 0);
    
    private static boolean exibirGrid;
    
    private static final Pincel PINCEIS[] = {Pincel.PINCEL_DIAMANTE, Pincel.PINCEL_QUADRADO, Pincel.PINCEL_XIS, Pincel.PINCEL_CRATERA };
    private static final String PINCEIS_NOMES[] = {"DIAMANTE", "QUADRADO", "XIS", "CRATERA" };
            
    /**
     * Cria um terreno com malha de tamanho comprimento * largura e elevacao 0 em todos os pontos
     * @param comprimento Comprimento da malha, analogo ao eixo X
     * @param largura Largura da malha, analoga ao eixo Z
     */
    public Terreno(int comprimento, int largura) {
        this.comprimento = comprimento;
        this.largura = largura;
        
        pontoSelecionado = null;
        selecaoX = -1;
        selecaoZ = -1;
        
        altText = "";
        
        malhaTerreno = new Ponto4D[this.comprimento][this.largura];
        edificios = new ArrayList<>();
        mapaEdificios = new HashMap<>();
        
        pincelSelecionado = 0;
        
        exibirGrid = false;
        
        criaMalhaTerreno();
    }
    
    public void displayText(GL gl, GLUT glut) {
        if (pontoSelecionado == null)
            return;
        
        if (altText.isEmpty())
            altText = pontoSelecionado.getTextoPosicao();
        
        float cor[] = new float[3];
        COR_SELECAO.getColorComponents(cor);
        gl.glColor3f(cor[0],cor[1],cor[2]);
        
        gl.glRasterPos3d(pontoSelecionado.getX(), pontoSelecionado.getY() + 5, pontoSelecionado.getZ());
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, altText);
        
        altText = "";
    }
    
    public void display(GL gl) {
        float cor[] = new float[3];
        COR_TERRENO_BAIXO.getColorComponents(cor);
        
        //gl.glEnable(GL.GL_COLOR_MATERIAL);
	gl.glColor3f(cor[0],cor[1],cor[2]);
        
        //gl.glEnable(GL.GL_LIGHTING);
        
        gl.glLineWidth(2);
        
        for (int x = 0; x < comprimento - 1; ++x) {
            for (int z = 0; z < largura - 1; ++z) {
                desenhaCelula(gl, x, z);
            }
        }
        
        desenhaEdificios(gl);
        
        gl.glDisable(GL.GL_LIGHTING);

        if (pontoSelecionado != null) {
            gl.glColor3ub((byte)COR_SELECAO.getRed(), (byte)COR_SELECAO.getGreen(), (byte)COR_SELECAO.getBlue());
            gl.glPointSize(5.0f);
            
            gl.glBegin(GL.GL_POINTS);
            gl.glVertex3d(pontoSelecionado.getX(), pontoSelecionado.getY(), pontoSelecionado.getZ());
            gl.glEnd();
        }
    }
    
    public int getComprimento() {
        return comprimento;
    }
    
    public int getLargura() {
        return largura;
    }
    
    public Ponto4D getPonto(int x, int z) {
        return malhaTerreno[x][z];
    }
    
    public double getElevacao(int x, int z) {
        return malhaTerreno[x][z].getY();
    }
    
    public void alteraElevacao(int x, int z, double alteracao) {
        Ponto4D ponto = malhaTerreno[x][z];
        
        ponto.translacaoPonto(0.0, alteracao, 0.0);
        if (ponto.getY() < ALTURA_MINIMA) ponto.setY(ALTURA_MINIMA);
        if (ponto.getY() > ALTURA_MAXIMA) ponto.setY(ALTURA_MAXIMA);
    }
    
    public void nivelaElevacao(int x, int z, double alvo, double alteracao) {
        double elevacao = getElevacao(x, z);
        double variacao = alvo - elevacao;
        
        if (variacao > 0) {
            if (alteracao < 0)
                alteracao = -alteracao;
            
            if (alteracao < variacao)
                variacao = alteracao;
        } else {
            if (alteracao > 0)
                alteracao = -alteracao;
            
            if (alteracao > variacao)
                variacao = alteracao;
        }
        
        alteraElevacao(x, z, variacao);
    }
    
    public void setElevacao(int x, int z, double elevacao) {
        malhaTerreno[x][z].setY(elevacao);
    }
    
    public void toggleExibirGrid() {
        exibirGrid = !exibirGrid;
    }
    
    
    public void proximoPincel() {
        pincelSelecionado++;
        if (pincelSelecionado == PINCEIS.length)
            pincelSelecionado = 0;
        
        altText = PINCEIS_NOMES[pincelSelecionado];
    }
    
    public boolean selecionaPontoProximo(Ponto4D ponto) {
        limpaSelecao();
        Ponto4D pontoDeslocado = new Ponto4D(ponto);
        
        pontoDeslocado.translacaoPonto((TAMANHO_CELULA * comprimento / 2), 0.0, (TAMANHO_CELULA * largura / 2));
        
        int hitX = (int)(Math.round(pontoDeslocado.getX()) / Math.round(TAMANHO_CELULA));
        int hitZ = (int)(Math.round(pontoDeslocado.getZ()) / Math.round(TAMANHO_CELULA));
        
        if (hitX >= 0 && hitX < comprimento && hitZ >= 0 && hitZ < largura) {
            pontoSelecionado = malhaTerreno[hitX][hitZ];
            selecaoX = hitX;
            selecaoZ = hitZ;
            
            System.out.println("(" + pontoSelecionado.getX() + ", " + pontoSelecionado.getZ() + ")");
            
            setSelecaoEdificio(true);
            
            return true;
        }
        
        return false;
    }
    
    public void alteraElevacaoPontoSelecionado(double alteracao) {
        if (pontoSelecionado != null) {
            alteraElevacao(selecaoX, selecaoZ, alteracao);
            atualizaEdificios();
        }
    }
    
    public void aplicaPincelPontoSelecionado(boolean inverter) {
        if (pontoSelecionado != null) {
            PINCEIS[pincelSelecionado].aplicaTransformacao(this, selecaoX, selecaoZ, inverter);
            atualizaEdificios();
        }
    }
    
    public void aplicaPincelNivelamentoPontoSelecionado() {
        if (pontoSelecionado != null) {
            PINCEIS[pincelSelecionado].aplicaNivelamento(this, selecaoX, selecaoZ, pontoSelecionado.getY());
            atualizaEdificios();
        }
    }
    
    public void criaEdificioPontoSelecionado() {
        if (removeSelecaoEdificio())
            return;
        
        if (pontoSelecionado != null) {
            Edificio edificio = new Edificio(pontoSelecionado); 
            edificios.add(edificio);
            mapaEdificios.put(pontoSelecionado, edificio);
        }
    }
    
    public void rotacionaEdificioPontoSelecionado(double rotacao) {
        Edificio edificio = selecionaEdificio();
        
        if (edificio != null) {
            edificio.rotaciona(rotacao);
        }
    }
    
    public void limpaSelecao() {
        setSelecaoEdificio(false);
        
        pontoSelecionado = null;
        selecaoX = -1;
        selecaoZ = -1;
    }
    
    public Edificio selecionaEdificio() {
        if (pontoSelecionado != null)
            return mapaEdificios.get(pontoSelecionado);
        
        return null;
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
    
    private void atualizaEdificios() {
        for (Edificio edificio : edificios)
            edificio.atualizaPontosDesenho();
    }
    
    private void setSelecaoEdificio(boolean selecao) {
        Edificio edificio = selecionaEdificio();
        if (edificio != null)
            edificio.setSelecionado(selecao);
    }
    
    private boolean removeSelecaoEdificio() {
        Edificio edificio = selecionaEdificio();
        if (edificio != null) {
            mapaEdificios.remove(pontoSelecionado);
            edificios.remove(edificio);
            
            return true;
        }
        
        return false;
    }
    
    private void desenhaCelula(GL gl, int x, int z) {
        Ponto4D pontos[] = new Ponto4D[4];
        
        pontos[3] = malhaTerreno[x][z];
        pontos[2] = malhaTerreno[x+1][z];
        pontos[1] = malhaTerreno[x+1][z+1];
        pontos[0] = malhaTerreno[x][z+1];
        
        double altura = 0.0;
        for (int i = 0; i < pontos.length; ++i) {
            if (pontos[i].getY() > altura)
                altura = pontos[i].getY();
        }
        
        Color cor = getCorAltura(altura);
        gl.glColor3ub((byte)cor.getRed(), (byte)cor.getGreen(), (byte)cor.getBlue());
        
        gl.glBegin(exibirGrid ? GL.GL_LINE_STRIP : GL.GL_POLYGON);
        
        for (int i = 0; i < pontos.length; ++i) {
            gl.glVertex3d(pontos[i].getX(), pontos[i].getY(), pontos[i].getZ());
        }
        
        Ponto4D normal = Geometria.calculaNormal(pontos[0], pontos[1], pontos[2]);
        gl.glNormal3d(normal.getX(), normal.getY(), normal.getZ());
        
        gl.glEnd();
    }
    
    private void desenhaEdificios(GL gl) {
        for (Edificio edificio : edificios)
            edificio.display(gl);
    }
    
    
    private Color getCorAltura(double altura) {
        if (altura < ALTURA_MINIMA) return COR_TERRENO_BAIXO;
        if (altura > ALTURA_MAXIMA) return COR_TERRENO_ALTO;
        
        double P = (altura - ALTURA_MINIMA) / (ALTURA_MAXIMA - ALTURA_MINIMA);
        
        double R = (COR_TERRENO_ALTO.getRed()   * P) + (COR_TERRENO_BAIXO.getRed()   * (1 - P));
        double G = (COR_TERRENO_ALTO.getGreen() * P) + (COR_TERRENO_BAIXO.getGreen() * (1 - P));
        double B = (COR_TERRENO_ALTO.getBlue()  * P) + (COR_TERRENO_BAIXO.getBlue()  * (1 - P));
        
        return new Color((int)R, (int)G, (int)B);
    }
    
    
    private Ponto4D pontoSelecionado;
    private int selecaoX;
    private int selecaoZ;
    
    private final int comprimento;
    private final int largura;
    private final Ponto4D malhaTerreno[][];
    
    private int pincelSelecionado;
    
    private final ArrayList<Edificio> edificios;
    private final HashMap<Ponto4D, Edificio> mapaEdificios;
    
    private String altText;
}
