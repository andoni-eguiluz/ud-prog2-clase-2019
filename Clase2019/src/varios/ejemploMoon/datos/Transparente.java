package tema5.ejemploMoon.datos;

/** Interfaz para las fichas que pueden hacerse transparentes u opacas en pantalla (animación de transparencia)
 * @author andoni.eguiluz at deusto.es
 */
public interface Transparente {
	/** Cambia la opacidad de la ficha
	 * @param valOpacidad	Valor de opacidad entre los límites 1.0f = completamente opaca, 0.0f = completamente transparente
	 */
	void setOpacidad( float valOpacidad );
}
