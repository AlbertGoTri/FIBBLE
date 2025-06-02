package dominio;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Representa una partida de Scrabble (o variante similar) gestionando usuarios,
 * saco de fichas, tablero, diccionario y estadísticas.  Incluye la lógica para
 * iniciar, pausar y finalizar la partida, así como la (de)serialización para
 * persistir el estado en disco.
 * <p>
 * <b>Serialización:</b> La clase implementa {@link java.io.Serializable} para
 * poder guardarse mediante {@code ObjectOutputStream} y restaurarse con
 * {@code ObjectInputStream}.  Los campos marcados como {@code transient} se
 * recrean tras la carga mediante {@link #inicializarPostCarga(Tablero)}.
 * </p>
 *
 * @author  Yeray Franco
 */
public class Partida implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Identificador único de la partida (por ejemplo, UUID). */
    private String id;
    /** Lista de jugadores (usuarios humanos o bots). */
    private List<Usuario> usuarios;
    /** Saco con las fichas restantes. */
    private Saco saco;
    /** Índice del jugador cuyo turno está activo. Empieza en 0. */
    private int turnoActual;
    /** Marca si la partida está en curso (true) o finalizada/pendiente (false). */
    private boolean partidaEnCurso;
    /** Idioma seleccionado para fichas y diccionario (es, en, ca, ...). */
    private String idioma;
    /** Algoritmo de búsqueda de jugadas (solo para bots). No se serializa. */
    private transient Algoritmo algoritmo;
    /** Vista de interacción (CLI/GUI). No se serializa. */
    private transient Vista vista;
    /** Diccionario usado para validar palabras. */
    private Diccionario diccionario;
    /** Estadísticas acumuladas de la partida. */
    private Estadistica estadisticas;
    /** Representación lógica del tablero. */
    private Tablero tablero;

    /**
     * Crea una nueva partida con la lista de usuarios indicada.
     * Se inicializa un tablero vacío, un saco sin fichas y la vista por defecto.
     * La partida no se pone en marcha hasta llamar a {@link #iniciarPartida()}.
     *
     * @param usuarios lista de jugadores que disputarán la partida (tamaño &ge; 2)
     * @throws NullPointerException si {@code usuarios} es {@code null}
     */
    public Partida(List<Usuario> usuarios) {
        if (usuarios == null) throw new NullPointerException("La lista de usuarios no puede ser null");
        this.usuarios = usuarios;
        this.tablero = new Tablero();
        this.saco = new Saco();
        this.turnoActual = 0;
        this.partidaEnCurso = false;
        this.vista = new Vista();
        this.estadisticas = new Estadistica();
    }

    /** @return lista inmutable de usuarios que participan en la partida */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @return devuelve la propia instancia; sintáctico-azúcar para cadenas de llamada
     */
    public Partida currentGame() {
        return this;
    }

    /**
     * Carga una partida previamente guardada desde un archivo.
     *
     * @param archivo ruta absoluta o relativa al fichero {@code .ser}
     * @return instancia de {@code Partida} cargada o {@code null} si hubo error
     */
    public static Partida cargarPartida(String archivo) {
        try (ObjectInputStream ois =
                new ObjectInputStream(Files.newInputStream(Paths.get(archivo)))) {
            System.out.println("Partida cargada desde " + archivo);
            return (Partida) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar la partida: " + e.getMessage());
            return null;
        }
    }

    /**
     * Asigna el identificador único de la partida.
     * @param id UUID o cadena identificativa
     */
    public void setId(String id) {
        this.id = id;
    }

    /** @return identificador único actual de la partida */
    public String getId() {
        return id;
    }

    /**
     * Guarda el estado completo de la partida en disco.
     *
     * @param archivo ruta de destino donde se escribirá el objeto serializado
     */
    public void salvarPartida(String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(this);
            vista.printlnMensaje("Partida guardada correctamente en " + archivo);
        } catch (IOException e) {
            vista.printlnMensaje("Error al guardar la partida: " + e.getMessage());
        }
    }

    /**
     * Reconstruye los campos {@code transient} y otras dependencias tras cargar
     * la partida desde disco.
     *
     * @throws IllegalStateException si el idioma no está inicializado
     */
    public void inicializarPostCarga() {
        this.vista = new Vista();

        if (this.idioma == null) {
            throw new IllegalStateException("El idioma no se ha inicializado correctamente al cargar la partida.");
        }

        if (this.tablero == null) {
            this.tablero = new Tablero();
        }

        if (this.diccionario == null) {
            this.diccionario = new Diccionario();
            this.diccionario.setCargarDiccionario(this.idioma);
        }

        this.algoritmo = new Algoritmo(diccionario, tablero, this.idioma);


        for (Usuario u : usuarios) {
            if (u instanceof Robot) {
                ((Robot) u).setAlgoritmo(this.algoritmo);
            }
        }

        if (this.saco == null) {
            this.saco = new Saco();
            this.saco.setLlenarSaco(this.idioma);
        }

        this.partidaEnCurso = true;
    }

    /**
     * Pone la partida en marcha: llena las fichas iniciales de cada jugador y
     * muestra en la vista de salida a quién le toca mover.
     * Ignora la llamada si la partida ya estaba en curso.
     */
    public void iniciarPartida() {
        if (partidaEnCurso) {
            vista.printlnMensaje("La partida ya está en curso.");
            return;
        }
        partidaEnCurso = true;
        vista.printlnMensaje("Partida iniciada con ID: " + id);
        vista.printlnMensaje("");

        for (Usuario usuario : usuarios) {
            for (int i = 0; i < 7; i++) {
                String letraRobada = saco.getRobarLetra();
                if (letraRobada != null) {
                    usuario.agregarFicha(letraRobada);
                }
            }
        }
        vista.printlnMensaje("Turno de: " + usuarios.get(turnoActual).getUsername());
        vista.printlnMensaje("");
    }

    /**
     * Configura el idioma de la partida (carga fichas y diccionario
     * correspondientes).
     *
     * @param idioma código ISO del idioma ej. "castellano", "ingles", "catalan".
     * @param tablero tablero asociado del que se servirán los bots
     */
    public void seleccionarIdioma(String idioma, Tablero tablero) {
        this.idioma = idioma;
        llenarSaco(idioma);
        cargarDiccionario(idioma, tablero);
    }

    /**
     * Avanza el turno al siguiente jugador, si la partida está en curso.
     */
    public void pasarTurno() {
        if (partidaEnCurso) {
            turnoActual = (turnoActual + 1) % usuarios.size();
        } else {
            vista.printlnMensaje("No hay partida en curso.");
        }
    }

    /**
     * Repone las fichas del {@code usuario} hasta tener 7 robando del saco.
     *
     * @param usuario jugador al que se le reponen las fichas
     */
    public void reponerFichasUsuario(Usuario usuario) {
        Saco saco = getSaco();
        while (usuario.getFichas().size() < 7 && !saco.estaVacio()) {
            String letra = saco.getRobarLetra();
            if (letra == null) break;
            usuario.agregarFicha(letra);
        }
    }

    /**
     * Finaliza la partida, actualiza puntuaciones y muestra el resultado final.
     */
    public void finalizarPartida() {
        partidaEnCurso = false;
        vista.printlnMensaje("Partida finalizada. ID: " + id);
        for (Usuario usuario : usuarios) {
            boolean winner = false;
            if (getWinnerUsuario() == usuario) {
                winner = true;
                vista.printlnMensaje(usuario.getUsername() + " ha ganado la partida!");
            } else {
                vista.printlnMensaje(usuario.getUsername() + " ha perdido la partida.");
            }
            estadisticas.updateEstadisticasJugador(usuario.getPuntuacionPartidaActual(),usuario.getBestWordGame(), winner, usuario.getNumWordsGame());
            vista.printlnMensaje(usuario.getUsername() + ": " + usuario.getPuntuacionPartidaActual());
        }
    }

    /**
     * Llena el saco con las fichas del idioma indicado.
     * @param idioma código ISO ("es", "en", ...)
     */
    public void llenarSaco(String idioma) {
        saco.setLlenarSaco(idioma);
        vista.printlnMensaje("Saco llenado con fichas del idioma: " + idioma);
        vista.printlnMensaje("");
    }

    /**
     * Carga el diccionario en un hilo con animación de *spinner* en consola.
     * Posteriormente crea el {@link Algoritmo} y lo asigna a los bots.
     *
     * @param idioma1 idioma del diccionario
     * @param tablero  tablero al que asociar el algoritmo
     */
    public void cargarDiccionario(String idioma1, Tablero tablero) {
        Thread spinnerThread = new Thread(() -> {
            String[] spinner = {"|", "/", "-", "\\"};
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print(spinner[i] + "\rCargando diccionario... ");
                i = (i + 1) % spinner.length;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        spinnerThread.start();

        Diccionario diccionario1 = new Diccionario();
        diccionario1.setCargarDiccionario(idioma1);
        this.algoritmo = new Algoritmo(diccionario1, tablero, idioma1);

        spinnerThread.interrupt();
        System.out.print("\r");
        vista.printlnMensaje("Diccionario cargado con el idioma: " + idioma1);
        vista.printlnMensaje("");
    }

    /** @return índice del turno actual (0-based) */
    public int getTurnoActual() {
        return this.turnoActual;
    }

    /** @return algoritmo usado por los bots para sugerir jugadas */
    public Algoritmo getAlgoritmo() {
        return this.algoritmo;
    }

    /**
     * Determina el ganador según reglas: sin fichas cuando el saco está vacío o
     * mayor puntuación si ya no quedan jugadas disponibles.
     *
     * @return jugador ganador o {@code null} si hay empate o aún no concluye
     */
    public Usuario getWinnerUsuario() {
        for (Usuario usuario : usuarios) {
            List<String> fichas = usuario.getFichas();
            if (usuario.getNoTieneFichas() && saco.estaVacio()) {
                return usuario;
            }
            usuario.setFichas(fichas);
        }
        
        System.out.println("getWinnerUsuario de partida 1");
        boolean hayJugadasDisponibles = hayJugadasDisponibles();
        System.out.println("getWinnerUsuario de partida 2");
        if (!hayJugadasDisponibles) {
            Usuario ganador = usuarios.get(0);
            for (Usuario usuario : usuarios) {
                if (usuario.getPuntuacionPartidaActual() > ganador.getPuntuacionPartidaActual()) {
                    ganador = usuario;
                } else if (usuario.getPuntuacionPartidaActual() == ganador.getPuntuacionPartidaActual()) {
                    return null;
                }
            }
            return ganador;
        }
        return null;
    }

    /**
     * Comprueba si algún jugador tiene una jugada válida disponible.
     * @return {@code true} si existe al menos una jugada; en caso contrario {@code false}
     */
    public boolean hayJugadasDisponibles() {
        for (Usuario usuario : usuarios) {
            if (algoritmo.calcularMejorJugada(usuario) != null) {
                return true;
            }
        }
        return false;
    }

    /** @return jugador cuyo turno está activo */
    public Usuario getUsuarioTurnoActual() {
        return usuarios.get(turnoActual);
    }

    /**
     * Sustituye la lista completa de usuarios por otra (por ejemplo, tras cargar
     * una partida) manteniendo el orden.
     *
     * @param usuarios nueva lista de jugadores
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /** @return {@code true} si la partida está en curso */
    public boolean isPartidaEnCurso() {
        return partidaEnCurso;
    }

    /** Activa o desactiva el estado de partida en curso. 
     * * @param partidaEnCurso boolean para activar o desactivar el estado de partida en curso
    */
    public void setPartidaEnCurso(boolean partidaEnCurso) {
        this.partidaEnCurso = partidaEnCurso;
    }

    /** @return referencia al saco actual */
    public Saco getSaco() {
        return saco;
    }

    /** @param saco nuevo saco (principalmente para depuración o tests) */
    public void setSaco(Saco saco) {
        this.saco = saco;
    }

    /** @return idioma configurado */
    public String getIdioma() {
        return idioma;
    }

    /** @param idioma idioma a establecer (sin efectos colaterales) */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /** @return diccionario en uso */
    public Diccionario getDiccionario() {
        return diccionario;
    }

    /** @param diccionario diccionario a usar para validar palabras */
    public void setDiccionario(Diccionario diccionario) {
        this.diccionario = diccionario;
    }

    /** @return tablero de la partida */
    public Tablero getTablero() {
        return tablero;
    }

    /** @param tablero tablero que se asignará a la partida */
    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }
}
