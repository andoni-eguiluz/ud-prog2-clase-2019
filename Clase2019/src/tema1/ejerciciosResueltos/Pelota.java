package tema1.ejerciciosResueltos;
import java.awt.Color;
import java.awt.Point;

import tema1.VentanaGrafica;

/** Clase que permite crear y gestionar pelotas y dibujarlas en una ventana gráfica
 * Versión 3 con más y mejores métodos y visibilidad de atributos
 */
public class Pelota {
	
	// =================================================
	// PARTE STATIC
	// =================================================
	
	/** Método principal de prueba de la clase
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		// Creamos una pelota en (300,300) con radio 200
		Pelota p1 = new Pelota( 200, 300, 300, 'a' );
		p1.bota = true;
		Pelota p2 = p1; // Son la misma pelota aunque no lo parezcan
		p2.radio = 50; // Al cambiar el radio de p2 cambia también p1 (aliasing)
		// Creamos una ventana gráfica para dibujarla
		VentanaGrafica v = new VentanaGrafica( 1000, 700, "Ventana gráfica de pelotas" );
		// La dibujamos y la borramos
		dibujaYBorra( v, p1 );
		v.espera( 1000 );  // Esperamos 1000 milisegundos
		// Que crezca
		dibujaYCrece( v, p1, 400 );
		v.espera( 1000 );
		p1.borra( v );  // Y la borramos
		// Que decrezca
		dibujaYCrece( v, p1, 100 );
		v.espera( 1000 );
		p1.borra( v );  // Y la borramos
		
		// La movemos interactivamente con el ratón
		p1.dibuja( v );
		while ( !v.estaCerrada() ) {
			Point p = v.getRatonPulsado();
			if (p!=null) {
				p1.borra( v );
				p1.x = p.x;
				p1.y = p.y;
				p1.dibuja( v );
			}
			v.espera(1);
		}
		
		// Otras opciones
		
		/*
		// Si quisiéramos cerrar la ventana tras 3 segundos y cerramos la ventana
		v.espera( 3000 );
		v.acaba();
		*/

		/*
		// Si quisiéramos crear pelotas a saco al hacer click con el ratón
		p1.dibuja( v );
		while ( !v.estaCerrada() ) {
			Point p = v.getRatonPulsado();
			if (p!=null) {
				PelotaV1 p3 = new PelotaV1();
				p3.x = p.x;
				p3.y = p.y;
				p3.radio = 50;
				p3.bota = true;
				p3.dibuja( v );
			}
			v.espera(1);
		}
		*/

	}

	// Dibuja una pelota en la ventana, espera un segundo y la borra
	private static void dibujaYBorra( VentanaGrafica v, Pelota r ) {
		r.dibuja( v );
		v.espera( 1000 );
		r.borra( v );
	}
	
	// Dibuja una pelota en la ventana y hace que crezca hasta el nuevo radio indicado 
	// Va dibujando esa pelota mientras su radio crece o decrece (de píxel en píxel)
	private static void dibujaYCrece( VentanaGrafica v, Pelota p, double nuevoRadio ) {
		if (p.radio<nuevoRadio) { // Si el radio tiene que crecer
			while (p.radio<nuevoRadio) {
				p.dibuja( v ); // Dibuja, espera 10 milisegundos y borra para causar el efecto visual
				v.espera( 10 );
				p.borra( v );
				p.radio += 1; // Suma de uno en uno hasta el radio final
			}
		} else {
			while (p.radio>nuevoRadio) {
				p.dibuja( v ); // Dibuja, espera 10 milisegundos y borra para causar el efecto visual
				v.espera( 10 );
				p.borra( v );
				p.radio -= 1; // Resta de uno en uno hasta el radio final
			}
		}
		p.dibuja( v ); // La deja dibujada al final
	}
	

	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private double radio;  // Radio de pelota
	private double x;      // Coordenada x de centro de pelota
	private double y;      // Coordenada y de centro de pelota
	private char color;    // Color de la pelota ('a' = azul, 'v' = verde)
	private boolean bota;  // Información de si la pelota bota o no

	/** Crea una nueva pelota azul, en las coordenadas 0,0, de radio 0, no bota
	 */
	public Pelota() {
		color = 'a';
	}
	
	/** Crea una nueva pelota que no bota
	 * @param radio	Píxels de radio de la pelota (debe ser mayor que 0)
	 * @param x	Coordenada x del centro de la pelota (en píxels)
	 * @param y	Coordenada y del centro de la pelota (en píxels)
	 * @param color	Color de la pelota ('a' azul, 'v' verde, 'r' rojo)
	 */
	public Pelota( double radio, double x, double y, char color ) {
		this.radio = radio;
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	/** Crea una nueva pelota
	 * @param radio	Píxels de radio de la pelota (debe ser mayor que 0)
	 * @param x	Coordenada x del centro de la pelota (en píxels)
	 * @param y	Coordenada y del centro de la pelota (en píxels)
	 * @param color	Color de la pelota ('a' azul, 'v' verde, 'r' rojo)
	 * @param bota	true si la pelota bota, false si no
	 */
	public Pelota(double radio, double x, double y, char color, boolean bota) {
		// super();
		this.radio = radio;
		this.x = x;
		this.y = y;
		this.color = color;
		this.bota = bota;
	}
	
	
	
	/** Devuelve el radio de la pelota
	 * @return	Radio en píxels
	 */
	public double getRadio() {
		return radio;
	}

	/** Cambia el radio de la pelota. Debe ser mayor que cero
	 * @param radio	Nuevo radio de la pelota (debe ser mayor que cero)
	 */
	public void setRadio(double radio) {
		this.radio = radio;
	}

	/** Devuelve la coordenada x del centro de la pelota
	 * @return	Coordenada x en píxels (0=izquierda de la ventana)
	 */
	public double getX() {
		return x;
	}

	/** Cambia la coordenada del centro de la pelota
	 * @param x	Nueva coordenada x en píxels (0=izquierda de la ventana)
	 */
	public void setX(double x) {
		this.x = x;
	}

	/** Devuelve la coordenada y del centro de la pelota
	 * @return	Coordenada y en píxels (0=arriba de la ventana)
	 */
	public double getY() {
		return y;
	}

	/** Cambia la coordenada del centro de la pelota
	 * @param y	Nueva coordenada y en píxels (0=arriba de la ventana)
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/** Incrementa o decrementa las coordenadas de la pelota
	 * @param incX	Incremento de la coordenada X (negativo si es decremento)
	 * @param incY	Incremento de la coordenada Y (idem)
	 */
	public void incXY( double incX, double incY ) {
		x += incX;
		y += incY;
	}

	/** Devuelve el color de la pelota
	 * @return	Color en forma de char ('v' = verde, 'a' = azul, 'r' = rojo)
	 */
	public char getColor() {
		return color;
	}

	/** Modifica el color de la pelota
	 * @param color	Color en forma de char ('v' = verde, 'a' = azul, 'r' = rojo)
	 */
	public void setColor(char color) {
		this.color = color;
	}

	/** Informa si la pelota bota o no
	 * @return	true si la pelota bota, false en caso contrario
	 */
	public boolean isBota() {
		return bota;
	}

	/** Modifica el bote de la pelota
	 * @param bota	true si la pelota debe botar, false en caso contrario
	 */
	public void setBota(boolean bota) {
		this.bota = bota;
	}

	/** Calcula el volumen de la pelota partiendo de su información de radio
	 * @return	Volumen de la pelota suponiendo una esfera perfecta
	 */
	double getVolumen() {
		return 4.0/3*Math.PI*radio*radio*radio;
	}
	
	/** Dibuja la pelota en una ventana
	 * @param v	Ventana en la que dibujar la pelota
	 */
	public void dibuja( VentanaGrafica v ) {
		Color color = Color.black;
		if (this.color=='v') color = Color.green;
		else if (this.color=='a') color = Color.blue;
		else if (this.color=='r') color = Color.red;
		v.dibujaCirculo( x, y, radio, 2f, color );
	}
	
	/** Borra la pelota en una ventana
	 * @param v	Ventana en la que borrar la pelota
	 */
	public void borra( VentanaGrafica v ) {
		v.borraCirculo( x, y, radio, 2f );
	}

	@Override
	public String toString() {
		return x + "," + y;
	}
	
	/** Visualiza en consola la pelota, con su centro y radio, en el formato "x,y - Radio r\n"
	 */
	public void visualiza() {
		System.out.println( this.x + "," + this.y + " - Radio " + this.radio  );
	}
	
	
	// Los comentarios de métodos override no son imprescindibles (ya veremos por qué con herencia) pero
	// sí se pueden indicar si el método aporta algo diferencial como aquí
	
	/** Comprueba si la pelota es igual a otro objeto dado. Se entiende que dos pelotas son iguales
	 * cuando las coordenadas de sus centros (redondeadas a enteros) son iguales
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO a mejorar cuando veamos polimorfismo
		Pelota p2 = (Pelota) obj;  // Cast de obj a Pelota2 (lo entenderemos mejor al ver herencia)
		return Math.round(p2.x)==Math.round(x) && Math.round(p2.y)==Math.round(y); // Devuelve true o false dependiendo de las coordenadas de las pelotas this y p2
	}
	
}
