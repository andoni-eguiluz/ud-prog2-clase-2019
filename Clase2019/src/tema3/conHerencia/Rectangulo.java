package tema3.conHerencia;
import java.awt.Color;

import tema3.VentanaGrafica;

/** Clase que permite crear y gestionar rectángulos y dibujarlos en una ventana gráfica
 */
public class Rectangulo extends Figura {
	
	private static Color COLOR_POR_DEFECTO = Color.green;  // Color por defecto de los rectángulos nuevos
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private double tamX;   // Anchura de rectángulo
	private double tamY;   // Altura de rectángulo

	/** Crea un nuevo rectángulo de radio 0, coordenada 0,0, color azul
	 */
	public Rectangulo() {
		// super();  // Lo pone por defecto Java si no lo ponemos nosotros
		super( COLOR_POR_DEFECTO );
		System.out.println( "Construyo círculo");
		// color = COLOR_POR_DEFECTO;
	}
	
	/** Crea un nuevo rectángulo
	 * @param anc	Píxels de anchura del rectángulo (debe ser mayor que 0)
	 * @param alt	Píxels de altura del rectángulo (debe ser mayor que 0)
	 * @param x	Coordenada x del centro del rectángulo (en píxels)
	 * @param y	Coordenada y del centro del rectángulo (en píxels)
	 * @param color	Color del rectángulo
	 */
	public Rectangulo( double anc, double alt, double x, double y, Color color ) {
		super( x, y, color );
		this.tamX = anc;
		this.tamY = alt;
		// this.x = x;
		// this.y = y;
		// this.color = color;
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

	/** Dibuja el rectángulo en una ventana
	 * @param v	Ventana en la que dibujar el rectángulo
	 */
	@Override
	public void dibuja( VentanaGrafica v ) {
		v.dibujaRect( x-tamX/2, y-tamY/2, tamX, tamY, 2f, color, color );
	}
	
	/** Comprueba si el rectángulo se sale por la vertical de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por arriba o por abajo (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	@Override
	public boolean seSaleEnVertical( VentanaGrafica v ) {
		return y-tamY/2<=0 || y+tamY/2>=v.getAltura();
	}

	/** Comprueba si el rectángulo se sale por la horizontal de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por izquierda o derecha (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	@Override
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
