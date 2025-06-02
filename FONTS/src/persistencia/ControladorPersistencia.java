package persistencia; 


/**
 * Clase ControladorPersistencia: ç
 *<p>
 * Es la clase dónde se delegan todas las responsabilidades para almecenar todos los
 * datos necesarios para poder mantener los datos y estados del sistemas coherentes 
 * a lo largo de todas las ejecuciones. 
 * </p>
 * <p>
 * <b>Propósito:</b> Gestionar la persistencia de los usuarios en el sistema.
 * Esta clase sigue le patrón de diseño Singleton, que nos garantiza una única instancia
 * y, además, de acceso global en cualquier punto del programa. 
 * </p> 
 */

 import dominio.*;

public class ControladorPersistencia {
    /** Representa la única instanica que podemos obtener de ControladorPersistencia (Singleton). */
    private static ControladorPersistencia instancia;
    /** Representa la navegabilidad al GestorUsuarios de nuestra layer.*/ 
    private GestorUsuarios GestUsuarios; 
    private GestorGuardarPartida GestorGuardarPartida;
    
    private ControladorPersistencia() {
        instancia = null; 
    }

    /** 
     * Método para obtener la única instancia de ControladorPersistencia
     */
    public static ControladorPersistencia getInstance() {
        if(instancia == null) {
            instancia = new ControladorPersistencia(); 
        }
        return instancia; 
    }

    /**
     * Método para almacenar el user creado en el sistema en la capa de persistencia. 
     * El método invoca a al gestor de usuario y lo serializa en un JSON.
     * 
     * @param usuario objeto Usuario que representa al nuevo creado en el sistema.
     */
    public void crearUsuario(Usuario usuario) {
        GestUsuarios.guardarUsuario(usuario); 
    }

    /**
     * Método que carga el usuario desde la capa de persistencia.
     * 
     * @param usuario objeto Usuario que se desea cargar desde la capa de presentación.
     * @return usuario correctamente cargado desde la capa de persistencia. 
     */
    public Usuario cargarUsuario(Usuario usuario) {
    
       return GestUsuarios.cargarUsuario(usuario.getUsername());
    }

    /**
     * Método que elimina un usuario de la capa de persistencia.
     * 
     * @param usuario objeto Usuario que se desea eliminar de la capa de presentación.
     */
    public void eliminarUsuario(Usuario usuario) {
        GestUsuarios.eliminarUsuario(usuario); 
    }

    /** Método que nos permite verificar si existe un usuario en el sistema.
     * 
     * @param username Representa el nombre del usuario que se desea buscar. 
     * @return true en caso de que sí exista en el sistema, false en caso contrario. 
     */
    public boolean existeUser(String username) {
        return GestorUsuarios.existeUser(username); 
    }

    /**
     * Método que carga el usuario desde la capa de persistencia mediante el nombre de usuario.
     * 
     * @param username Nombre del usuario que se desea cargar.
     * @return usuario correctamente cargado desde la capa de persistencia. 
     */
    public Usuario cargarUsuario(String username) {
        return GestUsuarios.cargarUsuario(username); 
    }

    /**
     * Método que carga el usuario desde la capa de persistencia mediante el nombre de usuario.
     * 
     * @param username Nombre del usuario que se desea cargar.
     * @return usuario correctamente cargado desde la capa de persistencia. 
     */
    public void salvarPartida(ControladorPartida controladorPartida) {
        this.GestorGuardarPartida = new GestorGuardarPartida(controladorPartida);
        GestorGuardarPartida.salvarPartida(controladorPartida);
    }

    /**
     * Método que carga el estado de la partida desde un archivo utilizando el método cargarPartida()
     * de ControladorPartida.
     *
     * @param archivo             nombre o ruta del archivo desde el cual cargar la partida.
     * @param controladorPartida  instancia de ControladorPartida donde se cargará el estado.
     */
    public void cargarPartida(String archivo, ControladorPartida controladorPartida) {
        this.GestorGuardarPartida = new GestorGuardarPartida(controladorPartida);
        GestorGuardarPartida.cargarPartida(archivo, controladorPartida);
    }
}