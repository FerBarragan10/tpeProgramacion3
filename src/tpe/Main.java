package tpe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String args[]) {
		Servicios servicios = new Servicios("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");
        servicios.mostrarDatos();
        String ID="t4".toUpperCase();
       
        //llamado al servicio 1 donde se devuelve una tarea
//        System.out.println("la tarea con id==> " + ID +", es "+ servicios.servicio1(ID));
        
        
        List<Tarea> filtroTareas = servicios.servicio2(true); // Llamando al metodo servicio2 donde se obtiene una lista de tareas segun si se 

        // Opcion 1: Imprimir cada tarea por separado
//    	System.out.println("servicio 2 tareas criticas o no");

//        for (Tarea tarea : filtroTareas) {
//            System.out.println(tarea); 
//        }
        
        int prioridadMinima=13;
        int prioridadMaxima=65;
        List<Tarea> filtroTareasPorPrioridad = servicios.servicio3(prioridadMinima,prioridadMaxima); // Llamando al metodo servicio2 donde se obtiene una lista de tareas segun si se 

        // Opcion 1: Imprimir cada tarea por separado
//    	System.out.println("servicio 3 por prioridad");
//        for (Tarea tarea : filtroTareasPorPrioridad) {
//            System.out.println(tarea); 
//        }
        
        
      
        
	}
}