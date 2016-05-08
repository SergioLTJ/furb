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
    private static final Color cores[] = { Color.BLACK, Color.YELLOW, Color.ORANGE, Color.RED, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA };
    

    public ObjetoGrafico() {
        indiceCor = 0; 
        bound = new BoundingBox();
        pontos = new LinkedList<>();
        pontoSelecionado = null;
        pontoConstrucao = null;
        primitiva = GL.GL_LINE_STRIP;
        transform = new Transform();
        filhos = new LinkedList<>();
    }
    

    public void display(GL gl) {
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
            obj.displayChild(gl, transform, getCor());
        }
        
        if (selecionado) {
            bound.display(gl);
        }
        if (selecionado && pontoSelecionado != null) {
            pontoSelecionado.getAreaSelecao().display(gl);
        }
    }
    
    private void displayChild(GL gl, Transform matrizBase, Color corBase) {
        gl.glColor3f(corBase.getRed(), corBase.getGreen(), corBase.getBlue());
        gl.glLineWidth(selecionado ? 4 : 2);
        
        Transform matrizCombinada = new Transform();
        matrizCombinada = matrizCombinada.transformMatrix(matrizBase);
        matrizCombinada = matrizCombinada.transformMatrix(transform);
        
        gl.glPushMatrix();
        gl.glMultMatrixd(matrizCombinada.getDate(), 0);
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
        
        for (ObjetoGrafico obj : filhos) {
            obj.displayChild(gl, matrizCombinada, getCor());
        }
        
        if (selecionado) {
            bound.display(gl);
        }
        if (selecionado && pontoSelecionado != null) {
            pontoSelecionado.getAreaSelecao().display(gl);
        }
    }
    

    public Color getCor() {
        return cores[indiceCor];
    }

    public Transform getMatriz() {
        return transform;
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
    
    public boolean possuiPontoSelecionado() {
        return pontoSelecionado != null;
    }
    
    public Ponto4D getPontoSelecionado() {
        return pontoSelecionado;
    }
    

    public ObjetoGrafico getFilho(int index) {
        return filhos.get(index);
    }
    
    public int getQuantFilhos() {
        return filhos.size();
    }
    

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
    

    public void addFilho(ObjetoGrafico filho) {
        filhos.add(filho);
    }
    
    public boolean removeFilho(ObjetoGrafico obj) {
        return filhos.remove(obj);
    }
    

    public void addPonto(Ponto4D ponto) {
        pontos.add(ponto);
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
    
    public void selecionaPonto(Ponto4D clique) {
        int index = indexPonto(clique);
        if (index < 0) {
            removeSelecaoPonto();
        } else {
            pontoSelecionado = pontos.get(index);
        }
    }
    
    public void removeSelecaoPonto() {
        pontoSelecionado = null;
        for (ObjetoGrafico obj : filhos) {
            obj.removeSelecaoPonto();
        }
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
    
    public ObjetoGrafico testaSelecao(Ponto4D ponto) {
        if (bound.calcula(ponto) && pontoNoPoligono(ponto)) {
            return this;
        }
        
        ObjetoGrafico sel = null;
        for (ObjetoGrafico obj : filhos) {
            sel = obj.testaSelecao(ponto);
            if (sel != null) {
                break;
            }
        }
        
        return sel;
    }
    
    public void setSelecao(boolean selecao) {
        selecionado = selecao;
        
        for (ObjetoGrafico obj : filhos) {
            obj.setSelecao(selecao);
        }
    }
    
    private boolean pontoNoPoligono(Ponto4D ponto) {
        int interseccoes = 0;
        for (int i = 0; i < pontos.size(); ++i) {
            Ponto4D vertice = pontos.get(i);
            Ponto4D proxVert = pontos.get((i == (pontos.size()-1)) ? 0 : i+1);
            
            if (vertice.getY() != ponto.getY()) {
                Ponto4D intersec = ponto.intersecHorizontal(vertice, proxVert);
                if (intersec.getX() == ponto.getX()) {
                    break;
                } else {
                    if (intersec.getX() > ponto.getX() && 
                        intersec.getY() > Math.min(vertice.getY(), proxVert.getY()) &&
                        intersec.getY() <= Math.max(vertice.getY(), proxVert.getY())) {
                        interseccoes++;
                    }
                }
            } else {
                if (ponto.getY() == vertice.getY() && 
                    ponto.getX() >= Math.min(vertice.getX(), proxVert.getX()) &&
                    ponto.getX() <= Math.max(vertice.getX(), proxVert.getX())) {
                    break;
                }
            }
        }
        return interseccoes % 2 == 1;
    }
    

    public void translacaoSelecao(double x, double y, double z) {
        if (pontoSelecionado != null) {
            translacaoPonto(x, y, z);
        } else {
            Transform translate = new Transform();
            translate.atribuirTranslacao(x,y,z);
            transform = translate.transformMatrix(transform);
        }
    }
    
    public void escalaSelecao(double escala) {
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
    
    public void rotacaoSelecao(double rotacao) {
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
    
    private void translacaoPonto(double x, double y, double z) {
        pontoSelecionado.translacaoPonto(x, y, z);
        atualizaBoundingBox();
    }
    
    // ATRIBUTOS - LOGICA
    private final BoundingBox bound;
    private Transform transform;
    private Ponto4D pontoSelecionado;
    
    // ATRIBUTOS - GRAFICOS
    private final List<Ponto4D> pontos;
    private Ponto4D pontoConstrucao;
    private int indiceCor;
    private int primitiva;
    private boolean selecionado;
    
    // ATRIBUTOS - OUTROS
    private final List<ObjetoGrafico> filhos;
}
