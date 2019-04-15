package temas78;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;

public class VentanaLogin extends JFrame {
	
	public static void main(String[] args) {
		VentanaLogin v = new VentanaLogin();
		v.setVisible( true );
	}

	// Atributos de ventana
	private JTextField tfNick;
	private JTextField tfPassword;
	private JLabel lMensaje;
	private JButton bAceptar;
	private JButton bCancelar;
	
	// Atributos de lógica
	private HashMap<String,String> mapaNicksPassword;  // Clave = nick, Valor = password
	
	public VentanaLogin() {
		// Inicializar lógica
		initClaves();
		// Configurar ventana
		setTitle( "Login de usuario" );
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setSize( 400, 300 );
		setLocation( 100, 0 );  // Coord. absoluta
		// Crear componentes y contenedores
		JLabel lNick = new JLabel( "Nick:" );
		JLabel lPassword = new JLabel( "Password:" );
		JLabel lLogotipo = new JLabel( new ImageIcon( "src/img/UD-icon.png" ) );
		tfNick = new JTextField( 10 );
		tfPassword = new JTextField( 5 );
		lMensaje = new JLabel();
		bAceptar = new JButton( "Aceptar" );
		bCancelar = new JButton( "Cancelar" );
		JPanel pInferior = new JPanel();
		JPanel pCentral = new JPanel();
		JPanel pFila1 = new JPanel( /* new FlowLayout() */ );
		JPanel pFila2 = new JPanel();
		BoxLayout layoutCentral = new BoxLayout( pCentral, BoxLayout.Y_AXIS );
		pCentral.setLayout( layoutCentral );
		// Asociar componentes y contenedores
		getContentPane().add( lMensaje, BorderLayout.NORTH );
		pFila1.add( lNick );
		pFila1.add( tfNick );
		pFila2.add( lPassword );
		pFila2.add( tfPassword );
		pCentral.add( pFila1 );
		pCentral.add( pFila2 );
		getContentPane().add( pCentral, BorderLayout.CENTER );
		getContentPane().add( lLogotipo, BorderLayout.WEST );
		pInferior.add( bAceptar );
		pInferior.add( bCancelar );
		getContentPane().add( pInferior, BorderLayout.SOUTH );
		// Eventos
		// Con clase interna:
		bCancelar.addActionListener( new EscuchadorCancelar() );
		// Con clase interna anónima:
		bAceptar.addActionListener( 
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// System.out.println( "Aceptar" );
					// Comprobar que el login es correcto
					String pass = mapaNicksPassword.get( tfNick.getText() );
					if (pass==null || !pass.equals( tfPassword.getText() ) ) {  // nick/clave incorrecta
						lMensaje.setText( "No existe este usuario o su clave es incorrecta" );
					} else {
						lMensaje.setText( "¡Login correcto!" );
					}
				}
			}
		);
		// Algunos otros eventos para entender su funcionamiento (solo sacan info a consola)
		// Evento de la ventana
		addWindowListener( new WindowListener() {
			@Override
			public void windowActivated(WindowEvent e) {
				System.out.println( "Evento windowActivated " + e );
			}
			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println( "Evento windowClosed " + e );
			}
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println( "Evento windowClosing " + e );
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
				System.out.println( "Evento windowDeactivated " + e );
			}
			@Override
			public void windowDeiconified(WindowEvent e) {
				System.out.println( "Evento windowDeiconified" + e );
			}
			@Override
			public void windowIconified(WindowEvent e) {
				System.out.println( "Evento windowIconified" + e );
			}
			@Override
			public void windowOpened(WindowEvent e) {
				System.out.println( "Evento windowOpened" + e );
			}
		});
		// Evento de ratón - en el gráfico del logotipo
		lLogotipo.addMouseListener( new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println( "Evento mouseReleased " + e );
			}
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println( "Evento mousePressed " + e );
			}
			@Override
			public void mouseExited(MouseEvent e) {
				System.out.println( "Evento mouseExited " + e );
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				System.out.println( "Evento mouseEntered " + e );
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println( "Evento mouseClicked " + e );
			}
		});
		// Evento de MOVIMIENTO de ratón - también en el logotipo
		lLogotipo.addMouseMotionListener( new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				System.out.println( "Evento mouseMoved (MouseMotion) " + e );
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				System.out.println( "Evento mouseDragged (MouseMotion) " + e );
			}
		});
		// Evento de teclado - en el campo textfield de contraseña
		tfPassword.addKeyListener( new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				System.out.println( "Evento keyTyped " + e );
			}
			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println( "Evento keyReleased " + e );
			}
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println( "Evento keyPressed " + e );
			}
		});
		// Evento de foco (cambio de posición de acción del teclado) - en el campo textfield de nick
		tfNick.addFocusListener( new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				System.out.println( "Evento focusLost " + e );
			}
			@Override
			public void focusGained(FocusEvent e) {
				System.out.println( "Evento focusGained " + e );
			}
		});
		// Evento de cambio de componente - por ejemplo en el panel central
		pCentral.addComponentListener( new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
				System.out.println( "Evento componentShown " + e );
			}
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println( "Evento componentResized " + e );
			}
			@Override
			public void componentMoved(ComponentEvent e) {
				System.out.println( "Evento componentMoved " + e );
			}
			@Override
			public void componentHidden(ComponentEvent e) {
				System.out.println( "Evento componentHidden " + e );
			}
		});
	}
	
	class EscuchadorCancelar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}
	
	// Método de inicialización de claves - por código (normalmente se haría con un fichero o base de datos)
	private void initClaves() {
		mapaNicksPassword = new HashMap<>();
		mapaNicksPassword.put( "andoni", "andoni" );  // Usuario "andoni" clave "andoni"
		mapaNicksPassword.put( "admin", "1234" );  // Usuario "admin" clave "1234"
	}
	
}
