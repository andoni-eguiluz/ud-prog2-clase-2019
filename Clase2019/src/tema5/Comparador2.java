package tema5;

import java.util.Comparator;

/** Comparador de Pelis
 * @author andoni.eguiluz at deusto.es
 */
public class Comparador2 implements Comparator<Peli> {
	@Override
	public int compare(Peli arg0, Peli arg1) {
		if (arg0.getAnyo()!=arg1.getAnyo()) {
			return arg0.getAnyo() - arg1.getAnyo(); // negativo si 1<2, positivo si 1>2, 0 si 1==2
		}
		return arg0.getNombre().compareTo( arg1.getNombre() );
	}

}
