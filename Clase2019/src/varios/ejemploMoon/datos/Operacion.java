package tema5.ejemploMoon.datos;

import tema5.ejemploMoon.Mesa;

/** Clase abstracta para ficha de operación
 * @author andoni.eguiluz at deusto.es
 */
public abstract class Operacion extends Ficha implements Draggable, Estropeable {
	
	protected int coste;          // Medias unidades de energía de coste de cada operación
	protected boolean estropeada; // Estropeada o no
	
	/** Crea una nueva ficha operación
	 * @param x	Posición x (píxels)
	 * @param y	Posición y (píxels)
	 * @param tamanyo	Tamaño de ancho y de alto (píxels) - las fichas son cuadradas
	 * @param mesa	Mesa a la que pertenece la ficha
	 * @param coste	Coste de unidades de energía de la ejecución de la operación
	 */
	public Operacion(int x, int y, int tamanyo, Mesa mesa, int coste) {
		super(x, y, tamanyo, mesa);
		this.coste = coste;
	}
	
	/** Devuelve el coste de la operación
	 * @return	coste en medias unidades de energía
	 */
	public int getCoste() {
		return coste;
	}
	
	/** Cambia el coste de la operación
	 * @param coste	en medias unidades de energía
	 */
	public void setCoste(int coste) {
		this.coste = coste;
	}
	
	@Override
	public void setEstropeada( boolean estropeada ) {
		this.estropeada = estropeada;
	}

	@Override
	public boolean isEstropeada() {
		return estropeada;
	}
	
	/** Devuelve el nombre texto del tipo de operación
	 * @return	Tipo de operación en formato texto
	 */
	public abstract String getNombreTipo();

	// No hace falta indicarlo porque implementa un interfaz y si no se indica sigue siendo abstract
	// public abstract boolean dragTo( Ficha ficha );
	
}
