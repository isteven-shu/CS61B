package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Shuyuan Wang
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }
    public MyHashMap(int bucketNum) {
        buckets = new ArrayMap[bucketNum];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int idx = hash(key);
        return buckets[idx].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        int idx = hash(key);
        size -= buckets[idx].size();
        buckets[idx].put(key, value);
        size += buckets[idx].size();

        if (loadFactor() > MAX_LF) {
            resize(buckets.length * 2);
        }
    }

    private void resize(int n) {
        ArrayMap<K, V>[] originalBuckets = buckets;
        buckets = new ArrayMap[n];
        for (int i = 0; i < buckets.length; ++i) {
            buckets[i] = new ArrayMap<>();
        }

        for (ArrayMap<K, V> bucket : originalBuckets) {
            for (K key : bucket) {
                int idx = hash(key);
                buckets[idx].put(key, bucket.get(key));
            }
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keyset = new HashSet<>();
        for (ArrayMap<K, V> bucket : buckets) {
            for (K key : bucket) {
                keyset.add(key);
            }
        }
        return keyset;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        int idx = hash(key);
        size -= buckets[idx].size();
        V value = buckets[idx].remove(key);
        size += buckets[idx].size();
        return value;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        V actualValue = get(key);
        if (actualValue.equals(value)) {
            return remove(key);
        }
        return actualValue;
    }

    @Override
    public Iterator<K> iterator() {
        return new keyIterator();
    }

    private class keyIterator implements Iterator<K>{
        Iterator<K> myIterator;
        int idx;
        keyIterator() {
            idx = 0;
            myIterator = buckets[idx].iterator();
        }

       @Override
        public boolean hasNext() {
            if (myIterator.hasNext()) {
                return true;
            }

            do {
                idx++;
                myIterator = buckets[idx].iterator();
            } while (idx < buckets.length && !myIterator.hasNext());

            return myIterator.hasNext();
        }

        @Override
        public K next() {
            return myIterator.next();
        }
    }
}
