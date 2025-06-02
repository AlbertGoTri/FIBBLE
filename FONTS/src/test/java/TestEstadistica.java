import org.junit.Before;
import org.junit.Test;
import dominio.*;

import static org.junit.Assert.*;

/**
 * Clase TestEstadistica:
 * Esta clase contiene los tests unitarios para verificar el correcto funcionamiento de la clase Estadistica. 
 * Se testean casos válidos e inválidos para garantizar que la clase maneje correctamente las estadísticas del jugador.
 * 
 * El único método que contiene la clase es:
 * - `estadisticas`: una instancia de la clase Estadistica que se utiliza para realizar las pruebas.
 */
public class TestEstadistica {
    private Estadistica estadisticas;

    //necesitamos una instanca previa de estadística:
    /**
     * Método de configuración que se ejecuta antes de cada test.
     * Inicializa una nueva instancia de la clase Estadistica.
     */
    @Before 
    public void setUp(){
        estadisticas = new Estadistica(); 
    }

    /**
     * Test para la actualización de puntos totales con valores válidos.
     * Este test verifica que la suma de los puntos totales se realice correctamente.
     */
    @Test
    public void testActualizarPuntosTotalesValido(){
        int puntosTotales1 = 10000; 
        int puntosTotales2 = 10000;
        estadisticas.updateTotalPoint(puntosTotales1); 
        assertEquals(puntosTotales1, estadisticas.getTotalPoints()); 
        //hacemos una segunda comprobocación. 
        estadisticas.updateTotalPoint(puntosTotales2); 
        assertEquals(puntosTotales1 + puntosTotales2, estadisticas.getTotalPoints()); 
    }

    /**
     * Test para la actualización de puntos totales con un valor negativo mayor que el total acumulado.
     * Este test verifica que el total de puntos no se vuelva negativo.
     */
    @Test 
    public void testActualizarPuntosTotalesConValorNegativoMayor(){
        int puntosTotales1 = 99; 
        estadisticas.updateTotalPoint(puntosTotales1);
        int puntosTotales2 = -100;
        estadisticas.updateTotalPoint(puntosTotales2); 
        assertEquals(0, estadisticas.getTotalPoints()); 
    }

    /**
     * Test para la actualización de puntos totales con un valor negativo válido.
     * Este test verifica que la suma de los puntos totales se realice correctamente.
     */
    @Test
    public void testActualizarPuntosTotalesConValorNegativoValido(){
        int puntosTotales1 = 500; 
        estadisticas.updateTotalPoint(puntosTotales1); 
        int puntosTotales2 = -250; 
        estadisticas.updateTotalPoint(puntosTotales2); 
        assertEquals(250, estadisticas.getTotalPoints()); 
    }

    /**
     * Test para la actualización válida de partidas ganadas por el usuario. 
     * Este test verifica que el número de partidas ganadas se incremente correctamente.
     */
    @Test
    public void testActualizarWinsValido(){
        estadisticas.updateWins(); 
        estadisticas.updateWins(); 
        estadisticas.updateWins(); 
        assertEquals(3, estadisticas.getWins()); 
    }

    /**
     * Test para la actualización válida de partidas perdidas por el usuario.
     * Este test verifica que el número de partidas perdidas se incremente correctamente.
     */
    @Test 
    public void testActualizarLosesValido(){
        estadisticas.updateLoses(); 
        estadisticas.updateLoses(); 
        estadisticas.updateLoses(); 
        assertEquals(3, estadisticas.getLoses());
    }

    /**
     * Test para la actualización válida de la tasa de victorias del usuario.
     * Este test verifica que la tasa de victorias se calcule correctamente
     */
    @Test
    public void testActualizarWinRateValido(){
        //es el caso de tener partidas jugadas y loses mayores que 0. 
        estadisticas.updateWins(); 
        estadisticas.updateLoses(); 
        estadisticas.updateLoses();
        estadisticas.updateWinRate(); 
        //winRate = 1.0/2.0 0.01 == tolerancia. 
        assertEquals(1.0/3.0f, estadisticas.getWinRate(), 0.01f);  
    }

    /**
     * Test para la actualización de la tasa de victorias del usuario con cero partidas perdidas.
     * Este test verifica que la tasa de victorias se calcule correctamente.
     */
    @Test 
    public void testActualizarWinRateLosesCero(){
        estadisticas.updateWins(); 
        estadisticas.updateWins(); 
        estadisticas.updateWinRate(); 
        //solo son partidas ganadas ==> WR tiene que ser 1. 
        assertEquals(1.0f, estadisticas.getWinRate(), 0.01f); 
    }

    /**
     * Test para la actualización de la tasa de victorias del usuario con cero partidas jugadas.
     * Este test verifica que la tasa de victorias se calcule correctamente.
     */
    @Test
    public void testActualizatWinRateSinPartidasJugadas(){
        assertEquals(0.0f, estadisticas.getWinRate(), 0.01f); 
    }

    /** 
     * Test para la actualización de la mejor palabra del usuario.
     * Este test verifica que la mejor palabra se actualice correctamente, 
     * con la incial lanzada. Préviamente no tiene palabras jugadas. 
     */
    @Test
    public void testActualizaBestWordInicial(){
        //contemplamos el caso inical bestWord nulo. 
        String nuevaPalabra = "software"; 
        estadisticas.updateBestWord(nuevaPalabra); 
        assertEquals(nuevaPalabra.trim(), estadisticas.getBestWord()); 
    }

    /**
     * Test para la actualización de la mejor palabra del usuario.
     * Este test verifica que la mejor palabra se actualice correctamente,
     * en este caso el jugador ha juga ya otra palabra en algún otro momento.
     * Se espera que la mejor palabra sea la más larga (última lanzada). 
     */
    @Test
    public void testActualizarBestWordPalabraMasLarga(){
        //tenemos una bestWord corta y añadimos una nueva bestWord valida. 
        String palabraActual = "hola"; 
        estadisticas.updateBestWord(palabraActual); 
        String nuevaPlabraValdia = "ingeniería"; 
        estadisticas.updateBestWord(nuevaPlabraValdia); 
        assertEquals(nuevaPlabraValdia.trim(), estadisticas.getBestWord()); 
    }

    /** 
     * Test para la actualización de la mejor palabra del usuario.
     * Este test verifica que la mejor palabra se mantenga si se intenta actualizar con una palabra más corta.
     */
    @Test 
    public void testActualizaBestWordPalabraMasCorta(){
        String palabraActual = "ingeniería"; 
        estadisticas.updateBestWord(palabraActual);
        String nuevaPalabraNoValida = "hola";
        estadisticas.updateBestWord(nuevaPalabraNoValida); 
        //debemos observar que la palabra sigue siendo la primera actualizada. 
        assertEquals(palabraActual.trim(), estadisticas.getBestWord());   
    }

    /**
     * Test para la actualización de la mejor palabra del usuario.
     * Este test verifica que la mejor palabra resultante,  que son del mismo tamaño, sea la mayor en orden lexicográfico.
     */
    @Test 
    public void testActualizaBestWordMismoTamMayorLex(){
        String palabraActual = "casa"; 
        String nuevaPlabraMayorLex = "dado"; 
        estadisticas.updateBestWord(palabraActual); 
        estadisticas.updateBestWord(nuevaPlabraMayorLex); 
        assertEquals(nuevaPlabraMayorLex.trim(), estadisticas.getBestWord()); 
    }

    /**
     * Test para la actualización de la mejor palabra del usuario.
     * Este test verifica que la mejor palabra sea la misma si se intenta actualizar con la misma palabra.
     */
    @Test
    public void testActualizarBestWordMismaPlabra(){
        String palabraActual = "best"; 
        estadisticas.updateBestWord(palabraActual); 
        estadisticas.updateBestWord(palabraActual); 
        assertEquals(palabraActual.trim(), estadisticas.getBestWord());
    }

    /**
     * Test para la actualización del número de partidas jugadas.
     * Este test verifica que el número de partidas jugadas se incremente correctamente.
     */
    @Test 
    public void testActualizaNumMatchesValido(){
        estadisticas.updateNumMatches(); 
        estadisticas.updateNumMatches(); 
        estadisticas.updateNumMatches(); 
        assertEquals(3, estadisticas.getNumMatches()); 
    }

    /**
     * Test para la actualización del número total de palabras jugadas.
     * Este test verifica que la suma de las palabras jugadas se realice correctamente.
     * Se espera que el total de palabras jugadas sea la suma de las palabras jugadas en cada partida.
     */
    @Test
    public void testActualizaTotalWordsValido(){
        int valor1 = 100; 
        estadisticas.updateTotalWords(valor1); 
        int valor2 = 200; 
        estadisticas.updateTotalWords(valor2); 
        assertEquals(300, estadisticas.getTotalWords()); 
    }

    /**
     * Test para la actualización del número total de palabras jugadas con un valor negativo.
     * Este test verifica que se lanza una excepción IllegalArgumentException al intentar actualizar con un valor negativo.
     * Se espera que se lance una excepción al intentar actualizar con un valor negativo.
     * 
     * @throws IllegalArgumentException Si el número de palabras es negativo.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testActualizaTotalWordsInvalido(){
        //no es posible tener un valor negativo de palabras jugadas en scrabble. 
        int valorInvalido = -1000; 
        estadisticas.updateTotalWords(valorInvalido); 
    }

    /** 
     * Test para la actualización de la puntuación máxima del jugador inicial.
     * Este test verifica que la puntuación máxima del jugador, en la primera actualziación que se realiza, sea correcta. 
     */
    @Test
    public void testActualizarMaxPointsIni(){
        int puntActual = 500; 
        estadisticas.updateMaxPoints(puntActual); 
        assertEquals(puntActual, estadisticas.getMaxPoints()); 
    }

    /**
     * Test para la actualización de la puntuación máxima del jugador.
     * Este test verifica que la puntuación máxima se actualice correctamente si la nueva puntuación es mayor que la anterior.
     * Se espera que la puntuación máxima sea la nueva puntuación.
     */
    @Test 
    public void testActualizarMaxPointsValido(){
        int puntPrevia = 500; 
        estadisticas.updateMaxPoints(puntPrevia); 
        int nuevaPunt = 10000; 
        estadisticas.updateMaxPoints(nuevaPunt); 
        assertEquals(nuevaPunt, estadisticas.getMaxPoints()); 
    }


    /**
     * Test para la actualización de las estadísticas del jugador.
     * Este test verifica que todas las estadísticas se actualicen correctamente al finalizar una partida.
     * Se espera que todas las estadísticas reflejen los valores correctos después de la actualización.
     */
    @Test 
    public void testActualizaEstadsiticasUsuario() { 
        int gamePoints = 1000;
        String bestWordGame = "ingeniería"; 
        boolean winner = true;
        int numWordsGame = 100; 

        //llamamos al métiodo de actualización de estadísticas:
        estadisticas.updateEstadisticasJugador(gamePoints, bestWordGame, winner, numWordsGame);
        //comprobamos que se han actualizado correctamente los valores:
        assertEquals(gamePoints, estadisticas.getTotalPoints());
        assertEquals(bestWordGame.trim(), estadisticas.getBestWord());
        assertEquals(1, estadisticas.getWins());
        assertEquals(0, estadisticas.getLoses());
        assertEquals(1.0f, estadisticas.getWinRate(), 0.01f);
        assertEquals(1, estadisticas.getNumMatches());
        assertEquals(numWordsGame, estadisticas.getTotalWords());
        assertEquals(gamePoints, estadisticas.getMaxPoints());
        assertEquals((double) gamePoints, estadisticas.getAveragePoints(), 0.01f);
    }
}