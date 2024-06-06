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
	    public GreedyAssignment() {
	        mejoresAsignaciones = new ArrayList<>();
	        mejorTiempo = 120;
	        
	    }
	    
	    public int getTiempoFinal(){
	    	return this.calcularTiempoFinal(mejoresAsignaciones);
	    }

	    public List<Procesador> asginarGreedy(List<Procesador> procesadores, List<Tarea> tareas, int limiteTareasCriticas, int limiteTiempoNoRefrigerado) {
	    	cantTareasCriticasMaximas=(cantidadMaximaTareasCriticas(procesadores)*2)+1;
	    	greedy(new ArrayList<>(), procesadores, new ArrayList<>(tareas), limiteTareasCriticas, limiteTiempoNoRefrigerado, cantTareasCriticasMaximas);
	          
	        return mejoresAsignaciones;
	    }
	    
	    

<<<<<<< HEAD:src/tpe/m/src/m/GreedyAssignment.java
	    private void greedy(List<Procesador> asignacionActual, List<Procesador> procesadores, List<Tarea> tareasRestantes, int limiteTareasCriticas, int limiteTiempoNoRefrigerado) {
	    	procesadores.sort(Comparator.comparing(Procesador::getTiempo));
=======
	    private void greedy(List<Procesador> asignacionActual, List<Procesador> procesadores, List<Tarea> tareasRestantes, int limiteTareasCriticas, int limiteTiempoNoRefrigerado,int cantTareasCriticasMaximas) {
	    	Tarea tarea=new Tarea("","",0,false,0);
>>>>>>> e01d7f90e3f4c9c486905d7f42cac0a80767d282:src/tpe/GreedyAssignment.java
	    	tareasRestantes.sort(Comparator.comparing(Tarea::getTiempo).reversed());
	    	System.out.println(procesadores);
	    	
	    	
<<<<<<< HEAD:src/tpe/m/src/m/GreedyAssignment.java
	    	if (tareasRestantes.isEmpty()) {
	            int tiempoFinal = calcularTiempoFinal(asignacionActual);
	            if (tiempoFinal < mejorTiempo) {
//	                mejoresAsignaciones = new ArrayList<>(asignacionActual);
	                mejoresAsignaciones.addAll(asignacionActual);
	                mejorTiempo = tiempoFinal;
	            } 
	            
	    	} else{
	    		
	            	
	    	        	for (int j = 0; j < tareasRestantes.size(); j++) {
	    	        		Tarea tarea = tareasRestantes.get(j);
	    	        		
	    	        		int i=0;
	    	        		while (i < procesadores.size() && !tareasRestantes.isEmpty()) {
	    	        			Procesador procesador = procesadores.get(i);
	    					
=======
	    	  
	    		while(cantTareasCriticasMaximas<tareasRestantes.size() && !tareasRestantes.isEmpty()) {
	    	        for (Procesador procesador : procesadores) {

//	    	        	for (int j = 0; j < tareasRestantes.size(); j++) {
	    	        	if(!tareasRestantes.isEmpty()) {
	    	        		tarea = tareasRestantes.get(0);
	    	        
>>>>>>> e01d7f90e3f4c9c486905d7f42cac0a80767d282:src/tpe/GreedyAssignment.java
	    	        		if (verificarRestricciones(asignacionActual, procesador, tarea, limiteTareasCriticas, limiteTiempoNoRefrigerado)) {
	    	        			asignarTarea(asignacionActual, procesador, tarea);
	    	        			tareasRestantes.remove(tarea);
<<<<<<< HEAD:src/tpe/m/src/m/GreedyAssignment.java
	    	        			
	    	        			greedy(asignacionActual, procesadores, tareasRestantes, limiteTareasCriticas, limiteTiempoNoRefrigerado);
	    	        		}
	    	        		else{
	    	        			i++;
	    	        		}
	    				}
=======
	    	        		}  	
	    	        	}
	    	        	else {
    	        			break;
    	        		}
>>>>>>> e01d7f90e3f4c9c486905d7f42cac0a80767d282:src/tpe/GreedyAssignment.java
	    	        }
	    		}    
	    			if(cantTareasCriticasMaximas>tareasRestantes.size()) {
	    				System.out.println("no existe una solucion en greedy");
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
	        // Segunda restricción: Los procesadores no refrigerados no podrán dedicar más de X tiempo de ejecución a las tareas asignadas.
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
	            List<Tarea> listaTareasCopia = nuevoProcesador.getTareas();
	            listaTareasCopia.add(tarea);
	            System.out.println(nuevoProcesador);
	            asignacion.add(nuevoProcesador);
	            
	        }
	    }

//	    private void desasignarTarea(List<Procesador> asignacion, Procesador procesador, Tarea tarea) {
//	        Procesador procesadorEnAsignacion = asignacion.stream()
//	                                                      .filter(p -> p.equals(procesador))
//	                                                      .findFirst()
//	                                                      .orElse(null);
//	        if (procesadorEnAsignacion != null) {
//	            // Si el procesador está en la asignación, clonémoslo antes de eliminar la tarea
//	            Procesador procesadorClonado = new Procesador(procesadorEnAsignacion.getId(), 
//	                                                           procesadorEnAsignacion.getCodigo(), 
//	                                                           procesadorEnAsignacion.refrigerado(), 
//	                                                           procesadorEnAsignacion.getAnio());
//	            procesadorClonado.getTareas().addAll(procesadorEnAsignacion.getTareas());
//	            procesadorClonado.getTareas().remove(tarea);
//	            asignacion.remove(procesadorEnAsignacion); // Eliminamos el procesador original de la asignación
//	            if (!procesadorClonado.getTareas().isEmpty()) {
//	                // Si el procesador clonado aún tiene tareas, lo volvemos a agregar a la asignación
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
	
}
