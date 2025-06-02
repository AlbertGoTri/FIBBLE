package dominio;

/**
 * Clase Estadistica
 * <p>
 * Esta clase representa las estadísticas de un jugador en el juego de Scrabble.
 * </p>
 * <p>
 * <b>Propósito:</b> Gestionar y actualizar las estadísticas del jugador, incluyendo:
 * la puntuación total, la puntuación media, la puntuación máxima, el número de partidas jugadas,
 * el número de partidas ganadas, el número de partidas perdidas, la tasa de victorias, entre otras más. 
 * </p>
 * <p>
 * Contiene métodos para actualizar y obtener estos atributos, permitiendo un seguimiento detallado del rendimiento del jugador.
 * </p>
 */
public class Estadistica implements java.io.Serializable {
    /** Puntos totales acumulados.*/
    private int totalPoints; 

    /** Puntuación media por partida.*/
    private double avgPoints;

    /** Puntuación Máxima de todas las partidas jugadas.*/
    private int maxPoints; 

    /** Número de partidas jugadas.*/
    private int num_matches; 

    /** Número de partidas ganadas.*/
    private int wins; 

    /** Número de partidas perdidas.*/
    private int loses; 

    /** Tasa de victorias.*/
    private double winRate;

    /** Número de palabras totales jugadas.*/
    private int totalWords;

    /** Mejor palabra jugada (la más larga) */
    private String bestWord;

    /** 
     * Serial Version UID para la serialización de la clase Estadistica.
     * Se utiliza para asegurar la compatibilidad entre versiones de la clase durante la serialización/deserialización.
     */
    private static final long serialVersionUID = 1L;


    /**
     * Constructor de la clase Estadistica.
     * <p>
     * Este constructor inicializa todos los atributos a sus valores por defecto (0 o null en función del tipo de variable).
     * </p>
     * 
     * @param totalPoints Puntos totales acumulados (inicialmente 0).
     * @param avgPoints Puntuación media por partida (inicialmente 0).
     * @param maxPoints Puntuación máxima de todas las partidas jugadas (inicialmente 0).
     * @param num_matches Número de partidas jugadas (inicialmente 0).
     * @param wins Número de partidas ganadas (inicialmente 0).
     * @param loses Número de partidas perdidas (inicialmente 0).
     * @param winRate Tasa de victorias (inicialmente 0.0).
     * @param totalWords Número de palabras totales jugadas (inicialmente 0).
     * @param bestWord Mejor palabra jugada (inicialmente null).
     */
    public Estadistica() {
        this.totalPoints = 0;       
        this.avgPoints = 0.0;
        this.maxPoints = 0; //-1?
        this.num_matches = 0;
        this.wins = 0;
        this.loses = 0;
        this.winRate = 0.0;
        this.totalWords = 0;
        this.bestWord = null; 
    }

    //getters:

    /**
     * Métododo para obtener la puntuación total del jugador.
     * 
     * @return La puntuación total acumulada por el jugador.
     */
    public int getTotalPoints() {
        return this.totalPoints;
    }

    /**
     * Método para obtener la puntuación media del jugador.
     * 
     * @return La puntuación media por partida del jugador.
     */
    public double getAveragePoints() {
        return this.avgPoints;
    }

    /**
     * Método para obtener la puntuación máxima del jugador.
     * 
     * @retunrn La puntuación máxima obtenida por el jugador en una partida.
     */
    public int getMaxPoints() {
        return this.maxPoints;
    }

    /** 
     * Método para obtener el número de partidas jugadas por el jugador.
     * 
     * @return El número total de partidas jugadas por el jugador.
     */
    public int getNumMatches() {
        return this.num_matches;
    }

    /**
     * Método para obtener el número de partidas ganadas por el jugador.
     * 
     * @return El número total de partidas ganadas por el jugador.
     */
    public int getWins() {
        return this.wins;
    }

    /**
     * Método para obtener el número de partidas perdidas por el jugador.
     * 
     * @return El número total de partidas perdidas por el jugador.
     */
    public int  getLoses() {
        return this.loses;
    }

    /**
     * Método para obtener la tasa de victorias del jugador.
     * 
     * @return La tasa de victorias del jugador.
     */
    public double getWinRate() {    
        return this.winRate;
    }

    /**
     * Método para obtener el número total de palabras jugadas por el jugador.
     * 
     * @return El número total de palabras jugadas por el jugador.
     */
    public int getTotalWords() {
        return this.totalWords;
    }

    /**
     * Método para obtener la mejor palabra jugada por el jugador.
     *
     * @return La mejor palabra jugada por el jugador (la más larga o la que tiene más puntos).
     *        - Si no hay mejor palabra, devuelve null.
     */
    public String getBestWord() {
        if(this.bestWord == null) {
            return null;
        }
        else return this.bestWord;
    }

    ////////////////// MÉTODOS PARA ACTUALIZAR LOS ATRIBUTOS ////////////////////////////////////////

    /**
     * Método para actualizar la puntuación total del jugador (siempre que sea posible).
     * 
     * @param gamePoints La puntuación obtenida en la partida actual.
     */
   public void updateTotalPoint(int gamePoints) {
       //en el caso de que la puntuación pueda ser negativa y se pueda restar.
       if(gamePoints < 0) {
            if((this.totalPoints + gamePoints) >= 0) {
                this.totalPoints += gamePoints;
            }
            else this.totalPoints = 0;
       }
       else this.totalPoints += gamePoints;
   }

   /**
    *  Método para actualizar el número de partidas ganadas.
    */
   public void updateWins() {
       ++this.wins;
   }

   /**
    * Método para actualizar el número de partidas perdidas.
    */
   public void updateLoses() {
       ++this.loses;
   }

   /**
    * Método para actualizar la tasa de victorias del jugador.
    */
   public void updateWinRate() {
       //contemplamos el caso de calc error:
       int partidasTotales = this.wins + this.loses; 
       if(partidasTotales != 0) {
            if(this.loses != 0) {
                this.winRate = (double)this.wins/partidasTotales;
            }
            else winRate = 1.0f;
       }
       else this.winRate = 0.0f;
   }

    /**
     * Método para actualizar la mejor palabra jugada por el jugador.
     *      - Si no hay mejor palabra, se establece la palabra jugada en la partida actual como la mejor.
     *      - Si hay una mejor palabra, se compara la longitud de ambas palabras y se actualiza si es necesario.
     * @param wordGame La palabra jugada en la partida actual.
     */
   public void updateBestWord(String wordGame) { 
    //analizamos los posibles casos: 
    if(this.bestWord == null) bestWord = wordGame.trim(); 
    else {
       if (this.bestWord.trim().length() < wordGame.trim().length()) this.bestWord = wordGame.trim(); 
       else if(this.bestWord.trim().length() == wordGame.trim().length()) {
        if(this.bestWord.trim().compareTo(wordGame.trim()) < 0) this.bestWord = wordGame.trim(); 
       }
    }
   }

    /**
     * Método para actualizar el número de partidas jugadas.
     */
   public void updateNumMatches() {
       ++this.num_matches;
   }

   /**
    * Método para actualizar el número total de palabras jugadas por el jugador.
    *
    * @param numWordsGame El número de palabras jugadas en la partida actual.
    * @throws IllegalArgumentException Si el número de palabras es negativo.
    */
   public void updateTotalWords(int numWordsGame) {
    if(numWordsGame < 0) throw new IllegalArgumentException("Error Estadistica: no puedes jugar un número negativo de palabras\n");  
    else this.totalWords += numWordsGame;
   }
 
   /**
    * Método para actualizar la puntuación máxima del jugador.
    *
    * @param gamePoints La puntuación obtenida en la partida actual.
    */
   public void updateMaxPoints(int gamePoints) {
    if(this.maxPoints <= gamePoints) maxPoints = gamePoints; 
   }

   /**
    * Método para actualizar la puntuación media del jugador.
    */
   public void updateAveragePoints() {
    if(this.num_matches > 0) {
        this.avgPoints = (double)this.totalPoints/(double)this.num_matches;  
    }
    else this.avgPoints = 0.0;
   }

   //Pre: recibe como parámetro de entrada la puntuación de la partida, la palabra más larga de la partida, si es ganador o no y el número de palabras totales de la partida que ha puntuado. 
   //Post: se centraliza en el método la actualización de todas las estadisitcas necesarias del jugador al acabar la partida.
   /**
    * Método para actualizar las estadísticas del jugador al finalizar una partida.
    *
    * La actualización incluye la actualización de todos los parámetros pasados como parámetros de entrada en el método.
    *
    * @param gamePoints La puntuación obtenida en la partida actual.
    * @param bestWordGame La mejor palabra jugada en la partida actual.
    * @param winner Indica si el jugador ha ganado la partida.
    * @param numWordsGame El número de palabras jugadas en la partida actual. 
   */ 
   public void updateEstadisticasJugador(int gamePoints, String bestWordGame, boolean winner, int numWordsGame) {
     this.updateTotalPoint(gamePoints); 
     this.updateBestWord(bestWordGame);
     if(winner) this.updateWins();
     else this.updateLoses(); 
     this.updateWinRate();
     //factor común acutalizar las partidas totales jugadas. 
     this.updateNumMatches();
     this.updateTotalWords(numWordsGame); 
     this.updateMaxPoints(gamePoints); 
     this.updateAveragePoints();
   }
}
