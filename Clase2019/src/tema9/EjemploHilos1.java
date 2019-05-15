package tema9;

// 2 hilos que escriban 1-10 y a-z (1 por segundo)
public class EjemploHilos1 {
	public static void main(String[] args) {
		Runnable r1 = new Runnable1();
		Thread hilo1 = new Thread( r1 );
		hilo1.start();
		Thread hilo2 = new Thread2();
		hilo2.start();
		// 3 hilos
		for (int i=0; i<20; i++) {
			System.out.println( "funcionando" );
			try { Thread.sleep(1000); } catch (InterruptedException e) {}
		}
	}
	
	static class Runnable1 implements Runnable {
		@Override
		public void run() {
			for (int num=1; num<=10; num++) {
				System.out.println( num );
				try { Thread.sleep(1000); } catch (InterruptedException e) {}
			}
		}
	}
	
	static class Thread2 extends Thread {
		@Override
		public void run() {
			for (char c='a'; c<='z'; c = (char) (c+1)) {
				System.out.println( c );
				try { Thread.sleep(500); } catch (InterruptedException e) {}
			}
		}
	}
}
