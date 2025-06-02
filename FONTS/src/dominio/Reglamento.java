//SINGLETON!
package dominio;

/**
 * Clase Reglamento
 * <p>
 * Esta clase implementa el patrón Singleton y contiene las reglas del juego de Scrabble.
 * <b>Propósito:</b> Proporcionar un conjunto de reglas que guíen el juego y aseguren su correcto desarrollo. Implementa el patrón Singleton para garantizar que solo haya una instancia de las reglas en todo el sistema.
 * </p>
 */
public class Reglamento {
   /** Instancia única de la clase Reglamento (Singleton) */
    private static Reglamento instancia; 
    /** Variable que contiene las reglas del juego. */
    private static String reglas = """ 
    ############# REGLAMENTO SCRABBLE #############

    1. OBJETIVO DEL JUEGO:
       - Obtener la mayor cantidad de puntos formando palabras válidas en el tablero.

    2. INICIO DEL JUEGO:
       - Cada jugador toma 7 fichas al azar del saco al inicio del juego.
       - El jugador que forme la palabra con mayor puntuación comienza.

    3. FORMACIÓN DE PALABRAS:
       - Las palabras deben formarse de izquierda a derecha (horizontal) o de arriba hacia abajo (vertical).
       - Todas las palabras deben estar conectadas con las ya existentes en el tablero.

    4. PUNTUACIÓN DE LAS FICHAS:
       - Cada ficha tiene un valor en puntos. Los valores están indicados en las fichas.
       - Las casillas especiales del tablero pueden duplicar o triplicar el valor de una ficha o de una palabra.

    5. TURNOS DE LOS JUGADORES:
       - En su turno, un jugador puede:
         - Colocar una palabra en el tablero.
         - Cambiar algunas o todas sus fichas por nuevas del saco.
         - Pasar el turno si no puede o no quiere jugar.

    6. PALABRAS VÁLIDAS:
       - Solo se permiten palabras que existan en el diccionario oficial del juego.

    7. REPOSICIÓN DE FICHAS:
       - Después de colocar una palabra, el jugador toma nuevas fichas del saco para volver a tener 7 fichas en su atril.

    8. FINAL DEL JUEGO:
       - El juego termina cuando:
         - No quedan fichas en el saco y un jugador ha usado todas sus fichas.
         - Todos los jugadores pasan su turno consecutivamente.
       - Los puntos de las fichas restantes en el atril de cada jugador se restan de su puntuación final.

    9. GANADOR:
       - El jugador con la mayor cantidad de puntos al final del juego es el ganador.
           """;

   /** 
    * Constructor privado para evitar la creación de instancias externas.
    <p>
    * Este constructor inicializa la instancia a null.
    </p>
    */
    private Reglamento() {
        instancia = null;
    }

    /**
     * Método estático para obtener la única instancia de la clase Reglamento.
     * 
     * @return La única instancia de la clase Reglamento.
     */
    public static Reglamento getInstance() {
        if(instancia == null) {
            instancia = new Reglamento(); 
        }
        return instancia;
    }

   /**
    * Método para obtener las reglas del juego.
    *
    * @return Las reglas del juego como un String.
    */
    public String getReglamento() {
        return reglas; 
    }
}
