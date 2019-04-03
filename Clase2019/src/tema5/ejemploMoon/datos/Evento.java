package tema5.ejemploMoon.datos;

import java.awt.Point;

import tema5.VentanaGrafica;
import tema5.ejemploMoon.EstadoDeJuego;
import tema5.ejemploMoon.Mesa;

/** Clase para ficha de evento
 * @author andoni.eguiluz at deusto.es
 */
public class Evento extends Ficha implements Volteable, Transparente {
	
	/** Tipo de evento */
	public enum TipoOp {
		BUG, ERROR_BX, ERROR_CX, ERROR_DX, ERROR_NOT, ERROR_ROL, ERROR_XOR, 
		RESET_AX, RESET_BX, RESET_CX, RESET_DX, OK
	}
	
	private TipoOp tipo;             // Tipo del evento
	private float opacidad = 1.0f;   // Opacidad (para animaciones de transparencia)
	private boolean volteado = true; // Volteada-no vista, no jugable (true) o no volteada-vista, jugable (false)
	
	/** Crea una nueva ficha evento
	 * @param x	Posición x (píxels)
	 * @param y	Posición y (píxels)
	 * @param tamanyo	Tamaño de ancho y de alto (píxels) - las fichas son cuadradas
	 * @param mesa	Mesa a la que pertenece la ficha
	 * @param tipo	Tipo del evento
	 */
	public Evento( int x, int y, int tamanyo, Mesa mesa, TipoOp tipo ) {
		super( x, y, tamanyo, mesa );
		this.tipo = tipo;
		calcNombreImg();
	}

	// Recalcula el nombre del fichero gráfico asociado (llamarla tras cambiar el tipo)
	private void calcNombreImg() {
		nombreImg = "/img/moon/evento-" + tipo.toString().toLowerCase() + ".png";
	}
	
	/** Devuelve el tipo
	 * @return	tipo
	 */
	public TipoOp getTipo() {
		return tipo;
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
		VentanaGrafica v = mesa.getVentana();
		if (volteado) {
			v.dibujaImagen( "/img/moon/back.png", x, y, tamanyo, tamanyo, 1.0, 0.0, opacidad );
		} else {
			v.dibujaImagen( nombreImg, x, y, tamanyo, tamanyo, 1.0, 0.0, opacidad );
		}
	}
	
	@Override
	public boolean click(Point punto) {
		return false;
	}
	
	/** Procesa el evento. El cálculo es inmediato excepto en el caso del OK, que espera del usuario la información de la ficha a reparar
	 */
	public void procesa() {
		boolean quitarloAlFinal = true;
		if (tipo.toString().startsWith( "RESET" )) {
			mesa.getJuego().resetReg( tipo.toString().substring( tipo.toString().length()-2 ) );
		} else if (tipo.toString().startsWith( "ERROR" )) {
			int posiNom = tipo.toString().indexOf( "_" );
			mesa.getJuego().ponErrorEnFicha( tipo.toString().substring( posiNom+1 ) );
		} else if (tipo==TipoOp.BUG) {
			quitarloAlFinal = false;
		} else if (tipo==TipoOp.OK) {
			quitarloAlFinal = false;
			mesa.getJuego().setEstadoDeJuego( EstadoDeJuego.EN_EVENTO_OK );
			mesa.getJuego().setMensaje( "Indica qué elemento erróneo quieres arreglar con OK" );
		}
		if (quitarloAlFinal) {
			mesa.getJuego().quitarObjetivo( this );
		}
	}
	
	@Override
	public void setOpacidad(float valOpacidad) {
		opacidad = valOpacidad;
	}
	
	@Override
	public String toString() {
		return "Operación " + tipo;
	}
	
}
