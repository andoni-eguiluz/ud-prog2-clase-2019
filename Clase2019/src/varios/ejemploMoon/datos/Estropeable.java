package tema5.ejemploMoon.datos;

/** Interfaz para las fichas que pueden estropearse (con eventos de ERROR)
 * @author andoni.eguiluz at deusto.es
 */
public interface Estropeable {
	/** Devuelve el nombre de la ficha (en forma de texto)
	 * @return	Nombre de la ficha
	 */
	String getNombre();
	
	/** Modifica el estado de error de la ficha
	 * @param estropeada	true si se produce el error, false si se soluciona
	 */
	void setEstropeada( boolean estropeada );
	
	/** Devuelve la información de error de la ficha
	 * @return	true si está estropeada, false en caso contrario
	 */
	boolean isEstropeada();
}
