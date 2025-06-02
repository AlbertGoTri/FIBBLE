package dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>
 * <strong>ControladorPartida</strong> actúa como capa de orquestación entre la
 * lógica de juego ({@link Partida}) y la interfaz de usuario ({@link Vista}).
 * Sus responsabilidades principales son:
 * </p>
 * <ul>
 *   <li>Crear, cargar y guardar partidas.</li>
 *   <li>Gestionar el ciclo de turnos y el menú de acciones durante la partida.</li>
 *   <li>Delegar en {@link Robot} la ejecución automática de jugadas cuando el
 *       jugador activo es un bot.</li>
 *   <li>Mostrar información relevante (tablero, puntuaciones, orden de
 *       jugadores, reglamento, etc.) a través de la vista.</li>
 * </ul>
 * <p>
 * Este controlador está pensado para una interfaz CLI.  Muchos métodos realizan
 * limpieza de pantalla mediante secuencias ANSI para ofrecer una experiencia
 * más clara en terminales compatibles.
 * </p>
 *
 * <h2>Convenciones</h2>
 * <ul>
 *   <li>Todas las operaciones que requieren que exista una partida activa
 *       verifican {@code partidaActual != null} y muestran un mensaje de error
 *       en caso contrario.</li>
 *   <li>Las interacciones con el usuario se realizan exclusivamente a través de
 *       la clase {@link Vista} para facilitar la futura sustitución de la capa
 *       de presentación.</li>
 *   <li>Los bucles de menú están diseñados para ser bloqueantes: cuando se
 *       devuelve el control al método llamante, la acción solicitada ya ha
 *       finalizado.</li>
 * </ul>
 *
 * @author  Yeray Franco y Darío González
 */
public class ControladorPartida {

    private static ControladorPartida instancia;

    /** Partida actualmente en curso; {@code null} si no se ha creado o ya ha finalizado. */
    private Partida partidaActual;

    /** Referencia al usuario humano que participa en la partida. */
    private Usuario humano;

    /** Referencia al usuario que está jugando actualmente. */
    private Usuario jugadorActual;

    /** Encapsula toda la entrada/salida de texto para la interfaz en consola. */
    private Vista vista;
    
    private static final Path SAVE_DIR =
            Paths.get(System.getProperty("user.dir"), "saves"); // o donde prefieras

    private int numPartidasGuardadas = 0;

    /**
     * Crea un controlador sin partida y con una vista por defecto.
     */
    public ControladorPartida(Usuario humano) {
        this.humano = humano;
        this.partidaActual = null;
        this.vista = new Vista();
    }

    // ---------------------------------------------------------------------
    //  MENÚS PRINCIPALES (CLI)
    // ---------------------------------------------------------------------

    /**
     * <em>CLI solamente</em>.  Muestra un menú con las opciones de crear, cargar
     * o salir de la aplicación.  El código se conserva comentado porque la
     * ejecución en entornos que no sean consola puede requerir otra solución
     * de UI.  Descomentar para activar la versión interactiva.
     */
    public void menuPrincipal() {
        /* ... (implementación CLI comentada) ... */
    }

    public static ControladorPartida getInstance(Usuario humano) {
        if (instancia == null) {
            instancia = new ControladorPartida(humano);
        }
        return instancia;
    }


    /**
     * Menú de gestión mostrado mientras la partida está en curso.  Permite al
     * jugador humano realizar acciones como pasar turno, colocar fichas,
     * consultar puntuaciones o guardar la partida.
     * <p>
     * El método bloquea la ejecución hasta que el usuario elija «Volver al menú
     * principal».  Cada opción delega en métodos auxiliares del controlador.
     * </p>
     */
    public void menuGestionPartida() {
        boolean salir = false;
        while (!salir) {
            vista.printlnMensaje("\n--- Menú de Gestión de Partida ---");
            vista.printlnMensaje("1. Pasar turno");
            vista.printlnMensaje("2. Realizar jugada");
            vista.printlnMensaje("3. Mostrar orden de jugadores");
            vista.printlnMensaje("4. Mostrar puntuaciones");
            vista.printlnMensaje("5. Reglamento Scrabble");
            vista.printlnMensaje("6. Salvar partida");
            vista.printlnMensaje("7. Finalizar partida");
            vista.printlnMensaje("8. Volver al menú principal");
            vista.printMensaje("Selecciona una opción: ");
        }
    }

    // ---------------------------------------------------------------------
    //  CREACIÓN / CARGA / GUARDADO DE PARTIDAS
    // ---------------------------------------------------------------------

    /**
     * Construye una nueva {@link Partida} con el usuario humano y un número
     * determinado de bots.  Se encarga de:
     * <ol>
     *   <li>Establecer el idioma y crear el diccionario.</li>
     *   <li>Instanciar los bots con el algoritmo resultante.</li>
     *   <li>Mezclar el orden de los jugadores y mostrarlo.</li>
     *   <li>Asignar el identificador y poner en marcha la partida.</li>
     * </ol>
     *
     * @param idPartida identificador único que se asignará a la partida
     * @param idioma    idioma elegido («catalan», «castellano» o «ingles»)
     * @param numBots   número de bots entre 1 y 3 (el valor se corrige si está fuera de rango)
     */
    public void crearPartida(String idPartida, String idioma, int numBots) {
        limpiarPantalla();
        
        List<Usuario> jugadores = new ArrayList<>();
        jugadores.add(this.humano);
        
        if (this.humano == null) {
            vista.printlnMensaje("No se ha encontrado un jugador humano.");
            return;
        }
        
        this.partidaActual = new Partida(jugadores);
        partidaActual.getTablero().anadirJugador(this.humano);
        
        
    
        switch (idioma) {
            case "catalan"    -> this.partidaActual.getTablero().setIdioma("catalan");
            case "castellano" -> this.partidaActual.getTablero().setIdioma("castellano");
            case "ingles"     -> this.partidaActual.getTablero().setIdioma("ingles");
            default -> {
                vista.printlnMensaje("Opción de idioma no válida. Seleccionando 'castellano' por defecto.");
                idioma = "castellano";
                this.partidaActual.getTablero().setIdioma("castellano");
            }
        }
        this.partidaActual.seleccionarIdioma(this.partidaActual.getTablero().getIdioma(), this.partidaActual.getTablero());
        Algoritmo algPartida = this.partidaActual.getAlgoritmo();

        
        if (numBots < 1 || numBots > 3) {
            vista.printlnMensaje("Número de bots no válido. Se añadirá un bot por defecto.");
            numBots = 1;
        }
        for (int i = 0; i < numBots; i++) {
            String nombreBot = "Bot" + (int) (Math.random() * 100000);
            Robot bot = new Robot(nombreBot, algPartida, this.partidaActual.getTablero());
            vista.printlnMensaje("Bot creado: " + nombreBot);
            jugadores.add(bot);
            this.partidaActual.getTablero().anadirJugador(bot);
        }
       
        for (Usuario jugador : jugadores) {
            jugador.eliminarFichas();
        }

        
        vista.printlnMensaje("\nOrden de los jugadores:\n");
        jugadores.forEach(j -> vista.printlnMensaje(j.getUsername()));
        
        this.jugadorActual = jugadores.get(0);
        vista.printlnMensaje("");

        
        this.partidaActual.setUsuarios(jugadores);
        this.partidaActual.setId(idPartida);
        vista.printlnMensaje("ID de la partida: " + idPartida);
        vista.printlnMensaje("Idioma de la partida: " + idioma);
        vista.printlnMensaje("Número de bots: " + numBots);

        salvarPartida();

        iniciarPartida();
    }

    /**
     * Devuelve el tablero actual de la partida en curso.
     * <p>
     * Si no hay partida activa, devuelve {@code null}.
     * </p>
     *
     * @return matriz bidimensional representando el tablero
     */
    public String[][] getTab() {
        
            return partidaActual.getTablero().getTab();
        
      
    }

    /**
     * Devuelve la ruta del fichero donde se guardará la partida con el ID
     * especificado.  El fichero se crea en la carpeta <code>saves/</code> con
     * extensión <code>.sav</code>.
     *
     * @param partidaId identificador de la partida (sin extensión)
     * @return ruta completa del fichero de guardado
     */
    private Path getSaveFile(String partidaId) {
        return SAVE_DIR.resolve(partidaId + ".sav");
    }

    /**
     * Carga una partida desde el fichero {@code archivo}.  El método verifica la
     * consistencia del objeto serializado e invoca
     * {@link Partida#inicializarPostCarga(Tablero)} para reconstruir miembros
     * <em>transient</em>.  La partida cargada pasa a ser la vigente.
     *
     * @param archivo archivo {@code .sav} previamente generado por
     *                {@link #salvarPartida()}
     */
    public void cargarPartida(String nombreFichero) {
        Path f = getSaveFile(nombreFichero.replaceFirst("\\.sav$", "")); // quita ".sav" si viene
        if (!Files.exists(f)) {
            vista.printlnMensaje("No existe: " + f.toAbsolutePath());
            throw new IllegalArgumentException("Archivo de partida no encontrado: " + f);
        }
        Partida cargada = Partida.cargarPartida(f.toString());
        if (cargada == null) {
            vista.printlnMensaje("No se pudo cargar la partida.");
            throw new IllegalArgumentException("Partida no válida o dañada.");
        }
        try {
            cargada.inicializarPostCarga();


        } catch (Exception ex) {
            vista.printlnMensaje("La partida está dañada: " + ex.getMessage());
            return;
        }
        this.partidaActual = cargada;
        vista.printlnMensaje("Partida cargada desde " + f.getFileName());
    }

    /**
     * Guarda la {@link Partida} actual en la carpeta <code>saves/</code> con
     * extensión <code>.sav</code>.  Si no hay partida activa, muestra un aviso.
     */
    public void salvarPartida() {
        if (partidaActual == null) {
            vista.printlnMensaje("No hay partida en curso para guardar.");
            return;
        }
        try {
            
            Files.createDirectories(SAVE_DIR);
            partidaActual.salvarPartida(getSaveFile(partidaActual.getId() + "_" + numPartidasGuardadas).toString());
            numPartidasGuardadas++;
        } catch (IOException e) {
            vista.printlnMensaje("Error al guardar la partida: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------------
    //  GESTIÓN DE TURNOS Y JUGADAS
    // ---------------------------------------------------------------------

    /**
     * Inicia la partida actual (si existe) y muestra un mensaje de confirmación
     * al usuario.  En caso contrario, informa de que no se ha creado partida.
     */
    public void iniciarPartida() {
        limpiarPantalla();
        if (partidaActual != null) {
            partidaActual.iniciarPartida();
            vista.printlnMensaje("La partida acaba de comenzar.");
        } else {
            vista.printlnMensaje("No hay partida creada.");
        }
    }

    /**
     * Bucle principal que coordina el turno de cada jugador.  Si el turno
     * pertenece a un {@link Robot}, se ejecuta su jugada automáticamente; si es
     * el turno del usuario humano, se muestra el menú de gestión.
     */
    public void gestionarTurnos() {
        if (partidaActual == null) {
            vista.printlnMensaje("No hay partida en curso.");
            return;
        }
        limpiarPantalla();
        
        puntuacionesJugadores();
        this.jugadorActual = partidaActual.getUsuarioTurnoActual();
        if (jugadorActual instanceof Robot) {
           
            partidaActual.reponerFichasUsuario(jugadorActual);
            ImprimirAtrilJugadores();
            
        } else {
            vista.printlnMensaje("Es tu turno, " + jugadorActual.getUsername() + ". Realiza tu jugada.");
            
        }
    }

    /**
     * Avanza el turno al siguiente jugador y vuelve a llamar a
     * {@link #gestionarTurnos()} para continuar la partida.
     */
    public void pasarTurno() {
        if (partidaActual != null) {
            partidaActual.pasarTurno();
            vista.printlnMensaje("Turno pasados.");
            
            gestionarTurnos();
        } else {
            vista.printlnMensaje("No hay partida en curso.");
        }
    }

    public Map<String, List<int[]>> robotRealizarJugada() {
        if (jugadorActual instanceof Robot) {
            try {
                Tablero tableroActual = partidaActual.getTablero();
                System.out.println("tableroActual");
                String[][] tablero2 = tableroActual.getTab();
                for (int i = 0; i < 15; i++) {
                    for (int j = 0; j < 15; j++) {
                        System.out.print(tablero2[i][j] + " ");
                    }
                    System.out.println();
                }
                Movimiento jugada = ((Robot) jugadorActual).jugar(tableroActual);
                //chequear que jugada no sea null
                if (jugada == null) {
                    vista.printlnMensaje("El robot no ha podido realizar una jugada válida.");
                    pasarTurno();
                    return null;
                }
                System.out.println("tablero en CPartida después de robot.jugar: ");
                String[][] tablero70 = getTab();
                for (int i = 0; i < 15; i++) {
                    for (int j = 0; j < 15; j++) {
                        System.out.print(tablero70[i][j] + " ");
                    }
                    System.out.println();
                }
                String palabraFormada = jugada.getPalabraFormada();
                List<int[]> posiciones = jugada.getPosiciones();
                //mostrar posiciones
                System.out.println("PosicionesMovvvv: ");
                for (int[] posicion : posiciones) {
                    System.out.println("Fila: " + posicion[0] + ", Columna: " + posicion[1]);
                }
                int z=0;
                Map<String, List<int[]>> mapaLetras = new HashMap<>();
                for (int i = 0; i < palabraFormada.length(); i++) {
                    String letra = String.valueOf(palabraFormada.charAt(i));
                    int[] posicion = posiciones.get(z);
    
                    // Verificar si la letra es especial según el idioma
                    String idioma = partidaActual.getTablero().getIdioma();
                    if ("catalan".equals(idioma)) {
                        if (i + 2 < palabraFormada.length() && palabraFormada.charAt(i) == 'L' && palabraFormada.charAt(i + 1) == '·') {
                            letra = letra + palabraFormada.charAt(i + 1) + palabraFormada.charAt(i + 2);
                            i += 2;
                        } else if (i + 1 < palabraFormada.length() && palabraFormada.charAt(i) == 'N' && palabraFormada.charAt(i + 1) == 'Y') {
                            letra = letra + palabraFormada.charAt(i + 1);
                            i++;
                        }
                    } else if ("castellano".equals(idioma)) {
                        if (i + 1 < palabraFormada.length() && palabraFormada.charAt(i) == 'C' && palabraFormada.charAt(i + 1) == 'H') {
                            letra = letra + palabraFormada.charAt(i + 1);
                            i++;
                        } else if (i + 1 < palabraFormada.length() && palabraFormada.charAt(i) == 'L' && palabraFormada.charAt(i + 1) == 'L') {
                            letra = letra + palabraFormada.charAt(i + 1);
                            i++;
                        } else if (i + 1 < palabraFormada.length() && palabraFormada.charAt(i) == 'R' && palabraFormada.charAt(i + 1) == 'R') {
                            letra = letra + palabraFormada.charAt(i + 1);
                            i++;
                        }
                    }
                    ++z;
                    // Agregar la posición a la lista asociada con la letra
                    mapaLetras.putIfAbsent(letra, new ArrayList<>());
                    mapaLetras.get(letra).add(new int[]{posicion[0], posicion[1]});
    
                    System.out.println("Letra: " + letra + " -> Coordenadas: (" + posicion[0] + ", " + posicion[1] + ")");
                }
                return mapaLetras;
            } catch (IllegalArgumentException e) {
                vista.printlnMensaje("Error al realizar la jugada: " + e.getMessage());
                return null;
            }
        } else {
            vista.printlnMensaje("El jugador no es un robot.");
            return null;
        }
    }

    public Usuario getWinnerUsuario() {
        return partidaActual.getWinnerUsuario();
    }

    /**
     * Realiza una jugada en la partida actual utilizando un mapa de letras y sus
     * coordenadas.  Coloca las letras en el tablero y verifica si la jugada es
     * válida, sumando puntos al jugador actual.
     *
     * @param mapaLetras mapa que asocia cada letra con una lista de coordenadas
     *                   donde se debe colocar
     * @return código de error o 0 si la jugada fue exitosa
     */
    public int realizarJugada(Map<String, List<int[]>> mapaLetras) {
        
            limpiarPantalla();
            List<int[]> coordenadasLetras = new ArrayList<>();
            int n=0;
            for(Map.Entry<String, List<int[]>> entry : mapaLetras.entrySet()) {
                // Extraer la letra y sus coordenadas
                if (entry.getValue().isEmpty()) {
                    System.out.println("Error: No se han proporcionado coordenadas para la letra " + entry.getKey());
                    return -1; // Error si no hay coordenadas
                }
                for (int[] coordenada : entry.getValue()) {
                    String letra = entry.getKey();
                    int fila = coordenada[0];
                    int columna = coordenada[1];
                    System.out.println("Letra: " + letra + " -> Coordenadas: (" + fila + ", " + columna + ")");
                    this.partidaActual.getTablero().setCasilla(letra, fila, columna); // Colocar la letra en el tablero
                    coordenadasLetras.add(coordenada);
                }
                
            }
            try {  
            n=this.partidaActual.getTablero().extraerPalabras(coordenadasLetras, true, jugadorActual);
        } catch (Tablero.ErrorJuegoException e) {
            int codigoError = e.getCodigoError();
            switch (codigoError) {
                case -1:
                    System.out.println("Error: No se puede colocar una sola letra en el tablero vacío.");
                    return -1;
                    
                case -2:
                    System.out.println("Error: Las letras no están en el centro del tablero.");
                    return -2;
                    
                case -3:
                    System.out.println("Error: Las letras no están alineadas en la misma fila o columna.");
                    return -3;
                case -4:
                    System.out.println("Error: Hay un espacio en blanco entre las letras en la misma columna.");
                    return -4;
                case -5:
                    System.out.println("Error: Hay un espacio en blanco entre las letras en la misma fila.");
                    return -5;
                case -6:
                    System.out.println("Error: Las letras no están contiguas a ninguna letra existente.");
                    return -6;
                case -7:
                    System.out.println("Error: Las letras no forman una palabra válida.");
                    return -7;
                case -8:
                    System.out.println("Error: PalabraVF no valida");
                    return -8;
                case -9:
                    System.out.println("Error: PalabraHF no válida.");
                    return -9;
                default:
                    System.out.println("Error desconocido.");
            }
        }
        
       //Marcar las posiciones ocupadas
        for (int[] posicion : coordenadasLetras) {
            System.out.println("Fila: " + posicion[0] + ", Columna: " + posicion[1]);
            this.partidaActual.getTablero().setCasillaOcupada(posicion[0], posicion[1]);
        }
        jugadorActual.sumarPuntos(n); // Sumar puntos al jugador

        return 0;
    }  

    /**
     * Finaliza la partida actual, actualiza estadísticas y libera la referencia
     * a {@code partidaActual}.
     */
    public void finalizarPartida() {
        if (partidaActual != null) {
            partidaActual.finalizarPartida();
            vista.printlnMensaje("La partida ha finalizado.");
            partidaActual = null;
        } else {
            vista.printlnMensaje("No hay partida en curso.");
        }
    }

    // ---------------------------------------------------------------------
    //  MÉTODOS DE CONSULTA / SALIDA
    // ---------------------------------------------------------------------

    /**
     * Muestra el tablero actual de la partida en curso.  Si no hay partida
     * activa, informa al usuario.
     */
    public void mostrarOrdenJugadores() {
        if (partidaActual != null) {
            vista.printlnMensaje("Orden de jugadores:");
            partidaActual.getUsuarios().forEach(u -> vista.printlnMensaje(u.getUsername()));
        } else {
            vista.printlnMensaje("No hay partida en curso.");
        }
    }

    /**
     * Muestra las puntuaciones de todos los jugadores en la partida actual.
     * Si no hay partida activa, informa al usuario.
     */
    public void puntuacionesJugadores() {
        if (partidaActual != null) {
            vista.printlnMensaje("Puntuaciones de los jugadores:\n");
            partidaActual.getUsuarios().forEach(u -> vista.printlnMensaje(u.getUsername() + ": " + u.getPuntuacionPartidaActual()));
            vista.printlnMensaje("");
        } else {
            vista.printlnMensaje("No hay partida en curso.");
        }
    }

    /**
     * Devuelve la puntuación del usuario especificado en la partida actual.
     * Si no hay partida activa o el usuario no existe, devuelve un mensaje
     * informativo.
     *
     * @param usuario nombre de usuario del jugador
     * @return puntuación del usuario o mensaje de error
     */
    public String getPuntuacionUsuario(String usuario) {
        if (partidaActual != null) {
            for (Usuario u : partidaActual.getUsuarios()) {
                if (u.getUsername().equals(usuario)) {
                    return String.valueOf(u.getPuntuacionPartidaActual());
                }
            }
        }
        return "No hay partida en curso o el usuario no existe.";
    }

    /**
     * Muestra el reglamento del juego Scrabble.  Utiliza la clase
     * {@link Reglamento} para obtener el texto y lo imprime en la vista.
     * Si no hay reglamento disponible, informa al usuario.
     */
    public void mostrarManualReglamento() {
        Reglamento reglamento = Reglamento.getInstance();
        vista.printlnMensaje(reglamento.getReglamento());
    }

    // ---------------------------------------------------------------------
    //  GETTERS / SETTERS DE APOYO (ÚTILES PARA TESTS O INTEGRACIÓN GUI)
    // ---------------------------------------------------------------------

    /** @return referencia al propio controlador (útil en patrones MVC) */
    public ControladorPartida getControladorPartida() {
        return this;
    }

    /** @return jugador cuyo turno está activo */
    public Usuario getPartidaUsuarioTurnoActual() {
        return partidaActual.getUsuarioTurnoActual();
    }

    /** @return nombre del jugador cuyo turno está activo */
    public String getPartidaNombreUsuarioTurnoActual() {
        return partidaActual.getUsuarioTurnoActual().getUsername();
    }

    /** @return representación de texto del tablero */
    public String[][] getPartidaTablero() {
        return partidaActual.getTablero().getTab();
    }

    /**
     * Devuelve una cadena con todas las fichas que quedan en el saco actual.
     * Cada letra aparece tantas veces como su frecuencia.
     */
    public String getPartidaSaco() {
        if (partidaActual != null && partidaActual.getSaco() != null) {
            Map<String, Integer> letrasFreq = partidaActual.getSaco().getLetrasFreq();
            List<String> contenidoSaco = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : letrasFreq.entrySet()) {
                contenidoSaco.addAll(java.util.Collections.nCopies(entry.getValue(), entry.getKey()));
            }
            return contenidoSaco.toString();
        }
        return "El saco está vacío o no hay partida en curso.";
    }

    /**
     * Establece el usuario humano que participa en la próxima partida.
     * @param humano instancia de {@link Usuario} no nula
     */
    public void setHumano(Usuario humano) {
        this.humano = humano;
    }

    /**
     * Devuelve una lista con los nombres de los jugadores en la partida
     * actual.  Si no hay partida activa, devuelve una lista vacía.
     *
     * @return lista de nombres de los jugadores
     */
    public List<String> getNombresJugadores() {
        List<String> nombresJugadores = new ArrayList<>();
        if (partidaActual != null) {
            for (Usuario jugador : partidaActual.getUsuarios()) {
                nombresJugadores.add(jugador.getUsername());
            }
        }
        return nombresJugadores;

    }
    
    /**
     * Devuelve una lista con las puntuaciones de todos los jugadores en la
     * partida actual.  Si no hay partida activa, devuelve una lista vacía.
     *
     * @return lista de puntuaciones de los jugadores
     */
    public List<Integer> getPuntuacionesJugadores() {
        List<Integer> puntuacionesJugadores = new ArrayList<>();
        if (partidaActual != null) {
            for (Usuario jugador : partidaActual.getUsuarios()) {
                puntuacionesJugadores.add(jugador.getPuntuacionPartidaActual());
            }
        }
        return puntuacionesJugadores;

    }

    /**
     * Imprime el atril de cada jugador en la partida actual.  Si no hay partida
     * activa, informa al usuario.
     */
    public void ImprimirAtrilJugadores() {
        if (partidaActual != null) {
            for (Usuario jugador : partidaActual.getUsuarios()) {
                System.out.println("Atril de " + jugador.getUsername() + ": " + jugador.getFichas().toString());
            }
        } else {
            vista.printlnMensaje("No hay partida en curso.");
        }
    }

    public List<String> getLetrasJugadorTurno() {
        if (partidaActual != null) {
            Usuario jugadorTurno = partidaActual.getUsuarioTurnoActual();
            if (jugadorTurno != null) {
                return jugadorTurno.getFichas();
            }
        }
        return new ArrayList<>();
    }

    public void eliminarLetrasDelAtril(List<String> letras) {
        if (partidaActual != null) {
            Usuario jugadorTurno = partidaActual.getUsuarioTurnoActual();
            if (jugadorTurno != null) {
                for (String letra : letras) {
                    jugadorTurno.eliminarLetra(letra);
                }
            }
            
            partidaActual.reponerFichasUsuario(jugadorTurno);
        }
    }

    // ---------------------------------------------------------------------
    //  UTILIDADES PRIVADAS
    // ---------------------------------------------------------------------

    /** Limpia la consola en terminales compatibles con ANSI. */
    private void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    
}
