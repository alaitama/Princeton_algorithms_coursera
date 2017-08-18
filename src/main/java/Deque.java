import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int numNodes = 0;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return numNodes == 0;
    }

    // return the number of items on the deque
    public int size() {
        return numNodes;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkItem(item);

        Node newNode = new Node();
        newNode.item = item;
        newNode.next = first;
        if (first != null) {
            first.previous = newNode;
        }

        first = newNode;
        numNodes++;

        if (last == null) {
            last = first;
        }
    }

    // add the item to the end
    public void addLast(Item item) {
        checkItem(item);

        Node newNode = new Node();
        newNode.item = item;
        newNode.previous = last;
        if (last != null) {
            last.next = newNode;
        }

        last = newNode;
        numNodes++;

        if (first == null) {
            first = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException("Stack underflow");
        }

        Item itemFirst = first.item;
        first = first.next;
        if (first != null) {
            first.previous = null;
        }
        numNodes--;

        if (first == null) {
            last = first;
        }

        return itemFirst;
    }

    // remove and return the item from the end
    public Item removeLast() {

        if (last == null) {
            throw new NoSuchElementException("Stack underflow");
        }

        Item itemLast = last.item;
        last = last.previous;
        if (last != null) {
            last.next = null;
        }
        numNodes--;

        if (last == null) {
            first = last;
        }

        return itemLast;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        Iterator<Item> iterator = new Iterator<Item>() {

            private Node currentNode = first;

            public boolean hasNext() {
                return currentNode != null;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                Item currenItem = currentNode.item;
                currentNode = currentNode.next;

                return currenItem;
            }
        };

        return iterator;
    }

    private void checkItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can not be null");
        }
    }

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();

        deque.addFirst("alberto");

        StdOut.println(deque.size());
        StdOut.println(deque.removeLast());

        deque.addLast("tito");
        printDeque(deque);

        StdOut.println(deque.size());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.size());

        deque.addLast("tito");
        deque.addFirst("alberto");
        deque.addFirst("alberto1");

        printDeque(deque);
        deque.removeLast();
        StdOut.println(deque.size());
        printDeque(deque);
        deque.removeFirst();
        StdOut.println(deque.size());
        printDeque(deque);
        deque.removeFirst();
        printDeque(deque);




    }

    private static void printDeque(Deque<String> deque) {
        for (String item : deque) {
            StdOut.println(item);
        }
    }
}
