package es.datastructur.synthesizer;

import java.util.Iterator;

/** Interface for all classes that implements BoundedQueue.
 *
 * @param <T>
 *
 * @author Dawei Gu
 */
public interface BoundedQueue<T> extends Iterable<T>{
    /** Return size of the buffer.
     *
     */
    int capacity();

    /** return number of items currently in the buffer.
     *
     */
    int fillCount();

    /** add item x to the end.
     *
     * @param x the item to be added.
     */
    void enqueue(T x);

    /** delete and return item from the front.
     *
     */
    T dequeue();

    /** return (but do not delete) item from the front.
     *
     */
    T peek();

    /** Returns true if the buffer is empty,
     * otherwise returns false.
     */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /** Returns true if the buffer is full (fillCount is same as capacity),
     * otherwise returns false.
     */
    default boolean isFull() {
        return fillCount() == capacity();
    }

    /** Returns an Iterator of type T. */
    Iterator<T> iterator();

}
