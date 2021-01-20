/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import data.BurbujaOptimizado;
import data.DistInsta;
import data.MatrizConf;
import data.Patron;
import interfaces.ClasificadorSupervisado;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Dell
 */
public class KNN implements ClasificadorSupervisado {

    private ArrayList<Patron> patrones;
    private DistInsta[] distancias;
    private DistInsta[] listaordenada;
    int K;
    ArrayList<String> totalClases;
    private MatrizConf matriz;

    public KNN(int k) {
        this.patrones = new ArrayList<>();
        this.K = k;
        this.matriz = null;
    }

    @Override
    public void entrenar(ArrayList<Patron> instancias) {

        for (int i = 0; i < instancias.size(); i++) {
            patrones.add(instancias.get(i));
        }
        this.distancias = new DistInsta[this.patrones.size()];
        this.totalClases = new ArrayList(this.patrones.size());
    }

    @Override
    public void clasificar(Patron comparaPatron) {

        sacarListado(comparaPatron);
        BurbujaOptimizado bo = new BurbujaOptimizado();
        listaordenada = bo.ordenarLists(distancias);

        //System.out.println("La clase resultante: " + asignarClase_profe() + " y era: " + comparaPatron.getClase());
        comparaPatron.setClaseResultante(asignarClase_profe());
    }

    public String asignarClase_profe() {
        String clase = "";

        for (int i = 0; i < patrones.size(); i++) {
            totalClases.add(patrones.get(i).getClase());
        }

        Set<String> hashSet = new HashSet<String>(totalClases);
        totalClases.clear();
        totalClases.addAll(hashSet);
        int[] contadores = new int[totalClases.size()];

        for (int i = 0; i < listaordenada.length - 1; i++) {

            for (int j = 0; j < totalClases.size(); j++) {
                if (listaordenada[i].getClase().equals(totalClases.get(j))) {
                    contadores[j] += 1;
                    if (contadores[j] == K) {
                        clase = totalClases.get(j);
                        return clase;

                    }
                }

            }
        }

        return clase;
    }

    private void sacarListado(Patron patron) {
        // recorrer el conjunto de entrenamiento y calcular las distancias
        int x = 0;
        for (Patron p : this.patrones) {

            this.distancias[x] = new DistInsta(p.getClase(), p, patron);

            x++;
        }
    }

    public String getPatrones(int i) {
        return patrones.get(i).getClase();
    }

    public double eficacia(ArrayList<Patron> este) {
        int n = 0;
        for (int k = 0; k < este.size(); k++) {
            if (este.get(k).getClaseResultante().equals(este.get(k).getClase())) {
                n++;
            }
        }
        System.out.println("Total de patrones analizados: " + este.size());
        System.out.println("Aciertos aplicando KNN: " + n);
        return (n * 100) / ((double)este.size());
    }

    public void clasificar(ArrayList<Patron> patrones) {
        for (Patron p : patrones) {
            clasificar(p);
        }
        this.matriz = new MatrizConf(patrones);
    }

    /**
     * @return the mc
     */
    public MatrizConf getMc() {
        return matriz;
    }

}
