package tema1;

/** Pruebas de clase string
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class PruebasString {
	public static void main(String[] args) {
		String s1 = "hola";
		String s2 = "adios Fernando";
		String s3 = "holo";
		System.out.println( s1.compareTo(s2) );
		System.out.println( s1.compareTo(s3) );
		System.out.println( s1.contains( "ol" ) );
		System.out.println( s1.indexOf( "ol" ) );
		System.out.println( s1.indexOf( "el" ) );
		for (int posi=0; posi<s2.length(); posi++) {
			System.out.println( s2.charAt(posi) );
		}
		int posiEspacio = s2.indexOf(" ");
		if (posiEspacio!=-1) {
			System.out.println( s2.substring(posiEspacio+1));
		}
	}
}
