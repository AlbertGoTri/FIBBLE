package dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * La clase Algoritmo implementa la lógica para calcular las mejores jugadas en el juego de Scrabble.
 * Utiliza estructuras de datos como Trie y DAWG para optimizar la generación y validación de palabras.
 */
public class Algoritmo {
    /**
     * Diccionario utilizado para obtener las palabras válidas y sus puntuaciones.
     */
    private Diccionario diccionario;

    /**
     * Tablero de juego asociado al algoritmo.
     */
    private Tablero tablero;

    /**
     * Estructura Trie utilizada para almacenar las palabras del diccionario.
     */
    private static Trie trie;

    /**
     * Estructura DAWG utilizada para minimizar redundancias en las palabras del diccionario.
     */
    private static DAWG dawg;

    /**
     * Constructor de la clase Algoritmo.
     * Inicializa el Trie y el DAWG a partir del diccionario proporcionado.
     *
     * @param diccionario Diccionario con las palabras válidas.
     * @param tablero     Tablero de juego asociado.
     */
    public Algoritmo(Diccionario diccionario, Tablero tablero, String idioma) {
        this.diccionario = diccionario;
        this.tablero = tablero;
        // Initialize Trie and DAWG only once
        if (trie == null || dawg == null) {
            synchronized (Algoritmo.class) {
                if (trie == null) {
                    trie = diccionario2trie(diccionario, idioma);
                }
                if (dawg == null) {
                    dawg = trie2dawg(trie);
                }
            }
        }
    }

    /**
     * Convierte un diccionario en una estructura Trie.
     *
     * @param diccionario Diccionario con las palabras válidas.
     * @return Estructura Trie generada.
     */
    private Trie diccionario2trie(Diccionario diccionario, String idioma) {
        Trie trie = new Trie();
        List<String> letrasEspeciales = new ArrayList<>();

        if ("catalan".equals(idioma)) {
            letrasEspeciales.add("L·L");
            letrasEspeciales.add("NY");
        } else if ("castellano".equals(idioma)) {
            letrasEspeciales.add("CH");
            letrasEspeciales.add("LL");
            letrasEspeciales.add("RR");
        }

        for (String palabra : diccionario.getPalabras()) {
            trie.insert(palabra, letrasEspeciales);
        }
        return trie;
    }


    /**
     * Convierte un Trie en una estructura DAWG.
     *
     * @param trie Estructura Trie a convertir.
     * @return Estructura DAWG generada.
     */
    private DAWG trie2dawg(Trie trie) {
        DAWG dawg = new DAWG();
        dawg.buildFromTrie(trie);

        return dawg;
    }

    /**
     * Calcula la mejor jugada posible para el jugador dado.
     *
     * @param jugador Usuario que está jugando.
     * @return Movimiento que representa la mejor jugada.
     */
    public Movimiento calcularMejorJugada(Usuario jugador) {
        //System.out.println("LLegando a calcularMejorJugada");
        System.out.println("tablero en algoritmo:");
        String[][] tablerothis = tablero.getTab();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.print(tablerothis[i][j] + " ");
            }
            System.out.println();
        }
        List<Movimiento> movimientos = generarMovimientos(jugador);
        Movimiento mejorMovimiento = evaluarMovimientos(jugador, movimientos);
        return mejorMovimiento;
    }

    /**
     * Genera una lista de movimientos posibles para el jugador dado.
     *
     * @param jugador Usuario que está jugando.
     * @return Lista de movimientos generados.
     */
    public List<Movimiento> generarMovimientos(Usuario jugador) {
        List<Movimiento> movimientos = new ArrayList<>();
        //System.out.println("llega aqui anclaje " );
        System.out.println("tablero en generarMovimientos:");
        String[][] tablerothis = tablero.getTab();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.print(tablerothis[i][j] + " ");
            }
            System.out.println();
        }
        List<int[]> anclajes = tablero.obtenerPuntosDeAnclaje();
        System.out.println("Lista de anclajes:");
        for (int[] anclaje : anclajes) {
            System.out.println("Anclaje: (" + anclaje[0] + ", " + anclaje[1] + ")");
        }

        System.out.println("Tamaño de la lista de anclajes: " + anclajes.size());
        StringBuilder coordenadas = new StringBuilder("Coordenadas de los anclajes: ");
        for (int[] anclaje : anclajes) {
            coordenadas.append("(").append(anclaje[0]).append(", ").append(anclaje[1]).append(") ");
        }
       
        System.out.println(coordenadas.toString());
        for (int[] anclaje : anclajes) {
            int fila = anclaje[0];
            int columna = anclaje[1];
            System.out.println("AnclajeAlgoritmo: (" + fila + ", " + columna + ")");
            List<String> posiblesPalabras = generarPalabrasValidas(jugador.getFichas());
            for (String palabra : posiblesPalabras) {
                List<String> fichasUsadas = obtenerFichasUsadas(jugador.getFichas(), palabra);
                if (tablero.puedeColocarPalabra(palabra, fila, columna, true) ) {
                    int puntuacion = calcularPuntuacion(palabra, fila, columna, true, false);

                    movimientos.add(new Movimiento(fichasUsadas, fila, columna, true, puntuacion, palabra));
                }
                if (tablero.puedeColocarPalabra(palabra, fila, columna, false)) {
                    int puntuacion = calcularPuntuacion(palabra, fila, columna, false, false);
                    movimientos.add(new Movimiento(fichasUsadas, fila, columna, false, puntuacion, palabra));
                }
            }
        }
        return movimientos;
    }

    /**
     * Obtiene las fichas utilizadas para formar una palabra a partir de las fichas disponibles.
     *
     * @param fichasDisponibles Fichas disponibles para formar palabras.
     * @param palabra           Palabra que se desea formar.
     * @return Lista de fichas utilizadas para formar la palabra.
     */
    private List<String> obtenerFichasUsadas(List<String> fichasDisponibles, String palabra) {
        List<String> fichasUsadas = new ArrayList<>();
        List<String> fichasTemp = new ArrayList<>(fichasDisponibles); // Temporary copy of available tiles
        for (char letra : palabra.toCharArray()) {
            String letraStr = String.valueOf(letra);
            if (fichasTemp.contains(letraStr)) {
                fichasUsadas.add(letraStr);
                fichasTemp.remove(letraStr); // Remove the used tile to avoid duplicates
            }
        }
        return fichasUsadas;
    }

    /**
     * Genera una lista de palabras válidas a partir de las fichas disponibles.
     *
     * @param fichas Fichas disponibles para formar palabras.
     * @return Lista de palabras válidas generadas.
     */
    private List<String> generarPalabrasValidas(List<String> fichas) {
        List<String> palabrasValidas = new ArrayList<>();
        generarCombinaciones(fichas, "", dawg.getRoot(), palabrasValidas);
        return palabrasValidas;
    }

    /**
     * Genera todas las combinaciones posibles de letras a partir de las fichas disponibles.
     *
     * @param fichas   Fichas disponibles para formar palabras.
     * @param actual   Palabra actual en construcción.
     * @param nodo     Nodo actual en el DAWG.
     * @param resultados Lista de palabras generadas.
     */
    private void generarCombinaciones(List<String> fichas, String actual, DAWG.DAWGNode nodo, List<String> resultados) {
        if (nodo.isEndOfWord) {
            resultados.add(actual);
        }
        for (int i = 0; i < fichas.size(); i++) {
            String letra = fichas.get(i);
            if (nodo.getChildren().containsKey(letra)) {
                List<String> restantes = new ArrayList<>(fichas);
                restantes.remove(i);
                generarCombinaciones(restantes, actual + letra, nodo.getChildren().get(letra), resultados);
            }
        }
    }

    /**
     * Calcula la puntuación de una palabra colocada en el tablero.
     *
     * @param palabra   Palabra a evaluar.
     * @param fila      Fila donde se coloca la palabra.
     * @param columna   Columna donde se coloca la palabra.
     * @param horizontal {@code true} si la palabra es horizontal, {@code false} si es vertical.
     * @param setBon    {@code true} si se deben aplicar bonificaciones, {@code false} en caso contrario.
     * @return Puntuación total de la palabra colocada.
     */
    public int calcularPuntuacion(String palabra, int fila, int columna, boolean horizontal, boolean setBon) {
        int puntuacion = 0;
        int multpalabra = 1;
        if(setBon)System.out.println("Calculando puntuación para la palabra: " + palabra + " en fila: " + fila + ", columna: " + columna + ", horizontal: " + horizontal);

        try {
            int z=0;
            for (int i = 0; i < palabra.length(); i++) {
                String letra = String.valueOf(palabra.charAt(i));
                String idioma = this.tablero.getIdioma();
                if ("catalan".equals(idioma)) {
                    if (i + 2 < palabra.length() && palabra.charAt(i) == 'L' && palabra.charAt(i + 1) == '·') {
                        letra = letra + palabra.charAt(i + 1) + palabra.charAt(i + 2);
                        i += 2;
                    } else if (i + 1 < palabra.length() && palabra.charAt(i) == 'N' && palabra.charAt(i + 1) == 'Y') {
                        letra = letra + palabra.charAt(i + 1);
                        i++;
                    }
                } else if ("castellano".equals(idioma)) {
                    if (i + 1 < palabra.length() && palabra.charAt(i) == 'C' && palabra.charAt(i + 1) == 'H') {
                        letra = letra + palabra.charAt(i + 1);
                        i++;
                    } else if (i + 1 < palabra.length() && palabra.charAt(i) == 'L' && palabra.charAt(i + 1) == 'L') {
                        letra = letra + palabra.charAt(i + 1);
                        i++;
                    } else if (i + 1 < palabra.length() && palabra.charAt(i) == 'R' && palabra.charAt(i + 1) == 'R') {
                        letra = letra + palabra.charAt(i + 1);
                        i++;
                    }
                }
                int valorLetra = diccionario.getPuntuacionLetra(letra);
                int bonificacion = 0;
                
                if (horizontal) {
                    /*if (columna + i > this.tablero.getCols()) {
                        throw new IllegalArgumentException("Puntuacion Coordenadas fuera de los límites del tablero: fila=" + fila + ", columna=" + (columna + i));
                    }*/
                   
                    if(columna + i <= this.tablero.getCols()) {
                        bonificacion = tablero.getBonificacionCasilla(fila, columna + z);
                        if(setBon)System.out.println("Bonificación en la casilla (" + fila + ", " + (columna + z) + "): " + bonificacion);
                        //tablero.setBonificacionCasilla(fila, columna+i, 0); // Clear the bonus after using it
                    }
                   
                } else {
                    /*if (fila + i > this.tablero.getRows()) {
                        throw new IllegalArgumentException("Puntuacion Coordenadas fuera de los límites del tablero: fila=" + (fila + i) + ", columna=" + columna);
                    }*/
                    
                    if(fila + i <= this.tablero.getRows()) {
                        bonificacion = tablero.getBonificacionCasilla(fila + z, columna);
                        if(setBon)System.out.println("Bonificación en la casilla (" + (fila + z) + ", " + columna + "): " + bonificacion);
                        //tablero.setBonificacionCasilla(fila+i, columna, 0); // Clear the bonus after using it
                    }
                    
                }

                // Apply bonuses
                if (bonificacion == 1) {
                    valorLetra *= 2;
                } else if (bonificacion == 2) {
                    valorLetra *= 3;
                } else if (bonificacion == 3) {
                    multpalabra *= 2;
                } else if (bonificacion == 4) {
                    multpalabra *= 3;
                }

                puntuacion += valorLetra;
                z++;
                
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return 0; // Retorna 0 o maneja el error según sea necesario
        }

        return puntuacion * multpalabra;
    }

    /**
     * Evalúa los movimientos generados y selecciona el mejor movimiento basado en la puntuación.
     *
     * @param movimientos Lista de movimientos generados.
     * @return El mejor movimiento encontrado.
     */
    private Movimiento evaluarMovimientos(Usuario jugador, List<Movimiento> movimientos) {
        Movimiento mejorMovimiento = null;
        int mejorPuntuacion = Integer.MIN_VALUE;
        int umbralPuntuacion = 6;

        Tablero tableroBase = new Tablero(tablero);

        for (Movimiento movimiento : movimientos) {
            Tablero tableroSim = new Tablero(tableroBase);
            String palabraInv = "\0";
            if (movimiento.getPuntuacion() > mejorPuntuacion) {
                try {
                    List<int[]> coordenadasLetras = new ArrayList<>();
                    int fila = movimiento.getFila();
                    int columna = movimiento.getColumna();
                    String palabra = movimiento.getPalabraFormada();

                    if (!palabraInv.equals(palabra)) {
                        // Agregar las coordenadas de la palabra al tablero
                        System.out.println("palabra: "+palabra);
                        for (int i = 0; i < palabra.length(); i++) {
                            String l = null;
                            String idioma = tablero.getIdioma();
                            l= String.valueOf(palabra.charAt(i));
                            if (movimiento.esHorizontal()) {
                                if ("catalan".equals(idioma)) {
                                    if (i + 2 < palabra.length() && palabra.charAt(i) == 'L' && palabra.charAt(i + 1) == '·') {
                                        l = l + palabra.charAt(i + 1) + palabra.charAt(i + 2);
                                        i += 2;
                                    } else if (i + 1 < palabra.length() && palabra.charAt(i) == 'N' && palabra.charAt(i + 1) == 'Y') {
                                        l = l + palabra.charAt(i + 1);
                                        i++;
                                    }
                                } else if ("castellano".equals(idioma)) {
                                    if (i + 1 < palabra.length() && palabra.charAt(i) == 'C' && palabra.charAt(i + 1) == 'H') {
                                        l = l + palabra.charAt(i + 1);
                                        i++;
                                    } else if (i + 1 < palabra.length() && palabra.charAt(i) == 'L' && palabra.charAt(i + 1) == 'L') {
                                        l = l + palabra.charAt(i + 1);
                                        i++;
                                    } else if (i + 1 < palabra.length() && palabra.charAt(i) == 'R' && palabra.charAt(i + 1) == 'R') {
                                        l = l + palabra.charAt(i + 1);
                                        i++;
                                    }
                                }
                                coordenadasLetras.add(new int[]{fila, columna + i});
                                tableroSim.setCasilla(l, fila, columna + i);
                            } else {
                                coordenadasLetras.add(new int[]{fila + i, columna});
                                tableroSim.setCasilla(l, fila + i, columna);
                            }
                        }
                        List<int[]> nuevasCoordenadas = new ArrayList<>();
                        //System.out.println("palabra: " + palabra);
                        for(int[] coordenada : coordenadasLetras) {
                            nuevasCoordenadas.add(new int[]{coordenada[0], coordenada[1]});
                            //System.out.println("Coordenadas de la letraALGG: " + (coordenada[0]+1) + ", " + (coordenada[1]+1));
                        }
                        movimiento.setCoordenadas(nuevasCoordenadas);
                        // Intentar extraer palabras y calcular puntuación
                        int puntuacion;
                        try{
                        puntuacion = tableroSim.extraerPalabras(coordenadasLetras, false, jugador);
                        }
                        catch (Tablero.ErrorJuegoException e) {
                            //System.err.println("ErrorJuegoException lanzada con código: " + e.getCodigoError());
                            tableroSim.limpiarMap(); // Limpia el tablero en caso de error
                            continue; // Salta al siguiente movimiento
                        }
                        // Imprimir la palabra y su puntuación
                        System.out.println("Palabra evaluada: " + palabra + ", Puntuación: " + puntuacion);

                        // Limpiar el tablero después de la simulación
                        tableroSim.limpiarMap();

                        // Actualizar el mejor movimiento si la puntuación es mayor
                        if (puntuacion > mejorPuntuacion) {
                            mejorPuntuacion = puntuacion;
                            mejorMovimiento = movimiento;
                        }
                    }
                } catch (IllegalArgumentException e) {
                    // Limpiar el tablero en caso de error
                    tablero.limpiarMap();

                    // Manejar palabras inválidas
                    if (e.getMessage().equals("Error: La palabra " + movimiento.getPalabraFormada() + " no es válida en el idioma " + tablero.getIdioma() + ".")) {
                        palabraInv = movimiento.getPalabraFormada();
                    }
                }
            }
        }

        return mejorMovimiento;
    }

    /**
     * Verifica si una palabra es válida en el DAWG para un idioma específico.
     *
     * @param palabra La palabra a validar.
     * @param idioma  El idioma en el que se desea validar la palabra.
     * @return {@code true} si la palabra es válida, {@code false} en caso contrario.
     */
    public boolean esPalabraValida(String palabra, String idioma) {
        //System.out.println("Validating word: " + palabra + " in language: " + idioma);
        DAWG.DAWGNode nodoActual = dawg.getRoot();
        List<String> letrasEspeciales = new ArrayList<>();

        // Define special letters based on the language
        if ("catalan".equals(idioma)) {
            letrasEspeciales.add("L·L");
            letrasEspeciales.add("NY");
        } else if ("castellano".equals(idioma)) {
            letrasEspeciales.add("CH");
            letrasEspeciales.add("LL");
            letrasEspeciales.add("RR");
        }
        //System.out.println("Special letters for " + idioma + ": " + letrasEspeciales);

        for (int i = 0; i < palabra.length(); ) {
            String letra = String.valueOf(palabra.charAt(i));
            boolean letraEspecialEncontrada = false;

            // Check for special letters in the next characters
            for (String especial : letrasEspeciales) {
                if (i + especial.length() <= palabra.length() &&
                        palabra.substring(i, i + especial.length()).equals(especial)) {
                    letra = especial;
                    letraEspecialEncontrada = true;
                    i += especial.length();
                    //System.out.println("Found special letter: " + letra);
                    break;
                }
            }

            if (!letraEspecialEncontrada) {
                i++; // Move to the next character if no special letter is found
                //System.out.println("Processing normal letter: " + letra);
            }

            // Verify if the letter (normal or special) exists in the current node's children
            if (!nodoActual.getChildren().containsKey(letra)) {
                //System.out.println("Letter not found in DAWG: " + letra);
                return false; // The letter does not exist in the DAWG
            }
            nodoActual = nodoActual.getChildren().get(letra); // Move to the next node
            //System.out.println("Moved to next node for letter: " + letra);
        }

        boolean isEndOfWord = nodoActual.isEndOfWord();
        //System.out.println("Word validation result for '" + palabra + "': " + isEndOfWord);
        return isEndOfWord; // Check if the final node marks the end of a word
    }
}
