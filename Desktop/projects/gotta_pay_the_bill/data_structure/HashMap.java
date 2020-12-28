import java.util.Iterator;
import java.util.LinkedList;

//Trying to push something

public class HashMap<K extends Comparable<K>, V> implements Map61BL<K, V> {
    /* Instance variables here? */
    private LinkedList<Entry<K, V>>[] entryArray;
    final double loadFactor;
    int size;

    /* Constructor */
    /* Creates a new hash map with a default array of size 16 and load factor of 0.75. */
    public HashMap() {
        entryArray = new LinkedList[16];
        loadFactor = 0.75;

    }

    /* Creates a new hash map with an array of size INITIALCAPACITY
    and default load factor of 0.75. */
    public HashMap(int initialCapacity) {
        entryArray = new LinkedList[initialCapacity];
        loadFactor = 0.75;
    }

    /* Creates a new hash map with INITIALCAPACITY and LOADFACTOR. */
    HashMap(int initialCapacity, float loadFactor) {
        entryArray = new LinkedList[initialCapacity];
        this.loadFactor = loadFactor;
    }

    /* Returns the length of this HashMap's internal array. */
    int capacity() {
        return entryArray.length;
    }


    public int size() {
        return size;
    }

    @Override
    public void clear() {
        entryArray = new LinkedList[16];
        size = 0;
    }

    @Override
    public boolean remove(K key, V value) {
        if (containsKey(key)) {
            remove(key);
            size--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new HashMapitertator();
    }

    private class HashMapitertator implements Iterator<K> {
        int arrayIndex = 0;
        int llIndex = 0;
        int count = 0;
        @Override
        public K next() {
            K toReturn = null;
            if (hasNext()) {
                count++;
                while (arrayIndex < entryArray.length) {
                    if (entryArray[arrayIndex] != null) {
                        if (llIndex < entryArray[arrayIndex].size()) {
                        //if (entryArray[arrayIndex].get(llIndex) != null) {
                            toReturn = entryArray[arrayIndex].get(llIndex).key;
                            llIndex++;
                            return toReturn;
                        } else {
                            arrayIndex += 1;
                            llIndex = 0;
                        }
                    } else {
                        arrayIndex += 1;
                        llIndex = 0;
                    }
                }
            } else {
                return toReturn;
            }
            return toReturn;
        }

        @Override
        public boolean hasNext() {
            return count < size;

        }
    }
    /* Returns true if the map contains the KEY. */
    public boolean containsKey(K key) {
        int arrayIndex = Math.floorMod(key.hashCode(), entryArray.length);
        if (entryArray[arrayIndex] == null) {
            return false;
        } else {
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
    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        } else {
            int arrayIndex = Math.floorMod(key.hashCode(), entryArray.length);
            for (int i = 0; i < entryArray[arrayIndex].size(); i++) {
                if (key.equals(entryArray[arrayIndex].get(i).key)) {
                    return entryArray[arrayIndex].get(i).value;
                }
            }
        }

        return null;
    }

    /* Puts a (KEY, VALUE) pair into this map. If the KEY already exists in the
           SimpleNameMap, replace the current corresponding value with VALUE. */
    public void put(K key, V value) {
        if (containsKey(key)) {
            int arrayIndex = Math.floorMod(key.hashCode(), entryArray.length);
            for (int i = 0; i < entryArray[arrayIndex].size(); i++) {
                if (key.equals(entryArray[arrayIndex].get(i).key)) {
                    entryArray[arrayIndex].get(i).value = value;
                }
            }
        } else {
            if (((double) (size() + 1) / (double) entryArray.length) > loadFactor) {
                LinkedList[] tempArray = new LinkedList[entryArray.length * 2];
                for (int i = 0; i < entryArray.length; i++) {
                    if (entryArray[i] != null) {
                        for (int j = 0; j < entryArray[i].size(); j++) {
                            int arrayIndex = Math.floorMod(key.hashCode(), entryArray.length * 2);
                            if (tempArray[arrayIndex] != null) {
                                Entry toAdd = new Entry(entryArray[i].get(j).key,
                                        entryArray[i].get(j).value);
                                tempArray[arrayIndex].add(toAdd);
                            } else {
                                tempArray[arrayIndex] = new LinkedList();
                                Entry toAdd = new Entry(entryArray[i].get(j).key,
                                        entryArray[i].get(j).value);
                                tempArray[arrayIndex].add(toAdd);
                            }
                        }
                    }
                }
                entryArray = tempArray;
            }

            Entry<K, V> newEntry = new Entry<K, V>(key, value);
            int arrayIndex = Math.floorMod(key.hashCode(), entryArray.length);
            int keyIndex = 0;
            if (entryArray[arrayIndex] != null) {
                entryArray[arrayIndex].add(newEntry);
                size++;
            } else {
                entryArray[arrayIndex] = new LinkedList();
                entryArray[arrayIndex].add(0, newEntry);
                size++;
            }

        }
    }


    /* Removes a single entry, KEY, from this table and return the VALUE if
           successful or NULL otherwise. */
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        } else {
            V value = get(key);
            Entry<K, V> removeEntry = new Entry<K, V>(key, value);
            int arrayIndex = Math.floorMod(key.hashCode(), entryArray.length);
            entryArray[arrayIndex].remove(removeEntry);
            size--;
            return value;
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





