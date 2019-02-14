package tema1.ejemplos;

/** Ejemplo de esbozo de clase instanciable: SensorPresencia
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class SensorPresencia {
	int cantidadPresencia; // num de 0 a 255 0=nada 255=todo se mueve
	boolean encendido;
	static int numSensoresEncendidos;
	int leerValor() {
		return cantidadPresencia;
	}
	void encender() {
		encendido = true;
		numSensoresEncendidos++;
	}
	public static void main(String[] args) {
		System.out.println( numSensoresEncendidos );
		System.out.println( "hola" );
		SensorPresencia s1 = new SensorPresencia();
		s1.encender();
		System.out.println( numSensoresEncendidos );
		System.out.println( s1.encendido );
		SensorPresencia s2 = new SensorPresencia();
		s2.encender();
		System.out.println( s2.encendido );
		System.out.println( numSensoresEncendidos );
	}
}
