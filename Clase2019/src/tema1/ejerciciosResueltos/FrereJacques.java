package tema1.ejerciciosResueltos;

import tema1.Pianillo;

public class FrereJaques {

	public static void main(String[] args) {
		// Prueba 1: Una minicanción creada solo con notas:
		Nota.setTempo( 1.0 );
		Nota[] cancion = new Nota[4];
		cancion[0] = new Nota( "do", 4, 1, 4 );
		cancion[1] = new Nota( "re", 4, 1, 4 );
		cancion[2] = new Nota( "mi", 4, 1, 4 );
		cancion[3] = new Nota( "do", 4, 1, 4 );
		for (int i=0; i<4; i++) {
			cancion[i].play();
		}
		// Se podría hacer un "for each" pero veremos momentos mejores para hacerlo
		// for (Nota nota : cancion) {
		// 	   nota.play();
		// }

		// Prueba 2: Una canción creada con un objeto contenedor Cancion
		// Lo que queremos hacer con la clase canción:
		Cancion fj = new Cancion();
		// Posibilidad 1: fj.addNotas( "do re mi do" ); // Duración = 1/4 Escala = 4
		// Posibilidad 2: fj.addNotas( "1/4do4 1/4re4 1/4mi4 1/4do4" );
		// Posibilidad 3: fj.addNotas( "do re mi do", "1/4 1/4 1/4 1/4", "4 4 4 4");
		fj.play();
		
		Pianillo.closeCuandoSeAcabe();
	}

}
