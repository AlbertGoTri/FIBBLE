package dominio;

import java.util.HashMap;
import java.util.Map;

/**
 * La clase DAWG (Directed Acyclic Word Graph) implementa una estructura de datos
 * que permite almacenar palabras de manera eficiente, eliminando redundancias
 * mediante la minimización de nodos.
 */
public class DAWG {

    /**
     * La clase DAWGNode representa un nodo en el DAWG.
     * Cada nodo contiene un mapa de hijos y un indicador de si es el final de una palabra.
     */
    static class DAWGNode {
        /**
         * Mapa que almacena los hijos del nodo actual, donde la clave es un carácter
         * y el valor es otro nodo DAWGNode.
         */
        Map<String, DAWGNode> children;

        /**
         * Indica si el nodo actual representa el final de una palabra.
         */
        boolean isEndOfWord;

        /**
         * Constructor de la clase DAWGNode.
         * Inicializa el mapa de hijos y establece el indicador de fin de palabra en falso.
         */
        DAWGNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }

        /**
         * Obtiene los hijos del nodo actual.
         *
         * @return Un mapa que contiene los hijos del nodo.
         */
        public Map<String, DAWGNode> getChildren() {
            return children;
        }

        /**
         * Verifica si el nodo actual representa el final de una palabra.
         *
         * @return {@code true} si es el final de una palabra, {@code false} en caso contrario.
         */
        public boolean isEndOfWord() {
            return isEndOfWord;
        }

        /**
         * Calcula el código hash del nodo actual.
         *
         * @return El código hash basado en los hijos y el indicador de fin de palabra.
         */
        @Override
        public int hashCode() {
            return children.hashCode() + (isEndOfWord ? 1 : 0);
        }

        /**
         * Compara este nodo con otro objeto para verificar si son iguales.
         *
         * @param obj El objeto a comparar.
         * @return {@code true} si los nodos son iguales, {@code false} en caso contrario.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            DAWGNode other = (DAWGNode) obj;
            return isEndOfWord == other.isEndOfWord && children.equals(other.children);
        }
    }

    /**
     * Nodo raíz del DAWG.
     */
    private DAWGNode root;

    /**
     * Mapa que almacena los nodos minimizados para evitar redundancias.
     */
    private Map<DAWGNode, DAWGNode> minimizedNodes;

    /**
     * Constructor de la clase DAWG.
     * Inicializa el DAWG con un nodo raíz vacío y un mapa de nodos minimizados.
     */
    public DAWG() {
        root = new DAWGNode();
        minimizedNodes = new HashMap<>();
    }

    /**
     * Obtiene el nodo raíz del DAWG.
     *
     * @return El nodo raíz.
     */
    public DAWGNode getRoot() {
        return root;
    }

    /**
     * Construye el DAWG a partir de un Trie dado.
     *
     * @param trie El Trie del cual se desea construir el DAWG.
     */
    public void buildFromTrie(Trie trie) {
        root = minimize(trie.root);
    }

    /**
     * Minimiza un nodo del Trie y lo convierte en un nodo del DAWG.
     *
     * @param trieNode El nodo del Trie que se desea minimizar.
     * @return El nodo minimizado del DAWG.
     */
    private DAWGNode minimize(Trie.TrieNode trieNode) {
        DAWGNode dawgNode = new DAWGNode();
        dawgNode.isEndOfWord = trieNode.isEndOfWord;

        for (Map.Entry<String, Trie.TrieNode> entry : trieNode.children.entrySet()) {
            String key = entry.getKey();
            Trie.TrieNode childTrieNode = entry.getValue();
            DAWGNode childDawgNode = minimize(childTrieNode);
            dawgNode.children.put(key, childDawgNode);
        }

        if (minimizedNodes.containsKey(dawgNode)) {
            return minimizedNodes.get(dawgNode);
        } else {
            minimizedNodes.put(dawgNode, dawgNode);
            return dawgNode;
        }
    }
}