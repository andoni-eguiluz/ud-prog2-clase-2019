package tema3.conHerencia;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import com.sun.glass.events.KeyEvent;

import tema3.VentanaGrafica;
import tema3.sinHerencia.Fisica.Polar;

/** Juego de pong con HERENCIA
 * con físicas básicas de bola y pala (sin goles, sin puntuación)
 * Choques mejorables - no realizado recálculo de choque inicial de bola con lo que 
 * quedan posibilidades de bola "incrustada" con choques inadecuados
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class PongV4 {

	private static final long MSGS_POR_FRAME = 20; // 20 msgs por frame = 50 frames por segundo aprox
	private static double VEL_JUEGO = 1.0;         // 1.0 = tiempo real. Cuando mayor, más rápido pasa el tiempo y viceversa 
	private static final double RADIO_BOLA = 15;   // Radio de la pelota
	private static final double VEL_MIN = 300;     // Velocidad inicial mínima (300 píxels por segundo)
	private static final double VEL_RANGO = 200;   // Rango de velocidad mínima (de VEL_MIN a VEL_MIN + 200 píxels por segundo)
	private static final double VEL_PALA = 500;    // Velocidad de desplazamiento de las palas
	
	private static final boolean DEBUG_CHOQUES = false;  // Activar si se quiere "depurar" en pantalla los choques (con parada del juego en cada choque-rebote y líneas de referencia de lo que ocurre en cada choque)
	
	public static void main(String[] args) {
		PongV4 juego = new PongV4();
		juego.jugar();
	}
	
	// =================================
	// ATRIBUTOS Y MÉTODOS DE INSTANCIA (no static)
	// =================================
	
	private VentanaGrafica vent;       // Ventana del juego
	private ArrayList<Circulo> bolas;  // Bolas del juego (tiene una pero está preparado para tener varias)
	private Rectangulo pala1;          // Pala izquierda del juego (jugador 1)
	private Rectangulo pala2;          // Pala derecha del juego (jugador 2)
	
	public void jugar() {
		Random random = new Random();
		vent = new VentanaGrafica( 1000, 800, "Juego de pong" );
		// La bola
		Circulo bola = new Circulo( RADIO_BOLA, 500, 400, Color.magenta );
		bola.setVX( random.nextDouble() * VEL_RANGO );
		bola.setVX( bola.getVX()<0 ? bola.getVX()-VEL_RANGO : bola.getVX()+VEL_MIN ); // Velocidad x (aleatoria entre -500 y +500 px/seg, al menos 300)
		bola.setVY( random.nextDouble() * VEL_RANGO );
		bola.setVY( bola.getVY()<0 ? bola.getVY()-VEL_RANGO : bola.getVY()+VEL_MIN ); // Velocidad y (aleatoria entre -500 y +500 px/seg, al menos 300)
		bolas = new ArrayList<Circulo>();
		bolas.add( bola );
		// Las palas
		pala1 = new Rectangulo( 20, 100, 60, vent.getAltura()/2, Color.green );
		pala2 = new Rectangulo( 20, 100, vent.getAnchura()-60, vent.getAltura()/2, Color.blue );
		mover();
	}
	
	// Bucle de movimiento
	private void mover() {
		vent.setDibujadoInmediato( false );
		while (!vent.estaCerrada()) {
			// Manejo de teclado
			gestionTeclado();
			// Movimiento de la bola
			Circulo bola = bolas.get(0);
			if (DEBUG_CHOQUES) { // En modo depuración, dibuja palas y vector de velocidad para pantalla de choque
				vent.borra(); 
				vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.orange, 20 );
				pala1.dibuja( vent );
				pala2.dibuja( vent );
			}
			bola.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
			// Movimiento de las palas
			pala1.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
			pala2.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
			// Control de salida de pantalla
			if (bola.seSaleEnHorizontal( vent )) {
				bola.setVX( -bola.getVX() );
				if (DEBUG_CHOQUES) {vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.green, 20 ); }  // En modo depuración, dibuja el cambio de velocidad tras choque
			}
			if (bola.seSaleEnVertical( vent )) {
				bola.setVY( -bola.getVY() );
				if (DEBUG_CHOQUES) {vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.gray, 20 ); }  // En modo depuración, dibuja el cambio de velocidad tras choque
			}
			// Choque bola y palas
			boolean hayChoque = false;
			if (choqueLateralPala(bola, pala1) || choqueLateralPala(bola, pala2)) {
				hayChoque = true;
				bola.setVX( -bola.getVX() );
				if (DEBUG_CHOQUES) {vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.green, 20 ); }  // En modo depuración, dibuja el cambio de velocidad tras choque
			} else if (choqueVerticalPala(bola, pala1) || choqueVerticalPala(bola, pala2)) {
				hayChoque = true;
				bola.setVY( -bola.getVY() );
				if (DEBUG_CHOQUES) {vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.gray, 20 ); }  // En modo depuración, dibuja el cambio de velocidad tras choque
			} else if (choqueExtremoPala(bola, pala1) || choqueExtremoPala(bola, pala2)) {  
				hayChoque = true;
				// bola.setVX( -bola.getVX() );  // V2 (comportamiento mejorado)
				if (choqueExtremoPala(bola, pala1)) calculaReboteEsquina( bola, pala1 );
				else calculaReboteEsquina( bola, pala2 );
				if (DEBUG_CHOQUES) {vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.cyan, 20 ); }  // En modo depuración, dibuja el cambio de velocidad tras choque
			}
			if (hayChoque) { 
				reajustaBola( bola ); 
			}
			// Dibujado
			vent.borra();
			dibujaBordes();
			// Programación genérica con herencia:
			Figura[] fs = new Figura[] { bola, pala1, pala2 };
			// Obsérvese que el array de Figuras tiene en algunas posiciones Circulos y en otras Rectángulos, pero nunca figuras (la clase es abstracta)
			// (Datos polimórficos - único caso en Java en que un tipo de variable no se corresponde con el dato que contiene - solo se permite con herencia)
			for (Figura oj : fs) {  // O el mismo bucle sin for-each:
			// for (int i=0; i<fs.length; i++) {
				// Figura oj = fs[i];
				oj.dibuja( vent );  // Tanto la bola como pala1/pala2 son Figuras luego se pueden dibujar
				// Obsérvese que a veces oj es Circulo, a veces es Rectángulo, y se llama a distinto código en cada caso
				// (Método polimórfico)
			}
			// En vez de:
			// bola.dibuja( vent );
			// pala1.dibuja( vent );
			// pala2.dibuja( vent );
			if (DEBUG_CHOQUES) { vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.magenta, 20 ); }  // En modo depuración, dibuja vector de velocidad de la bola
			vent.repaint();
			// Ciclo de espera en cada bucle
			vent.espera( MSGS_POR_FRAME );
		}
	}
	
	private void gestionTeclado() {
		if (vent.isTeclaPulsada( KeyEvent.VK_PLUS )) {
			VEL_JUEGO *= 1.05;
			vent.setMensaje( "Nueva velocidad de juego: " + VEL_JUEGO );
		}
		if (vent.isTeclaPulsada( KeyEvent.VK_MINUS )) {
			VEL_JUEGO /= 1.05;
			vent.setMensaje( "Nueva velocidad de juego: " + VEL_JUEGO );
		}
		// Movimiento jugador 1
		if (vent.isTeclaPulsada( KeyEvent.VK_UP )) {
			pala1.setVY( -VEL_PALA );
		} else if (vent.isTeclaPulsada( KeyEvent.VK_DOWN )) {
			pala1.setVY( VEL_PALA );
		} else {
			pala1.setVY( 0 );
		}
		// Movimiento jugador 2
		if (vent.isTeclaPulsada( KeyEvent.VK_W )) {
			pala2.setVY( -VEL_PALA );
		} else if (vent.isTeclaPulsada( KeyEvent.VK_S )) {
			pala2.setVY( VEL_PALA );
		} else {
			pala2.setVY( 0 );
		}
	}
	
	private void dibujaBordes() {
		vent.dibujaRect( 0, 0, vent.getAnchura(), vent.getAltura(), 0.5f, Color.black );
		vent.dibujaLinea( vent.getAnchura()/2, 0, vent.getAnchura()/2, vent.getAltura(), 0.5f, Color.black );
	}
	
	// Choque directo con pala
	private boolean choqueLateralPala( Circulo bola, Rectangulo pala ) {
		if (bola.getY() >= pala.getY()-pala.getAltura()/2 &&
			bola.getY() <= pala.getY()+pala.getAltura()/2) {  // La y de la bola está dentro de la pala
			if (bola.getX()-bola.getRadio()<=pala.getX()+pala.getAnchura()/2 &&
				bola.getX()+bola.getRadio()>=pala.getX()-pala.getAnchura()/2 ) {
				return true;
			}
		}
		return false;
	}
	private boolean choqueVerticalPala( Circulo bola, Rectangulo pala ) {
		if (bola.getX() >= pala.getX()-pala.getAnchura()/2 &&
			bola.getX() <= pala.getX()+pala.getAnchura()/2) {  // La x de la bola está dentro de la pala
			if (bola.getY()-bola.getRadio()<=pala.getY()+pala.getAltura()/2 &&
				bola.getY()+bola.getRadio()>=pala.getY()-pala.getAltura()/2 ) {
				return true;
			}
		}
		return false;
	}
	private boolean choqueSuperiorPala( Circulo bola, Rectangulo pala ) {
		return choqueVerticalPala(bola, pala) && bola.getY() < pala.getY();
	}
	private boolean choqueInferiorPala( Circulo bola, Rectangulo pala ) {
		return choqueVerticalPala(bola, pala) && bola.getY() > pala.getY();
	}
	
	// Coche con esquina
	private boolean choqueExtremoPala(Circulo bola, Rectangulo pala) {
		double dist1 = Fisica.distancia( bola.getX(), bola.getY(), pala.getX()+pala.getAnchura()/2, pala.getY()+pala.getAltura()/2 );
		double dist2 = Fisica.distancia( bola.getX(), bola.getY(), pala.getX()+pala.getAnchura()/2, pala.getY()-pala.getAltura()/2 );
		double dist3 = Fisica.distancia( bola.getX(), bola.getY(), pala.getX()-pala.getAnchura()/2, pala.getY()+pala.getAltura()/2 );
		double dist4 = Fisica.distancia( bola.getX(), bola.getY(), pala.getX()-pala.getAnchura()/2, pala.getY()-pala.getAltura()/2 );
		if (dist1<=bola.getRadio() || dist2<=bola.getRadio() || dist3<=bola.getRadio() || dist4<=bola.getRadio()) {
			return true;
		} else {
			return false;
		}
	}

	private void calculaReboteEsquina( Circulo bola, Rectangulo pala ) {
		for (double anch : new double[] {-1.0, 1.0}) {
			for (double alt : new double[] {-1.0, 1.0}) {  // Bucles para las 4 esquinas con las que puede chocar la bola
				Point2D esquina = new Point2D.Double( pala.getX()+anch*pala.getAnchura()/2, pala.getY()+alt*pala.getAltura()/2 );
				double dist = Fisica.distancia( bola.getX(), bola.getY(), esquina.getX(), esquina.getY() );
				if (dist<=bola.getRadio()) {  // Este es el vértice del choque
					Point2D vectorImpacto = new Point2D.Double( esquina.getX()-bola.getX(), esquina.getY()-bola.getY() );
					Polar vectorImpactoP = new Polar( vectorImpacto );
					double anguloDesv = vectorImpactoP.getArgumento();
					Point2D vectorVel = new Point2D.Double( bola.getVX(), bola.getVY() );
					Polar vectorVelP = new Polar( vectorVel );
					vectorVelP.rotar( -anguloDesv );
					Point2D velRotada = vectorVelP.toPoint();
					velRotada.setLocation( -velRotada.getX(), velRotada.getY() );  // Invierte la x (rebote)
					Polar velRotadaP = new Polar( velRotada );
					velRotadaP.rotar( anguloDesv ); // Volver a poner el vector en términos de x,y original
					Point2D velTrasRebote = velRotadaP.toPoint();
					bola.setVX( velTrasRebote.getX() );
					bola.setVY( velTrasRebote.getY() );
					// Flechas de apoyo si se quieren ver los vectores en pantalla
					// vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+vectorImpacto.getX()*2, bola.getY()+vectorImpacto.getY()*2, 1.5f, Color.magenta, 10 ); // Vector de ángulo de contacto
					// vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/2, bola.getY()+bola.getVY()/2, 1.5f, Color.green, 10 ); // Vector de velocidad original
					// vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+velRotada.getX()/2, bola.getY()+velRotada.getY()/2, 1.5f, Color.orange, 10 ); // Vector de velocidad girado
					// vent.repaint();
					// while (vent.getRatonPulsado()==null) vent.espera(100);
					return;
				}
			}
		}
	}

	// A veces la bola se queda "dentro" de la pala o de las paredes: sacarla si es así
	private void reajustaBola(Circulo bola) {
		if (DEBUG_CHOQUES) { // En modo depuración, dibuja la posición original de la bola antes de reajustes
			vent.dibujaCirculo( bola.getX(), bola.getY(), 4, 1.5f, Color.orange ); 
			vent.dibujaCirculo( bola.getX(), bola.getY(), 25, 1.5f, Color.orange ); 
		}
		if (bola.seSaleEnHorizontal( vent )) {
			bola.setVX( Math.abs(bola.getVX()) * bola.salidaHorizontal( vent ) );  // Hacia adentro siempre
		}
		if (choqueVerticalPala(bola, pala1)) {  // Si hay choque "plano" vertical
			// 1.- Reajustar la velocidad para que la velocidad vertical de la bola sea mayor que la de la pala
			if ((choqueSuperiorPala(bola, pala1) && pala1.getVY()<0)       // Si va hacia arriba sumar velocidad arriba de la pala
			    || (choqueInferiorPala(bola, pala1) && pala1.getVY()>0)) { // Si va hacia abajo sumar velocidad abajo de la pala
				bola.setVY( bola.getVY() + pala1.getVY() );  // La velocidad de la pala se suma a la de la bola
			}
			// 2.- Ajustar la posición para que no "coma" a la pala
			if (bola.getY() < pala1.getY()) bola.setY(pala1.getY()-pala1.getAltura()/2-bola.getRadio()-0.01);
			else bola.setY(pala1.getY()+pala1.getAltura()/2+bola.getRadio()+0.01);
		}
		if (choqueVerticalPala(bola, pala2)) {  // Si hay choque "plano" vertical
			// 1.- Reajustar la velocidad para que la velocidad vertical de la bola sea mayor que la de la pala
			if ((choqueSuperiorPala(bola, pala2) && pala2.getVY()<0)       // Si va hacia arriba sumar velocidad arriba de la pala
				|| (choqueInferiorPala(bola, pala2) && pala2.getVY()>0)) { // Si va hacia abajo sumar velocidad abajo de la pala
				bola.setVY( bola.getVY() + pala2.getVY() );
			}
			// 2.- Ajustar la posición para que no "coma" a la pala
			if (bola.getY() < pala2.getY()) bola.setY(pala2.getY()-pala2.getAltura()/2-bola.getRadio()-0.01);
			else bola.setY(pala2.getY()+pala2.getAltura()/2+bola.getRadio()+0.01);
		}
		if (bola.seSaleEnVertical( vent )) {
			bola.setVY( Math.abs(bola.getVY()) * bola.salidaVertical( vent ) );  // Hacia adentro siempre
		}
		if (choqueLateralPala(bola, pala1)) {  // Si hay choque "plano" horizontal
			if (bola.getVX() < 0) bola.setX(pala1.getX()-pala1.getAnchura()/2-bola.getRadio()-0.01);
			else bola.setX(pala1.getX()+pala1.getAnchura()/2+bola.getRadio()+0.01);
		}
		if (choqueLateralPala(bola, pala2)) {  // Si hay choque "plano" horizontal
			if (bola.getVX() < 0) bola.setX(pala2.getX()-pala2.getAnchura()/2-bola.getRadio()-0.01);
			else bola.setX(pala2.getX()+pala2.getAnchura()/2+bola.getRadio()+0.01);
		}
		// Si está dentro por una esquina, moverla hasta que "salga" (iteraciones - mejorable con recursividad)
		while (choqueExtremoPala(bola, pala1) || choqueExtremoPala(bola, pala2)) {
			bola.setX( bola.getX()+bola.getVX()*0.001 );
			bola.setY( bola.getY()+bola.getVY()*0.001 );
		}
		if (DEBUG_CHOQUES) {  // En modo depuración, dibuja los elementos y espera a click para observar cada choque
			bola.dibuja( vent );
			vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX()/4, bola.getY()+bola.getVY()/4, 1.0f, Color.blue, 20 );
			vent.repaint();
			while (!vent.estaCerrada() && vent.getRatonPulsado()==null) ; // Espera al ratón pulsado sin hacer nada
			while (!vent.estaCerrada() && vent.getRatonPulsado()!=null) ; // Espera al ratón soltado sin hacer nada		
		}
	}

}
