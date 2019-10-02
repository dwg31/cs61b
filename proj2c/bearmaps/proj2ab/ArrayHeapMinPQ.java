package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Collections;

/**
 * Using ArrayList to implement heap minimum priority queue.
 *
 * @author Dawei Gu
 */

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /** ArrayList enabled heap. */
    private ArrayList<Node<T>> heap;
    /** a HashMap that stores item and item position
     * in the heap entries.
     */
    private Map<T, Integer> indexMap;

    /** every Node stores the item and its priority. */
    private static class Node<T> {
        /** item of any type. */
        private T item;
        /** priority of this item. */
        private double priority;

        /** default constructor.
         *
         * @param i item
         * @param p priority
         */
        Node(T i, double p) {
            this.item = i;
            this.priority = p;
        }

        /**
         * @return the item in this Node.
         */
        T getItem() {
            return item;
        }

        /**
         * @return the priority of the item in this Node.
         */
        double getPriority() {
            return priority;
        }
    }

    /** default constructor. */
    public ArrayHeapMinPQ() {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        heap.add(new Node<>(item, priority));
        indexMap.put(item, size());
        swim(size() - 1);
    }

    @Override
    public boolean contains(T item) {
        return indexMap.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return heap.get(0).getItem();
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        T returnItem = getSmallest();
        swap(0, size() - 1);
        heap.remove(size() - 1);
        indexMap.remove(returnItem);
        sink(0);
        return returnItem;
    }

    @Override
    public int size() {
        return indexMap.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int i = indexMap.get(item);
        heap.set(i, new Node<>(item, priority));
        if (smaller(i, parent(i))) {
            swim(i);
        } else {
            sink(i);
        }
    }

    /** swap the position of two items in the heap.
     *
     * @param c the index of the first item.
     * @param p the index of the second item.
     */
    private void swap(int c, int p) {
        Collections.swap(heap, c, p);
        indexMap.put(heap.get(c).getItem(), c);
        indexMap.put(heap.get(p).getItem(), p);
    }

    /** swim item up if the priority of this item is
     * smaller than its parent.
     *
     * @param c the index of the current item
     */
    private void swim(int c) {
        while (c > 0 && smaller(c, parent(c))) {
            int p = parent(c);
            swap(c, p);
            c = p;
        }
    }

    /** sink item down if the priority of this item
     * greater than either of its children.
     *
     * @param p the index of the current item
     */
    private void sink(int p) {
        while (leftChild(p) < size()) {
            int left = leftChild(p);
            int right = rightChild(p);
            int c = left;
            if (right <= size() - 1 && smaller(right, left)) {
                c = right;
            }
            if (smaller(p, c)) {
                break;
            }
            swap(c, p);
            p = c;
        }
    }

    /**
     * @param c the index of the first item
     * @param p the index of the second item
     * @return true if the priority of the first item is smaller
     *         than that of the second.
     */
    private boolean smaller(int c, int p) {
        return heap.get(c).getPriority() < heap.get(p).getPriority();
    }

    /**
     * @param i the index of the given item.
     * @return the index of the parent of the given item.
     */
    private int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * @param i the index of the given item.
     * @return the index of the left child of the given item.
     */
    private int leftChild(int i) {
        return 2 * i + 1;
    }

    /**
     * @param i the index of the given item.
     * @return the index of the right child of the given item.
     */
    private int rightChild(int i) {
        return 2 * i + 2;
    }

    /**
     * @return true if every item's priority is less than or
     * equal to both of its children.
     *
     * this method is DESTRUCTIVE.
     */
    boolean priorityCheck() {
        boolean checked = true;
        while (size() > 1) {
            double parentPriority = heap.get(0).getPriority();
            removeSmallest();
            double childPriority = heap.get(0).getPriority();
            checked &= parentPriority <= childPriority;
        }
        return checked;
    }
}
