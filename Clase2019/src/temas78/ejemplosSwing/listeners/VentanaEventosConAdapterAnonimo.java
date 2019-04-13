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
import java.awt.event.*;

/** Ejemplo de uso de escuchador de ratón MouseListener, ActionListener y WindowListener - usando Adapter anónimo
 * @author andoni.eguiluz at deusto.es
 */
@SuppressWarnings("serial")
public class VentanaEventosConAdapterAnonimo extends JFrame implements ActionListener
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
	
	public VentanaEventosConAdapterAnonimo()
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

		// Asignamos escuchadores a todos los elementos que puedan recibir eventos
		// Ponemos como escuchador de todos ellos a la propia ventana
		botonAzul.addActionListener(this);
		botonVerde.addActionListener(this);
		botonRojo.addActionListener(this);
		botonLimpiar.addActionListener(this);
		botonSalir.addActionListener(this);
		
		// Escuchador con adapter anónimo
		// Es como crear una clase interna sin nombre, extendiendo una dada (MouseAdapter)
		// y a la vez se crea y devuelve un objeto único de esa clase (que será el escuchador,
		// también sin nombre)
		panelSuperior.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String linea = "Evento CLICK, ";
				if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
					linea = linea + "con BOTON IZQUIERDO ";
				} else if (e.getModifiers() == MouseEvent.BUTTON2_MASK) {
					linea = linea + "con BOTON DEL CENTRO ";
				} else if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
					linea = linea + "con BOTON DERECHO ";
				}
				linea = linea + "en coordenadas (" + e.getX() + "," + e.getY() + ")";
				datosLista.addElement(linea);
				listaEventos.ensureIndexIsVisible( datosLista.size()-1 );  // Esto asegura que se vea en el scroll el elemento recién añadido
			}
			public void mouseEntered(MouseEvent e) {
				String linea = "Evento ENTRAR AL PANEL en coordenadas (" + e.getX() + "," + e.getY() + ")";
				datosLista.addElement(linea);
				listaEventos.ensureIndexIsVisible( datosLista.size()-1 );  // Esto asegura que se vea en el scroll el elemento recién añadido
			}
			public void mouseExited(MouseEvent e) {
				String linea = "Evento SALIR DEL PANEL en coordenadas (" + e.getX() + "," + e.getY() + ")";
				datosLista.addElement(linea);
				listaEventos.ensureIndexIsVisible( datosLista.size()-1 );  // Esto asegura que se vea en el scroll el elemento recién añadido
			}
		});
		
		// Otro escuchador con adapter anónimo
		this.addWindowListener( new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.setTitle("Ventana con Eventos");
		this.setSize(500,400);
		this.setVisible(true);
	}
	
	// Método del interfaz ActionListener
	public void actionPerformed(ActionEvent e)
	{
		JButton botonPulsado = (JButton)e.getSource();
		if (botonPulsado == botonAzul) {
			panelSuperior.setBackground(Color.blue);
		} else if (botonPulsado == botonVerde) {
			panelSuperior.setBackground(Color.green);
		} else if (botonPulsado == botonRojo) {
			panelSuperior.setBackground(Color.red);
		} else if (botonPulsado == botonLimpiar) {
			datosLista.clear();
			panelSuperior.setBackground(Color.lightGray);
		} else if (botonPulsado == botonSalir) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args)
	{
		new VentanaEventosConAdapter();
	}
	
}
