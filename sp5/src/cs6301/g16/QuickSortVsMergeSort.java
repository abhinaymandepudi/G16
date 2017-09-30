/**
 * <h1>Fall 2017 Short Project 5</h1>
 * <p>
 * Compare the performance of your best implementation of Merge sort with
 * quick sort that uses dual-pivot (or multi-pivot) partition. Try inputs
 * that are distinct, and inputs that have many duplicates.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-27
 */

package cs6301.g16;

import cs6301.g00.Timer;

import java.util.Arrays;

public class QuickSortVsMergeSort {
    private static final int threshold = 20;

    //The best merge sort implementation in sp4

    /**
     * @param A   The array need to be sorted.
     * @param B   Copy of A.
     * @param p   Start index of array will be sorted.
     * @param r   End index of array will be sorted.
     */
    public static void mergeSort(int[] A, int[] B, int p, int r) {
        if (r - p < threshold) {
            insertion_srt(A, p, r);
        } else if (p < r) {
            int q = (p + r) / 2;
            mergeSort(B, A, p, q);
            mergeSort(B, A, q + 1, r);
            merge(B, A, p, q, r);
        }
    }

    /**
     * Merge sorted A[p...q] and sorted A[q+1...r] into A[p...r] in sorted order.
     *
     * @param A   The array need to be sorted.
     * @param B   Copy of A.
     * @param p   Start index of array will be merge.
     * @param q   The split position index of A[p...r].
     * @param r   End index of array will be merge.
     */
    private static void merge(int[] A, int[] B, int p, int q, int r) {
        int i = p, j = q + 1;
        for (int k = p; k <= r; k++) {
            if (j > r || (i <= q && A[i]<=A[j]))
                B[k] = A[i++];
            else
                B[k] = A[j++];
        }
    }

    /**
     * Insertion sort to sort part of array.
     *
     * @param arr   The array need to be sorted.
     * @param start Start index of array will be sort.
     * @param l     End index of array will be sort.
     */
    public static void insertion_srt(int[] arr, int start, int l) {
        int k;
        int key;
        for (int i = start + 1; i <= l; i++) {
            key = arr[i];
            k = i - 1;
            while (k > start - 1 && arr[k]>key) {
                arr[k + 1] = arr[k];
                k--;
            }
            arr[k + 1] = key;
        }
    }

    /**
     * Call (4) merge sort function to sort array.
     *
     * @param A The array need to be sorted.
     */
    public static void mergeSort(int[] A) {
        int[] temp = A.clone();
        mergeSort(A, temp, 0, A.length - 1);
    }


    /**
     * Main function for testing
     * @param args
     */
    public static void main(String[] args) {
        {
            System.out.println("\n====================\nTest with array with distinct elements:\n");
            int[] A = ArrayHelper.getArray(10000000);

            int[] B = ArrayHelper.shuffle(A);
            int[] C = Arrays.copyOf(B, B.length);

            System.out.println("Input Array:");
            ArrayHelper.printArray(B);

            System.out.println("Dual Pivot Quick Sort:");
            Timer t = new Timer();
            DualPivotQuickSort.dPQuickSort(B);
            t.end();
            ArrayHelper.printArray(B);
            boolean isSuccess = true;
            for (int i = 0; i < A.length; i++) {
                if (A[i] != B[i]) {
                    System.out.printf("Sorting failed at index=%d\n", i);
                    isSuccess = false;
                    break;
                }
            }
            if (isSuccess)
                System.out.println("Sorting Success");
            System.out.println(t + "\n");


            System.out.println("Merge Sort:");
            B = C;
            t = new Timer();
            mergeSort(B);
            t.end();
            ArrayHelper.printArray(B);
            isSuccess = true;
            for (int i = 0; i < A.length; i++) {
                if (A[i] != B[i]) {
                    System.out.printf("Sorting failed at index=%d\n", i);
                    isSuccess = false;
                    break;
                }
            }
            if (isSuccess)
                System.out.println("Sorting Success");
            System.out.println(t + "\n");
        }

        {
            System.out.println("\n====================\nTest with array with duplicate elements:\n");
            int[] A = ArrayHelper.getDuplicateElementArray(0.8f,10000000);

            int[] B = ArrayHelper.shuffle(A);
            int[] C = Arrays.copyOf(B, B.length);

            System.out.println("Input Array:");
            ArrayHelper.printArray(B);

            System.out.println("Dual Pivot Quick Sort:");
            Timer t = new Timer();
            DualPivotQuickSort.dPQuickSort(B);
            t.end();
            ArrayHelper.printArray(B);
            boolean isSuccess = true;
            for (int i = 0; i < A.length; i++) {
                if (A[i] != B[i]) {
                    System.out.printf("Sorting failed at index=%d\n", i);
                    isSuccess = false;
                    break;
                }
            }
            if (isSuccess)
                System.out.println("Sorting Success");
            System.out.println(t + "\n");


            System.out.println("Merge Sort:");
            B = C;
            t = new Timer();
            mergeSort(B);
            t.end();
            ArrayHelper.printArray(B);
            isSuccess = true;
            for (int i = 0; i < A.length; i++) {
                if (A[i] != B[i]) {
                    System.out.printf("Sorting failed at index=%d\n", i);
                    isSuccess = false;
                    break;
                }
            }
            if (isSuccess)
                System.out.println("Sorting Success");
            System.out.println(t + "\n");
        }
    }
}
