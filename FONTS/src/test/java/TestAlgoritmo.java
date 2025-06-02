import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import dominio.Algoritmo;
import dominio.Diccionario;
import dominio.Tablero;
import dominio.Movimiento;
import dominio.Usuario;

/**
 * Clase de pruebas unitarias para la clase {@link Algoritmo}.
 * Esta clase utiliza JUnit para verificar el correcto funcionamiento de las funcionalidades
 * de la clase Algoritmo, incluyendo la validación de palabras, la generación de movimientos
 * y el cálculo de puntuaciones.
 * 
 * @author Albert
 * @version 1.0
 */
public class TestAlgoritmo {

    private Algoritmo algoritmo;
    private Tablero tablero;
    private Diccionario diccionario;

    /**
     * Configuración inicial antes de cada prueba.
     * Se inicializan las instancias de {@link Tablero}, {@link Diccionario} y {@link Algoritmo}.
     * Además, se configura el idioma del tablero y se carga el diccionario correspondiente.
     */
    @Before
    public void setUp() {
        tablero = new Tablero();
        tablero.setIdioma("castellano");
        diccionario = new Diccionario();
        diccionario.setCargarDiccionario("castellano");
        algoritmo = new Algoritmo(diccionario, tablero);
    }

    /**
     * Prueba unitaria para verificar la validación de palabras.
     * Se asegura de que el método {@link Algoritmo#esPalabraValida(String, String)} 
     * identifique correctamente palabras válidas e inválidas.
     */
    @Test
    public void testEsPalabraValida() {
        assertTrue(algoritmo.esPalabraValida("CASA", "castellano"));
        assertFalse(algoritmo.esPalabraValida("XYZ", "castellano"));
    }

    /*@Test
    public void testCalcularPuntuacion() {
        tablero.setCasilla("C", 7, 7);
        tablero.setCasilla("A", 7, 8);
        tablero.setCasilla("S", 7, 9);
        tablero.setCasilla("A", 7, 10);

        int puntuacion = algoritmo.calcularPuntuacion("CASA", 7, 7, true);
        assertTrue(puntuacion > 0);
    }*/

    @Test
    public void testGenerarMovimientos() {
        Usuario usuario = new Usuario("Jugador", "password");
        usuario.setFichas(Arrays.asList("C", "A", "S", "A"));

        List<Movimiento> movimientos = algoritmo.generarMovimientos(usuario);
        assertNotNull(movimientos);
        assertFalse(movimientos.isEmpty());
    }

    /**
     * Prueba unitaria para verificar el cálculo de la mejor jugada posible.
     * Se asegura de que el método {@link Algoritmo#calcularMejorJugada(Usuario)} 
     * identifique correctamente el mejor movimiento basado en las fichas del usuario
     * y el estado actual del tablero.
     */
    /*@Test
    public void testCalcularMejorJugada() {
        Usuario usuario = new Usuario("Jugador", "password");
        usuario.setFichas(Arrays.asList("C", "A", "S", "A"));

        tablero.setCasilla("C", 7, 7);
        tablero.setCasillaOcupada(7, 7);

        Movimiento mejorMovimiento = algoritmo.calcularMejorJugada(usuario);
        assertNotNull(mejorMovimiento);
        assertEquals("CASA", mejorMovimiento.getPalabraFormada());
    }*/

    /**
     * Prueba unitaria para verificar la generación de movimientos en un tablero parcialmente ocupado.
     * Se asegura de que el método {@link Algoritmo#generarMovimientos(Usuario)} 
     * genere movimientos válidos considerando las fichas ya colocadas en el tablero.
     */
    @Test
    public void testGenerarMovimientosTableroParcial() {
        Usuario usuario = new Usuario("Jugador", "password");
        usuario.setFichas(Arrays.asList("C", "A", "S", "A"));

        tablero.setCasilla("C", 7, 7);
        tablero.setCasillaOcupada(7, 7);

        List<Movimiento> movimientos = algoritmo.generarMovimientos(usuario);
        assertNotNull(movimientos);
        assertFalse(movimientos.isEmpty());
    }

    /**
     * Prueba unitaria para verificar la generación de movimientos cuando el usuario no tiene fichas válidas.
     * Se asegura de que el método {@link Algoritmo#generarMovimientos(Usuario)} 
     * devuelva una lista vacía en este caso.
     */
    @Test
    public void testGenerarMovimientosSinFichasValidas() {
        Usuario usuario = new Usuario("Jugador", "password");
        usuario.setFichas(Arrays.asList("X", "Y", "Z"));

        List<Movimiento> movimientos = algoritmo.generarMovimientos(usuario);
        assertNotNull(movimientos);
        assertTrue(movimientos.isEmpty());
    }

    /**
     * Prueba unitaria para verificar el cálculo de puntuaciones con bonificaciones.
     * Se asegura de que el método {@link Algoritmo#calcularPuntuacion(String, int, int, boolean)} 
     * calcule correctamente la puntuación considerando las bonificaciones del tablero.
     */
/*    @Test
    public void testCalcularPuntuacionConBonificaciones() {
        tablero.setCasilla("C", 7, 7);
        tablero.setCasilla("A", 7, 8);
        tablero.setCasilla("S", 7, 9);
        tablero.setCasilla("A", 7, 10);

        tablero.matrizOcupacio[6][6].setBonificacion(3); // Doble palabra
        tablero.matrizOcupacio[6][7].setBonificacion(2); // Triple letra

        int puntuacion = algoritmo.calcularPuntuacion("CASA", 7, 7, true);
        assertTrue(puntuacion > 0);
    }*/
}