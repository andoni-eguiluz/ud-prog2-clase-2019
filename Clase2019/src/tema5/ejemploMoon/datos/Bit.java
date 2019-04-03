package tema5.ejemploMoon.datos;

import java.awt.Point;

import tema5.VentanaGrafica;
import tema5.ejemploMoon.Mesa;

/** Clase para ficha de bit
 * @author andoni.eguiluz at deusto.es
 */
public class Bit extends Ficha implements Transparente {
	
	private boolean valor;          // Valor (true=1, false=0)
	private int peso;               // Peso del bit (1, 2, 4...)
	private float opacidad = 1.0f;  // Opacidad (para animaciones de transparencia)
	
	/** Crea una nueva ficha bit
	 * @param x	Posición x (píxels)
	 * @param y	Posición y (píxels)
	 * @param tamanyo	Tamaño de ancho y de alto (píxels) - las fichas son cuadradas
	 * @param mesa	Mesa a la que pertenece la ficha
	 * @param valor	Valor del bit (true=1 o false=0)
	 * @param peso	Peso del bit en el registro al que pertenece (1, 2, 4, 8 ...)
	 */
	public Bit(int x, int y, int tamanyo, Mesa mesa, boolean valor, int peso) {
		super(x, y, tamanyo, mesa);
		this.valor = valor;
		this.peso = peso;
		actualizaNombre();
	}
	
	/** Crea una nueva ficha bit
	 * @param bit	Bit del que parte (se copia toda su información)
	 */
	public Bit( Bit bit ) {
		this( bit.x, bit.y, bit.tamanyo, bit.mesa, bit.valor, bit.peso );
	}

	// Recálculo de nombre del fichero gráfico. Llamarlo cada vez que cambia el valor del bit
	private void actualizaNombre() {
		nombreImg = "/img/moon/bit" + (valor?"1":"0") + ".png";
	}
	
	/** Devuelve el valor del bit
	 * @return	true para bit 1, false para bit 0
	 */
	public boolean getValor() {
		return valor;
	}
	
	/** Cambia el valor del bit
	 * @param valor	true para bit 1, false para bit 0
	 */
	public void setValor(boolean valor) {
		this.valor = valor;
		actualizaNombre();
	}
	
	/** Devuelve el peso del bit
	 * @return	Peso del bit (1, 2, 4...)
	 */
	public int getPeso() {
		return peso;
	}
	
	/** Devuelve el valor decimal del bit, dado su peso
	 * @return	Valor decimal (el peso si el bit está activado, 0 si está desactivado)
	 */
	public int getValorPeso() {
		return valor ? peso : 0;
	}

	@Override
	public boolean click( Point punto ) {
		// setValor( !valor );  // Si se quiere cambiar interactivamente un bit (las reglas oficiales no lo permiten)
		// return true;
		return false;
	}
	
	@Override
	public String toString() {
		return "Bit " + (valor?"1":"0") + " con peso " + peso;
	}

	/* No reimplementamos equals porque consideramos las fichas iguales solo cuando son la misma. Si hubiera que considerar la igualdad por contenido, podría ser:
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Bit)) return false;
		return valor==((Bit)obj).valor && peso==((Bit)obj).peso;
	}
	*/

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
