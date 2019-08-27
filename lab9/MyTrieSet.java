import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Implement Trie Class.
 *
 * @author Dawei Gu
 */
public class MyTrieSet implements TrieSet61B {
    /** the root node. */
    private Node root;
    /** Node class implementation. */
    private static class Node {
        boolean isKey;
        private HashMap<Character, Node> map;
        private Node() {
            this(false);
        }
        private Node(boolean b) {
            isKey = b;
            map = new HashMap<>();
        }
    }

    public MyTrieSet() {
        root = new Node();
    }

    /**
     * Clears all items out of Trie.
     */
    @Override
    public void clear() {
        root = new Node();
    }

    /**
     * Returns true if the Trie contains KEY, false otherwise.
     *
     * @param key
     */
    @Override
    public boolean contains(String key) {
        if (key == null || key.length() < 1) {
            return false;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i += 1) {
            char c = key.charAt(i);
            if (curr == null) {
                return false;
            }
            if (!curr.map.containsKey(c)) {
                return false;
            }
            curr = curr.map.get(c);
        }
        return true;
    }

    /**
     * Inserts string KEY into Trie.
     *
     * @param key
     */
    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i += 1) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    /**
     * helper method for keysWithPrefix method.
     * @param s string
     * @param ls list storing the keys
     * @param n next node
     * @return the list
     */
    private List<String> prefixHelp(String s, List<String> ls, Node n) {
        if (n.isKey) {
            ls.add(s);
        }
        for (char c: n.map.keySet()) {
            ls.addAll(prefixHelp(s + c, new ArrayList<>(), n.map.get(c)));
        }
        return ls;
    }

    /**
     * Returns a list of all words that start with PREFIX.
     *
     * @param prefix
     */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> returnList = new ArrayList<>();
        if (prefix == null || prefix.length() < 1) {
            return null;
        }
        Node curr = root;
        for (int i = 0, n = prefix.length(); i < n; i += 1) {
            char c = prefix.charAt(i);
            if (curr == null) {
                return null;
            }
            if (!curr.map.containsKey(c)) {
                return null;
            }
            curr = curr.map.get(c);
        }
        return prefixHelp(prefix, returnList, curr);
    }

    /**
     * Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
