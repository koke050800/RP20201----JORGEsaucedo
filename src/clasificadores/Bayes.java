/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import data.Patron;
import interfaces.ClasificadorSupervisado;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author COQUE
 */
public class Bayes implements ClasificadorSupervisado {

    ArrayList<Patron> vectoresPromedio;
    ArrayList<Patron> vectoresProbPriori;
    ArrayList<Patron> vectoresVarianza;
    ArrayList<Patron> vectoresDesviacion;
    ArrayList<Patron> vectoresDistibucion;
    ArrayList<Patron> representativos;
    ArrayList<Patron> patronePosteriori;
    ArrayList<Double> contadoresPriori;
    ArrayList<String> totalClases;
    double evidenciaBayes;
    double nInstancias;
    

    public Bayes() {
        this.vectoresPromedio = new ArrayList<>();
        this.vectoresVarianza = new ArrayList<>();
        this.vectoresDesviacion = new ArrayList<>();
        this.vectoresDistibucion = new ArrayList<>();
        this.vectoresProbPriori = new ArrayList<>();
        this.totalClases = new ArrayList<>();
        this.patronePosteriori = new ArrayList<>();
        this.contadoresPriori = new ArrayList<>();
        this.evidenciaBayes = 0;
        this.nInstancias = 0;
    }

    @Override
    public void entrenar(ArrayList<Patron> instancias) {
        nInstancias = instancias.size();
        obtenerSoloClases(instancias);
        sacarVectoresPromedio(instancias);
        sacarVectoresVarianza(instancias);
        sacarVectoresDesviacion(instancias);
        evidenciaBayes = obtenerEvidencia();        
        System.out.println("Evidencia " + evidenciaBayes);
    }

    @Override
    public void clasificar(Patron comparaPatron) {
        sacarDistribucionNormal(comparaPatron);
        cambiaClasificacion(comparaPatron);

    }

    public void obtenerSoloClases(ArrayList<Patron> instancias) {
        // Primero como en minima distancia, necesitamos saber cuantas y cuales son nuestras clases para promediar correctamente.
        for (int i = 0; i < instancias.size(); i++) {
            totalClases.add(instancias.get(i).getClase());
        }
        //Hacemos el hasheo para no tener clases repetidas. 
        Set<String> hashSet = new HashSet<String>(totalClases);
        totalClases.clear();
        totalClases.addAll(hashSet);

    }

    public ArrayList<Patron> sacarVectoresDesviacion(ArrayList<Patron> instancias) {
        //Sacamos las desviaciones
        int n_clases = totalClases.size();
        int tam_vector = instancias.get(0).getVectorC().length;
        double[][] matriz_vectores = new double[n_clases][tam_vector];

        for (int i = 0; i < n_clases; i++) {      //vamos a hacer esto segun la cantidad de clases que tenemos
            for (int j = 0; j < tam_vector; j++) { //a cada columna de la clase, le sacamos la desviacion
                matriz_vectores[i][j] = sqrt(vectoresVarianza.get(i).getVectorC()[j]);
            }
            vectoresDesviacion.add(new Patron(totalClases.get(i), "", matriz_vectores[i]));
        }

        return vectoresDesviacion;
    }

    public ArrayList<Patron> sacarVectoresPromedio(ArrayList<Patron> instancias) {
        //Despues de eso, sacamos los promedios de cada columna, separando cada una con su clase
        int n_clases = totalClases.size();
        int tam_vector = instancias.get(0).getVectorC().length;
        double[][] matriz_vectores = new double[n_clases][tam_vector];
        int[] contadoresPromedio = new int[n_clases];
        double[] contadoresPriorii = new double[n_clases];
        
        
        // Creamos contadoresPriori
        for(int i =0; i<n_clases; i++){
            contadoresPriori.add(0.0);
        }


        for (int i = 0; i < n_clases; i++) {      //vamos a hacer esto segun la cantidad de clases que tenemos
            String temporal = totalClases.get(i); //el temporal es para saber que clase evaluamos
            contadoresPromedio[i] = 0;

            for (int j = 0; j < instancias.size(); j++) {  //siempre vamos a evaluar todas las instancias de nuestro arraylist de patrones
                String comparar = instancias.get(j).getClase();

                if (temporal.equals(comparar)) { //si la instancia que evaluamos es igual a nuestro temporal
                    contadoresPromedio[i]++;//aumentamos en su contador respectivo
                    contadoresPriori.set(i, (contadoresPriori.get(i) + (1.0)));
                    for (int y = 0; y < tam_vector; y++) { //sumamos cada columa "y" a su respectiva clase
                        matriz_vectores[i][y] += instancias.get(j).getVectorC()[y];
                    }
                }
            }
        }
        //crearemos un ciclo para sacar el promedios de una vez, y mandar su respectivo vector  
        for (int i = 0; i < n_clases; i++) {
            double[] vectorFinal = new double[tam_vector];
            for (int y = 0; y < tam_vector; y++) {
                vectorFinal[y] = matriz_vectores[i][y] / contadoresPromedio[i];
            }
            vectoresPromedio.add(new Patron(totalClases.get(i), "", vectorFinal));
        }

        return vectoresPromedio;

    }

    public ArrayList<Patron> sacarVectoresVarianza(ArrayList<Patron> instancias) {
        //Despues de eso, sacamos los promedios de cada columna, separando cada una con su clase
        int n_clases = totalClases.size();
        int tam_vector = instancias.get(0).getVectorC().length;
        double[][] matriz_vectores = new double[n_clases][tam_vector];
        int[] contadoresPromedio = new int[n_clases];

        for (int i = 0; i < n_clases; i++) {      //vamos a hacer esto segun la cantidad de clases que tenemos
            String temporal = totalClases.get(i); //el temporal es para saber que clase evaluamos
            contadoresPromedio[i] = 0;

            for (int j = 0; j < instancias.size(); j++) {  //siempre vamos a evaluar todas las instancias de nuestro arraylist de patrones
                String comparar = instancias.get(j).getClase();

                if (temporal.equals(comparar)) { //si la instancia que evaluamos es igual a nuestro temporal
                    contadoresPromedio[i]++;//aumentamos en su contador respectivo
                    for (int y = 0; y < tam_vector; y++) { //sumamos cada columa "y" a su respectiva clase, haciendo la resta que lleva la varianza
                        matriz_vectores[i][y] += pow((instancias.get(j).getVectorC()[y]) - (vectoresPromedio.get(i).getVectorC()[y]), 2);
                    }
                }
            }
        }
        //crearemos un ciclo para sacar el promedios de una vez, y mandar su respectivo vector  
        for (int i = 0; i < n_clases; i++) {
            double[] vectorFinal = new double[tam_vector];
            for (int y = 0; y < tam_vector; y++) {
                vectorFinal[y] = matriz_vectores[i][y] / (contadoresPromedio[i] - 1); //Aqui no es en si el promedio porque es entre N-1;
            }
            vectoresVarianza.add(new Patron(totalClases.get(i), "", vectorFinal));
        }

        return vectoresVarianza;

    }

    public double obtenerEvidencia() {
        double evidencia = 0;
        double temporal;
        int tam_vector = vectoresDesviacion.get(0).getVectorC().length;
        double n_VectorDesviacion = vectoresDesviacion.size();

        for (int i = 0; i < n_VectorDesviacion; i++) {
            temporal = contadoresPriori.get(i)/nInstancias; //Sacamos proPriori
            
            for (int j = 0; j < tam_vector; j++) {
                temporal = temporal * vectoresDesviacion.get(i).getVectorC()[j];
            }
            evidencia = evidencia + temporal;
        }

        return evidencia;
    }

    public ArrayList<Patron> sacarDistribucionNormal(Patron instancia) {

        //Sacamos las distribuciones
        int n_clases = totalClases.size();
        int tam_vector = vectoresPromedio.get(0).getVectorC().length;
        double[][] matriz_vectores = new double[n_clases][tam_vector];

        for (int i = 0; i < n_clases; i++) {      //vamos a hacer esto segun la cantidad de clases que tenemos
            for (int j = 0; j < tam_vector; j++) { //a cada columna de la clase, le sacamos la desviacion
                matriz_vectores[i][j] = (1 / (sqrt(2 * Math.PI * (vectoresVarianza.get(i).getVectorC()[j])))) * pow(Math.E, (-1)
                        * (pow((instancia.getVectorC()[j] - vectoresPromedio.get(i).getVectorC()[j]), 2)) / (2 * (vectoresVarianza.get(i).getVectorC()[j])));
            }
            vectoresDistibucion.add(new Patron(totalClases.get(i), "", matriz_vectores[i]));

        }
        return vectoresDistibucion;
    }

    public void cambiaClasificacion(Patron instancia) {

        //Sacamos las probabilidades posteriori
        double[] probsPosteriorii = new double[totalClases.size()];
        int tam_vector = instancia.getVectorC().length;
        double n_clases = totalClases.size();
        double temporal = 1;
        
        
        for (int i = 0; i < n_clases; i++) {
            temporal = contadoresPriori.get(i)/nInstancias; //Sacamos proPriori
            for (int j = 0; j < tam_vector; j++) {
                temporal = temporal * vectoresDistibucion.get(i).getVectorC()[j];

            }

            temporal = temporal / evidenciaBayes;
            //System.out.println("Prob priori final " + i + " --> " + temporal);
            probsPosteriorii[i] = temporal;
            temporal = 0;

        }

        //sacamos cual es el mayor y su posicion
        double mayor = probsPosteriorii[0];
        int posMayor = 0;
        for (int x = 1; x < totalClases.size(); x++) {
            if (probsPosteriorii[x] > mayor) {
                mayor = probsPosteriorii[x];
                posMayor = x;

            }
        }

        instancia.setClaseResultante(vectoresDistibucion.get(posMayor).getClase());
        System.out.println("Clase resultante: " + instancia.getClaseResultante() + "  era: " + instancia.getClase());

        //limpiamos donde guardamos probabilidades
        for (int x = 0; x < probsPosteriorii.length; x++) {
            probsPosteriorii[x] = 0;
        }

        //limpiamos ArrayList de distribuciones, pq cambian para cada patron
        vectoresDistibucion.clear();

    }

    public double eficacia(ArrayList<Patron> este) {
        int n = 0;
        for (int k = 0; k < este.size(); k++) {
            if (este.get(k).getClaseResultante().equals(este.get(k).getClase())) {
                n++;
            } else {
                System.out.println("Instancia " + k + " mal clasificada");
            }
        }
        System.out.println("");
        System.out.println("Aciertos de Bayes: " + n);
        System.out.println("Total de Elemnentos: " + este.size());
        return (n * 100) / ((double)este.size());
    }

    public ArrayList<Patron> getVectoresPromedio() {
        return vectoresPromedio;
    }

    public void setVectoresPromedio(ArrayList<Patron> vectoresPromedio) {
        this.vectoresPromedio = vectoresPromedio;
    }

    public ArrayList<Patron> getVectoresVarianza() {
        return vectoresVarianza;
    }

    public void setVectoresVarianza(ArrayList<Patron> vectoresVarianza) {
        this.vectoresVarianza = vectoresVarianza;
    }

    public ArrayList<Patron> getVectoresDesviacion() {
        return vectoresDesviacion;
    }

    public void setVectoresDesviacion(ArrayList<Patron> vectoresDesviacion) {
        this.vectoresDesviacion = vectoresDesviacion;
    }

    public ArrayList<Patron> getRepresentativos() {
        return representativos;
    }

    public void setRepresentativos(ArrayList<Patron> representativos) {
        this.representativos = representativos;
    }

    public ArrayList<String> getTotalClases() {
        return totalClases;
    }

    public void setTotalClases(ArrayList<String> totalClases) {
        this.totalClases = totalClases;
    }

    public ArrayList<Patron> getVectoresProbPriori() {
        return vectoresProbPriori;
    }

    public void setVectoresProbPriori(ArrayList<Patron> vectoresProbPriori) {
        this.vectoresProbPriori = vectoresProbPriori;
    }

    public ArrayList<Patron> getVectoresDistibucion() {
        return vectoresDistibucion;
    }

    public void setVectoresDistibucion(ArrayList<Patron> vectoresDistibucion) {
        this.vectoresDistibucion = vectoresDistibucion;
    }

    public ArrayList<Patron> getPatronePosteriori() {
        return patronePosteriori;
    }

    public void setPatronePosteriori(ArrayList<Patron> patronePosteriori) {
        this.patronePosteriori = patronePosteriori;
    }

    public double getEvidenciaBayes() {
        return evidenciaBayes;
    }

    public void setEvidenciaBayes(double evidenciaBayes) {
        this.evidenciaBayes = evidenciaBayes;
    }

}
