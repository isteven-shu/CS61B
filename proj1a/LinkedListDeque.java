public class LinkedListDeque<T> {
    private class Node {
        private T item;
        private Node next;
        private Node prev;

        public Node(T i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
        }

        /** Constructor for sentinel node */
        public Node() {
            next = this;
            prev = this;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node();
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T item) {
        size++;
        Node originalHead = sentinel.next;
        Node newHead = new Node(item, originalHead, sentinel);
        sentinel.next = newHead;
        originalHead.prev = newHead;
    }

    public void addLast(T item) {
        size++;
        Node originalEnd = sentinel.prev;
        Node newEnd = new Node(item, sentinel, originalEnd);
        originalEnd.next = newEnd;
        sentinel.prev = newEnd;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item);
            System.out.print(' ');
            p = p.next;
        }
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T temp = sentinel.next.item;
        // 这里一开始顺序写反了！
        // sentinel.next = sentinel.next.next;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return temp;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        T temp = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return temp;
    }

    public T get(int index) {
        if (size <= index) {
            return null;
        }

        Node p = sentinel;
        for (int i = 0; i <= index; ++i) {
            p = p.next;
        }
        return p.item;
    }

    private T getRecursiveHelper(int index, Node p) {
        if (index == 0) {
            return p.item;
        }
        return getRecursiveHelper(index - 1, p.next);
    }

    public T getRecursive(int index) {
        if (size <= index) {
            return null;
        }
        return  getRecursiveHelper(index, sentinel.next);
    }
}
