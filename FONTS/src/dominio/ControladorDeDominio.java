package dominio;
import persistencia.*;
import dominio.ControladorPartida;
import dominio.ControladorUsuario;
import java.util.ArrayList; 
import java.util.List;
import java.util.Vector;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;

/**
 * Clase ControladorDeDominio
 * <p>
 * Actúa como intermediario entre la capa de presentación y la capa de dominio.
 * Gestiona las operaciones principales del sistema, como la creación de perfiles,
 * edición de perfiles, visualización de estadísticas, ranking, y la comunicación con
 * la capa de persistencia. 
 * </p>
 * <p>
 * <b>Patrón Singleton:</b> Esta clase implementa el patrón Singleton, de esta froma
 * podemos garantizar que solo haya una instancia de ControladorDeDominio activa en el sistema. 
 * </p> 
 */
public class ControladorDeDominio {
    /** Instancia del ControladorDeDominio (única Singleton). */
    private static ControladorDeDominio instancia;

    /** Controlador de Partida encargado de getionar partidas. */
    private ControladorPartida controladorPartida;

    /** Controlador de usuario encargado de gestionar los usuarios. */
    private ControladorUsuario CU;

    /**  Nombre de usuario del user actual que crea el perfil en el sistema. */
    private String currentUsuario;

    /** Instancia de Vista usada para interacutar por consola de forma transparente al user. */
    private Vista vista; 

    /**  Nos indica si es la primera vez que se crea un usuario. */
    private boolean primeraCreacion; 

    /** Controlador encargado de la comunicación con la capa de persitencia en el sistema. */
    private ControladorPersistencia CPers; 


    /** 
     * Contructor privado de la clase ControladorDeDominio.
     * <p>
     * Inicializa los controladores, la vist a y las variables necesarias. 
     * Este constructor no debe ser llamdo de forma directa, ya que la clase implementa
     * el patrón Singleton.
     * </p>
     */
    private ControladorDeDominio() {
        
        instancia = null; 
        CU = new ControladorUsuario();
        controladorPartida = null;
        currentUsuario = null;    
        vista = new Vista(); 
        primeraCreacion = true;
        
        CPers = CPers.getInstance();  
        cargarUsuariosDesdePersistencia(); 
    }


    /**
     * Método estático que devuelve la instancia única de ControladorDeDominio.
     * <p>
     * Si la instancia no ha sido creada, se crea una nueva instancia. 
     * </p>
     * @return La instancia única de ControladorDeDominio.
     */
    public static ControladorDeDominio getInstance() {
        if(instancia == null) {
            instancia = new ControladorDeDominio(); 
        }
        return instancia; 
    }
    
    /**
     * Limpia la pantalla mostrando una serie de líneas en blanco y un separador.
     */
    public void limpiarPantalla() {
        vista.printlnMensaje("=====================================================\n");
        for(int i = 0; i < 2; ++i) {
            vista.printlnMensaje("\n"); 
        }
    }

    /**
     * Método para crear un perfil de usuario en el sistema.
     * <p>
     * Este método se encarga de crear un nuevo usuario en el sistema, 
     * asegurándose de que no se cree más de un perfil y lo almacena en la capa de persistencia.
     * </p>
     * 
     * @param username Nombre de usuario del nuevo perfil.
     * @param password Contraseña del nuevo perfil.
     */
    public void crearPerfil(String username, String password) {
        if(CU == null) {
            System.out.println("CU == null");
            return; 
        }
        if(primeraCreacion) {
            try{
                CU.creaUsuario(username, password);
                
                Usuario user = CU.getUsuario(username);
                controladorPartida = new ControladorPartida(user);
                controladorPartida.setHumano(user);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
                CPers.crearUsuario(user);  
                currentUsuario = username;
                primeraCreacion = false;
            } catch(IllegalArgumentException e) {
                System.out.println("Error CD procede de ==> " + e.getLocalizedMessage() + "\n");
                throw e;
            }
        }
        else{
            vista.printlnMensaje("Error CD al crear perfil: Ya tienes un usuario creado!\n");
        }
    }

    /**
     * Método que resetea las estadísticas del usuario actual.
     */
    public void reiniciarEstadisticas() {
        CU.eliminaEstadisticasUser(currentUsuario);
        CPers.eliminarUsuario(CU.getUsuario(currentUsuario));
        CPers.crearUsuario(CU.getUsuario(currentUsuario));
    }

    /**
     * Método para editar el perfil de usuario en el sistema.
     * <p>
     * Permite cambiar el nombre de usuario y/o la contraseña del perfil actual. Si el usuario no existe,
     * se muestra un mensaje de error.
     * </p>
     * 
     * @param viewUsername Nombre de usuario nuevo.
     * @param viewPassword Contraseña nueva.
     * @throws IllegalArgumentException Si el usuario no existe o no se puede editar.
     * @throws NullPointerException Si el controlador de usuario es null.
     */
   public void editarPerfil(String viewUsername, String viewPassword) {

        boolean fin = false;
        boolean first = (CU.getUsuario(currentUsuario) == null); 
 
        boolean puedeEditar; 
        
            if(first) {
                first = false;
                puedeEditar = false; 
                fin = true;
            }
            else {

                puedeEditar = true;
            }
                try { 
                    if(puedeEditar) {
                        Usuario prev_user = CU.getUsuario(currentUsuario); 
                        String prev_username = prev_user.getUsername();

                        String newUsername = CU.editarPerfilUsuario(currentUsuario, viewUsername, viewPassword);

                        if(!prev_username.equals(newUsername)) {
                            Usuario userAEliminar = new Usuario(prev_username, "1234"); 
                            CPers.eliminarUsuario(userAEliminar);
                        }
        
                        currentUsuario = newUsername;

                        Usuario user = CU.getUsuario(currentUsuario);
                        CPers.crearUsuario(user);  
                       
                    }
                    else {
                        vista.pausarEjecucion(1500); 
                    
                    }
                }
                catch(IllegalArgumentException e) {
                    vista.printlnMensaje("\n" + "Error CD al editar perifil procede de ==>" + e.getMessage() + "\n");
                    throw e; 
                }
                vista.pausarEjecucion(1500);
    }

    /** 
     * Método para ver las estadísticas del usuario actual.
     * <p>
     * Muestra las estadísticas del usuario actual en el sistema. Si el usuario no existe,
     * se muestra un mensaje de error.
     * </p>
     * 
     * @param username Nombre de usuario del perfil actual.
     */
    public void verEstadisticas(String username) {
        try {
            CU.showEstadisticasUsuario(username);
        }
        catch(IllegalArgumentException e) {
            vista.printlnMensaje("Error de CD al ver Estadísticas procede de ==> " + e.getMessage() + "\n");
            vista.pausarEjecucion(1500);
        }
    }


    public void cargarUsuario() {

    }

    /**
     * Método para ver el ranking de usuarios en el sistema.
     * <p>
     * Muestra el ranking de usuarios en el sistema. Si no hay usuarios, se muestra un mensaje de error.
     * </p>
     */
    public void verRanking() {
        try {
            CU.showRanking();
        }
        catch(IllegalArgumentException e) {
            vista.printlnMensaje("Error CD al ver Ranking, procede de ==> " + e.getMessage() + "\n");
        }
    }

    /**
     * Método obtener el controlador de partida.
     * 
     * @return La instancia del controlador de partida.
     */
   public ControladorPartida getControladorPartida()  {
        return controladorPartida;
   }


   /**
    * Método para obtener la lista de fichas del usuario actual que dispone en su atril.
    *
    * @return Lista de fichas del usuario actual.
    */
   public List<String> getFichasCurrentUser() {
    if(currentUsuario != null) {
       return CU.getFichasCurrentUser(currentUsuario); 
    }
    else {
        return null; 
    }
   }

   /**
    * Método que establece al usuario como jugador humano en la partida.
    *
    * @param user El usuario que se va a establecer como jugador humano.
    */
   public void setHumano(Usuario user) {
        controladorPartida.setHumano(user);
   }


   /**
    * Método para obtener el usuario actual.
    * 
    * @return El usuario actual.
    */
   public Usuario getCurrentUsuario() {
        if(currentUsuario != null) {
            return CU.getUsuario(currentUsuario); 
        }
        else {
            return null; 
        }
   }

   /** Método que obtiene las estadística del usuario actual. 
    * 
    *@return El objeto Estadística en caso de que el user exista, null en caso contrario.
   */
   public Estadistica getEstadisticasCurrentUser() {
    if(currentUsuario != null) {
        Estadistica estadisticas = CU.getEstadisticasCurrentUser(currentUsuario);
        if(estadisticas == null) {
        }
        return CU.getEstadisticasCurrentUser(currentUsuario); 
    }
    else {
        return null;
    }
   }

   /** Método que se encarga de realizar el procso de login
    * 
    @param username Representa en nombre de usuario
    @param password Representa la contraseña de acceso del usuario
    */
    public void logInUser(String username, String password) {
        if(CPers.existeUser(username)) {
            Usuario user = CU.getUsuario(username);
            if(user == null) {
                return; 
            }
            Usuario userCargado  = CPers.cargarUsuario(CU.getUsuario(username)); 
            if(userCargado.getPassword().equals(password)) { 
                currentUsuario = userCargado.getUsername(); 
                controladorPartida = new ControladorPartida(userCargado);
                controladorPartida.setHumano(userCargado);
            }
            else {
                throw new IllegalArgumentException("La contraseña no corresponde con la del user."); 
            }
        }
        else {
            throw new IllegalArgumentException("El usuario no está registrado en el sistema."); 
        } 
    }

    /**
     * Método para cargar los usuarios desde la capa de persistencia.
     */
    public void cargarUsuariosDesdePersistencia() {
        List<Usuario> usuarios_cargados = new ArrayList<>();

        File carpetaUsuarios = new File("data_persistencia/usuarios/");
        
        if(carpetaUsuarios.exists() && carpetaUsuarios.isDirectory()) {
            File[] archivosUsuarios = carpetaUsuarios.listFiles();

           if(archivosUsuarios != null) {
            for(File archivo : archivosUsuarios) {
                if(archivo.isFile()) {
                    String username = archivo.getName();
                    Usuario usuarioCargado = CPers.cargarUsuario(username);
                    if(usuarioCargado != null) {
                        usuarios_cargados.add(usuarioCargado);
                    }
                }
            }
           }
           else {
               System.out.println("\n");
           }
        }
        else {
            System.out.println("\n");
        }
        CU.setUsuarios(usuarios_cargados);
    }

    /**
     * Método para guardar la partida actual.
     * <p>
     * Este método se encarga de guardar la partida actual en la capa de persistencia.
     * </p>
     */
    public void salvarPartida() {
        if(controladorPartida != null) {
            CPers.salvarPartida(controladorPartida);
        }
    }
    
    /**
     * Método para cargar una partida desde un archivo.
     * <p>
     * Este método se encarga de cargar una partida desde un archivo en la capa de persistencia.
     * </p>
     * 
     * @param nombreArchivo Nombre del archivo desde el cual se cargará la partida.
     */
    public void cargarPartida(String nombreArchivo) {
        if(controladorPartida != null) {
            CPers.cargarPartida(nombreArchivo, controladorPartida);
        }
    }

    /**
     * Método para obtener la lista de usuarios humanos ordenados por su ranking.
     * 
     * @return Una lista de usuarios ordenados por su ranking.
     */
    public List<String> getRanking() {
        Vector<Usuario> u_vec = CU.getSortedVectorRanking();
        List<String> nombres = new ArrayList<>();
        if (u_vec == null || u_vec.isEmpty()) {
            return nombres;
        }
        for (Usuario u : u_vec) {
            nombres.add(u.getUsername());
        }
        return nombres;
    }
}
