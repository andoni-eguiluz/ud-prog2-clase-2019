package temas78.ejemplosSwing.listeners;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;

/** Ejemplo de uso de escuchador de ratón MouseListener, ActionListener y WindowListener - usando la misma clase como escuchadora
 * @author andoni.eguiluz at deusto.es
 */
@SuppressWarnings("serial")
public class VentanaEventos extends JFrame implements ActionListener, MouseListener, WindowListener
{
	JPanel panelSuperior;
	JPanel panelInferior;
	JPanel panelBotones;
	JButton botonAzul;
	JButton botonVerde;
	JButton botonRojo;
	JButton botonLimpiar;
	JButton botonSalir;
	JLabel etiqueta;
	
	JList<String> listaEventos;
	DefaultListModel<String> datosLista;
	JScrollPane scrollLista;
	
	public VentanaEventos()
	{		
		panelSuperior = new JPanel();
		panelInferior = new JPanel();
		panelBotones = new JPanel();
		botonAzul = new JButton("Azul");
		botonVerde = new JButton("Verde");
		botonRojo = new JButton("Rojo");
		botonLimpiar = new JButton("Limpiar");
		botonSalir = new JButton("Salir");
		etiqueta = new JLabel("Panel que captura los eventos del ratón");
		datosLista = new DefaultListModel<String>();
		listaEventos = new JList<String>(datosLista);
		scrollLista = new JScrollPane(listaEventos);
		panelBotones.add(botonAzul);
		panelBotones.add(botonVerde);
		panelBotones.add(botonRojo);
		panelBotones.add(botonLimpiar);
		panelBotones.add(botonSalir);
		
		panelSuperior.add(etiqueta);
		panelInferior.setLayout(new BorderLayout());
		panelInferior.add(scrollLista, "Center");
		panelInferior.add(panelBotones, "South");
	
		this.getContentPane().setLayout(new GridLayout(2,1));
		this.getContentPane().add(panelSuperior);
		this.getContentPane().add(panelInferior);
		// Asignamos escuchadores a todos los elementos 
		// que puedan recibir eventos
		// Ponemos como escuchador de todos ellos a la 
		// propia ventana
		botonAzul.addActionListener(this);
		botonVerde.addActionListener(this);
		botonRojo.addActionListener(this);
		botonLimpiar.addActionListener(this);
		botonSalir.addActionListener(this);
		panelSuperior.addMouseListener(this);
		this.addWindowListener(this);
		
		this.setTitle("Ventana con Eventos");
		this.setSize(500,400);
		this.setVisible(true);
	}
	// Método del interfaz ActionListener
	public void actionPerformed(ActionEvent e)
	{
		JButton botonPulsado = (JButton)e.getSource();
		if (botonPulsado == botonAzul)
		{
			panelSuperior.setBackground(Color.blue);
		}
		if (botonPulsado == botonVerde)
		{
			panelSuperior.setBackground(Color.green);
		}
		if (botonPulsado == botonRojo)
		{
			panelSuperior.setBackground(Color.red);
		}
		if (botonPulsado == botonLimpiar)
		{
			datosLista.clear();
			panelSuperior.setBackground(Color.lightGray);
		}
		if (botonPulsado == botonSalir)
		{
			System.exit(0);
		}
	}
	// Los 5 métodos del interfaz MouseListener
	public void mouseClicked(MouseEvent e)
	{
		String linea = "Evento CLICK, ";
		if (e.getModifiers() == MouseEvent.BUTTON1_MASK)
		{
			linea = linea + "con BOTON IZQUIERDO ";
		}
		if (e.getModifiers() == MouseEvent.BUTTON2_MASK)
		{
			linea = linea + "con BOTON DEL CENTRO ";
		}
		if (e.getModifiers() == MouseEvent.BUTTON3_MASK)
		{
			linea = linea + "con BOTON DERECHO ";
		}
		linea = linea + "en coordenadas (" + e.getX() + "," + e.getY() + ")";
		datosLista.addElement(linea);
		listaEventos.ensureIndexIsVisible( datosLista.size()-1 );  // Esto asegura que se vea en el scroll el elemento recién añadido
	}
	public void mouseEntered(MouseEvent e)
	{
		String linea = "Evento ENTRAR AL PANEL en coordenadas (" + e.getX() + "," + e.getY() + ")";
		datosLista.addElement(linea);
		listaEventos.ensureIndexIsVisible( datosLista.size()-1 );  // Esto asegura que se vea en el scroll el elemento recién añadido
	}
	public void mouseExited(MouseEvent e)
	{
		String linea = "Evento SALIR DEL PANEL en coordenadas (" + e.getX() + "," + e.getY() + ")";
		datosLista.addElement(linea);
		listaEventos.ensureIndexIsVisible( datosLista.size()-1 );  // Esto asegura que se vea en el scroll el elemento recién añadido
	}
	public void mousePressed(MouseEvent e)
	{
		// Este método se invoca cuando se pulsa el botón 
		// del ratón
	}
	public void mouseReleased(MouseEvent e)
	{
		// Este método se invoca cuando se libera el 
		// botón del ratón
	}
	//Los 7 métodos del interfaz WindowListener
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}
	
	public void windowClosed(WindowEvent e){ }
	public void windowIconified(WindowEvent e){ }
	public void windowOpened(WindowEvent e){ }
	public void windowActivated(WindowEvent e){ }
	public void windowDeactivated(WindowEvent e){ }
	public void windowDeiconified(WindowEvent e){ }
	public static void main(String[] args)
	{
		new VentanaEventos();
	}
}
