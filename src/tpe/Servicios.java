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

	    /*
     * Expresar la complejidad temporal del constructor.
     */
	public Servicios(String pathProcesadores, String pathTareas){
		CSVReader reader = new CSVReader();
        this.tareas = new HashMap<>();
        this.procesadores = new HashMap<>();
        this.listaTareasCriticas=new LinkedList<>();
        this.listaTareasNoCriticas=new LinkedList<>();

//		reader.readProcessors(pathProcesadores);
       reader.readTasks(pathTareas,this.tareas,listaTareasCriticas,listaTareasNoCriticas);
		reader.readProcessors(pathProcesadores,this.procesadores);
//        this.tareas = reader.readTasks(pathTareas);
		
		
		
		
		
		  int limiteTareasCriticas = 2; // Define el límite de tareas críticas por procesador
	        int limiteTiempoNoRefrigerado = 100; // Define el límite de tiempo para procesadores no refrigerados

	        // Crea una instancia de BacktrackingAssignment
	        BacktrackingAssignment asignador = new BacktrackingAssignment();
	        // Llama al método para encontrar las mejores asignaciones
	        
//	        List<Procesador> mejoresAsignaciones = asignador.encontrarMejoresAsignaciones(new ArrayList<>(procesadores.values()), new ArrayList<>(tareas.values()), limiteTareasCriticas, limiteTiempoNoRefrigerado);
//
//	        	System.out.println("las mejores asignaciones son: " +mejoresAsignaciones);
//	        	
	        	
	        	
		        GreedyAssignment asignadorGreddy = new GreedyAssignment();
		        // Llama al método para encontrar las mejores asignaciones
		        
		        List<Procesador> mejorAsignacion= asignadorGreddy.asginarGreedy(new ArrayList<>(procesadores.values()), new ArrayList<>(tareas.values()), limiteTareasCriticas, limiteTiempoNoRefrigerado);

		        System.out.println("las mejor asignacion en greedy es : " +mejorAsignacion);
	      
	}
	
	
	
	
	
	
	
	 public void mostrarDatos() {
//	        System.out.println("Procesadores:");
//	    
//	            for (Entry<String, Procesador> entry : procesadores.entrySet()) {
//	                System.out.println("Id: " + entry.getKey());
//	                Procesador procesador = entry.getValue();
//	                System.out.println("Codigo: " + procesador.getCodigo());
//	                System.out.println("refrigerado: " + procesador.refrigerado());
//	                System.out.println("Año: " + procesador.getAnio());
//	                // Agrega aquí la impresión de otros atributos del procesador si es necesario
//	            }
	            
	            
	            System.out.println("Tareas criticas:");
	    	    
//	            for (Entry<String, Tarea> entry : tareas.entrySet()) {
//	                System.out.println("Id: " + entry.getKey());
//	                Tarea tarea = entry.getValue();
//	                System.out.println("Nombre: " + tarea.getNombre());
//	                System.out.println("Tiempo: " + tarea.getTiempo());
//	                System.out.println("Critica: " + tarea.isCritica());
//	                System.out.println("Prioridad: " + tarea.getPrioridad());
//
//	                // Agrega aquí la impresión de otros atributos del procesador si es necesario
//	            }
	            
	            
	            for(Tarea lista:listaTareasCriticas) {
	            	  System.out.println("Id: " + lista.getId());
		                System.out.println("Nombre: " + lista.getNombre());
		                System.out.println("Tiempo: " + lista.getTiempo());
		                System.out.println("Critica: " + lista.isCritica());
		                System.out.println("Prioridad: " + lista.getPrioridad());
		         }
	            System.out.println("Tareas NO criticas:");

	            for(Tarea listaNoCriticas:listaTareasNoCriticas) {
	            	  System.out.println("Id: " + listaNoCriticas.getId());
		                System.out.println("Nombre: " + listaNoCriticas.getNombre());
		                System.out.println("Tiempo: " + listaNoCriticas.getTiempo());
		                System.out.println("Critica: " + listaNoCriticas.isCritica());
		                System.out.println("Prioridad: " + listaNoCriticas.getPrioridad());
		         }
	 }
	/*
     * Expresar la complejidad temporal del servicio 1.
     */
	public Tarea servicio1(String ID) {
//        for (Entry<String, Tarea> entry : tareas.entrySet()) {
//        	if(entry.getKey().equals(ID)) {
//        		return entry.getValue();
//        	}
//        }

		return tareas.get(ID);
	}
    
    /*
     * Expresar la complejidad temporal del servicio 2.
     */
	public List<Tarea> servicio2(boolean esCritica) {
//		        List<Tarea> filtroTareas = new ArrayList<>();
//		        for (Entry<String, Tarea> entry : tareas.entrySet()) {
//		        	if(entry.getValue().isCritica()==esCritica) {
//		        		filtroTareas.add(entry.getValue());
//		        	}                                                                                                                                         
//		        }
//
//			return filtroTareas;
		if(esCritica) {
			return this.listaTareasCriticas;
		}
		else {
			return this.listaTareasNoCriticas;
		}
		
	}
		
		
		
	
    /*
     * Expresar la complejidad temporal del servicio 3.
     */
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
