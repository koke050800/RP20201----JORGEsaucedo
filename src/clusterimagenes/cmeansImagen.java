/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clusterimagenes;

import data.Patron;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;


public class cmeansImagen{
    private int c;              // se declaran los 3 atributos de los que ocupará la clase 
    // el c que es para saber con cuantos grupos se agrupará 
    private ArrayList<PatronImagen> representativos = new ArrayList();
    // el arraylist de representativos para saber a donde se iran agrupando
    private ArrayList<PatronImagen> patrones;   
    // el arraylist de patrones para tomar los valores a clusterizar 
    public cmeansImagen(int c) {
        this.c = c;
    }
    // el constructor solo toma el valor del número de grupos
    
    public void entrenar(ArrayList<PatronImagen> instancias) {
        this.patrones = (ArrayList<PatronImagen>) instancias.clone();
    }
    // la función de entrenar solo toma las instancias de tipo PatronImagen para poder asignarles a qué grupo pertenecen 

    // la función clasificar
    public void clasificar() {
       ArrayList<PatronImagen> comparar;  // ocupa dos arraylist de tipo PatronImagen, para hacer comparaciones 
       ArrayList<PatronImagen> comparar2;   // hasta llegar a que sean iguales y se corte la iteración
       double distancia = 1;            // la distancia que ayuda a comparar
       double comparacion;              // la comparacion que resulta del primer arraylist 
       double com;                      // la com que resulta del segundo arraylist 
       int contador=0;                  // un contador que nos ayuda a ver cuántas veces entra al while
       generarRepresentativosIn();          // primero se manda a llamar a la función que genera aleatoriamente los primeros representativos
       
       asignarClase(this.representativos);      // y asignamos a un grupo todas las instancias que queremos agrupar, de acuerdo a los primeros 
       while(distancia!=0){                         // representativos. 
                System.out.println(contador++);     // iniciamos la iteración y no para hasta que la distancia no sea igual a 0
                comparacion=0;                          
                com = 0;
                comparar = this.representativos;            // al primer arraylist le asignamos los primeros representativos
                for(int i=0; i<this.representativos.size(); i++){               // calculamos el valor del vector de sus representativos
                    for(int j = 0; j<this.representativos.get(i).getVectorC().length; j++){
                        comparacion += comparar.get(i).getVectorC()[j];
                    }  
                }
                asignarClase(comparar);             // asignamos la nueva clase a los patrones
                comparar2 = encontrarNuevoRepresentativo();         // encontramos los nuevos representativos 
                for(int i=0; i<comparar2.size(); i++){                              // calculamos el valor del vector de los nuevos 
                    for(int j = 0; j<comparar2.get(i).getVectorC().length; j++){                // representativos 
                        com += comparar2.get(i).getVectorC()[j];
                    }  
                }
                
                this.representativos = (ArrayList<PatronImagen>) comparar2.clone();         // igualamos a los representativos los nuevos generados
                distancia = Math.abs(comparacion - com);   // hacemos una resta entre el resultado de comparación y com, y si resulta ser cero
                                                            // es porque son iguales los representativos, entonces se corta la iteración
                
        }
    }

    private void generarRepresentativosIn() {
        
        Random ran = new Random();                          // para generar los representativos iniciales 
        int aux = ran.nextInt(this.patrones.size());        // solo generamos un numero random que puede agarrar entre el tamaño de los patrones
         //this.representativos.add(patrones.get(aux));
        while(this.c!=0){                                   // hacemos un while, que termine hasta que c sea igual a cero 
            if(!buscaPatron(patrones.get(aux))){                // si el patron que se quiere crear ya existe, vuelve a iterar
                aux = ran.nextInt(this.patrones.size());            // sino, se genera el nuevo representativo
                this.representativos.add(new PatronImagen(0,0,patrones.get(aux).getVectorC()));         // se agrega a los representativos
                this.c--;       // y se resta en uno el valor de c
            }
        }
        
    }

    public ArrayList<PatronImagen> getRepresentativos() {
        return representativos;             // para obtener los representativos
    }

    private ArrayList<PatronImagen> encontrarNuevoRepresentativo() {
        
        ArrayList<PatronImagen> nuevo = new ArrayList();
        HashMap<Integer,String> entrenador = new HashMap();         // para encontrar el nuevo representativo
         int prom=0;                                            // ocupamos de estos atributos
         int n1;
         int con = 1;
        double[] vector = new double[this.patrones.get(0).getVectorC().length];      
        for(int x=0; x<this.patrones.size();x++){                               // corremos por todo la lista de patrones, para 
            if(!entrenador.containsValue(this.patrones.get(x).getClase())){         // saber cuantas clases hay 
                entrenador.put(con, this.patrones.get(x).getClase());                   // y las agregamos a un hashmap 
                con++;
            } 
        }
        Iterator<Integer> iterador = entrenador.keySet().iterator();
        while(iterador.hasNext()){                              // para sacar los promedios
            Integer llave = iterador.next();                        // iteramos el hashmap 
            for(int x=0; x<this.patrones.size();x++){                               // y lo comparamos con la lista de patrones 
                if(entrenador.get(llave).equals(this.patrones.get(x).getClase())){          // y vamos contando cuantos patrones hay de cada clase
                    prom++;                                                     // se va incrementando el número de instancias con ese grupo
                    for(int y=0; y<vector.length;y++){
                        vector[y]+=this.patrones.get(x).getVectorC()[y];            // y se va agregando a un vector 
                        
                    }
                }
            }
                for(int j = 0; j<vector.length; j++){               // ahora hacemos la división de lo que entró al patrón entre el número de 
                    vector[j] = vector[j]/prom;                     // instancias que tuvieron esa clase 
                }
                nuevo.add(new PatronImagen(0,0,vector));            // y se agrega el nuevo representativo 
               
                vector = new double[this.patrones.get(0).getVectorC().length];      // hacemos a cero para volver a empezar
                prom = 0;                                                           // hasta que ya no halla nada en el hashmap
        } 
        return nuevo;       // y regresamos la nueva arraylist de representativos
    }
     
    private void asignarClase(ArrayList<PatronImagen> instancias) {
        
        for(int i = 0; i<patrones.size(); i++){                         // para asignar clase, recorremos toda la arraylist de patrones
        double menor =  instancias.get(0).calcularDistancia(patrones.get(i));   // declaramos un menor, y le asignamos la primer instancia 
        int n = 0;                                                              // de los representativos con la distancia a cada patrón
        for(int k = 1; k<instancias.size(); k++){                           // recorremos todos los reprentativos                        
            double numeroActual = instancias.get(k).calcularDistancia(this.patrones.get(i));    // y el número actual es el del representativo 
            if (numeroActual < menor) {                                                         // en el que se encuentra la iteración
            menor = numeroActual;                       // si el numero actul es menor que el menor
            n = k;                      // entonces cambia a ser el menor el que está corriendo en la iteración
            }
        }
      
       patrones.get(i).setClase(instancias.get(n).getClase());  // al final se le asigna la clase del patron con el valor de n
       }
    }

    private boolean buscaPatron(PatronImagen aux) {
        for(int i=0; i<this.representativos.size(); i++){           // se busca si el patrón está repetido
            if(this.representativos.get(i).getVectorC().equals(aux)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<PatronImagen> getPatrones() {              // para regresar la lista de patrones 
        return patrones;
    }
    
    
}
