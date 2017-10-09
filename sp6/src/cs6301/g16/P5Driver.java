package cs6301.g16;

import cs6301.g00.BinaryHeap;
import cs6301.g00.Timer;

import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class P5Driver {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter length of array to be generated: ");

        final int size = scanner.nextInt();

        System.out.println("Generating random array within " + size + ".");

        Random r = new Random();

        Integer[] arr = new Integer[size];

        for (int i = 0; i < size; i++) {
            arr[i] = r.nextInt(size);
        }

        {
            StringBuilder sb = new StringBuilder("The array is [");

            sb.append(arr[0]).append(",").append(arr[1]).append(",").append(arr[2]).append(",")
                    .append(arr[3]).append(",").append(arr[4]).append(",").append(arr[5]).append(",")
                    .append(arr[6]).append(",").append(arr[7]).append(",").append(arr[8]).append(",")
                    .append(arr[9]).append(",").append("...]");

            System.out.println("The array is " + sb.toString());

            System.out.println();

        }

        {
            System.out.println("Sorting the array in ascending order.");

            Timer t = new Timer();
            BinaryHeap.heapSort(arr, Comparator.reverseOrder());
            System.out.println(t.end());

            System.out.println();
            StringBuilder sb = new StringBuilder("The array is [");

            sb.append(arr[0]).append(",").append(arr[1]).append(",").append(arr[2]).append(",")
                    .append(arr[3]).append(",").append(arr[4]).append(",").append(arr[5]).append(",")
                    .append(arr[6]).append(",").append(arr[7]).append(",").append(arr[8]).append(",")
                    .append(arr[9]).append(",").append("...]");

            System.out.println("The array is " + sb.toString());

            System.out.println();

        }

        {
            System.out.println("Sorting the array in descending order again.");

            Timer t = new Timer();
            BinaryHeap.heapSort(arr, Comparator.naturalOrder());
            System.out.println(t.end());

            System.out.println();

            StringBuilder sb = new StringBuilder("The array is [");

            sb.append(arr[0]).append(",").append(arr[1]).append(",").append(arr[2]).append(",")
                    .append(arr[3]).append(",").append(arr[4]).append(",").append(arr[5]).append(",")
                    .append(arr[6]).append(",").append(arr[7]).append(",").append(arr[8]).append(",")
                    .append(arr[9]).append(",").append("...]");

            System.out.println("The array is " + sb.toString());
        }
    }
}

