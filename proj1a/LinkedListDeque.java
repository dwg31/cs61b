/**
 * Project 1a pt.1: implementing Double Ended Queue (Deque) using linked lists.
 * @author Dawei Gu
 */

public class LinkedListDeque<T> {
    /* Builds the node of a linked list */
    private class Node {
        private Node previous;
        private T item;
        private Node next;

        private Node(Node p, T i, Node n) {
            previous = p;
            item = i;
            next = n;
        }
    }

    private Node sentinel = new Node(null, null, null);
    private int size = 0;

    /* Creates an empty Deque */
    public LinkedListDeque() {
        sentinel.previous = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public LinkedListDeque(LinkedListDeque other) {
        //Node sentinel = new Node(null, null, null);
        sentinel.previous = sentinel;
        sentinel.next = sentinel;
        for (int i = 0; i < other.size(); i += 1) {
            addLast((T) other.get(i));
        }
    }

    /* Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        sentinel.next = new Node(sentinel, item, sentinel.next);
        sentinel.next.next.previous = sentinel.next;
        size += 1;
    }

    /* Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        sentinel.previous = new Node(sentinel.previous, item, sentinel);
        sentinel.previous.previous.next = sentinel.previous;
        size += 1;
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
            Node p = sentinel.next;
            for (int i = 0; i < size; i += 1) {
                System.out.print(p.item);
                p = p.next;
            }
            System.out.print("\n");
        }

    }

    /* Removes and returns the item at the front of the
     * deque. If no such item exists, returns null.
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            sentinel.next = sentinel.next.next;
            sentinel.next.previous = sentinel;
            size -= 1;
            return sentinel.next.item;
        }
    }

    /* Removes and returns the item at the back of the
     * deque. If no such item exists, returns null.
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            sentinel.previous = sentinel.previous.previous;
            sentinel.previous.next = sentinel;
            size -= 1;
            return sentinel.previous.item;
        }
    }

    /* Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth. If no such item exists,
     * returns null.
     */
    public T get(int index) {
        assert index < size : "Index exceeding range!";
        Node p = sentinel.next;
        for (int i = 0; i < index; i += 1) {
            p = p.next;
        }
        return p.item;
    }
}