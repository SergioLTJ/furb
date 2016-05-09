/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import Logica.BoundingBox;
import Logica.Transform;
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
    
    /**
     * Constroi um ObjetoGrafico
     */
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
    
    /**
     * Desenhar o ObjetoGrafico e seus componentes
     * @param gl Objeto de desenho
     */
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
            obj.displayChild(gl, transform);
        }
        
        if (selecionado) {
            bound.display(gl);
        }
        if (selecionado && pontoSelecionado != null) {
            pontoSelecionado.getAreaSelecao().display(gl);
        }
    }
    
    /**
     * Desenhar o ObjetoGrafico e seus componentes utilizando uma matriz de transformacao base
     * @param gl Objeto de desenho
     * @param matrizBase Matriz de transformacao a utilizar
     */
    private void displayChild(GL gl, Transform matrizBase) {
        gl.glColor3f(getCor().getRed(), getCor().getGreen(), getCor().getBlue());
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
            obj.displayChild(gl, matrizCombinada);
        }
        
        if (selecionado) {
            bound.display(gl);
        }
        if (selecionado && pontoSelecionado != null) {
            pontoSelecionado.getAreaSelecao().display(gl);
        }
    }
    
    /**
     * Retorna o objeto Color correspondente ao ObjetoGrafico
     * @return Objeto Color
     */
    public Color getCor() {
        return cores[indiceCor];
    }

    /**
     * Retorna a matriz de transformacao do ObjetoGrafico
     * @return Objeto Transform
     */
    public Transform getMatriz() {
        return transform;
    }
    
    /**
     * Retorna vertice correspondente ao indice
     * @param index Indice do vertice
     * @return Vertice correspondente
     */
    public Ponto4D getPonto(int index) {
        return pontos.get(index);
    }
    
    /**
     * Retorna o ultimo vertice do ObjetoGrafico
     * @return Ultimo vertice
     */
    public Ponto4D getUltimoPonto() {
        if (pontos.isEmpty())
            return null;
        return pontos.get(pontos.size() - 1);
    }
    
    /**
     * Retorna a quantidade de vertices no ObjetoGrafico
     * @return Quantidade de vertices
     */
    public int getQuantPontos() {
        return pontos.size();
    }
    
    /**
     * Testa a existencia de vertice selecionado
     * @return true caso haja vertice selecionado
     */
    public boolean possuiPontoSelecionado() {
        return pontoSelecionado != null;
    }
    
    /**
     * Retorna o vertice selecionado
     * @return Vertice selecionado
     */
    public Ponto4D getPontoSelecionado() {
        return pontoSelecionado;
    }
    
    /**
     * Retorna o ObjetoGrafico filho para o indice
     * @param index Indice do filho
     * @return ObjetoGrafico filho
     */
    public ObjetoGrafico getFilho(int index) {
        return filhos.get(index);
    }
    
    /**
     * Retorna a quantidade de filhos para o ObjetoGrafico
     * @return Quantidade de filhos
     */
    public int getQuantFilhos() {
        return filhos.size();
    }
    
    /**
     * Altera a cor do ObjetoGrafico para a proxima na lista de cores, e define esta cor para todos os filhos
     */
    public void proximaCor() {
        indiceCor++;
        if (indiceCor >= cores.length)
            indiceCor = 0;
        
        for (ObjetoGrafico obj : filhos) {
            obj.setCor(indiceCor);
        }
    }

    /**
     * Define a cor para o ObjetoGrafico e todos os filhos
     * @param indice Indice da lista de cores
     */
    public void setCor(int indice) {
        indiceCor = indice;
        
        for (ObjetoGrafico obj : filhos) {
            obj.setCor(indice);
        }
    }
    
    /**
     * Define a primitiva de desenho do ObjetoGrafico
     * @param primitiva Primitiva de desenho
     */
    public void setPrimitiva(int primitiva) {
        this.primitiva = primitiva;
    }
    
    /**
     * Define o ponto de exibicao do preview de construcao do ObjetoGrafico
     * @param ponto Ponto de atualizacao
     */
    public void setPontoConstrucao(Ponto4D ponto) {
        pontoConstrucao = ponto;
    }
    
    /**
     * Adiciona um ObjetoGrafico filho
     * @param filho Novo filho
     */
    public void addFilho(ObjetoGrafico filho) {
        filhos.add(filho);
    }
    
    /**
     * Remove o filho correspondente ao ObjetoGrafico, se houver
     * @param obj ObjetoGrafico a remover
     * @return true caso tenha removido um filho
     */
    public boolean removeFilho(ObjetoGrafico obj) {
        return filhos.remove(obj);
    }
    
    /**
     * Adiciona vertice ao ObjetoGrafico
     * @param ponto Novo vertice
     */
    public void addPonto(Ponto4D ponto) {
        pontos.add(ponto);
    }
    
    /**
     * Remove o ultimo vertice adicionado
     * @return O vertice removido
     */
    public Ponto4D removeUltimoPonto() {
        return pontos.remove(pontos.size() - 1);
    }
    
    /**
     * Testa a selecao dos vertices existentes e retorna o indice do vertice selecionado, se houver
     * @param clique Ponto de selecao
     * @return Indice do vertice selecionado, ou -1 se nao houver selecao
     */
    public int indexPonto(Ponto4D clique) {
        for (int i = 0; i < pontos.size(); ++i) {
            if (pontos.get(i).getAreaSelecao().calcula(clique))
                return i;
        }
        
        return -1;
    }
    
    /**
     * Seleciona ou remove a selecao de um vertice existente
     * @param clique Ponto de selecao
     */
    public void selecionaPonto(Ponto4D clique) {
        int index = indexPonto(clique);
        if (index < 0) {
            removeSelecaoPonto();
        } else {
            pontoSelecionado = pontos.get(index);
        }
    }
    
    /**
     * Remove a selecao de vertices para o ObjetoGrafico e seus filhos
     */
    public void removeSelecaoPonto() {
        pontoSelecionado = null;
        for (ObjetoGrafico obj : filhos) {
            obj.removeSelecaoPonto();
        }
    }
    
    /**
     * Finaliza a construcao do ObjetoGrafico, criando um poligono fechado caso o indice do vertice selecionado seja igual ao primeiro vertice adicionado, ou aberto caso contrario
     * @param index Indice do vertice selecionado para finalizar o ObjetoGrafico
     */
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
    
    /**
     * Refaz o calculo da BoundingBox
     */
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
    
    /**
     * Testa a selecao do ObjetoGrafico e seus filhos
     * @param ponto Ponto de selecao
     * @return O ObjetoGrafico selecionao, podendo ser o mesmo ou qualquer um dos filhos
     */
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
    
    /**
     * Define o estado de selecao do ObjetoGrafico e seus filhos
     * @param selecao Estado de selecao
     */
    public void setSelecao(boolean selecao) {
        selecionado = selecao;
        
        for (ObjetoGrafico obj : filhos) {
            obj.setSelecao(selecao);
        }
    }
    
    /**
     * Deleta o ObjetoGrafico selecionado, caso esteja entre os filhos do ObjetoGrafico atual
     * @param selecao ObjetoGrafico selecionado
     * @return true caso tenha encontrado e deletado um filho
     */
    public boolean deletaSelecao(ObjetoGrafico selecao) {
        for (int i = 0; i < filhos.size(); ++i) {
            ObjetoGrafico obj = filhos.get(i);
            if (obj == selecao) {
                filhos.remove(i);
                return true;
            }
            
            if (obj.deletaSelecao(selecao)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Verifica se o ponto esta no ObjetoGrafico utilizando o metodo scanline
     */
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
    
    /**
     * Faz a translacao do ObjetoGrafico ou do vertice selecionado, caso exista
     * @param x Translacao eixo X
     * @param y Translacao eixo Y
     * @param z Translacao eixo Z
     */
    public void translacaoSelecao(double x, double y, double z) {
        if (pontoSelecionado != null) {
            translacaoPonto(x, y, z);
        } else {
            Transform translate = new Transform();
            translate.atribuirTranslacao(x,y,z);
            transform = translate.transformMatrix(transform);
        }
    }
    
    /**
     * Altera a escala do ObjetoGrafico
     * @param escala Multiplicador da escala
     */
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
    
    /**
     * Efetua a rotacao do ObjetoGrafico
     * @param rotacao Angulo da rotacao
     */
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
    
    /**
     * Faz a translacao do vertice selecionado
     */
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
