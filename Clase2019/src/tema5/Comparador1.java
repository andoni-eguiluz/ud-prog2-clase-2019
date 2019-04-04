package tema5;

import java.util.Comparator;

/** Comparador de Pelis
 * @author andoni.eguiluz at deusto.es
 */
public class Comparador1 implements Comparator<Peli> {
	@Override
	public int compare(Peli arg0, Peli arg1) {
		return -arg0.getNombre().compareTo( arg1.getNombre() );
	}

}
