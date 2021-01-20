/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rp20201a;

import clasificadores.Bayes;
import clasificadores.CAP;
import clasificadores.Cmeans;
import clasificadores.KNN;
import clasificadores.MinimaDistancia;
import clusterimagenes.generarPatrones;
import data.Patron;
import data.LeerDatos;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;

/**
 *
 * @author working
 */
public class RP2021A {

    public static void main(String[] args) throws IOException {

        
        ArrayList<Patron> patronesLista = LeerDatos.tokenizarDataSet();
        
        
        CAP cap = new CAP(patronesLista);
        cap.aprendizaje();

        for (int j = 0; j < patronesLista.size(); j++) {
            System.out.println("***** Instancia " + j + " *****");
            cap.recuperacion(patronesLista.get(j));
            System.out.println("");
        }
        
        System.out.println("Eficacia CAP: "+ String.format("%.2f", cap.eficacia(patronesLista)) + "%");
        System.out.println("");


        
       /* generarPatrones nuevo = new generarPatrones();
        nuevo.abrir();
        nuevo.clusterizar(500);*/
        
        //ArrayList<Patron> patronesListaEntrenar = LeerDatos.tokenizarDataSet();
        //ArrayList<Patron> patronesLista = LeerDatos.tokenizarDataSet();
        //MinimaDistancia mn = new MinimaDistancia();
        //mn.entrenar(patronesListaEntrenar);
        //KNN kn;
        
       // Cmeans c = new Cmeans(3);
        //c.calsificar(patronesLista);

        /*Bayes b = new Bayes();
        System.out.println("***** Clasificador Bayes *****");
        b.entrenar(patronesLista);
        for (int j = 0; j < patronesLista.size(); j++) {
            System.out.println("***** Instancia " + j + " *****");
            b.clasificar(patronesLista.get(j));
            System.out.println("");
        }
        System.out.println("Eficacia Bayes: " + String.format("%.2f", b.eficacia(patronesLista)) + "%");
        System.out.println("");
        
        for (int i = 1; i <= 10; i++) {
            KNN kn = new KNN(i);
            System.out.println("***** Clasificador KNN con k = " + i + " *****");
            kn.entrenar(patronesLista);
            for (int j = 0; j < patronesLista.size(); j++) {
                kn.clasificar(patronesLista.get(j));
            }
            System.out.println("Eficacia KNN: "+ String.format("%.2f", kn.eficacia(patronesLista)) + "%");
            System.out.println("");
        }

       
        System.out.println("***** Clasificador Minima Distancia *****");
        mn.entrenar(patronesLista);
        for (int j = 0; j < patronesLista.size(); j++) {
            mn.clasificar(patronesLista.get(j));
        }
        System.out.println("Eficacia Minima Distancia: " + String.format("%.2f", mn.eficacia(patronesLista))  + "%");
        System.out.println("");*/



/*
        for (int i = 0; i < patronesLista.size(); i++) {            
            mn.clasificar(patronesLista.get(i));
        }
        System.out.println("***** Clasificador Minima Distancia *****");
        System.out.println("Eficacia MD: " + mn.eficacia(patronesLista) + "%");*/
        
        
        
        
        

    }
}
