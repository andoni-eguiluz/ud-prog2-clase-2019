package temas78.ejemplosSwing.otros;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** Ejemplo de cambio de ventana en caliente
 * @author andoni.eguiluz at deusto.es
 */
@SuppressWarnings("serial")
public class EjemploCambioVentanaEnCaliente extends JFrame {
	
	public static void main(String[] args) {
		EjemploCambioVentanaEnCaliente tt = new EjemploCambioVentanaEnCaliente();
		tt.setVisible( true );
	}
	
	JPanel pCentro = new JPanel();
	int numBotones = 0;
	public EjemploCambioVentanaEnCaliente() {
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 800, 640 );
		JButton bPrueba = new JButton( "Púlsame!" );
		bPrueba.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numBotones++;
				JButton bNuevo = new JButton( "Botón nuevo " + numBotones );
				pCentro.add( bNuevo );
				pCentro.revalidate();  // Fundamental hacer esto cuando se cambia la estructura de un contenedor "en caliente"
			}
		});
		getContentPane().add( bPrueba, BorderLayout.SOUTH );
		getContentPane().add( pCentro, BorderLayout.CENTER );
	}
}
