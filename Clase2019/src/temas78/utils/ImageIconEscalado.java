package temas78.utils;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

/** Clase para crear un imageicon con el tamaño deseado (el imageicon original tiene el tamaño de la imagen que se carga)
 * @author andoni.eguiluz at deusto.es
 */
@SuppressWarnings("serial")
public class ImageIconEscalado extends ImageIcon {

	// Prueba del imageicon escalado
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		f.setVisible( true );
		f.getContentPane().add( new JLabel( new ImageIconEscalado( "bin/img/coches/coche.png", 120, 120) ), BorderLayout.SOUTH );
		f.pack();
		f.setVisible( true );
	}
	
	/** Crea un imageicon escalado de acuerdo a la anchura y altura indicadas
	 * @param fichero	Fichero de imagen
	 * @param anchura	Anchura a escalar (en píxels)
	 * @param altura	Altura a escalar (en píxels)
	 */
	public ImageIconEscalado( String fichero, int anchura, int altura ) {
		super( getImageEscalada( new ImageIcon(fichero), anchura, altura ) );
	}
	
	/** Crea un imageicon escalado de acuerdo a la anchura y altura indicadas
	 * @param recurso	URL de imagen
	 * @param anchura	Anchura a escalar (en píxels)
	 * @param altura	Altura a escalar (en píxels)
	 */
	public ImageIconEscalado( URL recurso, int anchura, int altura ) {
		super( getImageEscalada( new ImageIcon(recurso), anchura, altura ) );
	}
	
	/** Devuelve un imageicon escalado partiendo de cualquier imageicon
	 * @param ii	ImageIcon original
	 * @param ancho	Anchura a escalar (en píxels)
	 * @param alto	Altura a escalar (en píxels)
	 * @return	Nuevo imageicon con la escala indicada
	 */
	public static ImageIcon getImageIconEscalado( ImageIcon ii, int ancho, int alto ) {
		Image image = ii.getImage();
		Image newimg = image.getScaledInstance( ancho, alto, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}
	
	/** Devuelve una imagen escalada partiendo de cualquier imageicon
	 * @param ii	ImageIcon original
	 * @param ancho	Anchura a escalar (en píxels)
	 * @param alto	Altura a escalar (en píxels)
	 * @return	Nuevo image con la escala indicada
	 */
	public static Image getImageEscalada( ImageIcon ii, int ancho, int alto ) {
		Image image = ii.getImage();
		return image.getScaledInstance( ancho, alto, java.awt.Image.SCALE_SMOOTH);
	}

	/** Devuelve una imagen escalada partiendo de cualquier image
	 * @param ii	Imagen original
	 * @param ancho	Anchura a escalar (en píxels)
	 * @param alto	Altura a escalar (en píxels)
	 * @return	Nuevo image con la escala indicada
	 */
	public static Image getImageEscalada( Image image, int ancho, int alto ) {
		return image.getScaledInstance( ancho, alto, java.awt.Image.SCALE_SMOOTH);
	}
	
}
