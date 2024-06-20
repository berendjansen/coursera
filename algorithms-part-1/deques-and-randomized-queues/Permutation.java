/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        if (args.length != 0) {
            int k = Integer.parseInt(args[0]);
            if (k > 0) {
                RandomizedQueue<String> rq = new RandomizedQueue<String>();
                for (int i = 0; i < k; i++) {
                    rq.enqueue(StdIn.readString());
                }
                double counter = k + 1;
                while (!StdIn.isEmpty()) {
                    String in = StdIn.readString();
                    if (StdRandom.bernoulli(k / counter)) {
                        rq.dequeue();
                        rq.enqueue(in);
                    }
                    counter++;
                }
                Iterator<String> rqIt = rq.iterator();
                while (rqIt.hasNext()) {
                    System.out.println(rqIt.next());
                }
            }
        }
    }
}
