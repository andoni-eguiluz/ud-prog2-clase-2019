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
	private static HashMap<String,Integer> hmEquipos = new HashMap<String, Integer>();
	private static TreeMap<String,Contador> tmEquipos = new TreeMap<>();
	private static TreeMap<String,ArrayList<String>> tmPartidos = new TreeMap<>();

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
		// Quiero ir calculando el nº de veces que aparece cada equipo
		if (hmEquipos.containsKey( equipo )) {
			int valor = hmEquipos.get( equipo ).intValue(); // podríamos hacer inc si se pudiera
			valor++;
			hmEquipos.put( equipo, new Integer(valor) );  // sustituye
		} else {
			hmEquipos.put( equipo, new Integer(1) );
		}
		// Versión 2 - utilizando integers no inmutables
		Contador c = tmEquipos.get( equipo );
		if (c==null) {
			Contador c2 = new Contador();
			tmEquipos.put( equipo, c2 );
		} else {
			// tmEquipos.get( equipo ).inc();
			c.inc();
			// c = new Contador( c.get() + 1 );  // NO FUNCIONARIA
		}
		
		// Sacar lista de partidos
		if (primerEquipo==null) {  // Equipo impar
			primerEquipo = equipo;
		} else {  // Equipo par
			procesaPartido( primerEquipo, equipo );
			primerEquipo = null;
		}
	}
		private static String primerEquipo = null;
		
	private static void procesaPartido( String eq1, String eq2 ) {
		// Actualiza el partido en la lista del equipo 1
		ArrayList<String> l1 = tmPartidos.get( eq1 );
		if (l1==null) {
			l1 = new ArrayList<String>();
			tmPartidos.put( eq1, l1 );
		}
		l1.add( eq1 );
		l1.add( eq2 );
		// Actualiza el partido en la lista del equipo 2
		ArrayList<String> l2 = tmPartidos.get( eq2 );
		if (l2==null) {
			l2 = new ArrayList<String>();
			tmPartidos.put( eq2, l2 );
		}
		l2.add( eq1 );
		l2.add( eq2 );
		
		// TAREA - Cómo hacer esto con objetos "Partido" que contengan
		// dos equipos cada uno? (y también los resultados [deberás investigar el html para eso])
		
		
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
		// Visualizar y comprobar que hay siempre 38
		System.out.println( hmEquipos );
		// hmEquipos.keySet() - claves
		// hmEquipos.values() - valores
		for (Integer numPartidos : hmEquipos.values()) {
			if (numPartidos!=38) {
				System.out.println( "Error: " + numPartidos + " en vez de 38" );
			}
		}
		
		System.out.println( tmEquipos );
		
		System.out.println( tmPartidos );
		for (String equipo : tmPartidos.keySet()) {
			System.out.println( equipo + " -> " + tmPartidos.get(equipo) );
		}
	}
	
}
