package tema4.runnerConInterface2;

/** Interfaz para objetos visuales que pueden chocar
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public interface Chocable {
	
	/** Devuelve la envolvente (forma geométrica) con la que choca un objeto
	 * @return	Envolvente de choque
	 */
	public Envolvente getEnvolvente();
	
}
