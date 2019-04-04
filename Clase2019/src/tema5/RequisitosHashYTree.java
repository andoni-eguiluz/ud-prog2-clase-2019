package tema5;

import java.util.HashSet;
import java.util.TreeSet;

public class RequisitosHashYTree {
	public static void main(String[] args) {
		// Prueba de hashset
		HashSet<Peli> listaPelis = new HashSet<Peli>();
		Peli p1 = new Peli( "Avengers Endgame" );
		Peli p2 = new Peli( "Green Book" );
		Peli p3 = new Peli( "Avengers Endgame" );
		Peli p4 = new Peli( "Roma" );
		listaPelis.add( p1 );
		listaPelis.add( p2 );
		listaPelis.add( p3 );
		listaPelis.add( p4 );
		System.out.println( p1.hashCode() ); // Tiene que estar bien definida la función hashCode
		System.out.println( p2.hashCode() );
		System.out.println( p3.hashCode() ); // Este tiene que ser igual al de p1
		System.out.println( p1.equals( p3 ) ); // Y tiene que estar bien definido el equals (true)
		System.out.println( listaPelis ); // Entonces sí funciona bien el hashset
		
		TreeSet<Peli> treePelis = new TreeSet<>();
		treePelis.add( p1 ); // Tiene que estar definido Peli como Comparable<Peli>
		treePelis.add( p2 );
		treePelis.add( p3 );
		treePelis.add( p4 );
		System.out.println( p1.compareTo(p4) ); // Si p1<p4 devuelve negativo, si es > positivo, si son == 0
		System.out.println( treePelis );
		
	}
}
