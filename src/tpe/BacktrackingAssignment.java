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
    private int cantTareasCriticasMaximas=0;
    private int cantTareasMaximas=0;
    private int cantMaximaDeCasos;
    
    public BacktrackingAssignment() {
    	this.mejoresAsignaciones = new ArrayList<>();
    	this.mejorTiempo = 200;
        this.cantMaximaDeCasos=0;
    }

    public List<Procesador> encontrarMejoresAsignaciones(List<Procesador> procesadores, List<Tarea> tareas, int limiteTareasCriticas, int limiteTiempoNoRefrigerado) {
    	//Aqui obtenemos la cantidad de tareas criticas maxima que es el resultado de obtener el doble de procesadores + 1.
    	cantTareasCriticasMaximas=(cantidadMaximaTareasCriticas(procesadores)*2)+1;
    	
    	//la cantidad de tareas criticas actual no puede superar la cantidad maxima de tareas criticas permitida
    	if(cantTareasCriticasMaximas>cantTareasCriticas(tareas)){
    	backtrack(new ArrayList<>(), procesadores, new ArrayList<>(tareas), limiteTareasCriticas, limiteTiempoNoRefrigerado,cantTareasCriticasMaximas);
    	}else{
			System.out.println("no existe una solucion en greedy");
    	}
    	System.out.println("Se generaron: "+ cantMaximaDeCasos +" soluciones");
        System.out.println("El tiempo de backtracking maximo es: "+ mejorTiempo);
        return mejoresAsignaciones;
    }

    private void backtrack(List<Procesador> asignacionActual, List<Procesador> procesadores, List<Tarea> tareasRestantes, int limiteTareasCriticas, int limiteTiempoNoRefrigerado,int cantTareasCriticasMaximas) {
    		if (tareasRestantes.isEmpty()) {
                int tiempoFinal = calcularTiempoFinal(asignacionActual);
                if (tiempoFinal < mejorTiempo) {
                    mejoresAsignaciones.addAll(asignacionActual);
                    mejorTiempo = tiempoFinal;
                }
            }
            else {
            	  Tarea tarea = tareasRestantes.get(0);

                  for (Procesador procesador : procesadores) {
                      if (verificarRestricciones(asignacionActual, procesador, tarea, limiteTareasCriticas, limiteTiempoNoRefrigerado)) {
                    	  cantMaximaDeCasos++;

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
        // Primera restriccion: Ningun procesador podra ejecutar mas de 2 tareas criticas.
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
    	}
        // Segunda restriccion: Los procesadores no refrigerados no podran dedicar mas de X tiempo de ejecucion a las tareas asignadas.
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
            // Si el procesador ya esta en la asignacion, clonemoslo antes de agregar la tarea
            Procesador procesadorClonado = new Procesador(procesadorEnAsignacion.getId(), 
                                                           procesadorEnAsignacion.getCodigo(), 
                                                           procesadorEnAsignacion.isRefrigerado(), 
                                                           procesadorEnAsignacion.getAnio());
            procesadorClonado.getTareas().addAll(procesadorEnAsignacion.getTareas());
            procesadorClonado.getTareas().add(tarea);
            procesadorClonado.getTiempoActual();

            asignacion.remove(procesadorEnAsignacion); // Eliminamos el procesador original de la asignacion
            asignacion.add(procesadorClonado); // Agregamos el procesador clonado con la nueva tarea
        } else {
            // Si el procesador no esta en la asignacion, simplemente agreguemos el nuevo procesador con la tarea
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
            // Si el procesador esta en la asignacion, clonemoslo antes de eliminar la tarea
            Procesador procesadorClonado = new Procesador(procesadorEnAsignacion.getId(), 
                                                           procesadorEnAsignacion.getCodigo(), 
                                                           procesadorEnAsignacion.isRefrigerado(), 
                                                           procesadorEnAsignacion.getAnio());
            procesadorClonado.getTareas().addAll(procesadorEnAsignacion.getTareas());
            procesadorClonado.getTiempoActual();
            procesadorClonado.getTareas().remove(tarea);
            asignacion.remove(procesadorEnAsignacion); // Eliminamos el procesador original de la asignacion
            if (!procesadorClonado.getTareas().isEmpty()) {
                // Si el procesador clonado aun tiene tareas, lo volvemos a agregar a la asignacion
                asignacion.add(procesadorClonado);
            }
        }
    }


    private int cantidadMaximaTareasCriticas(List<Procesador>procesadores) {
		return procesadores.size();
    }
    


    private int calcularTiempoFinal(List<Procesador> asignacion) {
    	//sumamos el tiempo de ejecucion total de cada procesador
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
    
    private int cantTareasCriticas(List<Tarea> tareas){
    	//devolvemos la cantidad de tareas criticas 
    	int contador=0;
    	for(Tarea t : tareas){
    		if(t.isCritica()){
    			contador++;
    		}
    	}
    	return contador;
    }
}
