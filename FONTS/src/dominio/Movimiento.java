package dominio;

import java.util.List;

/**
 * La clase Movimiento representa un movimiento realizado en el tablero de Scrabble.
 * Contiene información sobre las fichas jugadas, la posición inicial, la orientación,
 * la puntuación obtenida y la palabra formada.
 */
public class Movimiento {
    /**
     * Lista de fichas utilizadas en el movimiento.
     */
    private List<String> fichas;

    /**
     * Lista de posiciones ocupadas por las fichas en el tablero.
     */
    private List<int[]> posiciones;

    /**
     * Fila inicial del movimiento en el tablero.
     */
    private int fila;

    /**
     * Columna inicial del movimiento en el tablero.
     */
    private int columna;

    /**
     * Indica si el movimiento es horizontal.
     */
    private boolean esHorizontal;

    /**
     * Puntuación obtenida con el movimiento.
     */
    private int puntuacion;

    /**
     * Palabra formada con el movimiento.
     */
    private String palabraFormada;

    /**
     * Constructor de la clase Movimiento.
     *
     * @param fichas         Lista de fichas utilizadas en el movimiento.
     * @param fila           Fila inicial del movimiento en el tablero.
     * @param columna        Columna inicial del movimiento en el tablero.
     * @param esHorizontal   {@code true} si el movimiento es horizontal, {@code false} si es vertical.
     * @param puntuacion     Puntuación obtenida con el movimiento.
     * @param palabraFormada Palabra formada con el movimiento.
     */
    public Movimiento(List<String> fichas, int fila, int columna, boolean esHorizontal, int puntuacion, String palabraFormada) {
        this.fichas = fichas;
        this.fila = fila;
        this.columna = columna;
        this.esHorizontal = esHorizontal;
        this.puntuacion = puntuacion;
        this.palabraFormada = palabraFormada;
        this.posiciones = null; 

    }

    /**
     * Obtiene la lista de fichas utilizadas en el movimiento.
     *
     * @return Lista de fichas utilizadas.
     */
    public List<String> getFichas() {
        return fichas;
    }

    /**
     * Obtiene la fila inicial del movimiento.
     *
     * @return Fila inicial.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Obtiene la columna inicial del movimiento.
     *
     * @return Columna inicial.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Obtiene la lista de posiciones ocupadas por las fichas en el tablero.
     *
     * @return Lista de posiciones ocupadas.
     */
    public List<int[]> getPosiciones() {
        return posiciones;
    }

    /**
     * Verifica si el movimiento es horizontal.
     *
     * @return {@code true} si el movimiento es horizontal, {@code false} si es vertical.
     */
    public boolean esHorizontal() {
        return esHorizontal;
    }

    /**
     * Obtiene la puntuación obtenida con el movimiento.
     *
     * @return Puntuación obtenida.
     */
    public int getPuntuacion() {
        return puntuacion;
    }

    /**
     * Obtiene la palabra formada con el movimiento.
     *
     * @return Palabra formada.
     */
    public String getPalabraFormada() {
        return palabraFormada;
    }

    /**
     * Establece la puntuación obtenida con el movimiento.
     *
     * @param puntuacion Nueva puntuación.
     */
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * Establece la palabra formada con el movimiento.
     *
     * @param posiciones Nueva palabra formada.
     */
    public void setCoordenadas(List<int[]> posiciones) {
        this.posiciones = posiciones;
    }

    /**
     * Devuelve una representación en forma de cadena del movimiento.
     *
     * @return Representación en forma de cadena del movimiento.
     */
    @Override
    public String toString() {
        return "Movimiento{" +
                "palabra='" + palabraFormada + '\'' +
                ", fila=" + fila +
                ", columna=" + columna +
                ", horizontal=" + esHorizontal +
                ", puntuacion=" + puntuacion +
                '}';
    }
}