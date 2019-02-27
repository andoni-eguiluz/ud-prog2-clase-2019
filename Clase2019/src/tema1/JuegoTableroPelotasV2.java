package tema1;

import java.awt.Point;
import java.util.Random;

import tema1.ejercicios.Pelota4;

/** Planteamiento de hipotético juego de "fichas" redondas en un tablero de 5x5<br>
 * Solución de ejercicio 1.9
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class JuegoTableroPelotasV2 {
	private static int contadorPelotas = 0;
	private static final int ANCHO_CASILLA = 200;
	private static final int ALTO_CASILLA = 150;
	private static int numPelotasEnTablero = 21;
	
	/** Método principal
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		lanzaTablero();
	}

	private static final char[] COLORES_POSIBLES = { 'a', 'v', 'r' }; 
	// Crear pelotas aleatorias en un tablero virtual de 5x5
	private static void lanzaTablero() {
		VentanaGrafica v = new VentanaGrafica( ANCHO_CASILLA*5, ALTO_CASILLA*5, "Mueve las fichas con drag & drop" );
		Random r = new Random();
		Pelota4[] tablero = new Pelota4[numPelotasEnTablero];
		for (contadorPelotas = 0; contadorPelotas<numPelotasEnTablero;) {
			// Crea pelota nueva
			// Con constructor por defecto sería:
			//  Pelota4 p = new Pelota4();
			//	p.x = r.nextInt(5) * ANCHO_CASILLA + (ANCHO_CASILLA/2); // Posición aleatoria de centro en 5 filas
			//	p.y = r.nextInt(5) * ALTO_CASILLA + (ALTO_CASILLA/2);  // Posición aleatoria de centro en 5 columnas
			//	p.radio = r.nextInt(21) + 50; // Radio aleatorio entre 50 y 70
			//	p.color = COLORES_POSIBLES[ r.nextInt( COLORES_POSIBLES.length ) ];
			// Con constructor con parámetros:
			Pelota4 p = new Pelota4(
				r.nextInt(21) + 50, // Radio aleatorio entre 50 y 70
				r.nextInt(5) * ANCHO_CASILLA + (ANCHO_CASILLA/2), // Posición aleatoria de centro en 5 filas
				r.nextInt(5) * ALTO_CASILLA + (ALTO_CASILLA/2),  // Posición aleatoria de centro en 5 columnas
				COLORES_POSIBLES[ r.nextInt( COLORES_POSIBLES.length ) ]
			);
			boolean existeYa = yaExistePelota( tablero, p, contadorPelotas );
			if (existeYa) { // Esto va a ocurrir un número indeterminado de veces
				System.out.println( "Pelota repetida" );
			} else {
				// Esto se ejecuta 'n' veces: se dibuja la pelota y se añade al array
				p.dibuja( v );
				tablero[contadorPelotas] = p;
				contadorPelotas++;
			}
		}
		v.setMensaje( contadorPelotas + " pelotas creadas." );
		moverPelotas( v, tablero );
	}
	
	// Mueve las pelotas en pantalla
	private static void moverPelotas( VentanaGrafica v, Pelota4[] tablero ) {
		// v.setDibujadoInmediato( false );  // Notar la mejoría de "vibración" si se hace esto y (2)
		while (!v.estaCerrada()) {
			Point puls = v.getRatonPulsado();
			if (puls!=null) {
				Pelota4 pelotaPulsada = hayPelotaPulsadaEn( puls, tablero );
				if (pelotaPulsada!=null) {
					double coordInicialX = pelotaPulsada.getX();
					double coordInicialY = pelotaPulsada.getY();
					v.espera(20); // Espera un poquito (si no pasa todo demasiado rápido)
					// Hacer movimiento hasta que se suelte el ratón
					Point drag = v.getRatonPulsado();
					while (drag!=null) {
						pelotaPulsada.borra( v );
						pelotaPulsada.incXY( drag.x - puls.x, drag.y - puls.y );
						pelotaPulsada.dibuja( v );
						repintarTodas(v, tablero);  // Notar la diferencia si no se hace esto (se van borrando las pelotas al pintar otras que pasan por encima)
						// v.repaint(); // (2)
						puls = drag;
						drag = v.getRatonPulsado();
					}
					// Recolocar pelota en un sitio válido
					recolocar( v, pelotaPulsada, tablero, coordInicialX, coordInicialY );
				}
			}
		}
	}
	
	private static void repintarTodas( VentanaGrafica v, Pelota4[] tablero ) {
		for (int i=0; i<tablero.length; i++) {
			tablero[i].dibuja( v );
		}
	}
	
	private static Pelota4 hayPelotaPulsadaEn( Point punto, Pelota4[] tablero ) {
		Pelota4 pelotaPulsada = null;
		double distanciaMinima = Double.MAX_VALUE;
		for (Pelota4 p : tablero) {
			double dist = Math.sqrt( Math.pow( p.getX()-punto.x, 2) + Math.pow( p.getY()-punto.y, 2) );
			if (dist <= p.getRadio() && dist < distanciaMinima) {   // Pulsación dentro de la pelota
				pelotaPulsada = p;
				distanciaMinima = dist;
			}
		}
		return pelotaPulsada;
	}
	
	private static void recolocar( VentanaGrafica v, Pelota4 pelota, Pelota4[] tablero, double xDonde, double yDonde ) {
		// 1.- Buscar el sitio del tablero del cual está más cerca el centro de nuestra pelota
		double distMin = Double.MAX_VALUE;
		int filaMasCerca = -1;
		int colMasCerca = -1;
		int xMasCerca = -1;
		int yMasCerca = -1;
		for (int fila=0; fila<5; fila++) {
			for (int col=0; col<5; col++) {
				int coordX = ANCHO_CASILLA * col + (ANCHO_CASILLA/2);
				int coordY = ALTO_CASILLA * fila + (ALTO_CASILLA/2);
				double dist = Math.sqrt( Math.pow( coordX-pelota.getX(), 2 ) + Math.pow( coordY-pelota.getY(), 2 ) );
				if (dist < distMin) {
					distMin = dist;
					filaMasCerca = fila;
					colMasCerca = col;
					xMasCerca = coordX;
					yMasCerca = coordY;
				}
			}
		}
		System.out.println( "Pelota llevada a casilla (" + filaMasCerca + "," + colMasCerca + ")" );
		// 2.- Comprobar si ese sitio está vacío
		boolean sitioLibre = true;
		for (Pelota4 p : tablero) {
			if (p.getX()==xMasCerca && p.getY()==yMasCerca) {  // Esta pelota ocupa ese sitio
				sitioLibre = false;
				System.out.println( "La pelota " + p + " ya ocupa ese espacio. No se puede mover" );
				break;
			}
		}
		// 3.- Mover al sitio que corresponda (casilla exacta si está libre o casilla inicial si está ocupada)
		if (sitioLibre) {
			xDonde = xMasCerca;
			yDonde = yMasCerca;
		}
		double xInicial = pelota.getX();
		double yInicial = pelota.getY();
		// Hacemos el movimiento que dure medio segundo, dibujando 50 veces en medio segundo (un movimiento cada 10 milésimas)
		for (int fotograma=1; fotograma<=50; fotograma++) {
			v.espera(10);
			double progresionX = xInicial + (xDonde - xInicial)/50.0 * fotograma;  // Importante la operativa con doubles porque si no el redondeo a enteros quita mucha precisión 
			double progresionY = yInicial + (yDonde - yInicial)/50.0 * fotograma;  // Importante la operativa con doubles porque si no el redondeo a enteros quita mucha precisión 
			pelota.borra( v );
			pelota.setX( Math.round( progresionX ) );
			pelota.setY( Math.round( progresionY ) );
			// pelota.dibuja( v );
			repintarTodas(v, tablero);
		}
	}

	
	private static boolean yaExistePelota( Pelota4[] tablero, Pelota4 p, int numPelotas ) {
		for (int i=0; i<numPelotas; i++) {
			if (p.equals(tablero[i])) return true;
		}
		return false;
		// Otra opción equivalente:
		//		boolean yaExiste = false;
		//		for (int i=0; i<numPelotas; i++) {
		//			if (p==tablero[i]) {
		//				yaExiste = true;
		//				break;
		//			}
		//		}
		//		return yaExiste;
	}

}
