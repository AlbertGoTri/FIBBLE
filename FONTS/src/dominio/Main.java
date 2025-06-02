package dominio;

import dominio.*;
import persistencia.*; 
import presentacion.ControladorPresentacion;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal del programa.
 * Inicia la aplicación mostrando la vista inicial a través del controlador de presentación.
 */
public class Main {
    public static void main(String[] args) {
        ControladorPresentacion controladorPresentacion = new ControladorPresentacion();
        controladorPresentacion.iniciarAplicacion();
    }
}
