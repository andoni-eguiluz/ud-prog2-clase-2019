package tema5;

import java.util.*;

/** Ejemplo de colecciones de Java Collection
 * Partiendo de equipos de fútbol recopilados de la web
 * @author andoni.eguiluz @ ingenieria.deusto.es
 *
 */
public class EjemploJavaCollections {
	
	public static void main(String[] args) {
		buscaEquipos();
	}
	
	// Buscamos equipos en una página de calendario de marca.com (previamente hemos analizado el html
	// para ver dónde encontrar a los equipos)
	private static void buscaEquipos() {
		ArrayList<String> l = ProcesaURLs.buscaEnWeb( "https://www.marca.com/futbol/primera-division/calendario.html", 
				"<img src=\"https://e00-marca.uecdn.es/assets/", 
				"iso-8859-15" );
		procesa( l );
	}
	
	private static void procesa( ArrayList<String> l ) {
		for (String linea : l) {
			// Buscar esta parte: alt="Leganés"/>
			int posi1 = linea.indexOf( "alt=\"" );
			int posi2 = linea.indexOf( "\"", posi1+5 );
			// System.out.println( posi1 + " - " + posi2 );
			String equipo = linea.substring( posi1+5, posi2 );
			procesaEquipo( equipo );
		}
		finProceso();
	}
	
	// Vamos a trabajar los equipos con distintas estructuras para probarlas
	private static ArrayList<String> lEquipos = new ArrayList<>();
	private static HashSet<String> hsEquipos = new HashSet<>();
	private static TreeSet<String> tsEquipos = new TreeSet<>();
	private static HashMap<String,Integer> hmEquipos = new HashMap<>();

	// Proceso de cada equipo
	private static void procesaEquipo( String equipo ) {
		// Primera estructura arraylist - con posición (índice)
		if (!lEquipos.contains(equipo)) { // No repetir requiere hacer una comprobación de si ya existe
			lEquipos.add( equipo );
		}
		// Segunda opción set
		hsEquipos.add( equipo ); // Automáticamente NO SE REPITE (hashset = sin orden). Eficiencia máxima
		// Treeset - solo para Comparable
		tsEquipos.add( equipo ); // NO SE REPITE (treeset = con orden -Comparable-). Eficiencia importante (más lento que el hash, pero con orden)
		// Mapas:  ¿cómo calcular por ejemplo el número de veces que aparece cada equipo?
		// hmEquipos.put( clave, valor )
		// hmEquipos.get( clave ) -> valor  (null si no está) 
	}
	
	private static void finProceso() {
		System.out.println( "Equipos sin duplicados" );
		System.out.println( "Usando AL: " + lEquipos.size() + " - " + lEquipos );
		System.out.println( "Usando HS: " + hsEquipos.size() + " - " + hsEquipos );
		System.out.println( "Usando TS: " + tsEquipos.size() + " - " + tsEquipos );
		
		System.out.println( "Recorrido. Con arraylist:" );
		for (int i=0; i<lEquipos.size(); i++) {
			System.out.println( "  " + lEquipos.get(i) );
			// No hay posición en los sets tsEquipos.get
		}
		System.out.println( "Recorrido. Con treeset:" );
		for (String equipo : tsEquipos) {
			System.out.println( "  " + equipo );
		}
	}
	
}
