package temas78.ejemplos;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/** Ejemplo de ventana antigua con AWT (sin Swing)
 * Observa la diferencia de sensación gráfica
 */
@SuppressWarnings("serial")
public class VentanaAWT1 extends Frame {
	TextField tfEmail;
	TextArea taMensaje;
	Button botonEnviar;
	Button botonSalir;

	public VentanaAWT1()
	{
		// Creación de componentes
		Panel panelCentral = new Panel();
		tfEmail = new TextField(20);
		taMensaje = new TextArea(5,35);
		Label etiqueta1 = new Label("Email:");
		botonEnviar = new Button("Enviar");
		botonSalir = new Button("Salir");
					
		// Añadir comp a contenedores
		Panel panelSup = new Panel();
		panelSup.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		panelSup.add(etiqueta1);
		panelSup.add(tfEmail);		
	
		panelCentral.add( new Label("Escribe aquí tu Mensaje") );
		panelCentral.add(taMensaje);

		Panel panelInferior = new Panel();
		panelInferior.add(botonEnviar);
		panelInferior.add(botonSalir);

		this.setLayout( new BorderLayout() );
		this.add(panelSup, BorderLayout.NORTH );
		this.add(panelCentral, BorderLayout.CENTER );
		this.add(panelInferior, BorderLayout.SOUTH );

		// Formato
		this.setSize(300,225);
		this.setTitle("Envío de Correo Electrónico");
	//	this.setVisible(true);
		
		// Eventos
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) { // Cierre de la ventana
				dispose();
			}
		});
	}
	
	void hacerAlgo() {
		botonEnviar.setLabel( "hola");
	}

	public static void main(String[] args)
	{
		boolean pruebas = false;
		if (!(pruebas)) {
			VentanaAWT1 v =new VentanaAWT1();
			// MiVentana v2 = new MiVentana();  ... etc etc
			v.setVisible(true);
			System.out.println( "Final" );
			// try { Thread.sleep( 10000 ); } catch (InterruptedException e) {}
			// v.dispose();
			// v2.dispose();
			// System.exit(0);
		} else {	
			Frame f = new Frame();
			f.setTitle( "Mi ventanita" );
			f.setSize( 300, 200 );
			Panel p = new Panel();
			TextField miCuadro = new TextField( "saluda", 15 );
			p.add( miCuadro );
			Button b2 = new Button( "no saludes" );
			f.setVisible( true );
			p.add( b2 );
			
			f.add( p );
			System.out.println( "Se acabó");
			try { Thread.sleep( 10000 ); } catch (InterruptedException e) {}
			System.out.println( miCuadro.getText() );
			// f.dispose();
		}
	}
}
