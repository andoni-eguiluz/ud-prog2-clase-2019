package tema1;

import java.util.Date;

public class PruebasPrimitivosYObjetos {
	static int j = 5;
	int i = 5;
	public static void main(String[] args) {
		int a = 5;
		System.out.println( a );
		PruebasPrimitivosYObjetos o = new PruebasPrimitivosYObjetos();
		o.i = 3;
		// System.out.println( i );
		
		// Clases todo static por ejemplo
		System.out.println( Math.PI );
		System.out.println( Math.sin( 2.0 ) );
		
		// Clases no static
		Date d1 = new Date();
		System.out.println( d1 );
		System.out.println( d1.getTime() );
		Date d2 = new Date();
		System.out.println( d2 );
		System.out.println( d2.getTime() );
	}
}
