package es.datastructur.synthesizer;
import java.util.Iterator;


/** ArrayRingBuffer class implements the BoundedQueue interface
 *  by using array.
 *
 * @author Dawei Gu
 * @param <T>
 */
public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /** Index for the next dequeue or peek. */
    private int first;
    /** Index for the next enqueue. */
    private int last;
    /** Variable for the fillCount. */
    private int fillCount;
    /** Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     *
     * @param capacity The capacity for the initiated Buffer.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Helper method that increments "first" and "last" variables.
     *
     * @param i integer variable.
     * @return the incremented integer variable.
     *
     */
    public int increment(int i) {
        if (i + 1 > capacity() - 1) {
            return 0;
        } else {
            return i + 1;
        }
    }

    /**
     * Helper method that decrements "first" and "last" variables.
     *
     * @param i integer variable.
     * @return the incremented integer variable.
     */
    public int decrement(int i) {
        if (i - 1 < 0) {
            return capacity() - 1;
        } else {
            return i - 1;
        }
    }


    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer overflow");
        }

        rb[last] = x;
        fillCount += 1;
        last = increment(last);
    }

    /**
     * Return size of the buffer.
     */
    @Override
    public int capacity() {
        return rb.length;
    }

    /**
     * return number of items currently in the buffer.
     */
    @Override
    public int fillCount() {
        return fillCount;
    }


    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     *
     * @return The oldest item in the ring buffer.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow");
        }

        T returnItem = rb[decrement(last)];
        first = increment(first);
        fillCount -= 1;

        return returnItem;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        return rb[first];
    }

}
