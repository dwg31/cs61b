/**
 * Project 1a pt.2: implementing Double Ended Queue (Deque) using array.
 * @author Dawei Gu
 */

public class ArrayDeque<T> {
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;
    private float R;

    private static final int initSize = 8;
    private static final double lowerUsageLimit = 0.25;
    private static final int RFactor = 2;

    /* Constructor, creates an empty Deque. */
    private ArrayDeque() {
        items = (T[]) new Object[initSize];
        nextFirst = 7;
        nextLast = 0;
        size = 0;
        R = (float)size / items.length;
    }

    /* Creates a deep copy of other. */
    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[initSize];
        nextFirst = 7;
        nextLast = 0;
        size = 0;
        R = (float)size / items.length;
        for (int i = 0; i < other.size(); i += 1) {
            addLast((T) other.get(i));
        }
    }

    /* Move pointer to the next position */
    private int mvForward(int p) {
        return (p + 1) % items.length;
    }

    /* Move pointer to the previous position*/
    private int mvBackward(int p) {
        return (p - 1 + items.length) % items.length;
    }

    /* If array is full, increases the array size by doubling it.
     * If usage factor < 25%, reduce the array size by halving it.
     */
    private void resize(int s) {
        T[] a = (T[]) new Object[s];
        int curr = mvForward(nextFirst);

        for (int i = 0; i < size; i += 1) {
            a[i] = items[curr];
            curr = mvForward(curr);
        }
        nextFirst = s - 1;
        nextLast = size;
        items = a;
        R = (float)size / items.length;

    }

    /* Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * RFactor);
        }
        items[nextFirst] = item;
        nextFirst = mvBackward(nextFirst);
        size += 1;
        R = (float)size / items.length;
    }

    /* Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * RFactor);
        }
        items[nextLast] = item;
        nextLast = mvForward(nextLast);
        size += 1;
        R = (float)size / items.length;
    }

    /* Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /* Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /* Prints the items in the deque from first to last,
     * separated by a space. Once all the items have been
     * printed, print out a new line.
     */
    public void printDeque() {
        if (isEmpty()) {
            System.out.println("Empty deque");
        } else {
            int curr = mvForward(nextFirst);
            while (curr != nextLast) {
                System.out.print(items[curr] + " ");
                curr = mvForward(curr);
            }
        }
            System.out.println();
    }


    /* Removes and returns the item at the front of the
     * deque. If no such item exists, returns null.
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            nextFirst = mvForward(nextFirst);
            items[nextFirst] = null;
            size -= 1;
            R = (float)size / items.length;

            if (R < 0.25 && items.length >= 16) {
                resize(items.length / RFactor);
            }

            return items[mvForward(nextFirst)];
        }
    }

    /* Removes and returns the item at the back of the
     * deque. If no such item exists, returns null.
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            nextLast = mvBackward(nextLast);
            items[nextLast] = null;
            size -= 1;
            R = (float)size / items.length;

            if (R < 0.25 && items.length >= 16) {
                resize(items.length / RFactor);
            }

            return items[mvBackward(nextLast)];
        }
    }

    /* Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null.
     */
    public T get(int index) {
        assert index < size : "Index exceeding range!";
        int curr = mvForward(nextFirst);
        for (int i = 0; i < index; i += 1) {
            curr = mvForward(curr);
        }
        return items[curr];
    }
}

