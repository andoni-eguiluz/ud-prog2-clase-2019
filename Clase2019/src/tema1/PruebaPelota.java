package tema1;

import java.util.Random;

import tema1.ejercicios.Pelota2;

/** Clase de prueba de pelotas - evolutiva mientras revisamos el tema 1
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class PruebaPelota {
	private static int contadorPelotas = 0;
	
	/** Método principal de la clase de prueba de pelotas
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		conceptosDeObjetos();
		conceptosDeObjetos2();
	}

	// Crear pelotas aleatorias en un tablero virtual de 5x5
	private static void conceptosDeObjetos2() {
		int anchoTablero = 200;
		int altoTablero = 150;
		VentanaGrafica v = new VentanaGrafica( anchoTablero*5, altoTablero*5, "Ventana gráfica con pelotas 5x5" );
		Random r = new Random();
		Pelota2[] tablero = new Pelota2[20];
		for (contadorPelotas = 0; contadorPelotas<20;) {
			// Crea pelota nueva
			Pelota2 p = new Pelota2();
			p.x = r.nextInt(5) * anchoTablero + (anchoTablero/2); // Posición aleatoria de centro en 5 filas
			p.y = r.nextInt(5) * altoTablero + (altoTablero/2);  // Posición aleatoria de centro en 5 columnas
			p.radio = 50; // r.nextInt(91) + 10; // Radio aleatorio entre 10 y 100
			boolean existeYa = yaExistePelota( tablero, p, contadorPelotas );
			if (existeYa) { // Esto va a ocurrir un número indeterminado de veces
				System.out.println( "Pelota repetida" );
			} else {
				// Esto se ejecuta 20 veces: se dibuja la pelota y se añade al array
				p.dibuja( v );
				tablero[contadorPelotas] = p;
				contadorPelotas++;
			}
		}
		v.setMensaje( contadorPelotas + " pelotas creadas." );
	}
	
	private static boolean yaExistePelota( Pelota2[] tablero, Pelota2 p, int numPelotas ) {
		for (int i=0; i<numPelotas; i++) {
			if (p.equals(tablero[i])) return true;
		}
		return false;
//		boolean yaExiste = false;
//		for (int i=0; i<numPelotas; i++) {
//			if (p==tablero[i]) {
//				yaExiste = true;
//				break;
//			}
//		}
//		return yaExiste;
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
		visualiza( p2 );
		p.visualiza();
		p2.visualiza();
		// No vale visualiza();
		// Los primitivos no cambian
		int i = 5;
		i = cambia(i);
		i = cambia(i,i);
		System.out.println( i );
		// Conversión inversa de string a algo
		String k = "27";
		int k2 = Integer.parseInt( k );
	}
	// Prueba de ámbitos y paso de parámetros
	private static int cambia(int i) {
		i = 3;
		for (int j=0; j<10; j++)
		{
			System.out.println( j );
		}
		// j = 5;  // No se puede (fuera de ámbito)
		return i+1;
	}
	private static int cambia(int i, int j) {
		return i+j;
	}
	
	private static void visualiza( Pelota2 p ) {
		System.out.println( p.toString() );
		p.x = 0;
	}
	
}
