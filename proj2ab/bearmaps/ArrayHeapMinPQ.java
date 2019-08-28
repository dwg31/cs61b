package bearmaps;

import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    ArrayList<Node<T>> heap;
    Map<T, Integer> indexMap;

    static class Node<T> {
        private T item;
        private double priority;

        Node(T i, double p) {
            this.item = i;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }
    }

    ArrayHeapMinPQ() {
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

    private void swap(int c, int p) {
        Collections.swap(heap, c, p);
        indexMap.put(heap.get(c).getItem(), c);
        indexMap.put(heap.get(p).getItem(), p);
    }

    private void swim(int c) {
        while (c > 0 && smaller(c, parent(c))) {
            int p = parent(c);
            swap(c, p);
            c = p;
        }
    }

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

    private boolean smaller(int c, int p) {
        return heap.get(c).getPriority() < heap.get(p).getPriority();
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }
}