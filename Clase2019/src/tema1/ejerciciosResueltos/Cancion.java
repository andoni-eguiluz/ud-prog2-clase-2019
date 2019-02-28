package tema1.ejerciciosResueltos;

import tema1.Pianillo;

/** Clase canción. Permite crear objetos que representan una canción completa en función de sus notas
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class Cancion {

	private static final int NUM_MAX_NOTAS = 2000;

	private String nombre;  // Nombre de la canción
	private Nota[] notas;   // Array de notas de la canción
	private int numNotas;   // Número de notas utilizadas de ese array
	
	/** Construye una nueva canción vacía (sin notas)
	 */
	public Cancion( String nombre ) {
		this.nombre = nombre;
		notas = new Nota[ NUM_MAX_NOTAS ];
		numNotas = 0;
	}
	
	/** Añade notas a una canción
	 * @param cancion	Notas a añadir en un string separadas por espacios. Por ejemplo "do re mi do"
	 *               	Se añaden por defecto notas de duración 1/4 y de escala 4
	 */
	public void addNotas( String cancion ) {
		String[] listaNotas = cancion.split(" ");
		// int i = Integer.parseInt( string );
		// TODO Pendiente
	}
	
	/** Reproduce una canción completa, reproduciendo en secuencia todas sus notas, 
	 * a través de la utilidad {@link Pianillo}
	 */
	public void play() {
		for (int i=0; i<numNotas; i++) {
			notas[i].play();
		}
	}
}
