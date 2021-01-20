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
public class BurbujaOptimizado {
      
   
    public long tInicio;
    public long tFinal;
    public long tTotal;

    public BurbujaOptimizado() {
        this.tFinal = 0;
        this.tInicio = 0;
        this.tTotal = 0 ;
    }
  
     public long gettFinal() {
        return tFinal;
    }

    public long gettInicio() {
        return tInicio;
    }

    public long gettTotal() {
        return tTotal;
    }

   public DistInsta[] ordenarLists(DistInsta[] datos) {
  
      double AUX;
      int N=0;
      int bandera=1;
       String auxclase;
      for(int paso=0 ; paso< (datos.length-1) && bandera==1 ;paso++) {
	bandera=0;
	for(int j=0;j<(datos.length-paso-1) ;j++){
		if(datos[j].getDistancia()>datos[j+1].getDistancia())
		  {
			bandera=1; /* indica si se han realizados cambios o no */
			AUX=datos[j].getDistancia();
			datos[j].setDistancia(datos[j+1].getDistancia());
			datos[j+1].setDistancia(AUX);
                        auxclase =datos[j].getClase();
			datos[j].setClase(datos[j+1].getClase());
			datos[j+1].setClase(auxclase);
		  }
	  }
         }
      return datos;
    }   
  
}
