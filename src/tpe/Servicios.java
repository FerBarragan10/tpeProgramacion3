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
		this.procesadores = new HashMap<>();
		this.tareas = new HashMap<>();
        this.listaTareasCriticas=new LinkedList<>();
        this.listaTareasNoCriticas=new LinkedList<>();


        reader.readTasks(pathTareas,this.tareas,listaTareasCriticas,listaTareasNoCriticas);
		reader.readProcessors(pathProcesadores,this.procesadores);	      
	}
	 
	public Tarea servicio1(String ID) {
		return tareas.get(ID);
	}
    

	public List<Tarea> servicio2(boolean esCritica) {
		if(esCritica) {
			return this.listaTareasCriticas;
		}
		else {
			return this.listaTareasNoCriticas;
		}
	}
		

	public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        List<Tarea> filtroTareasPorPrioridad = new ArrayList<>();
        for (Entry<String, Tarea> entry : tareas.entrySet()) {
        	if(entry.getValue().getPrioridad()>=prioridadInferior && entry.getValue().getPrioridad()<=prioridadSuperior) {
        		filtroTareasPorPrioridad.add(entry.getValue());
        	}                                                                                                                                         
        }

	return filtroTareasPorPrioridad;
	}
	
	
	
	public HashMap<String,Procesador> obtenerProcesadores(){
		HashMap<String,Procesador> retorno = new HashMap<>();
			retorno.putAll(procesadores);
		
			return retorno;
	}
	
	public HashMap<String,Tarea> obtenerTareas(){
		HashMap<String,Tarea> retorno = new HashMap<>();
			retorno.putAll(tareas);
		
			return retorno;
	}
}
