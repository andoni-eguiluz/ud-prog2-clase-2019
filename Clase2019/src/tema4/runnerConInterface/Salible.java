package tema4.runnerConInterface;

import java.util.ArrayList;

import tema3.VentanaGrafica;

public interface Salible {
	boolean seSalePorLaIzquierda( VentanaGrafica v );
	void sal( ArrayList<ObjetoEspacial> lElementos );
}
