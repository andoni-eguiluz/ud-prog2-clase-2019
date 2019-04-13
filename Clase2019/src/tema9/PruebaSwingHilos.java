package tema9;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class PruebaSwingHilos {
	private static Thread miHilo;
	public static void main(String[] args) {
		JFrame v = new JFrame();
		v.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		v.setSize( 320, 200 );
		JButton bAceptar = new JButton( "Aceptar" );
		JTextArea taInfo = new JTextArea();
		v.add( new JScrollPane( taInfo ), BorderLayout.CENTER );
		v.add( bAceptar, BorderLayout.SOUTH );
		v.setVisible( true );
		bAceptar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// procesoLento( taInfo );   // Esto daba problemas
				// Manera 1: Herencia de Thread
				miHilo = new MiHiloPrueba( taInfo );
				miHilo.start();
				// Manera 2: Un thread asociado a un runnable
				Runnable miRunnable = new MiRunnablePrueba( taInfo );
				// No: miRunnable.start();
				Thread miHiloConRunnable = new Thread( miRunnable );
				miHiloConRunnable.start();
			}
		});
		v.addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				miHilo.stop();
			}
		});
	}
	
	public static void procesoLento( JTextArea ta ) {
		try {
			for (int i=0; i<5; i++) {
				Thread.sleep( 3000 );  // Esto va a traer problemas...  :-)
				
				ta.append( "Estoy en ello...\n");

				// O mejor... con invokeLater:
				/*
				SwingUtilities.invokeLater( new Runnable() {
					@Override
					public void run() {
						ta.append( "Estoy en ello...\n");
					}
				});
				*/

				
				
				
			}
		} catch (Exception ex) {}
	}
}

// Manera 1: heredando Thread
class MiHiloPrueba extends Thread {
	private JTextArea miTa;
	public MiHiloPrueba( JTextArea ta ) { miTa = ta; }
	@Override
	public void run() {
		PruebaSwingHilos.procesoLento( miTa );
	}
}

// Manera 2: implementando runnable
class MiRunnablePrueba implements Runnable {
	private JTextArea miTa;
	public MiRunnablePrueba( JTextArea ta ) { miTa = ta; }
	@Override
	public void run() {
		PruebaSwingHilos.procesoLento( miTa );
	}
}

