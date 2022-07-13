public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int n) {
        T[] newArray = (T[]) new Object[n];
        System.arraycopy(items, nextFirst + 1, newArray, 0, size - nextFirst);
        System.arraycopy(items, 0, newArray, size - nextFirst, nextLast);

        nextFirst = n - 1;
        nextLast = items.length;

        items = newArray;
    }

    public void addFirst(T item) {
        if(size == items.length) {
            resize(items.length * 2);
        }
        size++;
        items[nextFirst] = item;
        nextFirst--;
        if(nextFirst < 0) {
            nextFirst += items.length;
        }
    }

    public void addLast(T item) {
        if(size == items.length) {
            resize(items.length * 2);
        }
        size++;
        items[nextLast] = item;
        nextLast++;
        if(nextLast >= items.length) {
            nextFirst -= items.length;
        }
    }

    public T removeFirst() {
        if(isEmpty()) {
            return null;
        }

        nextFirst++;
        if(nextFirst >= items.length) {
            nextFirst -= items.length;
        }
        T removedFirst = items[nextFirst];
        size--;

        if(size * 4 <= items.length) {
            resize(items.length / 2);
        }

        return removedFirst;
    }

    public T removeLast() {
        if(isEmpty()) {
            return null;
        }

        nextLast--;
        if(nextLast < 0) {
            nextFirst += items.length;
        }
        T removedLast = items[nextLast];
        size--;

        if(size * 4 <= items.length) {
            resize(items.length / 2);
        }

        return removedLast;
    }

    public void printDeque() {
        if(isEmpty()) {
            return;
        }
        if(nextFirst < nextLast && nextLast - nextFirst != 1) {
            for(int i = nextFirst + 1; i < nextLast; ++i) {
                System.out.print(items[i]);
                System.out.print(' ');
            }
        }
        else {
            for(int i = nextFirst + 1; i < items.length; ++i) {
                System.out.print(items[i]);
                System.out.print(' ');
            }
            for(int i = 0; i < nextLast; ++i) {
                System.out.print(items[i]);
                System.out.print(' ');
            }
        }
    }

    public T get(int index) {
        if(nextFirst + 1 + index < items.length) {
            return items[nextFirst + 1 + index];
        }
        return items[nextFirst + 1 + index -items.length];
    }
}