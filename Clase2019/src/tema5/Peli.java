package tema5;

import java.io.Serializable;

/** Clase de ejemplo para pruebas de Java Collections - objeto "película" que solo tiene el título de la película
 * @author andoni.eguiluz at deusto.es
 */
public class Peli extends Object implements Comparable<Peli>, Serializable {
	private static final long serialVersionUID = 1L;  // Número de versión de este objeto (desde el punto de vista de la serialización)
	private String nombre;
	private int anyo;
	// private Genero genero;  // Si tuviéramos otros objetos tienen que implementar también Serializable

	public Peli(String nombre) {
		super();
		this.nombre = nombre;
		this.anyo = 0;
	}
	
	public Peli(String nombre, int anyo) {
		this.nombre = nombre;
		this.anyo = anyo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	@Override
	public int hashCode() {
		return nombre.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Peli)) return false;
		return nombre.equals( ((Peli)obj).nombre ); 
	}

	@Override
	public String toString() {
		return nombre + " (" + anyo + ")";
	}

	@Override
	public int compareTo(Peli o) {
		return nombre.compareTo( o.nombre );
	}
	
	
	// ========================================= 
	//  Métodos para gestión de pelis en fichero de texto
	// ========================================= 
	
	/** Método de conversión a string para escritura a fichero de texto
	 * @return
	 */
	public String toStringAFichero() {
		return nombre + "\t" + anyo;
	}
	
	/** Constructor indirecto que devuelve un objeto Peli partiendo de un string formateado
	 * con el nombre de la peli un tabulador y el año de la peli
	 * @param linea
	 * @return
	 */
	public static Peli leerDeLinea( String linea ) {
		int posi = linea.indexOf( "\t" );
		int anyo = Integer.parseInt( linea.substring( posi+1 ) );
		Peli peli = new Peli( linea.substring( 0, posi ), anyo );
		return peli;
	}	
	
}
