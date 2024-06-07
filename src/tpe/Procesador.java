package tpe;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Procesador {
 private String id;
 private String codigo;
 private boolean refrigerado;
 private int anio;
 private int tiempoActual;
 private List<Tarea> tareas;

 public Procesador(String id, String codigo, boolean refrigerado,int anio) {
     this.id = id;
     this.codigo = codigo;
     this.refrigerado = refrigerado;
     this.anio=anio;
     this.tiempoActual=0;
     this.tareas=new LinkedList<>();
 }





public Procesador(String id2, Object object) {
	 this.id = id;
  
     this.tareas=new LinkedList<>();
}





public String getId() {
	return id;
}


public void setId(String id) {
	this.id = id;
}


public String getCodigo() {
	return codigo;
}


public void setCodigo(String codigo) {
	this.codigo = codigo;
}


public boolean refrigerado() {
	return refrigerado;
}


public void setRefrigerado(boolean refrigerado) {
	this.refrigerado = refrigerado;
}


public int getAnio() {
	return anio;
}


public void setAnio(int anio) {
	this.anio = anio;
}


public List<Tarea> getTareas() {
	return tareas;
}


public void setTareas(List<Tarea> tareas) {
	this.tareas = tareas;
}


public boolean isRefrigerado() {
	return refrigerado;
}





@Override
public String toString() {
	return "Procesador [id=" + id + ", codigo=" + codigo + ", refrigerado=" + refrigerado + ", anio=" + anio
			+ ", tareas=" + tareas + "]";
}


@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Procesador that = (Procesador) o;
    return Objects.equals(id, that.id);
}





public int getTiempoActual() {
	int tiempoActual=0;
	for(Tarea task:tareas) {
		tiempoActual += task.getTiempo();
	}
	return tiempoActual;
}


public int cantidadTareasCriticas() {
	int cantidad=0;
	for(Tarea task:tareas) {
		if(task.isCritica()) {
			cantidad++;
		}
	}
	return cantidad;
}

}