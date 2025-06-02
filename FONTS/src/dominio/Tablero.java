package dominio;

import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Representa el tablero de un juego de Scrabble.
 * Contiene la lógica para inicializar el tablero, gestionar las casillas,
 * y verificar las reglas del juego.
 */

public class Tablero implements Serializable {

    /**
     * Número de columnas del tablero.
     */
    private int cols;

    /**
     * Número de filas del tablero.
     */
    private int rows;

    /**
     * Matriz que representa las casillas del tablero.
     */
    public Casilla[][] matrizOcupacio;

    /**
     * Idioma actual del tablero.
     */
    private String idioma;

    /**
     * Cuantas palabras ha jugado el humano
     */
    private int contadorHumano;

    /**
     * Identificador único de versión para la serialización de la clase {@code Tablero}.
     * Este valor asegura la compatibilidad entre diferentes versiones de la clase
     * durante el proceso de serialización y deserialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * mapa jugadores de la partida y su palabra mas larga
     */
    private Map<Usuario, String> mapaJugadoresPalabraLarga = new HashMap<>();



    // ANSI escape codes para colores
    /*private static final String ANSI_ORANGE_BACKGROUND = "\u001B[103m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BACKGROUND = "\u001B[47m";
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_PURPLE_BACKGROUND = "\u001B[105m";
    private static final String ANSI_CYAN_BACKGROUND = "\u001B[106m";
    private static final String ANSI_BROWN_BACKGROUND = "\u001B[43m"; // Marrón
    private static final String ANSI_BLACK = "\u001B[30m"; // Negro*/

    /**
     * Constructor de la clase {@code Tablero}.
     * Inicializa un tablero de 15x15 con casillas vacías y sin idioma definido.
     */
    public Tablero() {
        this.cols = 15;
        this.rows = 15;
        this.matrizOcupacio = new Casilla[15][15];
        this.idioma = null; // Idioma por defecto
        mapaJugadoresPalabraLarga = new HashMap<>();
        this.contadorHumano=0;
        inicializarMatriz();
    }

    /**
     * Constructor de copia para la clase {@code Tablero}.
     * Crea un nuevo tablero como copia de otro tablero existente.
     * 
     * @param original El tablero original a copiar.
     */
    public Tablero(Tablero original) {
        this.cols = original.cols;
        this.rows = original.rows;
        this.idioma = original.idioma;
        // Copia profunda de la matriz de casillas
        this.matrizOcupacio = new Casilla[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.matrizOcupacio[i][j] = new Casilla(original.matrizOcupacio[i][j]);
            }
        }
        // Copia el mapa de palabras largas de los jugadores si es necesario
        this.mapaJugadoresPalabraLarga = new HashMap<>(original.mapaJugadoresPalabraLarga);
    }

    /**
     * Inicializa la matriz del tablero con las casillas correspondientes
     * y sus bonificaciones.
     */
    public void inicializarMatriz() {
        for (int row = 1; row < 16; row++) {
            for (int col = 1;col < 16; col++) {
                int tiposBonus=0;
                if ((row == 1 || row == 15) && (col == 1 || col == 8 || col == 15)) {
                    tiposBonus = 4; // Triple word score
                } else if ((row == 1 || row == 15) && (col == 4 || col == 12)) {
                    tiposBonus=1; // Double letter score
                } else if ((row == 3 || row == 13) && (col == 7 || col == 9)) {
                    tiposBonus=1; // Double letter score
                } else if ((row == 4 || row == 12) && (col == 8 || col == 1 || col == 15)) {
                    tiposBonus=1; // Double letter score
                } else if ((row == 7 || row == 9) && (col == 7 || col == 9 || col == 3 || col == 13)) {
                    tiposBonus=1; // Double letter score
                } else if ((row == 8) && (col == 4 || col == 12)) {
                    tiposBonus=1; // Double letter score
                } else if (row == 8 && col == 8) {
                    tiposBonus = 3; // double word score
                } else if (row == 8 && (col == 1 || col == 15)) {
                    tiposBonus = 4; // Triple word score
                } else if ((row == 6 || row ==10) && (col == 2 || col == 6 || col == 10 || col ==14)) {
                    tiposBonus = 2; // Triple letter score
                } else if ((row == 2 || row == 14 )&& (col == 6 || col == 10)) {
                    tiposBonus = 2; // Triple letter score
                } else if ((row == col || row + col == 16)) {
                    tiposBonus = 3; // double word
                }

                this.matrizOcupacio[row-1][col-1] = new Casilla(tiposBonus, col, row,  false);
            }
        }
    }

    /**
     * Obtiene el número de columnas del tablero.
     * 
     * @return El número de columnas.
     */
    public int getCols() {
        return this.cols;
    }

    /**
     * Obtiene el número de filas del tablero.
     * 
     * @return El número de filas.
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * Obtiene el idioma actual del tablero.
     * 
     * @return El idioma del tablero.
     */
    public String getIdioma() {
        return this.idioma;
    }

    /**
     * Obtiene la letra en una casilla específica del tablero.
     * 
     * @param f La fila de la casilla.
     * @param c La columna de la casilla.
     * @return La letra en la casilla.
     * @throws IllegalArgumentException Si las coordenadas están fuera de los límites.
     */
    public String getLetra(int f, int c) {
        if (f < 0 || f >= this.getRows() || c < 0 || c >= this.getCols()) {
            throw new IllegalArgumentException("getletra Coordenadas fuera de los límites del tablero: fila=" + f + ", columna=" + c);
        }
        return this.matrizOcupacio[f][c].getLetra();
    }

    /**
     * Obtiene la bonificación de una casilla específica.
     * 
     * @param f La fila de la casilla.
     * @param c La columna de la casilla.
     * @return La bonificación de la casilla.
     */
    public int getBonificacionCasilla(int f, int c) {
        //System.out.println("BonificacionTablero: " + f+"   "+c);
        return this.matrizOcupacio[f-1][c-1].getBonificacion();
    }

    /**
     * Obtiene el contador de palabras jugadas por el jugador humano.
     * 
     * Este contador se incrementa cada vez que el jugador humano juega una palabra.
     * 
     * @return El número de palabras jugadas por el jugador humano.
     */
    public int getContadorHumano() {
        return contadorHumano;
    }

    /**
     * Obtiene el mapa de jugadores y sus palabras más largas.
     * 
     * Este mapa asocia a cada jugador de la partida con la palabra más larga
     * que ha formado durante el juego. La clave del mapa es un objeto {@code Usuario},
     * que representa al jugador, y el valor es un {@code String} que contiene la palabra más larga.
     * 
     * @return Un mapa que asocia a cada jugador ({@code Usuario}) con su palabra más larga ({@code String}).
     */
    public Map<Usuario, String> getMapaJugadoresPalabraLarga() {
        return mapaJugadoresPalabraLarga;
    }

    /**
     * Añade un jugador al mapa de jugadores y su palabra más larga.
     * 
     * @param jugador El jugador a añadir.
     */
    public void anadirJugador(Usuario jugador) {
        mapaJugadoresPalabraLarga.put(jugador, "");
    }

    /**
     * Añade una palabra al jugador en el mapa de jugadores y actualiza su palabra más larga si corresponde.
     * 
     * @param jugador El jugador al que se le añadirá la palabra.
     * @param palabra La palabra que se añadirá.
     */
    public void anadirPalabraAJugador(Usuario jugador, String palabra) {
        if (mapaJugadoresPalabraLarga.containsKey(jugador)) {
            String palabraActual = mapaJugadoresPalabraLarga.get(jugador);
            if (palabra.length() > palabraActual.length()) {
                mapaJugadoresPalabraLarga.put(jugador, palabra);
            }
        } else {
            mapaJugadoresPalabraLarga.put(jugador, palabra);
        }
    }

    /**
     * Verifica si una casilla específica está ocupada.
     * 
     * @param f La fila de la casilla.
     * @param c La columna de la casilla.
     * @return {@code true} si la casilla está ocupada, {@code false} en caso contrario.
     */
    public boolean isOcupadafunc(int f, int c) {
        return this.matrizOcupacio[f-1][c-1].isOcupada();
    }



    /**
     * Establece el idioma del tablero.
     * 
     * @param idioma El idioma a establecer.
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Establece la bonificación de una casilla específica.
     * 
     * @param f La fila de la casilla.
     * @param c La columna de la casilla.
     * @param bonificacion La bonificación a establecer.
     */
    public void setBonificacionCasilla(int f, int c, int bonificacion) {
        this.matrizOcupacio[f-1][c-1].setBonificacion(bonificacion);
    }
    
    /**
     * Verifica si una palabra puede colocarse en el tablero.
     * 
     * @param p La palabra a colocar.
     * @param f La fila inicial.
     * @param c La columna inicial.
     * @param horizontal {@code true} si la palabra es horizontal, {@code false} si es vertical.
     * @return {@code true} si la palabra puede colocarse, {@code false} en caso contrario.
     */
    public boolean puedeColocarPalabra(String p, int f, int c, boolean horizontal) {
        //reviasar si la palabra cabe en el tablero horizoantlmente o verticalmente
        if (horizontal) {
            
            for (int i = 0; i < p.length(); i++) {
                if(c-1+i>=15 ||(c-1+i<15 && this.matrizOcupacio[f-1][c + i-1].isOcupada())) {
                    return false; // La posición ya está ocupada
                }
            }
            return true; // La palabra cabe horizontalmente
        } else {
            
            for (int i = 0; i < p.length(); i++) {
                if(f-1+i>=15 || (f-1+i<15 && this.matrizOcupacio[f + i-1][c-1].isOcupada())) {
                    return false; // La posición ya está ocupada
                }
            }
            return true; // La palabra cabe verticalmente
        }
    }

    /**
     * Devuelve una representación visual del tablero.
     * 
     * @return Una cadena que representa el tablero.
     */
    /*public String[][] getTab() {
        char[] vector = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O'};
        StringBuilder tableroString = new StringBuilder();
        // Print column headers
        tableroString.append("                                                                                     ");
        tableroString.append("\n");
        tableroString.append("   ");
        for (int i = 0; i < cols; i++) {
            tableroString.append(String.format("%5c", vector[i]));
        }
        tableroString.append("       ");
        tableroString.append("\n");

        // Print separator line
        tableroString.append("                    ");
        tableroString.append("     ");
        for (int i = 1; i <= cols; i++) {
            tableroString.append("    " );
        }

        tableroString.append("\n");

        // Print rows with row headers
        for (int i = 1; i <= rows; i++) {
            tableroString.append(String.format("%3d  ", i));

            for (int j = 1; j <= cols; j++) {
                Casilla casilla = this.matrizOcupacio[i-1][j-1];
                String color = getColor(i, j); // Obtén el color de fondo de la casilla

                if (casilla.getLetra()!=("\0")) {
                    // Aplica el color de fondo y luego escribe la letra en negro
                    tableroString.append(String.format(color+ "%3s " +  "|", casilla.getLetra()));
                } else {
                    // Casilla vacía, solo aplica el color de fondo
                    tableroString.append(color + "    "  + "|" );
                }
            }
            tableroString.append(String.format( "%3d  " + ANSI_RESET, i));
            tableroString.append("\n");
            // Print bottom line of each row
            for (int j = 0; j < cols; j++) {
                tableroString.append(ANSI_BROWN_BACKGROUND + ANSI_BLACK + "     " + ANSI_RESET);
                if(j>9)
                    tableroString.append(ANSI_BROWN_BACKGROUND + ANSI_BLACK + "  " + ANSI_RESET);
            }
            tableroString.append("\n");
        }

        // Print column headers again at the bottom
        tableroString.append(ANSI_BROWN_BACKGROUND+"   "+ANSI_RESET);
        for (int i = 1; i <= cols; i++) {
            tableroString.append(String.format(ANSI_BROWN_BACKGROUND + ANSI_BLACK + "%5c" + ANSI_RESET, vector[i-1]));
        }
        tableroString.append(ANSI_BROWN_BACKGROUND + "       " + ANSI_RESET);
        tableroString.append("\n");
        tableroString.append(ANSI_BROWN_BACKGROUND + "                                                                                     " + ANSI_RESET);
        tableroString.append("\n");
        return tableroString.toString();
    }*/
    public String[][] getTab() {
        String[][] tablero = new String[rows][cols]; // Crear la matriz de strings
        // Inicializar la primera fila y columna con números y letras
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Casilla casilla = this.matrizOcupacio[i][j];
                String letra = casilla.getLetra();
                if (letra.equals("\0")) { // Si la casilla está vacía
                    tablero[i][j] = ""; // Representar casillas vacías con una cadena vacía
                } else {
                    tablero[i][j] = letra; // Asignar la letra de la casilla
                }
            }
        }
    
        return tablero; // Devolver la matriz
    }

    /**
     * Obtiene los puntos de anclaje disponibles en el tablero.
     * 
     * @return Una lista de coordenadas de los puntos de anclaje.
     */
    /*private String getColor(int row, int col) {
        // Define the positions of special cells
        if ((row == 1 || row == 15) && (col == 1 || col == 8 || col == 15)) {
            return ANSI_RED_BACKGROUND; // Triple word score
        } else if ((row == 1 || row == 15) && (col == 4 || col == 12)) {
            return ANSI_CYAN_BACKGROUND; // Double letter score
        } else if ((row == 3 || row == 13) && (col == 7 || col == 9)) {
            return ANSI_CYAN_BACKGROUND; // Double letter score
        } else if ((row == 4 || row == 12) && (col == 8 || col == 1 || col == 15)) {
            return ANSI_CYAN_BACKGROUND; // Double letter score
        } else if ((row == 7 || row == 9) && (col == 7 || col == 9 || col == 3 || col == 13)) {
            return ANSI_CYAN_BACKGROUND; // Double letter score
        } else if ((row == 8) && (col == 4 || col == 12)) {
            return ANSI_CYAN_BACKGROUND; // Double letter score
        } else if (row == 8 && col == 8) {
            return ANSI_ORANGE_BACKGROUND; // Center cell
        } else if (row == 8 && (col == 1 || col == 15)) {
            return ANSI_RED_BACKGROUND; // Triple word score
        } else if ((row == 6 || row ==10) && (col == 2 || col == 6 || col == 10 || col ==14)) {
            return ANSI_PURPLE_BACKGROUND; // Triple word score
        } else if ((row == 2 || row == 14 )&& (col == 6 || col == 10)) {
            return ANSI_PURPLE_BACKGROUND; // Triple word score
        } else if ((row == col || row + col == 16)) {
            return ANSI_ORANGE_BACKGROUND; // Diagonal cells except 7-7, 7-9, 9-7, and 9-9
        } else {
            return ANSI_BACKGROUND; // Normal cell
        }
    }*/

    /**
     * Obtiene los puntos de anclaje disponibles en el tablero.
     * Los puntos de anclaje son casillas vacías adyacentes a casillas ocupadas.
     * Si el tablero está vacío, el punto de anclaje será el centro del tablero.
     * 
     * @return Una lista de coordenadas de los puntos de anclaje en formato {@code List<int[]>}.
     */
    public List<int[]> obtenerPuntosDeAnclaje() {
        Set<String> anclajesSet = new HashSet<>();
        boolean hayCasillasOcupadas = false;
        
        System.out.println("tablero en obtenerPuntosDeAnclaje:");
        String[][] tablerothis = getTab();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.print(tablerothis[i][j] + " ");
            }
            System.out.println();
        }
        
        for (int fila = 1; fila <= this.getRows(); fila++) {
            for (int columna = 1; columna <= this.getCols(); columna++) {
                if (this.isOcupadafunc(fila, columna)) {
                    hayCasillasOcupadas = true;
                    // Add adjacent empty cells as anchor points
                    if (fila > 1 && !this.isOcupadafunc(fila - 1, columna) && ((fila<this.getRows() && !this.isOcupadafunc(fila + 1, columna))|| fila==this.getRows())) // Above
                        anclajesSet.add((fila - 1) + "," + columna);
                    if (fila < this.getRows() && !this.isOcupadafunc(fila+1, columna) && ((fila > 1 && !this.isOcupadafunc(fila - 1, columna)) || fila==1)) // Below
                        anclajesSet.add((fila + 1) + "," + columna);
                    if (columna > 1 && !this.isOcupadafunc(fila, columna - 1)&& ((columna<this.getCols() && !this.isOcupadafunc(fila, columna+1))|| columna==this.getCols())) // Left
                        anclajesSet.add(fila + "," + (columna - 1) );
                    if (columna < this.getCols() && !this.isOcupadafunc(fila, columna+1)&& ((columna>1  && !this.isOcupadafunc(fila , columna-1)) || columna==1)) // Right
                        anclajesSet.add(fila + "," + (columna + 1));
                }
            }
        }

        // If no occupied cells, add the center of the board as the anchor point
        if (!hayCasillasOcupadas) {
            int centroFila = this.getRows() / 2 + 1;
            int centroColumna = this.getCols() / 2 + 1;
            anclajesSet.add(centroFila + "," + centroColumna);
        }

        // Convert the Set back to a List of int[] for compatibility
        List<int[]> anclajes = new ArrayList<>();
        if(this.estalimpioTablero()){
            System.out.println("LO DETECTA COMO PUTO LIMPIO");
            anclajes.add(new int[]{8, 8});
            return anclajes;
        }
        for (String coord : anclajesSet) {
            String[] parts = coord.split(",");
            anclajes.add(new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
        }

        return anclajes;
    }

    /**
     * Limpia el tablero eliminando todas las letras no ocupadas.
     * Las casillas vacías se restablecen a su estado inicial.
     */
    public void limpiarMap(){
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getCols(); j++) {
                if(!this.matrizOcupacio[i][j].isOcupada() && this.matrizOcupacio[i][j].getLetra()!="\0") {
                    this.matrizOcupacio[i][j].ColocarFicha("\0");
                }
            }
        }
    }

    /**
     * Verifica si el tablero está completamente limpio.
     * 
     * @return {@code true} si todas las casillas están vacías, {@code false} en caso contrario.
     */
    public boolean estalimpioTablero() {
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getCols(); j++) {
                if(this.matrizOcupacio[i][j].isOcupada()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static class ErrorJuegoException extends RuntimeException {
        private final int codigoError;

        public ErrorJuegoException(int codigoError) {
            this.codigoError = codigoError;
        }

        public int getCodigoError() {
            return codigoError;
        }
    }

    /**
     * Extrae palabras del tablero basándose en las coordenadas dadas.
     * Verifica que las palabras sean válidas según las reglas del juego y el idioma configurado.
     * 
     * @param coordenadasLetras Una lista de coordenadas de las letras colocadas.
     * @param cambiarBon {@code true} si se deben aplicar las bonificaciones, {@code false} en caso contrario.
     * @return La puntuación total obtenida al extraer las palabras.
     * @throws IllegalArgumentException Si las reglas del juego no se cumplen.
     */
    public int extraerPalabras(List<int[]> coordenadasLetras, boolean cambiarBon, Usuario jugador) {
        int n=0;
        // Verificar si hay letras en el centro
        
        if(this.estalimpioTablero()){
            //checquear que no hayas puesto una letra solo
            if (coordenadasLetras.size() == 1) {
                limpiarMap();
                throw new ErrorJuegoException(-1);  
                           
            }
            boolean centro = false;
            for (int[] coordenada : coordenadasLetras) {
                int fila = coordenada[0]-1;
                int columna = coordenada[1]-1;
                
                if (fila == 7 && columna == 7) {
                    centro = true; // Al menos una ficha está en el centro
                }
            }
            if (!centro) {
                limpiarMap();
                throw new ErrorJuegoException(-2); // Código -2: Letras no están en el centro
            }
        }

        //System.out.println(this.idioma ); // Mostrar las fichas disponibles
        Diccionario diccionario = new Diccionario();
        diccionario.setCargarDiccionario(this.idioma); // Cargar el diccionario según el idioma
        Algoritmo algoritmo = new Algoritmo(diccionario, this, this.idioma);
        boolean colindante = false;

        // Verificar si las letras están en la misma fila o columna
        boolean mismaFila = true;
        boolean mismaColumna = true;
        int filaBase = coordenadasLetras.get(0)[0]-1;
        int columnaBase = coordenadasLetras.get(0)[1]-1;
        if(cambiarBon) System.out.println("coordenadasBasewwwwwww: " + (filaBase+1) + " " + (columnaBase+1) + "  letra: " + this.matrizOcupacio[filaBase][columnaBase].getLetra());

        for (int[] coordenada : coordenadasLetras) {
            //System.out.println("coordenadas: " + coordenada[0] + " " + filaBase+1);
            if (coordenada[0] != filaBase+1) {
                //System.out.println("entra1");
                mismaFila = false;
            }
            //System.out.println("coordenadasssssss: " + columnaBase+1 + " " + coordenada[1]);
            if (coordenada[1] != columnaBase+1) {
                //System.out.println("entra2");
                mismaColumna = false;
            }
        }
        //System.out.println(mismaFila + " " + mismaColumna);
        if (!mismaFila && !mismaColumna) {
            //System.out.println("entra qui");
            limpiarMap();
            throw new ErrorJuegoException(-3); // Código -3: Letras no alineadas
        }

        // Verificar si hay espacios en blanco entre las letras
        if(mismaColumna){
            int minFila = Integer.MAX_VALUE;
            int maxFila = Integer.MIN_VALUE;
            for (int[] coordenada : coordenadasLetras) {
                minFila = Math.min(minFila, coordenada[0]-1);
                maxFila = Math.max(maxFila, coordenada[0]-1);
            }
            //System.out.println(minFila + " filasss "+ maxFila+ " " + columnaBase);
            for (int fila = minFila; fila <= maxFila; fila++) {
                //System.out.println(this.matrizOcupacio[fila][columnaBase].getLetra());
                if (columnaBase>=15 || fila>=15 || columnaBase<0 || fila<0 || this.matrizOcupacio[fila][columnaBase].getLetra().equals("\0")){
                    limpiarMap();
                    throw new ErrorJuegoException(-4); // Código -4: Espacio en blanco en columna
                }
            }
        } else if(mismaFila){
            int minCol = Integer.MAX_VALUE;
            int maxCol = Integer.MIN_VALUE;
            for (int[] coordenada : coordenadasLetras) {
                minCol = Math.min(minCol, coordenada[1]-1);
                maxCol = Math.max(maxCol, coordenada[1]-1);
            }
            for (int col = minCol; col <= maxCol; col++) {
                if (filaBase>=15 || col>=15 || filaBase<0 || col<0 || this.matrizOcupacio[filaBase][col].getLetra()==("/0")) {
                    limpiarMap();
                    throw new ErrorJuegoException(-5); // Código -5: Espacio en blanco en fila
                }
            }
        }
        boolean recorrerV1vez = true;
        boolean recorrerH1vez = true;
        //recorrer coordinadasLetras
        for (int[] coordenada : coordenadasLetras) {
            int fila = coordenada[0]-1;
            int columna = coordenada[1]-1;
            //.println(fila + " " + columna);
            //if(cambiarBon) System.out.println("xxxxxxxxxxxxxxxxxx " + fila + " " + columna);


            // Verificar si hay palabra vertical
            if((mismaColumna &&recorrerV1vez) || (!mismaColumna &&((fila > 0 && fila <= this.getRows() && this.matrizOcupacio[fila - 1][columna].getLetra()!=("\0")) || (fila>=0 && fila < this.getRows() - 1 && this.matrizOcupacio[fila + 1][columna].getLetra()!=("\0")))))
            {   System.out.println("palabra vertical");
                List<int[]> coordenadasPalabra = new ArrayList<>();
                recorrerV1vez = false;
                StringBuilder palabraV = new StringBuilder();
                boolean haypalabraV = false;
                int ftop=-1;

                // Verificar si hay letra encima
                if(fila > 0 && fila <= this.getRows() && this.matrizOcupacio[fila - 1][columna].getLetra()!=("\0")) {
                    haypalabraV = true;
                    if(cambiarBon)System.out.println("hay letra arriba");
                    int filaActual = fila - 1;

                    while (filaActual >=0 && this.matrizOcupacio[filaActual][columna].getLetra()!=("\0")) {
                        if(this.matrizOcupacio[filaActual][columna].isOcupada()) {
                            colindante = true;
                            
                        }
                        filaActual--;
                        //System.out.println("coooordenadasasdfasdf: " + (filaActual+1) + " " + (columna+1));
                    }

                    //añadir de arriba hacia abajo
                    ftop = filaActual+1;
                    for(int i=ftop; i<fila; i++) {
                        if(i>=0 && i<this.getRows() && columna>=0 && columna <this.getCols()){
                            palabraV.append(this.matrizOcupacio[i][columna].getLetra());
                            coordenadasPalabra.add(new int[]{i+1, columna+1});
                            //coordenadasPalabra.add(new int[]{i + 1, columna + 1});
                            if(cambiarBon)System.out.println("Coordenadav11 " + (i+1) + " " + (columna+1));
                            //if(cambiarBon)System.out.println("coordenadasasdfasdf: " + (i) + " " + (columna));
                        }
                    }
                }

                palabraV.append(this.matrizOcupacio[fila][columna].getLetra());
                System.out.println("coordenadasACt: " + (fila+1) + " " + (columna+1));
                coordenadasPalabra.add(new int[]{fila+1, columna+1});
                //verificar si hay letra debajo
                if (fila>=0 && fila < this.getRows() - 1 && this.matrizOcupacio[fila + 1][columna].getLetra()!=("\0")) {
                    if(ftop==-1) ftop=fila;
                    haypalabraV= true;
                    int filaActual = fila + 1;
                    if(cambiarBon)System.out.println("hay letra abajo");
                    
                    while (filaActual < this.getRows() && this.matrizOcupacio[filaActual][columna].getLetra()!=("\0")) {
                        if(this.matrizOcupacio[filaActual][columna].isOcupada()) {
                            colindante = true;
                        }
                        palabraV.append(this.matrizOcupacio[filaActual][columna].getLetra());
                        coordenadasPalabra.add(new int[]{filaActual+1, columna+1});
                        filaActual++;
                        //if(cambiarBon)System.out.println("Coordenadav22 " + (filaActual) + " " + (columna+1));
                    }
                }
                //System.out.println("palabraV: " + palabraV);
                //System.out.println("palabraV: " + palabraV.toString());
                if (haypalabraV) {
                    String palabraVF = palabraV.toString().replaceAll("\\\\0", "").trim().toUpperCase();
                    if (palabraVF.length() > 1) { // Verificar explícitamente que la palabra tiene más de una letra
                        if (algoritmo.esPalabraValida(palabraVF.toString(), this.idioma)) {
                            //System.out.println("coordenadasV: " + ftop +1 + " " + columna+1);
                            if(cambiarBon) System.out.println("palabraVpunt: " + palabraVF);
                            if(cambiarBon) System.out.println("coordenadasV: " + ftop + " " + (columna+1));
                            for (int[] coordenadaPalabra : coordenadasPalabra) {
                                int filaPalabra = coordenadaPalabra[0];
                                int columnaPalabra = coordenadaPalabra[1];
                                if(cambiarBon)System.out.println("coordenadas ocupadasV: " + filaPalabra + " " + columnaPalabra);
                                
                            }
                            n += algoritmo.calcularPuntuacion(palabraVF, ftop+1, columna+1, false, cambiarBon);
                            if(cambiarBon) {
                                if (jugador instanceof Humano) ++contadorHumano;
                                this.anadirPalabraAJugador(jugador, palabraVF);   
                                
                            }
                        } else {
                            limpiarMap();
                            throw new ErrorJuegoException(-7); // Código -5: Espacio en blanco en fila
                        }
                    } else {
                        //System.out.println("Palabra ignorada por tener solo una letra: " + palabraVF.toString());
                    }
                }
            }
            if((mismaFila && recorrerH1vez) || (!mismaFila && ((columna > 0 && columna <= this.getCols() && this.matrizOcupacio[fila][columna - 1].getLetra()!=("\0")) || (columna>=0 && columna < this.getCols() - 1 && this.matrizOcupacio[fila][columna + 1].getLetra()!=("\0"))))) {
                List<int[]> coordenadasPalabra = new ArrayList<>();
                recorrerH1vez = false;
                // Verificar si hay letra horizonal
                StringBuilder palabraH = new StringBuilder();
                boolean haypalabraH = false;
                int ctop=-1;
                
                // Verificar si hay letra a la izquierda
                if(columna > 0 && columna<=this.getCols() && this.matrizOcupacio[fila][columna - 1].getLetra()!=("\0")) {
                    haypalabraH = true;

                    int columnaActual = columna - 1;

                    while (columnaActual >=0 && this.matrizOcupacio[fila][columnaActual].getLetra()!=("\0")) {
                        //System.out.println(this.matrizOcupacio[fila][columnaActual].getLetra());
                        if(this.matrizOcupacio[fila][columnaActual].isOcupada()) {
                            colindante = true;
                        }
                        
                        columnaActual--;
                    }

                    //añadir de izquierda a derecha
                    ctop = columnaActual+1;
                    for(int i=columnaActual; i<columna; i++) {
                        if(i>=0 && i<this.getCols() && fila>=0 && fila<getRows()) {
                            palabraH.append(this.matrizOcupacio[fila][i].getLetra());
                            coordenadasPalabra.add(new int[]{fila+1, i+1});
                        }
                    }
                }
                palabraH.append(this.matrizOcupacio[fila][columna].getLetra());
                //verificar si hay letra a la derecha
                if (columna > 0 && columna < this.getCols() - 1 && this.matrizOcupacio[fila][columna + 1].getLetra()!=("\0")) {
                    if(ctop==-1) ctop=columna;
                    haypalabraH= true;
                    int columnaActual = columna + 1;
                    while (columnaActual < this.getCols() && this.matrizOcupacio[fila][columnaActual].getLetra()!=("\0")) {
                        if(this.matrizOcupacio[fila][columnaActual].isOcupada()) {
                            colindante = true;
                        }
                        palabraH.append(this.matrizOcupacio[fila][columnaActual].getLetra());
                        coordenadasPalabra.add(new int[]{fila+1, columnaActual+1});
                        columnaActual++;
                    }
                }
                //System.out.println("palabraH: " + palabraH);
                //System.out.println("palabraH: " + palabraH.toString());
                
                if (haypalabraH) {
                    ++ctop;
                    String palabraHF = palabraH.toString().replaceAll("\\\\0", "").trim().toUpperCase();
                    if (palabraHF.length() > 1) { // Verificar explícitamente que la palabra tiene más de una letra
                        if (algoritmo.esPalabraValida(palabraHF.toString(), this.idioma)) {
                            if(cambiarBon) System.out.println("palabraHpunt: " + palabraHF);
                            if(cambiarBon) System.out.println("coordenadasH: " + (fila +1)+ " " +ctop );
                            for (int[] coordenadaPalabra : coordenadasPalabra) {
                                int filaPalabra = coordenadaPalabra[0];
                                int columnaPalabra = coordenadaPalabra[1];
                                if(cambiarBon) System.out.println("coordenadas ocupadasH: " + filaPalabra + " " + columnaPalabra);
                            }
                            //System.out.println("coordinadasH: " + ctop +1 + " " + fila +1);

                            n+=algoritmo.calcularPuntuacion(palabraHF, fila+1, ctop, true, cambiarBon);
                            if(cambiarBon){
                                if (jugador instanceof Humano) ++contadorHumano;
                                System.out.println("puntuacionH: " + n);
                                this.anadirPalabraAJugador(jugador, palabraHF);
                                System.out.println("Palabra: " + palabraHF + " Coordenadas: " + coordenadasPalabra);
                            }
                        } else {
                            limpiarMap();
                            throw new ErrorJuegoException(-7); // Código -5: Espacio en blanco en fila
                        }
                    } else {
                        //System.out.println("Palabra ignorada por tener solo una letra: " + palabraHF.toString());
                    }
                }

            }
        }

        // Si hay letras contiguas, agregar la palabra a la lista
        if(!this.estalimpioTablero() && !colindante) {
            limpiarMap();
            throw new ErrorJuegoException(-6); // Código -5: Espacio en blanco en fila
        }
        return n;
    }
    
    /**
     * Coloca una letra en una casilla específica del tablero.
     * 
     * @param letra La letra a colocar.
     * @param fila La fila de la casilla.
     * @param col La columna de la casilla.
     * @throws IllegalArgumentException Si las coordenadas están fuera de los límites del tablero.
     */
    public void setCasilla(String letra, int fila, int col) {
        try {

            if(fila>=1 && fila <=15 && col>=1 && col<=15)this.matrizOcupacio[fila-1][col-1].ColocarFicha(letra);
            //System.out.println("Colocando letra: " + letra + " en la fila: " + fila + ", columna: " + col);
            else {
                throw new IllegalArgumentException("set casila Error: Coordenadas fuera de los límites del tablero.");
            }

        } catch (IllegalArgumentException e) {
            // Relanzar la excepción sin imprimir nada
            this.limpiarMap();
            Vista vista = new Vista();
            vista.printlnMensaje("Error: " + e.getMessage());
        }
    }

    /**
     * Marca una casilla específica como ocupada.
     * 
     * @param fila La fila de la casilla.
     * @param col La columna de la casilla.
     */
    public void setCasillaOcupada(int fila, int col) {
        this.matrizOcupacio[fila-1][col-1].setOcupada(true);
    }

}

