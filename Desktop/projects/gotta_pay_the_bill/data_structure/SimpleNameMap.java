import java.util.Arrays;
import java.util.LinkedList;

public class SimpleNameMap {

    /* Instance variables here? */
    private LinkedList<Entry>[] entryArray;

    /* Constructor */
    public SimpleNameMap() {
        entryArray = new LinkedList[26];
    }

    /* Returns true if the given KEY is a valid name that starts with A - Z. */
    private static boolean isValidName(String key) {
        return 'A' <= key.charAt(0) && key.charAt(0) <= 'Z';
    }

    int size() {
        int size = 0;
        for (int i = 0; i < entryArray.length; i++) {
            size += entryArray[i].size();
        }
        return size;
    }

    /* Returns true if the map contains the KEY. */
    boolean containsKey(String key) {
        if (!isValidName(key)) {
            //System.out.println("Invalid name");
            return false;
        } else {
            int arrayIndex = Math.floorMod(key.hashCode(), entryArray.length);
            for (int i = 0; i < entryArray[arrayIndex].size(); i++) {
                if (key.equals(entryArray[arrayIndex].get(i).key)) {
                    return true;
                }
            }
            return false;
        }

    }

    /* Returns the value for the specified KEY. If KEY is not found, return
       null. */
    String get(String key) {
        if (!containsKey(key)) {
            return null;
        } else {
            int arrayIndex = Math.floorMod(key.hashCode(), entryArray.length);
            for (int i = 0; i < entryArray[arrayIndex].size(); i++) {
                if (key.equals(entryArray[arrayIndex].get(i).key)) {
                    return entryArray[arrayIndex].get(i).value;
                }
            }
            return null;
        }
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
       SimpleNameMap, replace the current corresponding value with VALUE. */
    void put(String key, String value) {
        if (containsKey(key)) {
            int arrayIndex = Math.floorMod(key.hashCode(), entryArray.length);
            for (int i = 0; i < entryArray[arrayIndex].size(); i++) {
                if (key.equals(entryArray[arrayIndex].get(i).key)) {
                    entryArray[arrayIndex].get(i).value = value;
                }
            }

        } else {
            if ((size() + 1) / entryArray.length >= 0.75) {
                entryArray = Arrays.copyOf(entryArray, entryArray.length * 2);
            }

            Entry newEntry = new Entry(key, value);
            int arrayIndex = Math.floorMod(key.hashCode(), entryArray.length);
            int keyIndex = 0;
            for (int i = 0; i < entryArray[arrayIndex].size(); i++) {
                if (entryArray[arrayIndex].get(i).key.compareTo(key) < 0) {
                    keyIndex += 1;
                }

            }
            entryArray[arrayIndex].add(keyIndex, newEntry);
        }
    }

    /* Removes a single entry, KEY, from this table and return the VALUE if
       successful or NULL otherwise. */
    String remove(String key) {
        if (!containsKey(key)) {
            return null;
        } else {
            String value = get(key);
            Entry removeEntry = new Entry(key, value);
            int arrayIndex = Math.floorMod(key.hashCode(), entryArray.length);
            entryArray[arrayIndex].remove(removeEntry);
            return value;
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
