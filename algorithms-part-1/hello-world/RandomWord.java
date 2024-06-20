import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        double i = 1.0;
        String champion = "Error: no stdin provided.";
        while (!StdIn.isEmpty()) {
            String nextWord = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / i)) {
                champion = nextWord;
            }
            i += 1;
        }
        System.out.println(champion);
    }
}
