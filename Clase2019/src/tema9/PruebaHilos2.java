package tema9;

public class PruebaHilos2 {
	public static void main(String[] args) {
		// Crear hilo que visualice el tiempo en msg
		// una vez cada segundo, durante 10 segundos
		Thread h = new Thread( new MiBonitoRunnable() );
		h.start();
		for (int i=0; i<100; i++) {
			try { Thread.sleep(500); } catch (InterruptedException e) {}
			System.out.print( "a" );
		}
	}
}

class MiBonitoRunnable implements Runnable {
	public void run() {
		for (int j=0; j<10; j++) {
			try { Thread.sleep(1000); } catch (InterruptedException e) {}
			System.out.println( System.currentTimeMillis() );
		}
	}
}