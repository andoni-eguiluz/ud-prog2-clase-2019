package tema3.sinHerencia;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import com.sun.glass.events.KeyEvent;

import tema3.VentanaGrafica;
import tema3.sinHerencia.Fisica.Polar;

/** Clase de pruebas de cómo resolver las colisiones y los movimientos de rebote
 * en juegos del estilo del Pong  (círculos rebotando con rectángulos)
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class PruebaFisicasPong {

	private static final long MSGS_POR_FRAME = 20; // 20 msgs por frame = 50 frames por segundo aprox
	private static double VEL_JUEGO = 1.0;         // 1.0 = tiempo real. Cuando mayor, más rápido pasa el tiempo y viceversa 
	private static final double RADIO_BOLA = 15;   // Radio de la pelota
	private static final double VEL_MIN = 300;     // Velocidad inicial mínima (300 píxels por segundo)
	private static final double VEL_RANGO = 200;   // Rango de velocidad mínima (de VEL_MIN a VEL_MIN + 200 píxels por segundo)
	private static final double VEL_PALA = 500;    // Velocidad de desplazamiento de las palas (pixels/sg)
	
	public static void main(String[] args) {
		PruebaFisicasPong juego = new PruebaFisicasPong();
		juego.vent = new VentanaGrafica( 1000, 800, "Pruebas físicas de colisión para pong" );
		juego.vent.setDibujadoInmediato( false );
		juego.prueba( 1, "Rebote lateral" );
		juego.prueba( 2, "Rebote lateral poco realista" );
		juego.prueba( 3, "Problema colisión en alta velocidad" );
		juego.prueba( 4, "Rebote superior" );
		juego.prueba( 5, "Rebote superior/lateral con problemas" );
		juego.prueba( 6, "Rebote superior/lateral corregido"  );
		juego.prueba( 7, "Rebote esquina no realista (solo lateral/vertical)" );
		juego.prueba( 8, "Rebote esquina realista" );
		juego.prueba( 9, "Varios rebotes esquina" );
		juego.vent.acaba();
	}
	
	// =================================
	// ATRIBUTOS Y MÉTODOS DE INSTANCIA (no static)
	// =================================
	
	private VentanaGrafica vent;  // Ventana gráfica para la prueba
	private ArrayList<Circulo> bolas;  // Bolas para la prueba
	private Rectangulo pala1;  // Pala para la prueba
	private int numPrueba;  // Número de cada nivel de prueba
	private String textoPrueba;  // Texto correspondiente a la descripción de cada nivel de prueba
	private boolean finPrueba;  // Booleano para acabar cada nivel de prueba
	private Font font = new Font( "Arial", Font.PLAIN, 32 );  // Font de feedback de texto en pantalla
	private ArrayList<Circulo> bolasIniciales;  // Para memorizar la bola inicial en cada nivel de prueba
	private Rectangulo palaInicial;  // Para memorizar la pala inicial en cada nivel de prueba
	
	/** Prueba de comportamiento de físicas de Pong en ventana
	 * @param numPrueba	Número de prueba (1 a 9)
	 * @param textoPrueba	Texto a sacar en pantalla con cada prueba
	 */
	public void prueba( int numPrueba, String textoPrueba ) {
		this.numPrueba = numPrueba;
		this.textoPrueba = textoPrueba;
		finPrueba = false;
		pala1 = new Rectangulo( 60, 300, 180, 400, Color.green ); // Pala aumentada
		Circulo bola = new Circulo( RADIO_BOLA*3, 600, 100, Color.magenta ); // Bola aumentada
		bola.setVX( -400 );
		bola.setVY( 200 );
		bolas = new ArrayList<Circulo>();
		bolas.add( bola );
		switch (numPrueba) {
			case 1: { // Rebote lateral
				break;
			}
			case 2: { // Rebote lateral poco realista
				bola.setX( 580 );
				bola.setVX( -4000 );
				bola.setVY( 2000 );
				break;
			}
			case 3: { // Problema colisión en alta velocidad
				bola.setX( 580 );
				bola.setVX( -8000 );
				bola.setVY( 4000 );
				break;
			}
			case 4: { // Rebote superior
				pala1.setY( 500 );
				break;
			}
			case 5: { // Rebote superior/lateral con problemas
				pala1.setY( 970 );
				pala1.setVY( -450 );
				break;
			}
			case 6: { // Rebote superior corregido
				pala1.setY( 970 );
				pala1.setVY( -450 );
				break;
			}
			case 7: { // Rebote esquina no realista (solo lateral/vertical)
				pala1.setY( 480 );
				break;
			}
			case 8: { // Rebote esquina realista
				pala1.setY( 480 );
				break;
			}
			case 9: { // Rebote esquina con varias bolas
				pala1.setY( 452 );
				bolas.get(0).setRadio( RADIO_BOLA*2 );
				Circulo bola2 = new Circulo( RADIO_BOLA*2, 600, 100, Color.orange );
				Fisica.Polar vectorVelocidad = new Polar( new Point2D.Double( bolas.get(0).getVX(), bolas.get(0).getVY() ) );
				vectorVelocidad.rotar( Math.PI / 100 );
				bola2.setVX( vectorVelocidad.toPoint().getX() ); bola2.setVY( vectorVelocidad.toPoint().getY() );
				bolas.add( bola2 );
				Circulo bola3 = new Circulo( RADIO_BOLA*2, 600, 100, Color.cyan );
				vectorVelocidad.rotar( - 2 * Math.PI / 100 );
				bola3.setVX( vectorVelocidad.toPoint().getX() ); bola3.setVY( vectorVelocidad.toPoint().getY() );
				bolas.add( bola3 );
				break;
			}
		}
		mover();
	}
	
	// Bucle de movimiento dirigido por el usuario
	// (click mueve un fotograma, ctrl mueve contínuo)
	private void mover() {
		vent.borra();
		vent.dibujaTexto( 50, 750, "Prueba " + numPrueba + ". Haz <click> para iniciar", font, Color.black );
		vent.repaint();
		bolasIniciales = new ArrayList<>();
		for (Circulo bola : bolas) bolasIniciales.add( new Circulo( bola ) );  // Duplica las bolas para una copia
		palaInicial = new Rectangulo( pala1 );
		while (!vent.estaCerrada() && vent.getRatonPulsado()==null) ; // Espera al ratón pulsado sin hacer nada
		while (!vent.estaCerrada() && vent.getRatonPulsado()!=null) ; // Espera al ratón soltado sin hacer nada
		while (!vent.estaCerrada() && !finPrueba) {
			// Manejo de teclado
			gestionTeclado();
			// Movimiento de las bolas de prueba
			for (Circulo bola : bolas) {
				bola.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO ); }
			// Movimiento de la pala de prueba
			pala1.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
			// Control de salida de pantalla (la prueba se acaba cuando la bola llega a un borde) 
			for (Circulo bola : bolas) {
				if (bola.seSaleEnHorizontal( vent ) || bola.seSaleEnVertical( vent )) {
					finPrueba = true;
				}
			}
			// Preparación de dibujado
			vent.borra();
			dibujaBordes();
			// Choque bola y palas
			for (Circulo bola : bolas) {
				if (choqueLateralPala(bola, pala1)) {  // Solo hay pala1 en esta prueba
					bola.setVX( -bola.getVX() );
				} else if (choqueVerticalPala(bola, pala1)) {  // Solo hay pala1 en esta prueba
					bola.setVY( -bola.getVY() );
				} else if (choqueExtremoPala(bola, pala1)) {  // Solo hay pala1 en esta prueba  
					// bola.setVX( -bola.getVX() );  // V2 (comportamiento mejorado)
					calculaReboteEsquina( pala1, bola );
				}
				reajustaBola( bola );
			}
			// Dibujado
			vent.dibujaTexto( 50, 700, textoPrueba, font, Color.black );
			vent.dibujaTexto( 50, 750, "<ctrl> mueve, <click> avanza frame, <r> reinicia, <esc> acaba", font, Color.black );
			pala1.dibuja( vent );
			for (Circulo bola : bolas) {
				bola.dibuja( vent );
				// Dibujado de apoyo
				vent.dibujaCirculo( bola.getX(), bola.getY(), 5, 1.0f, Color.blue );
				vent.dibujaFlecha( bola.getX(), bola.getY(), bola.getX()+bola.getVX(), bola.getY()+bola.getVY(), 2.0f, Color.blue, 20 );
			}
			vent.repaint();
			// Ciclo de espera en cada bucle (dirigido por el usuario
			if (vent.isControlPulsado()) {  // Si se pulsa control, velocidad normal
				vent.espera( MSGS_POR_FRAME );
			} else {  // Si no se pulsa, se controla fotograma a fotograma haciendo click
				while (!vent.estaCerrada() && !vent.isTeclaPulsada( KeyEvent.VK_ESCAPE ) && !vent.isTeclaPulsada( KeyEvent.VK_R ) && !finPrueba && !vent.isControlPulsado() && vent.getRatonPulsado()==null) ; // Espera al ratón pulsado sin hacer nada
				while (!vent.estaCerrada() && !vent.isTeclaPulsada( KeyEvent.VK_ESCAPE ) && !vent.isTeclaPulsada( KeyEvent.VK_R ) && !finPrueba && !vent.isControlPulsado() && vent.getRatonPulsado()!=null) ; // Espera al ratón soltado sin hacer nada
			}
		}
	}
	
	private void gestionTeclado() {
		if (vent.isTeclaPulsada( KeyEvent.VK_PLUS )) {  // + acelera
			VEL_JUEGO *= 1.05;
			vent.setMensaje( "Nueva velocidad de juego: " + VEL_JUEGO );
		}
		if (vent.isTeclaPulsada( KeyEvent.VK_MINUS )) {  // - decelera
			VEL_JUEGO /= 1.05;
			vent.setMensaje( "Nueva velocidad de juego: " + VEL_JUEGO );
		}
		if (vent.isTeclaPulsada( KeyEvent.VK_ESCAPE )) {  // Escape finaliza la prueba
			finPrueba = true;
		}
		if (vent.isTeclaPulsada( KeyEvent.VK_R ) ) {  // 'R' reinicia el nivel
			bolas.clear(); // Reinicia lista de bolas
			for (Circulo bola : bolasIniciales) bolas.add( new Circulo( bola ) );
			pala1 = new Rectangulo( palaInicial );
		}
		// No se hacen movimientos de jugador (esto no es un juego interactivo, solo es una prueba)
	}
	
	private void dibujaBordes() {
		vent.dibujaRect( 0, 0, vent.getAnchura(), vent.getAltura(), 0.5f, Color.black );
		// vent.dibujaLinea( vent.getAnchura()/2, 0, vent.getAnchura()/2, vent.getAltura(), 0.5f, Color.black );
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
		if (numPrueba==7) return false;  // En la prueba 7 no se considera el choque con esquina
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

	private void calculaReboteEsquina( Rectangulo pala, Circulo bola ) {
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
		if (numPrueba<6) return; // No hace reajuste en pruebas 1 a 5
		if (bola.seSaleEnHorizontal( vent )) {
			bola.setVX( Math.abs(bola.getVX()) * bola.salidaHorizontal( vent ) );  // Hacia adentro siempre
		}
		if (choqueVerticalPala(bola, pala1)) {  // Si hay choque "plano" vertical
			// 1.- Reajustar la velocidad para que la velocidad vertical de la bola sea mayor que la de la pala
			System.out.println( "Velocidad vertical " + bola.getVY() + " (pala " + pala1.getVY() + ")" );
			if (choqueSuperiorPala(bola, pala1) && pala1.getVY()<0) { // Si va hacia arriba sumar velocidad arriba de la pala
				bola.setVY( bola.getVY() + pala1.getVY() );  // La velocidad de la pala se suma a la de la bola
			} else if (choqueInferiorPala(bola, pala1) && pala1.getVY()>0) { // Si va hacia abajo sumar velocidad abajo de la pala
				bola.setVY( bola.getVY() + pala1.getVY() );  // La velocidad de la pala se suma a la de la bola
			}
			System.out.println( "Nueva velocidad vertical " + bola.getVY() );
			// 2.- Ajustar la posición para que no "coma" a la pala
			System.out.println( "Posición vertical " + bola.getY() );
			if (bola.getY() < pala1.getY()) bola.setY(pala1.getY()-pala1.getAltura()/2-bola.getRadio()-0.01);
			else bola.setY(pala1.getY()+pala1.getAltura()/2+bola.getRadio()+0.01);
			System.out.println( "Nueva posición vertical " + bola.getY() );
		}
		if (bola.seSaleEnVertical( vent )) {
			bola.setVY( Math.abs(bola.getVY()) * bola.salidaVertical( vent ) );  // Hacia adentro siempre
		}
		if (choqueLateralPala(bola, pala1)) {  // Si hay choque "plano" horizontal
			if (bola.getX() < pala1.getX()) bola.setX(pala1.getX()-pala1.getAnchura()/2-bola.getRadio()-0.01);
			else bola.setX(pala1.getX()+pala1.getAnchura()/2+bola.getRadio()+0.01);
		}
		while (choqueExtremoPala(bola, pala1)) {  // Solo hay pala1 en esta prueba
			bola.setX( bola.getX()+bola.getVX()*0.05 );
			bola.setY( bola.getY()+bola.getVY()*0.05 );
		}
	}

}
