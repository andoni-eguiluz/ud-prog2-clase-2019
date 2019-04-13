package tema9;

class Persona {
	private String nombre;
	Persona( String nombre ) {
		this.nombre = nombre;
	}
	synchronized public void saluda( Persona amigo ) {
    	// Saluda = sonreir + que él/ella sonría
    	sonrie( amigo );
    	amigo.sonrie( this );
    }
	synchronized public void sonrie( Persona p ) {
    	System.out.println( this + " sonríe a " + p );
    }
    public String toString() {
    	return nombre;
    }
}

public class EjemploProblemasInterbloqueo implements Runnable {
	private Persona persona,amigo;
	public EjemploProblemasInterbloqueo( Persona persona, Persona amigo ) {
		this.persona = persona;
		this.amigo = amigo;
	}	
	@Override
	public void run() {
		for (int i = 0; i<1000; i++) {
			System.out.print( "(" + i + ")  " );
			persona.saluda( amigo );
		}
	}
	public static void main( String[] s ) {
		Persona persona1 = new Persona( "Marta" );
		Persona persona2 = new Persona( "Luis" );
		Thread t1 = new Thread( new EjemploProblemasInterbloqueo( persona1, persona2 ) );
		Thread t2 = new Thread( new EjemploProblemasInterbloqueo( persona2, persona1 ) );
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
		}
	}
}
