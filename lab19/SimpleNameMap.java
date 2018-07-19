import java.util.LinkedList;
import java.lang.reflect.Array;

public class SimpleNameMap {

    /* Instance variables here*/
    private int size;
    private LinkedList<Entry>[] entries;

    /* Constructs an empty SimpleNameMap with initial capacity of 26. */
    @SuppressWarnings("unchecked")
    public SimpleNameMap() {
        entries = (LinkedList<Entry> []) new LinkedList[26];
    }

    /* Returns true if the given KEY is a valid name that starts with A - Z. */
    private static boolean isValidName(String key) {
        return 'A' <= key.charAt(0) && key.charAt(0) <= 'Z';
    }

    /* Returns a specific integer when given a key. */
    private int hash(String key) {
        return (int) (key.charAt(0) - 'A');
    }

    /* Returns true if the map contains the KEY. */
    boolean containsKey(String key) {
        int keyHash = hash(key);
        LinkedList<Entry> entryList = entries[keyHash];
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
            LinkedList<Entry> entryList = entries[hash(key)];
            for (Entry e : entryList) {
                if (e.key.equals(key)) {
                    e.value = value;
                    break;
                }
            }
        } else {
            if (isValidName(key)) {
                entries[hash(key)].add(new Entry(key, value));
                size += 1;
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
                    e = null;
                    break;
                }
            }
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
