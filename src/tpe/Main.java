package tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {
	private static HashMap<String,Procesador> procesadores;
	private static HashMap<String,Tarea> tareas;

	public static void main(String args[]) {
		Servicios servicios = new Servicios("./src/tpe/datasets/Procesadores.csv", "./src/tpe/datasets/Tareas.csv");
        tareas = servicios.obtenerTareas();
        procesadores = servicios.obtenerProcesadores();
        //servicios.mostrarDatos();
        
		

		  int limiteTareasCriticas = 2; // Define el limite de tareas criticas por procesador
	      int limiteTiempoNoRefrigerado = 100; // Define el limite de tiempo para procesadores no refrigerados

	 
	      BacktrackingAssignment asignador = new BacktrackingAssignment();
	     
	        
	      List<Procesador> mejoresAsignaciones = asignador.encontrarMejoresAsignaciones(new ArrayList<>(procesadores.values()), new ArrayList<>(tareas.values()), limiteTareasCriticas, limiteTiempoNoRefrigerado);

	      System.out.println("las mejores asignaciones son: " +mejoresAsignaciones);
	        	
	  
		  GreedyAssignment asignadorGreddy = new GreedyAssignment();

		        
		  List<Procesador> mejorAsignacion= asignadorGreddy.asginarGreedy(new ArrayList<>(procesadores.values()), new ArrayList<>(tareas.values()), limiteTareasCriticas, limiteTiempoNoRefrigerado);

		  System.out.println("las mejor asignacion en greedy es : " +mejorAsignacion);
    
	}
//	public void mostrarDatos() {   
//		System.out.println("Tareas criticas:");
//		
//		for(Tarea lista:listaTareasCriticas) {
//			System.out.println("Id: " + lista.getId());
//			System.out.println("Nombre: " + lista.getNombre());
//			System.out.println("Tiempo: " + lista.getTiempo());
//			System.out.println("Critica: " + lista.isCritica());
//			System.out.println("Prioridad: " + lista.getPrioridad());
//		}
//		System.out.println("Tareas NO criticas:");
//		
//		for(Tarea listaNoCriticas:listaTareasNoCriticas) {
//			System.out.println("Id: " + listaNoCriticas.getId());
//			System.out.println("Nombre: " + listaNoCriticas.getNombre());
//			System.out.println("Tiempo: " + listaNoCriticas.getTiempo());
//			System.out.println("Critica: " + listaNoCriticas.isCritica());
//			System.out.println("Prioridad: " + listaNoCriticas.getPrioridad());
//		}
//	}
}