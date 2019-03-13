package tema3.conHerencia;

import java.awt.Color;

import tema3.VentanaGrafica;

public abstract class Figura /*extends Object */ {
	
	public static void main(String[] args) {
		// Figura f = new Figura();  // No se puede instanciar!!!  Al ser una clase abstracta
	}
	
	protected double x;      // Coordenada x de centro o punto de referencia de la figura
	protected double y;      // Coordenada y de centro o punto de referencia de la figura
	protected double vX;     // velocidad x de desplazamiento de la figura (en píxels por segundo)
	protected double vY;     // velocidad y de desplazamiento de la figura (en píxels por segundo)
	protected Color color;   // Color de la figura
	
	
//	public Figura() {
//		System.out.println( "Constructor Figura por defecto");
//	}
	
	public Figura( Color c ) {
		this.color = c;
		System.out.println( "Constructor figura con color" );
	}
	
	public Figura( double x, double y, Color c ) {
		this.x = x;
		this.y = y;
		this.color = c;
	}

	/** Devuelve la coordenada x del centro de la figura
	 * @return	Coordenada x en píxels (0=izquierda de la ventana)
	 */
	public double getX() {
		return x;
	}

	/** Cambia la coordenada del centro de la figura
	 * @param x	Nueva coordenada x en píxels (0=izquierda de la ventana)
	 */
	public void setX(double x) {
		this.x = x;
	}

	/** Devuelve la coordenada y del centro de la figura
	 * @return	Coordenada y en píxels (0=arriba de la ventana)
	 */
	public double getY() {
		return y;
	}

	/** Cambia la coordenada del centro de la figura
	 * @param y	Nueva coordenada y en píxels (0=arriba de la ventana)
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/** Devuelve la velocidad de desplazamiento de la figura en el eje X
	 * @return	velocidad en píxels por segundo (positivo hacia la derecha, negativo hacia la izquierda, 0 parado)
	 */
	public double getVX() {
		return vX;
	}

	/** Cambia la velocidad de desplazamiento de la figura en el eje X
	 * @param vx	Nueva velocidad en píxels por segundo (positivo hacia la derecha, negativo hacia la izquierda, 0 parado)
	 */
	public void setVX(double vx) {
		this.vX = vx;
	}

	/** Devuelve la velocidad de desplazamiento de la figura en el eje Y
	 * @return	velocidad en píxels por segundo (positivo hacia abajo, negativo hacia arriba, 0 parado)
	 */
	public double getVY() {
		return vY;
	}

	/** Cambia la velocidad de desplazamiento de la figura en el eje Y
	 * @param vy	Nueva velocidad en píxels por segundo (positivo hacia abajo, negativo hacia arriba, 0 parado)
	 */
	public void setVY(double vy) {
		this.vY = vy;
	}

	/** Incrementa o decrementa las coordenadas de la figura
	 * @param incX	Incremento de la coordenada X (negativo si es decremento)
	 * @param incY	Incremento de la coordenada Y (idem)
	 */
	public void incXY( double incX, double incY ) {
		x += incX;
		y += incY;
	}

	/** Devuelve el color de la figura
	 * @return	color de la figura
	 */
	public Color getColor() {
		return color;
	}

	/** Modifica el color de la figura
	 * @param color	color de la figura
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/** Mueve la figura cambiando su posición según su velocidad lineal y el tiempo
	 * @param segs	Tiempo transcurrido
	 */
	public void mueve( double segs ) {
		x = Fisica.calcEspacio( x, vX, segs );
		y = Fisica.calcEspacio( y, vY, segs );
	}
	
	/** Dibuja la figura en una ventana
	 * @param v	Ventana en la que dibujar la figura
	 */
	public abstract void dibuja( VentanaGrafica v );

	/** Comprueba si la figura se sale por la vertical de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por arriba o por abajo (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	public abstract boolean seSaleEnVertical( VentanaGrafica v );

	/** Comprueba si la figura se sale por la horizontal de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por izquierda o derecha (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	public abstract boolean seSaleEnHorizontal( VentanaGrafica v );

	@Override
	public String toString() {
		return x + " , " + y;
	}
	
}
