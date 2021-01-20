package clasificadores;

import data.Patron;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Cmeans {
    
    private ArrayList<Patron> patronesCentros;
    private ArrayList<Patron> patronesCentrosNuevos;
    private int n_clases;

    
    public Cmeans(int c) { 
        this.patronesCentros = new ArrayList<>();
        this.patronesCentrosNuevos = new ArrayList<>();
        this.n_clases = c;
    }


    public void calsificar(ArrayList<Patron> instancias) {
        
        crearPrimerosRepresentativos(instancias);
        System.out.println("----- Primeros centros ------");
        for (int i = 0; i < patronesCentros.size(); i++) {
            System.out.println("Centro " + i + " = " + patronesCentros.get(i).getVectorC()[0] + ", " + patronesCentros.get(i).getVectorC()[1] + ", " + patronesCentros.get(i).getVectorC()[2] + ", " + patronesCentros.get(i).getVectorC()[3] + " -- " + patronesCentros.get(i).getClase());
        }
        System.out.println("");

        //Sacamos primeros grupos
        sacarGrupos(instancias);

        //sacamos los nuevos centros para tener que comparar en while
        sacarCentrosNuevos(instancias);
        System.out.println("----- nuevos centros ------");
        for (int i = 0; i < patronesCentrosNuevos.size(); i++) {
            System.out.println("Centro " + i + " = " + patronesCentrosNuevos.get(i).getVectorC()[0] + ", " + patronesCentrosNuevos.get(i).getVectorC()[1] + ", " + patronesCentrosNuevos.get(i).getVectorC()[2] + ", " + patronesCentrosNuevos.get(i).getVectorC()[3] + " -- " + patronesCentrosNuevos.get(i).getClase());
        }
        System.out.println("");



        while (sonCentrosDiferentes()){ 
            
            //los centros nuevos pasan a ser los centros que se usan para sacar grupos
            patronesCentros.clear();
            patronesCentros = (ArrayList<Patron>) patronesCentrosNuevos.clone();

            //sacamos los nuevos grupos
            sacarGrupos(instancias);

            //limpiamos el de los centros nuevos antes de sacar los nuevos
            patronesCentrosNuevos.clear();
            sacarCentrosNuevos(instancias);

            System.out.println("----- nuevos centros ------");
            for (int i = 0; i < patronesCentrosNuevos.size(); i++) {
                System.out.println("Centro " + i + " = " + patronesCentrosNuevos.get(i).getVectorC()[0] + ", " + patronesCentrosNuevos.get(i).getVectorC()[1] + ", " + patronesCentrosNuevos.get(i).getVectorC()[2] + ", " + patronesCentrosNuevos.get(i).getVectorC()[3] + " -- " + patronesCentrosNuevos.get(i).getClase());
            }
            System.out.println("");


        }

        System.out.println("-------------------------------");
        System.out.println("----------- Finales -----------");
        System.out.println("-------------------------------");
        for (int i = 0; i < instancias.size(); i++) {
            System.out.println("Patron " + i + " --> " + instancias.get(i).getClase());
        }

    }

    private boolean sonCentrosDiferentes() {
        // si se determina que son diferentes susituimos a los actuales.
        for (int x = 0; x < patronesCentros.size(); x++) {
            if (!Arrays.equals(patronesCentrosNuevos.get(x).getVectorC(), patronesCentros.get(x).getVectorC())) {
                return true;
            }
        }

        return false;
    }

    public void sacarCentrosNuevos(ArrayList<Patron> instancias) {
        //Despues de eso, sacamos los promedios de cada columna, separando cada una con su clase        
        int tam_vector = instancias.get(0).getVectorC().length;
        double[][] matriz_vectores = new double[n_clases][tam_vector];
        int[] contadoresPromedio = new int[n_clases];

        for (int i = 0; i < n_clases; i++) {      //vamos a hacer esto segun la cantidad de clases que tenemos
            String temporal = patronesCentros.get(i).getClase(); //el temporal es para saber que clase evaluamos
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
            patronesCentrosNuevos.add(new Patron(patronesCentros.get(i).getClase(), "", vectorFinal));
        }

        /*System.out.println("");
        for (int i = 0; i < patronesCentrosNuevos.size(); i++) {
            System.out.println("Patron centro nuevo " + (i) + " = " + patronesCentrosNuevos.get(i).getVectorC()[0]
                    + ", " + patronesCentrosNuevos.get(i).getVectorC()[1] + ", "
                    + patronesCentrosNuevos.get(i).getVectorC()[2] + ", "
                    + patronesCentrosNuevos.get(i).getVectorC()[3] + " -- "
                    + patronesCentrosNuevos.get(i).getClase());
        }*/
    }

    public void sacarGrupos(ArrayList<Patron> instancias) {

        for (int x = 0; x < instancias.size(); x++) {
            //creamos los grupos
            double actualDis;
            double menorDis = calcularDistanciaEuclidiana(instancias.get(x), patronesCentros.get(0));
            int identificadorClase = 0;
            for (int i = 1; i < patronesCentros.size(); i++) {
                actualDis = calcularDistanciaEuclidiana(instancias.get(x), patronesCentros.get(i));

                if (actualDis < menorDis) {
                    menorDis = actualDis;
                    identificadorClase = i;
                }
            }

            //asignamos las diferentes clases
            instancias.get(x).setClase(patronesCentros.get(identificadorClase).getClase());
        }
    }

    public void crearPrimerosRepresentativos(ArrayList<Patron> instancias) {
        Random ran = new Random();
        for (int i = 0; i < n_clases; i++) { //Agregamos 15 patrones aleatorios
            int pos = ran.nextInt(instancias.size());
            patronesCentros.add(instancias.get(pos));
        }

        //quitamos los repetidos
        quitarRepetidos();

        //Si por alguna razon se repitio algun patron
        if (patronesCentros.size() < n_clases) {
            while (patronesCentros.size() < n_clases) { //agregamos y buscamos si no hay algun repetido
                int pos = ran.nextInt(instancias.size());
                patronesCentros.add(instancias.get(pos));
                quitarRepetidos();
            }
        }

        for (int i = 0; i < n_clases; i++) {
            //System.out.println(patronesCentros.get(i).getClase());
            String clase_temp = "Clase " + i;
            // System.out.println("Clase temp: " + clase_temp);
            patronesCentros.get(i).setClase(clase_temp);
            //System.out.println("");
        }

    }

    public void quitarRepetidos() {
        Set<Patron> hashSet = new HashSet<Patron>(patronesCentros);
        patronesCentros.clear();
        patronesCentros.addAll(hashSet);
    }

    public static double calcularDistanciaEuclidiana(Patron a, Patron b) {
        double aux = 0;
        for (int x = 0; x < a.getVectorC().length; x++) {
            aux += Math.pow((a.getVectorC()[x] - b.getVectorC()[x]), 2);
        }
        return Math.sqrt(aux);

    }

}
