package varios.ejemploMoon;

import java.util.ArrayList;
import java.util.Collections;

import tema5.VentanaGrafica;
import varios.ejemploMoon.datos.*;

/** Clase principal de la mesa del juego con toda la información de las fichas que se encuentran en él
 * @author andoni.eguiluz at deusto.es
 */
public class Mesa {

	/** Número de bits del juego */
	public static final int NUM_BITS = 4;
	/** Número máximo de valor de objetivo (hay objetivos desde 0000 a este) */
	public static final int OBJETIVO_MAX = (1 << NUM_BITS) - 1;
	/** Tamaño de las fichas (cuadradas) en píxels (alto y ancho) */
	public static final int TAMANYO = 100;
	/** Distancia en píxels entre columnas */
	public static final int X_DIST_COLS = 115;
	/** Distancia en píxels entre filas */
	public static final int Y_DIST_FILAS = 115;
	/** Posición x inicial de las operaciones */
	public static final int X_OPS = 75;
	/** Posición x inicial de los registros */
	public static final int X_REGS = 450;
	/** Posición x de la columna de objetivos */
	@SuppressWarnings("unused")
	public static final int X_OBJETIVOS = 1050 + (NUM_BITS<=4 ? 0 : X_DIST_COLS*(NUM_BITS-4));  // Calculado en función de los bits (a su derecha)
	/** Posición y del primer objetivo (inferior) */
	public static final int Y_OBJETIVOS = 660;
	/** Posición y de las cabeceras (pesos) */
	public static final int Y_CABS = 85;
	/** Posición y del primer registro (AX) */
	public static final int Y_REG_AX = 200;
	
	private ArrayList<Ficha> fichas;    // Lista de fichas en la mesa (excepto los objetivos y el mazo)
	private Registro registroAX;        // Registro AX
	private Bateria bateria;            // Batería (energía)
	private VentanaGrafica ventana;     // Ventana gráfica asociada a la mesa
	private Moon juego;                 // Juego actual de esta mesa
	private ArrayList<Ficha> mazo;      // Mazo (cargas volteadas) del que se "saca" en cada turno
	private ArrayList<Ficha> objetivos; // Objetivos ya sacados en la mesa (0, el de más abajo)
	
	/** Crea una mesa de juego Moon
	 * @param ventana	Ventana gráfica asociada a esa mesa
	 * @param juego	Juego asociado a esa mesa
	 */
	public Mesa( VentanaGrafica ventana, Moon juego ) {
		fichas = new ArrayList<Ficha>();
		mazo = new ArrayList<Ficha>();
		objetivos = new ArrayList<Ficha>();
		this.ventana = ventana;
		this.juego = juego;
	}
	
	/** Devuelve la batería (energía de turno) de la mesa
	 * @return	batería actual
	 */
	public Bateria getBateria() {
		return bateria;
	}
	
	/** Devuelve el juego asociado a la mesa
	 * @return	objeto juego
	 */
	public Moon getJuego() {
		return juego;
	}
	
	/** Devuelve las fichas de la mesa
	 * @return	lista de fichas
	 */
	public ArrayList<Ficha> getFichas() {
		return fichas;
	}

	/** Devuelve el mazo de la mesa
	 * @return	lista de fichas (volteadas) en el mazo
	 */
	public ArrayList<Ficha> getMazo() {
		return mazo;
	}
	
	/** Devuelve los objetivos ya desplegados en la mesa
	 * @return	lista de objetivos, el primero es el más inferior. Pueden contener null (según van subiendo)
	 */
	public ArrayList<Ficha> getObjetivos() {
		return objetivos;
	}

	/** Devuelve la ventana gráfica asociada a la mesa
	 * @return	ventana gráfica
	 */
	public VentanaGrafica getVentana() {
		return ventana;
	}
	
	/** Devuelve el registro AX
	 * @return	Registro AX (el que se comprueba para los objetivos)
	 */
	public Registro getRegistroAX() {
		return registroAX;
	}
	
	/** Crea los objetos básicos de la mesa al inicio del juego: batería, operaciones, registros, mazo
	 */
	public void init() {
		// Batería
		bateria = new Bateria( X_OPS, Y_CABS, TAMANYO, this, Bateria.CARGA_MAXIMA );
		fichas.add( bateria );
		// Operaciones
		int x = X_OPS;
		int[] altura = { 0, 2, 1, 0, 3 };
		for (int coste=4; coste>=1; coste /= 2) {
			int y = Y_CABS + Y_DIST_FILAS*altura[coste];
			for (OperacionUnaria.TipoOp tipo : OperacionUnaria.TipoOp.values()) {
				if (tipo.getCoste()==coste) {
					OperacionUnaria op = new OperacionUnaria( x, y, TAMANYO, this, tipo );
					fichas.add( op );
					y += Y_DIST_FILAS;
				}
			}
			for (OperacionBinaria.TipoOp tipo : OperacionBinaria.TipoOp.values()) {
				if (tipo.getCoste()==coste) {
					OperacionBinaria op = new OperacionBinaria( x, y, TAMANYO, this, tipo );
					fichas.add( op );
					y += Y_DIST_FILAS;
				}
			}
			x += X_DIST_COLS;
		}
		// Marcas de pesos
		x = X_REGS + X_DIST_COLS;
		for (int i=NUM_BITS-1; i>=0; i--) {
			Marca marca = new Marca( x, Y_CABS, TAMANYO, this, "" + (int)Math.pow(2,i) );
			x += X_DIST_COLS;
			fichas.add( marca );
		}
		// Registros con sus bits
		int y = Y_REG_AX;
		for (TipoRegistro tipo : TipoRegistro.values()) {
			int peso = 1 << (NUM_BITS-1);
			ArrayList<Bit> l = new ArrayList<>();
			x = X_REGS + X_DIST_COLS;
			for (int i=0; i<NUM_BITS; i++) {
				Bit b = new Bit( x, y, TAMANYO, this, false, peso );
				l.add( b );
				x += X_DIST_COLS;
				peso /= 2;
				fichas.add( b );
			}
			Registro r = new Registro( X_REGS, y, TAMANYO, this, tipo, l );
			if (tipo == TipoRegistro.AX) registroAX = r;
			fichas.add( r );
			y += Y_DIST_FILAS;
		}
		// Objetivos en el mazo
		for (int valor=0; valor<=OBJETIVO_MAX; valor++) {
			mazo.add( new Objetivo( X_OBJETIVOS, Y_OBJETIVOS, TAMANYO, this, valor ) );
		}
		// Eventos en el mazo
		for (Evento.TipoOp tipo : Evento.TipoOp.values()) {
			mazo.add( new Evento( X_OBJETIVOS, Y_OBJETIVOS, TAMANYO, this, tipo ) );  // 1 de cada
		}
		mazo.add( new Evento( X_OBJETIVOS, Y_OBJETIVOS, TAMANYO, this, Evento.TipoOp.OK ) );  // 2 ok
		mazo.add( new Evento( X_OBJETIVOS, Y_OBJETIVOS, TAMANYO, this, Evento.TipoOp.BUG ) );  // 2 bug
		Collections.shuffle( mazo );  // Desordena aleatoriamente el mazo
	}
	
	/** Dibuja la mesa completa en su ventana gráfica
	 */
	public void dibuja() {
		if (!mazo.isEmpty()) { // Dibuja el mazo
			mazo.get(0).dibuja();  // Como están volteadas y todas encima vale con dibujar una
		}
		for (Ficha ficha : objetivos) { // Dibuja los objetivos
			if (ficha!=null) ficha.dibuja();
		}
		for (Ficha ficha : fichas) { // Dibuja todas las demás fichas
			ficha.dibuja();
		}
	}
	
}
