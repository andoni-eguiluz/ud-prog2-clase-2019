package tema1.ejerciciosResueltos;

import java.awt.Point;

/** Clase que permite gestionar un grupo de pelotas
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class GrupoPelotas {
	private Pelota[] grupo;
	private int numPelotas;
	
	/** Crea un grupo de pelotas vacío
	 * @param numMax	Número máximo de pelotas del grupo (debe ser positivo)
	 */
	public GrupoPelotas( int numMax ) {
		grupo = new Pelota[numMax];
		numPelotas = 0;
	}
	
	/** Devuelve el número de pelotas actualmente en el grupo
	 * @return	Número de pelotas en el grupo
	 */
	public int size() {
		return numPelotas;
	}
	
	/** Devuelve el número máximo de pelotas que caben en el grupo
	 * @return	Número máximo
	 */
	public int tamMaximo() {
		return grupo.length;
	}
	
	/** Añade una pelota al grupo
	 * @return	true si se añade correctamente, false si no cabe (no se añade)
	 */
	public boolean addPelota( Pelota pelota ) {
		if (numPelotas==grupo.length) {
			return false;
		} else {
			grupo[numPelotas] = pelota;
			numPelotas++;
			return true;
		}
	}
	
	/** Quita una pelota del grupo
	 * @param pelota	Pelota a quitar. Si está en el grupo, se elimina (EXACTAMENTE ese objeto, no otra pelota con las mismas coordenadas)
	 */
	public void removePelota( Pelota pelota ) {
		for (int i=0; i<numPelotas; i++) {
			Pelota p = grupo[i];
			if (p==pelota) {
				// Movemos todas las pelotas siguientes una posición hacia la izquierda
				for (int j=i+1; j<numPelotas; j++) {
					grupo[j-1] = grupo[j];
				}
				numPelotas--;  // Una pelota menos
				return;
			}
		}
	}
	
	/** Devuelve una pelota del grupo
	 * @param num	Número de la pelota (en orden de añadido. 0 es la primera). Debe ser inferior a size().
	 * @return	Pelota en esa posición del grupo
	 */
	public Pelota getPelota( int num ) {
		return grupo[num];
	}

	/** Comprueba si alguna de las pelotas corresponde a una coordenada dada de la pantalla
	 * @param punto	Coordenada de pantalla a comprobar
	 * @return	Pelota que tiene esta coordenada dentro. Si hay varias, se devuelve aquella que tenga 
	 * más cerca su centro del punto pulsado. Si no hay ninguna, se devuelve null.
	 */
	public Pelota hayPelotaPulsadaEn( Point punto ) {
		Pelota pelotaPulsada = null;
		double distanciaMinima = Double.MAX_VALUE;
		for (int i=0; i<numPelotas; i++) {
			Pelota p = grupo[i];
			double dist = Math.sqrt( Math.pow( p.getX()-punto.x, 2) + Math.pow( p.getY()-punto.y, 2) );
			if (dist <= p.getRadio() && dist < distanciaMinima) {   // Pulsación dentro de la pelota
				pelotaPulsada = p;
				distanciaMinima = dist;
			}
		}
		return pelotaPulsada;
	}
	
	/** Informa si una pelota ya existe en el grupo
	 * @param p	Pelota a comprobar
	 * @return	true si ya hay una pelota en esas mismas coordenadas, false en caso contrario
	 */
	public boolean yaExistePelota( Pelota p ) {
		for (int i=0; i<numPelotas; i++) {
			if (p.equals(grupo[i])) return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return grupo.toString();  // Esto no funciona bien con arrays, pero sí con ArrayList
	}
	
}
