package tema1.ejercicios;
import java.awt.Color;
import java.awt.Point;

import tema1.VentanaGrafica;

/** Clase que permite crear y gestionar pelotas y dibujarlas en una ventana gráfica
 * Versión 2
 */
public class Pelota3 {
	
	// =================================================
	// PARTE STATIC
	// =================================================
	
	/** Método principal de prueba de la clase
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		// Creamos una pelota en (300,300) con radio 200
		Pelota3 p1 = new Pelota3();
		p1.x = 300;
		p1.y = 300;
		p1.radio = 200;
		p1.color = 'a';  // a -> azul
		p1.bota = true;
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
	private static void dibujaYBorra( VentanaGrafica v, Pelota3 r ) {
		r.dibuja( v );
		v.espera( 1000 );
		r.borra( v );
	}
	
	// Dibuja una pelota en la ventana y hace que crezca hasta el nuevo radio indicado 
	// Va dibujando esa pelota mientras su radio crece o decrece (de píxel en píxel)
	private static void dibujaYCrece( VentanaGrafica v, Pelota3 p, double nuevoRadio ) {
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
	
	public double radio;  // Radio de pelota
	public double x;      // Coordenada x de centro de pelota
	public double y;      // Coordenada y de centro de pelota
	public char color;    // Color de la pelota ('a' = azul, 'v' = verde)
	private boolean bota;  // Información de si la pelota bota o no
	
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
		v.dibujaCirculo( x, y, radio, 2f, color );
	}
	
	/** Borra la pelota en una ventana
	 * @param v	Ventana en la que borrar la pelota
	 */
	public void borra( VentanaGrafica v ) {
		v.borraCirculo( x, y, radio, 2f );
	}
	
	// Faltan gets y sets (para exponer y poder modificar públicamente los atributos)

	public String toString() {
		return x + "," + y;
	}
	
	public void visualiza() {
		System.out.println( this.x + "," + this.y + " - Radio " + this.radio  );
	}
	
	@Override
	public boolean equals(Object obj) {
		Pelota3 p2 = (Pelota3) obj;  // Cast de obj a Pelota2 (lo entenderemos mejor al ver herencia)
		return p2.x==x && p2.y==y; // Devuelve true o false dependiendo de las coordenadas de las pelotas this y p2
	}
	
}
