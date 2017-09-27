/**
 * <h1>Fall 2017 Short Project 4-2</h1>
 * <p>
 * Helper class to implement array related helper functions
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-27
 */

package cs6301.g16;

import java.util.Arrays;

public class ArrayHelper {

    /**
     * Helper function to exchange 2 elements in the given array
     * Utilizing XOR exchange algorithm
     *
     * @param A - input array
     * @param i - element index 1
     * @param j - element index 2
     */
    public static void exchange(int[] A, int i, int j) {
//        A[i] = A[i] ^ A[j];
//        A[j] = A[i] ^ A[j];
//        A[i] = A[i] ^ A[j];
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    /**
     * Print an array nicely
     **/
    public static void printArray(int[] A) {
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
     * Returns a random shuffling copy of the array.
     */
    public static int[] shuffle(int[] nums) {
        int[] rand = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int r = (int) (Math.random() * (i + 1));
            rand[i] = rand[r];
            rand[r] = nums[i];
        }
        return rand;
    }

    /**
     * Return a random array with specified length all elements are distinct
     * @param length
     * @return
     */
    public static int[] getRandomArray(int length){
        int[] A = new int[length];
        for (int i = 0; i < length; i++) {
            A[i] = i + 1;
        }
        A = shuffle(A);
        return A;
    }

    /**
     * Return an array in reversed order with specified length, all elements are distinct
     * @param length
     * @return
     */
    public static int[] getReversedArray(int length){
        int[] A = new int[length];
        for (int i = 0; i < length; i++) {
            A[i] = length - i;
        }
        return A;
    }
}
