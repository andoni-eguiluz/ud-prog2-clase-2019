package tema3.sinHerencia;

import java.awt.Point;

public class Fisica {
	
	/** Devuelve la distancia entre dos puntos
	 * @param p1	punto 1
	 * @param p2	punto 2
	 * @return	Distancia entre ambos (positiva)
	 */
	public static double distancia (Point p1, Point p2) {
		return distancia( p1.x, p1.y, p2.x, p2.y );
	}
	
	/** Devuelve la distancia entre dos puntos
	 * @param x1	x del punto 1
	 * @param y1	y del punto 1
	 * @param x2	x del punto 2
	 * @param y2	y del punto 2
	 * @return	Distancia entre ambos (positiva)
	 */
	public static double distancia( double x1, double y1, double x2, double y2 ) {
		return Math.sqrt( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) ); 
	}
	
	/** Calcula el movimiento de espacio en función de un desplazamiento lineal sin aceleración
	 * @param espacio	Espacio original (en píxels)
	 * @param velocidad	Velocidad (en píxels por segundo)
	 * @param segs	Segundos transcurridos en el movimimento
	 * @return	Nuevo espacio
	 */
	public static double calcEspacio( double espacio, double velocidad, double segs ) {
		// s2 = s1 + v*t
		return espacio + velocidad * segs;
	}
}
