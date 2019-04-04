package tema5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class PruebasUtilidadesJavaCollections {
	public static void main(String[] args) {
		ArrayList<Peli> l = new ArrayList<Peli>();
		// Facilitar las listas de valores
		inicializarValores( l );
		// Ordenación
		ordenar( l );
		// Otras utilidades
		otras( l );
	}
	
	private static void otras( ArrayList<Peli> l ) {
		Peli p1 = new Peli( "Green Book", 2018 );
		Peli p2 = new Peli( "La Favorita", 2018 );
		System.out.println();
		Collections.sort( l );
		System.out.println( l );
		Collections.reverse( l );
		System.out.println( l );
		Collections.shuffle( l );
		System.out.println( l );
		// Invertir el 0 y el 2
		Peli temp = l.get( 0 );
		l.set( 0, l.get(2) );
		l.set( 2, temp );
		System.out.println( l );
		// Inversión con Collections
		Collections.swap( l, 0, 2 );
		System.out.println( l );
		// Búsqueda binaria - solo en estructuras de comparables y YA ORDENADAS
		Collections.sort( l );
		System.out.println();
		System.out.println( l );
		int res1 = Collections.binarySearch( l, p1 );
		int res2 = Collections.binarySearch( l, p2 );
		System.out.println( res1 );
		System.out.println( res2 );
		// Positivo n -> está en la posición n-1
		// Negativo -n -> se podría insertar en la posición (n-1)
	}
	
	private static void ordenar( ArrayList<Peli> l ) {
		Collections.sort( l );
		System.out.println( l );
		// Ordenar de distintas maneras?
		Comparator<Peli> comparador = new Comparador1();
		Collections.sort( l, comparador );
		System.out.println( l );
		Comparator<Peli> comparador2 = new Comparador2();
		Collections.sort( l, comparador2 );
		System.out.println( l );
		// l.sort( comparador2 );
	}
	
	private static void inicializarValores( ArrayList<Peli> l ) {
		Peli p1 = new Peli( "Avengers: EndGame", 2019 );
		Peli p2 = new Peli( "Green Book", 2018 );
		Peli p3 = new Peli( "Roma", 2018 );
		// Inicialización array
		Peli[] ap = new Peli[] { p1, p2, p3 };
		// Inicialización collection
		// Manera 1
		l.add( p1 );
		l.add( p2 );
		l.add( p3 );
		// Manera 2
		l.addAll( Arrays.asList( ap ) );
		// Manera 3
		l.addAll( Arrays.asList( p1, p2, p3 ) );
		System.out.println( l );
	}
}
