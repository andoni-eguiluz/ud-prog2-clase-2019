package tema7;

import javax.swing.*;

public class PrimeraVentana {
	public static void main(String[] args) {
		JFrame vent = new JFrame();
		vent.setVisible( true );
		// ENTRA el hilo de Swing
		// Mejoramos la ventana
		vent.setLocation( 200, 100 );
		vent.setSize( 600, 400 );
		vent.setTitle( "Mi primera ventana" );
		vent.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		System.out.println( "Final" );
	//	vent.dispose();
	}
}
