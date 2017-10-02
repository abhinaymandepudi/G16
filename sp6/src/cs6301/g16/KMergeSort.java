/*
 * <h1>Fall 2017 Short Project 6 - 1</h1>
 * <p>
 * Sort an array using k-way merge (normally used for external sorting): Split the array A into k
 * fragments, sort them recursively, and merge them using a priority queue (of size k). [In external
 * sorting applications, intermediate sorted sub-arrays are written to disk. The algorithm sorts A
 * by using buffers of size O(k).]
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-02
 */

package cs6301.g16;

import cs6301.g00.Timer;

import java.util.*;
import java.util.stream.Collectors;

public class KMergeSort<T extends Comparable<? super T>> {
    private static final int threshold = 20;
    private int k;

    public KMergeSort(int k) {
        this.k = k;
    }

    public List<T> sort(List<T> list) {

        if (list.size() < 10 || list.size() <= k * 10) {
            Collections.sort(list);
            return list;
        }

        List<List<T>> lists = new ArrayList<>(k);

        final int size = list.size() / k + 1;

        for (int i = 0; i < k; i++) {
            // Split the recursively sort each segment with length size.
            lists.add(this.sort(list.stream().skip(i * size).limit(size).collect(Collectors.toList())));
        }

        KMerger<T> merger = new KMerger<>(k);

        return merger.merge(lists);
    }

    public T[] sort(T[] arr) {
        return null;
    }

    private T[] sort(T[] A, T[] B, int p, int r) {
        if (r - p < threshold) {
            T[] tmp = Arrays.copyOfRange(A, p, r);
            int i = p;
            for (T t : tmp)
                A[i++] = t;
        } else if (p < r) {
            int q = (p + r) / 2;
//            mergeSortFour(B, A, p, q);
//            mergeSortFour(B, A, q + 1, r);
//            mergeFour(B, A, p, q, r);
        }

        return null;
    }

    public static void main(String[] args) {
        int size = 5000000;
        if (args.length >= 1)
            size = Integer.parseInt(args[0]);
        List<Integer> list = new ArrayList<>(size);
        Random r = new Random();

        for (int i = 0; i < size; i++)
            list.add(r.nextInt(100));

        assert !isSorted(list);

        KMergeSort<Integer> sort = new KMergeSort<>(8);

        Timer t = new Timer();
        t.start();
        List<Integer> sortedList = sort.sort(list);
        System.out.println(t.end());

        assert sortedList.size() == list.size();
        assert isSorted(sortedList);
    }

    private static <T extends Comparable<? super T>> boolean isSorted(List<T> list) {
        Iterator<T> iterator = list.iterator();
        if (!iterator.hasNext())
            return true;
        T pre = iterator.next();

        while (iterator.hasNext()) {
            T cur = iterator.next();
            if (pre.compareTo(cur) > 0)
                return false;
        }

        return true;
    }

    private static <T extends Comparable<? super T>> boolean isSorted(T[] arr) {
        return false;
    }
}
