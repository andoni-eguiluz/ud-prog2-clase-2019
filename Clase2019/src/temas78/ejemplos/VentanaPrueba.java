package temas78.ejemplos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** Ventana de prueba con algunos elementos básicos
 */
@SuppressWarnings("serial")
public class VentanaPrueba extends JFrame {
	
	private JTextArea taTexto;
	private JTextField tfNick;
	private JTextField tfPass;
	public VentanaPrueba() {
		// Formato de la ventana
		this.setTitle( "Mi ventana" );
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 640, 480 );
		setLocation( 100, 100 );
		// Swing -> componentes y contenedores
		// Crear componentes
		taTexto = new JTextArea( "Hola", 5, 40 );
		JLabel lNick = new JLabel( "Nick:" );
		tfNick = new JTextField( "", 10 );
		JLabel lPass = new JLabel( "Pass:" );
		tfPass = new JTextField( "", 10 );
		JLabel lFoto = new JLabel( new ImageIcon("src/img/coches/coche.png") );
		lFoto.setBorder( BorderFactory.createLineBorder(Color.red,2));
		JButton bBorrar = new JButton( "Borrar texto" );
		JButton bAceptar = new JButton( "Aceptar" );
		// Crear contenedores
		JPanel pIzquierdo = new JPanel();
		JPanel pInferior = new JPanel();
		JPanel pIzq1 = new JPanel();  // FlowLayout
		JPanel pIzq2 = new JPanel();
		// Asignar formatos (layouts)
		// getContentPane().setLayout( null ); // Prueba de quitar el layout
		pIzquierdo.setLayout( new GridLayout( 2, 1 ) );
		// Asignar componentes a contenedores
		getContentPane().add( taTexto, BorderLayout.NORTH );
		getContentPane().add( lFoto, BorderLayout.CENTER );
		lFoto.setLocation( -200, 0 );
		// pIzquierdo.setLayout( new GridLayout( 2, 2 ) );
		pIzq1.add( lNick );  
		pIzq1.add( tfNick );
		pIzq2.add( lPass );
		pIzq2.add( tfPass );
		pIzquierdo.add( pIzq1 );
		pIzquierdo.add( pIzq2 );
		getContentPane().add( pIzquierdo, BorderLayout.WEST );
		pInferior.add( bAceptar );
		pInferior.add( bBorrar );
		getContentPane().add( pInferior, BorderLayout.SOUTH );
		// Configuración de componentes
		// Pruebas
		// lFoto.setLocation( 100, 100 );
		// lFoto.setSize( 100, 75 );
		// lFoto.setBounds( 100, 100, 200, 150 );
		// pInferior.setBounds( 10, 260, 100, 155 );
		// pInferior.setBorder( BorderFactory.createLineBorder( Color.blue, 1 ));
		// Asociar gestores de eventos a componentes
		bBorrar.addActionListener( new RespuestaBoton() );
		tfPass.addKeyListener( new TecladoPass() );
		tfNick.addKeyListener( new TecladoPass() );
		lFoto.addMouseMotionListener( new MovtoRaton() );
		lFoto.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println( "Suelta en " + e.getX() + "," + e.getY() );
			}
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println( "Pulsación en " + e.getX() + "," + e.getY() );
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println( "Click del coche en " + e.getX() + "," + e.getY() );
			}
		});
		lFoto.addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println( lFoto.getWidth() + "," + lFoto.getHeight() );
			}
		});
	}

	/** Prueba de la clase
	 * @param args
	 */
	public static void main(String[] args) {
		VentanaPrueba v = new VentanaPrueba();
		v.setVisible( true );  
		// Swing lanza un gestor de eventos
		// Swing lanza un NUEVO HILO de ejecución
	}

	class RespuestaBoton implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			taTexto.setText( "" );
		}
	}

	class TecladoPass implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println( "Typed " + e.getKeyCode() );
		}
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println( "Press"  + e.getKeyCode());
			if (e.getKeyCode()==KeyEvent.VK_UP) {
				System.out.println( "Arriba");
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println( "Released "  + e.getKeyCode() );
		}
	}
	
	class MovtoRaton implements MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println( e.getX() + "," + e.getY() );
		}
		
	}
}
