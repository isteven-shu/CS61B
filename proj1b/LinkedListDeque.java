/** Deque based on doubly linked list.
 * @author Shuyuan Wang
 * */
public class LinkedListDeque<T> implements Deque<T> {
    private class Node {
        private T item;
        private Node next;
        private Node prev;

        /** Constructor for normal nodes */
        Node(T i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
        }

        /** Constructor for the sentinel node */
        Node() {
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

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void addFirst(T item) {
        size++;
        Node originalHead = sentinel.next;
        Node newHead = new Node(item, originalHead, sentinel);
        sentinel.next = newHead;
        originalHead.prev = newHead;
    }

    @Override
    public void addLast(T item) {
        size++;
        Node originalEnd = sentinel.prev;
        Node newEnd = new Node(item, sentinel, originalEnd);
        originalEnd.next = newEnd;
        sentinel.prev = newEnd;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item);
            System.out.print(' ');
            p = p.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T temp = sentinel.next.item;
        /* 这里一开始顺序写反了！
         sentinel.next = sentinel.next.next; */
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return temp;
    }

    @Override
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

    @Override
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
