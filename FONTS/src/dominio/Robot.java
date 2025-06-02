package dominio;

import java.util.List;

/**
 * La clase Robot representa un jugador automatizado en el juego de Scrabble.
 * Extiende la clase Usuario y utiliza un algoritmo para calcular y realizar movimientos en el tablero.
 */
public class Robot extends Usuario {
    /**
     * Algoritmo utilizado por el robot para calcular la mejor jugada.
     * Es marcado como {@code transient} para evitar su serialización.
     */
    private transient Algoritmo algoritmo;

    /**
     * Tablero de juego en el que el robot realiza sus movimientos.
     */
    private Tablero tablero;

    /**
     * Constructor de la clase Robot.
     *
     * @param username Nombre de usuario del robot.
     * @param algoritmo Algoritmo utilizado para calcular las jugadas.
     * @param tablero Tablero de juego asociado al robot.
     */
    public Robot(String username, Algoritmo algoritmo, Tablero tablero) {
        super(username, "admin");
        this.algoritmo = algoritmo;
        this.tablero = tablero;
    }

    /**
     * Realiza un movimiento en el tablero utilizando el algoritmo para calcular la mejor jugada.
     * Si no hay movimientos posibles, se muestra un mensaje indicando esta situación.
     */
    public Movimiento jugar(Tablero tablero) {
        this.tablero = tablero;

        System.out.println("Turno del robot: " + this.getUsername());

        Movimiento mejor = algoritmo.calcularMejorJugada(this);
        System.out.println("Mejor movimiento: " + mejor);
        if (mejor != null) {
            ejecutarMovimiento(mejor);
        } else {
            System.out.println("No hay movimientos posibles para el Robot.");
        }
        return mejor;
    }

    /**
     * Ejecuta un movimiento en el tablero.
     * Coloca las fichas en las posiciones correspondientes y actualiza la puntuación del robot.
     *
     * @param movimiento Movimiento que se desea ejecutar.
     */
    private void ejecutarMovimiento(Movimiento movimiento) {
        try {
            System.out.println("tablero en ejecutarMovimiento:");
            String[][] tablero80 = this.tablero.getTab();
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    System.out.print(tablero80[i][j] + " ");
                }
                System.out.println();
            }
            // Imprimir las fichas recibidas en el movimiento
            List<String> fichas = movimiento.getFichas();
            //System.out.println("Fichas recibidas: " + fichas);
            
            // Imprimir las posiciones del movimiento
            List<int[]> posiciones = movimiento.getPosiciones();
            System.out.println("Posiciones del movimiento:");
            for (int[] posicion : posiciones) {
                System.out.println("Fila: " + posicion[0] + ", Columna: " + posicion[1]);
            }
            
            // Colocar las fichas en el tablero
            int fila = movimiento.getFila();
            int columna = movimiento.getColumna();
            boolean esHorizontal = movimiento.esHorizontal();
            //System.out.println("puntuacion " +this.getPuntuacionPartidaActual());
            
            System.out.println("tablero en ejecutarMovimiento2:");
            String[][] tablero81 = this.tablero.getTab();
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    System.out.print(tablero81[i][j] + " ");
                }
                System.out.println();
            }

            for (String ficha : fichas) {
                tablero.setCasilla(ficha, fila, columna); // Colocar la ficha en el tablero
                tablero.setCasillaOcupada(fila, columna); // Marcar la casilla como ocupada

                // Incrementar fila o columna según la orientación
                if (esHorizontal) {
                    columna++;
                } else {
                    fila++;
                }
            }
            for (String ficha : fichas) {
                this.getFichas().remove(ficha);
            }
            System.out.println("tablero en ejecutarMovimiento3:");
            String[][] tablero82 = this.tablero.getTab();
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    System.out.print(tablero82[i][j] + " ");
                }
                System.out.println();
            }
            
            int bonificacion=tablero.extraerPalabras(movimiento.getPosiciones(), true, this);
            movimiento.setPuntuacion(bonificacion);
            this.sumarPuntos(bonificacion);
            for (int[] posicion : posiciones) {
                tablero.setBonificacionCasilla(posicion[0], posicion[1], 0); // Clear the bonus after using it
            }

        }catch(Exception e){
            System.out.println("Error al ejecutar el movimiento: " + e.getMessage());
        }
    }

    /**
     * Establece un nuevo algoritmo para el robot.
     *
     * @param algoritmo Nuevo algoritmo que se desea asignar al robot.
     */
    public void setAlgoritmo(Algoritmo algoritmo) {
        this.algoritmo = algoritmo;
    }
}