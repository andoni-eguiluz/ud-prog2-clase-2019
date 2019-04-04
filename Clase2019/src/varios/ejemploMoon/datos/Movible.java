package varios.ejemploMoon.datos;

import java.awt.Point;

/** Interfaz para las fichas que pueden moverse directamente en pantalla
 * @author andoni.eguiluz at deusto.es
 */
public interface Movible {
	/** Mover la ficha dados los puntos inicial y final de un drag de ratón
	 * @param puntoIni	Punto inicial del movimiento
	 * @param puntoFin	Punto final del movimiento
	 * @return	true si hay que redibujar tras este movimiento, false en caso contrario
	 */
	public boolean drag( Point puntoIni, Point puntoFin );
}
