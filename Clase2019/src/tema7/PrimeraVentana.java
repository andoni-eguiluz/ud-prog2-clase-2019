package tema7;

import java.awt.*;
import javax.swing.*;

public class PrimeraVentana {
	public static void main(String[] args) {
		JFrame vent = new JFrame();
		// Movido al final para que dibuje cuando la ventana está entera
		// vent.setVisible( true );  // NO DIBUJA - Informa a Swing de que la ventana debe ser visible
		// ENTRA el hilo de Swing
		// Mejoramos la ventana
		vent.setLocation( 200, 100 );
		vent.setSize( 600, 400 );
		vent.setTitle( "Mi primera ventana" );
		vent.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		
		// Añadir cosas a la ventana
		JPanel p = new JPanel();
		// p.setLayout( new FlowLayout() );  // Por defecto
		p.setLayout( new GridLayout( 3, 2 ));
		JButton b = new JButton( "Aceptar" );
		JLabel l1 = new JLabel( "Nombre:" );
		JTextField t1 = new JTextField( 10 );
		JLabel l2 = new JLabel( "Password:" );
		JTextField t2 = new JTextField( 5 );
		
		p.add( l1 );
		p.add( t1 );
		p.add( l2 );
		p.add( t2 );
		p.add( b );
		vent.add( p );
		
		p.setOpaque( true );
		p.setBackground( Color.red );
		
		vent.setVisible( true );
		
		System.out.println( "Final" );
	//	vent.dispose();  // Solo cuando queramos cerrarla
	}
}
