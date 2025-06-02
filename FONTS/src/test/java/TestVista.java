/* para poder realizar este test vamos a neceistar redireccionar entradas y salidas
 * para poder verificar que los métodos implementados en la clase vista son correctos
 * de manera que haremos que uso de algunos imports java.io
 */

import org.junit.After; 
import org.junit.Before;
import org.junit.Test; 
import static org.junit.Assert.*;
import dominio.Vista;

import java.io.ByteArrayInputStream; //sirve para simular la entrada std (System.in) en nuestros tests.
import java.io.ByteArrayOutputStream; //sirve para capturar la salida estándar (System.out) en los tests.
import java.io.PrintStream; //Redirige la salida std a un flujo personalizado.

/**
 * Clase TestVista: 
 * Esta clase contiene los tests unitarios para la clase Vista para verificar su correcto funcionamiento de los métodos de dicha clase.
 * Se utilizan técnicas de redirección de entrada y salida estándar para simular la interacción con el usuario.
 * 
 * Los artributos principales que contiene la clase son:
 * - `outContenido`: un ByteArrayOutputStream que captura la salida estándar.
 * - `outOriginal`: un PrintStream que almacena la salida estándar original para restaurarla después de los tests.
 */

public class TestVista {
    //de esta manera podemos capturar la salida estándar.
    private final ByteArrayOutputStream outContenido = new ByteArrayOutputStream(); 
    //de esta manera podemos redirigir la salida estándar a un flujo personalizado.
    private final PrintStream outOriginal = System.out;

    /**
     * Configura la redirección de la salida estándar antes de cada test.
     */
    @Before
    public void setUpStreams() {
        //de esta manera podemos redirigir la salida std. 
        System.setOut(new PrintStream(outContenido)); 
    }

    /** 
     * Restaura la salida estándar después de cada test.
     */
    @After
    public void restaurarStreams() {
        //de esta manera podemos restaruar la salida std. 
        System.setOut(outOriginal); 
    }

    /**
     * Test para el método printlnMensaje() de la clase Vista.
     * Este test verifica que el mensaje se imprima correctamente en la consola incluyendo un único salto de línea.
     */
    @Test
    public void testPrintlnMensaje() {
        Vista vista = new Vista();
        String mensaje = "Me gusta programar"; 
        //enviamos el mensaje que quermos printear a través de la vista. 
        vista.printlnMensaje(mensaje); 
        //debemos verificar que realmente printeamos lo que quermeos:

        /*Hacemos uso del System.lineSeparator por la simple razón de que
         * nos retorna el carácter de salto de línea específica del SO que usamos.*/
        assertEquals(mensaje + System.lineSeparator(), outContenido.toString()); 
    }

    /**
     * Test para el método printMensaje() de la clase Vista.
     * Este test verifica que el mensaje se imprima correctamente en la consola sin salto de línea.
     */
    @Test 
    public void testPrintMensaje() {
        Vista vista = new Vista(); 
        String mensaje = "Me gusta programar"; 
        //enviamos el mensaje a la clase vista para que haga el print. 
        vista.printMensaje(mensaje); 
        //verificamos, en este caso no hace falta usar el separator. 
        assertEquals(mensaje, outContenido.toString()); 
    }

    /**
     * Test para el método leerInt() de la clase Vista.
     * Este test verifica que se pueda leer un número entero correctamente desde la entrada estándar.
     */
    @Test
    public void testLeerInt() {
        String inputContenido = "42\n"; 
        //simulamos la entrada std leyendo el inputContenido. 
        System.setIn(new ByteArrayInputStream(inputContenido.getBytes()));
        //importante tener la inicialización  de vista en esta línea, sino se queda colgado el test. 
        Vista vista = new Vista(); 
        int res = vista.leerInt(); 
        assertEquals(42, res); 
    }

    /**
     * Test para el método leerDouble() de la clase Vista.
     * Este test verifica que se pueda leer un número de punto flotante correctamente desde la entrada estándar.
     */ 
    @Test
    public void testLeerChar() {
        String inputContenido = "d\n"; 
        //simulamos la entrada std leyendo el input que quermeos. 
        System.setIn(new ByteArrayInputStream(inputContenido.getBytes())); 
        Vista vista = new Vista(); 
        char res = vista.leerChar(); 
        assertEquals('d', res); 
    }

    /*@Test
    public void testLeerString() {
        String inputContenido = "me gusta programar\n"; 
        //simulamos la entrada std leyendo el string que queremos. 
        System.setIn(new ByteArrayInputStream(inputContenido.getBytes()));
        Vista vista = new Vista(); 
        String res = vista.leerString(); 
        //verificamos que el valor es el esperado. 
        assertEquals("me gusta programar", res); 
    }*/

    /**
     * Test para el método pausarEjecucion() de la clase Vista.
     * Este test verifica que la ejecución se pause durante un tiempo específico.
     */
    @Test 
    public void testPausarEjecucion() {
        Vista vista = new Vista(); 
        int tiempoEspera = 500; 
        /*necesitamos capturar el tiempo de espera en dos puntos de la ejecución*/
        long tiempoIni = System.currentTimeMillis(); //esta func. nos permite eso. 
        vista.pausarEjecucion(tiempoEspera); 
        long tiempoFin = System.currentTimeMillis(); 
        assertTrue((tiempoFin - tiempoIni) >= tiempoEspera); 
    }
}
