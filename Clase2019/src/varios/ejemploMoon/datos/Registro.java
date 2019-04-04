package varios.ejemploMoon.datos;

import java.awt.Point;
import java.util.ArrayList;

import varios.ejemploMoon.EstadoDeJuego;
import varios.ejemploMoon.Mesa;

/** Clase para ficha de registro
 * @author andoni.eguiluz at deusto.es
 */
public class Registro extends Ficha implements Movible, Estropeable {
	
	private TipoRegistro tipo;          // Tipo de registro
	private ArrayList<Bit> bits;        // Lista de bits
	private boolean estropeada = false; // Estropeada o no
	
	/** Crea una nueva ficha registro
	 * @param x	Posición x (píxels)
	 * @param y	Posición y (píxels)
	 * @param tamanyo	Tamaño de ancho y de alto (píxels) - las fichas son cuadradas
	 * @param mesa	Mesa a la que pertenece la ficha
	 * @param tipo	Tipo del registro
	 * @param bits	Lista de bits incluidos en este registro
	 */
	public Registro(int x, int y, int tamanyo, Mesa mesa, TipoRegistro tipo, ArrayList<Bit> bits) {
		super(x, y, tamanyo, mesa);
		this.tipo = tipo;
		this.bits = bits;
		nombreImg = "/img/moon/registro-" + tipo.toString().toLowerCase() + ".png";
	}

	/** Devuelve el tipo
	 * @return	tipo
	 */
	public TipoRegistro getTipo() {
		return tipo;
	}

	/** Devuelve los bits del registro
	 * @return	lista de bits
	 */
	public ArrayList<Bit> getBits() {
		return bits;
	}

	@Override
	public boolean click( Point punto ) {
		if (mesa.getJuego().getEstadoDeJuego()==EstadoDeJuego.EN_EVENTO_OK) {
			if (estropeada) {
				setEstropeada( false );
				mesa.getJuego().setMensaje( "Registro " + tipo + " reparada." );
			}
		}
		return false;
	}
	
	@Override
	public boolean drag( Point puntoIni, Point puntoFin ) {
		Point centroDesplazamiento = new Point( getX() + (puntoFin.x-puntoIni.x), getY() + (puntoFin.y-puntoIni.y) );
		mesa.getJuego().mover( this, centroDesplazamiento );  // El movimiento lo hace el juego desde el nuevo estado
		return true;
	}
	
	/** Mueve el registro
	 * @param puntoFin	Punto final del movimiento
	 */
	public void mover( Point puntoFin ) {
		int difx = puntoFin.x - x;  // Diferencias entre donde estoy y donde tengo que moverme
		int dify = puntoFin.y - y;
		x = puntoFin.x;
		y = puntoFin.y;
		for (Bit bit : bits) {  // También se mueven mis bits (con las mismas diferencias)
			bit.setX( bit.getX() + difx );
			bit.setY( bit.getY() + dify );
		}
	}
	
	/** Devuelve el valor del registro (interpretando el valor binario de sus bits)
	 * @return	Valor entero del registro, de acuerdo al estado de sus bits
	 */
	public int getValor() {
		int valor = 0;
		for (Bit bit : bits) {
			valor += bit.getValorPeso();
		}
		return valor;
	}
	
	/** Cambia el valor del registro, cambiando el valor de sus bits
	 * @param nuevoValor	Nuevo valor para el registro
	 */
	public void setValor( int nuevoValor ) {
		for (Bit bit : bits) {
			if (nuevoValor>=bit.getPeso()) {
				bit.setValor( true );
				nuevoValor -= bit.getPeso();
			} else {
				bit.setValor( false );
			}
		}
	}
	
	@Override
	public String getNombre() {
		return tipo.toString();
	}
	
	@Override
	public void setEstropeada( boolean estropeada ) {
		this.estropeada = estropeada;
		if (estropeada) {
			nombreImg = "/img/moon/evento-error_" + tipo.toString().toLowerCase() + ".png";
		} else {
			nombreImg = "/img/moon/registro-" + tipo.toString().toLowerCase() + ".png";
		}
	}	
	
	@Override
	public boolean isEstropeada() {
		return estropeada;
	}
	
	@Override
	public String toString() {
		return "Registro " + tipo;
	}
	
	/* No reimplementamos equals porque consideramos las fichas iguales solo cuando son la misma. Si hubiera que considerar la igualdad por contenido, podría ser:
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Registro)) return false;
		return tipo==((Registro)obj).tipo && bits.equals( ((Registro)obj).bits );
	}
	*/
	
}
