package tema5;

public class Contador {
	private int contador;
	/** Construye un nuevo contador
	 * @param valorInicial	Valor inicial de ese contador
	 */
	public Contador( int valorInicial ) {
		contador = valorInicial;
	}
	/** Construye un nuevo contador con valor 1
	 */
	public Contador() {
		contador = 1;
	}
	/** Devuelve el valor actual del contador
	 * @return	Valor del contador
	 */
	public int get() {
		return contador;
	}
	/** Incrementa el valor del contador en una unidad
	 */
	public void inc() {
		contador++;
	}
	@Override
	public String toString() {
		return "" + contador;
	}
}
