package persistencia;

import dominio.ControladorPartida;

public class GestorGuardarPartida {

    /**
     * Constructor de GestorGuardarPartida.
     *
     */
    public GestorGuardarPartida(ControladorPartida controladorPartida) {
    }

    /**
     * Guarda el estado actual de la partida utilizando el método salvarPartida()
     * de ControladorPartida.
     *
     * @param controladorPartida instancia de ControladorPartida a guardar.
     */
    public void salvarPartida(ControladorPartida controladorPartida) {
        controladorPartida.salvarPartida();
    }
    
    /**
     * Carga el estado de la partida desde un archivo utilizando el método cargarPartida()
     * de ControladorPartida.
     *
     * @param archivo             nombre o ruta del archivo desde el cual cargar la partida.
     * @param controladorPartida  instancia de ControladorPartida donde se cargará el estado.
     */
    public void cargarPartida(String archivo, ControladorPartida controladorPartida) {
        controladorPartida.cargarPartida(archivo);
    }
}