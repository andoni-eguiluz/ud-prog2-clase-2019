package tema9;

public class PruebaThreads {
	public static void main(String[] args) {
		MiHilo h = new MiHilo();
		h.start();  // ejecuta h.run()
		MiRunnable r = new MiRunnable();
		Thread h2 = new Thread( r );
		h2.start(); // -> ejecuta r.run()
		// Hilo con clase interna y anónima
		(new Thread( new Runnable() {
			public void run() {
				System.out.println( "Soy el runnable anónimo!");
			}
		} )).start();
		System.out.println( "Soy el main!");
	}
}

class MiHilo extends Thread {
	@Override
	public void run() {
		System.out.println( "Soy el hilo!");
	}	
}

class MiRunnable implements Runnable {
	@Override
	public void run() {
		System.out.println( "Soy el código de runnable!");
	}
}
