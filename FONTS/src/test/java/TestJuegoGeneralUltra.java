/*
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import dominio.Partida;
import dominio.Usuario;
import dominio.Tablero;
import dominio.Movimiento;
import dominio.Diccionario;
import dominio.Algoritmo;
import dominio.Robot;
import dominio.Casilla;

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
    public void testPartidaCompletaConRobotYMovimientos() {
        partida.seleccionarIdioma("castellano");
        partida.llenarSaco("castellano");
        partida.iniciarPartida();

        assertTrue(partida.isPartidaEnCurso());
        assertEquals(7, jugador1.getCantidadFichas());

        // Simular puntuaciones y turnos
        jugador1.setPuntuacionPartidaActual(25);
        partida.pasarTurno();
        jugador2.setPuntuacionPartidaActual(30);
        partida.pasarTurno();

        // Verificar rotación de turnos
        assertEquals(jugador1, partida.getUsuarioTurnoActual());

        // Simular fin de juego
        jugador2.eliminarFichas();
        partida.getSaco().vaciarSaco();
        partida.finalizarPartida();

        assertFalse(partida.isPartidaEnCurso());
        assertEquals(jugador2, partida.getWinnerUsuario());
    }

    @Test
    public void testRobotJugandoPalabra() {
        Diccionario diccionario = new Diccionario();
        diccionario.setCargarDiccionario("castellano");

        Tablero tablero = new Tablero();
        tablero.setIdioma("castellano");

        Algoritmo algoritmo = new Algoritmo(diccionario, tablero);
        Robot robot = new Robot("RobotX", algoritmo, tablero);
        robot.setFichas(Arrays.asList("C", "A", "S", "A", "R", "O", "L"));

        // Preparar casillas existentes para conexión
        tablero.setCasilla("C", 8, 8);
        tablero.matrizOcupacio[7][7].setOcupada(true);

        // Intentar movimiento (aquí normalmente harías robot.jugarTurno(), simplificado)
        List<int[]> coords = new ArrayList<>();
        tablero.setCasilla("A", 8, 9);
        tablero.setCasilla("S", 8, 10);
        tablero.setCasilla("A", 8, 11);
        coords.add(new int[]{8, 8});
        coords.add(new int[]{8, 9});
        coords.add(new int[]{8, 10});
        coords.add(new int[]{8, 11});

        try {
            tablero.extraerPalabras(coords, 0);
        } catch (Exception e) {
            fail("Movimiento del robot no debería lanzar excepción: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMovimientoFueraDeRango() {
        Tablero tablero = new Tablero();
        tablero.inicializarMatriz();
        tablero.setCasilla("Z", 100, 100); // Esto debería lanzar excepción
    }

    @Test
    public void testReiniciarPartida() {
        partida.llenarSaco("castellano");
        partida.iniciarPartida();
        partida.finalizarPartida();

        assertFalse(partida.isPartidaEnCurso());

        // Volver a iniciar una nueva partida
        partida.llenarSaco("castellano");
        partida.iniciarPartida();

        assertTrue(partida.isPartidaEnCurso());
        assertEquals(7, partida.getUsuarioTurnoActual().getCantidadFichas());
    }

    @Test
    public void testCambioDeIdiomaEnMedioDePartida() {
        partida.llenarSaco("castellano");
        partida.iniciarPartida();
        assertEquals("castellano", partida.getIdioma());

        partida.seleccionarIdioma("ingles", new Tablero());
        assertEquals("ingles", partida.getIdioma()); // Suponiendo que lo permite

        // Revalidar estado
        assertTrue(partida.isPartidaEnCurso());
    }

    @Test
    public void testEmpateEnPartida() {
        partida.llenarSaco("castellano");
        partida.iniciarPartida();
        jugador1.setPuntuacionPartidaActual(50);
        jugador2.setPuntuacionPartidaActual(50);

        jugador1.eliminarFichas();
        partida.getSaco().vaciarSaco();
        partida.finalizarPartida();

        assertNull(partida.getWinnerUsuario()); // Empate
    }

    @Test
    public void testVistaDeTurnosYJugadores() {
        partida.llenarSaco("castellano");
        partida.iniciarPartida();

        Usuario actual = partida.getUsuarioTurnoActual();
        assertNotNull(actual);

        partida.pasarTurno();
        Usuario siguiente = partida.getUsuarioTurnoActual();
        assertNotNull(siguiente);
        assertNotEquals(actual, siguiente);
    }
}
*/