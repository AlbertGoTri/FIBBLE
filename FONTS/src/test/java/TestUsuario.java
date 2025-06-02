import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import dominio.Usuario;

/**
 * Clase TestUsuario
 * Esta clase contiene los tests unitarios para la clase Usuario.
 * Se utilizan técnicas de redirección de entrada y salida estándar para simular la interacción con el usuario.
 * 
 */
public class TestUsuario {
    /**
     * Test para la creación de un usuario de manera correcta.
     * Este test verifica que el nombre de usuario y la contraseña sean válidos y se asignen correctamente. 
    */ 
    @Test 
    public void testCreacionUsuarioCorrecta() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        //verificamos que se ha asigando de manera correcta el nombre.
        assertEquals("userTest", usuario.getUsername().trim()); 
        assertEquals("passwordTest", usuario.getPassword().trim()); 
    }

    /**
     * Test para la creación de un usuario con nombre inválido.
     * Este test verifica que se lanza una excepción IllegalArgumentException si el nombre de usuario no es válido.
     *
     * @throws IllegalArgumentException si el nombre de usuario es vacío.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreacionUsuarioNombreIncorrecto() {
        new Usuario("", "passwordTest"); //username vacío.  
    }

    /** 
     * Test para la creación de un usuario con contraseña inválida.
     * Este test verifica que se lanza una excepción IllegalArgumentException si la contraseña no es válida.
     *
     * @throws IllegalArgumentException si la contraseña es vacía.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreacionUsuarioPasswordIncorrecta() {
        new Usuario("userTest", ""); //contra vacía. 
    }

    /**
     * Test para corroborar que se realiza de forma correcta el establecimiento de nombre de usuario.
     * Este test verifica que el nombre de usuario se establece correctamente y que no se permite un nombre vacío.
     */     
    @Test 
    public void setUsernameValido() {
        String newName = "Lorca";
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        usuario.setUsername(newName); 
        assertEquals(newName, usuario.getUsername());
    }

    /**
     * Test para corroborar que se lanza una excepción IllegalArgumentException si el nombre de usuario es inválido.
     * Este test verifica que se lanza una excepción IllegalArgumentException si el nombre de usuario es vacío.
     *
     * @throws IllegalArgumentException si el nombre de usuario es vacío.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setUsernameInvalido() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        usuario.setUsername(""); 
    }

    /**
     * Test para corroborar que se realiza de forma correcta el estableicimiento de contraseña.
     * Este test verifica que la contraseña se establece correctamente y que no se permite una contraseña vacía.
     */
    @Test
    public void setPasswordValido() {
        Usuario usuario = new Usuario("userTest", "passwordTest");
        String newPassword = "contraseña"; 
        usuario.setPassword(newPassword); 
        assertEquals(newPassword, usuario.getPassword()); 
    }

    /** 
     * Test para corroborar que se lanza una excepción IllegalArgumentException si la contraseña es inválida.
     * Este test verifica que se lanza una excepción IllegalArgumentException si la contraseña es vacía.
     *
     * @throws IllegalArgumentException si la contraseña es vacía.
     */
    @Test(expected = IllegalArgumentException.class)
    public void setPasswordInvalido() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        usuario.setPassword(""); 
    }

    //PARA ESTADISTICAS NO ME HACE FALTA.
    /**
     * Test para corroborar que se realiza de forma correcta el cambio de nombre de usuario.
     * Este test verifica que el nombre de usuario se cambia correctamente y que no se permite un nombre vacío.
     */
    @Test
    public void testChangeUsernameValido() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        String newName = "newUserTestName"; 
        newName = newName.trim(); 
        usuario.changeUsername(newName); 
        //verficamos que el nombre usuario se ha cambiado correctamente.
        assertEquals(newName, usuario.getUsername().trim()); 
    }

    /**
     * Test para corroborar que se lanza una excepción IllegalArgumentException si el nombre de usuario es inválido.
     * Este test verifica que se lanza una excepción IllegalArgumentException si el nombre de usuario es vacío a la hora de querer cambiarlo.
     *
     * @throws IllegalArgumentException si el nombre de usuario es vacío.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testChangeUsernameInvalido() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        usuario.changeUsername(""); 
    }

    /**
     * Test para corroborar que se realiza de forma correcta el cambio de contraseña.
     * Este test verifica que la contraseña se cambia correctamente y que no se permite una contraseña vacía.
     *
     * @throws IllegalArgumentException si la contraseña es vacía.
     */
    @Test 
    public void testChangePasswordValido() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        String newPassword = "newPasswordTest"; 
        newPassword = newPassword.trim(); 
        usuario.changePassword(newPassword); 
        //verificamos que se ha modificado de forma correcta la contraseña: 
        assertEquals(newPassword, usuario.getPassword().trim()); 
    }

    /**
     * Test para corroborar que se lanza una excepción IllegalArgumentException si la contraseña es inválida.
     * Este test verifica que se lanza una excepción IllegalArgumentException si la contraseña es vacía a la hora de querer cambiarla.
     *
     * @throws IllegalArgumentException si la contraseña es vacía.
     */
    @Test(expected = IllegalArgumentException.class) 
    public void testChangePasswordInvalido() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        usuario.changePassword("");
    }

    /**
     * Test para corroborar que se realiza de forma correcta la obtención de fichas.
     * Este test verifica que se obtienen las fichas correctamente y que no se permite un atril vacío.
     */
    @Test 
    public void testGetFichasValido() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        //tenemos que testear los casos en los que se añaden fichas en el atril. 
        String ficha1 = "A"; 
        String ficha2 = "B"; 
        String ficha3 = "C"; 
        usuario.agregarFicha(ficha1); 
        usuario.agregarFicha(ficha2); 
        usuario.agregarFicha(ficha3); 
        //analizamos el tamaño del atril. 
        assertEquals(3, usuario.getCantidadFichas()); 
        //analizamos si se han añadido de forma correcta. 
        assertTrue(usuario.getFichas().contains(ficha1));
        assertTrue(usuario.getFichas().contains(ficha2)); 
        assertTrue(usuario.getFichas().contains(ficha3));          
    }

    /**
     * Test para corroborar que se lanza una excepción IllegalArgumentException si el atril está vacío.
     * Este test verifica que se lanza una excepción IllegalArgumentException si el atril no contiene fichas.
     * 
     * @throws IllegalArgumentException si el atril está constituido de fichas nulas.
     */
    @Test(expected = IllegalArgumentException.class) 
    public void testGetFichasInvalidoNull() {
        Usuario usuario = new Usuario("userTest", "passwordTest");
        //el problema nace al no disponer de fichas en el atril. 
        usuario.getFichas(); 
    }

    /**
     * Test para corroborar que se lanza una excepción IllegalArgumentException si el atril está vacío.
     * Este test verifica que se lanza una excepción IllegalArgumentException si el atril no contiene fichas.
     * 
     * @throws IllegalArgumentException si el atril está constituido de fihcas vacías.
     */
    @Test(expected = IllegalArgumentException.class) 
    public void testGetFichasInvalidoConEspacio() {
        Usuario usuario = new Usuario("userTest", "passwordTest");
        //el problema nace al no disponer de fichas en el atril. 
        usuario.getFichas(); 
    }

    /** 
     * Test para corroborar que se realiza de forma correcta la adición de fichas al atril.
     * Este test verifica que se pueden agregar fichas válidas al atril.
    */
    @Test 
    public void testAgregarFichaValida() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        String ficha1 = "A"; 
        String ficha2 = "B"; 
        String ficha3 = "C"; 
        usuario.agregarFicha(ficha1); 
        usuario.agregarFicha(ficha2); 
        usuario.agregarFicha(ficha3); 
        assertEquals(3, usuario.getCantidadFichas()); 
        assertTrue(usuario.getFichas().contains(ficha1));
        assertTrue(usuario.getFichas().contains(ficha2)); 
        assertTrue(usuario.getFichas().contains(ficha3));  
    }

    /**
     * Test para corroborar que se lanza una excepción IllegalArgumentException si la adición de ficha se trata de una nula en el atril.
     * Este test verifica que se lanza una excepción IllegalArgumentException.
     * 
     * @throws IllegalArgumentException si la ficha es nula.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAgregarFichaInvalidaNull() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        usuario.agregarFicha(null);
    }

    /**
     * Test para corroborar que se lanza una excepción IllegalArgumentException si la adición de ficha se trata de una vacía en el atril.
     * Este test verifica que se lanza una excepción IllegalArgumentException.
     * 
     * @throws IllegalArgumentException si la ficha es vacía o contiene solo espacios.
    */
    @Test(expected = IllegalArgumentException.class)
    public void testAgregarFichaInvalidaVacia() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        usuario.agregarFicha(""); 
    }

    /**
     * Test para corroborar que se lanza una excepción IllegalArgumentException si la adición de ficha se trata de un espacio en el atril.
     * Este test verifica que se lanza una excepción IllegalArgumentException.
     * 
     * @throws IllegalArgumentException si la ficha es un espacio.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAgregarFichaInvalidaEspacio() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        usuario.agregarFicha(" "); 
    }

    /**
     * Test para corroborar que se lanza una excepción IllegalArgumentException si la adición de ficha se trata de un atril lleno.
     * Este test verifica que se lanza una excepción IllegalArgumentException.
     * 
     * @throws IllegalArgumentException si el atril está lleno.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAgregarFichaInvalidaAtrilLleno() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        for(int i = 0;  i < 7; ++i) {
            usuario.agregarFicha("A" + i); 
        }
        //el atril en este punto ya está lleno. 
        usuario.agregarFicha("B"); 
    }

    /**
     * Test para corroborar que se lanza una excepción IllegalArgumentException si la adición de ficha se trata de un atril lleno y la ficha es vacía.
     * Este test verifica que se lanza una excepción IllegalArgumentException.
     * 
     * @throws IllegalArgumentException si el atril está lleno y la ficha es vacía.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAgregarFichaInvalidaVaciaAtrillLleno() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        for(int i = 0; i < 7; ++i) {
            usuario.agregarFicha("A" + i); 
        }
        usuario.agregarFicha(""); 
    }

    /** 
     * Test para corroborar que se actualiza de forma correcta la puntuación de la partida actual.
     * Este test verifica que se puede sumar puntos a la puntuación de la partida actual.
     */
    @Test 
    public void sumarPuntos() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        int puntos = 1000; 
        usuario.sumarPuntos(puntos); 
        assertEquals(puntos, usuario.getPuntuacionPartidaActual()); 
    }

    /**
     * Test para corroborar que se actualiza de forma correcta la puntuación de la partida actual.
     * Este test verifica que no se permite sumar puntos negativos a la puntuación de la partida actual.
     */
    @Test 
    public void testSumarPuntosSinNegativos() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        int puntos = -1000; 
        usuario.sumarPuntos(puntos); 
        assertEquals(0, usuario.getPuntuacionPartidaActual());
    }

    /**
     * Test para corroborar que se actualiza de forma correcta la puntuación de la partida actual a la hora de restar.
     * Este test verifica que se puede restar puntos a la puntuación de la partida actual.
     */
    @Test 
    public void testRestarPuntos() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        int puntos = 1000; 
        usuario.sumarPuntos(puntos); 
        int penalizacion = 500; 
        usuario.restarPuntos(penalizacion); 
        assertEquals(500, usuario.getPuntuacionPartidaActual());
    }

    //este test te mira si podemos tener una puntuación  negativa cosa que no es cierta, la mínima  puntuación debe ser 0. 
    /**
     * Test para corroborar que se actualiza de forma correcta la puntuación de la partida actual a la hora de restar.
     * Este test verifica que no se permite restar puntos negativos a la puntuación de la partida actual (en caso de que en hacer A - B sea B > A, la puntuación deberá ser 0).
     */
    @Test
    public void testRestarPuntosSinNegativos() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        int puntos = 1000; 
        usuario.sumarPuntos(puntos); 
        int penalizacion = 2000; 
        usuario.restarPuntos(penalizacion); 
        assertEquals(0, usuario.getPuntuacionPartidaActual());
    }

    /**
     * Test para corroborar que se comprueba de forma correcta si el usuario es un robot.
     * Este test verifica que se puede establecer y obtener el estado de robot del usuario de forma correcta.
     */
    @Test
    public void testEsRobot() {
        Usuario usuario = new Usuario("userTest", "passwordTest"); 
        usuario.setEsRobot(true); 
        assertTrue(usuario.getesRobot());
        usuario.setEsRobot(false); 
        assertFalse(usuario.getesRobot()); 
    }

}