package dominio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Clase auxiliar que encapsula dos recursos clave para el juego:
 * </p>
 * <ul>
 *     <li><strong>Puntuación de las letras</strong>: mapa «letra → puntos» que
 *         se obtiene de un fichero <code>&lt;idioma&gt;Punt.txt</code>.</li>
 *     <li><strong>Diccionario de palabras válidas</strong>: conjunto de cadenas
 *         cargado desde <code>&lt;idioma&gt;Dic.txt</code>.</li>
 * </ul>
 * <p>
 * Ambos ficheros deben estar accesibles en el <em>classpath</em> (por ejemplo,
 * dentro de <code>resources/</code>).  La clase implementa
 * {@link java.io.Serializable} para que su estado sea persistente junto con
 * {@link dominio.Partida}.
 * </p>
 *
 * @author  Yeray Franco
 */
public class Diccionario implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /** Mapa que asocia letras o dígrafos (p. ej. «LL») con su puntuación. */
    private Map<String, Integer> puntuacionLetras;

    /** Conjunto de todas las palabras válidas admitidas en el idioma. */
    private Set<String> palabrasDiccionario;

    /** Crea un diccionario vacío. El contenido se carga con {@link #setCargarDiccionario(String)}. */
    public Diccionario() {
        puntuacionLetras = new HashMap<>();
        palabrasDiccionario = new HashSet<>();
    }

    /** @return conjunto sin envolver de palabras tal como se cargó. */
    public Set<String> getPalabras() {
        return palabrasDiccionario;
    }

    /**
     * Carga en memoria los archivos de puntuaciones y diccionario correspondientes
     * al {@code idioma}.  Los archivos deben llamarse<br>
     * <code>&lt;idioma&gt;Punt.txt</code> y <code>&lt;idioma&gt;Dic.txt</code>.
     * <p>
     * El fichero de puntuaciones debe contener líneas del tipo «LETRA PUNTOS».
     * El fichero de diccionario puede contener una palabra por línea.
     * </p>
     * @param idioma Identificador (en minúsculas) del idioma a cargar.
     */
    public void setCargarDiccionario(String idioma) {
        // ─── Fichero de puntuaciones ─────────────────────────────────────────
        String nombreArchivoPuntuacion = idioma + "Punt.txt";
        try (InputStream inputStreamPunt = getClass().getClassLoader().getResourceAsStream(nombreArchivoPuntuacion)) {
            if (inputStreamPunt == null) {
                System.out.println("No se encontró el archivo de puntuaciones: " + nombreArchivoPuntuacion);
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStreamPunt))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        String[] partes = linea.split(" ");
                        if (partes.length == 2) {
                            String letra = partes[0];
                            try {
                                int puntuacion = Integer.parseInt(partes[1]);
                                puntuacionLetras.put(letra, puntuacion);
                            } catch (NumberFormatException ex) {
                                System.out.println("⚠️ Número inválido en línea de puntuaciones: " + linea);
                            }
                        } else {
                            System.out.println("⚠️ Línea inválida en archivo de puntuaciones: " + linea);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo de puntuaciones: " + e.getMessage());
        }

        // ─── Fichero de palabras ────────────────────────────────────────────
        String nombreArchivoPalabras = idioma + "Dic.txt";
        try (InputStream inputStreamDic = getClass().getClassLoader().getResourceAsStream(nombreArchivoPalabras)) {
            if (inputStreamDic == null) {
                System.out.println("❌ No se encontró el archivo del diccionario: " + nombreArchivoPalabras);
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStreamDic))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        palabrasDiccionario.add(linea.trim().toUpperCase());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error al leer archivo de diccionario: " + e.getMessage());
        }
    }

    /**
     * Comprueba si la {@code palabra} existe en el diccionario (ignorando
     * mayúsculas/minúsculas).
     * @param palabra término a buscar
     * @return {@code true} si la palabra está en el diccionario, de lo contrario {@code false}
     */
    public boolean getEstaDiccionario(String palabra) {
        return palabrasDiccionario.contains(palabra.toUpperCase());
    }

    /**
     * Calcula la puntuación de {@code palabra} sumando las puntuaciones de cada
     * letra o dígrafo según las reglas de Scrabble.
     *
     * @param palabra palabra de la que se desea conocer la puntuación
     * @return suma de puntos de la palabra entera
     */
    public int calcularPuntuacionPalabra(String palabra) {
        int puntuacionTotal = 0;
        palabra = palabra.toUpperCase();
        int i = 0;
        while (i < palabra.length()) {
            if (i + 2 < palabra.length()) {
                String tres = palabra.substring(i, i + 3);
                if (puntuacionLetras.containsKey(tres)) {
                    puntuacionTotal += getPuntuacionLetra(tres);
                    i += 3;
                    continue;
                }
            }
            if (i + 1 < palabra.length()) {
                String dos = palabra.substring(i, i + 2);
                if (puntuacionLetras.containsKey(dos)) {
                    puntuacionTotal += getPuntuacionLetra(dos);
                    i += 2;
                    continue;
                }
            }
            String una = palabra.substring(i, i + 1);
            puntuacionTotal += getPuntuacionLetra(una);
            i += 1;
        }
        return puntuacionTotal;
    }

    /**
     * Devuelve la puntuación asociada a {@code letra} o 0 si no existe.
     */
    public int getPuntuacionLetra(String letra) {
        return puntuacionLetras.getOrDefault(letra, 0);
    }
}
