package tema4.runnerConInterface;
import java.awt.Color;
import java.util.ArrayList;

import tema3.VentanaGrafica;

/** Clase que permite crear y gestionar asteroides y dibujarlos en una ventana gráfica
 */
public class Asteroide extends ObjetoEspacial implements Rotable, Salible, Explotable {
	
	// =================================================
	// PARTE DE OBJETO (NO STATIC)
	// =================================================
	
	private double radio;  // Radio de asteroide
	private double rot = 0.0; // rotación del asteroide (para hacer animación)
	
	// Atributos para el interfaz Explotable
	private boolean explotado = false;
	private double tiempoExplosion = 3.0;  // La expl dura 3 segundos

	/** Crea un nuevo asteroide
	 * @param radio	Píxels de radio del asteroide (debe ser mayor que 0)
	 * @param x	Coordenada x del centro del asteroide (en píxels)
	 * @param y	Coordenada y del centro del asteroide (en píxels)
	 */
	public Asteroide( double radio, double x, double y ) {
		super( x, y );
		this.radio = radio;
	}
	
	/** Devuelve el radio del asteroide
	 * @return	Radio en píxels
	 */
	public double getRadio() {
		return radio;
	}

	/** Cambia el radio del asteroide. Debe ser mayor que cero
	 * @param radio	Nuevo radio del asteroide (debe ser mayor que cero)
	 */
	public void setRadio(double radio) {
		this.radio = radio;
	}

	// CAMBIO CON EXPLOSION
	/** Dibuja el asteroide en una ventana
	 * @param v	Ventana en la que dibujar el asteroide
	 */
	@Override
	public void dibuja( VentanaGrafica v ) {
		String nombre = "/img/asteroid.png";
		float transp = 1.0f;
		if (estaExplotando()) {
			nombre = "/img/asteroid-roto.png";
			if (tiempoExplosion < 1.0) {
				transp = (float) tiempoExplosion;
			}
		}
		v.dibujaImagen( nombre, x, y, 2*radio/500, rot, transp );  // el gráfico tiene 500 píxels
		if (DIBUJA_ENVOLVENTE) v.dibujaCirculo( x, y, radio, 2f, Color.magenta );  // Pintado a título de referencia de prueba
	}

	/** Rota el asteroide
	 * @param rot	Ángulo a rotar (en radianes)
	 */
	public void rotar( double rot ) {
		this.rot += rot;
	}
	
	
	/** Comprueba si el asteroide se sale completamente por el lado izquierdo de la ventana
	 * @param v	Ventana de comprobación
	 * @return	true si se está saliendo completamente por izquierda, false en caso contrario
	 */
	public boolean seSalePorLaIzquierda( VentanaGrafica v ) {
		return x+radio<=0;
	}

	public void sal( ArrayList<ObjetoEspacial> l ) {
		l.remove( this );
	}

	@Override
	public String toString() {
		return "Asteroide " + super.toString() + " (" + radio + ")";
	}
	
	/** Comprueba si el asteroide es igual a otro objeto dado. Se entiende que dos asteroides son iguales
	 * cuando las coordenadas de sus centros (redondeadas a enteros) son iguales
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Asteroide) {
			Asteroide p2 = (Asteroide) obj;  // Cast de obj a asteroide2 (lo entenderemos mejor al ver herencia)
			return Math.round(p2.x)==Math.round(x) && Math.round(p2.y)==Math.round(y); // Devuelve true o false dependiendo de las coordenadas de los asteroides this y p2
		} else {
			return false;
		}
	}
	
	// Métodos nuevos de interfaz Explotable
	
	@Override
	public void explota() {
		explotado = true;
		tiempoExplosion = 2.0;  // Aquí o en inicialización del atributo
	}

	@Override
	public boolean estaExplotando() {
		return explotado;
	}

	@Override
	public boolean yaDesaparecido() {
		return explotado && tiempoExplosion<=0.0;
	}

	// Métodos redefinidos por interfaz Explotable
	
	@Override
	public void mueve(double segs) {
		super.mueve(segs);
		// COmportamiento de explosión
		if (explotado) {
			tiempoExplosion -= segs;
		}
	}
	
	
	
	
}
