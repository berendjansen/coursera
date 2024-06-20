import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int n;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }
    
    // construct an empty deque
    public Deque() {
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("new Item cannot be null");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (oldFirst != null) {
            oldFirst.previous = first;
        }
        first.previous = null;
        if (isEmpty()) {
            last = first;
        }
        n += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("new Item cannot be null");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;
        if (oldLast != null) {
            oldLast.next = last;
        }
        if (isEmpty()) {
            first = last;
        }
        n += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (n == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.previous = null;
        }
        n -= 1;
        if (isEmpty()) {
            last = first;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (n == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = last.item;
        last = last.previous;
        if (last != null) {
            last.next = null;
        }
        n -= 1;
        if (isEmpty()) {
            first = last;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DQIterator();
    }

    private class DQIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next item in iterator");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported.");
        }
    }
    
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<>();
        // dq.printStatus();
        dq.addFirst(1);
        dq.removeFirst();
        System.out.println("Iterator:");
        for (int s : dq) {
            System.out.println(s);
        }
    }

}
