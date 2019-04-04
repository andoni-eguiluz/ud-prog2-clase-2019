package varios.ejemploMoon.datos;

import java.awt.Point;

import tema5.VentanaGrafica;
import varios.ejemploMoon.Mesa;

/** Clase abstracta para todas las fichas
 * @author andoni.eguiluz at deusto.es
 */
public abstract class Ficha {

	protected int x; // Coordenada x del centro de la ficha en la mesa
	protected int y; // Idem y
	protected int tamanyo; // Tamaño (ancho = alto) de la ficha
	protected Mesa mesa; // Mesa en la que está la ficha
	protected String nombreImg; // Nombre del fichero gráfico
	
	/** Crea una nueva ficha
	 * @param x	Posición x (píxels)
	 * @param y	Posición y (píxels)
	 * @param tamanyo	Tamaño de ancho y de alto (píxels) - las fichas son cuadradas
	 * @param mesa	Mesa a la que pertenece la ficha
	 */
	public Ficha(int x, int y, int tamanyo, Mesa mesa) {
		super();
		this.x = x;
		this.y = y;
		this.tamanyo = tamanyo;
		this.mesa = mesa;
	}
	
	/** Devuelve la coordenada x de la ficha
	 * @return	Píxels
	 */
	public int getX() {
		return x;
	}
	
	/** Cambia la coordenada x de la ficha
	 * @param x	Píxels (0 = izquierda, +n = derecha)
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/** Devuelve la coordenada y de la ficha
	 * @return
	 */
	public int getY() {
		return y;
	}
	
	/** Cambia la coordenada y de la ficha
	 * @param y	Pixels (0 = arriba, +n = abajo)
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/** Devuelve el tamaño de la ficha
	 * @return	Píxels de anchura y de altura (las fichas son cuadradas)
	 */
	public int getTamanyo() {
		return tamanyo;
	}
	
	/** Modifica el tamaño de la ficha
	 * @param tamanyo	Píxels de ancho y de alto (las fichas son cuadradas)
	 */
	public void setTamanyo(int tamanyo) {
		this.tamanyo = tamanyo;
	}
	
	/** Devuelve la mesa asociada a la ficha
	 * @return	Mesa de la ficha
	 */
	public Mesa getMesa() {
		return mesa;
	}
	
	/** Dibuja la ficha en la ventana gráfica asociada a su mesa
	 */
	public void dibuja() {
		VentanaGrafica v = mesa.getVentana();
		v.dibujaImagen( nombreImg, x, y, tamanyo, tamanyo, 1.0, 0.0, 1.0f );
	}
	
	/** Informa si un punto visual está o no dentro del espacio de la ficha
	 * @param punto	Punto en píxels de la ventana gráfica
	 * @return	true si el punto está dentro del cuadrado de la ficha, false si está fuera
	 */
	public boolean puntoEnFicha( Point punto ) {
		int distX = Math.abs( punto.x - x );
		int distY = Math.abs( punto.y - y );
		return (distX<=tamanyo/2 && distY<=tamanyo/2);
	}
	
	/** Realiza un click de ratón en la ficha
	 * @param punto	Punto x,y en el que se ha realizado el click
	 * @return	true si hay que redibujar tras la acción de click, false en caso contrario
	 */
	abstract public boolean click( Point punto );
	
}
