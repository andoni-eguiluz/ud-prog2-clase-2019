package varios.ejemploMoon.datos;

import java.awt.Point;
import java.util.ArrayList;

import varios.ejemploMoon.EstadoDeJuego;
import varios.ejemploMoon.Mesa;

/** Clase para ficha de operación unaria
 * @author andoni.eguiluz at deusto.es
 */
public class OperacionUnaria extends Operacion {
	
	/** Tipo de operación */
	public enum TipoOp {
		                                ROR, ROL, NOT, INC, DEC;
		private static int[] costes = {   2,   2,   2,   4,   4 };
		/** Devuelve el coste de la operación asociada a este tipo
		 * @return	Coste en unidades de energía
		 */
		public int getCoste() {
			return costes[ ordinal() ];
		}
	}
	
	private TipoOp tipo;  // Tipo de la operación
	
	/** Crea una nueva ficha operación unaria
	 * @param x	Posición x (píxels)
	 * @param y	Posición y (píxels)
	 * @param tamanyo	Tamaño de ancho y de alto (píxels) - las fichas son cuadradas
	 * @param mesa	Mesa a la que pertenece la ficha
	 * @param tipo	Tipo de la operación
	 * @param coste	Coste de unidades de energía de la ejecución de la operación
	 */
	public OperacionUnaria( int x, int y, int tamanyo, Mesa mesa, TipoOp tipo, int coste ) {
		super( x, y, tamanyo, mesa, coste );
		this.tipo = tipo;
		nombreImg = "/img/moon/op-1-" + tipo.toString().toLowerCase() + ".png";
	}
	
	/** Crea una nueva ficha operación unaria
	 * @param x	Posición x (píxels)
	 * @param y	Posición y (píxels)
	 * @param tamanyo	Tamaño de ancho y de alto (píxels) - las fichas son cuadradas
	 * @param mesa	Mesa a la que pertenece la ficha
	 * @param tipo	Tipo de la operación
	 */
	public OperacionUnaria( int x, int y, int tamanyo, Mesa mesa, TipoOp tipo ) {
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
			mesa.getJuego().setMensaje( "Selecciona registro para operación " + tipo );
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
			mesa.getJuego().operar( this );
			mesa.getJuego().setEstadoDeJuego( EstadoDeJuego.OPERANDO );
			mesa.getJuego().setMensaje( "Realizando operación " + tipo + " con " + ficha.toString() );
			opera( (Registro)ficha );
		}
		return false;
	}
	
	/** Realiza la operación
	 * @param regDesde	Registro operando
	 */
	public void opera(Registro reg) {
		if (mesa.getBateria().getCarga()<coste) {
			mesa.getJuego().setMensaje( "No hay suficiente energía para esta operación." );
		} else {
			if (reg.isEstropeada()) {
				mesa.getJuego().setMensaje( "Registro " + reg.getNombre() + " estropeado. No puede utilizarse" );
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
					case NOT: {
						// Ocultamos los bits
						lTransp.clear();
						for (Bit bit : reg.getBits()) {
							lTransp.add( bit );
						}
						mesa.getJuego().animacionAparicion( lTransp, false );
						// Cambiamos sus valores
						for (Bit bit : reg.getBits()) {
							bit.setValor( !bit.getValor() );
						}
						// Y mostramos los bits
						mesa.getJuego().animacionAparicion( lTransp, true );
						break;
					}
					case ROL: {
						// Duplicamos los bits para moverlos (y ocultamos los originales). Calculamos los movimientos
						ArrayList<Ficha> lFichas = new ArrayList<Ficha>();
						ArrayList<Point> lPuntos = new ArrayList<Point>();
						boolean primero = true;
						for (Bit bit : reg.getBits()) {
							Bit bitNuevo = new Bit( bit );
							lFichas.add( bitNuevo );
							lPuntos.add( new Point( bitNuevo.x - Mesa.X_DIST_COLS, bitNuevo.y ) );
							if (primero) { // Truco para que el bit que se va a mover esté por "encima" de los otros
								mesa.getFichas().add( bitNuevo );
								primero = false;
							} else {
								mesa.getFichas().add( mesa.getFichas().size()-1, bitNuevo );
							}
							bit.setOpacidad( 0.0f );
						}
						// Movemos los bits 
						mesa.getJuego().animacionMovimiento( lFichas, lPuntos );
						mesa.getJuego().animacionMovimiento( lFichas.get(0), new Point( lFichas.get(0).x, lFichas.get(0).y - 25 ));
						mesa.getJuego().animacionMovimiento( lFichas.get(0), new Point( lFichas.get(0).x + Mesa.X_DIST_COLS*lFichas.size(), lFichas.get(0).y ));
						mesa.getJuego().animacionMovimiento( lFichas.get(0), new Point( lFichas.get(0).x, lFichas.get(0).y + 25 ));
						// Eliminamos los bits de animación
						for (Ficha ficha : lFichas) {
							mesa.getFichas().remove( ficha );
						}
						// Cambiamos de verdad los bits originales y los mostramos de nuevo
						boolean valRot = reg.getBits().get(0).getValor();
						for (int i=0;i<reg.getBits().size()-1;i++) {
							Bit bit = reg.getBits().get(i);
							bit.setValor( reg.getBits().get(i+1).getValor() );
							bit.setOpacidad( 1.0f );
						}
						reg.getBits().get( reg.getBits().size()-1 ).setValor( valRot );
						reg.getBits().get( reg.getBits().size()-1 ).setOpacidad( 1.0f );
						mesa.getJuego().dibuja();
						break;
					}
					case ROR: {
						// Duplicamos los bits para moverlos (y ocultamos los originales). Calculamos los movimientos
						ArrayList<Ficha> lFichas = new ArrayList<Ficha>();
						ArrayList<Point> lPuntos = new ArrayList<Point>();
						for (Bit bit : reg.getBits()) {
							Bit bitNuevo = new Bit( bit );
							lFichas.add( bitNuevo );
							lPuntos.add( new Point( bitNuevo.x + Mesa.X_DIST_COLS, bitNuevo.y ) );
							mesa.getFichas().add( bitNuevo );
							bit.setOpacidad( 0.0f );
						}
						// Movemos los bits 
						int ult = lFichas.size()-1;
						mesa.getJuego().animacionMovimiento( lFichas, lPuntos );
						mesa.getJuego().animacionMovimiento( lFichas.get(ult), new Point( lFichas.get(ult).x, lFichas.get(ult).y - 25 ));
						mesa.getJuego().animacionMovimiento( lFichas.get(ult), new Point( lFichas.get(ult).x - Mesa.X_DIST_COLS*lFichas.size(), lFichas.get(ult).y ));
						mesa.getJuego().animacionMovimiento( lFichas.get(ult), new Point( lFichas.get(ult).x, lFichas.get(ult).y + 25 ));
						// Eliminamos los bits de animación
						for (Ficha ficha : lFichas) {
							mesa.getFichas().remove( ficha );
						}
						// Cambiamos de verdad los bits originales y los mostramos de nuevo
						boolean valRot = reg.getBits().get(ult).getValor();
						for (int i=reg.getBits().size()-1; i>=1; i--) {
							Bit bit = reg.getBits().get(i);
							bit.setValor( reg.getBits().get(i-1).getValor() );
							bit.setOpacidad( 1.0f );
						}
						reg.getBits().get( 0 ).setValor( valRot );
						reg.getBits().get( 0 ).setOpacidad( 1.0f );
						mesa.getJuego().dibuja();
						break;
					}
					case INC: {
						lTransp.clear();
						for (Bit bit : reg.getBits()) {
							lTransp.add( bit );
						}
						mesa.getJuego().animacionAparicion( lTransp, false );
						int valReg = reg.getValor();
						valReg++;
						if (valReg >= (1 << reg.getBits().size())) {  // Desbordamiento
							valReg = 0;
						}
						reg.setValor( valReg );
						mesa.getJuego().animacionAparicion( lTransp, true );
						break;
					}
					case DEC: {
						lTransp.clear();
						for (Bit bit : reg.getBits()) {
							lTransp.add( bit );
						}
						mesa.getJuego().animacionAparicion( lTransp, false );
						int valReg = reg.getValor();
						valReg--;
						if (valReg < 0) {  // Desbordamiento
							valReg = (1 << reg.getBits().size()) - 1; // 2^n - 1
						}
						reg.setValor( valReg );
						mesa.getJuego().animacionAparicion( lTransp, true );
						break;
					}
				}
				mesa.getJuego().setMensaje( "" );
			}
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
			nombreImg = "/img/moon/op-1-" + tipo.toString().toLowerCase() + ".png";
		}
	}
	
	@Override
	public String toString() {
		return "Operación " + tipo;
	}
	
}
