package temas78.utils;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import javax.swing.*;


/** Clase de label gráfico mejorada con capacidad de escalarse, rotar, y transparentarse
 * (la escala no afecta al tamaño del label con lo cual puede verse recortado)
 * @author andoni.eguiluz at deusto.es
 */
public class JLabelGrafico extends JLabel {

	// Parte static (main de prueba)
	
		// Atributos temporales para el main de prueba
		private static boolean acaba = false;
		private static float hazTransparente = 1.0f;  // 1.0 para hacer opaco, 0.0 para hacer transparente
		private static double posX = 200;
		private static double posY = 100;
		private static double velX = 0;
		private static double velY = 0;
		private static double tiempoAnimacion = 0;
		private static int milisegundosEspera = 15;
	/** Método main de prueba que saca gráfico y lo va rotando y cambiando zoom de manera continua.
	 * Si se hace click en él, se hace un ciclo de desaparición o aparición cambiando su opacidad.
	 * Si se hace drag, se mueve en el panel haciendo una animación lineal de un segundo
	 * @param args	No utilizado
	 */
	public static void main(String[] args) {
		JLabelGrafico o = new JLabelGrafico( "/img/coches/coche.png", 300, 300, Math.PI, 4.0 );
		o.setLocation( (int) Math.round(posX), (int) Math.round(posY) );
		JPanel p = new JPanel();
		p.setLayout( null );
		p.add( o );
		// o.setBorder( BorderFactory.createLineBorder( Color.magenta, 1 )); // TODO Quitar para no ver el borde
		javax.swing.JFrame v = new javax.swing.JFrame();
		v.setDefaultCloseOperation( javax.swing.JFrame.DISPOSE_ON_CLOSE );
		v.getContentPane().add( p, BorderLayout.CENTER );
		v.getContentPane().add( new JLabel( "Click para transparencia, drag para movimiento" ), BorderLayout.SOUTH );
		v.setSize( 800, 600 );
		v.setVisible( true );
		boolean zoomCreciendo = false;
		v.addWindowListener( new WindowAdapter() { 
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) { 
				acaba = true; 
			} });
		o.addMouseListener( new MouseAdapter() { 
			@Override public void mouseClicked(java.awt.event.MouseEvent e) {
				if (hazTransparente==1.0f)
					hazTransparente = 0.0f;
				else
					hazTransparente = 1.0f;
			}
			private Point pPressed = null;
			@Override public void mousePressed(java.awt.event.MouseEvent e) {
				pPressed = e.getPoint();
			}
			@Override public void mouseReleased(java.awt.event.MouseEvent e) {
				if (!e.getPoint().equals(pPressed)) {
					posX = o.getX();  // vamos desde esta x...
					posY = o.getY();  // y esta y
					velX = (e.getX() - pPressed.x) / 1000.0 * milisegundosEspera;  // Con esta velocidad x (en un segundo recorre el drag de ratón)
					velY = (e.getY() - pPressed.y) / 1000.0 * milisegundosEspera;  // Y esta velocidad y
					tiempoAnimacion = 1.0;  // Durante 1 segundo de tiempo de animación
				}
			}
		} );
		while (!acaba) {
			// Actualiza la transparencia
			if (hazTransparente==0.0f && o.getOpacity()>0.0f) {
				o.setOpacity( o.getOpacity() - 0.01f );
			} else if (hazTransparente==1.0f && o.getOpacity()<1.0f) {
				o.setOpacity( o.getOpacity() + 0.01f );
			}
			// Actualiza el zoom en ciclo
			if (o.getZoom() < 0.1) 
				zoomCreciendo = true; 
			else if (o.getZoom() > 4.0) 
				zoomCreciendo = false;
			if (zoomCreciendo) 
				o.setZoom( o.getZoom()*1.02 );
			else 
				o.setZoom( o.getZoom()*0.98 );
			// Actualiza la rotación en ciclo
			o.incRotacion( 0.005 );
			// Actualiza la posición (si hay movimiento)
			if (tiempoAnimacion>0.0) {
				posX = o.getX() + velX;
				posY = o.getY() + velY;
				o.setLocation( (int) Math.round(posX), (int) Math.round(posY) );
				tiempoAnimacion -= milisegundosEspera/1000.0;
			}
			// Espera unos milisegundos
			try { Thread.sleep(milisegundosEspera); } catch (InterruptedException e) {}
		}
	}
	
	// Parte no static
	
	// la posición X,Y se hereda de JLabel
	protected ImageIcon icono;  // icono del objeto
	protected Image imagenObjeto;  // imagen para el escalado
	protected double radsRotacion = 0;  // 0 = no rotación. PI = media vuelta
	protected double zoom = 1.0;  // 1.0 = 100% zoom  (0.1 = 10%, 10.0 = 1000% ...)
	protected float opacity = 1.0f;  // 1.0 = 100% opaque / 0.0 = 0% opaque

	private static final long serialVersionUID = 1L;  // Para serialización
	
	/** Crea un nuevo label gráfico mejorado .<br>
	 * Si no existe el fichero de imagen, se crea un rectángulo blanco con borde rojo
	 * @param ficheroImagenObjeto	Nombre fichero donde está la imagen del objeto (carpeta utils/img)
	 * @param anchura	Anchura del label en píxels (positivo)
	 * @param altura	Altura del label en píxels (positivo)
	 * @param rotacion	Rotación en radianes (0=sin rotación, 2PI=vuelta completa. Sentido horario)
	 * @param zoom	Zoom a aplicar al gráfico (número positivo). Si es negativo, se calcula el zoom correspondiente a escalar la imagen al tamaño indicado
	 */
	public JLabelGrafico( String ficheroImagenObjeto, int anchura, int altura, double rotacion, double zoom ) {
		// Intentamos cargar el icono como un fichero
		File ficImagen = new File(ficheroImagenObjeto);
		if (ficImagen.exists()) { // Carga de fichero
			icono = new ImageIcon( ficheroImagenObjeto );
			setIcon( icono );
			imagenObjeto = icono.getImage();
		} else {
			// Y si el fichero no existe, cargamos el icono (como un recurso - vale tb del .jar)
	        URL imgURL = JLabelGrafico.class.getResource(ficheroImagenObjeto);
	        if (imgURL == null) {  // Si no existe el recurso, poner una marca de colores
	        	icono = null;
	    		setOpaque( true );
	    		setBackground( Color.white );
	    		setForeground( Color.blue );
	        	setBorder( BorderFactory.createLineBorder( Color.red ));
	        	setText( ficheroImagenObjeto );
	        } else {
	        	icono = new ImageIcon(imgURL);
	    		setIcon( icono );
				imagenObjeto = icono.getImage();
	        }
		}
    	setSize( anchura, altura );
    	if (zoom<0.0)
    		calcStartZoom( anchura, altura );
    	else
    		this.zoom = zoom;
	}
	
		// Método privado que calcula el zoom inicial que es el que permite ver el gráfico completo ajustado al tamaño indicado
		private void calcStartZoom( int anchura, int altura ) {
			if (icono==null) {
				zoom = 1.0;
			} else {
				double ratioAnchura = anchura * 1.0 / icono.getIconWidth();
				double ratioAltura = altura * 1.0 / icono.getIconHeight();
				zoom = Math.min(ratioAnchura, ratioAltura);
			}
		}
	
	/** Cambia el zoom por el zoom indicado
	 * @param zoom	Valor nuevo de zoom, positivo (0.1 = 10%, 1.0 = 100%, 2.0 = 200%...)
	 */
	public void setZoom( double zoom ) {
		if (zoom>0.0) {
			this.zoom = zoom;
			repaint();
		}
	}
	
	/** Devuelve el zoom actual
	 * @return	Zoom actual
	 */
	public double getZoom() {
		return zoom;
	}
	
	/** Cambia la opacidad por la indicada
	 * @param opacity	Valor nuevo de opacidad, entre 0.0 (transparente) y 1.0 (opaco)
	 */
	public void setOpacity( float opacity ) {
		if (opacity>=0.0 && opacity<=1.0) {
			this.opacity = opacity;
			repaint();
		}
	}
	
	/** Devuelve la opacidad actual
	 * @return	Opacidad actual
	 */
	public float getOpacity() {
		return opacity;
	}
	
	/** Cambia la rotación a los radianes indicados
	 * @param rotacion	Rotación en radianes (0=sin rotación, 2PI=vuelta completa. Sentido horario)
	 */
	public void setRotacion( double rotacion ) {
		radsRotacion = rotacion;
		repaint();
	}
	
	/** Cambia la rotación con el incremento en radianes indicado
	 * @param rotacion	Incremento de rotación en radianes (0=sin incremento, 2PI=vuelta completa. Sentido horario)
	 */
	public void incRotacion( double rotacion ) {
		radsRotacion += rotacion;
		if (radsRotacion > 2*Math.PI) radsRotacion -= (2 * Math.PI);
		repaint();
	}
	
	/** Devuelve la rotación actual
	 * @return	Rotación actual en radianes (0=sin rotación, 2PI=vuelta completa. Sentido horario)
	 */
	public double getRotacion() {
		return radsRotacion;
	}
	
	/** Cambia la rotación a los grados indicados
	 * @param rotacion	Rotación en grados (0=sin rotación, 360=vuelta completa. Sentido horario)
	 */
	public void setRotacionGrados( double rotacion ) {
		radsRotacion = rotacion / 180 * Math.PI;
		repaint();
	}
	
	/** Devuelve la rotación actual
	 * @return	Rotación actual en grados (0=sin rotación, 360=vuelta completa. Sentido horario)
	 */
	public double getRotacionGrados() {
		return radsRotacion / Math.PI * 180;
	}


	// Dibuja este componente de una forma no habitual (cambiando el gráfico que se pinta)
	@Override
	protected void paintComponent(Graphics g) {
		if (imagenObjeto==null || icono==null) {
			super.paintComponent(g);
		} else {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR); // Configuración para mejor calidad del gráfico escalado
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	
	        g2.rotate( radsRotacion, getWidth()/2, getHeight()/2 );  // Incorporar al gráfico la rotación definida
	        g2.setComposite(AlphaComposite.getInstance( AlphaComposite.SRC_OVER, opacity ) ); // Incorporar la transparencia definida
	        int anchoDibujado = (int)Math.round(icono.getIconWidth()*zoom);  // Calcular las coordenadas de dibujado con el zoom, siempre centrado en el label
	        int altoDibujado = (int)Math.round(icono.getIconHeight()*zoom);
	        int difAncho = (getWidth() - anchoDibujado) / 2;  // Offset x para centrar
	        int difAlto = (getHeight() - altoDibujado) / 2;     // Offset y para centrar
	        g2.drawImage( imagenObjeto, difAncho, difAlto, anchoDibujado, altoDibujado, null);  // Dibujar la imagen con el tamaño calculado tras aplicar el zoom
	        g2.rotate( Math.PI*2-radsRotacion, getWidth()/2, getHeight()/2 );
		}
	}

}
