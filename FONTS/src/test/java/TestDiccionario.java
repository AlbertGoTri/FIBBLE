import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

import dominio.Diccionario;

public class TestDiccionario {

    private Diccionario diccionario;

    @Before
    public void setUp() {
        diccionario = new Diccionario();
    }

    @Test
    public void testCargarDiccionarioValido() {
        diccionario.setCargarDiccionario("castellano");
        Set<String> palabras = diccionario.getPalabras();

        assertFalse(palabras.isEmpty()); // Asegurarse de que el diccionario no esté vacío
        assertTrue(palabras.contains("HOLA")); // Verificar que una palabra conocida esté en el diccionario
    }

    @Test
    public void testCargarDiccionarioInexistente() {
        diccionario.setCargarDiccionario("inexistente");
        Set<String> palabras = diccionario.getPalabras();

        assertTrue(palabras.isEmpty()); // El diccionario debe estar vacío si el archivo no existe
    }

    @Test
    public void testCargarDiccionarioVacio() {
        diccionario.setCargarDiccionario("archivoVacio"); // Asegúrate de que archivoVacioDic.txt esté vacío
        Set<String> palabras = diccionario.getPalabras();

        assertTrue(palabras.isEmpty()); // El diccionario debe estar vacío si el archivo está vacío
    }

    @Test
    public void testPalabraEnDiccionario1() {
        diccionario.setCargarDiccionario("castellano");

        assertTrue(diccionario.getEstaDiccionario("CHACHALAQUEABAMOS")); // Palabra en el diccionario
        assertFalse(diccionario.getEstaDiccionario("XYZ")); // Palabra no en el diccionario
    }

    @Test
    public void testPalabraEnDiccionario2() {
        diccionario.setCargarDiccionario("catalan");

        assertTrue(diccionario.getEstaDiccionario("SOTERRAMENTS")); // Palabra en el diccionario
    }

    @Test
    public void testCalcularPuntuacionPalabraValida1() {
        diccionario.setCargarDiccionario("castellano");

        int puntuacion = diccionario.calcularPuntuacionPalabra("HOLA");
        assertTrue(puntuacion  == 7); // Verificar que la puntuación sea mayor a 0
    }
    @Test

    public void testCalcularPuntuacionPalabraValida2() {
        diccionario.setCargarDiccionario("castellano");

        int puntuacion = diccionario.calcularPuntuacionPalabra("CHARRASQUEASEMOS");
        assertTrue(puntuacion  == 31); // Verificar que la puntuación sea mayor a 0
    }

    @Test
    public void testCalcularPuntuacionPalabraValida3() {
        diccionario.setCargarDiccionario("catalan");

        int puntuacion = diccionario.calcularPuntuacionPalabra("CAL·LAR");
        
        assertTrue(puntuacion  == 15); // Verificar que la puntuación sea mayor a 0
    }

    @Test
    public void testCalcularPuntuacionPalabraInvalida() {
        diccionario.setCargarDiccionario("castellano");

        diccionario.getEstaDiccionario("XYZ");
        int puntuacion = diccionario.calcularPuntuacionPalabra("XYZ");
        assertEquals(22, puntuacion); // Palabra no válida debe tener puntuación 0
    }

    @Test
    public void testCalcularPuntuacionPalabraCompuesta() {
        diccionario.setCargarDiccionario("castellano");

        int puntuacion = diccionario.calcularPuntuacionPalabra("CHICO");
        assertTrue(puntuacion == 10); // Verificar que las letras compuestas sean procesadas correctamente
    }

    @Test
    public void testPalabraVacia() {
        diccionario.setCargarDiccionario("castellano");

        assertFalse(diccionario.getEstaDiccionario("")); // Palabra vacía no debe estar en el diccionario
    }
}