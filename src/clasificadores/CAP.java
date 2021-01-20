/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;
import data.Patron;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author COQUE
 */
public class CAP {

    int clases;
    ArrayList<Patron> patrones;
    ArrayList<Patron> nuevospatrones;
    double[][] clasbin;
    double[] promedio;
    double[][] matrizC;
    ArrayList<String> totalClases = new ArrayList<>();

    public CAP() {
    }

    public CAP(ArrayList<Patron> patrones) {
        this.clases = num_clases(patrones);
        this.patrones = patrones;
        this.clasbin = crearbin(this.clases);
        this.promedio = promedio(patrones);
        this.nuevospatrones = traslacion(patrones);

    }

    public void aprendizaje() {
        asignacionvecbin();
        //this.matrizC = new double[this.clases][nuevospatrones.get(0).getVectorC().length];

        for (int i = 0; i < nuevospatrones.size(); i++) {
            totalClases.add(nuevospatrones.get(i).getClase());
        }

        Set<String> hashSet = new HashSet<String>(totalClases);
        totalClases.clear();
        totalClases.addAll(hashSet);
        double sum = 0;
        double[][] r = new double[totalClases.size()][this.nuevospatrones.get(0).getVectorC().length];

        for (int i = 0; i < totalClases.size(); i++) {
            for (int j = 0; j < nuevospatrones.size(); j++) {
                if (nuevospatrones.get(j).getClase().equals(totalClases.get(i))) {
                    for (int x = 0; x < nuevospatrones.get(0).getVectorC().length; x++) {
                        r[i][x] += nuevospatrones.get(j).getVectorC()[x];
                    }
                }
            }
        }
        this.matrizC = r;

    }

    public void recuperacion(Patron Consulta) {
        double[] x = new double[Consulta.getVectorC().length];

        double[][] t = new double[matrizC.length][this.clases];
        double sum = 0;

        for (int s = 0; s < 1; s++) {
            for (int j = 0; j < this.clases; j++) {
                for (int k = 0; k < x.length; k++) { //valor del X
                    t[s][j] += (Consulta.getVectorC()[k] * matrizC[j][k]);
                }
            }
        }

        double[] nB = new double[this.clases];

        for (int i = 0; i < nB.length; i++) {
            nB[i] = t[0][i];
        }
        double mayor = 0;

        for (int y = 0; y < nB.length; y++) {
            if (mayor < nB[y]) {
                mayor = nB[y];
            }
        }
        for (int y = 0; y < nB.length; y++) {
            if (mayor == nB[y]) {
                nB[y] = 1;
            } else {
                nB[y] = 0;
            }
        }
        Consulta.setVectorBinres(nB);

        for (int r = 0; r < this.patrones.get(0).getVectorBinres().length; r++) {
            if (Consulta.getVectorBinres()[r] == 1) {
                Consulta.setClaseResultante(this.totalClases.get(r));
            }
        }

    }

    public void asignarResult() {

    }

    public double[] promedio(ArrayList<Patron> instancias) {
        double[] promedios = new double[instancias.get(0).getVectorC().length];

        for (int i = 0; i < promedios.length; i++) {
            for (int j = 0; j < instancias.size(); j++) {
                promedios[i] += instancias.get(j).getVectorC()[i];
            }
        }
        for (int i = 0; i < promedios.length; i++) {

            promedios[i] = promedios[i] / instancias.size();

        }
        return promedios;
    }

    public ArrayList<Patron> traslacion(ArrayList<Patron> instancias) {
        ArrayList<Patron> np = instancias;

        for (int i = 0; i < np.size(); i++) {
            for (int j = 0; j < np.get(0).getVectorC().length; j++) {
                np.get(i).getVectorC()[j] -= promedio[j];
            }
        }

        return np;
    }

    public int num_clases(ArrayList<Patron> instancias) {
        ArrayList<String> totalClases = new ArrayList<>();

        for (int i = 0; i < instancias.size(); i++) {
            totalClases.add(instancias.get(i).getClase());
        }

        Set<String> hashSet = new HashSet<String>(totalClases);
        totalClases.clear();
        totalClases.addAll(hashSet);

        return totalClases.size();

    }

    public double[][] crearbin(int n) {
        double[][] clas = new double[this.clases][this.clases];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clas[i][j] = 0;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    clas[i][j] = 1;
                } else {
                    clas[i][j] = 0;
                }
            }

        }

        return clas;

    }

    public void asignacionvecbin() {
        ArrayList<String> totalClases = new ArrayList<>();

        for (int i = 0; i < nuevospatrones.size(); i++) {
            totalClases.add(nuevospatrones.get(i).getClase());
        }

        Set<String> hashSet = new HashSet<String>(totalClases);
        totalClases.clear();
        totalClases.addAll(hashSet);

        for (int i = 0; i < totalClases.size(); i++) {
            for (int j = 0; j < nuevospatrones.size(); j++) {
                if (totalClases.get(i).equals(nuevospatrones.get(j).getClase())) {
                    double[] vb = new double[clasbin.length];
                    for (int r = 0; r < clasbin.length; r++) {
                        vb[r] = clasbin[i][r];
                    }
                    nuevospatrones.get(j).setVectorBin(vb);
                }
            }
        }

    }

    public double eficacia(ArrayList<Patron> este) {
        double n = 0;
        for (int k = 0; k < este.size(); k++) {
            if (este.get(k).getClase().equals(este.get(k).getClaseResultante())) {
                n++;
            }
        }
        System.out.println("Aciertos CAP: " + n);
        System.out.println("Total de Elemnentos: " + este.size());
        return (n * 100) / este.size();
    }
}