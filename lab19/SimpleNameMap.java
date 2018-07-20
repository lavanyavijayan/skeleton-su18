import java.util.LinkedList;

public class SimpleNameMap {

    /* Instance variables */
    private LinkedList<Entry>[] entries;
    private int numOfEntries;
    private int capacity;
    private int loadFactor;

    /* Constructs an empty SimpleNameMap with the specified initial capacity and load factor. */
    @SuppressWarnings("unchecked")
    public SimpleNameMap(int initialCapacity, int loadFactor) {
        entries = (LinkedList<Entry> []) new LinkedList[initialCapacity];
        capacity = initialCapacity;
        this.loadFactor = loadFactor;
    }

    /* Returns true if the given KEY is a valid name that starts with A - Z. */
    private static boolean isValidName(String key) {
        return 'A' <= key.charAt(0) && key.charAt(0) <= 'Z';
    }

    /* Returns a specific integer when given a key. */
    private int hash(String key) {
        //return (int) (key.charAt(0) - 'A');
        //return (key.charAt(0) - 'A') % 10;
        return Math.floorMod(key.hashCode(), 10);
    }

    /* Resizes the SimpleNameMap, by doubling its capacity. */
    @SuppressWarnings("unchecked")
    private void resize() {
        int currentLength = entries.length;
        LinkedList<Entry>[] resizedArray;
        resizedArray = (LinkedList<Entry> []) new LinkedList[currentLength * 2];
        for (int i = 0; i < currentLength; i++) {
            resizedArray[i] = entries[i];
        }
        entries = resizedArray;
    }

    /* Returns the size of the SimpleNameMap. */
    int size() {
        return numOfEntries;
    }

    /* Returns true if the map contains the KEY. */
    boolean containsKey(String key) {
        int keyHash = hash(key);
        LinkedList<Entry> entryList = entries[keyHash];
        if (entryList == null) {
            return false;
        }
        for (Entry e : entryList) {
            if (e.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    String get(String key) {
        if (containsKey(key)) {
            LinkedList<Entry> entryList = entries[hash(key)];
            String val = "";
            for (Entry e : entryList) {
                if (e.key.equals(key)) {
                    val = e.value;
                    break;
                }
            }
            return val;
        } else {
            return null;
        }
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    void put(String key, String value) {
        if (containsKey(key)) {
            if (((double) size() / (double) entries.length) > loadFactor) {
                resize();
            }
            LinkedList<Entry> entryList = entries[hash(key)];
            for (Entry e : entryList) {
                if (e.key.equals(key)) {
                    e.value = value;
                    return;
                }
            }
        } else {
            if (isValidName(key)) {
                if (((double) size() / (double) entries.length) > loadFactor) {
                    resize();
                }
                LinkedList<Entry> entryList = entries[hash(key)];
                if (entryList == null) {
                    entryList = new LinkedList<>();
                }
                entryList.add(new Entry(key, value));
                entries[hash(key)] = entryList;
                numOfEntries += 1;
                capacity += 1;
            }
        }
    }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    String remove(String key) {
        if (containsKey(key)) {
            String val = get(key);
            LinkedList<Entry> entryList = entries[hash(key)];
            for (Entry e : entryList) {
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

    private static class Entry {

        private String key;
        private String value;

        Entry(String key, String value) {
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
