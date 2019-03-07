package tema3.sinHerencia;
import java.awt.Color;

import tema3.VentanaGrafica;

/** Clase que permite crear y gestionar rectángulos y dibujarlos en una ventana gráfica
 */
public class Rect {
	
	private static final Color COLOR_POR_DEFECTO = Color.blue;  // Color por defecto de los rectángulos nuevos
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private double x;      // Coordenada x de centro de rectángulo
	private double y;      // Coordenada y de centro de rectángulo
	private double vX;     // velocidad x de desplazamiento del rectángulo (en píxels por segundo)
	private double vY;     // velocidad y de desplazamiento del rectángulo (en píxels por segundo)
	private Color color;   // Color del rectángulo
	private double anchura; // Anchura del rectángulo
	private double altura;  // Altura del rectángulo

	/** Crea un nuevo rectángulo de altura y anchura 0, coordenada 0,0, color azul
	 */
	public Rect() {
		color = COLOR_POR_DEFECTO;
	}
	
	/** Crea un nuevo rectángulo
	 * @param x	Coordenada x del centro del rectángulo (en píxels)
	 * @param y	Coordenada y del centro del rectángulo (en píxels)
	 * @param color	Color del rectángulo
	 */
	public Rect( double x, double y, double anchura, double altura, Color color ) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.anchura = anchura;
		this.altura = altura;
	}

	public double getAnchura() {
		return anchura;
	}

	public void setAnchura(double anchura) {
		this.anchura = anchura;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	/** Devuelve la coordenada x del centro del rectángulo
	 * @return	Coordenada x en píxels (0=izquierda de la ventana)
	 */
	public double getX() {
		return x;
	}

	/** Cambia la coordenada del centro del rectángulo
	 * @param x	Nueva coordenada x en píxels (0=izquierda de la ventana)
	 */
	public void setX(double x) {
		this.x = x;
	}

	/** Devuelve la coordenada y del centro del rectángulo
	 * @return	Coordenada y en píxels (0=arriba de la ventana)
	 */
	public double getY() {
		return y;
	}

	/** Cambia la coordenada del centro del rectángulo
	 * @param y	Nueva coordenada y en píxels (0=arriba de la ventana)
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/** Devuelve la velocidad de desplazamiento del rectángulo en el eje X
	 * @return	velocidad en píxels por segundo (positivo hacia la derecha, negativo hacia la izquierda, 0 parado)
	 */
	public double getVX() {
		return vX;
	}

	/** Cambia la velocidad de desplazamiento del rectángulo en el eje X
	 * @param vx	Nueva velocidad en píxels por segundo (positivo hacia la derecha, negativo hacia la izquierda, 0 parado)
	 */
	public void setVX(double vx) {
		this.vX = vx;
	}

	/** Devuelve la velocidad de desplazamiento del rectángulo en el eje Y
	 * @return	velocidad en píxels por segundo (positivo hacia abajo, negativo hacia arriba, 0 parado)
	 */
	public double getVY() {
		return vY;
	}

	/** Cambia la velocidad de desplazamiento del rectángulo en el eje Y
	 * @param vy	Nueva velocidad en píxels por segundo (positivo hacia abajo, negativo hacia arriba, 0 parado)
	 */
	public void setVY(double vy) {
		this.vY = vy;
	}

	/** Incrementa o decrementa las coordenadas del rectángulo
	 * @param incX	Incremento de la coordenada X (negativo si es decremento)
	 * @param incY	Incremento de la coordenada Y (idem)
	 */
	public void incXY( double incX, double incY ) {
		x += incX;
		y += incY;
	}

	/** Devuelve el color del rectángulo
	 * @return	color del rectángulo
	 */
	public Color getColor() {
		return color;
	}

	/** Modifica el color del rectángulo
	 * @param color	color del rectángulo
	 */
	public void setColor(Color color) {
		this.color = color;
	}

		double rot = 0.0; // rotación del balón (para hacer animación)
	/** Dibuja el rectángulo en una ventana
	 * @param v	Ventana en la que dibujar el rectángulo
	 */
	public void dibuja( VentanaGrafica v ) {
		v.dibujaRect( x-anchura/2.0, y-altura/2.0, anchura, altura, 1.0f, color );
	}
	
	/** Mueve el rectángulo cambiando su posición según su velocidad lineal y el tiempo
	 * @param segs	Tiempo transcurrido
	 */
	public void mueve( double segs ) {
		x = Fisica.calcEspacio( x, vX, segs );
		y = Fisica.calcEspacio( y, vY, segs );
	}
	
	/** Comprueba si el rectángulo se sale por la vertical de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por arriba o por abajo (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	public boolean seSaleEnVertical( VentanaGrafica v ) {
		return y-altura/2<=0 || y+altura/2>=v.getAltura();
	}

	/** Comprueba si el rectángulo se sale por la horizontal de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por izquierda o derecha (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	public boolean seSaleEnHorizontal( VentanaGrafica v ) {
		return x-anchura/2<=0 || x+anchura/2>=v.getAnchura();
	}

	/** Comprueba si el rectángulo se sale por la vertical de la ventana
	 * @param v	Ventana de comprobación
	 * @return	+1 si se está saliendo por arriba, -1 si se sale por abajo, 0 si no se sale
	 */
	public int salidaVertical( VentanaGrafica v ) {
		if (y-altura/2<=0) return +1;
		else if (y+altura/2>=v.getAltura()) return -1;
		else return 0;
	}

	/** Comprueba si el rectángulo se sale por la horizontal de la ventana
	 * @param v	Ventana de comprobación
	 * @return	+1 si se está saliendo por izquierda, -1 si se sale por derecha, 0 si no se sale
	 */
	public int salidaHorizontal( VentanaGrafica v ) {
		if (x-anchura/2<=0) return +1;
		else if (x+anchura/2>=v.getAnchura()) return -1;
		else return 0;
	}

	@Override
	public String toString() {
		return x + "," + y + " (" + anchura + "/" + altura + ")";
	}
	
	// Los comentarios de métodos override no son imprescindibles (ya veremos por qué con herencia) pero
	// sí se pueden indicar si el método aporta algo diferencial como aquí
	
	/** Comprueba si el rectángulo es igual a otro objeto dado. Se entiende que dos rectángulos son iguales
	 * cuando las coordenadas de sus centros (redondeadas a enteros) son iguales
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO a mejorar cuando veamos polimorfismo
		Rect p2 = (Rect) obj;  // Cast de obj a rectángulo2 (lo entenderemos mejor al ver herencia)
		return Math.round(p2.x)==Math.round(x) && Math.round(p2.y)==Math.round(y); // Devuelve true o false dependiendo de las coordenadas de los rectángulos this y p2
	}
	
}
