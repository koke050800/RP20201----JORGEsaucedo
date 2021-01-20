/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Dell
 */
public class DistInsta {
    
    private String clase;
    private double distancia;

    public DistInsta() {
        this.clase = "";
        this.distancia = 0;
    }
    
 
    public DistInsta(String clase, Patron p1, Patron p2) {
        this.clase = clase;
        this.distancia = calculaDistanciaEcuclidiana(p1, p2);
    }

 
    public String getClase() {
        return clase;
    }

   
    public void setClase(String clase) {
        this.clase = clase;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getDistancia() {
        return distancia;
    }
    
     public static double calculaDistanciaEcuclidiana(Patron p1,Patron p2){
     double acum = 0;
     
     for (int x=0;x < p1.getVectorC().length;x++){
     acum+=Math.pow((p2.getVectorC()[x]-p1.getVectorC()[x]),2);
     
     }
     return Math.sqrt(acum);
    }
    
    
}