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
	    private int cantTareasCriticasMaximas=0;
	    private int cantMaximaDeCasos;
	    public GreedyAssignment() {
	    	this.mejoresAsignaciones = new ArrayList<>();
	        this.cantMaximaDeCasos=0;
	    }
	    
	    /*
	     La estrategia elegida para el metodo Greedy fue ir asignando las tareas de mayor duracion en los procesadores
	     que menor tiempo tiempo de ejecucion tengan en ese momento. Para eso, se ordenaron las tareas de mayor a menor
	     y los procesadores a medida que se les va a asignando una tarea su orden se actualiza, 
	     quedando el de menor tiempo de ejecucion primero. De esta manera vamos a intentar de que los tiempos de 
	     ejecucion totales de los procesadores queden lo mas balanceados posible (es decir que su tiempo de ejecucion 
	     sea parecido), teniendo en cuenta tambien las restricciones planteadas ya en el enunciado del TPE.
	     */
	    

	    public List<Procesador> asginarGreedy(List<Procesador> procesadores, List<Tarea> tareas, int limiteTareasCriticas, int limiteTiempoNoRefrigerado) {

	        cantTareasCriticasMaximas = (cantidadMaximaTareasCriticas(procesadores) * 2) + 1;

	            if (cantTareasCriticasMaximas > cantTareasMaximas(tareas)) {
	                greedy(new ArrayList<>(), new ArrayList<>(procesadores), new ArrayList<>(tareas), limiteTareasCriticas, limiteTiempoNoRefrigerado, cantTareasCriticasMaximas);
	               
	            } else {
	                System.out.println("No existe una solucion en greedy");
	            }
	            if(!mejoresAsignaciones.isEmpty()) {
	            	 System.out.println("Se generaron: " + cantMaximaDeCasos + " soluciones");		                
		                return mejoresAsignaciones;
	            }
	            else {
	                System.out.println("No existe una solucion en greedy");

	            }
	        return null;
	    }

	    
	    

	    private void greedy(List<Procesador> asignacionActual, List<Procesador> procesadores, List<Tarea> tareasRestantes, int limiteTareasCriticas, int limiteTiempoNoRefrigerado,int cantTareasCriticasMaximas) {
	    	Tarea tarea=new Tarea("","",0,false,0);
	    	Procesador procesador= new Procesador("","",false,0);
	    	tareasRestantes.sort(Comparator.comparing(Tarea::getTiempo).reversed());
	    	int i = 0;
	    	  
	    		while(!tareasRestantes.isEmpty()) {
	    			

	    			procesadores.sort(Comparator.comparing(Procesador::getTiempoActual));
	    	        	if(!tareasRestantes.isEmpty() && i<procesadores.size()) {
	    	        		
	    	        		procesador= procesadores.get(i);
	    	        		tarea = tareasRestantes.get(0);
	    	        
	    	        		if (verificarRestricciones(asignacionActual, procesador, tarea, limiteTareasCriticas, limiteTiempoNoRefrigerado)) {
	    	        			asignarTarea(asignacionActual, procesador, tarea);
	    	        			procesador.getTareas().add(tarea);//pase la lista de procesadores para poder ordenarlos despues de agregarle la tarea
	    	        			tareasRestantes.remove(tarea);
	    	        			cantMaximaDeCasos++;
	    	        			i=0;
	    	        		}
	    	        		else{
	    	        			procesador = procesadores.get(i++);
	    	        		}
	    	        	}
	    	        	else  if(i==procesadores.size()){
	    					return;
    	        		}
	    	        
	    		}    
	    			
	    		
	            	if (tareasRestantes.isEmpty()) {

	    	                mejoresAsignaciones.addAll(asignacionActual);
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

	        	        for (Tarea tareaActual : procActual.getTareas()) {
	        	            if (tareaActual.isCritica()) {
	        	                totalTareasCriticas++;
	        	            }
	        	        }
	        	        if (totalTareasCriticas >= limiteTareasCriticas) {
	        	        	return false;
	        	        }
	        	 
	    	}
	        // Segunda restricci�n: Los procesadores no refrigerados no podr�n dedicar m�s de X tiempo de ejecuci�n a las tareas asignadas.
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


	    public int getTiempoFinal(){
	    	return this.calcularTiempoFinal(mejoresAsignaciones);
	    }


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