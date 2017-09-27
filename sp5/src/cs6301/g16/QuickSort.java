/**
 * <h1>Fall 2017 Short Project 4-2</h1>
 * <p>
 * Compare the performance of the two versions of partition discussed in class
 * on the running time of Quick sort, on arrays with distinct elements.
 * Try arrays that are randomly ordered (by shuffle) and arrays in
 * descending order.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-27
 */
package cs6301.g16;

import java.util.Arrays;
import java.util.Random;

import cs6301.g00.Timer;

public class QuickSort {

    /**
     * Helper function to exchange 2 elements in the given array
     * Utilizing XOR exchange algorithm
     *
     * @param A - input array
     * @param i - element index 1
     * @param j - element index 2
     */
    private static void exchange(int[] A, int i, int j) {
//        A[i] = A[i] ^ A[j];
//        A[j] = A[i] ^ A[j];
//        A[i] = A[i] ^ A[j];
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    /**
     * First version of partition discussed in class
     *
     * @param A - input array
     * @param p - prior index
     * @param r - rear index
     * @return - pivot index after partition
     */
    private static int partition1(int[] A, int p, int r) {

        // Select i uniformly at random in [p...r]
        Random rand = new Random();
        int i = p + rand.nextInt(r-p+1);

        // Put pivot element to last
        exchange(A, i, r);
        // set pivot value for comparing
        int x = A[r];

        // LI: A[p...i]≤x, A[i+1...j−1] > x,
        //     A[j...r−1] is unprocessed, A[r] = x.
        i = p - 1;
        for (int j = p; j < r; j++) {
            if (A[j] < x) {
                i = i + 1;
                exchange(A, i, j);
            }
        }

        // Bring pivot back to the middle
        exchange(A, i + 1, r);
        // A[p...i] ≤x, A[i+1] = x, A[i+2...r] > x

        return i + 1;
    }

    /**
     * Second version of partition given by Hoare
     *
     * @param A - input array
     * @param p - prior index
     * @param r - rear index
     * @return - pivot index after partition
     */
    private static int partition2(int[] A, int p, int r) {
        // Select pivot x uniformly from A[p...r]
        Random rand = new Random();
        int idx = p+rand.nextInt(r-p+1);
        int x = A[idx];

        int i = p;
        int j = r;

//        System.out.printf("Before partition: p=%d r=%d idx=%d x=%d",p,r,idx,x);
//        printArray(A);

        // LI:A[p...i] ≤ x, A[j...r] ≥ x
        while (true) {

            while (A[i] < x)
                i++;

            while (A[j] > x)
                j--;
            if (i >= j) {
//                System.out.printf("After partition: return=%d",j);
//                printArray(A);
                return j;
            }
            exchange(A, i, j);
        }
    }

    /**
     * Helper function for quickSort1
     *
     * @param A - input array
     * @param p - prior index
     * @param r - rear index
     */
    private static void quickSort1(int[] A, int p, int r) {
        if (p < r) {
            int q = partition1(A, p, r);
            quickSort1(A, p, q - 1);
            quickSort1(A, q + 1, r);
        }
    }

    /**
     * Helper function for quickSort2
     *
     * @param A - input array
     * @param p - prior index
     * @param r - rear index
     */
    private static void quickSort2(int[] A, int p, int r) {
        if (p < r) {
            int q = partition2(A, p, r);
            quickSort2(A, p, q - 1);
            quickSort2(A, q, r);
        }
    }

    /**
     * Quick sort algorithm utilizing partition1
     *
     * @param A - input array
     */
    public static void quickSort1(int[] A) {
        quickSort1(A, 0, A.length - 1);
    }

    /**
     * Quick sort algorithm utilizing partition2
     *
     * @param A - input array
     */
    public static void quickSort2(int[] A) {
        quickSort2(A, 0, A.length - 1);
    }

    /**
     * Helper functions for testing
     */

    /**
     * Print an array
     **/
    private static void printArray(int[] A) {
        if(A.length>100) {
            System.out.print("[");
            for (int i = 0; i < 5; i++) {
                System.out.print(A[i]+" ");
            }
            System.out.print("...");
            for (int i = A.length-6; i < A.length; i++) {
                System.out.print(" "+A[i]);
            }
            System.out.print("]\n");
        }
        else
            System.out.println(Arrays.toString(A));
    }

    /**
     * Returns a random shuffling of the array.
     */
    private static int[] shuffle(int[] nums) {
        int[] rand = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int r = (int) (Math.random() * (i + 1));
            rand[i] = rand[r];
            rand[r] = nums[i];
        }
        return rand;
    }

    /**
     * Main function for testing
     *
     * @param args
     */
    public static void main(String[] args) {

        // Test against array with random order
        {
            System.out.println("\n==========================");

            int length = 10000000;
            int[] A = new int[length];
            for (int i = 0; i < length; i++) {
                A[i] = i + 1;
            }
            A = shuffle(A);
            System.out.println("Test 1 - Shuffled Input Array:");
            printArray(A);
            int[] B = Arrays.copyOf(A, length);

            System.out.println("==========================\n");

            System.out.println("Quick Sort with Partition Algorithm 1:");
            Timer timer1 = new Timer();
            quickSort1(A);
            timer1.end();
            printArray(A);
            System.out.println(timer1);

            System.out.println("\n------------------------\n");

            System.out.println("Quick Sort with Partition Algorithm 2:");
            Timer timer2 = new Timer();
            quickSort2(B);
            timer2.end();
            printArray(B);
            System.out.println(timer2);
        }

        // Test against array with reversed order
        {
            System.out.println("\n==========================");

            int length = 10000000;
            int[] A = new int[length];
            for (int i = 0; i < length; i++) {
                A[i] = length - i;
            }
            System.out.println("Test 2 - Reversed Input Array:");
            printArray(A);
            int[] B = Arrays.copyOf(A, length);

            System.out.println("==========================\n");

            System.out.println("Quick Sort with Partition Algorithm 1:");
            Timer timer1 = new Timer();
            quickSort1(A);
            timer1.end();
            printArray(A);
            System.out.println(timer1);

            System.out.println("\n------------------------\n");

            System.out.println("Quick Sort with Partition Algorithm 2:");
            Timer timer2 = new Timer();
            quickSort2(B);
            timer2.end();
            printArray(B);
            System.out.println(timer2);
        }

    }
}
