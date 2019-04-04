package varios.ejemploMoon.datos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import varios.ejemploMoon.Mesa;

/** Clase para ficha de marca (texto libre en la ficha)
 * @author andoni.eguiluz at deusto.es
 */
public class Marca extends Ficha {
	
	/** Tipo de letra del texto de la marca */
	public static final Font TIPO_LETRA = new Font( "Courier New", Font.BOLD, 40 );
	/** Color del texto de la marca */
	public static final Color COLOR = Color.white;
	/** Desplazamiento x del texto con respecto al centro de la ficha */
	public static final int OFFSET_X = -10;
	/** Desplazamiento y del texto con respecto al centro de la ficha */
	public static final int OFFSET_Y = 10;
	
	private String texto;  // Texto que va en la ficha  (corto)
	
	/** Crea una nueva ficha marca
	 * @param x	Posición x (píxels)
	 * @param y	Posición y (píxels)
	 * @param tamanyo	Tamaño de ancho y de alto (píxels) - las fichas son cuadradas
	 * @param mesa	Mesa a la que pertenece la ficha
	 * @param texto	Texto a visualizar en la ficha
	 */
	public Marca(int x, int y, int tamanyo, Mesa mesa, String texto) {
		super(x, y, tamanyo, mesa);
		this.texto = texto;
		nombreImg = "/img/moon/enblanco.png";
	}
	
	/** Devuelve el texto de la marca
	 * @return	Texto actual
	 */
	public String getTexto() {
		return texto;
	}
	
	/** Cambia el texto de la marca
	 * @param texto	Nuevo texto
	 */
	public void setTexto(String texto) {
		this.texto = texto;
		dibuja();
	}
	
	@Override
	public boolean click( Point punto ) {
		// No hace nada
		return false;
	}
	
	@Override
	public void dibuja() {
		super.dibuja();  // Dibuja el fondo
		int posX = x+OFFSET_X;
		if (texto.length()>1) posX -= ((texto.length()-1)*14); // 14 pixels es el desplazamiento aprox que hay que hacer por cada carácter de más
		mesa.getVentana().dibujaTexto( posX, y+OFFSET_Y, texto, TIPO_LETRA, COLOR );
	}
	
	@Override
	public String toString() {
		return "Marca " + texto;
	}
	
	/* No reimplementamos equals porque consideramos las fichas iguales solo cuando son la misma. Si hubiera que considerar la igualdad por contenido, podría ser:
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Marca)) return false;
		return texto.equals( ((Marca)obj).texto );
	}
	*/
}
