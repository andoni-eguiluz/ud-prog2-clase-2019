package tema5;

import java.util.HashSet;
import java.util.TreeSet;

public class EjemploProblemasHashYTree {
	public static void main(String[] args) {
		// Sets de contadores
		
		HashSet<Contador> hs = new HashSet<Contador>();
		
		Contador c1 = new Contador( 2 );
		Contador c2 = new Contador( 7 );
		Contador c3 = new Contador( 2 );
		
		hs.add( c1 );
		hs.add( c2 );
		hs.add( c3 );
		
		System.out.println( hs );

		TreeSet<Contador> ts = new TreeSet<Contador>();
		
		ts.add( c1 );
		ts.add( c2 );
		ts.add( c3 );
		
		System.out.println( ts );
		
		// ¿Cómo resolver estos problemas?
	}
}
