import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int newSize) {
        Item[] copy = (Item[]) new Object[newSize];
        for (int i = 0; i < n; i++) {
            copy[i] = this.s[i];
        }
        s = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item is not allowed");
        }
        if (n == s.length) {
            resize(2 * s.length);
        }
        s[n] = item;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("No more items in queue");
        }
        if (n == s.length / 4) {
            resize(s.length / 2);
        }
        int sampledId = StdRandom.uniformInt(0, n);
        Item sampledItem = s[sampledId];
        if (sampledId != n - 1) {
            s[sampledId] = s[n - 1];
        }
        s[n - 1] = null;
        n -= 1;
        return sampledItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("No more items in queue");
        }
        int sampledId = StdRandom.uniformInt(0, n);
        return s[sampledId];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RDIterator();
    }

    private class RDIterator implements Iterator<Item> {
        int i = 0;
        Item[] valueList;

        public RDIterator() {
            valueList = (Item[]) new Object[n];
            for (int j = 0; j < n; j++) {
                valueList[j] = s[j];
            }
            StdRandom.shuffle(valueList);
        }

        public boolean hasNext() {
            return i < n;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next item in iterator");
            }
            Item nextItem = valueList[i];
            i += 1;
            return nextItem;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported.");
        }
    }

    // private void printStatus() {
    //     System.out.printf("Current length s: %d\n", this.s.length);
    //     System.out.printf("Current N: %d\n", this.n);
    // }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(1);
        rq.sample();
        rq.dequeue();
    }
}
