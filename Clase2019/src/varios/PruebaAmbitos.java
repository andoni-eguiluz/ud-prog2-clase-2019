package varios;

/** Prueba de ámbitos de variables
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class PruebaAmbitos {
	public static int iGlobal = 5;  // Ámbito global (de clase)
	
	public static void main(String[] args) {
		PruebaAmbitos p = new PruebaAmbitos();
		p.prueba( 2 );
	}

	
	private int i = 1;  // Ámbito de objeto (atributo)
	
	public void prueba( int i ) {
		System.out.println( this.i );  // Variable de objeto (dura toda la vida del objeto)
		     // (solo hace falta this si hay ambigüedad como aquí)
		System.out.println( i );       // Variable de método - parámetro  (se acaba al acabar el método)
		int j = 2;  // No puede ser i (mismo ámbito que el parámetro)  
		System.out.println( j );       // Variable de método - local  (también se acaba al acabar el método)
		{
			int k = 3; 
			System.out.println( k );   // Variable de bloque - solo en el bloque 
		}
		// System.out.println( k );  // Aquí k no se puede usar. i, j sí
	}
	
	
}
