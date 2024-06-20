import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n;
    private Item[] s;
    private int tail;
    private int head;

    // construct an empty deque
    public Deque() {
        this.n = 0;
        this.s = (Item[]) new Object[1];
        this.tail = 0;
        this.head = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.n;
    }

    private void resize(int newSize) {
        Item[] copy = (Item[]) new Object[newSize];
        for (int i = 0; i < n; i++) {
            copy[i] = s[(head + i) % s.length];
        }
        s = copy;
        head = 0;
        tail = n - 1;
    }

    private int negMod(int i, int mod) {
        return (((i % mod) + mod) % mod);
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("new Item cannot be null");
        }
        if (n == s.length) {
            resize(s.length * 2);
        }
        head = negMod(head - 1, s.length);
        s[head] = item;
        n += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("new Item cannot be null");
        }
        if (n == s.length) {
            resize(s.length * 2);
        }
        tail = (tail + 1) % s.length;
        s[tail] = item;
        n += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (n == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        if (n > 0 && n == s.length / 4) resize(s.length / 2);
        Item item = s[head];
        s[head] = null;
        this.head = (head + 1) % s.length;
        n -= 1;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (n == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        if (n > 0 && n == s.length / 4) resize(s.length / 2);
        Item item = s[tail];
        s[tail] = null;
        tail = negMod(tail - 1, s.length);
        n -= 1;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DQIterator();
    }

    private class DQIterator implements Iterator<Item> {
        private int j = 0;
        private int i = head;

        public boolean hasNext() {
            return j < n;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next item in iterator");
            }
            Item item = s[i];
            i = (i + 1) % s.length;
            j += 1;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported.");
        }
    }

    // private void printStatus() {
    //     // System.out.println(Arrays.toString(s));
    //     System.out.printf("Current length s: %d\n", this.s.length);
    //     System.out.printf("Current N: %d\n", this.n);
    //     System.out.printf("Current head: %d\n", this.head);
    //     System.out.printf("Current tail: %d\n", this.tail);
    // }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();
        // dq.printStatus();
        dq.addFirst(2);
        dq.addFirst(3);
        dq.addFirst(4);
        dq.removeFirst();
        dq.removeFirst();
        System.out.println(dq.removeFirst());
        // System.out.println(dq.removeFirst());


    }

}
