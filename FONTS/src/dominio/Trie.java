package dominio;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * La clase Trie implementa una estructura de datos de tipo árbol de prefijos (Trie),
 * que permite almacenar y buscar cadenas de caracteres de manera eficiente.
 */
public class Trie {

    /**
     * La clase TrieNode representa un nodo en el Trie.
     * Cada nodo contiene un mapa de hijos y un indicador de si es el final de una palabra.
     */
    class TrieNode {
        /**
         * Mapa que almacena los hijos del nodo actual, donde la clave es un carácter
         * y el valor es otro nodo TrieNode.
         */
        Map<String, TrieNode> children;

        /**
         * Indica si el nodo actual representa el final de una palabra.
         */
        boolean isEndOfWord;

        /**
         * Constructor de la clase TrieNode.
         * Inicializa el mapa de hijos y establece el indicador de fin de palabra en falso.
         */
        TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }

    /**
     * Nodo raíz del Trie.
     */
    TrieNode root;

    /**
     * Constructor de la clase Trie.
     * Inicializa el Trie con un nodo raíz vacío.
     */
    public Trie() {
        root = new TrieNode();
    }

    /**
     * Inserta una palabra en el Trie.
     *
     * @param word La palabra que se desea insertar.
     */
    public void insert(String word, List<String> letrasEspeciales) {
        TrieNode current = root;
        for (int i = 0; i < word.length();) {
            String letra = String.valueOf(word.charAt(i));
            boolean letraEspecialEncontrada = false;

            for (String especial : letrasEspeciales) {
                if (i + especial.length() <= word.length() &&
                    word.substring(i, i + especial.length()).equals(especial)) {
                    letra = especial;
                    i += especial.length();
                    letraEspecialEncontrada = true;
                    break;
                }
            }

            if (!letraEspecialEncontrada) {
                i++;
            }

            current.children.putIfAbsent(letra, new TrieNode());
            current = current.children.get(letra);
        }
        current.isEndOfWord = true;
    }


    /**
     * Busca una palabra en el Trie.
     *
     * @param word La palabra que se desea buscar.
     * @return {@code true} si la palabra existe en el Trie, {@code false} en caso contrario.
     */
    public boolean search(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            String substring = String.valueOf(word.charAt(i));
            if (!current.children.containsKey(substring)) {
                return false;
            }
            current = current.children.get(substring);
        }
        return current.isEndOfWord;
    }

    /**
     * Verifica si existe alguna palabra en el Trie que comience con el prefijo dado.
     *
     * @param prefix El prefijo que se desea buscar.
     * @return {@code true} si existe al menos una palabra con el prefijo dado,
     *         {@code false} en caso contrario.
     */
    public boolean startsWith(String prefix) {
        TrieNode current = root;
        for (int i = 0; i < prefix.length(); i++) {
            String substring = String.valueOf(prefix.charAt(i));
            if (!current.children.containsKey(substring)) {
                return false;
            }
            current = current.children.get(substring);
        }
        return true;
    }
}