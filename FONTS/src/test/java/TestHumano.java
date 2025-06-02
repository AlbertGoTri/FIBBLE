import org.junit.Before; 
import org.junit.Test; 
import static org.junit.Assert.*; 
import dominio.Humano; 
import dominio.Usuario;

/**
 * Clase TestHumano:
 * Esta clase contiene los tests unitarios para verificar el correcto funcionamiento de la clase Humano,
 * que extiende la clase Usuario. Se prueban casos válidos e inválidos para garantizar que la creación de objetos
 * sea robusta.
 */
public class TestHumano {
    
    //el único test que tenemos que realizar es ver que se crea de forma correcta el objeto humano. 
    /**
     * Test para la creación de un objeto Humano válido.
     * Este test verifica que el objeto se crea correctamente con un nombre de usuario y una contraseña válidos.
     */
    @Test
    public void testCreacionHumanoValido() {
        String username = "userTest" ; 
        String password = "passwordTest"; 
        Humano humano = new Humano("userTest", "passwordTest"); 
        assertEquals(username,  humano.getUsername()); 
        assertEquals(password, humano.getPassword()); 
    }

    /**
     * Test para la creación de un objeto Humano inválido.
     * Este test verifica que se lanza una excepción IllegalArgumentException al intentar crear un objeto
     * con un nombre de usuario vacío.
     * 
     * @throws IllegalArgumentException Si el nombre de usuario es nulo o vacío.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreacionUsuarioUsernameIncorrecto() {
        String username = ""; 
        String passWord = "passwordTest"; 
        Humano humano = new Humano(username, passWord); 
    }

    /**
     * Test para la creación de un objeto Humano inválido.
     * Este test verifica que se lanza una excepción IllegalArgumentException al intentar crear un objeto
     * con una contraseña vacía.
     * 
     * @throws IllegalArgumentException Si la contraseña es nula o vacía.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreacionHumanoContraseñaIncorrecta() {
        String username = "userTest"; 
        String  password = ""; 
        Humano humano = new Humano(username, password); 
    }

}
