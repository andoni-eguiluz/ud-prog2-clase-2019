package tema1;

/** Solución de ejercicio: tabla de multiplicar (del 0 al 9)
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class TablaMultiplicar {

	/** Método principal
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		tablaSencilla();
		tablaConFormato();
	}
	
	/** Saca a consola la tabla de multiplicar del 0 al 9 de forma sencilla:
	 * 0 x 0 = 0
	 * 0 x 1 = 0
	 * ...
	 */
	private static void tablaSencilla() {
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				System.out.println( i + " x " + j + " = " + i*j ); 
			}
		}
	}

	/** Saca a consola la tabla de multiplicar con formato de tabla:
	 *             0  1  2  3  4  5  6  7  8  9
	 *            -----------------------------
	 * Tabla de 0  0  0  0  0  0  0  0  0  0  0
	 * Tabla de 1  0  1  2  3  4  5  6  7  8  9
	 * ...
	 * Tabla de 9  0  9 18 27 36 45 54 63 72 81
	 */
	private static void tablaConFormato() {
		cabecera();
		for (int i=0; i<10; i++) {
			System.out.print( "Tabla de " + i ); 
			for (int j=0; j<10; j++) {
				String espacios = " ";
				if (i*j<10) espacios = "  ";
				System.out.print( espacios + i*j ); 
			}
			System.out.println();
		}
	}
	
	// Saca a consola la cabecera de la tabla de multiplicar (ver tablaConFormato())
	private static void cabecera() {
		System.out.print( "          " );
		for (int j=0; j<10; j++) {
			System.out.print( "  " + j ); 
		}
		System.out.println();
		System.out.println( "           -----------------------------" );
	}

}
