package tema1.ejerciciosResueltos;

import java.awt.Point;
import java.util.Arrays;
import java.util.Random;
import tema1.VentanaGrafica;

/** Planteamiento de hipotético juego de "fichas" redondas en un tablero de 4x4, 5x5, 6x6...<br>
 * Solución de ejercicio 1.9
 * Con unas reglas de juego sencillas (quita las "líneas" de fichas del mismo color, de 3
 * o más en fila o columna). El juego consiste en quitar el mayor número posible de fichas,
 * en el menor número posible de movimientos. Cuantas más fichas grandes se quiten, mejor
 * Si en algún nivel quedan <n-2> pelotas de algún color, el juego se pierde
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class JuegoTableroPelotas {
	// private static int contadorPelotas = 0; // Deja de ser necesario (va en el tablero)
	private static final int ANCHO_CASILLA = 150;  // Constante para el ancho de cada casilla en píxels
	private static final int ALTO_CASILLA = 120;   // Constante para el alto de cada casilla en píxels
	private static final int RADIO_MAXIMO = ALTO_CASILLA/2;    // Radio máximo de cada pieza
	private static final int RADIO_MINIMO = 20;    // Radio mínimo de cada pieza
	private static final Character[] COLORES_POSIBLES = { 'a', 'v', 'r' };  // Colores posibles de cada pelota
	private static final int PUNTOS_POR_MOVIMIENTO = 25; // Puntos que se quitan por cada movimiento
	private static final int PUNTOS_BONUS = 500; // Puntos de bonus si se vacía el tablero completo

	private static VentanaGrafica v;               // Ventana de juego
	private static GrupoPelotas tablero;           // Tablero (grupo de pelotas) de juego
	private static Random r;                       // Generador de aleatorios
	private static int tamanyoTablero = 4;         // Tamaño (filas y columnas) del tablero (cambia de acuerdo al nivel)
	private static int numPelotasEnTablero = 15;   // Número inicial de pelotas en tablero (cambia de acuerdo al nivel)
	private static int nivel;                      // Nivel del juego
	private static int puntuacion;                 // Puntuacion del juego
	private static int numMovimientos;             // Número de movimientos
	 
	/** Método principal
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		// Pelota[] tablero = new Pelota[numPelotasEnTablero]; // Sustituido por el objeto:
		r = new Random();
		nivel = 1;
		puntuacion = 0;
		boolean sigueElJuego = true;
		do {
			if (nivel>1 && nivel%2==1) {  // Si el nivel es impar sube la dificuldad
				tamanyoTablero++;
				numPelotasEnTablero = tamanyoTablero*tamanyoTablero - 1;  // Se empieza siempre con un solo hueco
			}
			v = new VentanaGrafica( ANCHO_CASILLA*tamanyoTablero, ALTO_CASILLA*tamanyoTablero, "Mueve las fichas con drag & drop" );
			tablero = new GrupoPelotas(numPelotasEnTablero);
			lanzaTablero();
			sigueElJuego = juega();
			if (!sigueElJuego) {
				v.setMensaje( "Oooohh... derrota!!! Vuelve pronto! Puntuación final: " + puntuacion );
				v.espera( 5000 );
			} else {
				if (tablero.size()==0) {  // Si se dejan 0 pelotas, bonus de 500 puntos
					puntuacion += PUNTOS_BONUS;
				}
			}
			v.acaba();  // Cierra la ventana de ese nivel
			nivel++;
		} while (sigueElJuego);
	}

	
	// Crear pelotas aleatorias en un tablero virtual de 5x5
	private static void lanzaTablero() {
		// for (contadorPelotas = 0; contadorPelotas<numPelotasEnTablero;) { // Cambiado porque ahora el contador va dentro del objeto
		while (tablero.size()<tablero.tamMaximo()) {
			// Crea pelota nueva
			// Con constructor por defecto sería:
			//  Pelota p = new Pelota();
			//	p.x = r.nextInt(5) * ANCHO_CASILLA + (ANCHO_CASILLA/2); // Posición aleatoria de centro en 5 filas
			//	p.y = r.nextInt(5) * ALTO_CASILLA + (ALTO_CASILLA/2);  // Posición aleatoria de centro en 5 columnas
			//	p.radio = r.nextInt(21) + 50; // Radio aleatorio entre 50 y 70
			//	p.color = COLORES_POSIBLES[ r.nextInt( COLORES_POSIBLES.length ) ];
			// Con constructor con parámetros:
			Pelota p = new Pelota(
				r.nextInt(RADIO_MAXIMO-RADIO_MINIMO+1) + RADIO_MINIMO, // Radio aleatorio entre los valores dados
				r.nextInt(tamanyoTablero) * ANCHO_CASILLA + (ANCHO_CASILLA/2), // Posición aleatoria de centro en n filas
				r.nextInt(tamanyoTablero) * ALTO_CASILLA + (ALTO_CASILLA/2),  // Posición aleatoria de centro en n columnas
				COLORES_POSIBLES[ r.nextInt( COLORES_POSIBLES.length ) ]
			);
			// boolean existeYa = yaExistePelota( tablero, p, contadorPelotas ); // Método movido a la clase GrupoPelotas
			boolean existeYa = tablero.yaExistePelota( p );  // Observa que el contador deja de ser necesario
			if (!existeYa) {
				// Se dibuja la pelota y se añade al array
				p.dibuja( v );
				// tablero[contadorPelotas] = p; // Sustituido por el objeto:
				tablero.addPelota( p );
				// contadorPelotas++;  // El contador deja de ser necesario (va incluido en el objeto GrupoPelotas)
			}
		}
		// Comprueba que el tablero sea posible (no hay solo N-2 pelotas de un color dado)
		char tabPosible = ' ';
		do {  // Repite hasta que el tablero sea posible
			
			if (tabPosible!=' ') {
				boolean existeYa = true;
				Pelota p = null;
				do {
					p = new Pelota(
						r.nextInt(RADIO_MAXIMO-RADIO_MINIMO+1) + RADIO_MINIMO, // Radio aleatorio entre los valores dados
						r.nextInt(tamanyoTablero) * ANCHO_CASILLA + (ANCHO_CASILLA/2), // Posición aleatoria de centro en n filas
						r.nextInt(tamanyoTablero) * ALTO_CASILLA + (ALTO_CASILLA/2),  // Posición aleatoria de centro en n columnas
						tabPosible
					);
					existeYa = tablero.yaExistePelota( p );
				} while (existeYa);
				p.dibuja( v );
				tablero.addPelota( p );
			}
			quitaPelotasSiLineas( false );
			tabPosible = tableroPosible();
		} while (tabPosible!=' ');
		v.setMensaje( tablero.size() + " pelotas creadas." );
	}
	
	// Mueve las pelotas en pantalla
	// Devuelve true si se ha "pasado" el nivel
	private static boolean juega() {
		v.setMensaje( "Empieza el nivel " + nivel + ". Puntuación = " + puntuacion + 
			". Dejar menos de " + (tamanyoTablero-2) + " pelotas de cada color. " + (tamanyoTablero-1) + " o más seguidas se quitan."  );
		// v.setDibujadoInmediato( false );  // Notar la mejoría de "vibración" si se hace esto y (2)
		while (!v.estaCerrada() && !juegoAcabado()) {
			Point puls = v.getRatonPulsado();
			if (puls!=null) {
				// Pelota pelotaPulsada = hayPelotaPulsadaEn( puls, tablero ); // Sustituido por el objeto:
				Pelota pelotaPulsada = tablero.hayPelotaPulsadaEn( puls );
				if (pelotaPulsada!=null) {
					double coordInicialX = pelotaPulsada.getX();
					double coordInicialY = pelotaPulsada.getY();
					v.espera(20); // Espera un poquito (si no pasa todo demasiado rápido)
					// Hacer movimiento hasta que se suelte el ratón
					Point drag = v.getRatonPulsado();
					while (drag!=null && drag.x>0 && drag.y>0 && drag.x<v.getAnchura() && drag.y<v.getAltura()) { // EN CORTOCIRCUITO - no se sale de los bordes
						pelotaPulsada.borra( v );
						pelotaPulsada.incXY( drag.x - puls.x, drag.y - puls.y );
						pelotaPulsada.dibuja( v );
						repintarTodas();  // Notar la diferencia si no se hace esto (se van borrando las pelotas al pintar otras que pasan por encima)
						// v.repaint(); // (2)
						puls = drag;
						drag = v.getRatonPulsado();
					}
					// Recolocar pelota en un sitio válido
					recolocar( pelotaPulsada, coordInicialX, coordInicialY );
					quitaPelotasSiLineas( true );
				}
			}
		}
		puntuacion -= (numMovimientos*PUNTOS_POR_MOVIMIENTO);  // Se penaliza por el número de movimientos
		if (v.estaCerrada()) return false; // Se acaba el juego cerrando la ventana
		if (nivelPasado()) return true; // Se ha pasado el nivel
		return false; // No se ha pasado el nivel
	}
	
	private static void repintarTodas() {
		// for (int i=0; i<tablero.length; i++) { // Sustituido por el objeto:
		for (int i=0; i<tablero.size(); i++) {
			// tablero[i].dibuja( v ); // Sustituido por el objeto:
			tablero.getPelota( i ).dibuja( v );
		}
	}
	
	private static void recolocar( Pelota pelota, double xDonde, double yDonde ) {
		// 1.- Buscar el sitio del tablero del cual está más cerca el centro de nuestra pelota
		double distMin = Double.MAX_VALUE;
		int xMasCerca = -1;
		int yMasCerca = -1;
		for (int fila=0; fila<tamanyoTablero; fila++) {
			for (int col=0; col<tamanyoTablero; col++) {
				int coordX = ANCHO_CASILLA * col + (ANCHO_CASILLA/2);
				int coordY = ALTO_CASILLA * fila + (ALTO_CASILLA/2);
				double dist = Math.sqrt( Math.pow( coordX-pelota.getX(), 2 ) + Math.pow( coordY-pelota.getY(), 2 ) );
				if (dist < distMin) {
					distMin = dist;
					xMasCerca = coordX;
					yMasCerca = coordY;
				}
			}
		}
		// System.out.println( "Pelota llevada a casilla (" + filaMasCerca + "," + colMasCerca + ")" );
		// 2.- Comprobar si ese sitio está vacío
		boolean sitioLibre = true;
		for (int i=0; i<tablero.size(); i++) {
			Pelota p = tablero.getPelota(i);
			if (Math.round(p.getX())==xMasCerca && Math.round(p.getY())==yMasCerca) {  // Esta pelota ocupa ese sitio
				sitioLibre = false;
				// System.out.println( "La pelota " + p + " ya ocupa ese espacio. No se puede mover" );
				break;
			}
		}
		// 3.- Mover al sitio que corresponda (casilla exacta si está libre o casilla inicial si está ocupada)
		if (sitioLibre) {
			xDonde = xMasCerca;
			yDonde = yMasCerca;
			numMovimientos++;
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
			repintarTodas();
		}
	}

	// Métodos pasados a la clase GrupoPelotas (observa que dejan de ser static y dejan de necesitar el parámetro array)
	// private static Pelota hayPelotaPulsadaEn( Point punto, Pelota[] tablero ) {
	// private static boolean yaExistePelota( Pelota[] tablero, Pelota p, int numPelotas ) {

	// Quita las pelotas que cumplen líneas de 4 o más del mismo color
	// Si el parámetro es true incrementa la puntuación, si no (en el azar del inicio del nivel) no se considera
	private static void quitaPelotasSiLineas( boolean incrementaPuntuacion ) {
		// Método no muy eficiente, sería mejor adaptando el tablero a filas y columnas.
		// (tal y como está, cada vez que se quiere comprobar se reconstruye el tablero de filas/columnas)
		Pelota tableroLogica[][] = new Pelota[tamanyoTablero][tamanyoTablero];
		boolean tableroAQuitar[][] = new boolean[tamanyoTablero][tamanyoTablero];
		for (int i=0; i<tablero.size(); i++) {
			Pelota pelota = tablero.getPelota( i );
			int fila = getFilaDePelota(pelota);
			int col = getColumnaDePelota(pelota);
			tableroLogica[fila][col] = pelota;
		}
		// Busca filas a quitar
		for (int fila=0; fila<tamanyoTablero; fila++) {
			int numSeguidas = 0;
			char colorAnt = ' ';
			for (int col=0; col<tamanyoTablero; col++) {
				char color = ' ';
				if (tableroLogica[fila][col]!=null) color = tableroLogica[fila][col].getColor();
				if (color==' ') {
					quitaEnFilaSiProcede( fila, col-1, numSeguidas, tableroAQuitar );
					numSeguidas = 1;
				} else if (color==colorAnt) { // Seguidas!
					numSeguidas++;
				} else { // No seguidas
					quitaEnFilaSiProcede( fila, col-1, numSeguidas, tableroAQuitar );
					numSeguidas = 1;
				}
				colorAnt = color;
			}
			quitaEnFilaSiProcede( fila, tamanyoTablero-1, numSeguidas, tableroAQuitar );
		}
		// Busca columnas a quitar
		for (int col=0; col<tamanyoTablero; col++) {
			int numSeguidas = 0;
			char colorAnt = ' ';
			for (int fila=0; fila<tamanyoTablero; fila++) {
				char color = ' ';
				if (tableroLogica[fila][col]!=null) color = tableroLogica[fila][col].getColor();
				if (color==' ') {
					quitaEnColumnaSiProcede( fila-1, col, numSeguidas, tableroAQuitar );
					numSeguidas = 1;
				} else if (color==colorAnt) { // Seguidas!
					numSeguidas++;
				} else { // No seguidas
					quitaEnColumnaSiProcede( fila-1, col, numSeguidas, tableroAQuitar );
					numSeguidas = 1;
				}
				colorAnt = color;
			}
			quitaEnColumnaSiProcede( tamanyoTablero-1, col, numSeguidas, tableroAQuitar );
		}
		// Quita las marcadas en el array de booleanos
		boolean quitadaAlguna = false;
		for (int fila=0; fila<tamanyoTablero; fila++) {
			for (int col=0; col<tamanyoTablero; col++) {
				if (tableroAQuitar[fila][col]) {
					if (incrementaPuntuacion) puntuacion += tableroLogica[fila][col].getRadio();  // Se suma el radio a la puntuación
					tablero.removePelota( tableroLogica[fila][col] );
					quitadaAlguna = true;
				}
			}
		}
		if (quitadaAlguna) {
			v.borra();
			repintarTodas();
		}
	}

	// Comprueba si hay que quitar en fila, dada la última fila-columna y el número de fichas seguidas que hay
	private static void quitaEnFilaSiProcede( int fila, int col, int numSeguidas, boolean tableroAQuitar[][] ) {
		if (numSeguidas>=tamanyoTablero-1) { // 4 o más seguidas!  A quitar
			for (int colIni=col-numSeguidas+1; colIni<=col; colIni++)
				tableroAQuitar[fila][colIni] = true;
		}
	}
	// Comprueba si hay que quitar en columna, dada la última fila-columna y el número de fichas seguidas que hay
	private static void quitaEnColumnaSiProcede( int fila, int col, int numSeguidas, boolean tableroAQuitar[][] ) {
		if (numSeguidas>=tamanyoTablero-1) { // 4 o más seguidas!  A quitar
			for (int filaIni=fila-numSeguidas+1; filaIni<=fila; filaIni++)
				tableroAQuitar[filaIni][col] = true;
		}
	}

	
	private static int getFilaDePelota( Pelota pelota ) {
		int fila = 0;
		while (fila < tamanyoTablero) {
			if (fila*ALTO_CASILLA + ALTO_CASILLA/2 == Math.round(pelota.getY())) return fila;
			fila++;
		}
		return -1;  // No debería darse (todas las pelotas están en una fila de 0 a n-1)
	}

	private static int getColumnaDePelota( Pelota pelota ) {
		int col = 0;
		while (col < tamanyoTablero) {
			if (col*ANCHO_CASILLA + ANCHO_CASILLA/2 == Math.round(pelota.getX())) return col;  // Como es un double comparamos con un poco de margen y con >=
			col++;
		}
		return -1;  // No debería darse (todas las pelotas están en una col de 0 a n-1)
	}

	// Devuelve true si se ha acabado el nivel (hay menos de N-1 pelotas de cada colores)
	private static boolean juegoAcabado() {
		int[] contPelotas = new int[ COLORES_POSIBLES.length ];  // Contadores por color
		for (int i=0; i<tablero.size(); i++) {
			Pelota pelota = tablero.getPelota(i);
			int contColor = Arrays.asList(COLORES_POSIBLES).indexOf( pelota.getColor() );  // Posición del color de la pelota en el array de contadores
			if (contColor==-1) System.out.println( pelota.getColor() );
			contPelotas[contColor]++;
		}
		for (int contador : contPelotas) if (contador>=tamanyoTablero-1) return false;
		return true;
	}
	
	// Devuelve true si se ha pasado el nivel (hay N-2 o menos pelotas de cada color), false en caso contrario
	private static boolean nivelPasado() {
		int[] contPelotas = new int[ COLORES_POSIBLES.length ];  // Contadores por color
		for (int i=0; i<tablero.size(); i++) {
			Pelota pelota = tablero.getPelota(i);
			int contColor = Arrays.asList(COLORES_POSIBLES).indexOf( pelota.getColor() );  // Posición del color de la pelota en el array de contadores
			contPelotas[contColor]++;
		}
		for (int contador : contPelotas) if (contador>=tamanyoTablero-2) return false;
		return true;
	}

	// Devuelve un color concreto si es un tablero (aleatorio) imposible (Solo hay N-2 pelotas de algún color - el que devuelve). ' ' si no es imposible
	private static char tableroPosible() {
		int[] contPelotas = new int[ COLORES_POSIBLES.length ];  // Contadores por color
		for (int i=0; i<tablero.size(); i++) {
			Pelota pelota = tablero.getPelota(i);
			int contColor = Arrays.asList(COLORES_POSIBLES).indexOf( pelota.getColor() );  // Posición del color de la pelota en el array de contadores
			contPelotas[contColor]++;
		}
		for (int i=0; i<contPelotas.length; i++) {
			int contador = contPelotas[i];
			if (contador==tamanyoTablero-2) return COLORES_POSIBLES[i];
		}
		return ' ';
	}
}
