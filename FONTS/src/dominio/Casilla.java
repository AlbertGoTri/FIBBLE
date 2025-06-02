package dominio;

import java.io.Serializable;

/**
 * Representa una casilla del tablero de Scrabble.
 * Cada casilla puede tener una bonificación, una posición específica en el tablero,
 * y puede estar ocupada o vacía.
 * También almacena la letra colocada en la casilla.
 */
public class Casilla implements Serializable {

    /**
     * Bonificación de la casilla.
     * <p>
     * Valores posibles:
     * <ul>
     * <li>0 = Sin bonificación</li>
     * <li>1 = x2 letra</li>
     * <li>2 = x3 letra</li>
     * <li>3 = x2 palabra</li>
     * <li>4 = x3 palabra</li>
     * </ul>
     */
    private int Bonificacion;

    /**
     * Serial ID para la serialización de la clase {@code Casilla}.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Columna de la casilla en el tablero.
     */
    private int col;

    /**
     * Fila de la casilla en el tablero.
     */
    private int row;

    /**
     * Indica si la casilla está ocupada.
     * También se utiliza para saber si la letra ya estaba colocada o es nueva.
     */
    private boolean ocupada;

    /**
     * Letra colocada en la casilla.
     */
    private String letra;

    /**
     * Constructor de la clase {@code Casilla}.
     * Inicializa una casilla con su bonificación, posición y estado de ocupación.
     * 
     * @param Bonificacion Bonificación de la casilla.
     * @param col          Columna de la casilla en el tablero.
     * @param row          Fila de la casilla en el tablero.
     * @param ocupada      {@code true} si la casilla está ocupada, {@code false} en caso contrario.
     */
    public Casilla(int Bonificacion, int col, int row, boolean ocupada) {
        this.Bonificacion = Bonificacion;
        this.col = col;
        this.row = row;
        this.ocupada = ocupada;
        this.letra = "\0";
    }

    /**
     * Constructor de copia de la clase {@code Casilla}.
     * Crea una nueva casilla como copia de una casilla existente.
     * 
     * @param original La casilla a copiar.
     */
    public Casilla(Casilla original) {
        this.Bonificacion = original.Bonificacion;
        this.col = original.col;
        this.row = original.row;
        this.ocupada = original.ocupada;
        this.letra = original.letra;
    }

    /**
     * Obtiene la letra colocada en la casilla.
     * 
     * @return La letra colocada en la casilla.
     */
    public String getLetra() {
        return this.letra;
    }

    /**
     * Obtiene la bonificación de la casilla.
     * 
     * @return La bonificación de la casilla.
     */
    public int getBonificacion() {
        return this.Bonificacion;
    }

    /**
     * Obtiene la columna de la casilla en el tablero.
     * 
     * @return La columna de la casilla.
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Obtiene la fila de la casilla en el tablero.
     * 
     * @return La fila de la casilla.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Verifica si la casilla está ocupada.
     * 
     * @return {@code true} si la casilla está ocupada, {@code false} en caso contrario.
     */
    public boolean isOcupada() {
        return this.ocupada;
    }

    /**
     * Establece el estado de ocupación de la casilla.
     * 
     * @param ocupada {@code true} si la casilla debe marcarse como ocupada, {@code false} en caso contrario.
     */
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    /**
     * Establece la bonificación de la casilla.
     * 
     * @param i La bonificación a establecer.
     */
    public void setBonificacion(int i) {
        this.Bonificacion = i;
    }

    /**
     * Coloca una letra en la casilla.
     * Si la casilla ya tiene una letra colocada y está marcada como ocupada, lanza una excepción.
     * 
     * @param letra La letra a colocar en la casilla.
     * @throws IllegalArgumentException Si la casilla ya tiene una letra colocada.
     */
    public void ColocarFicha(String letra) {
        if (!this.letra.equals("\0") && this.isOcupada()) {
            throw new IllegalArgumentException("Error: La casilla ya tiene una letra colocada.");
        }
        this.letra = letra;
        this.ocupada = false;
    }
}