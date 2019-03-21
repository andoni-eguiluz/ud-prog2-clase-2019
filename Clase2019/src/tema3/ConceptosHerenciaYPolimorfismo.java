package tema3;

import java.util.ArrayList;

/** Código en clase sobre conceptos de herencia y polimorfismo
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class ConceptosHerenciaYPolimorfismo {
	public static void main(String[] args) {
		Fruta f = new Fruta();
		f.hacerZumo();
		
		Comida c = new Fruta();
		// c.hacerZumo();  NO
		((Fruta)c).hacerZumo();
		
		// Comida c2 = new Comida();  // No se puede porque la clase es abstracta
		
		ArrayList<Comida> nevera = new ArrayList<>();
		for (Comida co : nevera) {
			co.comer();
			if (co instanceof Fruta) {
				// Fruta fruta = (Fruta) co;
				// fruta.hacerZumo();
				((Fruta)co).hacerZumo();
			}
		}
	}

}

abstract class Comida {
	String nombre;
	int calorias;
	public void comer() { System.out.println( "comer"); }
}

class Fruta extends Comida {
	int indiceCitrico;
	public void comer() { 
		System.out.println( "comer con deleite" );
	}
	void hacerZumo() { System.out.println( "zumo de" + nombre ); }
}

