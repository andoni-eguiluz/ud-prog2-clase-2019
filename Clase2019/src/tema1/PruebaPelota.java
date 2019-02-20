package tema1;

import java.util.Random;

import tema1.ejercicios.Pelota2;

/** Clase de prueba de pelotas - evolutiva mientras revisamos el tema 1
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class PruebaPelota {
	private static int contadorPelotas = 0;
	public static void main(String[] args) {
		conceptosDeObjetos();
		conceptosDeObjetos2();
	}

	// Crear pelotas aleatorias
	private static void conceptosDeObjetos2() {
		VentanaGrafica v = new VentanaGrafica( 1000, 700, "Ventana gráfica con pelotas" );
		Random r = new Random();
		for (int i=0; i<50; i++) {
			Pelota2 p = new Pelota2();
			contadorPelotas++;
			p.x = r.nextInt(1000);
			p.y = r.nextInt(700);
			p.radio = r.nextInt(91) + 10;
			p.color = 'a';
			p.dibuja( v );
		}
		v.setMensaje( contadorPelotas + " pelotas creadas." );
	}

	// Conceptos básicos de objetos
	private static void conceptosDeObjetos() {
		Pelota2 p = new Pelota2();
		p.x = 100;
		p.y = 50;
		System.out.println( p );
		Pelota2 p2 = p;
		// Aliasing
		p2.x = 200;
		p2.y = 100;
		visualiza( p );
		PruebaPelota.visualiza( p );
		// visualiza( p2 );
		p.visualiza();
		p2.visualiza();
		// No vale visualiza();
	}
	
	private static void visualiza( Pelota2 p ) {
		System.out.println( p );
	}
	
}
