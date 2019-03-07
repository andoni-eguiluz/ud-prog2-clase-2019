package tema3.sinHerencia;

import java.awt.Color;
import java.util.Random;

import tema3.VentanaGrafica;

public class Pong {

	public static final int ANCHURA_VENTANA = 1000;
	public static final int ALTURA_VENTANA = 800;
	public static final int RADIO_BOLA = 15;
	public static final long MSGS_POR_FRAME = 20; // = 50 FPS
	
	public static void main(String[] args) {
		Pong pong = new Pong();
		pong.jugar();
	}	
	
	// ===================== NO STATIC =================
	
	private VentanaGrafica ventana;
	private Circulo bola;
	private Rect palaI;
	private Rect palaD;
	
	public void jugar() {
		ventana = new VentanaGrafica( ANCHURA_VENTANA, ALTURA_VENTANA, "Pong" );
		ventana.getJFrame().setLocation( 2000, 0 );
		bola = new Circulo( RADIO_BOLA, ANCHURA_VENTANA/2, ALTURA_VENTANA/2, new Color(123, 55, 182) );
		Random random = new Random();
		bola.setVX( (random.nextDouble() - 0.5) * 1000);  // Aleaatorio entre -500 y 500
		bola.setVY( (random.nextDouble() - 0.5) * 500);   // Aleatorio entre -250 y 250
		palaI = new Rect( 25, ventana.getAltura()/2, 20, 60, Color.green );
		palaD = new Rect( ventana.getAnchura()-25, ventana.getAltura()/2, 20, 60, Color.blue );
		bucleJuego();
	}
	
	private void bucleJuego() {
		ventana.setDibujadoInmediato( false );
		while (!ventana.estaCerrada()) {
			// Control de teclado
			// TODO usar el teclado para cambiar las palas
			// ventana.isTeclaPulsada(codTecla)
			// Movimiento
			bola.mueve( MSGS_POR_FRAME / 1000.0 );
			// Control de choques
			if (bola.seSaleEnVertical(ventana)) {
				bola.setVY( -bola.getVY() );
			}
			if (bola.seSaleEnHorizontal(ventana)) {
				bola.setVX( -bola.getVX() );
			}
			// Dibujado
			ventana.borra();
			bola.dibuja( ventana );
			palaI.dibuja( ventana );
			palaD.dibuja( ventana );
			ventana.repaint();
			// Espera
			ventana.espera( MSGS_POR_FRAME );
		}
	}
	
}
