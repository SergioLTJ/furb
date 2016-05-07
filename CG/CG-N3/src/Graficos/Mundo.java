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
    

    public void display(GL gl) {
        for (ObjetoGrafico obj : listaObjetos) {
            obj.display(gl, false);
        }
        if (objetoSelecionado != null) {
            objetoSelecionado.display(gl, true);
        }
    }
    
    
    public boolean possuiSelecao() {
        return objetoSelecionado != null;
    }
    
    public boolean possuiConstrucao() {
        return objetoConstrucao != null;
    }
    
    
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
    }
    
    public void atualizaConstrucaoObjeto(Ponto4D ponto) {
        if (objetoConstrucao != null)
            objetoConstrucao.setPontoConstrucao(ponto);
    }
    
    public void avancaConstrucaoObjeto(Ponto4D ponto) {
        atualizaConstrucaoObjeto(ponto);
        
        int index = objetoConstrucao.indexPonto(ponto);
        if (index < 0) {
            objetoConstrucao.addPonto(ponto);
        } else {
            finalizaConstrucaoObjeto(index);
        }
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
        objetoConstrucao = null;
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
    
    
    public void translacaoObjetoSelecionado(double x, double y, double z) {
        if (possuiSelecao() && !possuiConstrucao())
            objetoSelecionado.translacaoSelecao(x, y, z);
    }
    
    public void escalaObjetoSelecionado(double escala) {
        if (possuiSelecao() && !possuiConstrucao())
            objetoSelecionado.escalaSelecao(escala);
    }
    
    public void rotacaoObjetoSelecionado(double rotacao) {
        if (possuiSelecao() && !possuiConstrucao())
            objetoSelecionado.rotacaoSelecao(rotacao);
    }
    

    public boolean selecionaObjeto(Ponto4D ponto) {
        ObjetoGrafico novaSelecao = null;
        
        for (ObjetoGrafico obj : listaObjetos) {
            if (obj.podeSelecionar(ponto)) {
                novaSelecao = obj;
                break;
            }
        }
        
        if (novaSelecao == null)
            return false;
        
        if (objetoSelecionado == novaSelecao) {
            novaSelecao.selecionaPonto(ponto);
        }
        
        objetoSelecionado = novaSelecao;
        return true;
    }
    
    public void deletaSelecao() {
        if (possuiSelecao())
            listaObjetos.remove(objetoSelecionado);
        
        removeSelecao();
    }
    
    public void removeSelecao() {
        objetoSelecionado = null;
    }
    

    public void alteraCorObjetoSelecionado() {
        if (objetoSelecionado != null)
            objetoSelecionado.proximaCor();
    }
    
    // ATRIBUTOS
    private List<ObjetoGrafico> listaObjetos;
    private ObjetoGrafico objetoSelecionado;
    private ObjetoGrafico objetoConstrucao;
    private Camera camera;
}
