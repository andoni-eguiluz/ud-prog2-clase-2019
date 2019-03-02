package varios;

import java.util.ArrayList;
import java.util.Arrays;

/** Prueba del equals de varias clases
 * @author andoni.eguiluz @ ingenieria.deusto.es
 *
 */
public class PruebaEquals {

	public static void main(String[] args) {
		// Dos objetos diferentes nunca son == pero pueden ser equals
		String s1 = "hola ";
		String s2 = "Andoni";
		String s3 = s1 + s2;  // Aquí hay un new implícito
		String s4 = "hola Andoni";  // Aquí hay otro new implícito
		System.out.println( "s3==s4? " + (s3==s4) + " - equals? " + (s3.equals(s4)) );
		// Un array no comprueba equals de sus objetos, los compara por ==
		String[] a1 = new String[] { s3, s4 };
		String[] a2 = new String[] { s3, s3 };
		System.out.println( "[s3,s4] equals [s3,s3]? " + (a1.equals(a2)) );
		// Un arraylist sí comprueba equals de sus objetos
		ArrayList<String> l1 = new ArrayList<>( Arrays.asList( s3, s4 ) );
		ArrayList<String> l2 = new ArrayList<>( Arrays.asList( s3, s3 ) );
		System.out.println( "AL{s3,s4} equals {s3,s3}? " + (l1.equals(l2)) );
	}

}
