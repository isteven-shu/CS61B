// https://github.com/r-k-jonynas/CS61B-solutions/blob/master/lab7/BSTMap.java#L190
import edu.princeton.cs.algs4.Stack;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {
    Entry root;
    int size;

    BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(K key) {
        return containsKeyHelper(root, key);
    }

    private boolean containsKeyHelper(Entry entry, K key) {
        if (entry == null) {
            return false;
        }

        if (entry.key.equals(key)) {
            return true;
        } else if (entry.key.compareTo(key) < 0) {
            return containsKeyHelper(entry.right, key);
        } else {
            return containsKeyHelper(entry.left, key);
        }
    }


    @Override
    public V get(K key) {
        Entry entry = getHelper(root, key);
        if (entry == null) {
            return null;
        }
        return entry.value;
    }

    /** Return the Entry object with target value */
    private Entry getHelper(Entry entry, K key) {
        if (entry == null) {
            return null;
        }

        if (entry.key.equals(key)) {
            return entry;
        } else if (entry.key.compareTo(key) < 0) {
            return getHelper(entry.right, key);
        } else {
            return getHelper(entry.left, key);
        }
    }

    @Override
    public void put(K key, V value) {
        root = putHelper(root, key, value);
    }

    /** Insert the label L into T.
     *  @return: returning the modified tree.*/
    private Entry putHelper(Entry entry, K key, V value) {
        if (entry == null) {
            size++;
            return new Entry(key, value);
        }

        if (entry.key.equals(key)) {
            entry.value = value;
        } else if (entry.key.compareTo(key) < 0) {
            entry.right = putHelper(entry.right, key, value);
        } else {
            entry.left = putHelper(entry.left, key, value);
        }
        return entry;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        keySetHelper(keys, root);
        return keys;
    }

    void keySetHelper(Set<K> keys, Entry entry) {
        if (entry == null) {
            return;
        }
        keySetHelper(keys, entry.left);
        keys.add(entry.key);
        keySetHelper(keys, entry.right);
    }

    /** return null if the argument key does not exist in the BSTMap.
     *  Otherwise, delete the key-value pair (key, value) and return value.*/
    @Override
    public V remove(K key) {
        Entry target = getHelper(root, key);
        if (target == null) return null;
        root = remove(root, key);
        return target.value;
    }

    /** Needless to change the name */
    private Entry remove(Entry entry, K key) {
        if (entry == null) {
            return null;
        }

        int cmp = entry.key.compareTo(key);
        if (cmp < 0) entry.right = remove(entry.right, key);
        if (cmp > 0) entry.left = remove(entry.left, key);

        if (cmp == 0) {
            size--;
            if (entry.left == null) return entry.right;
            if (entry.right == null) return entry.left;

            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // 放在这里不行！ 近上面那两行没有触发size--!!!!!!!!!!!!!!!!!!!!!!
            // 挪到141行了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // size--;
            Entry newRoot = max(entry.left);
            entry.value = newRoot.value;
            entry.key = newRoot.key;
            entry.left = removeMax(entry.left);
// 被注释掉的是官方做法
//            Entry originalRoot = entry;
//            entry = max(entry.left);
//            /**!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//             * Can not change the order since the second line will alter
//             *  originalRoot.left.
//             *!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!/
//            entry.left = removeMax(originalRoot.left);
//            entry.right = originalRoot.right;
        }
        // 事实证明放在这里也不行啊！！！
        // 仅仅从recursive leap of faith的角度考虑，目标key可能不存在，不一定要删除啊！
        //size--;
        return entry;
    }

    private Entry max(Entry entry) {
        if (entry.right == null) {
            return entry;
        }
        return max(entry.right);
    }

    /** Remove the max-key ndoe in entry
     *  does not alter size of the map!
     * @param the root of tree
     * @return the root of the removed tree
     */
    private Entry removeMax(Entry entry) {
        if (entry == null) {
            return null;
        }

        if (entry.right == null) {
            return entry.left;
        }
        entry.right = removeMax(entry.right);
        return entry;
    }

    @Override
    public V remove(K key, V value) {
        Entry target = getHelper(root, key);
        if (target == null) return null;
        if (target.value == value) return remove(key);
        return null;
    }

    public void printInOrder() {
        printInOrderHelper(root);
    }

    private void printInOrderHelper(Entry entry) {
        if (entry == null) {
            return;
        }

        printInOrderHelper(entry.left);
        System.out.print("[" + entry.key + ", " + entry.value + "] ");
        printInOrderHelper(entry.right);
    }

    private class Entry {
        K key;
        V value;
        Entry left;
        Entry right;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }

        /** Added additionally for remove() */
        boolean isLeaf() {
            return left == null && right == null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new keyIterator();
    }

    private class keyIterator implements Iterator<K> {
        private Stack<Entry> stack;

        keyIterator() {
            stack = new Stack<>();
            pushToLeft(root);
        }

        private void pushToLeft(Entry entry) {
            if (entry == null) {
                return;
            }
            stack.push(entry);
            pushToLeft(entry.left);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            Entry cur = stack.pop();
            pushToLeft(cur.right);
            return cur.key;
        }
    }
}
