import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import dominio.*;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class TestRanking {

    @Before 
    public void setUp() {
        // Reset the singleton instance before each test
        Ranking.getInstance().reset();
    }

    @Test
    public void testRankingSingleton() {
        Ranking ranking1 = Ranking.getInstance(); 
        Ranking ranking2 = Ranking.getInstance(); 
        Ranking ranking3 = Ranking.getInstance(); 
        //verficamos que todas las instancias apuntan al mismo lugar. 
        assertSame(ranking1, ranking2); 
        assertSame(ranking2, ranking3); 
        assertSame(ranking1, ranking3); 
    }

    @Test
    public void testOrdenarRankingPorPuntuacion() {
        Ranking ranking = Ranking.getInstance(); 
        List<Usuario> usuarios = new ArrayList<>(); 
        //creamos varios usuarios y les asignamos diversas puntuaciones diferentes. 
        Usuario usuario1 = new Usuario("userTest1", "1"); 
        Usuario usuario2 = new Usuario("userTest2", "2"); 
        Usuario usuario3 = new Usuario("userTest3", "3"); 
        //agregamos las puntuaciones a todos los users: 
        int punt1 = 100; 
        int punt2 = 500; 
        int punt3 = 200;
        usuario1.getStats().updateTotalPoint(punt1); 
        usuario2.getStats().updateTotalPoint(punt2); 
        usuario3.getStats().updateTotalPoint(punt3); 
        //añadimos los usuarios a la lista. 
        usuarios.add(usuario1); 
        usuarios.add(usuario2); 
        usuarios.add(usuario3);
        //verificamos que 2 es el primero, 3 el segundo 1 el último de la lista. 
        ranking.updateRanking(usuarios);
        List<String> rankingInfo = ranking.getRanking();
        System.out.println("RANKING: " + rankingInfo);
        assertTrue(rankingInfo.get(0).contains(usuario2.getUsername())); 
        assertTrue(rankingInfo.get(1).contains(usuario3.getUsername())); 
        assertTrue(rankingInfo.get(2).contains(usuario1.getUsername())); 
    }

    @Test(expected = IllegalArgumentException.class) 
    public void testgetRankingVacio() {
        Ranking ranking = Ranking.getInstance(); 
        List<String> rankingInfo = ranking.getRanking();
        System.out.println("RANKING: " + rankingInfo);
    }
    
    @Test 
    public void testRankingUnSoloJugador() {
        Ranking ranking = Ranking.getInstance();
        //Solo creamos un único usuario: 
        Usuario usuario = new Usuario("userTest", "1");
        List<Usuario> usuarios = new ArrayList<>();
        int punt = 1100000; 
        usuario.getStats().updateTotalPoint(punt); 
        usuarios.add(usuario);
        //ordenamos el ranking: 
        ranking.updateRanking(usuarios); 
        assertTrue(ranking.getRanking().get(0).contains(usuario.getUsername())); 
    }

    @Test
    public void testUsuariosConTotalPointsIguales() {
        //en este caso el valor esperado es que  si tenemos dos users el primero es el nombre mayor.
        Ranking ranking = Ranking.getInstance(); 
        Usuario usuario1 = new Usuario("userTest1", "1"); 
        Usuario usuario2 = new Usuario("userTest2", "2"); 
        //agregamos las correspondientes mismas puntuacioness: 
        int punt = 100; 
        usuario1.getStats().updateTotalPoint(punt); 
        usuario2.getStats().updateTotalPoint(punt); 
        //actualizamos ranking: 
        List<Usuario> usuarios = new ArrayList<>(); 
        usuarios.add(usuario1); 
        usuarios.add(usuario2); 
        ranking.updateRanking(usuarios); 
        List<String> rankingInfo = ranking.getRanking(); 
        System.out.println("RANKING: " + rankingInfo);
        assertTrue(rankingInfo.get(0).contains(usuario2.getUsername())); 
        assertTrue(rankingInfo.get(1).contains(usuario1.getUsername())); 
    }
    
    @Test
    public void testOrdenarRankingCriterioPuntuacionMedia() {
        Ranking ranking = Ranking.getInstance(); 
        List<Usuario> usuarios = new ArrayList<>(); 
        //creamos varios usuarios y les asignamos diversas puntuaciones diferentes. 
        Usuario usuario1 = new Usuario("userTest1", "1"); 
        Usuario usuario2 = new Usuario("userTest2", "2"); 
        Usuario usuario3 = new Usuario("userTest3", "3"); 
        //agregamos las puntuaciones a todos los users: 
        int puntTotalUsers = 1000;
        usuario1.getStats().updateTotalPoint(puntTotalUsers); 
        usuario2.getStats().updateTotalPoint(puntTotalUsers); 
        usuario3.getStats().updateTotalPoint(puntTotalUsers); 
        //los users tienen los mismos maxpoints.
        int max = 1000;
        usuario1.getStats().updateMaxPoints(max); 
        usuario2.getStats().updateMaxPoints(max);
        usuario3.getStats().updateMaxPoints(max); 
        //todos los users van a jugar diversas partidas: 
        //user1 2 partidas. 
        usuario1.getStats().updateNumMatches(); 
        usuario1.getStats().updateNumMatches();
        //user2 1 partida. 
        usuario2.getStats().updateNumMatches(); 
        //user3 3 partidas
        usuario3.getStats().updateNumMatches(); 
        usuario3.getStats().updateNumMatches(); 
        usuario3.getStats().updateNumMatches(); 
        //actualizamos averagePoints: 
        usuario1.getStats().updateAveragePoints(); 
        usuario2.getStats().updateAveragePoints();
        usuario3.getStats().updateAveragePoints();  
        //añadimos los usuarios a la lista. 
        usuarios.add(usuario1); 
        usuarios.add(usuario2); 
        usuarios.add(usuario3);
        //verificamos que 2 es el primero, 1 el segundo y 3 el último de la lista. 
        ranking.updateRanking(usuarios);
        List<String> rankingInfo = ranking.getRanking();
        assertTrue(rankingInfo.get(0).contains(usuario2.getUsername())); 
        assertTrue(rankingInfo.get(1).contains(usuario1.getUsername())); 
        assertTrue(rankingInfo.get(2).contains(usuario3.getUsername())); 
    }

    @Test
    public void testOrdenarRankingCriterioPuntuacionMaxima() {
        Ranking ranking = Ranking.getInstance(); 
        List<Usuario> usuarios = new ArrayList<>(); 
        //creamos varios usuarios y les asignamos diversas puntuaciones diferentes. 
        Usuario usuario1 = new Usuario("userTest1", "1"); 
        Usuario usuario2 = new Usuario("userTest2", "2"); 
        Usuario usuario3 = new Usuario("userTest3", "3"); 
        //agregamos las puntuaciones a todos los users: 
        int puntTotalUsers = 1000;
        usuario1.getStats().updateTotalPoint(puntTotalUsers); 
        usuario2.getStats().updateTotalPoint(puntTotalUsers); 
        usuario3.getStats().updateTotalPoint(puntTotalUsers); 
        //añadimos las maximas puntuaciones. 
        int max1 = 1500;
        int max2 = 1700;  
        int max3 = 2000;
        usuario1.getStats().updateMaxPoints(max1); 
        usuario2.getStats().updateMaxPoints(max2);
        usuario3.getStats().updateMaxPoints(max3); 
        //añadimos los usuarios a la lista. 
        usuarios.add(usuario1); 
        usuarios.add(usuario2); 
        usuarios.add(usuario3);
        //verificamos que 2 es el primero, 1 el segundo y 3 el último de la lista. 
        ranking.updateRanking(usuarios);
        List<String> rankingInfo = ranking.getRanking();
        assertTrue(rankingInfo.get(0).contains(usuario3.getUsername())); 
        assertTrue(rankingInfo.get(1).contains(usuario2.getUsername())); 
        assertTrue(rankingInfo.get(2).contains(usuario1.getUsername())); 
    }

    @Test
    public void testOrdenarRankingPorCriterioNumMatches() {
        Ranking ranking = Ranking.getInstance(); 
        List<Usuario> usuarios = new ArrayList<>(); 
        //creamos varios usuarios y les asignamos diversas puntuaciones diferentes. 
        Usuario usuario1 = new Usuario("userTest1", "1"); 
        Usuario usuario2 = new Usuario("userTest2", "2"); 
        Usuario usuario3 = new Usuario("userTest3", "3"); 
        //agregamos las puntuaciones a todos los users: 
        int puntTotalUsers = 1000;
        usuario1.getStats().updateTotalPoint(puntTotalUsers); 
        usuario2.getStats().updateTotalPoint(puntTotalUsers); 
        usuario3.getStats().updateTotalPoint(puntTotalUsers); 
        //los users tienen los mismos maxpoints.
        int max = 1000;
        usuario1.getStats().updateMaxPoints(max); 
        usuario2.getStats().updateMaxPoints(max);
        usuario3.getStats().updateMaxPoints(max); 
        //todos los users van a jugar diversas partidas: 
        //user1 2 partidas. 
        usuario1.getStats().updateNumMatches(); 
        usuario1.getStats().updateNumMatches();
        //user2 1 partida. 
        usuario2.getStats().updateNumMatches(); 
        //user3 3 partidas
        usuario3.getStats().updateNumMatches(); 
        usuario3.getStats().updateNumMatches(); 
        usuario3.getStats().updateNumMatches(); 
        //añadimos los usuarios a la lista. 
        usuarios.add(usuario1); 
        usuarios.add(usuario2); 
        usuarios.add(usuario3);
        //verificamos que 2 es el primero, 1 el segundo y 3 el último de la lista. 
        ranking.updateRanking(usuarios);
        List<String> rankingInfo = ranking.getRanking();
        assertTrue(rankingInfo.get(0).contains(usuario2.getUsername())); 
        assertTrue(rankingInfo.get(1).contains(usuario1.getUsername())); 
        assertTrue(rankingInfo.get(2).contains(usuario3.getUsername())); 
    }

    @Test
    public void testOrdenarRankingPorCriterioWins() {
        Ranking ranking = Ranking.getInstance(); 
        List<Usuario> usuarios = new ArrayList<>(); 
        //creamos varios usuarios y les asignamos diversas puntuaciones diferentes. 
        Usuario usuario1 = new Usuario("userTest1", "1"); 
        Usuario usuario2 = new Usuario("userTest2", "2"); 
        Usuario usuario3 = new Usuario("userTest3", "3"); 
        //agregamos las puntuaciones a todos los users: 
        int puntTotalUsers = 1000;
        usuario1.getStats().updateTotalPoint(puntTotalUsers); 
        usuario2.getStats().updateTotalPoint(puntTotalUsers); 
        usuario3.getStats().updateTotalPoint(puntTotalUsers); 

        //COMPROBAMOS RESULTADOS DE LOS TESTS: 
        System.out.println("PUNTUACION TOTAL: " + usuario1.getStats().getTotalPoints());
        System.out.println("PUNTUACION TOTAL: " + usuario2.getStats().getTotalPoints());
        System.out.println("PUNTUACION TOTAL: " + usuario3.getStats().getTotalPoints());

        //los users tienen los mismos maxpoints.
        int max = 1000;
        usuario1.getStats().updateMaxPoints(max); 
        usuario2.getStats().updateMaxPoints(max);
        usuario3.getStats().updateMaxPoints(max); 

        //COMPROBAMOS RESULTADOS DE LOS TESTS:
        System.out.println("PUNTUACION MAXIMA: " + usuario1.getStats().getMaxPoints());
        System.out.println("PUNTUACION MAXIMA: " + usuario2.getStats().getMaxPoints());
        System.out.println("PUNTUACION MAXIMA: " + usuario3.getStats().getMaxPoints());

        //todos los users van a jugar diversas partidas: 
        //user1 3 partidas. 
        usuario1.getStats().updateNumMatches(); 
        usuario1.getStats().updateNumMatches();
        usuario1.getStats().updateNumMatches();
        //user2 3 partidas. 
        usuario2.getStats().updateNumMatches();
        usuario2.getStats().updateNumMatches();
        usuario2.getStats().updateNumMatches();
        //user3 3 partidas. 
        usuario3.getStats().updateNumMatches(); 
        usuario3.getStats().updateNumMatches();
        usuario3.getStats().updateNumMatches();

        //COMPROBAMOS RESULTADOS DE LOS TESTS:
        System.out.println("NUM PARTIDAS JUGADAS: " + usuario1.getStats().getNumMatches());
        System.out.println("NUM PARTIDAS JUGADAS: " + usuario2.getStats().getNumMatches());
        System.out.println("NUM PARTIDAS JUGADAS: " + usuario3.getStats().getNumMatches());

        //tendran el mismo avgpoints- 
        usuario1.getStats().updateAveragePoints();
        usuario2.getStats().updateAveragePoints(); 
        usuario3.getStats().updateAveragePoints(); 

        //COMPROBAMOS RESULTADOS DE LOS TESTS:
        System.out.println("MEDIA PUNTOS: " + usuario1.getStats().getAveragePoints());
        System.out.println("MEDIA PUNTOS: " + usuario2.getStats().getAveragePoints());
        System.out.println("MEDIA PUNTOS: " + usuario3.getStats().getAveragePoints());
        //de las 3 partidas jugadas: 
        //user1 gana1 pierde 2. 
        usuario1.getStats().updateWins(); 
        usuario1.getStats().updateLoses(); 
        usuario1.getStats().updateLoses(); 
        //user2 gana todas: 
        usuario2.getStats().updateWins();
        usuario2.getStats().updateWins();
        usuario2.getStats().updateWins(); 
        //user3 gana dos, pierde 1
        usuario3.getStats().updateWins();
        usuario3.getStats().updateWins();
        usuario3.getStats().updateLoses(); 

        //COMPROBAMOS RESULTADOS DE LOS TESTS:
        System.out.println("NUM PARTIDAS GANADAS: " + usuario1.getStats().getWins());
        System.out.println("NUM PARTIDAS GANADAS: " + usuario2.getStats().getWins());
        System.out.println("NUM PARTIDAS GANADAS: " + usuario3.getStats().getWins());
        //COMPROBAMOS RESULTADOS DE LOS TESTS:
        System.out.println("NUM PARTIDAS PERDIDAS: " + usuario1.getStats().getLoses());
        System.out.println("NUM PARTIDAS PERDIDAS: " + usuario2.getStats().getLoses());
        System.out.println("NUM PARTIDAS PERDIDAS: " + usuario3.getStats().getLoses());

        //añadimos los usuarios a la lista. 
        usuarios.add(usuario1); 
        usuarios.add(usuario2); 
        usuarios.add(usuario3);

        //verificamos que 2 es el primero, 3 el segundo y 1 el último de la lista. 
        ranking.updateRanking(usuarios);
        List<String> rankingInfo = ranking.getRanking();
        System.out.println("RANKING: " + rankingInfo);
        assertTrue(rankingInfo.get(0).contains(usuario2.getUsername())); 
        assertTrue(rankingInfo.get(1).contains(usuario3.getUsername())); 
        assertTrue(rankingInfo.get(2).contains(usuario1.getUsername())); 
    }

    @Test
    public void testOrdenarRankingPorCriterioLoses() {
        Ranking ranking = Ranking.getInstance(); 
        List<Usuario> usuarios = new ArrayList<>(); 
        //creamos varios usuarios y les asignamos diversas puntuaciones diferentes. 
        Usuario usuario1 = new Usuario("userTest1", "1"); 
        Usuario usuario2 = new Usuario("userTest2", "2"); 
        Usuario usuario3 = new Usuario("userTest3", "3"); 
        //agregamos las puntuaciones a todos los users: 
        int puntTotalUsers = 1000;
        usuario1.getStats().updateTotalPoint(puntTotalUsers); 
        usuario2.getStats().updateTotalPoint(puntTotalUsers); 
        usuario3.getStats().updateTotalPoint(puntTotalUsers); 
        //los users tienen los mismos maxpoints.
        int max = 1000;
        usuario1.getStats().updateMaxPoints(max); 
        usuario2.getStats().updateMaxPoints(max);
        usuario3.getStats().updateMaxPoints(max); 
        //todos los users van a jugar diversas partidas: 
        //user1 4 partidas. 
        usuario1.getStats().updateNumMatches(); 
        usuario1.getStats().updateNumMatches();
        usuario1.getStats().updateNumMatches();
        usuario1.getStats().updateNumMatches(); 
        //user2 4 partidas. 
        usuario2.getStats().updateNumMatches();
        usuario2.getStats().updateNumMatches();
        usuario2.getStats().updateNumMatches();
        usuario2.getStats().updateNumMatches();
        //user3 4 partidas. 
        usuario3.getStats().updateNumMatches(); 
        usuario3.getStats().updateNumMatches();
        usuario3.getStats().updateNumMatches();
        usuario3.getStats().updateNumMatches();
        //tendran el mismo avgpoints- 
        usuario1.getStats().updateAveragePoints();
        usuario2.getStats().updateAveragePoints(); 
        usuario3.getStats().updateAveragePoints(); 
        //de las 3 partidas jugadas: 
        //user1 gana 3 pierde 1. 
        usuario1.getStats().updateWins(); 
        usuario1.getStats().updateWins(); 
        usuario1.getStats().updateWins(); 
        usuario1.getStats().updateLoses(); 
        //user2 gana 2 pierde 2: 
        usuario2.getStats().updateWins();
        usuario2.getStats().updateWins();
        usuario2.getStats().updateLoses();
        usuario2.getStats().updateLoses();
        //user3 gana 1, pierde 3
        usuario3.getStats().updateWins();
        usuario3.getStats().updateLoses();
        usuario3.getStats().updateLoses(); 
        usuario3.getStats().updateLoses(); 
        //añadimos los usuarios a la lista. 
        usuarios.add(usuario1); 
        usuarios.add(usuario2); 
        usuarios.add(usuario3);
        //verificamos que 1 es el primero, 2 el segundo y 3 el último de la lista. 
        ranking.updateRanking(usuarios);
        List<String> rankingInfo = ranking.getRanking();
        assertTrue(rankingInfo.get(0).contains(usuario1.getUsername())); 
        assertTrue(rankingInfo.get(1).contains(usuario2.getUsername())); 
        assertTrue(rankingInfo.get(2).contains(usuario3.getUsername())); 
    }

    @Test
    public void testOrdenarRankingPorCriterioTotalWords() {
        Ranking ranking = Ranking.getInstance(); 
        List<Usuario> usuarios = new ArrayList<>(); 
        //creamos varios usuarios y les asignamos diversas puntuaciones diferentes. 
        Usuario usuario1 = new Usuario("userTest1", "1"); 
        Usuario usuario2 = new Usuario("userTest2", "2"); 
        Usuario usuario3 = new Usuario("userTest3", "3"); 
        //agregamos las puntuaciones a todos los users: 
        int puntTotalUsers = 1000;
        usuario1.getStats().updateTotalPoint(puntTotalUsers); 
        usuario2.getStats().updateTotalPoint(puntTotalUsers); 
        usuario3.getStats().updateTotalPoint(puntTotalUsers); 
        //los users tienen los mismos maxpoints.
        int max = 1000;
        usuario1.getStats().updateMaxPoints(max); 
        usuario2.getStats().updateMaxPoints(max);
        usuario3.getStats().updateMaxPoints(max); 
        //todos los users van a jugar diversas partidas: 
        //user1 1 partida. 
        usuario1.getStats().updateNumMatches(); 
        //user2 1 partida. 
        usuario2.getStats().updateNumMatches();
        //user3 1 partida. 
        usuario3.getStats().updateNumMatches(); 
        //tendran el mismo avgpoints 
        usuario1.getStats().updateAveragePoints();
        usuario2.getStats().updateAveragePoints(); 
        usuario3.getStats().updateAveragePoints(); 
        //de las 3 partidas jugadas: 
        //user1 gana 1 
        usuario1.getStats().updateWins();  
        //user2 gana 1 
        usuario2.getStats().updateWins();
        //user3 gana 1
        usuario3.getStats().updateWins();
        //no hace falta añadir loses porque solo es una win y lose por defecto es 0. 
        int words1 = 100; 
        int words2 = 500; 
        int words3 = 250; 
        //añadimos las palabras totales jugadas por los users: 
        usuario1.getStats().updateTotalWords(words1); 
        usuario2.getStats().updateTotalWords(words2); 
        usuario3.getStats().updateTotalWords(words3); 
        //añadimos los usuarios a la lista.
        usuarios.add(usuario1); 
        usuarios.add(usuario2); 
        usuarios.add(usuario3);
        //verificamos que 2 es el primero, 3 el segundo y 1 el último de la lista. 
        ranking.updateRanking(usuarios);
        //obtenemos el ranking. 
        List<String> rankingInfo = ranking.getRanking();
        //verificamos que se ha ordenado de forma correcta. 
        assertTrue(rankingInfo.get(0).contains(usuario2.getUsername())); 
        assertTrue(rankingInfo.get(1).contains(usuario3.getUsername())); 
        assertTrue(rankingInfo.get(2).contains(usuario1.getUsername())); 
    }

    @Test
    public void testOrdenarRankingPorCriterioBestWord() {
        Ranking ranking = Ranking.getInstance(); 
        List<Usuario> usuarios = new ArrayList<>(); 
        //creamos varios usuarios y les asignamos diversas puntuaciones diferentes. 
        Usuario usuario1 = new Usuario("userTest1", "1"); 
        Usuario usuario2 = new Usuario("userTest2", "2"); 
        Usuario usuario3 = new Usuario("userTest3", "3"); 
        //agregamos las puntuaciones a todos los users: 
        int puntTotalUsers = 1000;
        usuario1.getStats().updateTotalPoint(puntTotalUsers); 
        usuario2.getStats().updateTotalPoint(puntTotalUsers); 
        usuario3.getStats().updateTotalPoint(puntTotalUsers); 
        //los users tienen los mismos maxpoints.
        int max = 1000;
        usuario1.getStats().updateMaxPoints(max); 
        usuario2.getStats().updateMaxPoints(max);
        usuario3.getStats().updateMaxPoints(max); 
        //todos los users van a jugar diversas partidas: 
        //user1 1 partida. 
        usuario1.getStats().updateNumMatches(); 
        //user2 1 partida. 
        usuario2.getStats().updateNumMatches();
        //user3 1 partida. 
        usuario3.getStats().updateNumMatches(); 
        //tendran el mismo avgpoints 
        usuario1.getStats().updateAveragePoints();
        usuario2.getStats().updateAveragePoints(); 
        usuario3.getStats().updateAveragePoints(); 
        //de las 3 partidas jugadas: 
        //user1 gana 1 
        usuario1.getStats().updateWins();  
        //user2 gana 1 
        usuario2.getStats().updateWins();
        //user3 gana 1
        usuario3.getStats().updateWins();
        //no hace falta añadir loses porque solo es una win y lose por defecto es 0. 
        int words = 100; 
        //añadimos las palabras totales jugadas por los users: 
        usuario1.getStats().updateTotalWords(words); 
        usuario2.getStats().updateTotalWords(words); 
        usuario3.getStats().updateTotalWords(words); 
        //asignamos diferentes mejores palabras a los users: 
        String word1 = "uno" ;
        String word2 = "ingeniería";
        String word3 = "esternocleidomastoideo"; 
        //suprmimos espacios por si las moscas. 
        word1 = word1.trim(); 
        word2 = word2.trim(); 
        word3 = word3.trim(); 
        usuario1.getStats().updateBestWord(word1); 
        usuario2.getStats().updateBestWord(word2);
        usuario3.getStats().updateBestWord(word3); 
        //añadimos los usuarios a la lista.
        usuarios.add(usuario1); 
        usuarios.add(usuario2); 
        usuarios.add(usuario3);
        //verificamos que 3 es el primero, 2 el segundo y 1 el último de la lista. 
        ranking.updateRanking(usuarios);
        //obtenemos el ranking. 
        List<String> rankingInfo = ranking.getRanking();
        //verificamos que se ha ordenado de forma correcta. 
        assertTrue(rankingInfo.get(0).contains(usuario3.getUsername())); 
        assertTrue(rankingInfo.get(1).contains(usuario2.getUsername())); 
        assertTrue(rankingInfo.get(2).contains(usuario1.getUsername())); 
    }
}