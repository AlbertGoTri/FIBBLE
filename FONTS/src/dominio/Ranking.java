//SINGLETON!
package dominio;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import dominio.Usuario;

/**
 * Clase Ranking
 * <p>
 * Esta clase implementa el patrón Singleton y contiene la lógica para gestionar el ranking de los jugadores.
 * </p>
 * <p>
 * <b>Propósito:</b> Ordenar y mostrar a los jugadores (que usan la máquiana) en función de su rendimiento en el juego 
 * utilizando varios criterios de comparación detallados en el método userComp.
 * <p>
 * <b>Patrón Singleton:</b> Asegura que solo haya una instancia de la clase Ranking en todo el sistema.
 * </p>
 */
public class Ranking {
    /** Instancia única de la clase Ranking (Singleton).*/
    private static Ranking instancia; 
    /** Vector que representa todos los usuarios humanos que usan el sistema o lo ha  usado. */  
    private static Vector<Usuario> u_vec;

    /**
     * Constructor privado para evitar la creación de instancias externas.
     * <p>
     * Este constructor inicializa la instancia a null y crea un nuevo Vector para almacenar los usuarios.
     * </p>
     */
    private Ranking() {
        instancia = null; 
        u_vec = new Vector<>();
    }

    /**
     * Método estático para obtener la única instancia de la clase Ranking.
     * Si la instancia no existe, se crea una nueva.
     * 
     * @return La única instancia de la clase Ranking.
     */
    public static Ranking getInstance() {
        if(instancia == null) {
            instancia = new Ranking(); 
        }
        return instancia; 
    }

    /**
     * Método para reiniciar el ranking.
     *  Limpia el Vector que almacena los usuarios en el ranking.
     */ 
    public void reset() {
        u_vec.clear(); 
    }

    /**
     * Método privado para comparar dos usuarios según varios criterios.
     * <p>
     * Los criterios que seguimos a la hora de hacer el sorting son:
     * </p>
     * <p> 
     * <b>- Puntuación total:</b> gana el usuario con mayor puntuación total.
     * <b>- Media de puntos por partida:</b> gana el usuario con mayor media de puntos por partida.
     * <b>- Puntuación máxima:</b> gana el usuario con mayor puntuación máxima.
     * <b>- Número de partidas jugadas:</b> gana el usuario que ha jugado menos partidas. 
     * <b>- Partidas ganadas:</b> gana el usuario con más partidas ganadas.
     * <b>- Partidas perdidas:</b> gana el usuario con menos partidas perdidas.
     * <b>- Palabras totales jugadas:</b> gana el usuario que ha jugado menos palabras.
     * <b>- Mejor palabra:</b> gana el usuario que ha jugado la palabra más larga.
     * <b>- Nombre de usuario:</b> gana el usuario con nombre de usuario alfabéticamente menor.
     * 
     * @param u1 El primer usuario a comparar.
     * @param u2 El segundo usuario a comparar.
     * @return Un valor entero que indica la comparación entre los dos usuarios.
     *       - Un valor negativo si u1 es menor que u2.
     *       - Un valor positivo si u1 es mayor que u2.
     */
    private int userComp(Usuario u1, Usuario u2) {
        Estadistica eu1 = u1.getStats(); 
        Estadistica eu2 = u2.getStats();
        //miramos todos los criterios que queriamos seguir: 
        if(eu1.getTotalPoints() != eu2.getTotalPoints()) {
            return Integer.compare(eu2.getTotalPoints(), eu1.getTotalPoints()); 
        }
        else if(eu1.getAveragePoints() != eu2.getAveragePoints()) {
            return Double.compare(eu2.getAveragePoints(), eu1.getAveragePoints()); 
        }
        else if(eu1.getMaxPoints() != eu2.getMaxPoints()) {
            return Integer.compare(eu2.getMaxPoints(), eu1.getMaxPoints()); 
        }
        else if(eu1.getNumMatches() != eu2.getNumMatches()) {
            return Integer.compare(eu1.getNumMatches(), eu2.getNumMatches()); 
        }
        else if(eu1.getWins() != eu2.getWins()) {
            return Integer.compare(eu2.getWins(), eu1.getWins()); 
        }
        else if(eu1.getLoses() != eu2.getLoses()) {
            return Integer.compare(eu1.getLoses(), eu2.getLoses()); 
        }
        else if(eu1.getTotalWords() != eu2.getTotalWords()) {
            return Integer.compare(eu2.getTotalWords(), eu1.getTotalWords()); 
        }
        else if(eu1.getBestWord() != null && eu2.getBestWord() != null) {
            return Integer.compare(eu2.getBestWord().length(), eu1.getBestWord().length());
        }
        else if(eu1.getBestWord() != null && eu2.getBestWord() == null) {
            return 1; 
        }
        else if(eu1.getBestWord() == null && eu2.getBestWord() != null) {
            return -1; 
        }
        else return u2.getUsername().compareTo(u1.getUsername()); //si son iguales.
    }

    /**
     * Método privado para transformar una lista de usuarios en un Vector.
     * 
     * @param usuarios La lista de usuarios a transformar.
     * @return Un Vector que contiene los usuarios de la lista.
     */
    private Vector<Usuario> transformaLista(List<Usuario> usuarios) {
        Vector<Usuario> res = new Vector<>();  
        Iterator<Usuario> it = usuarios.iterator(); 
        while(it.hasNext()) {
            //it.next == añade el elemento actual y actualiza el iterador en uno. 
            res.add(it.next()); 
        }
        return res;
    }
 
    /**
     * Método para actualizar el ranking de usuarios ordenándolo siguiendo los criterios préviamente establecidos.
     * 
     * @param usuarios La lista de usuarios a actualizar en el ranking.
     * @throws IllegalArgumentException Si la lista de usuarios está vacía (no hay usuarios registrados en el sistema).
     */
    public void updateRanking(List<Usuario> usuarios) {
        if(!usuarios.isEmpty()) {
            //ordenamos la lista de usuarios en función de los criterios establecidos. 
            usuarios.sort(this::userComp); 
            u_vec = transformaLista(usuarios);
        }
        else {
            throw new IllegalArgumentException("Error de Ranking: no se puede mostrar porque no hay usuarios registrados en el sistema\n"); 
        }
    }

    /** Método que obtiene el vecotro post transformación de la lista de usuarios
     * 
     * @return El vector de usuarios.
     */
    public Vector<Usuario> getSortedVectorRanking() {
        return u_vec;
    }

    /**
     * Método para obtener el ranking de usuarios.
     * 
     * @return Una lista de cadenas que contiene la información del ranking de usuarios.
     * @throws IllegalArgumentException Si la lista de usuarios está vacía (no hay usuarios registrados en el sistema).
     */
    public List<String> getRanking() {
        List<String> rankingInfo = new ArrayList<>();
        String userInfo;  
        if(!u_vec.isEmpty()) {
            for(int i = 0; i < u_vec.size(); ++i) {
                Usuario u = u_vec.get(i); 
                Estadistica ue = u.getStats(); 
                //vars para printear por user en ranking. 
                String name = u.getUsername(); 
                int totalPoints = ue.getTotalPoints() ; 
                double avgPoints = ue.getAveragePoints(); 
                int maxPoints = ue.getMaxPoints(); 
                int num_matches = ue.getNumMatches(); 
                int wins = ue.getWins(); 
                int loses = ue.getLoses(); 
                int totalWords = ue.getTotalWords(); 
                String bestWord = ue.getBestWord(); 
                userInfo = "=================================================\n" + 
                                    (i +  1) + "." + name + "\n" +
                                    "PUNTUACIÓN TOTAL: " + totalPoints + "\n" +
                                    "MEDIA DE PUNTOS POR PARTIDA: " + avgPoints + "\n" +
                                    "PUNTUACIÓN MÁXIMA: " + maxPoints + "\n" +
                                    "PARTIDAS JUGADAS: " + num_matches + "\n" + 
                                    "PARTIDAS GANADAS: " + wins + "\n" + 
                                    "PARTIDAS PERDIDAS: " + loses + "\n" + 
                                    "PALABRAS TOTALES JUGADAS: " + totalWords + "\n" +
                                    "MEJOR PALABRA: " + bestWord + "\n" + 
                                    "=================================================\n";
                rankingInfo.add(userInfo); 
            }
        }
        else {
            if(u_vec.isEmpty() || u_vec == null) {
                throw new IllegalArgumentException("Error de Ranking: no se puede mostrar porque no hay usuarios registrados en el sistema\n"); 
            }
        }
        return rankingInfo;
    }
}
