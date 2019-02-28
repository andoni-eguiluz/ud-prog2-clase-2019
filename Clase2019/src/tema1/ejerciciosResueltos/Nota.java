package tema1.ejerciciosResueltos;

import tema1.Pianillo;

/** Clase Nota. Permite crear objetos que representan una nota dentro de una canción
 * con su nota musical, duración y octava (escala)
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class Nota {
	
	private static double tempo = 1.0; // Segundos por tempo
	
	/** Cambia el tempo general de la reproducción de notas
	 * @param nuevoTempo	Número de segundos que tarda cada tempo
	 */
	public static void setTempo( double nuevoTempo ) {
		tempo = nuevoTempo;
	}
	
	/// -------------- NO STATIC
	
	private String nombre;
	private int escala;
	private int numDuracion;
	private int denDuracion;
	
	/** Crea una nueva nota
	 * @param nombre	Nombre de la nota ("do", "do#", "re", "re#", etc.)
	 * @param escala	Escala/octava de la nota, entre 0 (más grave) y 8 (más aguda)
	 * @param numDuracion	Numerador de la duración de la nota (por ejemplo 1 para una nota negra)
	 * @param denDuracion	Denominador de la duración de la nota (por ejemplo 4 para una nota negra)
	 */
	public Nota(String nombre, int escala, int numDuracion, int denDuracion) {
		this.nombre = nombre;
		this.escala = escala;
		this.numDuracion = numDuracion;
		this.denDuracion = denDuracion;
	}

	/** Devuelve el nombre de la nota
	 * @return	Nombre ("do", "do#", "re", etc.)
	 */
	public String getNombre() {
		return nombre;
	}

	/** Modifica el nombre de la nota
	 * @param nombre	Nuevo nombre de la nota ("do", "do#", "re", etc.)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/** Devuelve la escala/octava de la nota
	 * @return	Octava de 0 (más grave) a 8 (más aguda)
	 */
	public int getEscala() {
		return escala;
	}

	/** Modifica la escala/octava de la nota
	 * @param escala	Octava de 0 (más grave) a 8 (más aguda)
	 */
	public void setEscala(int escala) {
		this.escala = escala;
	}

	/** Devuelve el numerador de la duración de la nota
	 * @return	Numerador de la duración, por ejemplo 1 para una negra (= 1/4)
	 */
	public int getNumDuracion() {
		return numDuracion;
	}

	/** Devuelve el denominador de la duración de la nota
	 * @return	Denominador de la duración, por ejemplo 4 para una negra (= 1/4)
	 */
	public int getDenDuracion() {
		return denDuracion;
	}
	
	/** Devuelve la duración de la nota
	 * @return	Duración en términos del tempo (por ejemplo 0.25 = negra = 1/4)
	 */
	public double getDuracion() {
		return 1.0 * numDuracion / denDuracion;
	}

	/** Modifica el numerador de la duración de la nota
	 * @param numDuracion	Numerador de la duración de la nota, p. ej. 1 para negra (1/4)
	 */
	public void setNumDuracion(int numDuracion) {
		this.numDuracion = numDuracion;
	}

	/** Modifica el denominador de la duración de la nota
	 * @param numDuracion	Denominador de la duración de la nota, p. ej. 4 para negra (1/4)
	 */
	public void setDenDuracion(int denDuracion) {
		this.denDuracion = denDuracion;
	}
	
	/** Modifica la duración de la nota
	 * @param numDuracion	Numerador de la duración de la nota, p. ej. 1 para negra (1/4)
	 * @param numDuracion	Denominador de la duración de la nota, p. ej. 4 para negra (1/4)
	 */
	public void setDuracion(int numDuracion, int denDuracion) {
		this.denDuracion = denDuracion;
	}
	
	/** Reproduce la nota, utilizando la clase {@link Pianillo}
	 */
	public void play() {
		double hz = Pianillo.frecuenciaNota( this.nombre, 4 );
		double[] onda = Pianillo.samplesDeNota( hz, tempo * numDuracion / denDuracion, 1.0 );
		Pianillo.mandaSonido( 0, onda );
	}
	
}
