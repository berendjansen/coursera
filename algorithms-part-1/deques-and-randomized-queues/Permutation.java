/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = getK(args);
        if (k > 0) {
            RandomizedQueue<String> RandomizedQueue = getAndFillQueue(k);
            performIterations(k, RandomizedQueue);
            printQueue(RandomizedQueue);
        }
    }

    private static int getK(String[] args) {
        if (args.length == 1) {
            return Integer.parseInt(args[0]);
        }
        throw new IllegalArgumentException("Only a single integer input argument is allowed");
    }

    private static RandomizedQueue<String> getAndFillQueue(int k) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        for (int i = 0; i < k && !StdIn.isEmpty(); i++) {
            rq.enqueue(StdIn.readString());
        }
        return rq;
    }

    private static void performIterations(int k, RandomizedQueue<String> rq) {
        double counter = k + 1;
        while (!StdIn.isEmpty()) {
            String in = StdIn.readString();
            if (StdRandom.bernoulli(k / counter)) {
                rq.dequeue();
                rq.enqueue(in);
            }
            counter++;
        }
    }

    private static void printQueue(RandomizedQueue<String> rq) {
        for (String s : rq) {
            System.out.println(s);
        }
    }
}
