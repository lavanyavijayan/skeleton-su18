import java.util.LinkedList;
import java.util.Iterator;

public class HashMap<K, V> implements Map61BL<K, V> {

    /* Instance variables */
    private LinkedList<Entry<K, V>>[] entries;
    private int numOfEntries;
    private int capacity;
    private double loadFactor;

    /* Constructs a new HashMap with a default capacity of 16 and load factor of 0.75. */
    public HashMap() {
        entries = (LinkedList<Entry<K, V>> []) new LinkedList[16];
        capacity = 16;
        this.loadFactor = 0.75;
    }

    /* Constructs a new HashMap with the specified INITIALCAPACITY
    and default load factor of 0.75. */
    public HashMap(int initialCapacity) {
        entries = (LinkedList<Entry<K, V>> []) new LinkedList[initialCapacity];
        capacity = initialCapacity;
        this.loadFactor = 0.75;
    }

    /* Constructs a new HashMap with the specified INITIALCAPACITY and LOADFACTOR. */
    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity, double loadFactor) {
        entries = (LinkedList<Entry<K, V>> []) new LinkedList[initialCapacity];
        capacity = initialCapacity;
        this.loadFactor = loadFactor;
    }

    /* Returns true if the given KEY is a valid name that starts with A - Z. */
    private static boolean isValidName(String key) {
        return 'A' <= key.charAt(0) && key.charAt(0) <= 'Z';
    }

    /* Returns a specific integer when given a key. */
    private int hash(K key) {
        return Math.floorMod(key.hashCode(), entries.length);
    }

    /* Resizes the HashMap, by doubling its capacity. */
    @SuppressWarnings("unchecked")
    private void resize() {
        int currentLength = entries.length;
        capacity += currentLength;
        LinkedList<Entry<K, V>>[] resizedArray;
        resizedArray = (LinkedList<Entry<K, V>> []) new LinkedList[currentLength * 2];
        for (int i = 0; i < currentLength; i++) {
            resizedArray[i] = entries[i];
        }
        entries = (LinkedList<Entry<K, V>> []) new LinkedList[currentLength * 2];
        entries = resizedArray;
    }

    /* Returns the number of key-value pairs in the HashMap. */
    public int size() {
        return numOfEntries;
    }

    /* Returns true if the HashMap contains the KEY. */
    public boolean containsKey(K key) {
        int keyHash = hash(key);
        LinkedList<Entry<K, V>> entryList = entries[keyHash];
        if (entryList == null) {
            return false;
        }
        for (Entry<K, V> e : entryList) {
            if (e.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    public V get(K key) {
        if (containsKey(key)) {
            LinkedList<Entry<K, V>> entryList = entries[hash(key)];
            for (Entry<K, V> e : entryList) {
                if (e.key.equals(key)) {
                    return e.value;
                }
            }
        }
        return null;
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       HashMap, replace the current corresponding value with VALUE. */
    public void put(K key, V value) {
        if (((double) size() / (double) entries.length) > loadFactor) {
            resize();
        }
        if (containsKey(key)) {
            LinkedList<Entry<K, V>> entryList = entries[hash(key)];
            for (Entry<K, V> e : entryList) {
                if (e.key.equals(key)) {
                    e.value = value;
                    return;
                }
            }
        } else {
            LinkedList<Entry<K, V>> entryList = entries[hash(key)];
            if (entryList == null) {
                entryList = new LinkedList<>();
            }
            entryList.add(new Entry(key, value));
            entries[hash(key)] = entryList;
            numOfEntries += 1;
            capacity += 1;
        }
    }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    public V remove(K key) {
        if (containsKey(key)) {
            V val = get(key);
            LinkedList<Entry<K, V>> entryList = entries[hash(key)];
            for (Entry<K, V> e : entryList) {
                if (e.key.equals(key)) {
                    entryList.remove(e);
                    break;
                }
            }
            numOfEntries -= 1;
            capacity -= 1;
            return val;
        } else {
            return null;
        }
    }

    public void clear() {
        entries = (LinkedList<Entry<K, V>> []) new LinkedList[capacity()];
        numOfEntries = 0;
    }

    public boolean remove(K key, V value) {
        if (containsKey(key)) {
            LinkedList<Entry<K, V>> entryList = entries[hash(key)];
            for (Entry<K, V> e : entryList) {
                if (e.key.equals(key)) {
                    if (e.value.equals(value)) {
                        entryList.remove(e);
                        numOfEntries -= 1;
                        capacity -= 1;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("unsupported operation");
    }

    /* Returns the length of the HashMap's internal array. */
    public int capacity() {
        return capacity;
    }

    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}
