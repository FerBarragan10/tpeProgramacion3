package tpe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GreedyAssignment {
	
	 private List<Procesador> mejoresAsignaciones;
	    private int mejorTiempo;
	    private int cantTareasCriticasMaximas=0;
	    private int cantTareasMaximas=0;
	    private int cantMaximaDeCasos;
	    public GreedyAssignment() {
	    	this.mejoresAsignaciones = new ArrayList<>();
	        this.mejorTiempo = 200;
	        this.cantMaximaDeCasos=0;
	    }
	    
	    public int getTiempoFinal(){
	    	return this.calcularTiempoFinal(mejoresAsignaciones);
	    }

	    public List<Procesador> asginarGreedy(List<Procesador> procesadores, List<Tarea> tareas, int limiteTareasCriticas, int limiteTiempoNoRefrigerado) {
	    	cantTareasCriticasMaximas=(cantidadMaximaTareasCriticas(procesadores)*2)+1;
	    	
	    	if(cantTareasCriticasMaximas>cantTareasMaximas(tareas)){
	    		greedy(new ArrayList<>(), new ArrayList<>(procesadores), new ArrayList<>(tareas), limiteTareasCriticas, limiteTiempoNoRefrigerado, cantTareasCriticasMaximas);	    		
	    	}else{
				System.out.println("no existe una solucion en greedy");
	    	}
            System.out.println("Se generaron: "+ cantMaximaDeCasos +" soluciones");
            System.out.println("El tiempo maximo de Greedy es: "+ mejorTiempo);
	        return mejoresAsignaciones;
	    }
	    
	    

	    private void greedy(List<Procesador> asignacionActual, List<Procesador> procesadores, List<Tarea> tareasRestantes, int limiteTareasCriticas, int limiteTiempoNoRefrigerado,int cantTareasCriticasMaximas) {
	    	Tarea tarea=new Tarea("","",0,false,0);
	    	Procesador procesador= new Procesador("","",false,0);
	    	tareasRestantes.sort(Comparator.comparing(Tarea::getTiempo).reversed());
	    	int i = 0;
	    	  
	    		while(!tareasRestantes.isEmpty()) {
	    			

	    			procesadores.sort(Comparator.comparing(Procesador::getTiempoActual)); 
	    	        	if(!tareasRestantes.isEmpty()) {
	    	        		
	    	        		procesador= procesadores.get(i);
	    	        		tarea = tareasRestantes.get(0);
	    	        
	    	        		if (verificarRestricciones(asignacionActual, procesador, tarea, limiteTareasCriticas, limiteTiempoNoRefrigerado)) {
	    	        			asignarTarea(asignacionActual, procesador, tarea);
	    	        			procesador.getTareas().add(tarea);//pase la lista de procesadores para poder ordenarlos despues de agregarle la tarea
	    	        			tareasRestantes.remove(tarea);
	    	        			cantMaximaDeCasos++;
	    	        			
	    	        		}
	    	        		else{
	    	        			procesador = procesadores.get(i++);
	    	        		}
	    	        	}
	    	        	else {
    	        			return;
    	        		}
	    	        
	    		}    
	    			
	    		
	            	if (tareasRestantes.isEmpty()) {
	    	            int tiempoFinal = calcularTiempoFinal(asignacionActual);
	    	            if (tiempoFinal < mejorTiempo) {
	    	                mejoresAsignaciones.addAll(asignacionActual);
	    	                mejorTiempo = tiempoFinal;
	    	            } 

	    	            
	    	    	} 
	    	
	    }
	    

	    
	    private boolean verificarRestricciones(List<Procesador> asignacion, Procesador procesador, Tarea tarea, int limiteTareasCriticas, int limiteTiempoNoRefrigerado) {
	        // Primera restricción: Ningún procesador podrá ejecutar más de 2 tareas críticas.
	        int totalTareasCriticas=0;
	        Procesador procActual = getProcesador(asignacion,procesador);
        	if (procActual==null) {
        		procActual=procesador;
        	}
	        if (tarea.isCritica()) {

	        	        for (Tarea tareaActual : procActual.getTareas()) {
	        	            if (tareaActual.isCritica()) {
	        	                totalTareasCriticas++;
	        	            }
	        	        }
	        	        if (totalTareasCriticas > limiteTareasCriticas) {
	        	        	return false;
	        	        }
	        	 
	    	}
	        // Segunda restricción: Los procesadores no refrigerados no podrán dedicar más de X tiempo de ejecución a las tareas asignadas.
	        if (!procesador.refrigerado()) {
	        	
	            if (procActual.getTiempoActual() + tarea.getTiempo() > limiteTiempoNoRefrigerado) {
	                return false;
	            }
	        }
	        return true;
	    }
	    
	    
	    private Procesador getProcesador(List<Procesador> asignacion,Procesador procesador) {
	    	for (Procesador current: asignacion) {
	    		if(current.equals(procesador))
	    			return current;
	    	}
			return null;

		}
	   

	    private void asignarTarea(List<Procesador> asignacion, Procesador procesador, Tarea tarea) {
	        Procesador procesadorEnAsignacion = asignacion.stream()
	                                                      .filter(p -> p.equals(procesador))
	                                                      .findFirst()
	                                                      .orElse(null);
	        
	        if (procesadorEnAsignacion != null) {
	            // Si el procesador ya está en la asignación, clonémoslo antes de agregar la tarea
	            Procesador procesadorClonado = new Procesador(procesadorEnAsignacion.getId(), 
	                                                           procesadorEnAsignacion.getCodigo(), 
	                                                           procesadorEnAsignacion.refrigerado(), 
	                                                           procesadorEnAsignacion.getAnio());
	            procesadorClonado.getTareas().addAll(procesadorEnAsignacion.getTareas());
	            procesadorClonado.getTareas().add(tarea);
	            asignacion.remove(procesadorEnAsignacion); // Eliminamos el procesador original de la asignación
	            asignacion.add(procesadorClonado); // Agregamos el procesador clonado con la nueva tarea
	        } else {
	            // Si el procesador no está en la asignación, simplemente agreguemos el nuevo procesador con la tarea
	            Procesador nuevoProcesador = new Procesador(procesador.getId(), 
	                                                         procesador.getCodigo(), 
	                                                         procesador.refrigerado(), 
	                                                         procesador.getAnio());
	            nuevoProcesador.getTareas().add(tarea);
	            asignacion.add(nuevoProcesador);
	            
	        }
	    }
	    
	    
	    private int calcularTiempoFinal(List<Procesador> asignacion) {
	    	int tiempoFinal = 0;
	       
	        for (Procesador procesador : asignacion) {
	        	if(tiempoFinal==0){
	        		tiempoFinal = procesador.getTiempoActual();
	        	}else{
	        		if(tiempoFinal<procesador.getTiempoActual()){
	        			tiempoFinal = procesador.getTiempoActual();
	        		}
	        	}
	        }
	        return tiempoFinal;
	    }
	    
	    
	    private int cantidadMaximaTareasCriticas(List<Procesador>procesadores) {
	    		return procesadores.size();
	    }
	    
	    private int cantTareasMaximas(List<Tarea> tareas){
	    	int contador=0;
	    	for(Tarea t : tareas){
	    		if(t.isCritica()){
	    			contador++;
	    		}
	    	}
	    	return contador;
	    }
	
}