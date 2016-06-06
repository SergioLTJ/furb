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
public class Pincel {
    
    public static final Pincel PINCEL_DIAMANTE = new Pincel(new double[][]{{0.0,0.0,0.0,1.0,0.0,0.0,0.0},
                                                                           {0.0,0.0,1.0,2.0,1.0,0.0,0.0},
                                                                           {0.0,1.0,2.0,5.0,2.0,1.0,0.0},
                                                                           {1.0,2.0,5.0,5.0,5.0,2.0,1.0},
                                                                           {0.0,1.0,2.0,5.0,2.0,1.0,0.0},
                                                                           {0.0,0.0,1.0,2.0,1.0,0.0,0.0},
                                                                           {0.0,0.0,0.0,1.0,0.0,0.0,0.0}});
    
    public static final Pincel PINCEL_QUADRADO = new Pincel(new double[][]{{1.0,1.0,1.0,1.0,1.0,1.0,1.0},
                                                                           {1.0,2.0,2.0,2.0,2.0,2.0,1.0},
                                                                           {1.0,2.0,5.0,5.0,5.0,2.0,1.0},
                                                                           {1.0,2.0,5.0,5.0,5.0,2.0,1.0},
                                                                           {1.0,2.0,5.0,5.0,5.0,2.0,1.0},
                                                                           {1.0,2.0,2.0,2.0,2.0,2.0,1.0},
                                                                           {1.0,1.0,1.0,1.0,1.0,1.0,1.0}});
    
    public static final Pincel PINCEL_XIS = new Pincel(new double[][]{{1.0,1.0,0.0,0.0,0.0,1.0,1.0},
                                                                      {1.0,5.0,1.0,0.0,1.0,5.0,1.0},
                                                                      {0.0,1.0,5.0,5.0,5.0,1.0,0.0},
                                                                      {0.0,0.0,1.0,5.0,1.0,0.0,0.0},
                                                                      {0.0,1.0,5.0,1.0,5.0,1.0,0.0},
                                                                      {1.0,5.0,1.0,0.0,1.0,5.0,1.0},
                                                                      {1.0,1.0,0.0,0.0,0.0,1.0,1.0}});
    
    public Pincel(double pincel[][]) {
        transformacao = pincel;
        
        comprimento = pincel.length;
        largura = pincel[0].length;
    }
    
    
    public void aplicaTransformacao(Terreno terreno, int posX, int posZ) {
        int compTerreno = terreno.getComprimento();
        int largTerreno = terreno.getLargura();
        
        posX -= comprimento / 2;
        posZ -= largura / 2;
        
        for (int x = 0; x < comprimento; ++x) {
            int chX = posX + x;
            if (chX >= 0 && chX < compTerreno) {
                for (int z = 0; z < largura; ++z) {
                    int chZ = posZ + z;
                    if (chZ >= 0 && chZ < largTerreno) {
                        terreno.alteraElevacao(chX, chZ, transformacao[x][z]);
                    }
                }
            }
        }
    }
    
    private double transformacao[][];
    
    private int comprimento;
    private int largura;
    
}
