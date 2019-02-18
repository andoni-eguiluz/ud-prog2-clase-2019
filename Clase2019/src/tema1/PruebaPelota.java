package tema1;

import tema1.ejercicios.Pelota;

public class PruebaPelota {
	public static void main(String[] args) {
		Pelota p = new Pelota();
		p.x = 100;
		p.y = 50;
		System.out.println( p );
		Pelota p2 = p;
		p2.x = 200;
		p2.y = 100;
		System.out.println( p2 );
		System.out.println( p );
		
	}
}
