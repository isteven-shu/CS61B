package lab9;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import edu.princeton.cs.algs4.Stack;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Shuyuan Wang
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }

        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            return p.value;
        } else if (cmp > 0) {
            return getHelper(key, p.right);
        } else {
            return getHelper(key, p.left);
        }

    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            return new Node(key, value);
        }

        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            p.value = value;
        } else if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.right = putHelper(key, value, p.right);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null key not allowed.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Null values not allowed.");
        }
        root = putHelper(key, value, root);
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
        Set<K> keys = new HashSet<>();
        keySetHelper(keys, root);
        return keys;
    }

    void keySetHelper(Set<K> keys, Node p) {
        if (p == null) {
            return;
        }
        keySetHelper(keys, p.left);
        keys.add(p.key);
        keySetHelper(keys, p.right);
    }

    private Node max(Node p) {
        if (p == null) {
            return null;
        }

        if (p.right == null) {
            return p;
        }
        return max(p.right);
    }

    private Node removeMax(Node p) {
        if (p.right == null) {
            return p.left;
        }

        p.right = removeMax(p.right);
        return p;
    }

    private Node removeHelper(K key, Node p) {
        if (p == null) {
            return null;
        }

        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = removeHelper(key, p.left);
        } else if (cmp > 0) {
            p.right = removeHelper(key, p.right);
        } else {
            size--;
            if (p.left == null) return p.right;
            if (p.right == null) return p.left;

            Node newRoot = max(p.left);
            newRoot.left = removeMax(p.left);
            newRoot.right = p.right;
            p = newRoot;

        }
        return p;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V value = get(key);
        if (value != null) {
            root = removeHelper(key, root);
        }
        return value;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V v = get(key);
        if (v == value) {
            return remove(key);
        }
        return v;
    }

    @Override
    public Iterator<K> iterator() {
        return new KeyIterator();
    }

    private class KeyIterator implements Iterator<K> {
        Stack<Node> stk;
        KeyIterator() {
            stk = new Stack<>();
            pushToLeft(root);
        }

        private void pushToLeft(Node p) {
            if (p == null) {
                return;
            }
            stk.push(p);
            pushToLeft(p.left);
        }

        @Override
        public boolean hasNext() {
            return !stk.isEmpty();
        }

        @Override
        public K next() {
            Node nextNode = stk.pop();
            pushToLeft(nextNode.right);
            return nextNode.key;
        }
    }
}
