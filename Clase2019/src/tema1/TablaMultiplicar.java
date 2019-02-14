package tema1;

public class TablaMultiplicar {

	public static void main(String[] args) {
		tablaSencilla();
		tablaConFormato();
	}
	
	private static void tablaSencilla() {
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				System.out.println( i + " x " + j + " = " + i*j ); 
			}
		}
	}

	private static void tablaConFormato() {
		cabecera();
		for (int i=0; i<10; i++) {
			System.out.print( "Tabla de " + i ); 
			for (int j=0; j<10; j++) {
				String espacios = " ";
				if (i*j<10) espacios = "  ";
				System.out.print( espacios + i*j ); 
			}
			System.out.println();
		}
	}
	
	private static void cabecera() {
		System.out.print( "          " );
		for (int j=0; j<10; j++) {
			System.out.print( "  " + j ); 
		}
		System.out.println();
		System.out.println( "           -----------------------------" );
	}

}
