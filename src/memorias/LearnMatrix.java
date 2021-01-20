/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memorias;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author COQUE
 */
public class LearnMatrix {

    private ArrayList<PatronMemoria> vectoresX;
    private ArrayList<PatronMemoria> vectoresY;  
    private int[][] matrizM;
    private final int E = 1;

    public LearnMatrix() {
    }

    public LearnMatrix(ArrayList<PatronMemoria> vectoresX, ArrayList<PatronMemoria> vectoresY) {
        this.vectoresX = vectoresX;
        this.vectoresY = vectoresY;
        this.matrizM = new int[vectoresY.get(0).getVector().length][vectoresX.get(0).getVector().length]; // DimensionDelVectorY * DimensionDelVectorX
    }

    public void aprendizajeConParejasExtra(int[][] matrizParejasExtras) {

        //Ponemos en 0 la matriz
        for (int i = 0; i < vectoresY.get(0).getVector().length; i++) {
            for (int j = 0; j < vectoresX.get(0).getVector().length; j++) {
                matrizM[i][j] = 0;
            }
        }

        //Vamos a hacer nuevas matrices con los respectivos vectores x & y
        int k = 0;
        int k_x = 0;
        int k_y=0;
        while (k < vectoresX.size()) { // Lo vamos a repetir segun la cantidad de patrones x

            for (int j = 0; j < vectoresY.get(0).getVector().length; j++) { //Dimension del vector y
                for (int i = 0; i < vectoresX.get(0).getVector().length; i++) {//Dimension del vector x
                    if (vectoresX.get(k_x).getVector()[i] == 1 && vectoresY.get(k_y).getVector()[j] == 1) {
                        matrizM[j][i] += E;
                    } else if (vectoresX.get(k_x).getVector()[i] == 0 && vectoresY.get(k_y).getVector()[j] == 1) {
                        matrizM[j][i] += -E;
                    } else {
                        matrizM[j][i] += 0;
                    }
                }

            }
            
            k++;
            k_y++;
            k_x++;

            if (k_y == vectoresY.size()) { //para que use de nuevo la clase 0
                for (int x = 0; x < matrizParejasExtras.length; x++) {
                    k_x = matrizParejasExtras[x][0];
                    k_y = matrizParejasExtras[x][1];
                    
                    //hacemos la comparacion por parejas nuevas
                    for (int j = 0; j < vectoresY.get(0).getVector().length; j++) { //Dimension del vector y
                        for (int i = 0; i < vectoresX.get(0).getVector().length; i++) {//Dimension del vector x
                            if (vectoresX.get(k_x).getVector()[i] == 1 && vectoresY.get(k_y).getVector()[j] == 1) {
                                matrizM[j][i] += E;
                            } else if (vectoresX.get(k_x).getVector()[i] == 0 && vectoresY.get(k_y).getVector()[j] == 1) {
                                matrizM[j][i] += -E;
                            } else {
                                matrizM[j][i] += 0;
                            }
                        }

                    }
                }
                
                break; // rompemos el while pq ya estamos usando la asignacion extra

            }
           
            
            
        }

        System.out.println();
        System.out.println("Aprendizaje FINAL");
        for (int y = 0; y < vectoresY.get(0).getVector().length; y++) {
            for (int x = 0; x < vectoresX.get(0).getVector().length; x++) {
                if(matrizM[y][x]>=0){
                   System.out.print("\t " + matrizM[y][x] + "\t");
                }else{
                   System.out.print("\t" + matrizM[y][x] + "\t"); 
                }
                
            }
            System.out.println();
        }

    }
    
     public void aprendizaje() {

        //Ponemos en 0 la matriz
        for (int i = 0; i < vectoresY.get(0).getVector().length; i++) {
            for (int j = 0; j < vectoresX.get(0).getVector().length; j++) {
                matrizM[i][j] = 0;
            }
        }

        //Vamos a hacer nuevas matrices con los respectivos vectores x & y
        int k = 0;
        int k_y=0;
        while (k < vectoresX.size()) { // Lo vamos a repetir segun la cantidad de patrones x

            for (int j = 0; j < vectoresY.get(0).getVector().length; j++) { //Dimension del vector y
                for (int i = 0; i < vectoresX.get(0).getVector().length; i++) {//Dimension del vector x
                    if (vectoresX.get(k).getVector()[i] == 1 && vectoresY.get(k_y).getVector()[j] == 1) {
                        matrizM[j][i] += E;
                    } else if (vectoresX.get(k).getVector()[i] == 0 && vectoresY.get(k_y).getVector()[j] == 1) {
                        matrizM[j][i] += -E;
                    } else {
                        matrizM[j][i] += 0;
                    }
                }

            }

            k++;
            k_y++;

            if (k_y == vectoresY.size()) { //para que use de nuevo la clase 0
                k_y=0;
            }
            
        }

        System.out.println();
        System.out.println("Aprendizaje FINAL");
        for (int y = 0; y < vectoresY.get(0).getVector().length; y++) {
            for (int x = 0; x < vectoresX.get(0).getVector().length; x++) {
                if(matrizM[y][x]>=0){
                   System.out.print("\t " + matrizM[y][x] + "\t");
                }else{
                   System.out.print("\t" + matrizM[y][x] + "\t"); 
                }
                
            }
            System.out.println();
        }

    }
    
 
    public void recuperacion(int[] vectorRecupera) {

        int[] vectorFinal = new int[vectoresY.get(0).getVector().length];
        
        //Multiplicamos el que evaluamos por la matriz
        for (int y = 0; y < vectoresY.get(0).getVector().length; y++) { //se va hacer las veces necearias segun las filas de la matriz
            for (int x = 0; x < vectoresX.get(0).getVector().length; x++) {
                vectorFinal[y] += vectorRecupera[x] * matrizM[y][x];
            }
        }
        
        //ahora transformamos el vectorFinal a 1´s & 0´s
        int mayor = 0;
        for (int i = 0; i < vectorFinal.length; i++) {
            if (mayor < vectorFinal[i]) {
                mayor = vectorFinal[i];
            }
        }
        
        // si es igual al numero mas grande cambiamos por 1, sino por 0
        for (int i = 0; i < vectorFinal.length; i++) {
            
            if (mayor == vectorFinal[i]) {
                vectorFinal[i] = 1;
            } else {
                vectorFinal[i] = 0;

            }
        }
  
        if (isValido(vectorFinal)) {
            System.out.println("vectorFinal: ");
            for (int i = 0; i < vectorFinal.length; i++) {
                System.out.println(vectorFinal[i]);
            }
        } else {
            System.out.println("Error, fenomeno de saturacion: ");
            for (int i = 0; i < vectorFinal.length; i++) {
                System.out.println(vectorFinal[i]);
            }
        }

    }

    public boolean isValido(int[] vectorFinal) {
        boolean valido = true;

        int contador = 0;
        for (int i = 0; i < vectorFinal.length; i++) {
            contador += vectorFinal[i];
        }

        if (contador > 1) {
            valido = false;
        }

        return valido;

    }

    
    

    public static void main(String[] args) throws IOException {
        ArrayList<PatronMemoria> patronesX = LeerPatronMemoria.tokenizarDataSet();
        ArrayList<PatronMemoria> patronesY = LeerPatronMemoria.tokenizarDataSet();
        LearnMatrix learnMatrix = new LearnMatrix(patronesX, patronesY);
        int[][]matrizParejasAdicionales;
        
        //introducir {Vector de etrada x, clase asignaday}
        //matrizParejasAdicionales = new int [][] { {3,0} };
        matrizParejasAdicionales = new int [][] { {3,0},{4,2} };
        learnMatrix.aprendizajeConParejasExtra(matrizParejasAdicionales);
        
        int recupera1[] = {1,0,1,0,1};
        int recupera2[] = {1,1,0,0,1};
        int recupera3[] = {1,0,1,1,0};
        int recupera4[] = {0,1,0,1,1};
        int recupera5[] = {0,0,1,0,1};
        learnMatrix.recuperacion(recupera1);
        learnMatrix.recuperacion(recupera2);
        learnMatrix.recuperacion(recupera3);
        learnMatrix.recuperacion(recupera4);
        learnMatrix.recuperacion(recupera5);

    }

}
