package tema3.sinHerencia;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Random;

import com.sun.glass.events.KeyEvent;

import tema3.VentanaGrafica;
import tema3.sinHerencia.Fisica.Polar;

public class PongV2 {

	private static final long MSGS_POR_FRAME = 20; // 20 msgs por frame = 50 frames por segundo aprox
	private static double VEL_JUEGO = 1.0;         // 1.0 = tiempo real. Cuando mayor, más rápido pasa el tiempo y viceversa 
	private static final double RADIO_BOLA = 15;   // Radio de la pelota
	private static final double VEL_MIN = 300;     // Velocidad inicial mínima (300 píxels por segundo)
	private static final double VEL_RANGO = 200;   // Rango de velocidad mínima (de VEL_MIN a VEL_MIN + 200 píxels por segundo)
	private static final double VEL_PALA = 500;    // Velocidad de desplazamiento de las palas
	
	public static void main(String[] args) {
		PongV2 juego = new PongV2();
		juego.jugar();
	}
	
	// =================================
	// ATRIBUTOS Y MÉTODOS DE INSTANCIA (no static)
	// =================================
	
	private VentanaGrafica vent;
	private Circulo bola;
	private Rectangulo pala1; 
	private Rectangulo pala2; 
	
	public void jugar() {
		Random random = new Random();
		vent = new VentanaGrafica( 1000, 800, "Juego de pong" );
		// La bola
		bola = new Circulo( RADIO_BOLA, 500, 400, Color.magenta );
		bola.setVX( random.nextDouble() * VEL_RANGO );
		bola.setVX( bola.getVX()<0 ? bola.getVX()-VEL_RANGO : bola.getVX()+VEL_MIN ); // Velocidad x (aleatoria entre -500 y +500 px/seg, al menos 300)
		bola.setVY( random.nextDouble() * VEL_RANGO );
		bola.setVY( bola.getVY()<0 ? bola.getVY()-VEL_RANGO : bola.getVY()+VEL_MIN ); // Velocidad y (aleatoria entre -500 y +500 px/seg, al menos 300)
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
			bola.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
			// Movimiento de las palas
			pala1.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
			pala2.mueve( MSGS_POR_FRAME/1000.0 * VEL_JUEGO );
			// Control de salida de pantalla
			if (bola.seSaleEnHorizontal( vent )) {
				bola.setVX( -bola.getVX() );
			}
			if (bola.seSaleEnVertical( vent )) {
				bola.setVY( -bola.getVY() );
			}
			// Choque bola y palas
			if (choqueLateralPala(pala1) || choqueLateralPala(pala2)) {
				bola.setVX( -bola.getVX() );
			} else if (choqueVerticalPala(pala1) || choqueVerticalPala(pala2)) {
				bola.setVY( -bola.getVY() );
			} else if (choqueExtremoPala(pala1) || choqueExtremoPala(pala2)) {  
				// bola.setVX( -bola.getVX() );  // V2 (comportamiento mejorado)
				if (choqueExtremoPala(pala1)) calculaReboteEsquina( pala1, bola );
				else calculaReboteEsquina( pala2, bola );
			}
			reajustaBola();
			// Dibujado
			vent.repaint();
			vent.borra(); // podría hacerse bola.borra( vent ); antes de mover... pero luego meteremos más cosas que solo una bola, y es habitual tener que borrar todo
			dibujaBordes();
			bola.dibuja( vent );
			pala1.dibuja( vent );
			pala2.dibuja( vent );
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
	private boolean choqueLateralPala( Rectangulo pala ) {
		if (bola.getY() >= pala.getY()-pala.getAltura()/2 &&
			bola.getY() <= pala.getY()+pala.getAltura()/2) {  // La y de la bola está dentro de la pala
			if (bola.getX()-bola.getRadio()<=pala.getX()+pala.getAnchura()/2 &&
				bola.getX()+bola.getRadio()>=pala.getX()-pala.getAnchura()/2 ) {
				return true;
			}
		}
		return false;
	}
	private boolean choqueVerticalPala( Rectangulo pala ) {
		if (bola.getX() >= pala.getX()-pala.getAnchura()/2 &&
			bola.getX() <= pala.getX()+pala.getAnchura()/2) {  // La x de la bola está dentro de la pala
			if (bola.getY()-bola.getRadio()<=pala.getY()+pala.getAltura()/2 &&
				bola.getY()+bola.getRadio()>=pala.getY()-pala.getAltura()/2 ) {
				return true;
			}
		}
		return false;
	}
	private boolean choqueSuperiorPala( Rectangulo pala ) {
		return choqueVerticalPala(pala) && bola.getY() < pala.getY();
	}
	private boolean choqueInferiorPala( Rectangulo pala ) {
		return choqueVerticalPala(pala) && bola.getY() > pala.getY();
	}
	
	// Coche con esquina
	private boolean choqueExtremoPala(Rectangulo pala) {
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
	private void reajustaBola() {
		if (bola.seSaleEnHorizontal( vent )) {
			bola.setVX( Math.abs(bola.getVX()) * bola.salidaHorizontal( vent ) );  // Hacia adentro siempre
		}
		if (choqueVerticalPala(pala1)) {  // Si hay choque "plano" vertical
			// 1.- Reajustar la velocidad para que la velocidad vertical de la bola sea mayor que la de la pala
			if ((choqueSuperiorPala(pala1) && pala1.getVY()<0)       // Si va hacia arriba sumar velocidad arriba de la pala
			    || (choqueInferiorPala(pala1) && pala1.getVY()>0)) { // Si va hacia abajo sumar velocidad abajo de la pala
				bola.setVY( bola.getVY() + pala1.getVY() );  // La velocidad de la pala se suma a la de la bola
			}
			// 2.- Ajustar la posición para que no "coma" a la pala
			if (bola.getY() < pala1.getY()) bola.setY(pala1.getY()-pala1.getAltura()/2-bola.getRadio()-0.01);
			else bola.setY(pala1.getY()+pala1.getAltura()/2+bola.getRadio()+0.01);
		}
		if (choqueVerticalPala(pala2)) {  // Si hay choque "plano" vertical
			// 1.- Reajustar la velocidad para que la velocidad vertical de la bola sea mayor que la de la pala
			if ((choqueSuperiorPala(pala2) && pala2.getVY()<0)       // Si va hacia arriba sumar velocidad arriba de la pala
				|| (choqueInferiorPala(pala2) && pala2.getVY()>0)) { // Si va hacia abajo sumar velocidad abajo de la pala
				bola.setVY( bola.getVY() + pala2.getVY() );
			}
			// 2.- Ajustar la posición para que no "coma" a la pala
			if (bola.getY() < pala2.getY()) bola.setY(pala2.getY()-pala2.getAltura()/2-bola.getRadio()-0.01);
			else bola.setY(pala2.getY()+pala2.getAltura()/2+bola.getRadio()+0.01);
		}
		if (bola.seSaleEnVertical( vent )) {
			bola.setVY( Math.abs(bola.getVY()) * bola.salidaVertical( vent ) );  // Hacia adentro siempre
		}
		if (choqueLateralPala(pala1)) {  // Si hay choque "plano" horizontal
			if (bola.getX() < pala1.getX()) bola.setX(pala1.getX()-pala1.getAnchura()/2-bola.getRadio()-0.01);
			else bola.setX(pala1.getX()+pala1.getAnchura()/2+bola.getRadio()+0.01);
		}
		if (choqueLateralPala(pala2)) {  // Si hay choque "plano" horizontal
			if (bola.getX() < pala2.getX()) bola.setX(pala2.getX()-pala2.getAnchura()/2-bola.getRadio()-0.01);
			else bola.setX(pala2.getX()+pala2.getAnchura()/2+bola.getRadio()+0.01);
		}
		while (choqueExtremoPala(pala1) || choqueExtremoPala(pala2)) {
			bola.setX( bola.getX()+bola.getVX()*0.05 );
			bola.setY( bola.getY()+bola.getVY()*0.05 );
		}
	}

}
