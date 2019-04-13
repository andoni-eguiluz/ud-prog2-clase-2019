package temas78.ejemplosSwing;

import javax.swing.JOptionPane;

public class EjemploDeJOptionPane {

	public static void main(String[] args) {
		String dato = JOptionPane.showInputDialog( null, "¿Cómo te llamas?" );
		int respuesta = JOptionPane.showOptionDialog( null, dato + " ¿Estás seguro que quieres salir?", "Confirmar cierre",
			JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, 
			new String[] { "Sí", "No", "No lo tengo claro" },
			"Sí" );
		JOptionPane.showMessageDialog( null, "Tu respuesta es: " + respuesta );
	}

}
