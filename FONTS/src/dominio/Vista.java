package dominio;
import java.util.Scanner;

/**
 * Clase Vista
 * Esta clase se encarga de interacción con el usuario a través de la consola.
 * Proporciona métodos para imprimir mensajes, leer entradas de usuario y pausar la ejecución.
 * 
 * El único parámetro que contiene la clase es: 
 * - `scanner`: un objeto Scanner que se utiliza para leer la entrada del usuario desde la consola.
 */
public class Vista {
    private Scanner scanner; 

    /**
     * Constructor de la clase Vista
     * Inicializa el objeto Scanner para leer la entrada del usuario.
     */
    public Vista(){
        scanner = new Scanner(System.in); 
    }

    /**
     * Método para imprimir un mensaje en la consola usando el comando printl().
     * 
     * @param mensaje El mensaje a imprimir.
     */
    public void printlnMensaje(String mensaje) {
        System.out.println(mensaje); 
    }

    /**
     * Método para imprimir un mensaje en la consola usando el comando print().
     * 
     * @param mensaje El mensaje a imprimir.
     */
    public void printMensaje(String mensaje) {
        System.out.print(mensaje); 
    }

    /**
     * Método para leer un número entero desde la consola.
     * 
     * @return El número entero leído.
     * @throws java.util.InputMismatchException Si la entrada no es un número entero.
     */
    public int leerInt() {
        while (true) {
            try {
                return scanner.nextInt(); // Leer un entero
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, introduce un número entero.");
                scanner.nextLine(); // Limpiar la entrada no válida
            }
        }
    }

    /**
     * Método para leer un número de punto flotante desde la consola.
     * 
     * @return El número de punto flotante leído.
     */
    public char leerChar() {
        char opcion = scanner.next().charAt(0);
        return opcion; 
    }

    /**
     * Método para leer un String desde la consola. 
     * 
     * @return El String leído.
     */

    public String leerString() {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // Limpia el buffer si hay un salto de línea pendiente
        }
        return scanner.nextLine(); // Ahora lee la entrada del usuario correctamente
    }

    /**
     * Método para limpiar el buffer de entrada.
     * 
     * @return Un String vacío.
     */
    public String limpiarBuffer() {
        return scanner.nextLine();
    }

    /**
     * Método para pausar la ejecución del programa durante un tiempo específico.
     * 
     * @param tiempoEspera El tiempo en milisegundos para pausar la ejecución.
     * @throws InterruptedException Si la ejecución es interrumpida.
     */
    public void pausarEjecucion(int tiempoEspera) {
        try {
            Thread.sleep(tiempoEspera); 
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
 }
