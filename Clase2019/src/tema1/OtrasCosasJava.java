package tema1;
/** Algunas otras cosas sobre Java
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class OtrasCosasJava {

	public static void main(String[] args) {
		ejemploIfsEnLinea();
	}
	
	/** Ejemplo del if ternario (if de línea) vs. if normal
	 */
	private static void ejemploIfsEnLinea() {
		// 1.- Retorno con if / else
		System.out.println( valorAbsoluto1( -7 ) );
		// 2.- Retorno con if en línea
		System.out.println( valorAbsoluto2( -7 ) );
	}

		// Implementación con if / else
		private static int valorAbsoluto1( int valor ) {
			if (valor>=0)
				return valor;
			else
				return -valor;
		}
		
		// Implementación con if / else
		private static int valorAbsoluto2( int valor ) {
			return (valor>=0) ? valor : -valor;
		}
		
}
