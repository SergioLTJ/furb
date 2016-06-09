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
public class Ponto4D {

    /**
     * Constroi um Ponto4D na origem
     */
    public Ponto4D() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 1;
    }
    
    /**
     * Constroi um Ponto4D com base num ponto existente
     * @param ponto Ponto existente
     */
    public Ponto4D(Ponto4D ponto) {
        this.x = ponto.x;
        this.y = ponto.y;
        this.z = ponto.z;
        this.w = ponto.w;
    }
    
    /**
     * Constroi um Ponto4D nas coordenadas (x,y,0,1)
     * @param x Coordenada X
     * @param y Coordenada Y
     */
    public Ponto4D(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
        this.w = 1;
    }
    
    /**
     * Constroi um Ponto4D nas coordenadas (x,y,z,w)
     * @param x Coordenada X
     * @param y Coordenada Y
     * @param z Coordenada Z
     * @param w Coordenada W
     */
    public Ponto4D(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    /**
     * Inverte as coordenadas do Ponto4D
     * @param pto Ponto cujas coordenadas serao invertidas
     * @return Ponto com as coordenadas invertidas
     */
    public Ponto4D inverterSinal(Ponto4D pto) {
        pto.setX(pto.getX() * -1);
        pto.setY(pto.getY() * -1);
        pto.setZ(pto.getZ() * -1);
        return pto;
    }

    /**
     * Faz a translacao do Ponto4D
     * @param x Translacao eixo X
     * @param y Translacao eixo Y
     * @param z Translacao eixo Z
     */
    public void translacaoPonto(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }
    
    // GET
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getW() {
        return w;
    }

    // SET
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setW(double w) {
        this.w = w;
    }

    /**
     * Retorna um BoundingBox para selecionar o Ponto4D
     * @return BoundingBox para selecao
     */
    public BoundingBox getAreaSelecao() {
        Ponto4D top = new Ponto4D(x + 8, y + 8, z + 8, w);
        Ponto4D bot = new Ponto4D(x - 8, y - 8, z + 8, w);
        
        return new BoundingBox(bot.x, bot.y, bot.z, top.x, top.y, top.z);
    }
    
    /**
     * Retorna um Ponto4D correspondente a interseccao horizontal do Ponto4D atual com uma reta definida pelos objetos Ponto4D ptA e ptB
     * @param ptA Ponto A para definicao da reta
     * @param ptB Ponto B para definicao da reta
     * @return Ponto de interseccao horizontal com a reta
     */
    public Ponto4D intersecHorizontal(Ponto4D ptA, Ponto4D ptB) {
        double a = (ptB.getY() - ptA.getY()) / (ptB.getX() - ptA.getX());
        double b = ptA.getY() - (a * ptA.getX());
        
        double newX = (y - b) / a;
        return new Ponto4D(newX, this.y);
    }
    
    public String getTextoPosicao() {
        return "P(" + x + "," + z + ") H(" + y + ")";
    }
    
    // EQUALS/HASHCODE
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.w) ^ (Double.doubleToLongBits(this.w) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ponto4D other = (Ponto4D) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
            return false;
        }
        if (Double.doubleToLongBits(this.w) != Double.doubleToLongBits(other.w)) {
            return false;
        }
        return true;
    }
    
    // ATRIBUTOS
    private double x;
    private double y;
    private double z;
    private double w;
}
