import java.util.Iterator;
import java.util.Set;
import java.lang.Comparable;

/**
 * A BST-based implementation of the Map61B interface,
 * which represents a basic tree-based map.
 *
 * @author Dawei Gu
 */

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class Node {
        /** Key of the node. */
        private K key;
        /** Value of the node. */
        private V value;
        /** Left branch of the node. */
        private Node left;
        /** Right branch of the node. */
        private Node right;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    /** Root Node */
    private Node root;
    /** Size of the whole BSTMap. */
    private int size;

    /**
     * Default constructor of the BSTMap class.
     */
    BSTMap() {
        root = null;
        size = 0;
    }


    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        root = null;
    }

    /**
     * Find a the value of the given key.
     *
     * @param key Given key
     * @return the value of the key if found
     */
    private V find(Node n ,K key) {
        if (n == null) {
            return null;
        }

        int cmp = key.compareTo(n.key);
        if (cmp == 0) {
            return n.value;
        } else if (cmp < 0) {
            return find(n.left, key);
        } else {
            return find(n.right, key);
        }
    }


    /**
     * Returns true if this map contains a mapping
     * for the specified key.
     *
     * @param key Given key
     * @return true or false
     */
    @Override
    public boolean containsKey(K key) {
        return (find(root, key) != null);
    }

    @Override
    public V get(K key) {
        return find(root, key);
    }

    private int size(BSTMap m) {
        if (m.root == null) {
            return 0;
        } else {
            return m.size;
        }
    }

    @Override
    public int size() {
        return size(this);
    }


    private Node put(Node n, K key, V value) {
        if (n == null) {
            size += 1;
            return new Node(key, value);
        }

        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            n.left = put(n.left, key, value);
        } else if (cmp > 0) {
            n.right = put(n.right, key, value);
        }
        return n;
    }


    /**
     * Associates the specified value with the specified key in this map.
     *
     * @param key Given key
     * @param value Given value
     */
    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }


    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    private void printNode(Node n) {
        System.out.println("[" + n.key + " " + n.value + "]");
    }

    private void printInOrder(Node n) {
        if (n.left == null && n.right == null) {
            printNode(n);
        } else if (n.left == null) {
            printNode(n);
            printInOrder(n.right);
        } else if (n.right == null) {
            printInOrder(n.left);
            printNode(n);
        } else {
            printInOrder(n.left);
            printNode(n);
            printInOrder(n.right);
        }

    }

    /**
     * Prints out the BSTMap in order of increasing Key.
     */
    public void printInOrder() {
        printInOrder(root);
    }
}
