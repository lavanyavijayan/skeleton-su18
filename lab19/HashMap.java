import java.util.LinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ListIterator;
import java.util.List;
import java.util.ArrayList;

public class HashMap<K, V> implements Map61BL<K, V>, Iterable<K> {

    /* Instance variables */
    private LinkedList<Entry<K, V>>[] entries;
    private int numOfEntries;
    //private int capacity;
    private double loadFactor;

    /* Constructs a new HashMap with a default capacity of 16 and load factor of 0.75. */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public HashMap() {
        entries = new LinkedList[16];
        //capacity = 16;
        this.loadFactor = 0.75;
    }

    /* Constructs a new HashMap with the specified INITIALCAPACITY
    and default load factor of 0.75. */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public HashMap(int initialCapacity) {
        entries = new LinkedList[initialCapacity];
        //capacity = initialCapacity;
        this.loadFactor = 0.75;
    }

    /* Constructs a new HashMap with the specified INITIALCAPACITY and LOADFACTOR. */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public HashMap(int initialCapacity, double loadFactor) {
        entries = new LinkedList[initialCapacity];
        //capacity = initialCapacity;
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

    /* Returns a specific integer when given a key and an array. */
    private int hash(K key, int arrayLength) {
        return Math.floorMod(key.hashCode(), arrayLength);
    }

    /* Resizes the HashMap, by doubling its capacity. */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void resize() {
        int currentLength = entries.length;
        //capacity += currentLength;
        LinkedList<Entry<K, V>>[] resizedArray = new LinkedList[currentLength * 2];
        for (LinkedList<Entry<K, V>> oldList : entries) {
            if (oldList != null) {
                for (Entry<K, V> e : oldList) {
                    int resizedIndex = hash(e.key, currentLength * 2);
                    LinkedList<Entry<K, V>> newList = resizedArray[resizedIndex];
                    if (newList == null) {
                        newList = new LinkedList<>();
                    }
                    newList.add(new Entry(e.key, e.value));
                    resizedArray[resizedIndex] = newList;
                }
            }
        }
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
        if (containsKey(key)) {
            LinkedList<Entry<K, V>> entryList = entries[hash(key)];
            for (Entry<K, V> e : entryList) {
                if (e.key.equals(key)) {
                    e.value = value;
                    return;
                }
            }
        } else {
            if (((double) (size() + 1) / (double) entries.length) > loadFactor) {
                resize();
            }
            int position = hash(key);
            LinkedList<Entry<K, V>> entryList = entries[position];
            if (entryList == null) {
                entryList = new LinkedList<>();
            }
            entryList.add(new Entry<>(key, value));
            entries[position] = entryList;
            numOfEntries += 1;
            //capacity += 1;
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
            //capacity -= 1;
            return val;
        } else {
            return null;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void clear() {
        int currentCapacity = entries.length;
        entries = new LinkedList[currentCapacity];
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
                        //capacity -= 1;
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
        return new HashMapIterator();
    }

    /* Returns the length of the HashMap's internal array. */
    public int capacity() {
        return entries.length;
    }

    public class HashMapIterator implements Iterator<K> {

        private final List<ListIterator<Entry<K, V>>> iterators;
        private int index;

        public HashMapIterator() {
            iterators = new ArrayList<>();
            if (entries != null) {
                for (int i = 0; i < entries.length; i++) {
                    if (entries[i] != null) {
                        iterators.add(entries[i].listIterator());
                    }
                }
            }
        }

        public boolean hasNext() {
            if (iterators.size() == 0 || index >= iterators.size()) {
                return false;
            } else { // iterators.size() > 0 && index || iterators.size()
                return iterators.get(index).hasNext();
            }
        }

        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException("HashMap ran out of keys.");
            } else {
                K keyToReturn = iterators.get(index).next().key;
                if (index < iterators.size()) {
                    if (!iterators.get(index).hasNext()) {
                        index += 1;
                    }
                }
                return keyToReturn;
            }
        }
    }

    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry<K, V> other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry<K, V>) other).key)
                    && value.equals(((Entry<K, V>) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}
