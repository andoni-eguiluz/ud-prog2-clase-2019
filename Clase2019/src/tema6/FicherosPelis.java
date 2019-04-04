package tema6;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import tema5.Peli;

/** Ejemplo de gestión de ficheros, utilizando la clase Peli
 * @author andoni.eguiluz at deusto.es
 */
public class FicherosPelis {
	public static void main(String[] args) {
		ArrayList<Peli> l = new ArrayList<Peli>();
		init( l );
		System.out.println( "Lista antes de guardar:  " + l );
		
		// Con ficheros de texto
		guardarFicheroTexto( l, "pelis.txt" );
		l.clear();
		cargarFicheroTexto( l, "pelis.txt" );
		System.out.println( "Lista después de cargar: " + l );
		
		// Con ficheros binarios (serializados)
		guardarFicheroBinario( l, "pelis.dat" );
		l.clear();
		cargarFicheroBinario( l, "pelis.dat" );
		System.out.println( "Lista después de cargar: " + l );
	}
	
	private static void init( ArrayList<Peli> l ) {
		Peli p1 = new Peli( "Avengers Endgame", 2019 );
		Peli p2 = new Peli( "Green Book", 2018 );
		Peli p3 = new Peli( "La Favorita", 2018 );
		Peli p4 = new Peli( "Roma", 2018 );
		l.addAll( Arrays.asList( p1, p2, p3, p4 ) );
	}
	
	private static void guardarFicheroTexto( ArrayList<Peli> l, String nombreFic ) {
		// Stream - flujos de datos
		// En este caso de salida
		try {
			PrintStream fS = new PrintStream( nombreFic );
			for (Peli peli : l) {
				fS.println( peli.toStringAFichero() ); // Ojo: esto sale como esté definido en ... .toString()
			}
			fS.close();
		} catch (IOException e) {
			System.out.println( "No ha sido posible crear el fichero." );
		}
	}
	
	private static void cargarFicheroTexto( ArrayList<Peli> l, String nombreFic ) {
		try {
			Scanner fE = new Scanner( new FileInputStream( nombreFic ) );
			while (fE.hasNext()) {
				String linea = fE.nextLine();
				// Trabajo con cada línea
				try {
					Peli peli = Peli.leerDeLinea( linea );
					l.add( peli );
				} catch (Exception e) {
					System.out.println( "Problema en la línea " + linea );
				}
			}
			fE.close();
		} catch (IOException e) {
			System.out.println( "No ha sido posible leer el fichero." );
		}
	}
	
	private static void guardarFicheroBinario( ArrayList<Peli> l, String nombreFic ) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( nombreFic ) );
			oos.writeObject( l ); // Serializa - uno tras otro guarda todos los objetos incluidas
			oos.close();
		} catch (IOException e) {
			System.out.println( "Error en escritura de fichero " + nombreFic );
		}
	}
	
	private static void cargarFicheroBinario( ArrayList<Peli> l, String nombreFic ) {
		try {
			ObjectInputStream ois = new ObjectInputStream( new FileInputStream( nombreFic ) );
			ArrayList<Peli> lCargada = (ArrayList<Peli>) ois.readObject(); // Cargamos un objeto nuevo (ojo que tiene que concordar con lo que escribimos EXACTAMENTE)
			ois.close();
			l.addAll( lCargada ); // Modificamos el objeto original (para devolverlo tras la llamada)
		} catch (IOException | ClassNotFoundException e) {
			System.out.println( "Error en lectura de fichero " + nombreFic );
		}
	}
	
}
