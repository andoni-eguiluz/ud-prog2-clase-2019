package temas78.ejemplosSwing.modelos;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

public class EjemploDeJList {

	private static JList<String> lNombres;
	private static Vector<String> vNombres;
	private static DefaultListModel<String> mNombres;
	public static void main(String[] args) {
		JFrame v = new JFrame();
		v.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		v.setSize( 400, 300 );
		v.setLocation( 2000, 0 ); // TODO poner la posición que se quiera
		// Crear JList y meterlo en la ventana
		String[] nombres = { "Andoni", "Lucía", "Marta" };
		vNombres = new Vector<>( Arrays.asList(nombres) );
		mNombres = new DefaultListModel<>();
		// Se podría hacer desde fichero o donde sea
		for (String n : nombres) mNombres.addElement( n );
		//vNombres = new Vector<>( Arrays.asList(
		//	new String[] { "Andoni", "Lucía", "Marta" }	) );
		// vNombres.add( "Andoni" ); 
		lNombres = new JList<String>( mNombres );
		JScrollPane spNombres = new JScrollPane( lNombres );
		v.getContentPane().add( spNombres, BorderLayout.CENTER );
		
		JPanel pBotonera = new JPanel();
		JButton bAnyadir = new JButton( "Añadir" );
		pBotonera.add( bAnyadir );
		bAnyadir.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mNombres.addElement( "Ekaitz Agirregomezkorta Gutierrez" );
			}
		});
		v.getContentPane().add( pBotonera, BorderLayout.SOUTH );
		// Eventos de JList
		lNombres.addListSelectionListener( new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				System.out.println( e.getFirstIndex() + "," + e.getLastIndex() +
						"|" + e.getValueIsAdjusting()  );
				System.out.println( lNombres.getSelectedIndex() );
			}
		});
		// Hacer visible la ventana
		v.setVisible( true );

		// Chequeo de Thread-safe en Swing aprovechando el ejemplo de JList:
		// A la vez acceden a los datos (al modelo) el hilo main y Swing...
		// Posibles problemas
		
		try {
			Thread.sleep( 2000 );
		} catch (Exception e) { }
		
		for (int i=0;i<10000;i++) {
			// Sin control de hilos...  Swing no es Thread-safe
			
			mNombres.remove(0);
			mNombres.add( 0, "Nuevo " + r.nextInt(1000) );
			 
			// Con control de hilos...
//			SwingUtilities.invokeLater( new Runnable() {
//				@Override
//				public void run() {
//					mNombres.remove(0);
//					mNombres.add( 0, "Nuevo " + r.nextInt(1000) );
//				}
//			});
			
			try {
				Thread.sleep( 2 );
			} catch (Exception e) { }
		}
	}
	
	private static Random r = new Random();

}
