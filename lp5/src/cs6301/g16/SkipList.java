
// Change this to your group number
package cs6301.g16;


import java.util.Iterator;
import java.util.Random;

// Skeleton for skip list implementation.

public class SkipList<T extends Comparable<? super T>> {

    private int maxLevel = 32;

    static class Entry<T> {
        T element;
        int level;
        Entry<T>[] next;
        int[] span;

        Entry(T ele, int lev) {
            element = ele;
            level = lev;
            next = new Entry[lev];
            span = new int[lev];
        }
    }

    private Entry<T> head;
    private Entry<T> tail;
    private int size;

    // Constructor
    public SkipList() {
        head = new Entry<>(null, maxLevel);
        tail = new Entry<>(null, maxLevel);
        for (int i = 0; i < maxLevel; i++) {
            head.next[i] = tail;
            head.span[i] = 1;
        }
        size = 0;
    }

    //helper function
    private Entry<T>[] find(T x) {
        Entry<T> p = head;
        Entry<T>[] prev = new Entry[maxLevel];
        for (int i = maxLevel - 1; i >= 0; i--) {
            while (p.next[i].element != null && x.compareTo(p.next[i].element) > 0) {
                p = p.next[i];
            }
            prev[i] = p;
        }
        return prev;
    }

    private int chooseLevel(int lev) {
        int i = 1;
        while (i < lev) { // largest level is lev
            Random random = new Random();
            boolean b = random.nextBoolean();
            if (b) {
                i++;
            } else {
                break;
            }
        }
        return i;
    }

    // Add x to list. If x already exists, replace it. Returns true if new node is added to list
    public boolean add(T x) {
        Entry<T>[] prev = find(x);
        int newPrevSpan;
        if (prev[0].next[0].element != null && prev[0].next[0].element.compareTo(x) == 0) {
            prev[0].next[0].element = x;
            return false;
        } else {
            int lev = chooseLevel(maxLevel);
            Entry<T> n = new Entry<>(x, lev);
            prev[0].span[0] = 1;  //lowest level condition
            n.span[0] = 1;
            for (int i = 0; i < lev; i++) {
                n.next[i] = prev[i].next[i];
                prev[i].next[i] = n;
                if (i >= 1) {
                    newPrevSpan = prev[i - 1].span[i - 1] + distance(prev[i], prev[i - 1]);
                    n.span[i] = prev[i].span[i] - newPrevSpan + 1;   //update n's' forward span
                    prev[i].span[i] = newPrevSpan;  //update previous span
                }
            }

            //update the higher level span cross the new node
            for (int i = lev; i < prev.length; i++) {
                prev[i].span[i]++;
            }
            size++;
        }

        return true;
    }

    private int distance(Entry<T> a, Entry<T> b) {   //a is left side, b is right side,naive solution
        if (a == b)
            return 0;
        Entry<T> node = a;
        int dis = 0;
        while (node != b) {
            for (int i = node.level-1; i >= 0; i--) {
                if (node.next[i] != tail && node.next[i].element.compareTo(b.element) <= 0) {
                    dis += node.span[i];
                    node = node.next[i];
                    break;
                }
            }
        }

        return dis;
    }

    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {
        return find(x)[0].next[0].element;
    }

    // Does list contain x?
    public boolean contains(T x) {
        Entry<T>[] prev = find(x);
        return prev[0].next[0].element == x;
    }

    // Return first element of list
    public T first() {
        if (size > 0)
            return head.next[0].element;
        return null;
    }

    // Find largest element that is less than or equal to x
    public T floor(T x) {
        if (find(x)[0].next[0].element.compareTo(x) == 0)
            return x;
        else
            return find(x)[0].element;
    }

    // Return element at index n of list.  First element is at index 0.
    public T get(int n) {
        if (n >= size || n < 0)
            throw new IndexOutOfBoundsException();

        int currSpan = 0;
        Entry<T> node = head;

        for (int i = maxLevel - 1; i >= 0; i--) {
            while (currSpan + node.span[i] - 1 < n) {
                currSpan += node.span[i];
                node = node.next[i];
            }
            if (currSpan + node.span[i] - 1 == n) {
                node = node.next[i];
                break;
            }

        }
        return node.element;
    }

    // Is the list empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // Iterate through the elements of list in sorted order

    static class SkipListIterator<T extends Comparable<? super T>> implements Iterator<T> {
        Entry<T> curEntry;
        Entry<T> tailEntry;

        SkipListIterator(SkipList<T> sk) {
            curEntry = sk.head.next[0];
            tailEntry = sk.tail;
        }

        @Override
        public boolean hasNext() {
            return curEntry != tailEntry;
        }

        @Override
        public T next() {
            T tmp = curEntry.element;
            curEntry = curEntry.next[0];
            return tmp;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<T> iterator() {
        return new SkipListIterator<>(this);
    }

    // Return last element of list
    public T last() {
        Entry<T> node = head;
        for (int i = maxLevel - 1; i >= 0; i--) {
            while (node.next[i].element != null)
                node = node.next[i];
        }
        return node.element;
    }

    // Reorganize the elements of the list into a perfect skip list


    // Remove x from list.  Removed element is returned. Return null if x not in list
    public T remove(T x) {
        Entry<T>[] prev = find(x);
        Entry<T> n = prev[0].next[0];
        if (n.element != x)   // no such element in skiplist
            return null;
        else {

            for (int i = 0; i <= maxLevel; i++) {
                if (prev[i].next[i].element == x) {
                    prev[i].next[i] = n.next[i];
                    prev[i].span[i] = prev[i].span[i] + n.span[i] - 1;   //Update previous span
                } else {
                    break;
                }

            }
            for (int i = n.level + 1; i < prev.length; i++) {
                prev[i].span[i]--;
            }
        }
        size--;
        return n.element;
    }

    // Return the number of elements in the list
    public int size() {
        return size;
    }

    public void printList() {

        Entry node = head.next[0];

        System.out.println("----------START----------");
        while (node != null && node.element != null) {
            for (int i = 0; i < node.level; i++) {
                System.out.print(node.element + "\t");
            }
            for (int j = node.level; j < maxLevel; j++) {
                System.out.print("|\t");
            }
            System.out.println();
            node = node.next[0];
        }
        System.out.println("----------END----------");
    }

    public void printSpan() {
        System.out.println("----------SPAN----------");
        System.out.print("H: ");
        for (int i = 0; i < head.level; i++)
            System.out.print(head.span[i] + " ");
        Entry node = head.next[0];
        System.out.println();
        while (node != null && node.element != null) {
            System.out.print(node.element + ": ");
            for (int i = 0; i < node.level; i++) {
                System.out.print(node.span[i] + " ");
            }
            System.out.println();
            node = node.next[0];
        }
    }

    public static void main(String[] args) {

        SkipList<Integer> sk2 = new SkipList<>();
        for (int i = 0; i < 100; i++)
            sk2.add(i);
        sk2.add(3);
        sk2.add(5);
        sk2.add(7);
        sk2.add(1);
        sk2.add(9);
        sk2.remove(3);
        sk2.printSpan();
        sk2.printList();
        for (int i = 0; i < sk2.size; i++)
            System.out.println("Test get(" + i + "): " + sk2.get(i));

        // Test Iterator
        System.out.println("\nTest Iterator:");
        Iterator<Integer> it = sk2.iterator();
        while (it.hasNext()) {
            System.out.print(it.next());
            if (it.hasNext())
                System.out.print(", ");
        }
    }
}

