package m.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import m.Procesador;
import m.Tarea;


	
	
	
//	 public List<Tarea> readTasks(String filePath) {
//	        List<Tarea> tareas = new ArrayList<>();
//
//	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//	            String line;
//	            while ((line = br.readLine()) != null) {
//	                String[] data = line.split(";"); // Suponiendo que el CSV está separado por comas
//	                // Suponiendo que el CSV tiene el formato: id,descripcion,duracion
//	                String id = data[0];
//	                String descripcion = data[1];
//	                int duracion = Integer.parseInt(data[2]);
//	                int velocidad=Integer.parseInt(data[4]);
//	                Tarea tarea = new Tarea(id, descripcion, duracion, false, velocidad);
//	                tareas.add(tarea);
//	            }
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//
//	        return tareas;
//	    }
//	
	
//	public void readTasks(String taskPath) {
//		
//		// Obtengo una lista con las lineas del archivo
//		// lines.get(0) tiene la primer linea del archivo
//		// lines.get(1) tiene la segunda linea del archivo... y así
//		ArrayList<String[]> lines = this.readContent(taskPath);
//		
//		for (String[] line: lines) {
//			// Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
//			String id = line[0].trim();
//			String nombre = line[1].trim();
//			Integer tiempo = Integer.parseInt(line[2].trim());
//			Boolean critica = Boolean.parseBoolean(line[3].trim());
//			Integer prioridad = Integer.parseInt(line[4].trim());
//			// Aca instanciar lo que necesiten en base a los datos leidos
//		}
//		
//	}
	
//public void readProcessors(String processorPath) {
//		
//		// Obtengo una lista con las lineas del archivo
//		// lines.get(0) tiene la primer linea del archivo
//		// lines.get(1) tiene la segunda linea del archivo... y así
//		ArrayList<String[]> lines = this.readContent(processorPath);
//		
//		for (String[] line: lines) {
//			// Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
//			String id = line[0].trim();
//			String codigo = line[1].trim();
//			Boolean refrigerado = Boolean.parseBoolean(line[2].trim());
//			Integer anio = Integer.parseInt(line[3].trim());
//			// Aca instanciar lo que necesiten en base a los datos leidos
//		}
//		
//	}
	



//	private ArrayList<String[]> readContent(String path) {
//		ArrayList<String[]> lines = new ArrayList<String[]>();
//
//		File file = new File(path);
//		FileReader fileReader = null;
//		BufferedReader bufferedReader = null;
//		try {
//			fileReader = new FileReader(file);
//			bufferedReader = new BufferedReader(fileReader);
//			String line = null;
//			while ((line = bufferedReader.readLine()) != null) {
//				line = line.trim();
//				lines.add(line.split(";"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			if (bufferedReader != null)
//				try {
//					bufferedReader.close();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//		}
//		
//		return lines;
//	}
//	
	
	
	



	public class CSVReader {

		public CSVReader() {
		}
		
//		public void readTasks(String taskPath) {
//			
//			// Obtengo una lista con las lineas del archivo
//			// lines.get(0) tiene la primer linea del archivo
//			// lines.get(1) tiene la segunda linea del archivo... y así
//			ArrayList<String[]> lines = this.readContent(taskPath);
//			
//			for (String[] line: lines) {
//				// Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
//				String id = line[0].trim();
//				String nombre = line[1].trim();
//				Integer tiempo = Integer.parseInt(line[2].trim());
//				Boolean critica = Boolean.parseBoolean(line[3].trim());
//				Integer prioridad = Integer.parseInt(line[4].trim());
//				// Aca instanciar lo que necesiten en base a los datos leidos
//			}
//			
//		}
		
	public void readProcessors(String processorPath, HashMap<String, Procesador> ProcesadorMap) {
			
			// Obtengo una lista con las lineas del archivo
			// lines.get(0) tiene la primer linea del archivo
			// lines.get(1) tiene la segunda linea del archivo... y así
			ArrayList<String[]> lines = this.readContent(processorPath);
			
			for (String[] line: lines) {
				// Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
				String id = line[0].trim();
				String codigo = line[1].trim();
				Boolean refrigerado = Boolean.parseBoolean(line[2].trim());
				Integer anio = Integer.parseInt(line[3].trim());
			    Procesador procesador = new Procesador(id, codigo, refrigerado, anio);
		        // Guardar la tarea en el HashMap usando el id como clave
			    ProcesadorMap.put(id, procesador);			}
			
		}

		private ArrayList<String[]> readContent(String path) {
			ArrayList<String[]> lines = new ArrayList<String[]>();

			File file = new File(path);
			FileReader fileReader = null;
			BufferedReader bufferedReader = null;
			try {
				fileReader = new FileReader(file);
				bufferedReader = new BufferedReader(fileReader);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					line = line.trim();
					lines.add(line.split(";"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (bufferedReader != null)
					try {
						bufferedReader.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
			
			return lines;
		}
		
		public void readTasks(String taskPath, HashMap<String, Tarea> tareasMap,LinkedList criticas,LinkedList noCriticas) {
		    // Obtengo una lista con las lineas del archivo
		    // lines.get(0) tiene la primer linea del archivo
		    // lines.get(1) tiene la segunda linea del archivo... y así
		    ArrayList<String[]> lines = this.readContent(taskPath);
		    
		    for (String[] line: lines) {
		        // Cada linea es un arreglo de Strings, donde cada posicion guarda un elemento
		    	String id = line[0].trim();
				String nombre = line[1].trim();
				Integer tiempo = Integer.parseInt(line[2].trim());
				Boolean critica = Boolean.parseBoolean(line[3].trim());
				Integer prioridad = Integer.parseInt(line[4].trim());
		        // Crear un objeto Tarea con los datos obtenidos
		        Tarea tarea = new Tarea(id, nombre, tiempo, critica, prioridad);
		        // Guardar la tarea en el HashMap usando el id como clave
		        tareasMap.put(id, tarea);
		        if(critica) {
		        	criticas.add(tarea);
		        }
		        else {
		        	noCriticas.add(tarea);
		        }
		    }
		}
}

