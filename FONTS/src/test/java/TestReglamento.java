import org.junit.Before;
import org.junit.Test; 
import dominio.Reglamento;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/** 
 * Clase TestReglamento:
 * Esta clase contiene los tests unitarios para verficiar el correcto funcionamiento de la clase Reglamento, 
 * que implementa el patrón Singleton y gestiona las reglas del juego de Scrabble.
 * 
 * El atributo principal que contiene la clase es:
 * - `reglasReg`: una cadena de texto que contiene las reglas del juego de Scrabble.
 */
public class TestReglamento {
   //cadena de texto que contiene las reglas del juego de Scrabble.
   static String reglasReg = """ 
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
    * Test para verificar que la clase Reglamento implementa correctamente el patrón Singleton.
      * Este test asegura que solo se puede crear una instancia de la clase Reglamento y que todas las referencias apuntan a la misma instancia.
    */
    @Test
    public void testReglamentoSingleton() {
        //verificamos que las instancias que creemos apuntan a la misma instancia. 
        Reglamento reglamento1 = Reglamento.getInstance(); 
        Reglamento reglamento2 = Reglamento.getInstance(); 
        assertSame(reglamento1, reglamento2); 
    }

    /**
     * Test para verificar la inicialización del reglamento.
     * Este test asegura que la instancia de Reglamento no es nula y que el contenido de las reglas no es nulo.
     */
    @Test
    public void testIinicializacionReglamentoValido() {
        Reglamento reglamento = Reglamento.getInstance(); 
        //aseguramos que la instancia no es nula: 
        assertNotNull(reglamento); 
        //aseguramos que el contenido que obtenemos como reglas no es nulo: 
        assertNotNull(reglamento.getReglamento()); 
    }

    /**
     * Test para verificar el contenido del reglamento.
     * Este test asegura que el contenido de las reglas no es nulo y que contiene la cadena de texto esperada.
     */
    @Test
    public void testContenidoReglamentoValido() {
        Reglamento reglamento = Reglamento.getInstance(); 
        String reglas = reglamento.getReglamento(); 
        //verificamos que el contenido no es nulo y que podremos ver el reglamento. 
        assertNotNull(reglas); 
        assertTrue(reglas.contains(reglasReg)); 
    }
}
