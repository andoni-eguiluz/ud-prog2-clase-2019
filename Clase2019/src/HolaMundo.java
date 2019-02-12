/** Primera clase con pruebas básicas de sintaxis Java, variables, tipos
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class HolaMundo {
	
	public static void main(String[] args) {
		System.out.println( "¡Hola y alegría mundo!" );  // \n \t \\
		pruebasTipos();
	}
	
	private static void pruebasTipos() {
		// Primitivos: números, caracteres, boolean
		byte b = 5;   // 1 byte
		short s = 5;  // 2 bytes
		int i = 5;    // 4 bytes
		long l = 5;   // 8 bytes
		float f = 5;
		double d = 5;
		System.out.println( b + " máx " + Byte.MAX_VALUE );  // conv.implícita de byte a string y concatenación de strings
		System.out.println( s + " máx " + Short.MAX_VALUE ); // Lo mismo en todas estas (short a string, int a string...)
		System.out.println( i + " máx " + Integer.MAX_VALUE );
		System.out.println( l + " máx " + Long.MAX_VALUE );
		System.out.println( System.currentTimeMillis() );
		// System.out.println( new Date( 0 ) );   //  Si quisiéramos ver cómo se interpreta el long en una fecha
		// System.out.println( new Date( Long.MAX_VALUE ) );  // Máxima fecha que se puede representar con longs (milisegundos desde el 1/1/1970)
		System.out.println( f + " máx " + Float.MAX_VALUE );
		System.out.println( d + " máx " + Double.MAX_VALUE );
		char c = 'á';
		boolean bo = true; // o false
		System.out.println( "carácter " + c );
		System.out.println( "booleano " + bo );
	}
	
}
