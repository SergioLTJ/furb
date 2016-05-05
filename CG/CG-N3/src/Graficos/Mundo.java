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
    
    public Mundo() {
        listaObjetos = new LinkedList<>();
        objetoSelecionado = null;
        objetoConstrucao = null;
        camera = new Camera();
    }
    
    // DISPLAY
    public void display(GL gl) {
        for (ObjetoGrafico obj : listaObjetos) {
            obj.display(gl, false);
        }
        if (objetoSelecionado != null) {
            objetoSelecionado.display(gl, true);
        }
    }
    
    // FUNCOES - GET
    public boolean possuiSelecao() {
        return objetoSelecionado != null;
    }
    
    public ObjetoGrafico getSelecao() {
        return objetoSelecionado;
    }
    
    public ObjetoGrafico getConstrucao() {
        return objetoConstrucao;
    }
    
    // FUNCOES - CONSTRUCAO
    public void iniciaObjeto(Ponto4D pontoInicial) {
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
    }
    
    public void atualizaConstrucaoObjeto(Ponto4D ponto) {
        if (objetoConstrucao != null)
            objetoConstrucao.setPontoConstrucao(ponto);
    }
    
    public void avancaConstrucaoObjeto(Ponto4D ponto) {
        atualizaConstrucaoObjeto(ponto);
        objetoConstrucao.addPonto(ponto);
    }
    
    public boolean regressaConstrucaoObjeto() {
        if (objetoConstrucao.getQuantPontos() > 1) {
            objetoConstrucao.removeUltimoPonto();
            return false;
        }
        
        cancelaConstrucaoObjeto();
        return true;
    }
    
    public void finalizaConstrucaoObjeto(int index) {
        objetoConstrucao.encerraObjeto(index);
    }
    
    public void cancelaConstrucaoObjeto() {
        if (objetoSelecionado == objetoConstrucao) {
            listaObjetos.remove(objetoSelecionado);
            objetoSelecionado = null;
        } else {
            objetoSelecionado.removeFilho(objetoConstrucao);
        }
        
        objetoConstrucao = null;
    }
    
    // FUNCOES - TRANSFORMACAO
    public void moveObjeto(double x, double y, double z) {
        if (objetoSelecionado != null) {
            if (objetoSelecionado.possuiPontoSelecionado()) {
                objetoSelecionado.translacaoPonto(x, y, z);
            } else {
                objetoSelecionado.translacao(x, y, z);
            }
        }
    }
    
    public void escalaObjeto(double escala) {
        if (objetoSelecionado != null)
            objetoSelecionado.escala(escala);
    }
    
    public void rotacaoObjeto(double rotacao) {
        if (objetoSelecionado != null)
            objetoSelecionado.rotacao(rotacao);
    }
    
    // FUNCOES - SELECAO
    public boolean selecionaObjeto(Ponto4D ponto) {
        ObjetoGrafico novaSelecao = null;
        
        for (ObjetoGrafico obj : listaObjetos) {
            if (obj.getBound().calcula(ponto)) {
                novaSelecao = obj;
                break;
            }
        }
        
        if (novaSelecao == null)
            return false;
        
        if (objetoSelecionado == novaSelecao) {
            int indexPonto = novaSelecao.indexPonto(ponto);
            if (indexPonto < 0) {
                novaSelecao.removeSelecaoPonto();
            } else {
                novaSelecao.selecionaPonto(indexPonto);
            }
        }
        
        objetoSelecionado = novaSelecao;
        return true;
    }
    
    public void deletaSelecao() {
        listaObjetos.remove(objetoSelecionado);
        removeSelecao();
    }
    
    public void removeSelecao() {
        objetoSelecionado = null;
    }
    
    // ATRIBUTOS
    List<ObjetoGrafico> listaObjetos;
    ObjetoGrafico objetoSelecionado;
    ObjetoGrafico objetoConstrucao;
    Camera camera;
}
