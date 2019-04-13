package temas78.utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/** Clase que extiende a JLabel para permitir gráficos con escala y rotación
 * @author andoni.eguiluz at deusto.es
 */
@SuppressWarnings("serial")
public class JLabelEscalableRotable extends JLabel {
	double rotacion;
	int ancho, alto;
	Image imagenObjeto;
	/** Crea un objeto JLabel con escala y rotación
	 * @param objetoGrafico	Gráfico para el label
	 * @param ancho	Píxels de ancho
	 * @param alto	Píxels de alto
	 * @param rotacionInicial
	 */
	public JLabelEscalableRotable( ImageIcon objetoGrafico, int ancho, int alto, double rotacionInicial ) {
		super( objetoGrafico );
		this.ancho = ancho;
		this.alto = alto;
		rotacion = rotacionInicial;
		imagenObjeto = objetoGrafico.getImage();
	}
	@Override
	protected void paintComponent(Graphics g) {  // Dibujado con escala y rotación
		Graphics2D g2 = (Graphics2D) g;  // El Graphics realmente es Graphics2D
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	
        g2.rotate( rotacion, ancho/2, alto/2 );
        g2.drawImage( imagenObjeto, 0, 0, ancho, alto, null);
        // Si se quieren dibujar los rectángulos interior y exterior:
		// setBorder( BorderFactory.createLineBorder( Color.red ));
        // g2.setColor( Color.white );
        // g2.setStroke(new BasicStroke(3));
        // g2.drawRect( xInicioChoque, yInicioChoque, xFinChoque-xInicioChoque, yFinChoque-yInicioChoque );
	}
}
