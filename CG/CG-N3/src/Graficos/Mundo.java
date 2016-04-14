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
        camera = new Camera();
    }
    
    // DISPLAY
    public void display(GL gl) {
        for (ObjetoGrafico obj : listaObjetos)
            obj.display(gl);
    }
    
    // FUNCOES - GET
    public boolean possuiSelecao() {
        return objetoSelecionado != null;
    }
    
    public ObjetoGrafico getSelecao() {
        return objetoSelecionado;
    }
    
    // FUNCOES - CONSTRUCAO
    public void iniciaObjeto(Ponto4D pontoInicial) {
        ObjetoGrafico novoObjeto = new ObjetoGrafico();
        novoObjeto.addPonto(new Ponto4D(pontoInicial.getX(), pontoInicial.getY()));
        novoObjeto.setPontoConstrucao(pontoInicial);
        
        listaObjetos.add(novoObjeto);
        objetoSelecionado = novoObjeto;
    }
    
    public void atualizaConstrucaoObjeto(Ponto4D ponto) {
        if (objetoSelecionado != null)
            objetoSelecionado.setPontoConstrucao(ponto);
    }
    
    public void avancaConstrucaoObjeto(Ponto4D ponto) {
        atualizaConstrucaoObjeto(ponto);
        objetoSelecionado.addPonto(ponto);
    }
    
    public void regressaConstrucaoObjeto() {
        objetoSelecionado.removeUltimoPonto();
    }
    
    public void finalizaConstrucaoObjeto(int index) {
        objetoSelecionado.encerraObjeto(index);
        objetoSelecionado = null;
    }
    
    // ATRIBUTOS
    List<ObjetoGrafico> listaObjetos;
    ObjetoGrafico objetoSelecionado;
    Camera camera;
}
