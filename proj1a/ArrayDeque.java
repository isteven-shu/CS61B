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

    private int plusOne(int n) {
        if(n + 1 < items.length) {
            return n + 1;
        }
        return n + 1 - items.length;
    }

    private int minusOne(int n) {
        if(n - 1 >= 0) {
            return n - 1;
        }
        return n - 1 + items.length;
    }

    private void grow(int n) {
        T[] newArray = (T[]) new Object[n];
        System.arraycopy(items, plusOne(nextFirst), newArray, 0, size - 1 - nextFirst);
        System.arraycopy(items, 0, newArray, size - 1 - nextFirst, minusOne(nextLast) + 1);

        nextFirst = n - 1;
        nextLast = items.length;

        items = newArray;
    }

    private void shrink(int n) {
        T[] newArray = (T[]) new Object[n];
        if (nextFirst < nextLast) {
            System.arraycopy(items, plusOne(nextFirst), newArray, 0, size);
        } else {
            System.arraycopy(items, plusOne(nextFirst), newArray, 0, items.length - 1 - nextFirst);
            System.arraycopy(items, 0, newArray, items.length - 1 - nextFirst, nextLast);
        }

        nextFirst = n - 1;
        nextLast = size;

        items = newArray;
    }

    private void resize(int n) {
        if (n > items.length) {
            grow(n);
        } else {
            shrink(n);
        }
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        size++;
        items[nextFirst] = item;
        nextFirst--;
        if (nextFirst < 0) {
            nextFirst += items.length;
        }
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        size++;
        items[nextLast] = item;
        nextLast++;
        if (nextLast >= items.length) {
            nextLast -= items.length;
        }
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        nextFirst++;
        if (nextFirst >= items.length) {
            nextFirst -= items.length;
        }
        T removedFirst = items[nextFirst];
        size--;

        if (size * 4 <= items.length && items.length > 8) {
            resize(items.length / 2);
        }

        return removedFirst;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        nextLast--;
        if (nextLast < 0) {
            nextLast += items.length;
        }
        T removedLast = items[nextLast];
        size--;

        if (size * 4 <= items.length && items.length > 8) {
            resize(items.length / 2);
        }

        return removedLast;
    }

    public void printDeque() {
        if (isEmpty()) {
            return;
        }
        if (nextFirst < nextLast && nextLast - nextFirst != 1) {
            for (int i = nextFirst + 1; i < nextLast; ++i) {
                System.out.print(items[i]);
                System.out.print(' ');
            }
        } else {
            for (int i = nextFirst + 1; i < items.length; ++i) {
                System.out.print(items[i]);
                System.out.print(' ');
            }
            for (int i = 0; i < nextLast; ++i) {
                System.out.print(items[i]);
                System.out.print(' ');
            }
        }
    }

    public T get(int index) {
        if (nextFirst + 1 + index < items.length) {
            return items[nextFirst + 1 + index];
        }
        return items[nextFirst + 1 + index - items.length];
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> myDeque = new  ArrayDeque<Integer>();
        for(int i = 0; i < 8; ++i) {
            myDeque.addLast(i);
        }

        int x = myDeque.get(0);

        for(int i = 8; i < 16; ++i) {
            myDeque.addLast(i);
        }
        for(int i = 0; i < 16; ++i) {
            int a = myDeque.removeFirst();
            System.out.print(a);
            System.out.print(' ');
        }
        myDeque.addLast(1);
        int xx = 10;
        return;
    }
}
