package temas78;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** Mi primera ventana en Swing - clase de pruebas con ventanas
 * @author andoni.eguiluz at deusto.es
 */
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
		// Layout del contenedor
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

		// Logotipo a la izquierda
		vent.add( new JLabel( new ImageIcon( "bin/img/UD-blue-girable.png") ), BorderLayout.EAST );
		
		p.setOpaque( true );
		p.setBackground( Color.red );
		
		vent.setVisible( true );  // Lo ponemos al final para que Swing dibuje cuando la ventana ya está acabada
		
		// int valor = 0;  Posible variable local (ver abajo)
		// Gestionar la pulsación del botón ¿cómo?
		// Se nos podría ocurrir ... if (boton pulsado)
		// O también ... while (not boton pulsado) espera
		// Pero en programación orientada a eventos YA NO HAY POLLING: Solo asociación de eventos:
		b.addActionListener( new MiActionListener( t1, t2 ) );  // Evento de acción del botón
		t2.addKeyListener( new MiKeyListener() );  // Evento de teclado del textfield
		
		System.out.println( "Final" );
	//	vent.dispose();  // Solo cuando queramos cerrarla
	}
	
	// Clase interna - esto sería otra manera de hacerlo
	// (PENDIENTE)
	class MiActionListener2 implements ActionListener {
		private JTextField tfNombre;
		private JTextField tfPass;
		public MiActionListener2(JTextField tfNombre, JTextField tfPass) {
			this.tfNombre = tfNombre;
			this.tfPass = tfPass;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println( "Pulsado! " + tfNombre.getText() + " pass = " + tfPass.getText() );
			// valor++;  NO SE PUEDE ACCEDER a variables locales 
			// del método creador de la ventana
			// Cómo acceder a los textfields?  Recibiéndolos en el constructor
			
		}
	}

}

// Clase externa para gestión de teclado
class MiKeyListener implements KeyListener {
	@Override
	public void keyPressed(KeyEvent arg0) {
		System.out.println( "KeyPressed " + arg0 );
		if (arg0.getKeyCode()==KeyEvent.VK_CONTROL)
			arg0.consume();
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		System.out.println( "KeyReleased " + arg0 );
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		System.out.println( "KeyTyped " + arg0 );
	}
}

//Clase externa para gestión de acción (de botón)
class MiActionListener implements ActionListener {
	private JTextField tfNombre;
	private JTextField tfPass;
	public MiActionListener(JTextField tfNombre, JTextField tfPass) {
		this.tfNombre = tfNombre;
		this.tfPass = tfPass;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// valor++;  NO SE PUEDE ACCEDER a variables locales 
		// del método creador de la ventana
		// Cómo acceder a los textfields?  Recibiéndolos en el constructor
		System.out.println( "Pulsado! " + tfNombre.getText() + " pass = " + tfPass.getText() );
	}
}
