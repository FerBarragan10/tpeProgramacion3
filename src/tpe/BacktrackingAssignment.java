package tpe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BacktrackingAssignment {

    private Map<Procesador, List<Tarea>> mejoresAsignaciones;
    private int mejorTiempo;

    public BacktrackingAssignment() {
        mejoresAsignaciones = new HashMap<>();
        mejorTiempo = 120;
    }

    public Map<Procesador, List<Tarea>> encontrarMejoresAsignaciones(List<Procesador> procesadores, List<Tarea> tareas, int limiteTareasCriticas, int limiteTiempoNoRefrigerado) {
        // Inicializa la llamada recursiva con asignaciones vacías y sin asignar tareas
        backtrack(new HashMap<>(), procesadores, new ArrayList<>(tareas), limiteTareasCriticas, limiteTiempoNoRefrigerado);
        System.out.println("asignacion actual"+ mejoresAsignaciones);

        return mejoresAsignaciones;
        
    }

    private void backtrack(Map<Procesador, List<Tarea>> asignacionActual, List<Procesador> procesadores, List<Tarea> tareasRestantes, int limiteTareasCriticas, int limiteTiempoNoRefrigerado) {
        // Verifica si se ha completado una asignación
        if (tareasRestantes.isEmpty()) {
            // Calcula el tiempo final de ejecución para la asignación actual
            int tiempoFinal = calcularTiempoFinal(asignacionActual);
            // Verifica si esta asignación es la mejor hasta ahora
            if (tiempoFinal < mejorTiempo) {
                mejoresAsignaciones.clear();
                mejoresAsignaciones.putAll(asignacionActual);
                mejorTiempo = tiempoFinal;
             	System.out.println("asignacion actual"+ mejoresAsignaciones);
             	System.out.println("el mejor tiempo es "+ mejorTiempo);
             	return;
            }
             else if (tiempoFinal == mejorTiempo) {
                mejoresAsignaciones.putAll(asignacionActual);
            	//System.out.println("asignacion actual"+ mejoresAsignaciones);

            }
            
            return;
        }

        // Selecciona una tarea para asignar
        Tarea tarea = tareasRestantes.get(0);

        // Prueba asignar la tarea a cada procesador disponible
        for (Procesador procesador : procesadores) {
            // Verifica restricciones antes de asignar
            if (verificarRestricciones(asignacionActual, procesador, tarea, limiteTareasCriticas, limiteTiempoNoRefrigerado)) {
                // Asigna la tarea al procesador actual
                asignarTarea(asignacionActual, procesador, tarea);

                // Elimina la tarea asignada de la lista de tareas restantes
                tareasRestantes.remove(tarea);

                // Llama recursivamente con la nueva asignación y las tareas restantes actualizadas
                backtrack(asignacionActual, procesadores, tareasRestantes, limiteTareasCriticas, limiteTiempoNoRefrigerado);

                // Deshace la asignación para probar con otro procesador
                desasignarTarea(asignacionActual, procesador, tarea);

                // Agrega nuevamente la tarea a la lista de tareas restantes para la siguiente iteración
            tareasRestantes.add(0, tarea);

            }
        }
    }



    private boolean verificarRestricciones(Map<Procesador, List<Tarea>> asignacion, Procesador procesador, Tarea tarea, int limiteTareasCriticas, int limiteTiempoNoRefrigerado) {
        // Verifica restricción de máximo de tareas críticas por procesador
        if (tarea.isCritica() && asignacion.containsKey(procesador) && asignacion.get(procesador).stream().filter(Tarea::isCritica).count() >= limiteTareasCriticas) {
            return false;
        }

        // Verifica restricción de tiempo para procesadores no refrigerados
        if (!procesador.refrigerado() && asignacion.containsKey(procesador)) {
            int tiempoAsignado = asignacion.get(procesador).stream().mapToInt(Tarea::getTiempo).sum() + tarea.getTiempo();
            if (tiempoAsignado > limiteTiempoNoRefrigerado) {
                return false;
            }
        }

        return true;
    }

    private void asignarTarea(Map<Procesador, List<Tarea>> asignacion, Procesador procesador, Tarea tarea) {
    	asignacion.putIfAbsent(procesador, new ArrayList<>());
    	asignacion.get(procesador).add(tarea);

    }

    private void desasignarTarea(Map<Procesador, List<Tarea>> asignacion, Procesador procesador, Tarea tarea) {
        if (asignacion.containsKey(procesador)) {
            asignacion.get(procesador).remove(tarea);
            if (asignacion.get(procesador).isEmpty()) {
                asignacion.remove(procesador);
            }
        }
    }

    private int calcularTiempoFinal(Map<Procesador, List<Tarea>> asignacion) {
        int tiempoFinal = 0;
        for (List<Tarea> tareas : asignacion.values()) {
            tiempoFinal = Math.max(tiempoFinal, tareas.stream().mapToInt(Tarea::getTiempo).sum());
        }
        return tiempoFinal;
    }
}
