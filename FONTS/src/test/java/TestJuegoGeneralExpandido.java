/*
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import dominio.Partida;
import dominio.Usuario;
import dominio.Tablero;
import dominio.Movimiento;

public class TestJuegoGeneral {

    private Partida partida;
    private Usuario jugador1;
    private Usuario jugador2;

    @Before
    public void setUp() {
        jugador1 = new Usuario("Jugador1", "123abc");
        jugador2 = new Usuario("Jugador2", "456def");
        partida = new Partida(Arrays.asList(jugador1, jugador2));
    }

    @Test
    public void testFlujoCompletoDeJuego() {
        partida.seleccionarIdioma("castellano");
        assertEquals("castellano", partida.getIdioma());

        partida.llenarSaco("castellano");
        partida.iniciarPartida();

        assertTrue(partida.isPartidaEnCurso());
        assertEquals(7, jugador1.getCantidadFichas());
        assertEquals(7, jugador2.getCantidadFichas());

        // Turno actual debe ser jugador1
        assertEquals(jugador1, partida.getUsuarioTurnoActual());

        // Pasar turno varias veces
        partida.pasarTurno(); // jugador2
        assertEquals(jugador2, partida.getUsuarioTurnoActual());
        partida.pasarTurno(); // jugador1
        assertEquals(jugador1, partida.getUsuarioTurnoActual());

        // Probar reponer fichas (vaciando primero)
        jugador1.eliminarFichas();
        partida.reponerFichasUsuario(jugador1);
        assertEquals(7, jugador1.getCantidadFichas());

        // Vaciamos saco para probar límite
        partida.getSaco().vaciarSaco();
        jugador1.eliminarFichas();
        partida.reponerFichasUsuario(jugador1);
        assertEquals(0, jugador1.getCantidadFichas());

        // Finalizar partida manualmente
        partida.finalizarPartida();
        assertFalse(partida.isPartidaEnCurso());

        // Simular puntuaciones y ganador
        jugador1.setPuntuacionPartidaActual(50);
        jugador2.setPuntuacionPartidaActual(30);
        partida.llenarSaco("castellano");
        partida.iniciarPartida();
        jugador1.eliminarFichas();
        partida.getSaco().vaciarSaco();
        assertEquals(jugador1, partida.getWinnerUsuario());
    }

    @Test
    public void testGuardarYCargarPartida() {
        partida.setId("game001");
        partida.seleccionarIdioma("castellano");
        partida.llenarSaco("castellano");
        partida.iniciarPartida();
        partida.salvarPartida("test_guardado.json");

        Partida cargada = Partida.cargarPartida("test_guardado.json");
        assertNotNull(cargada);
        assertEquals("game001", cargada.getId());
    }

    @Test
    public void testCargarPartidaInexistente() {
        Partida cargada = Partida.cargarPartida("no_existe.json");
        assertNull(cargada);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSeleccionarIdiomaNoSoportado() {
        partida.seleccionarIdioma("klingon", new Tablero());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMovimientoInvalido() {
        Tablero tablero = new Tablero();
        tablero.inicializarMatriz();
        tablero.setCasilla("X", 20, 20); // Fuera del tablero
    }
}
*/