package tema3.sinHerencia;
import java.awt.Color;

import tema3.VentanaGrafica;

/** Clase que permite crear y gestionar rectángulos y dibujarlos en una ventana gráfica
 */
public class Rectangulo {
	
	private static Color COLOR_POR_DEFECTO = Color.green;  // Color por defecto de los rectángulos nuevos
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private double x;      // Coordenada x de centro de rectángulo
	private double y;      // Coordenada y de centro de rectángulo
	private double vX;     // velocidad x de desplazamiento del rectángulo (en píxels por segundo)
	private double vY;     // velocidad y de desplazamiento del rectángulo (en píxels por segundo)
	private double tamX;   // Anchura de rectángulo
	private double tamY;   // Altura de rectángulo
	private Color color;   // Color del rectángulo

	/** Crea un nuevo rectángulo de radio 0, coordenada 0,0, color azul
	 */
	public Rectangulo() {
		color = COLOR_POR_DEFECTO;
	}
	
	/** Crea un nuevo rectángulo
	 * @param anc	Píxels de anchura del rectángulo (debe ser mayor que 0)
	 * @param alt	Píxels de altura del rectángulo (debe ser mayor que 0)
	 * @param x	Coordenada x del centro del rectángulo (en píxels)
	 * @param y	Coordenada y del centro del rectángulo (en píxels)
	 * @param color	Color del rectángulo
	 */
	public Rectangulo( double anc, double alt, double x, double y, Color color ) {
		this.tamX = anc;
		this.tamY = alt;
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	/** Crea un nuevo rectángulo, copiando los datos de otro existente
	 * @param rectangulo	Rectángulo ya creado, del que copiar los datos
	 */
	public Rectangulo( Rectangulo rectangulo ) {
		this( rectangulo.tamX, rectangulo.tamY, rectangulo.x, rectangulo.y, rectangulo.color );
		vX = rectangulo.vX;
		vY = rectangulo.vY;
	}
	
	/** Devuelve la anchura del rectángulo
	 * @return	Anchura en píxels
	 */
	public double getAnchura() {
		return tamX;
	}

	/** Cambia la anchura del rectángulo. Debe ser mayor que cero
	 * @param anc	Nueva anchura del rectángulo (debe ser mayor que cero)
	 */
	public void setAnchura(double anc) {
		this.tamX = anc;
	}

	/** Devuelve la altura del rectángulo
	 * @return	Altura en píxels
	 */
	public double getAltura() {
		return tamY;
	}

	/** Cambia la altura del rectángulo. Debe ser mayor que cero
	 * @param anc	Nueva altura del rectángulo (debe ser mayor que cero)
	 */
	public void setAltura(double alt) {
		this.tamY = alt;
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

	/** Dibuja el rectángulo en una ventana
	 * @param v	Ventana en la que dibujar el rectángulo
	 */
	public void dibuja( VentanaGrafica v ) {
		v.dibujaRect( x-tamX/2, y-tamY/2, tamX, tamY, 2f, color, color );
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
		return y-tamY/2<=0 || y+tamY/2>=v.getAltura();
	}

	/** Comprueba si el rectángulo se sale por la horizontal de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por izquierda o derecha (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	public boolean seSaleEnHorizontal( VentanaGrafica v ) {
		return x-tamX/2<=0 || x+tamX/2>=v.getAnchura();
	}

	@Override
	public String toString() {
		return x + "," + y + " (" + tamX + "," + tamY + ")";
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
		Rectangulo p2 = (Rectangulo) obj;  // Cast de obj a rectángulo2 (lo entenderemos mejor al ver herencia)
		return Math.round(p2.x)==Math.round(x) && Math.round(p2.y)==Math.round(y); // Devuelve true o false dependiendo de las coordenadas de los rectángulos this y p2
	}
	
}
