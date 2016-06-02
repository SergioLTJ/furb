/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorTerreno;

import javax.media.opengl.glu.GLU;

/**
 *
 * @author Avell
 */
public class Camera {
    
    private double LARGURA_TELA = 800.0;
    private double ALTURA_TELA = 800.0;
    
    private double CAMPO_VISAO = 60.0;
    
    private double LIMITE_NEAR = 1.0;
    private double LIMITE_FAR = 1000.0;
    
    public Camera() {
        pontoOlho = new Ponto4D();
        pontoFoco = new Ponto4D();
        
        pontoOlho.setX(0.0);
        pontoOlho.setY(100.0);
        pontoOlho.setZ(-250.0);
        
        raioRotacao = Geometria.distancia2D(pontoOlho.getX(), pontoOlho.getZ(), pontoFoco.getX(), pontoFoco.getZ());
        anguloRotacao = 90.0;
        
        atualizaPontoFoco();
    }
    
    public void perspective(GLU glu) {
        glu.gluPerspective(CAMPO_VISAO, LARGURA_TELA / ALTURA_TELA, LIMITE_NEAR, LIMITE_FAR);
    }
    
    public void lookAt(GLU glu) {
        glu.gluLookAt(pontoOlho.getX(), pontoOlho.getY(), pontoOlho.getZ(), pontoFoco.getX(), pontoFoco.getY(), pontoFoco.getZ(), 0.0, 1.0, 0.0);
    }
    
    public void pan(Ponto4D deslocamento) {
        pontoOlho.translacaoPonto(deslocamento.getX(), 0, deslocamento.getZ());
        atualizaPontoFoco();
    }
    
    public void zoom(int valorZoom) {
        
    }
    
    public void rotate(double angulo) {
        anguloRotacao += angulo;
        atualizaPontoFoco();
    }
    
    private void atualizaPontoFoco() {
        double novoX = Geometria.rotacaoX(anguloRotacao, raioRotacao);
        double novoZ = Geometria.rotacaoZ(anguloRotacao, raioRotacao);
        
        pontoFoco.setX(pontoOlho.getX() + novoX);
        pontoFoco.setZ(pontoOlho.getZ() + novoZ);
    }
    
    private Ponto4D pontoOlho;
    private Ponto4D pontoFoco;
    
    private double raioRotacao;
    private double anguloRotacao;
}
