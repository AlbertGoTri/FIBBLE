import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

import dominio.Saco;

public class TestSaco {

    private Saco saco;

    @Before
    public void setUp() {
        saco = new Saco();
    }

    @Test
    public void testLlenarSacoConArchivoValido() {
        // Archivo castellanoFreq.txt debe estar en src/test/resources/
        saco.setLlenarSaco("castellano");

        Map<String, Integer> letras = saco.getLetrasFreq();

        assertFalse(letras.isEmpty());
        assertEquals(12, (int) letras.get("A"));
        assertEquals(1, (int) letras.get("Z"));
        assertEquals(1, (int) letras.get("CH"));  // prueba combinaciones
        assertEquals(28, letras.size()); // debe tener 28 claves distintas (según tu archivo)
    }

    @Test
    public void testRobarLetraYActualizarFrecuencia() {
        saco.setLlenarSaco("castellano");
    
        // Obtener la frecuencia inicial de la letra "A"
        Integer frecuenciaInicial = saco.getLetrasFreq().get("A");
        assertNotNull(frecuenciaInicial); // Asegurarse de que "A" exista en el saco inicialmente
    
        // Robar letras hasta que "A" se agote
        while (saco.getLetrasFreq().containsKey("A") && saco.getLetrasFreq().get("A") > 0) {
            String letraRobada = saco.getRobarLetra();
            assertNotNull(letraRobada); // Asegurarse de que siempre se robe una letra
        }
    
        // Verificar que "A" ya no está en el saco
        assertFalse(saco.getLetrasFreq().containsKey("A"));
    
        // Verificar que el saco no esté vacío si aún quedan otras letras
        if (!saco.estaVacio()) {
            assertTrue(saco.getLetrasFreq().size() > 0);
        }
    }

    @Test
    public void testEstaVacioInicialmente() {
        assertTrue(saco.estaVacio());
    }

    @Test
    public void testEstaVacioDespuesDeRobarTodo() {
        saco.setLlenarSaco("castellano");

        while (!saco.estaVacio()) {
            saco.getRobarLetra();
        }

        assertTrue(saco.estaVacio());
    }
    @Test
    public void testRobarLetraDeSacoVacio() {
        // Asegurarse de que el saco esté vacío
        assertTrue(saco.estaVacio());

        // Intentar robar una letra de un saco vacío
        String letraRobada = saco.getRobarLetra();

        // Verificar que no se pueda robar ninguna letra
        assertNull(letraRobada);
    }
    
    @Test
    public void testLlenarSacoConArchivoVacio() {
        // Simular un archivo vacío
        saco.setLlenarSaco("archivoVacio"); // Asegúrate de que archivoVacioFreq.txt esté vacío en resources

        // Verificar que el saco esté vacío después de intentar llenarlo
        assertTrue(saco.estaVacio());
        assertTrue(saco.getLetrasFreq().isEmpty());
    }
}
