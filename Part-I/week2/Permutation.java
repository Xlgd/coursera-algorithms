import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        String item;
        RandomizedQueue<String> testQueue = new RandomizedQueue<String>();
        int i = 0;
        while (!StdIn.isEmpty()) {
            item = StdIn.readString();
            testQueue.enqueue(item);
            i++;
        }
        if (k < 0 || k > i) {
            throw new IllegalArgumentException("k isn't a legal argument");
        }
        for (int j = 0; j < k; j++) {
            System.out.println(testQueue.sample());
        }

    }
}
