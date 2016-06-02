/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorTerreno;

/**
 *
 * @author Avell
 */
public class Geometria {
    public static double rotacaoX(double angulo, double raio) {
	return (raio * Math.cos(Math.PI * angulo / 180.0));
    }

    public static double rotacaoZ(double angulo, double raio) {
        return (raio * Math.sin(Math.PI * angulo / 180.0));
    }
    
    public static double distancia2D(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
}
