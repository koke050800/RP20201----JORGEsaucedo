/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import data.Patron;
import interfaces.ClasificadorSupervisado;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author KOKE
 */
public class MinimaDistancia implements ClasificadorSupervisado {

    ArrayList<Patron> representativos;
    ArrayList<String> totalClases;

    public MinimaDistancia() {
        this.representativos = new ArrayList<>();
        this.totalClases = new ArrayList<>();
    }

    /*public MinimaDistancia(ArrayList<Patron> representativos) {
        this.representativos = representativos;
    }*/
    @Override
    public void entrenar(ArrayList<Patron> instancias) {

        ArrayList<Double> prom = new ArrayList<>();

        for (int i = 0; i < instancias.size(); i++) {
            totalClases.add(instancias.get(i).getClase());
        }

        Set<String> hashSet = new HashSet<String>(totalClases);
        totalClases.clear();
        totalClases.addAll(hashSet);

        int n_clases = totalClases.size();
        int tam_vector = instancias.get(0).getVectorC().length;
        double[][] matriz_vectores = new double[n_clases][tam_vector];
        double[] contadoresPromedio = new double[n_clases];

        for (int i = 0; i < n_clases; i++) {      //vamos a hacer esto segun la cantidad de clases que tenemos
            String temporal = totalClases.get(i); //el temporal es para saber que clase evaluamos
            contadoresPromedio[i] = 0;

            for (int j = 0; j < instancias.size(); j++) {  //siempre vamos a evaluar todas las instancias de nuestro arraylist de patrones
                String comparar = instancias.get(j).getClase();

                if (temporal.equals(comparar)) { //si la instancia que evaluamos es igual a nuestro temporal
                    contadoresPromedio[i]++;//aumentamos en su contador respectivo
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
            representativos.add(new Patron(totalClases.get(i), "", vectorFinal));
        }
    }

    public ArrayList<Patron> getRepresentativos() {
        return representativos;
    }

    public void setRepresentativos(ArrayList<Patron> representativos) {
        this.representativos = representativos;
    }

    public static double calcularDistanciaEuclidiana(Patron a, Patron b) {
        double aux = 0;
        for (int x = 0; x < a.getVectorC().length; x++) {
            aux += Math.pow((a.getVectorC()[x] - b.getVectorC()[x]), 2);
        }
        return Math.sqrt(aux);

    }

    public void clasificar(Patron comparaPatron) {

        double menorDis = 0;
        double actualDis = 0;
        int n_clase = 0;

        menorDis = calcularDistanciaEuclidiana(this.representativos.get(0), comparaPatron);//patron representativo 0, sera comparado con respecto al patron enviado por el usuario

        for (int i = 1; i < this.representativos.size(); i++) { //ya usamos la pos 0

            actualDis = calcularDistanciaEuclidiana(this.representativos.get(i), comparaPatron);

            if (actualDis < menorDis) {
                menorDis = actualDis;
                n_clase = i;
            }

        }        

        comparaPatron.setClaseResultante(totalClases.get(n_clase));
    }

    public double eficacia(ArrayList<Patron> este) {
        int n = 0;
        for (int k = 0; k < este.size(); k++) {
            if (este.get(k).getClaseResultante().equals(este.get(k).getClase())) {
                n++;
            }
        }
        System.out.println("Total de patrones analizados: " + este.size());
        System.out.println("Aciertos aplicando Minima Distancia: " + n);
        return (n * 100) / ((double)este.size());
    }

}
