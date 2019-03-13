package tema3.conHerencia;
import java.awt.Color;

import tema3.VentanaGrafica;

/** Clase que permite crear y gestionar círculos y dibujarlos en una ventana gráfica
 */
public class Circulo extends Figura {
	
	private static final Color COLOR_POR_DEFECTO = Color.blue;  // Color por defecto de los círculos nuevos

	public static void main(String[] args) {
		// Circulo c2 = new Circulo( Color.red );  // No se hereda el constructor
		Circulo c1 = new Circulo( 2, 100, 150, Color.yellow );
		System.out.println( c1 );
	}
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private double radio;  // Radio de círculo

	/** Crea un nuevo círculo de radio 0, coordenada 0,0, color azul
	 */
	public Circulo() {
		super( COLOR_POR_DEFECTO );
		System.out.println( "Construyo círculo");
		// color = COLOR_POR_DEFECTO;
	}
	
	/** Crea un nuevo círculo
	 * @param radio	Píxels de radio del círculo (debe ser mayor que 0)
	 * @param x	Coordenada x del centro del círculo (en píxels)
	 * @param y	Coordenada y del centro del círculo (en píxels)
	 * @param color	Color del círculo
	 */
	public Circulo( double radio, double x, double y, Color color ) {
		super( x, y, color );
		System.out.println( "Construyo círculo con r,x,y,c");
		this.radio = radio;
		// this.x = x;
		// this.y = y;
		// this.color = color;
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

		double rot = 0.0; // rotación del balón (para hacer animación)
	/** Dibuja el círculo en una ventana
	 * @param v	Ventana en la que dibujar el círculo
	 */
	public void dibuja( VentanaGrafica v ) {
		v.dibujaImagen( "/img/balon.png", x, y, 2*radio/50, rot, 1.0f );
		rot += 0.05;
		v.dibujaCirculo( x, y, radio, 2f, color );
	}
	
	/** Comprueba si el círculo se sale por la vertical de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por arriba o por abajo (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	@Override
	public boolean seSaleEnVertical( VentanaGrafica v ) {
		return y-radio<=0 || y+radio>=v.getAltura();
	}

	/** Comprueba si el círculo se sale por la horizontal de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo por izquierda o derecha (tocar exactamente el borde se entiende así), false en caso contrario
	 */
	@Override
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
		return super.toString() + " (" + radio + ")";
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
