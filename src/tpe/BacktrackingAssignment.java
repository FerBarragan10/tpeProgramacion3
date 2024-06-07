package tpe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BacktrackingAssignment {

    private List<Procesador> mejoresAsignaciones;
    private int mejorTiempo;
    int cantTareasCriticasMaximas=0;
    int cantTareasMaximas=0;
    public BacktrackingAssignment() {
        mejoresAsignaciones = new ArrayList<>();
        mejorTiempo = 200;
    }

    public List<Procesador> encontrarMejoresAsignaciones(List<Procesador> procesadores, List<Tarea> tareas, int limiteTareasCriticas, int limiteTiempoNoRefrigerado) {
    	cantTareasCriticasMaximas=(cantidadMaximaTareasCriticas(procesadores)*2)+1;
    	
    	if(cantTareasCriticasMaximas>cantTareasMaximas(tareas)){
    	backtrack(new ArrayList<>(), procesadores, new ArrayList<>(tareas), limiteTareasCriticas, limiteTiempoNoRefrigerado,cantTareasCriticasMaximas);
    	}else{
			System.out.println("no existe una solucion en greedy");
    	}
        return mejoresAsignaciones;
    }

    private void backtrack(List<Procesador> asignacionActual, List<Procesador> procesadores, List<Tarea> tareasRestantes, int limiteTareasCriticas, int limiteTiempoNoRefrigerado,int cantTareasCriticasMaximas) {
   
    		if (tareasRestantes.isEmpty()) {
                int tiempoFinal = calcularTiempoFinal(asignacionActual);
                if (tiempoFinal < mejorTiempo) {
//                    mejoresAsignaciones = new ArrayList<>(asignacionActual);
                    mejoresAsignaciones.addAll(asignacionActual);
                    mejorTiempo = tiempoFinal;
                }
            }
            else {
            	  Tarea tarea = tareasRestantes.get(0);

                  for (Procesador procesador : procesadores) {
                      if (verificarRestricciones(asignacionActual, procesador, tarea, limiteTareasCriticas, limiteTiempoNoRefrigerado)) {
                          asignarTarea(asignacionActual, procesador, tarea);

                          tareasRestantes.remove(tarea);

                          backtrack(asignacionActual, procesadores, tareasRestantes, limiteTareasCriticas, limiteTiempoNoRefrigerado,cantTareasCriticasMaximas);

                          desasignarTarea(asignacionActual, procesador, tarea);

                          tareasRestantes.add(0, tarea);
                      }
                  }
            }
	
    	
    	
      
    }

    private boolean verificarRestricciones(List<Procesador> asignacion, Procesador procesador, Tarea tarea, int limiteTareasCriticas, int limiteTiempoNoRefrigerado) {
        // Primera restricción: Ningún procesador podrá ejecutar más de 2 tareas críticas.
        int totalTareasCriticas=0;
    	Procesador procActual=getProcesador(asignacion,procesador);
    	if (procActual==null) {
    		procActual=procesador;
    	}
    	if (tarea.isCritica()) {
    		
    		totalTareasCriticas=procActual.cantidadTareasCriticas();
	        if (totalTareasCriticas > limiteTareasCriticas) {
	        	return false;
	        }
//        	 }
    	}
        // Segunda restricción: Los procesadores no refrigerados no podrán dedicar más de X tiempo de ejecución a las tareas asignadas.
        if (!procesador.refrigerado()) {
        	int tiempoAsignado=0;
        	
        	for (Tarea tareaActual : procActual.getTareas()) {
     	                tiempoAsignado+=tareaActual.getTiempo();
     	    }
            if (tiempoAsignado + tarea.getTiempo() > limiteTiempoNoRefrigerado) {
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
                                                           procesadorEnAsignacion.isRefrigerado(), 
                                                           procesadorEnAsignacion.getAnio());
            procesadorClonado.getTareas().addAll(procesadorEnAsignacion.getTareas());
            procesadorClonado.getTareas().add(tarea);
            procesadorClonado.getTiempoActual();

            asignacion.remove(procesadorEnAsignacion); // Eliminamos el procesador original de la asignación
            asignacion.add(procesadorClonado); // Agregamos el procesador clonado con la nueva tarea
        } else {
            // Si el procesador no está en la asignación, simplemente agreguemos el nuevo procesador con la tarea
            Procesador nuevoProcesador = new Procesador(procesador.getId(), 
                                                         procesador.getCodigo(), 
                                                         procesador.isRefrigerado(), 
                                                         procesador.getAnio());
            nuevoProcesador.getTareas().add(tarea);
            nuevoProcesador.getTiempoActual();
            asignacion.add(nuevoProcesador);

            
        }
        
    }

    private void desasignarTarea(List<Procesador> asignacion, Procesador procesador, Tarea tarea) {
        Procesador procesadorEnAsignacion = asignacion.stream()
                                                      .filter(p -> p.equals(procesador))
                                                      .findFirst()
                                                      .orElse(null);
        if (procesadorEnAsignacion != null) {
            // Si el procesador está en la asignación, clonémoslo antes de eliminar la tarea
            Procesador procesadorClonado = new Procesador(procesadorEnAsignacion.getId(), 
                                                           procesadorEnAsignacion.getCodigo(), 
                                                           procesadorEnAsignacion.isRefrigerado(), 
                                                           procesadorEnAsignacion.getAnio());
            procesadorClonado.getTareas().addAll(procesadorEnAsignacion.getTareas());
            procesadorClonado.getTiempoActual();
            procesadorClonado.getTareas().remove(tarea);
            asignacion.remove(procesadorEnAsignacion); // Eliminamos el procesador original de la asignación
            if (!procesadorClonado.getTareas().isEmpty()) {
                // Si el procesador clonado aún tiene tareas, lo volvemos a agregar a la asignación
                asignacion.add(procesadorClonado);
            }
        }
    }


    private int cantidadMaximaTareasCriticas(List<Procesador>procesadores) {
		return procesadores.size();
    }
    


    private int calcularTiempoFinal(List<Procesador> asignacion) {
        int tiempoFinal = 0;
       
        for (Procesador procesador : asignacion) {
        	if(tiempoFinal==0) {
        		tiempoFinal=procesador.getTiempoActual();
        	}
        	else {
        		if(tiempoFinal<procesador.getTiempoActual()) {
            		tiempoFinal=procesador.getTiempoActual();

        		}
        	}

        
        }
        return tiempoFinal;
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
