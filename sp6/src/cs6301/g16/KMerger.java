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

import java.util.*;
import java.util.stream.Stream;

public class KMerger<T extends Comparable<? super T>> {
    private int k;
    private PriorityQueue<ListWrapper<T>> queue;

    public KMerger(int k) {
        this.k = k;
        this.queue = new PriorityQueue<>(k);
    }

    public List<T> merge(List<List<T>> kList) {
        Stream<ListWrapper<T>> kListWrapper = kList.stream().map(ListWrapper<T>::new);
        List<T> m = new ArrayList<>(this.k * kList.get(0).size());

        kListWrapper.forEach(x -> this.queue.offer(x));

        while (this.queue.peek() != null) {
            ListWrapper<T> next = this.queue.poll();
            m.add(next.pop());
            if (next.isEmpty())
                continue;
            this.queue.offer(next);
        }

        return m;
    }

    private class ListWrapper<T extends Comparable<? super T>> implements Comparable<ListWrapper<T>> {
        List<T> l;
        T next;
        Iterator<T> iterator;

        public ListWrapper(List<T> l) {
            this.l = l;
            this.iterator = this.l.iterator();
            this.next = iterator.hasNext() ? iterator.next() : null;
        }

        public T pop() {
            T ret = next;
            next = iterator.hasNext() ? iterator.next() : null;
            return ret;
        }

        public boolean isEmpty() {
            return next == null;
        }

        @Override
        public int compareTo(ListWrapper<T> o) {
            return this.next.compareTo(o.next);
        }

        @Override
        public String toString() {
            return next + ": " + l;
        }
    }

    public static void main(String[] args) throws Exception {
        List<List<Integer>> lists = Arrays.asList(
                new ArrayList<>(Arrays.asList(1, 3, 5, 7, 9)),
                new ArrayList<>(Arrays.asList(3, 4, 5, 6, 7)),
                new ArrayList<>(Arrays.asList(2, 4, 6, 8, 10)),
                new ArrayList<>(Arrays.asList(4, 5, 7, 9, 12)),
                new ArrayList<>(Arrays.asList(100, 200, 300, 400))
        );

        KMerger<Integer> merger = new KMerger<>(lists.size());

        System.out.println(merger.merge(lists));
        System.out.println(lists);
    }
}
