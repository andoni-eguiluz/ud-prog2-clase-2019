package varios;

/** Demostración de funcionamiento de operadores a nivel de bit en Java
 * Se utilizan muy poco pero de vez en cuando sale algún uso interesante...
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class OperadoresDeBit {

	public static void main(String[] args) {
		int test = 0b100000; // 32 en binario
		System.out.println( "Binario original: " + bin32(test) );
		int res = test & 0b1111111;
		System.out.println( "& con 11111111: " + bin32(res) );
		res = test | 0b1111111;
		System.out.println( "| con 11111111: " + bin32(res) );
		res = test ^ 0b1111111;
		System.out.println( "^ con 11111111: " + bin32(res) );
		res = ~ test;
		System.out.println( "~: " + Integer.toBinaryString(res) );
		test = -32;
		System.out.println( "Binario original con signo negativo: " + bin32(test) );
		System.out.println( "8 Desplazamientos sucesivos << de 5 posiciones (desp. aritmético)");
		res = test;
		for (int i=0; i<8; i++) {
			res = res << 5;  // 5 posiciones
			System.out.println( "  " + bin32(res) );
		}
		System.out.println( "8 Desplazamientos sucesivos >> de 5 posiciones (desp. aritmético)");
		res = test;
		for (int i=0; i<8; i++) {
			res = res >> 5;
		System.out.println( "  " + bin32(res) );
		}
		System.out.println( "8 Desplazamientos sucesivos >>> de 5 posiciones (desp. lógico)");
		res = test;
		for (int i=0; i<8; i++) {
			res = res >>> 5;
			System.out.println( "  " + bin32(res) );
		}

	}
	
	// Devuelve el valor int en formato binario rellenado a ceros
	private static String bin32( int valor ) {
		return String.format( "%32s", Integer.toBinaryString(valor)).replaceAll(" ", "0");
	}

}
