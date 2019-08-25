import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;


/**
 * Implement Map61B interface to create a class that represents
 * a hash map.
 *
 * @author Dawei Gu
 */
public class MyHashMap<Key, Value> implements Map61B<Key, Value> {
    /** number of buckets. */
    private int numOfBuckets;
    /** number of stored entries. */
    private int numOfEntries;
    /** load factor = numOfEntries / numOfBuckets. */
    private double loadFactor;
    /** buckets. */
    private ArrayList<ArrayList<Entry>> buckets;
    /** stored keys. */
    private Set<Key> keyChain = new HashSet<>();

    /** Key-Value Entry class. */
    private class Entry {
        private Key k;
        private Value v;

        Entry(Key key, Value value) {
            this.k = key;
            this.v = value;
        }
    }

    /** default constructor. */
    public MyHashMap() {
        this(16);
    }

    /** constructor with initialSize provided. */
    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    /** constructor with initialSize and loadFactor provided. */
    public MyHashMap(int initialSize, double loadFactor) {
        this.numOfBuckets = initialSize;
        this.loadFactor = loadFactor;
        buckets = new ArrayList<>(numOfBuckets);
        numOfEntries = 0;
        for (int i = 0; i < numOfBuckets; i++) {
            buckets.add(new ArrayList<>());
        }
    }


    /** hash function.
     *
     * @param key the key to be hashed
     * @return the bucket number for this key
     */
    private int hash(Key key) {
        return (key.hashCode() & 0x7FFFFFFF) % numOfBuckets;
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        keyChain = new HashSet<>();
        buckets = new ArrayList<>();
        numOfEntries = 0;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(Key key) {
        return keyChain.contains(key);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     * @return
     */
    @Override
    public Value get(Key key) {
        if (!containsKey(key)) {
            return null;
        }

        int hashCode = hash(key);
        for (Entry p : buckets.get(hashCode)) {
            if (p.k.equals(key)) {
                return p.v;
            }
        }
        return null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return numOfEntries;
    }

    /**
     *
     * @return number of buckets.
     */
    public int getNumOfBuckets() {
        return numOfBuckets;
    }

    /** Resize the HashMap.
     *
     * @param scale = newNumOfBuckets / numOfBuckets
     */
    private void resize(int scale) {
        int newNumOfBuckets = scale * numOfBuckets;
        ArrayList<ArrayList<Entry>> newBuckets = new ArrayList<>(newNumOfBuckets);
        for (int i = 0; i < newNumOfBuckets; i += 1) {
            newBuckets.add(new ArrayList<>());
        }
        for (Key k: keyChain) {
            int hashCode = hash(k);
            newBuckets.get(hashCode).add(new Entry(k, get(k)));
        }
        this.buckets = newBuckets;
        this.numOfBuckets *= 2;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(Key key, Value value) {
        int hashCode = hash(key);
        if (containsKey(key)) {
            for (Entry p : buckets.get(hashCode)) {
                if (p.k.equals(key)) {
                    p.v = value;
                }
            }
        } else {
            if ((double) size() / getNumOfBuckets() >= loadFactor) {
                this.resize(2);
            }
            buckets.get(hashCode).add(new Entry(key, value));
            keyChain.add(key);
            numOfEntries += 1;
        }
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<Key> keySet() {
        return keyChain;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     * @return
     */
    @Override
    public Value remove(Key key) {
        if (!keySet().contains(key)) {
            return null;
        } else {
            int hashCode = hash(key);
            for (Entry entry: buckets.get(hashCode)) {
                if (key.equals(entry.k)) {
                    Value returnValue = entry.v;
                    entry = null;
                    keyChain.remove(key);
                    numOfEntries -= 1;

                    return returnValue;
                }
            }
        }
        return null;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     * @param key
     * @param value
     * @return
     */
    @Override
    public Value remove(Key key, Value value) {
        if (value.equals(this.get(key))) {
            this.remove(key);
        }
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Key> iterator() {
        return keyChain.iterator();
    }
}
