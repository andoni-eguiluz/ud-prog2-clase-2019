package tema1;

/** Vista rápida de gestión de memoria en Java
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class GestionMemoria {
	
	// La devolución de freeMemory() no es siempre precisa y depende de todo el sistema Java, no solo
	// de nuestro código, con lo que no es muy aconsejable usarla para mediciones exactas de ocupación de memoria.
	// Pero nos vale para echar un primer vistazo a qué está dentro de heap y qué no:
	
	public static void main(String[] args) {
		System.out.println( "(a) " + Runtime.getRuntime().freeMemory() + " bytes" );
		String s[] = null;
		System.out.println( "(b) " + Runtime.getRuntime().freeMemory() + " bytes" );
		s = new String[10000];
		System.out.println( "(c) " + Runtime.getRuntime().freeMemory() + " bytes" );
		for (int i = 0; i<s.length; i++) {
			s[i] = "" + i;
		}
		System.out.println( "(d) " + Runtime.getRuntime().freeMemory() + " bytes" );
		s = null;  // Se quita la referencia
		System.out.println( "(e) " + Runtime.getRuntime().freeMemory() + " bytes" );
		System.gc();  // Garbage collector
		System.out.println( "(f) " + Runtime.getRuntime().freeMemory() + " bytes" );
	}
	
}
