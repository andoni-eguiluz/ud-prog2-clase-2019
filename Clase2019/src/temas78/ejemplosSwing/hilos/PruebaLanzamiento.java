package temas78.ejemplosSwing.hilos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/** Prueba de lanzamiento de un STR desde un botón (interactivo)
 * @author andoni.eguiluz at ingenieria.deusto.es
 */
public class PruebaLanzamiento {
	public static void main(String[] args) {
		JFrame v = new JFrame( "Prueba lanzamiento de STR desde botón" );
		v.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		v.setSize( 400, 100 );
		JButton b = new JButton( "Lanza juego!" );
		v.add( b );
		b.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Mal:
				varios.ejemploMoon.Moon.main( null );
				// TODO Bien: ¿?
				
			}
		});
		v.setVisible( true );
	}
}
