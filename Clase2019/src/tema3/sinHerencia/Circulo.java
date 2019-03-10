package tema3.sinHerencia;
import java.awt.Color;

import tema3.VentanaGrafica;

/** Clase que permite crear y gestionar círculos y dibujarlos en una ventana gráfica
 */
public class Circulo {
	
	private static final Color COLOR_POR_DEFECTO = Color.blue;  // Color por defecto de los círculos nuevos
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private double radio;  // Radio de círculo
	private double x;      // Coordenada x de centro de círculo
	private double y;      // Coordenada y de centro de círculo
	private double vX;     // velocidad x de desplazamiento del círculo (en píxels por segundo)
	private double vY;     // velocidad y de desplazamiento del círculo (en píxels por segundo)
	private Color color;   // Color del círculo

	/** Crea un nuevo círculo de radio 0, coordenada 0,0, color azul
	 */
	public Circulo() {
		color = COLOR_POR_DEFECTO;
	}
	
	/** Crea un nuevo círculo
	 * @param radio	Píxels de radio del círculo (debe ser mayor que 0)
	 * @param x	Coordenada x del centro del círculo (en píxels)
	 * @param y	Coordenada y del centro del círculo (en píxels)
	 * @param color	Color del círculo
	 */
	public Circulo( double radio, double x, double y, Color color ) {
		this.radio = radio;
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	/** Crea un nuevo círculo copiando los datos de otro existente
	 * @param circulo	Círculo ya creado, del que copiar los datos
	 */
	public Circulo( Circulo circulo ) {
		this( circulo.radio, circulo.x, circulo.y, circulo.color );
		vX = circulo.vX;
		vY = circulo.vY;
	}
	
	/** Devuelve el radio del círculo
	 * @return	Radio en píxels
	 */
	public double getRadio() {
		return radio;
	}

	/** Cambia el radio del círculo. Debe ser mayor que cero
	 * @param radio	Nuevo radio del círculo (debe ser mayor que cero)
	 */
	public void setRadio(double radio) {
		this.radio = radio;
	}

	/** Devuelve la coordenada x del centro del círculo
	 * @return	Coordenada x en píxels (0=izquierda de la ventana)
	 */
	public double getX() {
		return x;
	}

	/** Cambia la coordenada del centro del círculo
	 * @param x	Nueva coordenada x en píxels (0=izquierda de la ventana)
	 */
	public void setX(double x) {
		this.x = x;
	}

	/** Devuelve la coordenada y del centro del círculo
	 * @return	Coordenada y en píxels (0=arriba de la ventana)
	 */
	public double getY() {
		return y;
	}

	/** Cambia la coordenada del centro del círculo
	 * @param y	Nueva coordenada y en píxels (0=arriba de la ventana)
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/** Devuelve la velocidad de desplazamiento del círculo en el eje X
	 * @return	velocidad en píxels por segundo (positivo hacia la derecha, negativo hacia la izquierda, 0 parado)
	 */
	public double getVX() {
		return vX;
	}

	/** Cambia la velocidad de desplazamiento del círculo en el eje X
	 * @param vx	Nueva velocidad en píxels por segundo (positivo hacia la derecha, negativo hacia la izquierda, 0 parado)
	 */
	public void setVX(double vx) {
		this.vX = vx;
	}

	/** Devuelve la velocidad de desplazamiento del círculo en el eje Y
	 * @return	velocidad en píxels por segundo (positivo hacia abajo, negativo hacia arriba, 0 parado)
	 */
	public double getVY() {
		return vY;
	}

	/** Cambia la velocidad de desplazamiento del círculo en el eje Y
	 * @param vy	Nueva velocidad en píxels por segundo (positivo hacia abajo, negativo hacia arriba, 0 parado)
	 */
	public void setVY(double vy) {
		this.vY = vy;
	}

	/** Incrementa o decrementa las coordenadas del círculo
	 * @param incX	Incremento de la coordenada X (negativo si es decremento)
	 * @param incY	Incremento de la coordenada Y (idem)
	 */
	public void incXY( double incX, double incY ) {
		x += incX;
		y += incY;
	}

	/** Devuelve el color del círculo
	 * @return	color del círculo
	 */
	public Color getColor() {
		return color;
	}

	/** Modifica el color del círculo
	 * @param color	color del círculo
	 */
	public void setColor(Color color) {
		this.color = color;
	}

		double rot = 0.0; // rotación del balón (para hacer animación)
	/** Dibuja el círculo en una ventana
	 * @param v	Ventana en la que dibujar el círculo
	 */
	public void dibuja( VentanaGrafica v ) {
		v.dibujaImagen( "/img/balon.png", x, y, 2*radio/50, rot, 1.0f );
		rot += 0.05;
		v.dibujaCirculo( x, y, radio, 2f, color );
	}
	
	/** Mueve el círculo cambiando su posición según su velocidad lineal y el tiempo
	 * @param segs	Tiempo transcurrido
	 */
	public void mueve( double segs ) {
		x = Fisica.calcEspacio( x, vX, segs );
		y = Fisica.calcEspacio( y, vY, segs );
	}
	
	/** Comprueba si el círculo se sale por la vertical de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por arriba o por abajo (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	public boolean seSaleEnVertical( VentanaGrafica v ) {
		return y-radio<=0 || y+radio>=v.getAltura();
	}

	/** Comprueba si el círculo se sale por la horizontal de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por izquierda o derecha (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	public boolean seSaleEnHorizontal( VentanaGrafica v ) {
		return x-radio<=0 || x+radio>=v.getAnchura();
	}

	/** Comprueba si el círculo se sale por la vertical de la ventana
	 * @param v	Ventana de comprobación
	 * @return	+1 si se está saliendo por arriba, -1 si se sale por abajo, 0 si no se sale
	 */
	public int salidaVertical( VentanaGrafica v ) {
		if (y-radio<=0) return +1;
		else if (y+radio>=v.getAltura()) return -1;
		else return 0;
	}

	/** Comprueba si el círculo se sale por la horizontal de la ventana
	 * @param v	Ventana de comprobación
	 * @return	+1 si se está saliendo por izquierda, -1 si se sale por derecha, 0 si no se sale
	 */
	public int salidaHorizontal( VentanaGrafica v ) {
		if (x-radio<=0) return +1;
		else if (x+radio>=v.getAnchura()) return -1;
		else return 0;
	}

	@Override
	public String toString() {
		return x + "," + y + " (" + radio + ")";
	}
	
	// Los comentarios de métodos override no son imprescindibles (ya veremos por qué con herencia) pero
	// sí se pueden indicar si el método aporta algo diferencial como aquí
	
	/** Comprueba si el círculo es igual a otro objeto dado. Se entiende que dos círculos son iguales
	 * cuando las coordenadas de sus centros (redondeadas a enteros) son iguales
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO a mejorar cuando veamos polimorfismo
		Circulo p2 = (Circulo) obj;  // Cast de obj a círculo2 (lo entenderemos mejor al ver herencia)
		return Math.round(p2.x)==Math.round(x) && Math.round(p2.y)==Math.round(y); // Devuelve true o false dependiendo de las coordenadas de los círculos this y p2
	}
	
}
