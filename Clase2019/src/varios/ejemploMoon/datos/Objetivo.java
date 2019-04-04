package varios.ejemploMoon.datos;

import java.awt.Point;

import tema5.VentanaGrafica;
import varios.ejemploMoon.EstadoDeJuego;
import varios.ejemploMoon.Mesa;

/** Clase para ficha objetivo
 * @author andoni.eguiluz at deusto.es
 */
public class Objetivo extends Ficha implements Volteable, Transparente {

	/** Puntos que se ganan por conseguir un objetivo */
	public static final int PUNTOS_POR_OBJETIVO = 10;
	
	private int valor;              // Valor del objetivo (entero)
	private boolean volteado;       // Volteada-no vista, no jugable (true) o no volteada-vista, jugable (false)
	private float opacidad = 1.0f;  // Opacidad (para animaciones de transparencia)
	
	/** Crea una nueva ficha objetivo
	 * @param x	Posición x (píxels)
	 * @param y	Posición y (píxels)
	 * @param tamanyo	Tamaño de ancho y de alto (píxels) - las fichas son cuadradas
	 * @param mesa	Mesa a la que pertenece la ficha
	 * @param valor	Valor entero correspondiente a los bits deseados para el objetivo
	 */
	public Objetivo( int x, int y, int tamanyo, Mesa mesa, int valor ) {
		super( x, y, tamanyo, mesa );
		this.valor = valor;
		nombreImg = "/img/moon/objetivo.png";
		volteado = true;
	}
	
	/** Devuelve el valor entero del objetivo (valor a conseguir en el registro AX)
	 * @return	Valor del objetivo a conseguir
	 */
	public int getValor() {
		return valor;
	}
	
	@Override
	public void setVolteado( boolean volteado ) {
		this.volteado = volteado; 
	}
	
	@Override
	public boolean isVolteado() {
		return volteado;
	}
	
	@Override
	public void dibuja() {
		System.out.println( this );
		VentanaGrafica v = mesa.getVentana();
		if (volteado) {
			v.dibujaImagen( "/img/moon/back.png", x, y, tamanyo, tamanyo, 1.0, 0.0, opacidad );
		} else {
			v.dibujaImagen( nombreImg, x, y, tamanyo, tamanyo, 1.0, 0.0, opacidad );
			int valorTemp = valor;
			for (int bit=(Mesa.NUM_BITS-1); bit>=0; bit--) {
				int peso = 1 << bit;
				if (valorTemp>=peso) {
					v.dibujaImagen( "/img/moon/objetivo-bit" + bit + ".png", x, y, tamanyo, tamanyo, 1.0, 0.0, opacidad );
					System.out.println( "  - " + "/img/moon/objetivo-bit" + bit + ".png" );
					valorTemp -= peso;
				}
			}
		}
	}
	
	@Override
	public boolean click( Point punto ) {
		if (mesa.getJuego().getEstadoDeJuego()==EstadoDeJuego.EN_EVENTO_OK) return false;  // Click sin sentido porque no se estropea un objetivo
		// Comprobar si de verdad el objetivo está resuelto y quitarlo de la lista
		if (seCumpleObjetivo()) {
			mesa.getJuego().setMensaje( toString() + " cumplido!" );
			mesa.getJuego().quitarObjetivo( this );
			mesa.getJuego().incPuntuacion( PUNTOS_POR_OBJETIVO );
		}
		return false;
	}
	
	/** Informa si el objetivo está descubierto y se cumple
	 * @return	true si el objetivo está descubierto y el registro AX tiene ese mismo valor, false en caso contrario
	 */
	public boolean seCumpleObjetivo() {
		if (volteado) return false;
		return (mesa.getRegistroAX().getValor() == valor);
	}
	
	@Override
	public void setOpacidad(float valOpacidad) {
		opacidad = valOpacidad;
	}
	
	@Override
	public String toString() {
		return "Objetivo " + Integer.toBinaryString( valor );
	}

}
