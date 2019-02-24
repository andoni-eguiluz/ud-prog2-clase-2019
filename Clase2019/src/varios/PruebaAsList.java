package varios;
import java.util.Arrays;
import java.util.List;

/** Prueba del método asList de la clase Arrays
 * @author andoni.eguiluz @ ingenieria.deusto.es
 *
 */
public class PruebaAsList {

	public static void main(String[] args) {
		// Sea un array:
		Integer[] nums = { 10, 20, 30, 40, 50 };
		System.out.println( nums ); // El array no es visualizable directamente (no hay un "toString" particular)
		// Se puede "ver como" una lista:
		List<Integer> l = Arrays.asList( nums );
		System.out.println( l );  // Se puede y se visualiza como una lista
		for (int i : l) System.out.print( i + " " );  // También se puede recorrer como una lista
		if (l.contains( 20 )) System.out.println( "Se puede buscar el 20!" );
		l.set( 1, 12 ); // Incluso se puede modificar algún elemento
		System.out.println( l );
		// Pero ojo...
		l.clear(); // No se puede: java.lang.UnsupportedOperationException
		l.add( 2, 15 );  // No se puede: java.lang.UnsupportedOperationException
		l.remove( 2 );  // No se puede: java.lang.UnsupportedOperationException
		// En general no se puede modificar cuando cambia el TAMAÑO del array que hay de base
	}

}
