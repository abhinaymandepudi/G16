/**
 * <h1>Fall 2017 Short Project 5-3</h1>
 * <p>
 * Implement 3 versions of the Select algorithm (finding k largest elements)
 * and empirically evaluate their performance:
 * (a) Create a priority queue (max heap) of the n elements, and use remove() k times.
 * (b) Use a priority queue (min heap) of size k to keep track of the
 * k largest elements seen so far, as you iterate over the array.
 * (c) Implement the O(n) algorithm for Select discussed in class.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-27
 */
package cs6301.g16;


import java.util.*;
import java.util.stream.Stream;


public class SelectAlgorithm {
    static final int bound = 50;

    //(a)Create a priority queue (max heap) of the n elements, and use remove() k times.
    public static int PriorityQueueSort1(int[] A, int k) {
        Queue<Integer> queue = new PriorityQueue<>(k, Collections.reverseOrder());   // Trans to max heap
        List<Integer> list = new ArrayList<>();
        for (int j = 0; j < A.length; j++) {   //build max-heap
            queue.offer(A[j]);
        }
        for (int i = 1; i <= k; i++) {
            list.add(queue.remove());
        }
        return list.get(k - 1);
    }

    //(b)Use a priority queue (min heap) of size k to keep track of the k largest elements seen so far, as you iterate over the array.
    // TODO: 2017/9/29  怎么从Stream中取元素存到queue？为啥不对  下面那个q.add又可以？
    public static int PriorityQueueSort2(Stream<Integer> A, int k) {
        Iterator<Integer> it = A.iterator();
        Queue<Integer> q = new PriorityQueue<>();  //New a min heap
        for (int i = 1; i <= k; i++) {    //build min heap
            if (it.hasNext()) {
               // int temp= (int)it.next();
                q.add(it.next());
            }
        }
        while (it.hasNext()) {
            int x = it.next();
            if (q.peek()<x) {
                q.remove();
                q.add(x);
            }
        }
        return q.peek();
    }

    //(c) Implement the O(n) algorithm for Select discussed in class.

    public static int[] Select(int[] A, int k) {
        int n = A.length;
        if (k > n)
            return A;
        Select(A, 0, n, k);
        return Arrays.copyOfRange(A, n - k, n);
    }

    /**
     * @param A   array name
     * @param p   array start index
     * @param n   array's length
     * @param k   kth largest element need to find
     * @return the element we find
     */
    public static  int Select(int[] A, int p, int n, int k) {
        int r = p + n - 1;
        if (n < bound) {
            insertion_srt(A, p, r);
            return A[p + n - k];
        } else {
            int q = QuickSort.partition2(A, p, r);
            int left = q - p, right = r - q;
            if (right >= k)
                return Select(A, q + 1, right, k);
            else if (right + 1 == k)
                return A[q];
            else
                return Select(A, p, left, k - (right + 1));
        }
    }


    public static  void insertion_srt(int[] arr, int start, int l) {
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




    public static void main(String[] args) {
        int[] item = new int[3000];
        System.out.println("Input K(Kth largest element) of array (1-array.length):");
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        while(k<=0||k>item.length){
            System.out.println("Input K(Kth largest element) of array (1-array.length):");
            k=scanner.nextInt();
        }
        for (int i = 0; i < item.length; i++) {
            item[i] = i;
        }
        ArrayHelper.shuffle(item);
        System.out.println("test (a):");
        long startTime = System.currentTimeMillis();
        int resA = PriorityQueueSort1(item, k);
        System.out.println("Kth element is: " + resA + "     Running time : " + (System.currentTimeMillis() - startTime) + "ms");

        System.out.println("test (b):");
        ArrayList<Integer> a = new ArrayList<>(4000);
        for (int i : item) a.add(i);
        startTime = System.currentTimeMillis();
        int resB = PriorityQueueSort2(a.stream(), k);
        System.out.println("Kth element is: " + resB + "     Running time : " + (System.currentTimeMillis() - startTime) + "ms");

        System.out.println("test (c):");
        startTime = System.currentTimeMillis();
        int[] resC = Select(item, k);
        System.out.println("Kth element is: " + resC[0] + "     Running time : " + (System.currentTimeMillis() - startTime) + "ms");

    }
}



