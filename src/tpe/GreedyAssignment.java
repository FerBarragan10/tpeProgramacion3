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
	    int cantTareasCriticasMaximas=0;
	    int cantTareasMaximas=0;
	    public GreedyAssignment() {
	        mejoresAsignaciones = new ArrayList<>();
	        mejorTiempo = 120;
	        
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
	          
	        return mejoresAsignaciones;
	    }
	    
	    

	    private void greedy(List<Procesador> asignacionActual, List<Procesador> procesadores, List<Tarea> tareasRestantes, int limiteTareasCriticas, int limiteTiempoNoRefrigerado,int cantTareasCriticasMaximas) {
	    	Tarea tarea=new Tarea("","",0,false,0);
	    	Procesador procesador= new Procesador("","",false,0);
	    	tareasRestantes.sort(Comparator.comparing(Tarea::getTiempo).reversed());
	    	int i = 0;
	    	  
	    		while(!tareasRestantes.isEmpty()) {
	    			

	    			procesadores.sort(Comparator.comparing(Procesador::getTiempoActual)); 
//	    	        	for (int j = 0; j < tareasRestantes.size(); j++) {
	    	        	if(!tareasRestantes.isEmpty()) {
	    	        		
	    	        		procesador= procesadores.get(i);
	    	        		tarea = tareasRestantes.get(0);
	    	        
	    	        		if (verificarRestricciones(asignacionActual, procesador, tarea, limiteTareasCriticas, limiteTiempoNoRefrigerado)) {
	    	        			asignarTarea(asignacionActual, procesador, tarea);
	    	        			procesador.getTareas().add(tarea);//pase la lista de procesadores para poder ordenarlos despues de agregarle la tarea
	    	        			tareasRestantes.remove(tarea);
	    	        			
	    	        			
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
	    	            //if (tiempoFinal < mejorTiempo) {
	    	                mejoresAsignaciones.addAll(asignacionActual);
	    	                mejorTiempo = tiempoFinal;
	    	            //} 
	    	            
	    	    	} 
	    	
	    }
	    

	    
	    private boolean verificarRestricciones(List<Procesador> asignacion, Procesador procesador, Tarea tarea, int limiteTareasCriticas, int limiteTiempoNoRefrigerado) {
	        // Primera restricci�n: Ning�n procesador podr� ejecutar m�s de 2 tareas cr�ticas.
	        int totalTareasCriticas=0;
	        Procesador procActual = getProcesador(asignacion,procesador);
        	if (procActual==null) {
        		procActual=procesador;
        	}
	        if (tarea.isCritica()) {
	    		
	        	 //for (Procesador procActual : asignacion) {
	        	        for (Tarea tareaActual : procActual.getTareas()) {
	        	            if (tareaActual.isCritica()) {
	        	                totalTareasCriticas++;
	        	            }
	        	        }
	        	        if (totalTareasCriticas > limiteTareasCriticas) {
	        	        	return false;
	        	        }
	        	 //}
	    	}
	        // Segunda restricci�n: Los procesadores no refrigerados no podr�n dedicar m�s de X tiempo de ejecuci�n a las tareas asignadas.
	        if (!procesador.refrigerado()) {
	        	
//	        	for (Tarea tareaActual : procActual.getTareas()) {
//	     	                tiempoAsignado+=tareaActual.getTiempo();
//	     	            }
	     	       
//	            int tiempoAsignado = asignacion.stream()
//	                    .filter(p -> !p.equals(procesador)) // Excluye el tiempo del procesador actual
//	                    .flatMap(p -> p.getTareas().stream())
//	                    .mapToInt(Tarea::getTiempo)
//	                    .sum();
	        	
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
	            // Si el procesador ya est� en la asignaci�n, clon�moslo antes de agregar la tarea
	            Procesador procesadorClonado = new Procesador(procesadorEnAsignacion.getId(), 
	                                                           procesadorEnAsignacion.getCodigo(), 
	                                                           procesadorEnAsignacion.refrigerado(), 
	                                                           procesadorEnAsignacion.getAnio());
	            procesadorClonado.getTareas().addAll(procesadorEnAsignacion.getTareas());
	            procesadorClonado.getTareas().add(tarea);
	            asignacion.remove(procesadorEnAsignacion); // Eliminamos el procesador original de la asignaci�n
	            asignacion.add(procesadorClonado); // Agregamos el procesador clonado con la nueva tarea
	        } else {
	            // Si el procesador no est� en la asignaci�n, simplemente agreguemos el nuevo procesador con la tarea
	            Procesador nuevoProcesador = new Procesador(procesador.getId(), 
	                                                         procesador.getCodigo(), 
	                                                         procesador.refrigerado(), 
	                                                         procesador.getAnio());
	            nuevoProcesador.getTareas().add(tarea);
	            asignacion.add(nuevoProcesador);
	            
	        }
	    }

//	    private void desasignarTarea(List<Procesador> asignacion, Procesador procesador, Tarea tarea) {
//	        Procesador procesadorEnAsignacion = asignacion.stream()
//	                                                      .filter(p -> p.equals(procesador))
//	                                                      .findFirst()
//	                                                      .orElse(null);
//	        if (procesadorEnAsignacion != null) {
//	            // Si el procesador est� en la asignaci�n, clon�moslo antes de eliminar la tarea
//	            Procesador procesadorClonado = new Procesador(procesadorEnAsignacion.getId(), 
//	                                                           procesadorEnAsignacion.getCodigo(), 
//	                                                           procesadorEnAsignacion.refrigerado(), 
//	                                                           procesadorEnAsignacion.getAnio());
//	            procesadorClonado.getTareas().addAll(procesadorEnAsignacion.getTareas());
//	            procesadorClonado.getTareas().remove(tarea);
//	            asignacion.remove(procesadorEnAsignacion); // Eliminamos el procesador original de la asignaci�n
//	            if (!procesadorClonado.getTareas().isEmpty()) {
//	                // Si el procesador clonado a�n tiene tareas, lo volvemos a agregar a la asignaci�n
//	                asignacion.add(procesadorClonado);
//	            }
//	        }
//	    }




	    private int calcularTiempoFinal(List<Procesador> asignacion) {
	    	int tiempoFinal = 0;
	        //int mayorTiempo=0;
	       
	        for (Procesador procesador : asignacion) {
	        	if(tiempoFinal==0){
	        		tiempoFinal = procesador.getTiempoActual();
	        	}else{
	        		if(tiempoFinal<procesador.getTiempoActual()){
	        			tiempoFinal = procesador.getTiempoActual();
	        		}
	        	}
//	        	for(Tarea tarea:procesador.getTareas()) {
//	        		tiempoFinal+=tarea.getTiempo();
//	        	}
//	        	if(mayorTiempo<tiempoFinal) {
//	        		mayorTiempo=tiempoFinal;
//	        	}
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