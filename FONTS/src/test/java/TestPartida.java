import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test; 


import java.util.Arrays;

import dominio.Partida;
import dominio.Tablero;
import dominio.Usuario;

public class TestPartida {

    private Partida partida;
    private Usuario usuario1;
    private Usuario usuario2;

    @Before
    public void setUp() {
        usuario1 = new Usuario("Jugador1", "lñskad123");
        usuario2 = new Usuario("Jugador2", "sdfaj123");
        partida = new Partida(Arrays.asList(usuario1, usuario2));
        
    }

    // Test para iniciarPartida
    @Test
    public void testIniciarPartida() {
        partida.llenarSaco("castellano");
        partida.iniciarPartida();
        
        assertTrue(partida.isPartidaEnCurso());
        assertEquals(7, usuario1.getCantidadFichas());
        assertEquals(7, usuario2.getCantidadFichas());
    }

    @Test
    public void testIniciarPartidaSinFichas() {
        partida.getSaco().vaciarSaco();
        partida.iniciarPartida();

        assertEquals(0, usuario1.getCantidadFichas());
        assertEquals(0, usuario2.getCantidadFichas());
    }

    // Test para pasarTurno
    @Test
    public void testPasarTurno() {
        partida.iniciarPartida();
        partida.pasarTurno();

        assertEquals(1, partida.getTurnoActual());
    }

    @Test
    public void testPasarTurnoUltimoUsuario() {
        partida.iniciarPartida();
        partida.pasarTurno();
        partida.pasarTurno();

        assertEquals(0, partida.getTurnoActual());
    }

    // Test para finalizarPartida
    @Test
    public void testFinalizarPartida() {
        partida.iniciarPartida();
        partida.finalizarPartida();

        assertFalse(partida.isPartidaEnCurso());
    }

    @Test
    public void testFinalizarPartidaYaFinalizada() {
        partida.finalizarPartida();
        assertFalse(partida.isPartidaEnCurso());
    }

    // Test para getWinnerUsuario
    @Test
    public void testGetWinnerUsuarioPorFichas() {
        partida.llenarSaco("castellano");
        partida.iniciarPartida();
        usuario1.eliminarFichas();
        partida.getSaco().vaciarSaco();

        assertEquals(usuario1, partida.getWinnerUsuario());
    }

    /*@Test
    public void testGetWinnerUsuarioEmpate() {
        partida.llenarSaco("castellano");
        partida.iniciarPartida();
        usuario1.setPuntuacionPartidaActual(50);
        usuario2.setPuntuacionPartidaActual(50);

        assertNull(partida.getWinnerUsuario());
    }*/
    @Test
    public void testGetWinnerUsuario() {
        partida.llenarSaco("castellano");
        partida.iniciarPartida();
        usuario1.setPuntuacionPartidaActual(50);
        usuario2.setPuntuacionPartidaActual(30);

        usuario1.eliminarFichas();
        partida.getSaco().vaciarSaco();

        assertEquals(usuario1, partida.getWinnerUsuario());
    }

    // Test para reponerFichasUsuario
    @Test
    public void testReponerFichasUsuario() {
        partida.llenarSaco("castellano");
        partida.iniciarPartida();
        usuario1.eliminarFichas();
        partida.reponerFichasUsuario(usuario1);

        assertEquals(7, usuario1.getFichas().size());
    }

    @Test
    public void testReponerFichasUsuarioSinFichasEnSaco() {
        partida.llenarSaco("castellano");
        partida.iniciarPartida();
        usuario1.eliminarFichas();
        partida.getSaco().vaciarSaco();
        partida.reponerFichasUsuario(usuario1);
        

        assertEquals(0, usuario1.getCantidadFichas());
    }

    // Test para salvarPartida y cargarPartida
    /*@Test
    public void testSalvarYCargarPartida() {
        partida.setId("partida123");
        partida.iniciarPartida();
        partida.salvarPartida("test_partida.json");

        Partida cargada = Partida.cargarPartida("test_partida.json");
        assertNotNull(cargada);
        assertEquals("partida123", cargada.getId());
        assertEquals(2, cargada.getUsuarios().size());
    }*/

    @Test
    public void testCargarPartidaArchivoInexistente() {
        Partida cargada = Partida.cargarPartida("archivo_inexistente.json");
        assertNull(cargada);
    }

    // Test para hayJugadasDisponibles
    /*@Test
    public void testHayJugadasDisponibles() {
        partida.iniciarPartida();
        assertTrue(partida.hayJugadasDisponibles());
    }*/

    /*@Test
    public void testNoHayJugadasDisponibles() {
        partida.iniciarPartida();
        // Simular que no hay jugadas disponibles
        partida.getAlgoritmo().setNoHayJugadasDisponibles();
        assertFalse(partida.hayJugadasDisponibles());
    }*/

    // Test para seleccionarIdioma
    @Test
    public void testSeleccionarIdioma() {
        partida.seleccionarIdioma("castellano", new Tablero());
        assertEquals("castellano", partida.getIdioma());
    }

    @Test
    public void testSeleccionarIdiomaNoSoportado() {
        partida.seleccionarIdioma("xx", new Tablero());
        assertEquals("xx", partida.getIdioma()); // Dependerá de cómo manejes idiomas no soportados
    }

    @Test
    public void testFinalizarPartidaConPuntuaciones() {
        partida.llenarSaco("castellano");
        partida.iniciarPartida();

        usuario1.setPuntuacionPartidaActual(50);
        usuario2.setPuntuacionPartidaActual(30);

        partida.finalizarPartida();

        // Verificar que las puntuaciones finales se imprimen correctamente
        assertFalse(partida.isPartidaEnCurso());
        assertEquals(50, usuario1.getPuntuacionPartidaActual());
        assertEquals(30, usuario2.getPuntuacionPartidaActual());
    }

    @Test
    public void testGetUsuarioTurnoActual() {
        partida.iniciarPartida();
        assertEquals(usuario1, partida.getUsuarioTurnoActual());

        partida.pasarTurno();
        assertEquals(usuario2, partida.getUsuarioTurnoActual());
    }
}