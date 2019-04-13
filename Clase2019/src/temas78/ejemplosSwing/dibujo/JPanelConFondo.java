package temas78.ejemplosSwing.dibujo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class JPanelConFondo extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage imagenOriginal;
	public JPanelConFondo( String nombreImagenFondo ) {
		URL imgURL = getClass().getResource(nombreImagenFondo);
		try {
			imagenOriginal = ImageIO.read( imgURL );
		} catch (IOException e) {
		}
	}
	protected void paintComponent(Graphics g) {
		System.out.println( "Llamando pc");
		//super.paintComponent(g);  en vez de esto...
		Rectangle espacio = g.getClipBounds();  // espacio de dibujado del panel
		Graphics2D g2 = (Graphics2D) g;  // El Graphics realmente es Graphics2D
		// Código para que el dibujado se reescale al área disponible
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		// Dibujado
		g2.drawImage(imagenOriginal, 0, 0, (int)espacio.getWidth(), 
		             (int)espacio.getHeight(), null);
		g2.setColor( Color.blue );
		g2.setStroke( new BasicStroke( 3.5f ));
		g2.drawLine( 0, 0, 100, 100 );
	}
	/** Método de prueba del panel
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame prueba = new JFrame();
		prueba.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		prueba.setSize( 600, 500 );
		JPanel panelFondo = new JPanelConFondo( "/img/bicho.png" );
		panelFondo.setLayout( new BorderLayout() );
		JButton botonPrueba = new JButton( "Botón de prueba encima de panel con imagen" );
		panelFondo.add( botonPrueba, "North" );
		prueba.getContentPane().add( panelFondo, "Center" );
		MiBoton b2 = new MiBoton( "Prueba" );
		panelFondo.add( b2, "South" );
		prueba.setVisible( true );
		b2.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Graphics2D g2 = (Graphics2D) b2.getGraphics();
				g2.setColor( Color.red );
				g2.drawOval( 0, 0, b2.getWidth(), b2.getHeight() );
			}
		});
	}
	
	private static class MiBoton extends JButton {
		public MiBoton( String texto ) {
			super(texto);
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor( Color.red );
			g2.setStroke( new BasicStroke( 2.5f ) );
			g2.drawLine( 0, 0, getWidth(), getHeight());
			g2.drawLine( 0, getHeight(), getWidth(), 0);
		}
		
	}
	
}
