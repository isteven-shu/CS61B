/** Deque based on array.
 *  @author Shuyuan Wang
 * */
public class ArrayDeque<T> implements Deque<T> {
    /** The array to hold items. */
    private T[] items;
    /** The number of elements in array. */
    private int size;
    /** Used to keep track of next first position. */
    private int nextFirst;
    /** Used to keep track of next last position. */
    private int nextLast;

    /** Used to create an empty array.
     *  Initial size is 0.
     * */
    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
    }

    /** Return if the deque is empty. */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /** Return the number of current elements. */
    @Override
    public int size() {
        return size;
    }

    /** Inner private helper.
     *  @param n the index to be plus 1
     *  @return the logically plus-1 index
     * */
    private int plusOne(int n) {
        if (n + 1 < items.length) {
            return n + 1;
        }
        return n + 1 - items.length;
    }

    /** Inner private helper.
     *  @param n the index to be minus 1
     *  @return the logically minus-1 index
     *  */
    private int minusOne(int n) {
        if (n - 1 >= 0) {
            return n - 1;
        }
        return n - 1 + items.length;
    }

    /** Grow the capacity of list to n.
     *  @param n the target size
     * */
    private void grow(int n) {
        T[] newArray = (T[]) new Object[n];
        System.arraycopy(items, plusOne(nextFirst),
                         newArray, 0,
                  size - 1 - nextFirst);
        System.arraycopy(items, 0,
                         newArray, size - 1 - nextFirst,
                  minusOne(nextLast) + 1);

        nextFirst = n - 1;
        nextLast = items.length;

        items = newArray;
    }

    /** Shrink the capacity of list to n.
     *  @param n the target size
     * */
    private void shrink(int n) {
        T[] newArray = (T[]) new Object[n];
        if (nextFirst < nextLast) {
            System.arraycopy(items, plusOne(nextFirst),
                    newArray, 0, size);
        } else {
            System.arraycopy(items, plusOne(nextFirst),
                             newArray, 0,
                      items.length - 1 - nextFirst);
            System.arraycopy(items, 0,
                             newArray, items.length - 1 - nextFirst,
                             nextLast);
        }

        nextFirst = n - 1;
        nextLast = size;

        items = newArray;
    }

    /** Resize the capacity of list to n.
     * @param n the target size
     * */
    private void resize(int n) {
        if (n > items.length) {
            grow(n);
        } else {
            shrink(n);
        }
    }

    /** Add an item to the front.
     * @param item the item to be added
     * */
    @Override
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

    /** Add an item to the end.
     *  @param item the item to be added
     * */
    @Override
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

    /** Remove the first item.
     *  @return the item gotten removed
     * */
    @Override
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

    /** Remove the last item.
     *  @return the item gotten removed
     * */
    @Override
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

    /** Print the deque in order seperated by whitespace. */
    @Override
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
        System.out.println();
    }

    /** Return the index-th element.
     * @param index the index for the requested element
     * */
    @Override
    public T get(int index) {
        if (nextFirst + 1 + index < items.length) {
            return items[nextFirst + 1 + index];
        }
        return items[nextFirst + 1 + index - items.length];
    }
}
