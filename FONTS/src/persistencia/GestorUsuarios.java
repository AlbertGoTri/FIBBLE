package persistencia;

import dominio.Usuario;
import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader; //nos permite leer el archivo
import java.io.FileWriter; //nos permite escribir el archivo
import java.io.IOException;


/**
 * Clase GestorUsuarios:
 * Esta clase se encarga de gestionar la persistencia de los usuarios  existentes en el sistema. 
 */
public class GestorUsuarios {

    private static final String RUTA_USUARIOS = "data_persistencia/usuarios/";
    

    static  {
        File carpetaUsuarios = new File(RUTA_USUARIOS); 
        if(!carpetaUsuarios.exists()) {
            carpetaUsuarios.mkdirs();
        }
        else {
            System.out.println("\n"); 
        }
    }

    /**
     * Método que almacena en un archivo JSON el user que se pasa por parámetro. 
     * 
     * @param usuario Es el objeto Usuario que se desea almacenar. 
     */
    public static void guardarUsuario(Usuario usuario) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
        String archivo = RUTA_USUARIOS + usuario.getUsername(); 

        try(FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(usuario, writer);
        }
        catch(IOException e) {
            System.out.println("Gestor de Usuarios: Error a la hora de guardar el usuario " + e.getMessage()); 
        }
    }

    public static Usuario cargarUsuario(String username) {
        Gson gson = new Gson(); 
        String archivo = RUTA_USUARIOS + username;

        
        try(FileReader reader = new FileReader(archivo)) {
            
            Usuario user_json = gson.fromJson(reader, Usuario.class); 
            return user_json;
        }
        catch (IOException e) {
            System.out.println("Error Gestor Usuario: " + e.getMessage()); 
            return null;
        }
    }

    /**
     * Método para eliminar al usuario deseado de la capa de persistencia
     * 
     * @param usuario Es el objeto Usuario que representa el usuario que deseamos eliminar del sistema
     */
    
     public static void eliminarUsuario(Usuario usuario) { 
        String archivo = RUTA_USUARIOS + usuario.getUsername(); 
        File archivoUsuario = new File(archivo); 
        
        if(archivoUsuario.exists()) {
            archivoUsuario.delete();
        }
        else System.err.println("\n"); 
     }

     /**Método que busca entre los Json para verificar si existe el user o no en nuestro sistema.
      * 
      @param username Representa el nombre del user que se precisa verificar su existencia en el sistema. 
      @return true si existe en el sistema, false en caso contrario. 
      */
      public static boolean existeUser(String username) {
        String archivo = RUTA_USUARIOS + username.trim(); 
        File archivoUsuario = new File(archivo); 
        
        return archivoUsuario.exists(); 
      }
}


