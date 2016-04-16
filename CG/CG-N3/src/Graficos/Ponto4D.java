/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import Logica.BoundingBox;

/**
 *
 * @author Avell
 */
public class Ponto4D {

    // CONSTRUTORES
    public Ponto4D() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 1;
    }
    
    public Ponto4D(Ponto4D ponto) {
        this.x = ponto.x;
        this.y = ponto.y;
        this.z = ponto.z;
        this.w = ponto.w;
    }
    
    public Ponto4D(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
        this.w = 1;
    }
    
    public Ponto4D(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Ponto4D inverterSinal(Ponto4D pto) {
        pto.setX(pto.getX() * -1);
        pto.setY(pto.getY() * -1);
        pto.setZ(pto.getZ() * -1);
        return pto;
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

    // BOUNDING
    public BoundingBox getAreaSelecao() {
        Ponto4D top = new Ponto4D(x + 5, y + 5, z + 5, w);
        Ponto4D bot = new Ponto4D(x - 5, y - 5, z + 5, w);
        
        return new BoundingBox(bot.x, bot.y, bot.z, top.x, top.y, top.z);
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
