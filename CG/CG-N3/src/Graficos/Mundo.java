/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import Logica.Camera;
import java.util.LinkedList;
import java.util.List;
import javax.media.opengl.GL;

/**
 *
 * @author Avell
 */
public class Mundo {
    
    /**
     * Constroi um objeto Mundo
     */
    public Mundo() {
        listaObjetos = new LinkedList<>();
        objetoSelecionado = null;
        objetoConstrucao = null;
        camera = new Camera();
    }
    
    /**
     * Desenhar o Mundo e seus componentes
     * @param gl Objeto de desenho
     */
    public void display(GL gl) {
        for (ObjetoGrafico obj : listaObjetos) {
            obj.display(gl);
        }   
    }
    
    /**
     * Testa a existencia de selecao
     * @return true caso haja ObjetoGrafico selecionado
     */
    public boolean possuiSelecao() {
        return objetoSelecionado != null;
    }
    
    /**
     * Testa a existencia de construcao
     * @return true caso haja ObjetoGrafico em construcao
     */
    public boolean possuiConstrucao() {
        return objetoConstrucao != null;
    }
    
    /**
     * Cria um novo ObjetoGrafico e o coloca em construcao.
     * @param pontoInicial Ponto inicial do ObjetoGrafico
     */
    public void iniciaConstrucaoObjeto(Ponto4D pontoInicial) {
        ObjetoGrafico novoObjeto = new ObjetoGrafico();
        novoObjeto.addPonto(new Ponto4D(pontoInicial.getX(), pontoInicial.getY()));
        novoObjeto.setPontoConstrucao(pontoInicial);
        
        objetoConstrucao = novoObjeto;
        
        if (possuiSelecao()) {
            objetoSelecionado.addFilho(novoObjeto);
        } else {
            listaObjetos.add(novoObjeto);
            objetoSelecionado = novoObjeto;
        }
        
        objetoSelecionado.setSelecao(true);
    }
    
    /**
     * Atualiza a exibicao do preview de construcao do ObjetoGrafico
     * @param ponto Ponto de atualizacao
     */
    public void atualizaConstrucaoObjeto(Ponto4D ponto) {
        if (objetoConstrucao != null)
            objetoConstrucao.setPontoConstrucao(ponto);
    }
    
    /**
     * Adiciona um novo vertice ao ObjetoGrafico em construcao
     * @param ponto Ponto de construcao do novo vertice
     */
    public void avancaConstrucaoObjeto(Ponto4D ponto) {
        atualizaConstrucaoObjeto(ponto);
        
        int index = objetoConstrucao.indexPonto(ponto);
        if (index < 0) {
            objetoConstrucao.addPonto(ponto);
        } else {
            finalizaConstrucaoObjeto(index);
        }
    }
    
    /**
     * Remove o ultimo vertice do ObjetoGrafico em construcao, e cancela o objeto caso nao haja mais vertices restantes
     * @return true se a construcao foi cancelada
     */
    public boolean regressaConstrucaoObjeto() {
        if (objetoConstrucao.getQuantPontos() > 1) {
            objetoConstrucao.removeUltimoPonto();
            return false;
        }
        
        cancelaConstrucaoObjeto();
        return true;
    }
    
    /**
     * Encerra a construcao do ObjetoGrafico atual
     * @param index Indice do ponto selecionado para finalizar o ObjetoGrafico
     */
    public void finalizaConstrucaoObjeto(int index) {
        objetoConstrucao.encerraObjeto(index);
        objetoConstrucao = null;
    }
    
    /**
     * Cancela o ObjetoGrafico em construcao
     */
    public void cancelaConstrucaoObjeto() {
        if (objetoSelecionado == objetoConstrucao) {
            listaObjetos.remove(objetoSelecionado);
            objetoSelecionado = null;
        } else {
            objetoSelecionado.removeFilho(objetoConstrucao);
        }
        
        objetoConstrucao = null;
    }
    
    /**
     * Efetua a translacao do ObjetoGrafico ou vertice selecionado
     * @param x Translacao eixo X
     * @param y Translacao eixo Y
     * @param z Translacao eixo Z
     */
    public void translacaoObjetoSelecionado(double x, double y, double z) {
        if (possuiSelecao() && !possuiConstrucao())
            objetoSelecionado.translacaoSelecao(x, y, z);
    }
    
    /**
     * Altera a escala do ObjetoGrafico selecionado
     * @param escala Multiplicador da escala
     */
    public void escalaObjetoSelecionado(double escala) {
        if (possuiSelecao() && !possuiConstrucao())
            objetoSelecionado.escalaSelecao(escala);
    }
    
    /**
     * Efetua a rotacao do ObjetoGrafico selecionado
     * @param rotacao Angulo da rotacao
     */
    public void rotacaoObjetoSelecionado(double rotacao) {
        if (possuiSelecao() && !possuiConstrucao())
            objetoSelecionado.rotacaoSelecao(rotacao);
    }
    
    /**
     * Testa a selecao do itens ObjetoGrafico existentes, e a selecao dos vertices caso o ObjetoGrafico seja selecionado novamente
     * @param ponto Ponto de selecao
     * @return true caso tenha selecionado um ObjetoGrafico ou vertice
     */
    public boolean selecionaObjeto(Ponto4D ponto) {
        ObjetoGrafico novaSelecao = null;
        
        for (ObjetoGrafico obj : listaObjetos) {
            novaSelecao = obj.testaSelecao(ponto);
            if (novaSelecao != null) {
                break;
            }
        }
        
        if (objetoSelecionado != null) {
            objetoSelecionado.setSelecao(false);
            objetoSelecionado.removeSelecaoPonto();
        }
        
        if (novaSelecao == null)
            return false;
        
        novaSelecao.setSelecao(true);
        
        if (objetoSelecionado == novaSelecao) {
            novaSelecao.selecionaPonto(ponto);
        }
        
        objetoSelecionado = novaSelecao;
        return true;
    }
    
    /**
     * Exclui o ObjetoGrafico selecionado
     */
    public void deletaSelecao() {
        if (possuiSelecao()) {
            for (int i = 0; i < listaObjetos.size(); ++i) {
                ObjetoGrafico obj = listaObjetos.get(i);
                if (obj == objetoSelecionado) {
                    listaObjetos.remove(i);
                    break;
                }
            
                if (obj.deletaSelecao(objetoSelecionado)) {
                    break;
                }
            }
        }
        
        removeSelecao();
    }
    
    /**
     * Remove a selecao do ObjetoGrafico atual
     */
    public void removeSelecao() {
        if (objetoSelecionado != null)
            objetoSelecionado.setSelecao(false);
        objetoSelecionado = null;
    }
    
    /**
     * Altera a cor do ObjetoGrafico selecionado
     */
    public void alteraCorObjetoSelecionado() {
        if (objetoSelecionado != null)
            objetoSelecionado.proximaCor();
    }
    
    // ATRIBUTOS
    private final List<ObjetoGrafico> listaObjetos;
    private ObjetoGrafico objetoSelecionado;
    private ObjetoGrafico objetoConstrucao;
    private final Camera camera;
}
