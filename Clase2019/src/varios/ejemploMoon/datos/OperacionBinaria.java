package varios.ejemploMoon.datos;

import java.awt.Point;
import java.util.ArrayList;

import varios.ejemploMoon.EstadoDeJuego;
import varios.ejemploMoon.Mesa;

/** Clase para ficha de operación binaria
 * @author andoni.eguiluz at deusto.es
 */
public class OperacionBinaria extends Operacion {
	
	/** Tipo de operación */
	public enum TipoOp {
		                                MOV, AND, OR, XOR;
		private static int[] costes = {   2,   1,  1,   1 };
		/** Devuelve el coste de la operación asociada a este tipo
		 * @return	Coste en unidades de energía
		 */
		public int getCoste() {
			return costes[ ordinal() ];
		}
	}
	
	private TipoOp tipo;  // Tipo de la operación
	
	/** Crea una nueva ficha operación binaria
	 * @param x	Posición x (píxels)
	 * @param y	Posición y (píxels)
	 * @param tamanyo	Tamaño de ancho y de alto (píxels) - las fichas son cuadradas
	 * @param mesa	Mesa a la que pertenece la ficha
	 * @param tipo	Tipo de la operación
	 * @param coste	Coste de unidades de energía de la ejecución de la operación
	 */
	public OperacionBinaria( int x, int y, int tamanyo, Mesa mesa, TipoOp tipo, int coste ) {
		super( x, y, tamanyo, mesa, coste );
		this.tipo = tipo;
		nombreImg = "/img/moon/op-2-" + tipo.toString().toLowerCase() + ".png";
	}
	
	/** Crea una nueva ficha operación binaria
	 * @param x	Posición x (píxels)
	 * @param y	Posición y (píxels)
	 * @param tamanyo	Tamaño de ancho y de alto (píxels) - las fichas son cuadradas
	 * @param mesa	Mesa a la que pertenece la ficha
	 * @param tipo	Tipo de la operación
	 */
	public OperacionBinaria( int x, int y, int tamanyo, Mesa mesa, TipoOp tipo ) {
		this( x, y, tamanyo, mesa, tipo, tipo.getCoste() );
	}
	
	/** Devuelve el tipo
	 * @return	tipo
	 */
	public TipoOp getTipo() {
		return tipo;
	}
	
	@Override
	public String getNombreTipo() {
		return tipo.toString();
	}
	
	@Override
	public boolean click( Point punto ) {
		if (mesa.getJuego().getEstadoDeJuego() == EstadoDeJuego.ESPERANDO_ACCION) {
			mesa.getJuego().setMensaje( "Selecciona registro origen para operación " + tipo );
			mesa.getJuego().operar( this );
		} else if (mesa.getJuego().getEstadoDeJuego()==EstadoDeJuego.EN_EVENTO_OK) {
			if (estropeada) {
				setEstropeada( false );
				mesa.getJuego().setMensaje( "Operación " + tipo + " reparada." );
			}
		}
		return false;
	}
	
	@Override
	public boolean dragTo( Ficha ficha ) {
		if (mesa.getJuego().getEstadoDeJuego() == EstadoDeJuego.ESPERANDO_ACCION && ficha instanceof Registro) {
			mesa.getJuego().esperandoSegundoOperando( this, (Registro) ficha );
		}
		return false;
	}

	/** Realiza la operación
	 * @param regDesde	Registro operando 1
	 * @param regHasta	Registro operando 2 (destino)
	 */
	public void opera(Registro regDesde, Registro regHasta) {
		if (mesa.getBateria().getCarga()<coste) {
			mesa.getJuego().setMensaje( "No hay suficiente energía para esta operación." );
		} else {
			// Cambio de batería
			ArrayList<Transparente> lTransp = new ArrayList<Transparente>();
			lTransp.add( mesa.getBateria() );
			mesa.getJuego().animacionAparicion( lTransp, false );
			mesa.getBateria().setCarga( mesa.getBateria().getCarga() - coste );
			mesa.getJuego().animacionAparicion( lTransp, true );
			mesa.getJuego().incGastoEnergia( coste );
			// Operación
			switch (tipo) {
				case MOV: {
					// Duplicamos los bits para mover sus copias. Calculamos los movimientos
					ArrayList<Ficha> lFichas = new ArrayList<Ficha>();
					ArrayList<Point> lPuntos = new ArrayList<Point>();
					for (int i=0; i<regDesde.getBits().size(); i++) {
						Bit bitDesde = regDesde.getBits().get(i);
						Bit bitHasta = regHasta.getBits().get(i);
						Bit bitNuevo = new Bit( bitDesde );
						lFichas.add( bitNuevo );
						lPuntos.add( new Point( bitHasta.x, bitHasta.y ) );
						mesa.getFichas().add( bitNuevo );
					}
					// Movemos los bits nuevos
					mesa.getJuego().animacionMovimiento( lFichas, lPuntos );
					// Eliminamos los bits de animación
					for (Ficha ficha : lFichas) {
						mesa.getFichas().remove( ficha );
					}
					// Cambiamos de verdad los bits de destino y los repintamos
					for (int i=0; i<regDesde.getBits().size(); i++) {
						Bit bitDesde = regDesde.getBits().get(i);
						Bit bitHasta = regHasta.getBits().get(i);
						bitHasta.setValor( bitDesde.getValor() );
					}
					mesa.getJuego().dibuja();
					break;
				}
				case AND: {
					// Duplicamos los bits 0 para mover sus copias. Calculamos los movimientos
					ArrayList<Ficha> lFichas = new ArrayList<Ficha>();
					ArrayList<Point> lPuntos = new ArrayList<Point>();
					for (int i=0; i<regDesde.getBits().size(); i++) {
						Bit bitDesde = regDesde.getBits().get(i);
						if (!bitDesde.getValor()) {
							Bit bitHasta = regHasta.getBits().get(i);
							Bit bitNuevo = new Bit( bitDesde );
							lFichas.add( bitNuevo );
							lPuntos.add( new Point( bitHasta.x, bitHasta.y ) );
							mesa.getFichas().add( bitNuevo );
						}
					}
					// Movemos los bits nuevos
					mesa.getJuego().animacionMovimiento( lFichas, lPuntos );
					// Eliminamos los bits de animación
					for (Ficha ficha : lFichas) {
						mesa.getFichas().remove( ficha );
					}
					// Cambiamos de verdad los bits de destino y los repintamos
					for (int i=0; i<regDesde.getBits().size(); i++) {
						Bit bitDesde = regDesde.getBits().get(i);
						if (!bitDesde.getValor()) {
							Bit bitHasta = regHasta.getBits().get(i);
							bitHasta.setValor( false );
						}
					}
					mesa.getJuego().dibuja();
					break;
				}
				case OR: {
					// Duplicamos los bits 1 para mover sus copias. Calculamos los movimientos
					ArrayList<Ficha> lFichas = new ArrayList<Ficha>();
					ArrayList<Point> lPuntos = new ArrayList<Point>();
					for (int i=0; i<regDesde.getBits().size(); i++) {
						Bit bitDesde = regDesde.getBits().get(i);
						if (bitDesde.getValor()) {
							Bit bitHasta = regHasta.getBits().get(i);
							Bit bitNuevo = new Bit( bitDesde );
							lFichas.add( bitNuevo );
							lPuntos.add( new Point( bitHasta.x, bitHasta.y ) );
							mesa.getFichas().add( bitNuevo );
						}
					}
					// Movemos los bits nuevos
					mesa.getJuego().animacionMovimiento( lFichas, lPuntos );
					// Eliminamos los bits de animación
					for (Ficha ficha : lFichas) {
						mesa.getFichas().remove( ficha );
					}
					// Cambiamos de verdad los bits de destino y los repintamos
					for (int i=0; i<regDesde.getBits().size(); i++) {
						Bit bitDesde = regDesde.getBits().get(i);
						if (bitDesde.getValor()) {
							Bit bitHasta = regHasta.getBits().get(i);
							bitHasta.setValor( true );
						}
					}
					mesa.getJuego().dibuja();
					break;
				}
				case XOR: {
					// Duplicamos los bits 1 para mover sus copias. Calculamos los movimientos
					ArrayList<Ficha> lFichas = new ArrayList<Ficha>();
					ArrayList<Point> lPuntos = new ArrayList<Point>();
					for (int i=0; i<regDesde.getBits().size(); i++) {
						Bit bitDesde = regDesde.getBits().get(i);
						if (bitDesde.getValor()) {
							Bit bitHasta = regHasta.getBits().get(i);
							Bit bitNuevo = new Bit( bitDesde );
							lFichas.add( bitNuevo );
							lPuntos.add( new Point( bitHasta.x, bitHasta.y ) );
							mesa.getFichas().add( bitNuevo );
						}
					}
					// Movemos los bits nuevos
					mesa.getJuego().animacionMovimiento( lFichas, lPuntos );
					// Eliminamos los bits de animación
					for (Ficha ficha : lFichas) {
						mesa.getFichas().remove( ficha );
					}
					// Cambiamos de verdad los bits de destino y los repintamos, y calculamos la lista de los 1-1
					lTransp = new ArrayList<Transparente>();
					for (int i=0; i<regDesde.getBits().size(); i++) {
						Bit bitDesde = regDesde.getBits().get(i);
						if (bitDesde.getValor()) {
							Bit bitHasta = regHasta.getBits().get(i);
							if (bitHasta.getValor()) {  // 1 xor 1
								lTransp.add( bitHasta );
							}
							bitHasta.setValor( true );
						}
					}
					// Animamos y cambiamos los bits 1xor1
					mesa.getJuego().animacionAparicion( lTransp, false );
					for (Transparente ft : lTransp) {
						((Bit)ft).setValor( false );
					}
					mesa.getJuego().animacionAparicion( lTransp, true );
					break;
				}
			}
			mesa.getJuego().setMensaje( "" );
		}
		mesa.getJuego().setEstadoDeJuego( EstadoDeJuego.ESPERANDO_ACCION );
	}

	@Override
	public String getNombre() {
		return tipo.toString();
	}
	
	@Override
	public void setEstropeada(boolean estropeada) {
		super.setEstropeada(estropeada);
		if (estropeada) {
			nombreImg = "/img/moon/evento-error_" + tipo.toString().toLowerCase() + ".png";
		} else {
			nombreImg = "/img/moon/op-2-" + tipo.toString().toLowerCase() + ".png";
		}
	}
	
	@Override
	public String toString() {
		return "Operación " + tipo;
	}
	
}
