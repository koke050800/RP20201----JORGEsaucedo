/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author working
 */
public class Patron {

    private String clase;
    private String claseResultante;
    private double[] vectorC;
    private double[] vectorBin;
    private double[] vectorBinres;

    private int num_clas;
    private int x,y;
    
    public Patron() {
        
    }    
     public Patron(int n) {
        this.vectorC = new double[n];
        this.clase = "Desconocida";
        this.claseResultante = null;
        this.x = -1;
        this.y = -1;
    }
    public Patron(String clase, String claseResultante, double[] vectorC) {
        this.clase = clase;
        this.claseResultante = claseResultante;
        this.vectorC = vectorC;
        this.num_clas = num_clas;
    }
    public Patron(Patron patron) {
        this.vectorC = patron.getVectorC().clone();
        this.clase = patron.getClase();
        this.x = patron.getX();
        this.y = patron.getY();
        
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public String getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * @return the claseResultante
     */
    public String getClaseResultante() {
        return claseResultante;
    }

    /**
     * @param claseResultante the claseResultante to set
     */
    public void setClaseResultante(String claseResultante) {
        this.claseResultante = claseResultante;
    }

    /**
     * @return the vectorC
     */
    public double[] getVectorC() {
        return vectorC;
    }


    public void setVectorC(double[] vectorC) {
        this.vectorC = vectorC;
    }

    public int getNum_clas() {
        return num_clas;
    }

    public void setNum_clas(int num_clas) {
        this.num_clas = num_clas;
    }

    public double[] getVectorBin() {
        return vectorBin;
    }

    public void setVectorBin(double[] vectorBin) {
        this.vectorBin = vectorBin;
    }

    public double[] getVectorBinres() {
        return vectorBinres;
    }

    public void setVectorBinres(double[] vectorBinres) {
        this.vectorBinres = vectorBinres;
    }

}