/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterimagenes;

import java.awt.Color;


public class PatronImagen {
    
    private int x;
    private int y;
    private String clase;
    private String claseResultante;
    private double[] vectorC;

    public PatronImagen(int x, int y, double[]vector) {
        this.x = x;
        this.y = y;
        this.vectorC = vector;
        this.clase = calcularNombre(vector);
        this.claseResultante = "";
    }
    public double calcularDistancia (PatronImagen aux){
        double sumatoria = 0;
        // recorre el vector característico
        for (int x=0; x<aux.getVectorC().length;x++ ){
         sumatoria+= Math.pow(this.vectorC[x]-aux.getVectorC()[x], 2);
        }
        return Math.sqrt(sumatoria);
    }
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the clase
     */
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
        // hacer la adaptación para el tono de la imagen
        this.claseResultante = claseResultante;
    }

    /**
     * @return the vectorC
     */
    public double[] getVectorC() {
        return vectorC;
    }

    /**
     * @param vectorC the vectorC to set
     */
    public void setVectorC(double[] vectorC) {
        this.vectorC = vectorC;
    }

    private String calcularNombre(double[] vector) {
        Color color = new Color((int)vector[0],(int)vector[1],(int)vector[2]);
        return ""+color.getRGB();
    }
    
    
    public void actualizarNombre(){
        this.clase = calcularNombre(this.vectorC);
    }

    @Override
    public boolean equals(Object obj) {
        PatronImagen aux = (PatronImagen) obj;
        return this.clase.equals(aux.getClase()); //To change body of generated methods, choose Tools | Templates.
    }
      
}
