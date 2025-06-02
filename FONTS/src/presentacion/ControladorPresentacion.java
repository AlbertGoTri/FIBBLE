package presentacion;

import javax.swing.*;
import java.awt.CardLayout;
import dominio.ControladorDeDominio;
import dominio.ControladorPartida;
import dominio.Usuario;
import dominio.Tablero;
import dominio.Estadistica;
import java.util.List;
import dominio.Vista;

import java.io.File;
import java.util.Map;
import java.util.ResourceBundle.Control;

/**
 * ControladorPresentacion es el controlador principal de la capa de presentación del sistema Scrabble.
 * Gestiona la navegación entre vistas, la comunicación con el {@link ControladorDeDominio} y la coordinación
 * de acciones entre la interfaz gráfica y la lógica de dominio.
 *
 * <p>Responsabilidades principales:
 * <ul>
 *   <li>Inicializar y mostrar las distintas vistas de la aplicación.</li>
 *   <li>Delegar operaciones de usuario al dominio (crear perfil, login, crear/cargar partida, etc.).</li>
 *   <li>Gestionar la actualización de la interfaz según el estado de la aplicación.</li>
 * </ul>
 *
 * @author
 */
public class ControladorPresentacion {
    private Vista vista;
    private JFrame ventanaPrincipal;
    private JPanel panelPrincipal;
    private CardLayout cardLayout;
    private VistaInicio vistaInicio;
    private VistaGestionPartida vistaGestionPartida;
    private VistaCrearPartida vistaCrearPartida;
    private VistaCargarPartida vistaCargarPartida;
    private VistaGestionUsuario vistaGestionUsuario;
    private VistaCrearPerfil vistaCrearPerfil;
    private VistaEditarPerfil vistaEditarPerfil;
    private VistaPartida vistaPartida;
    private VistaEstadisticas vistaEstadisticas;
    private VistaLogin vistaLogin;
    private VistaRanking vistaRanking;
    private ControladorDeDominio controladorDeDominio;  

    /**
     * Constructor. Inicializa la instancia de {@link ControladorDeDominio}.
     */
    public ControladorPresentacion() {
        vista = new Vista();
        controladorDeDominio = ControladorDeDominio.getInstance();
    }

    /**
     * Inicia la aplicación y muestra la ventana principal.
     */
    public void iniciarAplicacion() {
        SwingUtilities.invokeLater(() -> {
            ventanaPrincipal = new JFrame("Scrabble");
            ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventanaPrincipal.setSize(1000, 800);
            ventanaPrincipal.setLocationRelativeTo(null);

            cardLayout = new CardLayout();
            panelPrincipal = new JPanel(cardLayout);

            vistaLogin = new VistaLogin(this);
            vistaInicio = new VistaInicio(this);
            vistaGestionPartida = new VistaGestionPartida(this);
            vistaCrearPartida = new VistaCrearPartida(this);
            vistaCargarPartida = new VistaCargarPartida(this);
            vistaGestionUsuario = new VistaGestionUsuario(this);
            vistaCrearPerfil = new VistaCrearPerfil(this);
            vistaPartida = new VistaPartida(this);
            vistaEditarPerfil = new VistaEditarPerfil(this);
            vistaEstadisticas = new VistaEstadisticas(this);
            vistaRanking = new VistaRanking(this); 

            panelPrincipal.add(vistaLogin, "LOGIN");
            panelPrincipal.add(vistaInicio, "INICIO");
            panelPrincipal.add(vistaGestionPartida, "GESTION_PARTIDA");
            panelPrincipal.add(vistaCrearPartida, "CREAR_PARTIDA");
            panelPrincipal.add(vistaCargarPartida, "CARGAR_PARTIDA");
            panelPrincipal.add(vistaGestionUsuario, "GESTION_USUARIO");
            panelPrincipal.add(vistaCrearPerfil, "CREAR_PERFIL");
            panelPrincipal.add(vistaPartida, "PARTIDA");
            panelPrincipal.add(vistaEditarPerfil, "EDITAR_PERFIL");
            panelPrincipal.add(vistaEstadisticas, "ESTADISTICAS");
            panelPrincipal.add(vistaRanking, "RANKING");

            ventanaPrincipal.add(panelPrincipal);
            ventanaPrincipal.setVisible(true);

            mostrarVista("LOGIN");
        });
    }

    /**
     * Cambia la vista actual mostrada en la interfaz.
     * Si la vista es "PARTIDA" o "RANKING", actualiza su contenido antes de mostrarla.
     *
     * @param nombreVista Nombre de la vista a mostrar.
     */
    public void mostrarVista(String nombreVista) {
        cardLayout.show(panelPrincipal, nombreVista);
        SwingUtilities.invokeLater(() -> {
            if (nombreVista.equals("PARTIDA")) {
                vistaPartida.cargarTablero();
            }
            if (nombreVista.equals("RANKING")) {
                vistaRanking.cargarRanking();
            }
            if (nombreVista.equals("ESTADISTICAS")) {
                vistaEstadisticas.cargarEstadisticas();
            }
        });
    }

    /**
     * Gestiona las opciones seleccionadas en la vista de inicio.
     *
     * @param opcion Opción seleccionada por el usuario.
     */
    public void manejarOpcionInicio(int opcion) {
        switch (opcion) {
        case 1:
            mostrarVista("GESTION_USUARIO");
            break;
        case 2:
            mostrarVista("GESTION_PARTIDA");
            break;
        case 3:
            mostrarVista("RANKING");
            break;
        case 4:
            System.exit(0);
            break;
        }
    }

    /**
     * Gestiona las opciones seleccionadas en la vista de gestión de partida.
     *
     * @param opcion Opción seleccionada por el usuario.
     */
    public void manejarOpcionGestionPartida(int opcion) {
        switch (opcion) {
        case 1:
            mostrarVista("CREAR_PARTIDA");
            break;
        case 2:
            mostrarVista("CARGAR_PARTIDA");
            break;
        case 3:
            mostrarVista("INICIO");
            break;
        }
    }

    /**
     * Gestiona las opciones seleccionadas en la vista de gestión de usuario.
     *
     * @param opcion Opción seleccionada por el usuario.
     */
    public void manejarOpcionGestionUsuario(int opcion) {
        switch (opcion) {
        case 1:
            mostrarVista("CREAR_PERFIL");
            break;
        case 2:
            mostrarVista("EDITAR_PERFIL");
            break;
        case 3:
            mostrarVista("ESTADISTICAS");
            break;
        case 4:
            mostrarVista("INICIO");
            break;
        }
    }

    /**
     * Devuelve la instancia del controlador de dominio.
     *
     * @return Instancia de {@link ControladorDeDominio}.
     */
    public ControladorDeDominio getControladorDeDominio() {
        return controladorDeDominio;
    }

    /**
     * Crea un nuevo perfil de usuario.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     */
    public void crearPerfil(String username, String password) {
        controladorDeDominio.crearPerfil(username, password);
    }

    /**
     * Realiza el proceso de inicio de sesión.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     */
    public void login(String username, String password) {
        try {
            controladorDeDominio.logInUser(username, password);
            mostrarVista("INICIO");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(ventanaPrincipal, ex.getMessage(), "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Crea una nueva partida con los parámetros especificados.
     *
     * @param nombre   Nombre de la partida.
     * @param idioma   Idioma seleccionado.
     * @param numBots  Número de bots.
     */
    public void crearPartida(String nombre, String idioma, int numBots) {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        cp.crearPartida(nombre, idioma, numBots);
        this.vistaPartida = new VistaPartida(this);
        panelPrincipal.add(vistaPartida, "PARTIDA");
    }

    /**
     * Carga una partida guardada desde un archivo.
     *
     * @param nombreArchivo Nombre del archivo de la partida guardada.
     */
    public void cargarPartida(String nombreArchivo) {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        if (cp == null) {
            throw new IllegalStateException("ControladorPartida (cp) is not initialized.");
        }
        cp.cargarPartida(nombreArchivo);
    }

    /**
     * Obtiene el nombre del usuario que tiene el turno actual.
     *
     * @return Nombre del usuario en turno.
     */
    public String getTurnoActual() {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        return cp.getPartidaNombreUsuarioTurnoActual();
    }

    /**
     * Devuelve el contenido del saco de letras como String.
     *
     * @return Contenido del saco.
     */
    public String mostrarSaco() {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        return cp.getPartidaSaco();
    }

    /**
     * Devuelve la lista de letras del jugador en turno.
     *
     * @return Lista de letras.
     */
    public List<String> getLetrasJugadorTurno() {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        return cp.getLetrasJugadorTurno();
        
    }

    /**
     * Devuelve el estado actual del tablero de la partida.
     *
     * @return Matriz de Strings representando el tablero.
     */
    public String[][] getTablero() {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        return cp.getPartidaTablero();
    }

    /**
     * Finaliza la partida y devuelve el nombre del usuario ganador, o null si no hay ganador.
     *
     * @return Nombre del usuario ganador o null.
     */
    public String finalizarPartida() {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        Usuario ganador = cp.getWinnerUsuario();
        if (ganador != null) {
            return ganador.getUsername();
            
        } else {
            return null;
        }
    }

    /**
     * Pasa el turno al siguiente jugador.
     */
    public void pasarTurno() {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        cp.pasarTurno();
    }

    /**
     * Realiza una jugada en la partida.
     *
     * @param jugada Mapa con la jugada a realizar.
     * @return Resultado de la jugada.
     */
    public int realizarJugada(Map<String, List<int[]>> jugada) {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        return cp.realizarJugada(jugada);
    }

    /**
     * Permite que el robot realice una jugada.
     *
     * @param letra       Letra a colocar.
     * @param coordenadas Lista de coordenadas donde colocar la letra.
     */
    public void robotRealizarJugada(String letra, List<int[]> coordenadas) {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        //cp.robotRealizarJugada(letra, coordenadas);
    }

    /**
     * Devuelve el controlador de partida actual.
     *
     * @return Instancia de {@link ControladorPartida}.
     */
    public ControladorPartida getControladorPartida() {
        return controladorDeDominio.getControladorPartida();

    }

    /**
     * Devuelve el contenido del tablero actual.
     *
     * @return Matriz de Strings representando el contenido del tablero.
     */
    public String[][] getContenidoTablero() {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        return cp.getTab();
    }

    /**
     * Edita el perfil del usuario actual.
     *
     * @param username Nuevo nombre de usuario (puede ser null si no se cambia).
     * @param password Nueva contraseña (puede ser null si no se cambia).
     */
    public void editarPerfil(String username, String password) {
        controladorDeDominio.editarPerfil(username, password);
    }

    /**
     * Guarda la partida actual.
     */
    public void salvarPartida() {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        cp.salvarPartida();
    }

    /**
     * Obtiene la jugada realizada por el robot.
     *
     * @return Mapa con la jugada del robot.
     */
    public Map<String, List<int[]>> getJugadaRobot() {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        Map<String, List<int[]>> jugadaRobot = cp.robotRealizarJugada();
        if(jugadaRobot == null || jugadaRobot.isEmpty()) {
            return null;
        }
        return jugadaRobot;
    }

    /**
     * Devuelve la lista de nombres de los jugadores de la partida.
     *
     * @return Lista de nombres de jugadores.
     */
    public List<String> getNombresJugadores() {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        return cp.getNombresJugadores();
    }

    /**
     * Devuelve la lista de puntuaciones de los jugadores de la partida.
     *
     * @return Lista de puntuaciones.
     */
    public List<Integer> getPuntuacionesJugadores() {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        return cp.getPuntuacionesJugadores();
    }

    /**
     * Devuelve las estadísticas del usuario actual.
     *
     * @return Objeto {@link Estadistica} con las estadísticas del usuario.
     */
    public Estadistica getEstadisticasCurrentUser() {
        return controladorDeDominio.getEstadisticasCurrentUser();
    }
    /**
     * Imprime el atril de todos los jugadores en consola (debug).
     */
    public void ImprimirAtrilJugadores() {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        cp.ImprimirAtrilJugadores();
    }

    /**
     * Elimina letras del atril del jugador actual.
     *
     * @param letras Letras a eliminar.
     */
    public void eliminarLetrasDelAtril(List<String> letras) {
        ControladorPartida cp = controladorDeDominio.getControladorPartida();
        cp.eliminarLetrasDelAtril(letras);
    }

    /**
     * Obtiene el ranking de jugadores desde el dominio.
     *
     * @return Lista de Strings con el ranking.
     */
    public List<String> getRanking() {
        return controladorDeDominio.getRanking();
    }

    /**
     * Reinicia las estadísticas del usuario actual.
     */
    public void reiniciarEstadisticas() {
        controladorDeDominio.reiniciarEstadisticas();
    }
}