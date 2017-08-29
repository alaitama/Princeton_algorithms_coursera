package es.cod3.queue;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items = null;
    private int numItems = 0;
    private int enqueuePosition = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[5];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return numItems == 0;
    }

    // return the number of items on the queue
    public int size() {
        return numItems;
    }

    // add the item
    public void enqueue(Item item) {
        checkItem(item);

        if (enqueuePosition == items.length) {
            resizeItems(items.length * 2);
        }

        items[enqueuePosition] = item;
        enqueuePosition++;
        numItems++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (numItems == 0) {
            throw new NoSuchElementException();
        }

        int randomPosition = getValidRandomPosition();
        Item item = items[randomPosition];
        items[randomPosition] = null;
        numItems--;

        if (numItems > 0 && numItems == items.length / 4) {
            resizeItems(items.length / 2);
        }
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (numItems == 0) {
            throw new NoSuchElementException();
        }

        return items[getValidRandomPosition()];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator<Item>();
    }

    private int getValidRandomPosition() {
        int randomPosition;
        do {
            randomPosition = StdRandom.uniform(0, enqueuePosition);
        } while (items[randomPosition] == null);

        return randomPosition;
    }

    private void checkItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    private void resizeItems(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];
        int newEnqueuePosition = 0;

        for (int i = 0; i < enqueuePosition; i++) {
            if (items[i] != null) {
                newItems[newEnqueuePosition] = items[i];
                newEnqueuePosition++;
            }
        }

        items = newItems;
        enqueuePosition = newEnqueuePosition;
    }

    private class RandomizedIterator<Item> implements Iterator<Item> {

        private final Item[] randomizedItems = (Item[]) new Object[numItems];
        private int currentPos = 0;

        public RandomizedIterator() {

            currentPos = 0;
            for (int i = 0; i < enqueuePosition; i++) {
                if (items[i] != null) {
                    randomizedItems[currentPos] = (Item) items[i];
                    currentPos++;
                }
            }

            StdRandom.shuffle(randomizedItems);
            currentPos = 0;
        }

        public boolean hasNext() {
            return currentPos < randomizedItems.length;
        }

        public Item next() {
            if (currentPos == randomizedItems.length) {
                throw new NoSuchElementException();
            }

            Item item = randomizedItems[currentPos];
            currentPos++;

            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        

        randomizedQueue.enqueue("alberto");

        StdOut.println(randomizedQueue.size());
        StdOut.println(randomizedQueue.dequeue());

        randomizedQueue.enqueue("tito");
        printIterator(randomizedQueue);

        StdOut.println(randomizedQueue.size());
        StdOut.println(randomizedQueue.dequeue());
        StdOut.println(randomizedQueue.size());

        randomizedQueue.enqueue("tito");
        randomizedQueue.enqueue("alberto");
        randomizedQueue.enqueue("alberto1");

        printIterator(randomizedQueue);
        randomizedQueue.dequeue();
        StdOut.println(randomizedQueue.size());
        printIterator(randomizedQueue);
        randomizedQueue.dequeue();
        StdOut.println(randomizedQueue.size());
        printIterator(randomizedQueue);
        randomizedQueue.dequeue();
        printIterator(randomizedQueue);
    }

    private static void printIterator(RandomizedQueue<String> randomizedQueue) {
        for (String item : randomizedQueue) {
            StdOut.println(item);
        }
    }

}