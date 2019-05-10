package tema9;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class EjemploAudio {
	
	/** Lanza un audio indicado en un fichero wav
	 * @param ficheroWav	Path correcto del fichero wav indicado
	 */
	public static void lanzaAudio( String ficheroWav ) {
	    int BUFFER_SIZE = 128000;
	    AudioInputStream flujoAudio = null;
	    AudioFormat formatoAudio = null;
	    SourceDataLine lineaDatosSonido = null;
	    File ficSonido = null;
        try {
            ficSonido = new File(ficheroWav);
            flujoAudio = AudioSystem.getAudioInputStream(ficSonido);
            formatoAudio = flujoAudio.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, formatoAudio);
            lineaDatosSonido = (SourceDataLine) AudioSystem.getLine(info);
            lineaDatosSonido.open(formatoAudio);
            lineaDatosSonido.start();
            int bytesLeidos = 0;
            byte[] bytesAudio = new byte[BUFFER_SIZE];
            while (bytesLeidos != -1) {
                try {
                    bytesLeidos = flujoAudio.read(bytesAudio, 0, bytesAudio.length);
                } catch (IOException e) { }
                if (bytesLeidos >= 0) {
                    lineaDatosSonido.write(bytesAudio, 0, bytesLeidos);
                }
            }
        } catch (Exception e) {
        	// Excepción si el fichero es nulo, erróneo, o wav incorrecto
        }
    	if (lineaDatosSonido != null) {
            lineaDatosSonido.drain();
            try {
            	lineaDatosSonido.close();
                flujoAudio.close();
            } catch (Exception e) {}
    	}
	}
	
	public static void main(String[] args) {
		pruebaSinHilos();
		pruebaConHilos();
	}
	
	private static void pruebaSinHilos() {
		lanzaAudio( "src/tema9/timbre.wav" );
		lanzaAudio( "src/tema9/aplauso.wav" );
		System.out.println( "Sólo se ve después de los dos audios");
	}

	private static void pruebaConHilos() {
		lanzaAudioEnHilo( "src/tema9/timbre.wav" );
		try { Thread.sleep(2000); } catch (Exception e) {}
		lanzaAudioEnHilo( "src/tema9/aplauso.wav" );
		System.out.println( "Se lanza un audio, el otro en 2 segundos y se acaba");
	}
	
	private static String ficAudioActual = null;
	public static void lanzaAudioEnHilo( String ficAudio ) {
		ficAudioActual = ficAudio; 
		Runnable r = new Runnable() {
			@Override
			public void run() {
				lanzaAudio( ficAudioActual );
			}
		};
		(new Thread(r)).start();
	}
	
}
