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

import java.util.Arrays;

public class Sort {

    /**
     * Sort generic array arr, using array tmp as buffer.
     * @param arr
     * @param tmp
     * @param <T>
     */
    static<T extends Comparable<? super T>> void mergeSort(T[] arr, T[] tmp) {
        mergeSort(arr, tmp, 0, arr.length - 1);
    }

    /**
     * Helper function for <code>static<T extends Comparable<? super T>> void mergeSort(T[] arr,T[] tmp) </code>.
     * Specify the part of array to be sorted at current call.
     * @param arr   : Array to be sorted.
     * @param tmp   : Buffer in merge.
     * @param left  : Start index of the part of array to be sorted.
     * @param right : End index of the part of array to be sorted.
     */
    static<T extends Comparable<? super T>> void mergeSort(T[] arr, T[] tmp, int left, int right) {
        if (left >= right)
            return;
        int mid = left + (right - left) / 2;
        mergeSort(arr, tmp, left, mid);
        mergeSort(arr, tmp, mid + 1, right);
        merge(arr, tmp, left, mid, right);
    }

    /**
     * Merge two adjacent part of array.
     * <ul>
     * <li>arr[left, mid]</li>
     * <li>arr[mid+1, right]</li>
     * </ul>
     * The two part should be sorted already
     * @param arr
     * @param tmp
     * @param left
     * @param mid
     * @param right
     */
    static<T extends Comparable<? super T>> void merge(T[] arr, T[] tmp, int left, int mid, int right) {

        System.arraycopy(arr, left, tmp, left, right - left + 1);

        for (int i = left, j = mid + 1, k = left; k <= right; k++) {
            if (i > mid) {
                arr[k] = tmp[j];
                j++;
            } else if (j > right) {
                arr[k] = tmp[i];
                i++;
            } else if (tmp[i].compareTo(tmp[j]) < 0) {
                arr[k] = tmp[i];
                i++;
            } else {
                arr[k] = tmp[j];
                j++;
            }
        }
    }

    /**
     * Sort int array arr, using array tmp as buffer.
     * @param arr
     * @param tmp
     */
    static void mergeSort(int[] arr, int[] tmp) {
        mergeSort(arr, tmp, 0, arr.length - 1);
    }

    /**
     * Helper function for <code>static void mergeSort(int[] arr, int[] tmp) </code>.
     * Specify the part of array to be sorted at current call.
     * @param arr   : Array to be sorted.
     * @param tmp   : Buffer in merge.
     * @param left  : Start index of the part of array to be sorted.
     * @param right : End index of the part of array to be sorted.
     */
    static void mergeSort(int[] arr, int[] tmp, int left, int right) {
        if (left >= right)
            return;
        int mid = left + (right - left) / 2;
        mergeSort(arr, tmp, left, mid);
        mergeSort(arr, tmp, mid + 1, right);
        merge(arr, tmp, left, mid, right);
    }

    /**
     * Merge two adjacent part of array.
     * <ul>
     * <li>arr[left, mid]</li>
     * <li>arr[mid+1, right]</li>
     * </ul>
     * The two part should be sorted already
     * @param arr
     * @param tmp
     * @param left
     * @param mid
     * @param right
     */
    static void merge(int[] arr, int[] tmp, int left, int mid, int right) {

        System.arraycopy(arr, left, tmp, left, right - left + 1);

        for (int i = left, j = mid + 1, k = left; k <= right; k++) {
            if (i > mid) {
                arr[k] = tmp[j];
                j++;
            } else if (j > right) {
                arr[k] = tmp[i];
                i++;
            } else if (tmp[i] < tmp[j]) {
                arr[k] = tmp[i];
                i++;
            } else {
                arr[k] = tmp[j];
                j++;
            }
        }
        return;
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
