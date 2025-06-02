package dominio;

/**
 * Clase Humano
 * <p>
 * Esta clase representa a un jugador humano en el juego de Scrabble.
 * Hereda de la clase {@link Usuario}.
 * </p>
 * <p>
 * <b>Propósito:</b> Identificar a los jugadores humanos en el sistema.
 * No contiene atributos adicionales, pero puede ser extendida en el futuro.
 * </p>
 */

public class Humano extends Usuario {
    
    /**
     * Constructor de la clase Humano
     * <p>
     * Este constructor inicializa el objeto Humano con un nombre de usuario y una contraseña.
     * </p>
     * 
     * @param username El nombre de usuario del jugador.
     * @param password La contraseña del jugador.
     */
    public Humano(String username, String password) {
        super(username, password); 
    }
}
