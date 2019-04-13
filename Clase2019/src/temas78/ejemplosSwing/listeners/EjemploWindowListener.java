package temas78.ejemplosSwing.listeners;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.Date;

@SuppressWarnings("serial")
public class EjemploWindowListener extends JFrame
{
	private static EjemploWindowListener miVentana = null;
	public static EjemploWindowListener getVentana() { return miVentana; }
	boolean fin;
	
	JPanel panelBoton;
	JTextArea area;
	JButton boton;

	public EjemploWindowListener()
	{
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		miVentana = this;
		area = new JTextArea( 20, 50 );
		panelBoton = new JPanel();
		boton = new JButton("Pulsa Aquí");

		panelBoton.add(boton);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new JScrollPane(area),"Center");
		this.getContentPane().add(panelBoton,"South");

		boton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				area.append( "Botón Pulsado: " + new Date() + "\n" );  // Mensaje a la textarea
				
				// Y qué pasa si tarda mucho tiempo algo...?
				// try { Thread.sleep( 100000 ); } catch (InterruptedException e2) {}
				
				// Swing no se corta con las excepciones. Las muestra y sigue pintando
				// throw new RuntimeException();  
			}
		} );
		
		this.setSize(600,400);
		this.setTitle("Ejemplo Sencillo para entender hilos");
		
		this.addWindowListener( new MiEscuchadorVentana() );
	}
	
	private double procesoIntenso() {
		double r = 3.0;
			// Hago algo que tarda mucho tiempo
			try { Thread.sleep(3000); } catch (InterruptedException e) {}  // Simulado con un sleep
			r = (new java.util.Random()).nextDouble()*100;
		return r;
	}
	
	public static void main(String[] args)
	{
		EjemploWindowListener v = new EjemploWindowListener();
		v.setVisible(true);
		v.fin = false;
		while (!v.fin) {
			double r = v.procesoIntenso();
			// Y visualizo el resultado
			v.area.append( r + "\n" );
		}
		v.dispose();
	}
}



class MiEscuchadorVentana implements WindowListener {
	@Override
	public void windowActivated(WindowEvent arg0) {
		System.out.println( "Activated" );
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
		System.out.println( "Closed" );
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		System.out.println( "Closing " + arg0.getSource() );
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		System.out.println( "Deactivated" );
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		System.out.println( "Deiconified" );
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
		System.out.println( "Iconified" );
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		System.out.println( "Opened" );
	}
	
}

