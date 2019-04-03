package tema5.ejemploMoon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import tema5.VentanaGrafica;
import tema5.ejemploMoon.datos.*;
import tema5.ejemploMoon.datos.Evento.TipoOp;

/** Clase principal del juego moon, permite definir objetos de juego asociados a una ventana gráfica
 * @author andoni.eguiluz at deusto.es
 */
public class Moon {
	
	/** Método principal. Crea un objeto de juego y lo lanza
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		Moon juego = new Moon();
		// Moon juego = new Moon( ModoDeJuego.MODO_OBJS_REVELADOS );  // Si se quisiera el otro modo de juego
		juego.jugar();
	}
	
	/** Número máximo de objetivos que caben en la mesa (si hay que sacar más, se pierde la partida) */
	public static final int MAX_OBJETIVOS = 5;  // 
	/** Bonus máximo de energia a gastar (si se supera no hay bonus) */
	public static final int BONUS_MAXIMO = 120; // 
	/** Tipo de letra para las cabeceras (pesos) de los bits */
	public static final Font FONT_GENERAL = new Font( "Arial", Font.PLAIN, 22 );
	/** Tipo de letra para la puntuación y la energía */
	public static final Font FONT_PUNTOS = new Font( "Arial", Font.BOLD, 50 );
	/** Velocidad de movimiento de las fichas en las animaciones */
	private static final double VEL_MOVIMIENTO = 600.0;  // Píxels por segundo
	
	private VentanaGrafica vent;           // Ventana gráfica asociada
	private Mesa mesa;                     // Mesa asociada
	private boolean sigueJuego;            // Booleano de fin de juego
	private EstadoDeJuego estado;          // Estado actual del juego
	private ModoDeJuego modoJuego;         // Modo de juego
	private boolean conAnimaciones = true; // Booleano de si se dibujan animaciones o no
	private int puntuacion = 0;            // Puntuación acumulada
	private int gastoEnergia = 0;          // Gasto acumulado de energía en el juego

	/** Crea un nuevo juego de moon
	 * @param modo	Modo de juego
	 */
	public Moon( ModoDeJuego modo ) {
		modoJuego = modo;
	}
	
	/** Crea un nuevo juego de moon en modo estándar
	 */
	public Moon() {
		modoJuego = ModoDeJuego.MODO_ESTANDAR;
	}
	
	/** Informa si el juego tiene animaciones
	 * @return	true si están activadas las animaciones, false si no
	 */
	public boolean isConAnimaciones() {
		return conAnimaciones;
	}
	
	/** Modifica el comportamiento de las animaciones
	 * @param conAnims	true si se quieren activar, false si se quieren desactivar
	 */
	public void setConAnimaciones( boolean conAnims ) {
		conAnimaciones = conAnims;
	}
	
	/** Cambia el estado del juego
	 * @param estado	estado a modificar
	 */
	public void setEstadoDeJuego( EstadoDeJuego estado ) {
		this.estado = estado;
	}
	
	/** Devuelve el estado actual del juego
	 * @return	Estado actual
	 */
	public EstadoDeJuego getEstadoDeJuego() {
		return estado;
	}
	
	/** Método principal de juego - inicializa el juego y lanza el bucle de juego
	 */
	public void jugar() {
		init();
		play();
	}
	
	// Inicializa el juego: la ventana, la mesa y la variable de control
	private void init() {
		@SuppressWarnings("unused")  // Para evitar el warning (el cálculo por constante genera un warning de código nunca utilizado)
		int anchura = Mesa.NUM_BITS<=4 ? 1150 : 1150 + Mesa.X_DIST_COLS*(Mesa.NUM_BITS-4);
		vent = new VentanaGrafica( anchura, 725, "Moon v0.5" ); 
		vent.setMensajeFont( FONT_GENERAL );
		vent.setDibujadoInmediato( false );
		mesa = new Mesa( vent, this );
		mesa.init();  // Crea los objetos básicos del juego
		sigueJuego = true;
	}

	// Bucle principal de juego
	private void play() {
		// Dibujado inicial
		dibuja();
		vent.espera( 10 );
		// Ciclo de interacción (en este juego no es un bucle de animación porque no hay animación en tiempo real salvo cuando hay drag)
		estado = EstadoDeJuego.ESPERANDO_ACCION;
		sacaCartaMazo();
		while (sigueJuego && !vent.estaCerrada()) {
			checkEstado();
			vent.espera( 20 );
		}
		// Final
		if (!vent.estaCerrada()) {
			dibuja();
			if (partidaGanada()) {
				int puntFinal = puntuacion + 
						((gastoEnergia>BONUS_MAXIMO) ? 0 : (BONUS_MAXIMO - gastoEnergia)*5);
				vent.sacaDialogo( "Fin de partida: Victoria", "Enhorabuena. ¡Has ganado la partida!\n" +
						"Puntuación " + puntuacion + " - Energía consumida " + gastoEnergia/2.0 + "\n" +
						"Puntuación final: " + puntFinal );
				setMensaje( "Fin de partida" );
			} else {
				vent.sacaDialogo( "Fin de partida: Derrota", "Lo sentimos. ¡Has perdido la partida!\n" +
						"Puntuación " + puntuacion + " - Energía consumida " + gastoEnergia/2.0 + " (No hay bonus)\n" +
						"Puntuación final: " + puntuacion );
				setMensaje( "No caben más objetivos. Has perdido" );
			}
			vent.acaba();
		}
	}

	// Chequeo y cambio de estado (se repite contínuamente en la lógica del juego)
	private void checkEstado() {
		if (partidaGanada()) {  // Si se acaban los objetivos la partida se gana -> FIN_JUEGO
			estado = EstadoDeJuego.FIN_JUEGO;
		} else if (mesa.getBateria().getCarga()==0) {  // Si no hay batería se acaba el turno -> FIN_TURNO 
			estado = EstadoDeJuego.FIN_TURNO;
		}
		switch (estado) {
			case ESPERANDO_ACCION: {  // Se espera acción - en método de interacción
				checkInteraccion();
				break;
			}
			case ESPERANDO_OPERANDO: {  // Se espera operando - en método de interacción
				checkInteraccion();
				break;
			}
			case MOVIENDO: {  // Estado de movimiento directo de fichas (con drag & drop)
				mover();
				estado = EstadoDeJuego.ESPERANDO_ACCION;  // Después de mover vuelve a espera de acción
				break;
			}
			case OPERANDO: {  // Estado de ejecución de operación 
				operar();
				estado = EstadoDeJuego.ESPERANDO_ACCION;  // Después de operar vuelve a espera de acción
				break;
			}
			case EN_EVENTO_OK: {  // Se espera ficha a reparar - en método de interacción
				checkInteraccion();
				break;
			}
			case FIN_TURNO: {  // Reinicia turno siguiente
				animacionAparicion( mesa.getBateria(), false );
				mesa.getBateria().reset();
				animacionAparicion( mesa.getBateria(), true );
				estado = EstadoDeJuego.ESPERANDO_ACCION;
				// Revisar los objetivos por si alguno se quita en el último momento antes de perder (solo en ese caso se quita solo - simulando click)
				if (mesa.getObjetivos().size()==MAX_OBJETIVOS) {  
					for (Ficha f : mesa.getObjetivos()) {
						if (f instanceof Objetivo && ((Objetivo)f).seCumpleObjetivo()) {
							f.click( null );
							break;
						}
					}
				}
				// Sacar siguiente objetivo
				sacaCartaMazo();
				break;
			}
			case FIN_JUEGO: {
				sigueJuego = false;
				break;
			}
			default: {
				// Nada que hacer en caso de SUBIENDO_OBJETIVOS
			}
		}
	}
	
		// Atributos privados usados para el movimiento del estado MOVIENDO (método mover())
		// (Normalmente se ponen todos los atributos al principio, pero Java permite poner atributos mezclados entre métodos en cualquier orden)
		private ArrayList<Ficha> fichasAMover;   // Fichas que se quieren mover
		private Point puntoDondeMoverPrimFicha;  // Punto donde mover la primera ficha (las demás irán siguiéndola)
		
	// Rutina de movimiento (lineal) de ficha, usando los atributos de movimiento
	// Se usa en el estado MOVIENDO
	private void mover() {
		ArrayList<Point> ptosFinales = new ArrayList<Point>();
		boolean primerPunto = true;
		int distX = 0;
		int distY = 0;
		for (Ficha ficha : fichasAMover) {
			if (primerPunto) {
				ptosFinales.add( puntoDondeMoverPrimFicha );
				distX = puntoDondeMoverPrimFicha.x - ficha.getX();
				distY = puntoDondeMoverPrimFicha.y - ficha.getY();
				primerPunto = false;
			} else {
				Point ptoFinal = new Point( ficha.getX()+distX, ficha.getY()+distY );
				ptosFinales.add( ptoFinal );
			}
		}
		animacionMovimiento( fichasAMover, ptosFinales );
	}
	
		// Atributos privados usados para la operación (método operar())
		private Operacion operacionPosible;
		private Registro operando1;
		private Registro operando2;
		
	// Rutina de operación (estado OPERANDO)
	private void operar() {
		if (operacionPosible instanceof OperacionUnaria) {
			((OperacionUnaria)operacionPosible).opera( operando1 );
		} else if (operacionPosible instanceof OperacionBinaria) {
			((OperacionBinaria)operacionPosible).opera( operando1, operando2 );
		}
	}

	// Rutina de interacción con teclado / ratón
	// En caso ESPERANDO_ACCION, ESPERANDO_OPERANDO, o EN_EVENTO_OK
	private void checkInteraccion() {
		if (vent.getCodUltimaTeclaTecleada() == KeyEvent.VK_ESCAPE) {  // Gestión de la tecla de Escape
			teclaEscape();
			return;
		}
		if (vent.getMensaje().trim().isEmpty()) {  // Mensaje por defecto si no hay ningún mensaje
			if (estado==EstadoDeJuego.ESPERANDO_ACCION) {
				vent.setMensaje( "Pulsa una operación (o arrastra una operación sobre un registro) - Esc acaba turno" );
			}
		}
		Point iniPuls = vent.getRatonPulsado();
		if (iniPuls!=null) { // Se ha pulsado el botón
			Point ultimaPuls = null;
			Point finClick = null;
			do { // Esperar hasta que se suelte
				ultimaPuls = finClick;
				finClick = vent.getRatonPulsado();
				vent.espera( 20 );
			} while (finClick!=null);
			if (iniPuls.equals(ultimaPuls) || ultimaPuls==null) {  // Click (inicio = final de pulsación)
				if (estado==EstadoDeJuego.ESPERANDO_ACCION) {  // Click con acción
					boolean redibujar = click( iniPuls );
					if (redibujar) { dibuja(); vent.espera( 10 ); }
				} else if (estado==EstadoDeJuego.EN_EVENTO_OK) {  // Click con evento ok
					click( iniPuls );
					estado = EstadoDeJuego.ESPERANDO_ACCION;
					quitarObjetivo( Evento.TipoOp.OK );
					setMensaje( "Operación OK consumida. " );
				} else { // Click con operación  (ESPERANDO_OPERACION)
					for (Ficha ficha : mesa.getFichas()) {
						if (ficha.puntoEnFicha( iniPuls )) {  // Esta es la ficha pulsada
							if (ficha instanceof Registro) {  // Solo si es registro vale como operando
								if (((Registro)ficha).isEstropeada()) {
									setMensaje( "Registro estropeado. No es usable hasta que aparezca un OK" );
								} else {
									if (operando1==null) {  // Es el operando 1
										operando1 = (Registro) ficha;
										if (operacionPosible instanceof OperacionBinaria) {
											setMensaje( "Selecciona registro destino para operación " + operacionPosible.getNombreTipo() );
										}
									} else {  // Es el operando 2
										operando2 = (Registro) ficha;
									}
									if (operacionPosible instanceof OperacionUnaria || 
										(operacionPosible instanceof OperacionBinaria && operando2!=null)) { // Ya tiene todos los operandos
										estado = EstadoDeJuego.OPERANDO;
										String registros = operando1.toString();
										if (operando2!=null) registros += " y " + operando2.toString();
										setMensaje( "Realizando operación " + operacionPosible.getNombreTipo() + " con " + registros );
									}
								}
							}
							break;
						}
					}
				}
			} else {  // Drag (inicio != final pulsación
				if (estado==EstadoDeJuego.ESPERANDO_ACCION) {  // Si se espera operando el drag no se usa
					boolean redibujar = drag( iniPuls, ultimaPuls );
					if (redibujar) { dibuja(); vent.espera( 10 ); }
				}
			}
		}
	}

	// Pulsación de tecla Esc (estados ESPERANDO_ACCION, ESPERANDO_OPERANDO o EN_EVENTO_OK)
	private void teclaEscape() {
		if (estado==EstadoDeJuego.ESPERANDO_ACCION) {  // Escape acaba el turno
			estado = EstadoDeJuego.FIN_TURNO;
			setMensaje( "" );
		} else if (estado==EstadoDeJuego.ESPERANDO_OPERANDO) {  // Escape acaba la operación sin realizarla
			estado = EstadoDeJuego.ESPERANDO_ACCION;
			setMensaje( "Operación " + operacionPosible.getNombreTipo() + " cancelada." );
		} else if (estado==EstadoDeJuego.EN_EVENTO_OK) {  // Escape desperdicia el ok
			estado = EstadoDeJuego.ESPERANDO_ACCION;
			quitarObjetivo( Evento.TipoOp.OK );
			setMensaje( "Operación OK cancelada." );
		}
	}
	
	// Click en estado ESPERANDO_ACCION o en EN_EVENTO_OK
	// Devuelve true si hay que redibujar
	private boolean click( Point puntoClick ) {
		for (Ficha ficha : mesa.getFichas()) {
			if (ficha.puntoEnFicha( puntoClick )) {
				return ficha.click( puntoClick );
			}
		}
		for (Ficha ficha : mesa.getObjetivos()) {
			if (ficha!=null && ficha.puntoEnFicha( puntoClick )) {
				return ficha.click( puntoClick );
			}
		}
		return false;
	}
	
	// Drag - comprueba si hay fichas en las coordenadas y hace el drag sobre ellas
	// true si hay que redibujar
	private boolean drag( Point iniDrag, Point finDrag ) {
		boolean redibujar = false;
		// El drag solo afecta a uno... y si hay varias fichas, se considera la que está más "arriba": recorrido al revés
		for (int i=mesa.getFichas().size()-1; i>=0; i--) {  // Primero las que se dibujan más arriba
			Ficha ficha = mesa.getFichas().get( i );
			if (ficha.puntoEnFicha( iniDrag ) && ficha instanceof Movible) { // Posibilidad 1: es un movimiento de la ficha a otro sitio de la mesa
				Movible m = (Movible) ficha;
				redibujar = m.drag( iniDrag, finDrag );
				break;  // Se ignoran el resto de posibles drags (si hay fichas solapadas)
			} else if (ficha.puntoEnFicha( iniDrag ) && ficha instanceof Draggable) {  // Posibilidad 2: es una operación con lógica draggable (se arrastra sobre otra)
				// Buscamos la ficha de destino
				for (int j=mesa.getFichas().size()-1; j>=0; j--) {
					Ficha ficha2 = mesa.getFichas().get( j );
					if (ficha2.puntoEnFicha( finDrag )) {
						redibujar = ((Draggable)ficha).dragTo( ficha2 );
						break;
					}
				}
				break;  // Se ignoran el resto de posibles drags (si hay fichas solapadas)
			}
		}
		return redibujar;
	}

	// Saca una nueva carta del mazo
	private void sacaCartaMazo() {
		if (!mesa.getMazo().isEmpty()) {
			Ficha fDelante = mesa.getMazo().remove(0);
			subirObjetivos( fDelante );
			if (fDelante instanceof Evento &&   // Si es evento con objetivo revelado, o si solo hay una carta, operarla
				(modoJuego==ModoDeJuego.MODO_OBJS_REVELADOS || noHayMasObjetivos())) {
				((Evento)fDelante).procesa();
			}
		} else {
			subirObjetivos( null );
		}
	}
	
		// Indica si en la lista lateral de objetivos no hay más objetivos (solo en todo caso la carta nueva)
		private boolean noHayMasObjetivos() {
			if (mesa.getObjetivos().size()==1) return true;
			for (int i=1;i<mesa.getObjetivos().size();i++) {
				if (mesa.getObjetivos().get(i) instanceof Objetivo)
					return false;
			}
			return true;
		}
	
	// Sube la columna de objetivos una posición
	// nuevaFicha es la nueva ficha que entra por abajo (null si ya no quedan)
	private void subirObjetivos( Ficha nuevaFicha ) {
		estado = EstadoDeJuego.SUBIENDO_OBJETIVOS;
		if (mesa.getObjetivos().size()==MAX_OBJETIVOS) { // Derrota - ya no caben nuevos objetivos
			estado = EstadoDeJuego.FIN_JUEGO;
		} else {
			mesa.getObjetivos().add( 0, nuevaFicha );
			Volteable aProcesar = null;
			if (nuevaFicha!=null && nuevaFicha instanceof Volteable) {
				if (modoJuego==ModoDeJuego.MODO_OBJS_REVELADOS || noHayMasObjetivos()) {  // Si es la única carta o estamos en modo objetivos revelados, voltearla
					aProcesar = ((Volteable)nuevaFicha);
					aProcesar.setVolteado( false );
				}
			}
			moverObjetivos();
			if (aProcesar!=null) procesarPrimerObjetivo( aProcesar );
			estado = EstadoDeJuego.ESPERANDO_ACCION;
		}
	}

		// Mueve los objetivos en pantalla
		private void moverObjetivos() {
			ArrayList<Ficha> lF = new ArrayList<Ficha>();
			ArrayList<Point> lP = new ArrayList<Point>();
			for (Ficha f : mesa.getObjetivos()) {
				if (f!=null) {
					lF.add( f );
					lP.add( new Point( f.getX(), f.getY()-Mesa.Y_DIST_FILAS ) );
				}
			}
			animacionMovimiento( lF, lP );
		}

	// Informa si se ha ganado la partida (el mazo está vacío y ya no quedan objetivos en la columna lateral)
	private boolean partidaGanada() {
		if (!mesa.getMazo().isEmpty()) return false;
		for (Ficha ficha : mesa.getObjetivos()) {
			if (ficha!=null && ficha instanceof Objetivo) {
				return false;
			}
		}
		return true;
	}

	// =============================================
	// Métodos PÚBLICOS  (usables desde las lógicas de las fichas)
	// =============================================
	
	/** Incrementa la puntuación del juego
	 * @param puntos	Puntos de incremento
	 */
	public void incPuntuacion( int puntos ) {
		puntuacion += puntos;
		dibuja();
		vent.espera( 10 );
	}
	
	/** Incrementa la energía gastada en el juego
	 * @param energia	Energía de incremento
	 */
	public void incGastoEnergia( int energia ) {
		gastoEnergia += energia;
	}
	
	/** Mueve una ficha en la mesa, pasando el juego a estado MOVIENDO
	 * @param ficha	Ficha a mover
	 * @param puntoFinal	Punto destino del movimiento
	 */
	public void mover( Movible ficha, Point puntoFinal ) {
		setEstadoDeJuego( EstadoDeJuego.MOVIENDO );
		fichasAMover = new ArrayList<Ficha>();
		fichasAMover.add( (Ficha)ficha );
		if (ficha instanceof Registro) {  // El registro contiene bits que se mueven con él
			for (Bit bit : ((Registro)ficha).getBits()) {
				fichasAMover.add( bit );
			}
		}
		puntoDondeMoverPrimFicha = puntoFinal;
	}
	
	/** Provoca el lanzamiento de una operación y un estado de ESPERANDO_OPERANDO en el juego
	 * @param op	Operación a operar (si está estropeada no se cambia el estado)
	 */
	public void operar( Operacion op ) {
		if (op.isEstropeada()) {   // Si está estropeada no se puede usar
			operacionPosible = null;
			setMensaje( "Operación estropeada. No es usable hasta que aparezca un OK" );
		} else {
			setEstadoDeJuego( EstadoDeJuego.ESPERANDO_OPERANDO );
			operacionPosible = op;
		}
		operando1 = null;
		operando2 = null;
	}
	
	/** Realiza una animación de movimiento en la mesa
	 * @param fichaAMover	Ficha que se quiere mover
	 * @param puntoDondeMover	Punto al que se quiere mover
	 */
	public void animacionMovimiento( Ficha fichaAMover, Point puntoDondeMover ) {
		animacionMovimiento( new ArrayList<Ficha>( Arrays.asList( new Ficha[] { fichaAMover } ) ),
				new ArrayList<Point>( Arrays.asList( new Point[] { puntoDondeMover } ) ) );
	}
	
	/** Realiza una animación de movimiento en la mesa
	 * @param fichasAMover	Fichas que se mueven
	 * @param puntosDondeMover	Puntos a los que se van a mover cada una de las fichas: debe tener la misma longitud que fichasAMover
	 */
	public void animacionMovimiento( ArrayList<Ficha> fichasAMover, ArrayList<Point> puntosDondeMover ) {
		if (fichasAMover.size()==0) return;
		double[] moviendoX = new double[fichasAMover.size()];
		double[] moviendoY = new double[fichasAMover.size()];
		double[] moviendoXFin = new double[fichasAMover.size()];
		double[] moviendoYFin = new double[fichasAMover.size()];
		double[] incXPorPaso = new double[fichasAMover.size()];
		double[] incYPorPaso = new double[fichasAMover.size()];
		for (int i=0; i<fichasAMover.size(); i++) {
			moviendoX[i] = fichasAMover.get(i).getX();
			moviendoY[i] = fichasAMover.get(i).getY();
			moviendoXFin[i] = puntosDondeMover.get(i).getX();
			moviendoYFin[i] = puntosDondeMover.get(i).getY();
		}
		double distMovto = Math.sqrt((moviendoXFin[0]-moviendoX[0])*(moviendoXFin[0]-moviendoX[0]) + (moviendoYFin[0]-moviendoY[0])*(moviendoYFin[0]-moviendoY[0]));
		double tiempoMovto = distMovto / VEL_MOVIMIENTO;  // Segundos del movimiento (basado en la primera ficha a mover)
		int numPasosMovto = (int) (tiempoMovto*50.0);  // A 50 fps
		for (int i=0; i<fichasAMover.size(); i++) {
			incXPorPaso[i] = (moviendoXFin[i]-moviendoX[i]) / numPasosMovto;
			incYPorPaso[i] = (moviendoYFin[i]-moviendoY[i]) / numPasosMovto;
		}
		for (int n=0; n<numPasosMovto; n++) {
			for (int i=0; i<fichasAMover.size(); i++) {
				moviendoX[i] += incXPorPaso[i];
				moviendoY[i] += incYPorPaso[i];
				fichasAMover.get(i).setX( (int)Math.round(moviendoX[i]) );
				fichasAMover.get(i).setY( (int)Math.round(moviendoY[i]) );
			}
			if (conAnimaciones) {
				dibuja();
				vent.espera( 20 );  // 20 msegs cada paso
			}
		}
		for (int i=0; i<fichasAMover.size(); i++) { // Aseguramos que las fichas acaben en su último punto
			fichasAMover.get(i).setX( (int)Math.round(moviendoXFin[i]) );
			fichasAMover.get(i).setY( (int)Math.round(moviendoYFin[i]) );
		}
		dibuja();
		vent.espera( 10 );  // 10 msegs tras el dibujado final
	}
	
	/** Realiza una animación de transparencia en pantalla que dura 25 centésimas
	 * @param fichas	Fichas que animar
	 * @param hazOpaca	true si se quieren visualizar, false si se quieren desaparecer
	 */
	public void animacionAparicion( ArrayList<Transparente> fichas, boolean hazOpaca ) {
		if (fichas.size()==0) return;
		float valIni = 0.0f;
		float valFin = 1.0f;
		float inc = 0.04f; // 25 incrementos (* 10 msegs = 0,25 segundos de transición)
		while (valIni<=valFin) {
			for (int i=0; i<fichas.size(); i++) {
				fichas.get(i).setOpacidad( hazOpaca ? valIni : (1.0f-valIni) );  // En una dirección o en la opuesta
			}
			if (conAnimaciones) {
				dibuja();
				vent.espera( 10 );  // 10 msegs cada paso
			}
			valIni += inc;
		}
		if (!conAnimaciones) {
			dibuja();
			vent.espera( 10 );  // 10 msegs tras el dibujado final
		}
	}
	
	/** Realiza una animación de transparencia en pantalla que dura 25 centésimas
	 * @param ficha	Ficha que animar
	 * @param hazOpaca	true si se quiere visualizar, false si se quiere desaparecer
	 */
	public void animacionAparicion( Transparente ficha, boolean hazOpaca ) {
		ArrayList<Transparente> fichas = new ArrayList<Transparente>();
		fichas.add( ficha );
		animacionAparicion( fichas, hazOpaca );
	}
	
	/** Pone un mensaje en la ventana del juego, en la línea inferior de mensajes
	 * @param texto	Mensaje a visualizar
	 */
	public void setMensaje( String texto ) {
		vent.setMensaje( texto );
	}
	
	/** Activa el estado de espera del segundo operando de una operación binaria 
	 * Si la operación o el operando están estropeados, no se realiza y se muestra un mensaje indicándolo
	 * @param op	Operación que se quiere operar
	 * @param primerOp	Primer operando ya definido de esa operación
	 */
	public void esperandoSegundoOperando( OperacionBinaria op, Registro primerOp ) {
		if (op.isEstropeada()) {  // Si está estropeada no se puede usar
			operacionPosible = null;
			setMensaje( "Operación estropeada. No es usable hasta que aparezca un OK" );
		} else {
			operacionPosible = op;
			if (primerOp.isEstropeada()) {
				setMensaje( "Registro estropeado. No es usable hasta que aparezca un OK" ); 
			} else {
				operando1 = primerOp;
				setMensaje( "Selecciona registro destino para operación " + operacionPosible.getNombreTipo() );
				estado = EstadoDeJuego.ESPERANDO_OPERANDO;
			}
		}
	}
	
	/** Quita un objetivo de la lista de objetivos, revelando la siguiente ficha objetivo (si la hay)
	 * @param f	Ficha objetivo a eliminar
	 */
	public void quitarObjetivo( Ficha f ) {
		int posi = mesa.getObjetivos().indexOf( f );
		if (posi!=-1) {
			// Animar desaparición
			if (f instanceof Transparente) {
				animacionAparicion( (Transparente)f, false );
			}
			ArrayList<Ficha> lF = new ArrayList<Ficha>();
			ArrayList<Point> lP = new ArrayList<Point>();
			for (int i=posi+1; i<mesa.getObjetivos().size(); i++) {
				Ficha fo = mesa.getObjetivos().get(i);
				if (fo!=null) {
					lF.add( fo );
					lP.add( new Point( fo.getX(), fo.getY() + Mesa.Y_DIST_FILAS ) );
				}
			}
			animacionMovimiento( lF, lP );
			// Quitar la carta cumplida
			mesa.getObjetivos().remove( f );
			if (modoJuego!=ModoDeJuego.MODO_OBJS_REVELADOS) {  // Si no es modo objetivos revelados, revelar siguiente objetivo
				revelarPrimerObjetivo();
			}
		}
	}
	
	// Revela el siguiente objetivo (si lo hay)
	private void revelarPrimerObjetivo() {
		if (mesa.getObjetivos().size()>0) {
			int posi = mesa.getObjetivos().size()-1;
			Ficha primerObjetivo = mesa.getObjetivos().get( posi );
			while (primerObjetivo instanceof Evento && ((Evento)primerObjetivo).getTipo()==TipoOp.BUG 
				   && !((Evento)primerObjetivo).isVolteado()) {
				posi--;
				if (posi<0) return; // No hay más cartas que revelar
				primerObjetivo = mesa.getObjetivos().get( posi );
			}
			if (primerObjetivo instanceof Volteable) {
				if (primerObjetivo instanceof Transparente && conAnimaciones) {
					animacionAparicion( (Transparente)primerObjetivo, false );
					((Volteable)primerObjetivo).setVolteado( false );
					animacionAparicion( (Transparente)primerObjetivo, true );
				} else {
					((Volteable)primerObjetivo).setVolteado( false );
					dibuja();
					vent.espera( 10 );
				}
				procesarPrimerObjetivo( (Volteable) primerObjetivo );
			}
		}
	}

	// Procesa el objetivo indicado (obj) si es un evento. Si es un bug, revela el siguiente
	private void procesarPrimerObjetivo( Volteable obj ) {
		if (obj instanceof Evento) {
			Evento ev = ((Evento)obj);
			ev.procesa();
			if (ev.getTipo()==Evento.TipoOp.BUG) {  // Si es un bug, como no se quita, hay que revelar explícitamente el siguiente
				revelarPrimerObjetivo();
			}
		}
	}
	
	/** Quita el primer objetivo de la lista de objetivos de acuerdo a su tipo de evento
	 * @param tipoOpAQuitarFicha tipo de evento a quitar (se quita el primero que tenga este tipo: si no hay ninguno, no se hace nada)
	 */
	public void quitarObjetivo( Evento.TipoOp tipoOpAQuitar ) {
		Ficha aQuitar = null;
		for (Ficha f : mesa.getObjetivos()) {
			if (f instanceof Evento) {
				if (!((Evento)f).isVolteado()) {
					aQuitar = f;
					break;
				}
			}
		}
		if (aQuitar!=null) quitarObjetivo( aQuitar );
	}

	/** Dibuja en la ventana de juego la mesa completa en el estado actual
	 */
	public void dibuja() {
		vent.borra();
		vent.dibujaImagen( "/img/moon/mat-vacia.png", vent.getAnchura()/2, vent.getAltura()/2, vent.getAnchura(), vent.getAltura(), 1.0, 0.0, 1.0f );
		mesa.dibuja();
		vent.dibujaTexto( Mesa.X_OPS, Mesa.Y_OBJETIVOS+10, ""+puntuacion, FONT_PUNTOS, Color.white );
		vent.dibujaTexto( Mesa.X_OPS + 200, Mesa.Y_OBJETIVOS+10, ""+gastoEnergia/2.0, FONT_PUNTOS, Color.yellow );
		vent.dibujaImagen( "/img/moon/bat-half.png", Mesa.X_OPS + 160, Mesa.Y_OBJETIVOS-10, 60, 60, 1.0, 0.0, 1.0f );
		vent.repaint();
	}
	
	/** Realiza y visualiza una operación de reset en el registro indicado
	 * @param nomReg	Nombre del registro que resetear ("AX", "BX", "CX", "DX")
	 */
	public void resetReg( String nomReg ) {
		for (Ficha f : mesa.getFichas()) {
			if (f instanceof Registro) {
				if (((Registro) f).getTipo().toString().equals( nomReg )) {
					if (conAnimaciones) {
						ArrayList<Transparente> lB = new ArrayList<>();
						for (Bit bit : ((Registro)f).getBits()) lB.add( bit );
						animacionAparicion( lB, false );
						((Registro)f).setValor( 0 );  // reset
						animacionAparicion( lB, true );
					} else {
						((Registro)f).setValor( 0 );  // reset
						dibuja();
						vent.espera( 10 );
					}
					setMensaje( "Registro reseteado: " + f );
					break;
				}
			}
		}
	}
	
	/** Estropea una ficha
	 * @param nom	Nombre de la ficha a estropear ("BX", "CX", "ROL", "XOR"...)
	 */
	public void ponErrorEnFicha( String nom ) {
		for (Ficha f : mesa.getFichas()) {
			if (f instanceof Estropeable) {
				Estropeable e = (Estropeable)f;
				if (e.getNombre().equals( nom )) {
					if (conAnimaciones && e instanceof Transparente) {
						Transparente t = (Transparente) e;
						animacionAparicion( t, false );
						e.setEstropeada( true );
						animacionAparicion( t, true );
					} else {
						e.setEstropeada( true );
						dibuja();
						vent.espera( 10 );
					}
					setMensaje( "Ficha estropeada: " + e );
					break;
				}
			}
		}
	}
	
}
