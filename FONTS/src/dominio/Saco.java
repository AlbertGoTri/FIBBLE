package dominio;

import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.util.Random;

/**
 * <p>
 * Representa el saco de fichas utilizado durante la partida.  Internamente se
 * modela como un mapa <em>letra → frecuencia</em>, donde la clave es la letra
 * (mayúscula o secuencia especial como «LL», «QU», etc.) y el valor es el número
 * de fichas restantes de esa letra en el saco.
 * </p>
 *
 * <h3>Características principales</h3>
 * <ul>
 *   <li>Carga las frecuencias desde un archivo de texto <code>&lt;idioma&gt;Freq.txt</code>
 *       ubicado en el <em>classpath</em>.  Cada línea debe tener el formato
 *       <code>LETRA FRECUENCIA</code>.</li>
 *   <li>Permite extraer una ficha al azar respetando la distribución de
 *       frecuencias (método {@link #getRobarLetra()}).</li>
 *   <li>Informa si el saco está vacío y expone el mapa de frecuencias con fines
 *       de depuración o visualización.</li>
 * </ul>
 *
 * <p>
 * La clase implementa {@link java.io.Serializable} para persistir el estado de
 * la partida.  Se añade un {@code serialVersionUID} explícito para evitar
 * conflictos en futuras versiones.
 * </p>
 *
 * @author  Yeray Franco
 */
public class Saco implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /** Mapa que almacena la cantidad restante de cada letra. */
    private Map<String, Integer> letrasFreq;

    /** Crea un saco vacío. El contenido se carga posteriormente con {@link #setLlenarSaco(String)}. */
    public Saco() {
        letrasFreq = new HashMap<>();
    }

    /**
     * Rellena el saco leyendo el fichero <code>&lt;idioma&gt;Freq.txt</code> del
     * <em>classpath</em>.  El método limpia cualquier contenido previo.
     *
     * @param idioma código de idioma en minúsculas («castellano», «catalan», «ingles»…)
     */
    public void setLlenarSaco(String idioma) {
        letrasFreq.clear();
        String nombreArchivo = idioma + "Freq.txt";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(nombreArchivo);
        if (inputStream == null) {
            System.out.println("No se encontró el archivo: " + nombreArchivo);
            return;
        }
        try (BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String linea;
            while ((linea = bufferReader.readLine()) != null) {
                String[] partes = linea.split(" ");
                if (partes.length != 2) {
                    System.out.println("Formato incorrecto en la línea: " + linea);
                    continue;
                }
                String letra = partes[0];
                try {
                    int frecuencia = Integer.parseInt(partes[1]);
                    letrasFreq.put(letra, frecuencia);
                } catch (NumberFormatException e) {
                    System.out.println("Error al convertir la frecuencia en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Extrae aleatoriamente una letra del saco respetando la distribución de
     * frecuencias restantes.
     *
     * @return la letra robada o {@code null} si el saco está vacío
     * @throws IllegalStateException si ocurre un error inesperado durante la selección
     */
    public String getRobarLetra() {
        int totalFrecuencia = letrasFreq.values().stream().mapToInt(Integer::intValue).sum();
        if (totalFrecuencia == 0) return null; // Saco vacío

        int randomInt = new Random().nextInt(totalFrecuencia) + 1;
        int acumulado = 0;
        for (Map.Entry<String, Integer> entry : letrasFreq.entrySet()) {
            acumulado += entry.getValue();
            if (randomInt <= acumulado) {
                String letraSeleccionada = entry.getKey();
                int nuevaFreq = entry.getValue() - 1;
                if (nuevaFreq == 0) {
                    letrasFreq.remove(letraSeleccionada);
                } else {
                    letrasFreq.put(letraSeleccionada, nuevaFreq);
                }
                return letraSeleccionada;
            }
        }
        
        throw new IllegalStateException("No se pudo seleccionar una letra.");
    }

    /**
     * @return {@code true} si el saco ya no contiene fichas.
     */
    public boolean estaVacio() {
        return letrasFreq.isEmpty();
    }

    /**
     * Devuelve el mapa interno de frecuencias.  Se recomienda devolver una copia
     * inmutable en futuras versiones para evitar mutaciones externas.
     */
    public Map<String, Integer> getLetrasFreq() {
        return letrasFreq;
    }

    /** Sustituye el mapa de frecuencias por otro (principalmente para tests). */
    public void setLetrasFreq(Map<String, Integer> letrasFreq) {
        this.letrasFreq = letrasFreq;
    }

    /** Elimina todas las fichas del saco. Equivale a dejar el saco vacío. */
    public void vaciarSaco() {
        letrasFreq.clear();
    }
}
