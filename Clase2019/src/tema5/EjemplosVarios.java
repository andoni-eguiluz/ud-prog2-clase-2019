package tema5;

public class EjemplosVarios {
	public static void main(String[] args) {
		codif();
		parsVars();
	}
	
	private static void codif() {
		// Algo con dos valores
		boolean valores2; // true / false
		boolean sonBlancas;  // blancas/negras
		// Parchis
		int colorParchis; // 0=Rojo, 1=Verde, 2=Azul, 3=Amarillo
		String colorParchisS; // "Rojo", "Verde", "Azul", "Amarillo"
		ColorParchis c;
		c = ColorParchis.AMARILLO;
		ColorParchis c2 = ColorParchis.VERDE;
		// 3 ventajas - visualizar
		System.out.println( c );
		// Hay orden - implements Comparable
		if (c.compareTo(c2)==0) {
			//
		}
		// Se puede recorrer - implementa Iterable (values())
		for (ColorParchis color : ColorParchis.values()) {
			System.out.println( color );
			if ( color == ColorParchis.AMARILLO ) {
				System.out.println( "  Yo soy el amarillo");
			}
		}
	}
	
	private static void parsVars() {
		System.out.println( media(2,5) );
		System.out.println( media(2.0,5.0) );
		System.out.println( media(2.0,5.0,7.0) );
		System.out.println( media(2.0,5.0,7.0,11.0,28.5,43.23) );
	}
	
	private static double media( int v1, int v2 ) {
		return( (v1+v2)/2.0 );
	}
	
	private static double media( double d1, double d2 ) {
		return (d1+d2)/2;
	}
	
	private static double media( double d1, double d2, double... ad ) {
		double suma = d1+d2;
		for (double d : ad) {
			suma += d;
		}
		return suma / (ad.length + 2);
	}
	/* esto sería ambiguo
	private static double media( double... ad ) {
		double suma = 0;
		for (double d : ad) {
			suma += d;
		}
		return suma / (ad.length);
	}
	*/
	
	
	
	
	
	
	
}
