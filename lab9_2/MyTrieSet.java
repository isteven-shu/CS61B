import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
public class MyTrieSet implements TrieSet61B{
    private class Node {
        boolean isKey;
        HashMap<Character, Node> links;

        Node(boolean isKey) {
            this.isKey = isKey;
            links = new HashMap<>();
        }
    }

    Node root;
    int size;

    MyTrieSet() {
        root = new Node(false);
        size = 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     @Override
     public void add(String key) {
         if (key == null || key.length() < 1) {
            return;
         }
         Node curr = root;
         for (int i = 0, n = key.length(); i < n; i++) {
             char c = key.charAt(i);
             if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
             }
            curr = curr.map.get(c);
         }
         curr.isKey = true;
     }
     */

    @Override
    public void add(String key) {
        root = add(root, key, 0);
    }

    private Node add(Node root, String key, int d) {
        if (root == null) {
            root = new Node(false);
        }
        if (d == key.length()) {
            root.isKey = true;
            return root;
        }

        char c = key.charAt(d);
        root.links.put(c, add(root.links.get(c), key, d + 1));
        return root;
    }

    @Override
    public boolean contains(String key) {
        return contains(root, key, 0);
    }

    private boolean contains(Node root, String key, int d) {
        if (root == null) {
            return false;
        }
        if (d == key.length()) {
            return root.isKey;
        }

        char c = key.charAt(d);
        return contains(root.links.get(c), key, d + 1);
    }

    private Node get(Node root, String key, int d) {
        if (root == null) {
            return null;
        }
        if (d == key.length()) {
            return root;
        }

        char c = key.charAt(d);
        return get(root.links.get(c), key, d + 1);
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        ArrayList<String> myArrayList = new ArrayList<>();
        collect(get(root, prefix, 0), prefix, myArrayList);
        return myArrayList;
    }

    /** Collect all the keys starts from root into the list */
    private void collect(Node root, String curKey, ArrayList<String> myList) {
        if (root == null) {
            return;
        }
        if (root.isKey == true) {
            myList.add(curKey);
        }

        for (char c : root.links.keySet()) {
            collect(root.links.get(c), curKey + c, myList);
        }
    }

    /** To find the longest key that is a prefix of a given string */
    @Override
    public String longestPrefixOf(String s) {
        if (s == null)    return null;
        return longestPrefixOf(root, s, "", "");
    }

    private String longestPrefixOf(Node root, String s, String curLongestKey, String curString) {
        if (root == null || curString.length() == s.length())   return curLongestKey;
        if (root.isKey)     curLongestKey = curString;
        char c = s.charAt(curString.length());
        return longestPrefixOf(root.links.get(c), s, curLongestKey, curString + c);
    }
}
