package tema9;

import javax.swing.SwingUtilities;

// Modo 1: Heredando de thread

public class ThreadHeredando extends Thread {

	private char miCaracter;

	public ThreadHeredando( char c ) {
		miCaracter = c;
	}

	public void run() { 
		// while (true)
		for( int i=0; i<100; i++ ) 
			System.out.print( miCaracter ); 
	}

	public static void main( String[] pars ) { 
		Thread t1 = new ThreadHeredando( '1' ); 
		Thread t2 = new ThreadHeredando( '2' ); 
		t1.start(); // Ejecuta t1.run() 
		t2.start(); // Ejecuta t2.run() 
		System.out.println("Ahora están los 2 en marcha"); 
		System.out.println(); 
		System.out.println( "Final del main" ); 
	}

}
