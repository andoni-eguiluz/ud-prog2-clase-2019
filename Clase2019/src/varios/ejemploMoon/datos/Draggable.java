package varios.ejemploMoon.datos;

/** Comportamiento de las fichas en las que se puede hacer drag sobre otra ficha 
 * @author andoni.eguiluz at deusto.es
 */
public interface Draggable {
	/** Mover (drag) sobre otra ficha
	 * @param ficha	Ficha destino
	 * @return	true si hay que redibujar después del drag, false en case contrario
	 */
	public boolean dragTo( Ficha ficha );
}
