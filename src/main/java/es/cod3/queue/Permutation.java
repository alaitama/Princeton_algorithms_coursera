package es.cod3.queue;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException();
        }

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            randomizedQueue.enqueue(StdIn.readString());
        }

        for (String item : randomizedQueue) {
            StdOut.println(item);
        }
    }
}