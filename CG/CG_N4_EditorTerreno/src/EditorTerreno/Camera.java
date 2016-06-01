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
        pontoCamera = new Ponto4D();
        pontoVisao = new Ponto4D();
        
        pontoCamera.setX(250.0);
        pontoCamera.setY(100.0);
        pontoCamera.setZ(250.0);
    }
    
    public void perspective(GLU glu) {
        glu.gluPerspective(CAMPO_VISAO, LARGURA_TELA / ALTURA_TELA, LIMITE_NEAR, LIMITE_FAR);
    }
    
    public void lookAt(GLU glu) {
        glu.gluLookAt(pontoCamera.getX(), pontoCamera.getY(), pontoCamera.getZ(), pontoVisao.getX(), pontoVisao.getY(), pontoVisao.getZ(), 0.0, 1.0, 0.0);
    }
    
    public void pan(Ponto4D deslocamento) {
        pontoCamera.translacaoPonto(deslocamento.getX(), 0, deslocamento.getZ());
        pontoVisao.translacaoPonto(deslocamento.getX(), 0, deslocamento.getZ());
    }
    
    public void zoom(int valorZoom) {
        
    }
    
    private Ponto4D pontoCamera;
    private Ponto4D pontoVisao;
}
