package dominio;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * Clase Usuario: 
 * <p>
 * Esta clase representa a un usuario del juego.
 * </p>
 * <p>
 * <b>Propósito:</b> Almacenar la información del usuario, como su nombre, contraseña, estadísticas, puntuación y fichas.
 * </p>
 */
public class Usuario implements Serializable {

    /** Nombre de usuario. */
    private String username;
    
    /** Contraseña de usuario. */
    private String password; 

    /** Estadísticas del usuario. */
    private Estadistica estadisticas; //stats del usuario.

    /** Mejor palabra de la partdida que acaba de jugar. */
    private String bestWordGame; 

    /** Puntuación de la partida actual. */
    private int gamePoints; 
    /** Indica si el usuario ha ganado la partida o no en caso contrario. */
    private boolean winner; 

    /** Número de palabras que ha jugado el usuario en la partida actual. */
    private int numWordsGame; 
    /** Partida actual. */
    private Partida currentGame; 
    /** Atril de del usuario. */
    private List<String> fichas_usuario; 

    /** Indica si el usuario es robot o no en caso contrario. */
    private boolean esRobot;

    /** 
     * Serial ID para la serialización de la clase Usuario.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor de la clase Usuario.
     * 
     * @param name Nombre de usuario.
     * @param password Contraseña del usuario.
     * @throws IllegalArgumentException Si el nombre de usuario o la contraseña son nulos o vacíos.
     */
    public Usuario(String name, String password) {
        if(name == null || name.trim().isBlank() && !password.trim().isBlank()) {
            throw new IllegalArgumentException("Error Usuario: El nombre de usuario no puede ser vacío\n"); 
        }
        if(password == null || password.trim().isBlank() && !name.trim().isBlank()) {
            throw new IllegalArgumentException("Error Usuario: La contraseña no puede ser vacía\n"); 
        }
        if((name == null || name.trim().isBlank()) && (password == null || password.trim().isBlank())) {
            throw new IllegalArgumentException("Error Usuario: Nombre de usuario y contraseñas no pueden ser vacíos\n");
        }
        this.username = name.trim();
        this.password = password.trim();
        this.estadisticas = new Estadistica();
        this.bestWordGame = null;
        this.gamePoints = 0;
        this.numWordsGame = 0;
        this.currentGame = null; //inicialmente no tiene partida.
        this.fichas_usuario = new ArrayList<>(); //no tenemos fichas hasta que no empiece la partida.
        this.esRobot = false;
    }

    /**
     * Establece el nombre de usuario.
     * 
     * @param name Nuevo nombre de usuario.
     * @throws IllegalArgumentException Si el nombre de usuario es nulo o vacío.
     */
    public void setUsername(String name) {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Error Usuario: El nombre de usuario no puede ser vacío\n"); 
        }
        this.username = name.trim();
    }

    /** 
     * Establece la contraseña del usuario.
     * 
     * @param pswd Nueva contraseña.
     * @throws IllegalArgumentException Si la contraseña es nula o vacía.
     */
    public void setPassword(String pswd) {
        if(password == null || pswd.trim().isEmpty()) {
            throw new IllegalArgumentException("Error Usuario: La contraseña no puede ser vacía\n"); 
        }
        this.password = pswd.trim();
    }

    /**
     * Establece las estadísticas del usuario.
     * 
     * @param estadisticas Objeto Estadistica que contiene las estadísticas del usuario.
     */
    public void setEstadisticas(Estadistica estadisticas) {
        this.estadisticas = estadisticas;
    }

    /**
     * Establece las fichas del usuario. 
     * 
     * @param fichas Lista de fichas a asignar al usuario. 
     */
    public void setFichas(List<String> fichas) {
        this.fichas_usuario = fichas;
    }

    /**
     * Obtiene el nomrbre de usuario.
     * 
     * @return Nombre de usuario. 
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return Contraseña del usuario.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Obtiene las estadísticas del usuario.
     * 
     * @return Objeto Estadistica que contiene las estadísticas del usuario.
     */
    public Estadistica getStats() {
        return this.estadisticas;
    }

    /**
     * Obtiene la partida actual del usuario. 
     * 
     * @return Objeto Partida que representa la partida actual del usuario.
     */
    public Partida getPartidaActual() {
        return this.currentGame.currentGame();
    }

    /**
     * Obtiee el identificador de la partida actual.
     * 
     * @reutrn Identificador de la partida actual.
     */
    public String getIdPartidaActual() {
        return currentGame.getId();
    }
    
    /**
     * Obtuiene la puntuación de la partida actual. 
     * 
     * @return Variable gamePoints que representa la puntuación de la partida actual.
     */
    public int getPuntuacionPartidaActual() {
        return this.gamePoints;
    }

    /**
     * Obtiene el ganador de la partida actual en la que está el usuario. 
     * 
     * @return Objeto Usuario que representa al ganador de la partida actual.
     */
    public Usuario getWinner() {
        return this.currentGame.getWinnerUsuario();
    }
    
    /**
     * Determina si el usuario es el ganador de la partida.
     * 
     * @return true si el usaurio es el ganador, false en caso contrario.
     */
    public boolean isWinner() {
        Usuario gameWinner = this.getWinner() ;
        if(this == gameWinner) return true;
        else return false;
    }

    /**
     * Modifica el nombre de usuario (en el caso de que sea válido). 
     * 
     * @param newName Nuevo nombre de usuario.
     * @throws IllegalArgumentException Si el nuevo nombre de usuario es nulo o vacío.
     */
    public void changeUsername(String newName) {
        //analizamos el caso de que sea el mismo nombre
        if(newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Error Usuario: El nuevo nombre de usuario no puede ser vacío\n"); 
        }
        this.username = newName.trim();
    }

    /**
     * Modifica la contraseña del usuario (en el caso de que sea válida).
     * 
     * @param newPassword Nueva contraseña.
     * @throws IllegalArgumentException Si la nueva contraseña es nula o vacía.
     */
    public void changePassword(String newPassword) {
        if(newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Error Usuario: La nueva contraseña no puede ser vacía\n"); 
        }
        this.password = newPassword.trim();
    }

    /**
     * Obtiene las fichas que actualmente dispone el usuario en su atril. 
     * 
     * @return Lista de fichas del usuario.
     * @throws IllegalArgumentException Si el usuario no tiene fichas en su atril.
     */ 
    public List<String> getFichas() {
        if(fichas_usuario == null) return new ArrayList<>();
        
        return fichas_usuario;
    }
 
    /**
     * Añade una ficha al atril del usuario.
     * 
     * @param ficha Ficha a añadir.
     * @throws IllegalArgumentException Si la ficha es nula o vacía, si el atril ya está lleno y la ficha es válida o está lleno el atril y ficha es vacía.

     */
    public void agregarFicha(String ficha) {
        if(ficha != null && !ficha.trim().isBlank()  && fichas_usuario.size() < 7) fichas_usuario.add(ficha.trim()); 
        else if(ficha == null || ficha.trim().isBlank()) {
            throw new IllegalArgumentException("Error Usuario: La ficha que se quiere añadir al atril tiene que ser no nula\n"); 
        } 
        else if(fichas_usuario.size() >= 7){
            throw new IllegalArgumentException("Error Usuario: Ya tenemos 7 fichas en el atril\n"); 
        }
        else if((ficha.trim().isBlank()) || fichas_usuario.size() >= 7) {
            throw new IllegalArgumentException("Error Usuario: No se pueden añadir ficha vacía además atril ya está lleno\n"); 
        }
        //System.out.println("Error clase Usuario: No podemos añadir ficha al atril\n"); 
    }
    
    /**
     * Obtiene el número de fichas que tiene el usuario en su atril.
     * 
     * @return Número de fichas en el atril del usuario.
     */
    public int getCantidadFichas() {
        return fichas_usuario.size(); 
    }
    
    /**
     * Elimina todas las fichas del atril del usuario.
     */
    public void eliminarFichas() {
        this.fichas_usuario.clear();
    }

    /**
     * Suma puntos a la puntuación gamePoints del usuario (si es posible).
     * 
     * @param puntos Puntos a sumar.
     */
    public void sumarPuntos(int puntos) {
        if(puntos >= 0) {
            this.gamePoints += puntos;
        }
    }

    /**
     * Resta puntos a la puntuación gamePoints del usuario (si es posible).
     * 
     * @param penalización Puntos a restar.
     */
    public void restarPuntos(int penalizacion) {
        if(this.gamePoints - penalizacion > 0) {
            this.gamePoints -= penalizacion;
        }
        else this.gamePoints = 0; 
    }

    /**
     * Acutlaiza las estadísticas del usuario después de una partida. 
    */ 
    public void upgradeEstadisticasUser() {
        this.estadisticas.updateEstadisticasJugador(this.gamePoints, this.bestWordGame, this.winner, this.numWordsGame); 
    }  


    /**
     * Obtiene si el usuario es un robot o no.
     * 
     * @return true si el usuario es un robot, false en caso contrario.
     */
    public boolean getesRobot() {
        return this.esRobot; 
    }

    /**
     * Establece si el usuario es un robot o no.
     * 
     * @param nuevoValor Valor a establecer (indica si ese user es robot o no).
     */
    public void setEsRobot(boolean nuevoValor) {
        this.esRobot = nuevoValor;
    }
    
    /**
     * Establece la puntuación de la partida actual.
     * 
     * @param puntos Puntos a establecer.
     */
    public void setPuntuacionPartidaActual(int puntos) {
        this.gamePoints = puntos;
    }

    /**
     * Determina si el usuario no tiene fichas en su atril.
     * 
     * @return true si el usuario no tiene fichas, false en caso contrario.
     */
    public boolean getNoTieneFichas() {
        return this.fichas_usuario.isEmpty();
    }

    public void eliminarLetra(String letra) {
        if(letra == null || letra.trim().isBlank()) {
            throw new IllegalArgumentException("Error Usuario: La letra a eliminar no puede ser nula o vacía\n"); 
        }
        if(fichas_usuario.contains(letra)) {
            fichas_usuario.remove(letra);
        } else {
            throw new IllegalArgumentException("Error Usuario: La letra no está en el atril del usuario\n"); 
        }
    }
    /**
     * Retorna el número de palabras jugadas en la partida actual.
     * 
     * @return el número de palabras jugadas en la partida actual.
     */
    public int getNumWordsGame() {
        return this.numWordsGame;
    }

    /**
     * Retorna la palabra más larga jugada en la partida actual.
     *
     * @return la palabra más larga jugada en la partida actual.
     */
    public String getBestWordGame() {
        return this.bestWordGame;
    }
}
