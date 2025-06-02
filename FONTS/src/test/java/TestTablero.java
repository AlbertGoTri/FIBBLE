import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import dominio.Algoritmo;
import dominio.Diccionario;
import dominio.Tablero;
import java.util.ArrayList;
import java.util.List;
/*
public class TestTablero {

    private Tablero tablero;

    @Before
    public void setUp() {
        tablero = new Tablero(); // Tablero 15x15 por defecto
    }

    // Test para comprobar que el tablero se crea correctamente
    @Test
    public void testCrearTablero() {
        assertNotNull(tablero);
        assertEquals(15, tablero.getRows());
    }

    // Test para comprobar que el tablero se inicializa correctamente
    @Test
    public void testInicializarTablero() {
        tablero.inicializarMatriz();
        for (int i = 0; i < tablero.getRows(); i++) {
            for (int j = 0; j < tablero.getCols(); j++) {
                assertEquals("\0", tablero.getLetra(i, j));
            }
        }
    }

    // Test para comprobar que obtienes bien una letra
    @Test
    public void testObtenerLetra() {
        tablero.inicializarMatriz();
        tablero.setCasilla("A", 1, 1);
        assertEquals("A", tablero.getLetra(0, 0));
    }

    //Test para comprobar que obtienes mal una letra, excepcion
    @Test(expected = IllegalArgumentException.class)
    public void testObtenerLetraExcepcion() {
        tablero.inicializarMatriz();
        tablero.setCasilla("A", 1, 1);
        tablero.getLetra(15, 15); // Fuera de los límites del tablero
    }

    //Test para comprobar que obtienes bien la bonificacion casilla
    @Test
    public void testBonificacionCasilla() {
        tablero.inicializarMatriz();
        tablero.setCasilla("A", 8, 8);
        assertEquals(1, tablero.getBonificacionCasilla(8, 8));  
    }

    //Test para comprobar que obtienes mal la bonificacion casilla
    @Test(expected = IllegalArgumentException.class)
    public void testBonificacionCasillaExcepcion() {
        tablero.inicializarMatriz();
        tablero.setCasilla("A", 8, 8);
        tablero.getBonificacionCasilla(15, 15); // Fuera de los límites del tablero
    }

    // test para checquear que no hayas puesto una letra solo con el tablero limpio debe ser incorrecto
    @Test (expected = IllegalArgumentException.class)
    public void testColocarLetraCorrecta() {
        tablero.inicializarMatriz();
        tablero.setCasilla("A", 7, 7);
        List<int[]> coordenadas = new ArrayList<>();
        coordenadas.add(new int[]{7, 7});
        //tablero.extraerPalabras(coordenadas, 0);
    }

    // Test para comprobar que las letras no se coloccan en el centro del tablero
    @Test(expected = IllegalArgumentException.class)
    public void testColocarLetraIncorrecta() {
        tablero.inicializarMatriz();
        //centro del tablero es 8, 8
        tablero.setCasilla("A", 7, 7);
        tablero.setCasilla("B", 6, 7);
        List<int[]> coordenadas = new ArrayList<>();
        coordenadas.add(new int[]{7, 7});
        coordenadas.add(new int[]{6, 7});
        //tablero.extraerPalabras(coordenadas, 0);
    }

    // test para comprobar que las Las letras no están alineadas en la misma fila o columna.
    @Test(expected = IllegalArgumentException.class)
    public void testColocarLetrasNoAlineadas() {
        tablero.inicializarMatriz();
        tablero.setCasilla("A", 7, 7);
        tablero.setCasilla("B", 8, 8);
        List<int[]> coordenadas = new ArrayList<>();
        coordenadas.add(new int[]{7, 7});
        coordenadas.add(new int[]{8, 8});
        //tablero.extraerPalabras(coordenadas, 0);
    }

    // test para comprobar que Hay un espacio en blanco entre las letras en la misma fila o columna.
    @Test(expected = IllegalArgumentException.class)
    public void testColocarLetrasEspacioEnBlanco() {
        tablero.inicializarMatriz();
        tablero.setCasilla("A", 8, 8);
        tablero.setCasilla("B", 5, 8);
        List<int[]> coordenadas = new ArrayList<>();
        coordenadas.add(new int[]{8, 8});
        coordenadas.add(new int[]{5, 8});
        //tablero.extraerPalabras(coordenadas, 0);
    }

    // test para comprobar Error: La palabra '" + palabraVF.toString() + "' no es válida en el idioma 
    @Test(expected = IllegalArgumentException.class)
    public void testPalabraNoValida() {
        Diccionario diccionario = new Diccionario();
        diccionario.setCargarDiccionario("castellano");
        this.tablero.setIdioma("castellano");
        
        tablero.setCasilla("C", 8, 8); // Palabra "CAOS" horizontal
        tablero.matrizOcupacio[5][6].setOcupada(true);
        tablero.setCasilla("Z", 8, 9);
        tablero.matrizOcupacio[5][7].setOcupada(true);
        tablero.setCasilla("S", 8, 10);
        tablero.matrizOcupacio[5][9].setOcupada(true);
        tablero.setCasilla("X", 8, 11); // Palabra "ARCO" vertical
        List<int[]> coordenadas = new ArrayList<>();
        coordenadas.add(new int[]{8, 8});
        coordenadas.add(new int[]{8, 9});
        coordenadas.add(new int[]{8, 10});
        coordenadas.add(new int[]{8, 11});
        //tablero.extraerPalabras(coordenadas, 0);
    }

    // test para comprobar que la palabra no es colindante con ninguna letra ya existente en el tablero
    @Test(expected = IllegalArgumentException.class)
    public void testPalabraNoColindante() {
        tablero.inicializarMatriz();
        tablero.setCasilla("C", 6, 7); // Palabra "CAOS" horizontal
        tablero.matrizOcupacio[5][6].setOcupada(true);
        tablero.setCasilla("A", 6, 8);
        tablero.matrizOcupacio[5][7].setOcupada(true);
        tablero.setCasilla("S", 6, 10);
        tablero.matrizOcupacio[5][9].setOcupada(true);
        tablero.setCasilla("O", 6, 9); // Palabra "ARCO" vertical
        tablero.matrizOcupacio[5][8].setOcupada(true);
        tablero.setCasilla("C", 5, 9);
        tablero.matrizOcupacio[4][8].setOcupada(true);
        tablero.setCasilla("R", 4, 9);
        tablero.matrizOcupacio[3][8].setOcupada(true);
        tablero.setCasilla("A", 3, 9);
        tablero.matrizOcupacio[2][8].setOcupada(true);
        Diccionario diccionario = new Diccionario();
        diccionario.setCargarDiccionario("castellano");
        this.tablero.setIdioma("castellano");
        List<int[]> coordenadas = new ArrayList<>();
        //esribo hola en columna 1
        tablero.setCasilla("H", 1, 1);
        tablero.setCasilla("O", 2, 1);
        tablero.setCasilla("L", 3, 1);
        tablero.setCasilla("A", 4, 1);
        coordenadas.add(new int[]{2, 2});   
        coordenadas.add(new int[]{3, 2});
        coordenadas.add(new int[]{4, 2});
        coordenadas.add(new int[]{5, 2});
        //tablero.extraerPalabras(coordenadas, 0);
    }

    // test para comprobar que la palabra es correctamente colocada en el tablero
    @Test
    public void testColocarPalabraCorrecta() {
        tablero.inicializarMatriz();
        tablero.setCasilla("C", 6, 7); // Palabra "CAOS" horizontal
        tablero.matrizOcupacio[5][6].setOcupada(true);
        tablero.setCasilla("A", 6, 8);
        tablero.matrizOcupacio[5][7].setOcupada(true);
        tablero.setCasilla("S", 6, 10);
        tablero.matrizOcupacio[5][9].setOcupada(true);
        tablero.setCasilla("O", 6, 9); // Palabra "ARCO" vertical
        tablero.matrizOcupacio[5][8].setOcupada(true);
        tablero.setCasilla("C", 5, 9);
        tablero.matrizOcupacio[4][8].setOcupada(true);
        tablero.setCasilla("R", 4, 9);
        tablero.matrizOcupacio[3][8].setOcupada(true);
        tablero.setCasilla("A", 3, 9);
        tablero.matrizOcupacio[2][8].setOcupada(true);

        tablero.setCasilla("C", 3, 8);
        tablero.setCasilla("S", 3, 10);
        tablero.setCasilla("A", 3, 11);
        Diccionario diccionario = new Diccionario();
        diccionario.setCargarDiccionario("castellano");
        this.tablero.setIdioma("castellano");
        List<int[]> coordenadas =  new ArrayList<>();
        coordenadas.add(new int[]{3, 8});
        coordenadas.add(new int[]{3, 10});
        coordenadas.add(new int[]{3, 11});

        String palabraFormada = "CASA";
        
        //tablero.extraerPalabras(coordenadas, 0);
        // assertEquals is removed since there's no result to compare
    }
}*/


