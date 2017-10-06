package cs6301.g16;

import java.util.PriorityQueue;

public class PerfectPower implements Comparable<PerfectPower> {
    private final Long value;
    private final int a;
    private final int b;

    public PerfectPower(int a, int b) {
        this.value = (long) Math.pow(a, b);
        this.a = a;
        this.b = b;
    }

    public int compareTo(PerfectPower o) {
        return value.compareTo(o.value);
    }

    public static void main(String[] args) {

        int n = 100;

        // initialize priority queue
        PriorityQueue<PerfectPower> pq = new PriorityQueue<>();

        // maximum power representable as a long is 2^62
        for (int b = 2; b <= 62; b++) {
            pq.add(new PerfectPower(2, b));
        }

        // find smallest power, print out, and update
        int c = 0;
        long pre = 0;
        while (!pq.isEmpty() && c < n) {
            PerfectPower p = pq.remove();
            if (p.value != pre) {
                System.out.print(p.value + " ");
                c++;
            }
            pre = p.value;
            // add next perfect power if it doesn't overflow a long
            if (Math.pow(p.a + 1, p.b) < Integer.MAX_VALUE)
                pq.add(new PerfectPower(p.a + 1, p.b));
        }
    }

}