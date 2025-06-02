import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;
import dominio.Movimiento;

/**
 * Clase de pruebas unitarias para la clase {@link Movimiento}.
 * Esta clase utiliza JUnit para verificar el correcto funcionamiento de las funcionalidades
 * de la clase Movimiento, incluyendo la creación de movimientos, la modificación de su puntuación
 * y la representación en forma de cadena.
 * 
 * @author Albert
 * @version 1.0
 */
public class TestMovimiento {

    /**
     * Prueba unitaria para verificar la creación de un movimiento.
     * Se asegura de que el objeto {@link Movimiento} no sea nulo y que sus atributos
     * sean los esperados.
     */
    @Test
    public void testCrearMovimiento() {
        Movimiento movimiento = new Movimiento(Arrays.asList("C", "A", "S", "A"), 7, 7, true, 10, "CASA");

        assertNotNull(movimiento);
        assertEquals(Arrays.asList("C", "A", "S", "A"), movimiento.getFichas());
        assertEquals(7, movimiento.getFila());
        assertEquals(7, movimiento.getColumna());
        assertTrue(movimiento.esHorizontal());
        assertEquals(10, movimiento.getPuntuacion());
        assertEquals("CASA", movimiento.getPalabraFormada());
    }

    /**
     * Prueba unitaria para verificar la modificación de la puntuación de un movimiento.
     * Se asegura de que el método {@link Movimiento#setPuntuacion(int)} actualice correctamente
     * la puntuación del movimiento.
     */
    @Test
    public void testSetPuntuacion() {
        Movimiento movimiento = new Movimiento(Arrays.asList("C", "A", "S", "A"), 7, 7, true, 10, "CASA");
        movimiento.setPuntuacion(15);

        assertEquals(15, movimiento.getPuntuacion());
    }

    /**
     * Prueba unitaria para verificar la representación en forma de cadena de un movimiento.
     * Se asegura de que el método {@link Movimiento#toString()} devuelva el formato esperado.
     */
    @Test
    public void testToString() {
        Movimiento movimiento = new Movimiento(Arrays.asList("C", "A", "S", "A"), 7, 7, true, 10, "CASA");
        String expected = "Movimiento{palabra='CASA', fila=7, columna=7, horizontal=true, puntuacion=10}";
        assertEquals(expected, movimiento.toString());
    }
}