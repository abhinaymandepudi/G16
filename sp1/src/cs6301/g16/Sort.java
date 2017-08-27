/**
 * <h1>Fall 2017 Short Project 1</h1>
 * <p>
 * Implement the merge sort algorithm on generic arrays and on an int array and compare their
 * running times on arrays sizes from 1M-16M, and with an O(n^2) time algorithm, such as Insertion
 * sort. Write a report with a table and/or chart showing the times for different sizes.
 *
 * @author Binhan Wang / Hanlin He / Zheng Gao
 * @version 0.1
 * @since 2017-08-25
 */

package cs6301.g16;
import cs6301.g00.Shuffle;

import static cs6301.g00.Shuffle.shuffle;

public class Sort {

    /**
     * Sort generic array arr, using array tmp as buffer.
     * @param arr
     * @param tmp
     * @param <T>
     */
    static<T extends Comparable<? super T>> void mergeSort(T[] arr, T[] tmp) {

    }

    /**
     * Sort int array arr, using array tmp as buffer.
     * @param arr
     * @param tmp
     */
    static void mergeSort(int[] arr, int[] tmp) {

    }

    /**
     * Insertion/Bubble sort generic array arr in place.
     * @param arr
     * @param <T>
     */
    static<T extends Comparable<? super T>> void nSquareSort(T[] arr) {
        for(int p = 1; p < arr.length; p++)
        {
            T tmp = arr[p];
            int j;
            for(j = p; j > 0 && tmp.compareTo(arr[j-1]) < 0; j--)
            {
                arr[j] = arr[j-1];//后移一位
            }
            arr[j] = tmp;//插入
        }
    }

    public static void main(String[] args) {
        Integer[] intg = new Integer[]{23,4,5,12,0};
        shuffle(intg);
        for (Integer i : intg) {
            System.out.print(i + " ");
        }
        System.out.println("");
        nSquareSort(intg);
        for (Integer i : intg) {
            System.out.print(i + " ");
        }
    }
//
}
