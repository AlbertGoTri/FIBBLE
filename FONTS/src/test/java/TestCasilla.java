import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import dominio.Algoritmo;
import dominio.Diccionario;
import dominio.Casilla;
import java.util.ArrayList;
import java.util.List;

public class TestCasilla {
    private Casilla casilla;

    @Before
    public void setUp() {
        casilla = new Casilla(0,8, 8,false); // Inicializa una casilla en la posición (0, 0)
    }
    // Test para comprobar que la casilla esta ocupada
    @Test(expected = IllegalArgumentException.class)
    public void testColocarFichaOcupada() {
        casilla.ColocarFicha("A");
        casilla.setOcupada(true); // Marca la casilla como ocupada
        casilla.ColocarFicha("B"); // Debería lanzar una excepción
    }

    // Test para comprobar que la casilla no esta ocupada
    @Test
    public void testColocarFichaNoOcupada() {
        casilla.ColocarFicha("A");
        assertEquals("A", casilla.getLetra()); // Verifica que la letra se ha colocado correctamente
        assertFalse(casilla.isOcupada()); // Verifica que la casilla no está ocupada
    }
}
