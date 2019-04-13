package temas78.ejemplosSwing.hilos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PruebaSwingHilos {
	public static void main(String[] args) {
		JFrame v = new JFrame();
		v.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		v.setSize( 320, 200 );
		JButton bAceptar = new JButton( "Aceptar" );
		v.add( bAceptar );
		v.setVisible( true );
		bAceptar.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Thread.sleep( 15000 );  // Esto va a traer problemas...  :-)
				} catch (Exception ex) {}
			}
		});
	}
}
