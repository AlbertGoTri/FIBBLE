package dominio;

import java.util.Vector;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/** 
 * Clase ControladorUsuario
 * <p>
 * <b>Propósito:</b>Esta clase se encarga de gestionar todas las operaciones relaciondas con los usuarios del sistema. 
 * Permite crear, editar, eliminar usuarios, así como gestionar estadísticas, reglamento y ranking. 
 * </p>
*/

public class ControladorUsuario {
    /** Lista de usuarios registrados en el sistema. */
    private List<Usuario> usuarios;
    /** Instancia del reglamento del juego. */
    private static Reglamento reglamento; 
    /** Instancia del ranking activo en el sistema. */
    private static Ranking ranking; 
    /** Instancia de la vista para mostrar mensajes por terminal. */
    private static Vista vista;


    /**
     * Contructor de la clase ControladorUsuario.
     * Inicializa la lista de usuarios y las instancias de reglamento. 
     */
    public ControladorUsuario() {
        this.usuarios = new ArrayList<>();
        reglamento = null;
        ranking = null; 
        vista = new Vista(); 
    }

    /**
     * Método que obtiene la instancia del ControladorUsuario. 
     * 
     * @retrun La instancia actual del ControladorUsuario.
     */
    public ControladorUsuario geControladorUsuario() {
        return this;
    }

    /**
     * Método que busca y retorna el usuario que le pasamos el username como parámetro de entrada.
     * 
     * @param username El nombnre de usuario. 
     * @return el objeto Usuario si existe, null en caso contrario. 
     */
    public Usuario getUsuario(String username) {
        if(!usuarios.isEmpty()) {
            for (Usuario u : usuarios) {
                if (u.getUsername().equals(username.trim())) return u;
            }
        }
        //vista.printlnMensaje("Usuario no encontrado, retorno null\n");
        return null;
    }

    /**
     * Método que obtiene la lista de los usuarios registrados de forma humana en el sistema. 
     * 
     * @return la lista de obejtos Usuario que contiene a los users registrados, null en caso contrario.
     */
    public List<Usuario> getUsuarios() {
        if(!usuarios.isEmpty()) return usuarios;
        else {
            return null;
        }
    }


    /**
     * Método que crea el usuario nuevo en el sistema y lo añade a la lista de usuarios. 
     * 
     * @param username El nombre de usuario. 
     * @param password La contraseña del usuario. 
     * @throws IllegalArgumentException Si el usuario ya existe.
     */
    public void creaUsuario(String username, String password) {
        Usuario user = getUsuario(username);
        //vista.printlnMensaje(username.length() + " " + password.length() + "\n");
        if(user == null) {
                user = new Usuario(username, password);
                usuarios.add(user); //añadimos el usuario a la lista. 
        }
        else {
            throw new IllegalArgumentException("Error CU: El usuario ya existe en el sistema, debe introducir otro nombre\n");
        }
    }

    /**
     * Método que elimina al usuairo de la lista de usuarios.
     * 
     * @param username El nombre de usuario que se desea eliminar. 
     */
    public void eliminarUsuario(String username) {
        Usuario user = getUsuario(username);
        if(user != null) usuarios.remove(user);
    }

    ////// 
    /// 
    /// 
    /// NO HACE FALTA SHOW ESTATS
    /// 
    /// //////
    public void showEstadisticasUsuario(String username) {
        Usuario user = getUsuario(username);
        if(user != null) {
            //System.out.println("OBTENIENDO ESTADŚTICIAS...\n");
            Estadistica stats = user.getStats();
            //System.out.println("ESTADŚITICAS CARGADAS CORRECTAMENTE\n");
            vista.printlnMensaje("=====================================================\n");
            vista.printlnMensaje("                     ESTADISTÍCAS                    \n");  
            vista.printlnMensaje("=====================================================\n"); 
            vista.printlnMensaje("==> USUARIO: " + username + "\n"); 
            vista.printlnMensaje("==> PUNTOS TOTALES: " + stats.getTotalPoints() + "\n");
            vista.printlnMensaje("==> PUNTOS MEDIOS: " + stats.getAveragePoints() + "\n");
            vista.printlnMensaje("==> MÁXIMA PUNTUACIÓN: " + stats.getMaxPoints() + "\n");
            vista.printlnMensaje("==> NÚMERO DE PARTIDAS JUGADAS: " + stats.getNumMatches() + "\n");
            vista.printlnMensaje("==> NÚMERO DE WINS: " + stats.getWins() + "\n");
            vista.printlnMensaje("==> NÚMERO DE LOSES: " + stats.getLoses() + "\n");
            vista.printlnMensaje("==> WINRATE: " + stats.getWinRate() + "\n");
            vista.printlnMensaje("==> NÚMERO DE PALABRAS: " + stats.getTotalWords() + "\n");
            vista.printlnMensaje("==> MEJOR PALABRA JUGADA: " + stats.getBestWord() + "\n");
        }
        else {
            throw new IllegalArgumentException("Error de CU: No estás registrado como usuario\n"); 
        }
    }

    /** Método que verifica si un nuevo nombre de usuario ya existe en el sistema. 
     * 
     * @param newName El nuevo nombre de usuario a veificar. 
     * @return true en el caso de que el nombre no existe en el sistema, false en caso contrario. 
     */
    public boolean newUsernameVerification(String newName) {
        if(!usuarios.isEmpty()) {
            //miramos que el usuario no exista ==> No está el nombre en el sistema.
            Usuario user = getUsuario(newName.trim());
            return (user == null);
        }
        else {
            return false;
        }
    }


    /**
     * Método que verifica si la nueva contraseña es diferente a la actual.
     * 
     * @param newPassword La nueva contraseña a verificar.
     * @param currentPassword La contraseña actual del usuario.
     * @return true si la nueva contraseña es diferente a la actual, false en caso contrario.
     */
    public boolean newPasswordVerification(String newPassword, String currentPassword) {
        if(currentPassword.equals(newPassword)) return false; 
        else return true;
    }

    
    /**
     * Método que edita el perfil del usuario. 
     * 
     * @param actualUsername El nombre de usuario actual.
     * @param viewUsername El nuevo nombre de usuario a establecer (recibido desde la capa de presentación).
     * @param viewPassword La nueva contraseña a establecer (recibido desde la capa de presentación).
     * @return El nuevo nombre de usuario si se ha cambiado, el nombre actual en caso contrario.
     * @throws IllegalArgumentException Si el nuevo nombre de usuario ya existe en el sistema.
     * @throws IllegalArgumentException Si la nueva contraseña es igual a la actual.
     * @throws IllegalArgumentException Si la opción introducida no es válida.
     */
    public String editarPerfilUsuario(String actualUsername, String viewUsername, String viewPassword) { 
        String nombre_res = actualUsername; 
        Usuario user = getUsuario(actualUsername);
        if(user != null) {
            if(viewUsername != null) { 
                   if(!newUsernameVerification(viewUsername)) {
                       nombre_res = actualUsername;  
                       throw new IllegalArgumentException("Error de CU: el nombre de usuario ya existe\n"); 
                   }
                   else {
                       user.changeUsername(viewUsername);
                       nombre_res = viewUsername; 
                       vista.pausarEjecucion(2000);
                       //actualizamos el user
                       user = getUsuario(viewUsername);
                       //return nombre_res;
                   }
            }
            //caso de solo cambio de contraseña.
            if(viewPassword != null) { 
                //nombre_res = actualUsername;
                    if(newPasswordVerification(viewPassword, user.getPassword())) {
                        user.changePassword(viewPassword);
                        vista.pausarEjecucion(2000);
                    }
                    else {
                        throw new IllegalArgumentException("\n" + "Error CU: Debes introducir una contraseña diferente a la actual\n");
                    }
                    vista.printlnMensaje("\n");
            }
            /*else {
                throw new IllegalArgumentException("Error CU: La opción introducida no es válida\n"); 
            }*/
        }   
        return nombre_res;
    }

    /** 
     * Método que actualiza las estadísticas de todos los usuarios en el sistema.
     */
    public void upgradeAllUsersEstadisticas() {
       for(Usuario u : usuarios) {
        u.upgradeEstadisticasUser();
       }
    }

    /**
     * Método que elimina las estadísticas de un usuario específico.
     * 
     * @param username El nombre de usuario del usuario que se desea eliminar sus stats.
     */
    public void eliminaEstadisticasUser(String username) {
        Usuario u = getUsuario(username); 
        Estadistica nuevaStat = new Estadistica(); 
        u.setEstadisticas(nuevaStat);
    }

    /**
     * Método que muestra el reglamento del juego.
     */
    public void showReglamento() {
        if(reglamento == null) reglamento = Reglamento.getInstance(); 
        String reglas = reglamento.getReglamento();
        vista.printlnMensaje(reglas);
    }

    /**
     * Método que actualiza el ranking global de los usuarios.
     */
    public void updateGlobalRanking() {
        if(ranking == null) ranking = Ranking.getInstance(); 
        if(!usuarios.isEmpty()) {
            ranking.updateRanking(usuarios);
        }
    }

    /** 
     * Método que muestra el ranking de los usuarios en el sistema.
     */
    public void showRanking() {
        if(ranking == null) ranking = Ranking.getInstance(); 
        ranking.updateRanking(usuarios);
        List<String> currentRanking = ranking.getRanking();
            for(String content : currentRanking) {
                vista.printlnMensaje(content);
            }
    }


    /**
     * Método que obtiene las fichas del atril del usuario actual.
     * 
     * @param username El nombre de usuario del usuario actual.
     * @return La lista de fichas del atril del usuario actual.
     */
    public List<String> getFichasCurrentUser(String username) {
        Usuario user = getUsuario(username); 
        return user.getFichas(); 
    }

    /**
     * Método que retorna el objeto Estadística del usuario que pasamos su username como parámetro de entrada. 
     * 
     * @param username Nombre del usuario del que queremos obtener sus estadśiticas. 
     * @return El objeto Estadística en caso de que el user no sea null, null en caso contrario. 
     */
     public Estadistica getEstadisticasCurrentUser(String username) {
        Usuario user = getUsuario(username); 
        if(user != null) {
            return user.getStats(); 
        }
        else return null;
     }

     /**
      * Método que retorna el vector de usuarios que tenemos en el controlador.
      *
      *@return Un vector de usuarios que contiene los usuarios registrados en el sistema.
      */
      public List<Usuario> getVectorUsuarios() {
        return usuarios; 
      }

      /** Método que establece usuarios asignándole la lista pasada como parámetro de entrada
       * 
       * @param usuarios_cargados La lista de usuarios obtenidos desde persistencia. 
       */
    public void setUsuarios(List<Usuario> usuarios_cargados) {
        this.usuarios = usuarios_cargados; 
    }

    /**
     * Método que obitene la lista de usuarios ordenadas siguiendo los criterios establecidos.
     * 
     * @return La lista de usuarios ordenada.
     */
    public Vector<Usuario> getSortedVectorRanking() {
        updateGlobalRanking();
        return ranking.getSortedVectorRanking();
    }
}

