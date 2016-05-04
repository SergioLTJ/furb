/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import Logica.BoundingBox;
import Logica.Transform;
import Tela.Main;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import javax.media.opengl.GL;

/**
 *
 * @author Avell
 */
public class ObjetoGrafico {
    
    // CORES
    private static Color cores[] = { Color.BLACK, Color.YELLOW, Color.ORANGE, Color.RED, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA };
    
    // CONSTRUTORES
    public ObjetoGrafico() {
        indiceCor = 0; 
        bound = new BoundingBox();
        pontos = new LinkedList<>();
        pontoConstrucao = null;
        primitiva = GL.GL_LINE_STRIP;
        transform = new Transform();
        filhos = new LinkedList<>();
    }
    
    // DISPLAY
    public void display(GL gl, boolean selecionado) {
        gl.glColor3f(getCor().getRed(), getCor().getGreen(), getCor().getBlue());
        gl.glLineWidth(selecionado ? 4 : 2);
        
        gl.glPushMatrix();
        gl.glMultMatrixd(transform.getDate(), 0);
        gl.glBegin(primitiva);
        
        // Desenhar objeto
        for (Ponto4D ponto : pontos) {
            gl.glVertex2d(ponto.getX(), ponto.getY());
        }
        
        gl.glEnd();
        gl.glPopMatrix();
        
        // Se estiver em construcao, desenhar a linha em construcao
        if (pontoConstrucao != null) {
            Ponto4D ultimoPonto = getUltimoPonto();
            
            if (ultimoPonto != null) {
                Color corConstrucao = Color.GREEN;
                gl.glColor3f(corConstrucao.getRed(), corConstrucao.getGreen(), corConstrucao.getBlue());
                gl.glBegin(GL.GL_LINE_STRIP);
            
                gl.glVertex2d(ultimoPonto.getX(), ultimoPonto.getY());
                gl.glVertex2d(pontoConstrucao.getX(), pontoConstrucao.getY());
            
                gl.glEnd();
            }
        }
        
        // Desenhar filhos
        for (ObjetoGrafico obj : filhos) {
            obj.display(gl, selecionado);
        }
        
        // Desenhar BoundingBox apenas em debug
        if (Main.modoDebug) {
            bound.display(gl);
        }
    }
    
    // GET
    public BoundingBox getBound() {
        return bound;
    }

    public Color getCor() {
        return cores[indiceCor];
    }

    public int getPrimitiva() {
        return primitiva;
    }
    
    public Ponto4D getPontoConstrucao() {
        return pontoConstrucao;
    }
    
    // GET - PONTOS
    public List<Ponto4D> getPontos() {
        return pontos;
    }
    
    public Ponto4D getPonto(int index) {
        return pontos.get(index);
    }
    
    public Ponto4D getUltimoPonto() {
        if (pontos.isEmpty())
            return null;
        return pontos.get(pontos.size() - 1);
    }
    
    public int getQuantPontos() {
        return pontos.size();
    }
    
    // GET - FILHOS
    public List<ObjetoGrafico> getFilhos() {
        return filhos;
    }
    
    public ObjetoGrafico getFilho(int index) {
        return filhos.get(index);
    }
    
    public int getQuantFilhos() {
        return filhos.size();
    }
    
    // SET
    public void proximaCor() {
        indiceCor++;
        if (indiceCor >= cores.length)
            indiceCor = 0;
    }

    public void setPrimitiva(int primitiva) {
        this.primitiva = primitiva;
    }
    
    public void setPontoConstrucao(Ponto4D ponto) {
        pontoConstrucao = ponto;
    }
    
    // SET - FILHOS
    public void addFilho(ObjetoGrafico filho) {
        filhos.add(filho);
    }
    
    public ObjetoGrafico removeFilho(int index) {
        return filhos.remove(index);
    }
    
    // SET - PONTOS
    public void addPonto(Ponto4D ponto) {
        pontos.add(ponto);
    }
    
    public Ponto4D removePonto(int index) {
        return pontos.remove(index);
    }
    
    public Ponto4D removeUltimoPonto() {
        return pontos.remove(pontos.size() - 1);
    }
    
    // FUNCOES
    public int indexPonto(Ponto4D clique) {
        for (int i = 0; i < pontos.size(); ++i) {
            if (pontos.get(i).getAreaSelecao().calcula(clique))
                return i;
        }
        
        return -1;
    }
    
    public void encerraObjeto(int index) {
        // Se clicar no primeiro vertice, o poligono sera fechado
        if (index == 0) {
            primitiva = GL.GL_LINE_LOOP;
        } else {
            primitiva = GL.GL_LINE_STRIP;
        }
        
        atualizaBoundingBox();
        pontoConstrucao = null;
    }
    
    private void atualizaBoundingBox() {
        Ponto4D primeiroPonto = pontos.get(0);
        
        double minX = primeiroPonto.getX();
        double maxX = primeiroPonto.getX();
        double minY = primeiroPonto.getY();
        double maxY = primeiroPonto.getY();
        double minZ = primeiroPonto.getZ();
        double maxZ = primeiroPonto.getZ();
        
        for (Ponto4D ponto : pontos) {
            double x = ponto.getX();
            double y = ponto.getY();
            double z = ponto.getZ();
            
            if (x < minX) minX = x;
            if (x > maxX) maxX = x;
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
            if (z < minZ) minZ = z;
            if (z > maxZ) maxZ = z;
        }
        
        bound.atribuirBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    // TRANSFORMACAO
    public void translacao(double x, double y, double z) {
        Transform translate = new Transform();
	translate.atribuirTranslacao(x,y,z);
	transform = translate.transformMatrix(transform);
    }
    
    public void escala(double escala) {
        Transform local = new Transform();
        
        Ponto4D pontoCentral = bound.obterCentro();
        pontoCentral.inverterSinal(pontoCentral);
        Transform translate = new Transform();
        
        translate.atribuirTranslacao(pontoCentral.getX(), pontoCentral.getY(), pontoCentral.getZ());
        local = translate.transformMatrix(local);

        Transform scale = new Transform();
        
	scale.atribuirEscala(escala, escala, 1.0);
	local = scale.transformMatrix(local);

	translate.atribuirIdentidade();
        pontoCentral.inverterSinal(pontoCentral);
        
	translate.atribuirTranslacao(pontoCentral.getX(), pontoCentral.getY(), pontoCentral.getZ());
	local = translate.transformMatrix(local);

	transform = transform.transformMatrix(local);
    }
    
    public void rotacao(double rotacao) {
        Transform local = new Transform();

        Ponto4D pontoCentral = bound.obterCentro();
        pontoCentral.inverterSinal(pontoCentral);
        Transform translate = new Transform();
        
	translate.atribuirTranslacao(pontoCentral.getX(),pontoCentral.getY(),pontoCentral.getZ());
	local = translate.transformMatrix(local);

        Transform rotate = new Transform();
        
	rotate.atribuirRotacaoZ(Transform.DEG_TO_RAD * rotacao);
	local = rotate.transformMatrix(local);

	pontoCentral.inverterSinal(pontoCentral);
        translate.atribuirIdentidade();
        
	translate.atribuirTranslacao(pontoCentral.getX(),pontoCentral.getY(),pontoCentral.getZ());
	local = translate.transformMatrix(local);

	transform = transform.transformMatrix(local);
    }
    
    // ATRIBUTOS - LOGICA
    private BoundingBox bound;
    private Transform transform;
    
    // ATRIBUTOS - GRAFICOS
    private List<Ponto4D> pontos;
    private Ponto4D pontoConstrucao;
    private int indiceCor;
    private int primitiva;
    
    // ATRIBUTOS - OUTROS
    private List<ObjetoGrafico> filhos;
}
