package tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tpe.utils.CSVReader;



/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {
	 private HashMap<String,Procesador> procesadores;
	    private HashMap<String,Tarea> tareas;
	    private LinkedList<Tarea> listaTareasCriticas;
	    private LinkedList<Tarea> listaTareasNoCriticas;

	    
	public Servicios(String pathProcesadores, String pathTareas){
		CSVReader reader = new CSVReader();
        this.tareas = new HashMap<>();
        this.procesadores = new HashMap<>();
        this.listaTareasCriticas=new LinkedList<>();
        this.listaTareasNoCriticas=new LinkedList<>();


       reader.readTasks(pathTareas,this.tareas,listaTareasCriticas,listaTareasNoCriticas);
		reader.readProcessors(pathProcesadores,this.procesadores);


		  int limiteTareasCriticas = 2; // Define el limite de tareas criticas por procesador
	      int limiteTiempoNoRefrigerado = 100; // Define el limite de tiempo para procesadores no refrigerados

	        // Crea una instancia de BacktrackingAssignment
	      BacktrackingAssignment asignador = new BacktrackingAssignment();
	        // Llama al metodo para encontrar las mejores asignaciones
	        
	      List<Procesador> mejoresAsignaciones = asignador.encontrarMejoresAsignaciones(new ArrayList<>(procesadores.values()), new ArrayList<>(tareas.values()), limiteTareasCriticas, limiteTiempoNoRefrigerado);

	      System.out.println("las mejores asignaciones son: " +mejoresAsignaciones);
	        	
	        // Crea una instancia de GreedyAssignment
		  GreedyAssignment asignadorGreddy = new GreedyAssignment();
		    // Llama al metodo para encontrar las mejores asignaciones
		        
		  List<Procesador> mejorAsignacion= asignadorGreddy.asginarGreedy(new ArrayList<>(procesadores.values()), new ArrayList<>(tareas.values()), limiteTareasCriticas, limiteTiempoNoRefrigerado);

		  System.out.println("las mejor asignacion en greedy es : " +mejorAsignacion);
	      
	}
	
	
	
	
	
	
	
	 public void mostrarDatos() {   
//	            System.out.println("Tareas criticas:");
//            
//	            for(Tarea lista:listaTareasCriticas) {
//	            	  System.out.println("Id: " + lista.getId());
//		                System.out.println("Nombre: " + lista.getNombre());
//		                System.out.println("Tiempo: " + lista.getTiempo());
//		                System.out.println("Critica: " + lista.isCritica());
//		                System.out.println("Prioridad: " + lista.getPrioridad());
//		         }
//	            System.out.println("Tareas NO criticas:");
//
//	            for(Tarea listaNoCriticas:listaTareasNoCriticas) {
//	            	  System.out.println("Id: " + listaNoCriticas.getId());
//		                System.out.println("Nombre: " + listaNoCriticas.getNombre());
//		                System.out.println("Tiempo: " + listaNoCriticas.getTiempo());
//		                System.out.println("Critica: " + listaNoCriticas.isCritica());
//		                System.out.println("Prioridad: " + listaNoCriticas.getPrioridad());
//		         }
	 }

	//la complejidad para este servicio es O(1)

	public Tarea servicio1(String ID) {
		return tareas.get(ID);
	}
    
	//la complejidad para este servicio es O(1)

	public List<Tarea> servicio2(boolean esCritica) {
		if(esCritica) {
			return this.listaTareasCriticas;
		}
		else {
			return this.listaTareasNoCriticas;
		}
	}
		
//la complejidad para este servicio es O(n)
	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        List<Tarea> filtroTareasPorPrioridad = new ArrayList<>();
        for (Entry<String, Tarea> entry : tareas.entrySet()) {
        	if(entry.getValue().getPrioridad()>=prioridadInferior && entry.getValue().getPrioridad()<=prioridadSuperior) {
        		filtroTareasPorPrioridad.add(entry.getValue());
        	}                                                                                                                                         
        }

	return filtroTareasPorPrioridad;
	}
}
