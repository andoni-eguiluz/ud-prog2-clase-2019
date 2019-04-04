package varios.ejemploMoon.datos;

import java.awt.Point;

import tema5.VentanaGrafica;
import varios.ejemploMoon.Mesa;

/** Clase para ficha de batería
 * @author andoni.eguiluz at deusto.es
 */
public class Bateria extends Ficha implements Transparente {
	
	/** Carga máxima de la batería (medido en medias baterías) */
	public static final int CARGA_MAXIMA = 6;
	
	private int carga;              // Carga actual de la batería
	private float opacidad = 1.0f;  // Opacidad (para animaciones de transparencia)
	
	/** Crea una nueva ficha batería
	 * @param x	Posición x (píxels)
	 * @param y	Posición y (píxels)
	 * @param tamanyo	Tamaño de ancho y de alto (píxels) - las fichas son cuadradas
	 * @param mesa	Mesa a la que pertenece la ficha
	 * @param carga	Carga inicial de la batería
	 */
	public Bateria(int x, int y, int tamanyo, Mesa mesa, int carga) {
		super(x, y, tamanyo, mesa);
		this.carga = carga;
		recalcNombre();
	}
	
	// Recalcula el nombre del fichero gráfico asociado (llamarla tras cambiar la carga)
	private void recalcNombre() {
		nombreImg = "/img/moon/bat-" + carga + ".png";
	}
	
	/** Devuelve la carga de la batería
	 * @return	Carga actual de la batería (media en "medias baterías")
	 */
	public int getCarga() {
		return carga;
	}
	
	/** Cambia la carga de la batería
	 * @param carga	Nueva carga medida en "medias baterías"
	 */
	public void setCarga(int carga) {
		this.carga = carga;
		recalcNombre();
		dibuja();
	}
	
	/** Cambia la carga de la batería, reiniciándola a la carga máxima
	 */
	public void reset() {
		carga = CARGA_MAXIMA;
		recalcNombre();
		dibuja();
	}
	
	@Override
	public boolean click( Point punto ) {
		// Cambia el modo de animación
		boolean nuevoModo = mesa.getJuego().isConAnimaciones();
		mesa.getJuego().setConAnimaciones( !nuevoModo );
		mesa.getJuego().setMensaje( "Activado modo " + (nuevoModo?"SIN":"CON") + " animaciones." );
		return false;
	}
	
	@Override
	public String toString() {
		return "Batería con carga " + carga/2.0;
	}
	
	@Override
	public void setOpacidad(float valOpacidad) {
		opacidad = valOpacidad;
	}
	
	@Override
	public void dibuja() {
		VentanaGrafica v = mesa.getVentana();
		v.dibujaImagen( nombreImg, x, y, tamanyo, tamanyo, 1.0, 0.0, opacidad );
	}
	
}
