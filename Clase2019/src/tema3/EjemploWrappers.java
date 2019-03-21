package tema3;

import java.awt.Point;
import java.util.ArrayList;

public class EjemploWrappers {
	
	public static void main(String[] args) {
		// Object polimorfismo total
		Object o = new String("A");  // "A"
		System.out.println( o );
		o = new Point(2,5);
		System.out.println( o.toString() );
		o = new Fruta();
		System.out.println( o );
		
		Object[] ao = new Object[5];
		ao[0] = "A";
		ao[1] = new Point(1,4);
		
		ArrayList<Object> alo = new ArrayList<>();
		alo.add( "A" );
		alo.add( new Point(1,4) );
		
		// Object guarda de todo
		// ¿Y los primitivos?
		// Con wrappers
		
		alo.add( new Integer(5) );
		alo.add( new Double(3.0) );
		alo.add( new Boolean(false) );
		alo.add( new Character('a') );
		
		o = new Integer(5);
		ao[1] = new Integer(5);
		
		System.out.println( alo );
		System.out.println( ao[1] );
		
		int total = 0;
		for (Object object : alo) {
		// for (int i=0; i<alo.size(); i++) {
		// 		Object object = alo.get(i);
			if (object instanceof Integer) {
				total += ((Integer)object).intValue();
			}
			System.out.println( object.toString() );
		}
		
		float f = 5.7f;
		int i2 = Math.round(f);
		
		// Ayuditas
		// Unboxing automático - conversión automática de wrapper al prim
		i2 = ((Integer)ao[1]); //.intValue()
		
		Integer objetoInt = new Integer(7);
		int i3 = i2 + objetoInt; //.intValue() unboxing automático
		Integer i4 = i3; // new Integer(i3);  boxing automático
		
		alo.add( 7 );  // new Integer(7) boxing automático
		alo.add( 8.5 );
		
		System.out.println( alo );
		for (Object o2 : alo) {
			System.out.println( o2.getClass() );
		}
		
		// Wrappers - qué más?
		// Algunas constantes
		System.out.println( Integer.MAX_VALUE );
		// Conversores
		String dato = "17";
		int otroi = Integer.parseInt( dato );
	}
	
}
