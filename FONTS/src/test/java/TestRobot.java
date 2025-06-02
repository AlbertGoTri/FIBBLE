import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import dominio.Robot;
import dominio.Tablero;
import dominio.Algoritmo;
import dominio.Movimiento;
import dominio.Diccionario;

/**
 * Clase de pruebas unitarias para la clase {@link Robot}.
 * Esta clase utiliza JUnit para verificar el correcto funcionamiento de las funcionalidades
 * de la clase Robot, incluyendo la creación de un robot, la ejecución de jugadas sin movimientos
 * posibles y otras interacciones con el tablero y el algoritmo.
 * 
 * @author Albert
 * @version 1.0
 */
public class TestRobot {

    private Robot robot;
    private Tablero tablero;
    private Algoritmo algoritmo;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    /**
     * Configuración inicial antes de cada prueba.
     * Se inicializan las instancias de {@link Tablero}, {@link Algoritmo} y {@link Robot}.
     * Además, se redirige la salida estándar para capturar el contenido de la consola.
     */
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        tablero = new Tablero();
        tablero.setIdioma("castellano");
        algoritmo = new Algoritmo(new Diccionario(), tablero);
        robot = new Robot("RobotTest", algoritmo, tablero);
    }

    /**
     * Prueba unitaria para verificar la creación de un robot.
     * Se asegura de que el objeto {@link Robot} no sea nulo y que su nombre de usuario
     * sea el esperado.
     */
    @Test
    public void testCrearRobot() {
        assertNotNull(robot);
        assertEquals("RobotTest", robot.getUsername());
    }

    /**
     * Prueba unitaria para verificar el comportamiento del robot cuando no hay movimientos posibles.
     * Se añaden fichas al atril del robot, se ejecuta su jugada y se valida que el mensaje
     * de salida sea el esperado.
     */
    /*@Test
    public void testJugarSinMovimientos() {
        robot.agregarFicha("A");
        robot.agregarFicha("B");
        robot.agregarFicha("C");
        robot.jugar();
        assertEquals("No hay movimientos posibles para el Robot.", outContent.toString().trim());
    }*/

    /*
     * Prueba unitaria para verificar el comportamiento del robot cuando hay un movimiento posible.
     * (Actualmente comentada).
     * Se añaden fichas al atril del robot, se configura el tablero con un punto de anclaje,
     * se ejecuta la jugada del robot y se valida el movimiento, el estado del tablero y la puntuación.
     */
    /*
    @Test
    public void testJugarConMovimiento() {
        robot.agregarFicha("C");
        robot.agregarFicha("A");
        robot.agregarFicha("S");
        robot.agregarFicha("A");
        tablero.setCasilla("C", 7, 7);
        tablero.setCasillaOcupada(7, 7);
        robot.jugar();
        Movimiento expectedMovimiento = new Movimiento(Arrays.asList("C", "A", "S", "A"), 7, 7, true, 10, "CASA");
        assertEquals("Robot juega: " + expectedMovimiento.toString(), outContent.toString().trim());
        assertEquals("C", tablero.getLetra(6, 6));
        assertEquals("A", tablero.getLetra(6, 7));
        assertEquals("S", tablero.getLetra(6, 8));
        assertEquals("A", tablero.getLetra(6, 9));
        assertEquals(10, robot.getPuntuacionPartidaActual());
    }
    */

    /**
     * Limpieza después de cada prueba.
     * Se restaura la salida estándar original.
     */
    @After
    public void tearDown() {
        System.setOut(originalOut);
    }
}