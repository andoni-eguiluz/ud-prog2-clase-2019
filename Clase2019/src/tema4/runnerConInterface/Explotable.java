package tema4.runnerConInterface;

public interface Explotable {
	void explota();
	boolean estaExplotando();
	boolean yaDesaparecido();
	// Otra opción sería
	// int devuelveEstado(); // 0 = normal, 1 = explotando, 2 = desaparecido
}
