package tema1;

import java.awt.Point;
import java.util.Random;

import tema1.ejercicios.Pelota3;

/** Planteamiento de hipotético juego de "fichas" redondas en un tablero de 5x5<br>
 * Solución de ejercicio 1.9
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class JuegoTableroPelotas {
	private static int contadorPelotas = 0;
	private static int anchoTablero = 200;
	private static int altoTablero = 150;
	
	/** Método principal
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		lanzaTablero();
	}

	private static final char[] COLORES_POSIBLES = { 'a', 'v', 'r' }; 
	// Crear pelotas aleatorias en un tablero virtual de 5x5
	private static void lanzaTablero() {
		VentanaGrafica v = new VentanaGrafica( anchoTablero*5, altoTablero*5, "Mueve las fichas con drag & drop" );
		Random r = new Random();
		Pelota3[] tablero = new Pelota3[20];
		for (contadorPelotas = 0; contadorPelotas<20;) {
			// Crea pelota nueva
			Pelota3 p = new Pelota3();
			p.x = r.nextInt(5) * anchoTablero + (anchoTablero/2); // Posición aleatoria de centro en 5 filas
			p.y = r.nextInt(5) * altoTablero + (altoTablero/2);  // Posición aleatoria de centro en 5 columnas
			p.radio = r.nextInt(21) + 50; // Radio aleatorio entre 50 y 70
			p.color = COLORES_POSIBLES[ r.nextInt(3) ];
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
		moverPelotas( v, tablero );
	}
	
	private static void moverPelotas( VentanaGrafica v, Pelota3[] tablero ) {
		// v.setDibujadoInmediato( false );  // Notar la mejoría de "vibración" si se hace esto y (2)
		while (!v.estaCerrada()) {
			Point puls = v.getRatonPulsado();
			if (puls!=null) {
				Pelota3 pelotaPulsada = hayPelotaPulsadaEn( puls, tablero );
				if (pelotaPulsada!=null) {
					double coordInicialX = pelotaPulsada.x;
					double coordInicialY = pelotaPulsada.y;
					v.espera(20); // Espera un poquito (si no pasa todo demasiado rápido)
					// Hacer movimiento hasta que se suelte el ratón
					Point drag = v.getRatonPulsado();
					while (drag!=null) {
						pelotaPulsada.borra( v );
						pelotaPulsada.x += (drag.x - puls.x);
						pelotaPulsada.y += (drag.y - puls.y);
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
	
	private static void repintarTodas( VentanaGrafica v, Pelota3[] tablero ) {
		for (int i=0; i<tablero.length; i++) {
			tablero[i].dibuja( v );
		}
	}
	
	private static Pelota3 hayPelotaPulsadaEn( Point punto, Pelota3[] tablero ) {
		Pelota3 pelotaPulsada = null;
		double distanciaMinima = Double.MAX_VALUE;
		for (Pelota3 p : tablero) {
			double dist = Math.sqrt( Math.pow( p.x-punto.x, 2) + Math.pow( p.y-punto.y, 2) );
			if (dist <= p.radio && dist < distanciaMinima) {   // Pulsación dentro de la pelota
				pelotaPulsada = p;
				distanciaMinima = dist;
			}
		}
		return pelotaPulsada;
	}
	
	private static void recolocar( VentanaGrafica v, Pelota3 pelota, Pelota3[] tablero, double xDonde, double yDonde ) {
		// 1.- Buscar el sitio del tablero del cual está más cerca el centro de nuestra pelota
		double distMin = Double.MAX_VALUE;
		int filaMasCerca = -1;
		int colMasCerca = -1;
		int xMasCerca = -1;
		int yMasCerca = -1;
		for (int fila=0; fila<5; fila++) {
			for (int col=0; col<5; col++) {
				int coordX = anchoTablero * col + (anchoTablero/2);
				int coordY = altoTablero * fila + (altoTablero/2);
				double dist = Math.sqrt( Math.pow( coordX-pelota.x, 2 ) + Math.pow( coordY-pelota.y, 2 ) );
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
		for (Pelota3 p : tablero) {
			if (p.x==xMasCerca && p.y==yMasCerca) {  // Esta pelota ocupa ese sitio
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
		double xInicial = pelota.x;
		double yInicial = pelota.y;
		// Hacemos el movimiento que dure medio segundo, dibujando 50 veces en medio segundo (un movimiento cada 10 milésimas)
		for (int fotograma=1; fotograma<=50; fotograma++) {
			v.espera(10);
			double progresionX = xInicial + (xDonde - xInicial)/50.0 * fotograma;  // Importante la operativa con doubles porque si no el redondeo a enteros quita mucha precisión 
			double progresionY = yInicial + (yDonde - yInicial)/50.0 * fotograma;  // Importante la operativa con doubles porque si no el redondeo a enteros quita mucha precisión 
			pelota.borra( v );
			pelota.x = Math.round( progresionX );
			pelota.y = Math.round( progresionY );
			// pelota.dibuja( v );
			repintarTodas(v, tablero);
		}
	}

	
	private static boolean yaExistePelota( Pelota3[] tablero, Pelota3 p, int numPelotas ) {
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
