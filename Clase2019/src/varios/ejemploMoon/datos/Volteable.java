package varios.ejemploMoon.datos;

/** Interfaz para las fichas que pueden voltearse en el juego (ponerse del anverso o del reverso)
 * @author andoni.eguiluz at deusto.es
 */
public interface Volteable {
	/** Cambia el estado de la ficha
	 * @param volteado	true si está volteada (se ve la parte trasera), false si no (se ve la ficha)
	 */
	void setVolteado( boolean volteado );
	
	/** Informa del estado de la ficha
	 * @return	true si está volteada (se ve la parte trasera), false si no (se ve la ficha)
	 */
	boolean isVolteado();
}
