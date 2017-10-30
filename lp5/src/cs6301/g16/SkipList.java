
// Change this to your group number
package cs6301.g16;


import java.util.Iterator;
import java.util.Random;

// Skeleton for skip list implementation.

public class SkipList<T extends Comparable<? super T>> {
    private static int maxLevel = 32;

    static class Entry<T> {
        T element;
        Entry<T>[] next;
        int level;

        Entry(T ele, int lev) {
            element = ele;
            level = lev;
            next = new Entry[lev+1];
        }
    }

    private Entry<T> head;
    private Entry<T> tail;
    private Entry<T>[] prev;
    private int size;


    // Constructor
    public SkipList() {
        head = new Entry<>(null, maxLevel);   //positive negative infinity?
        tail = new Entry<>(null, maxLevel);
        for (int i = 0; i < maxLevel; i++) {
            head.next[i] = tail;
        }
        prev = new Entry[maxLevel];
        size = 0;
    }

    //helper function
    private Entry<T>[] find(T x) {
        Entry<T> p = head;
        for (int i = maxLevel - 1; i >= 0; i--) {
            while (p.next[i].element != null && x.compareTo(p.next[i].element) > 0) {
                p = p.next[i];
            }
            prev[i] = p;
        }
        return prev;
    }

    private int chooseLevel(int lev) {
        int i = 0;
        while (i < lev) {
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
        prev = new Entry[maxLevel];
        prev = find(x);
        if (prev[0].next[0] != null && prev[0].next[0].element == x) {
            prev[0].next[0].element = x;
        } else {
            int lev = chooseLevel(maxLevel);
            Entry<T> n = new Entry<>(x, lev);
            for (int i = 0; i <= lev; i++) {
                n.next[i] = prev[i].next[i];
                prev[i].next[i] = n;
            }
            size++;
        }

        return true;
    }

    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {
        return null;
    }

    // Does list contain x?
    public boolean contains(T x) {
        prev = find(x);
        return prev[0].next[0].element == x;
    }

    // Return first element of list
    public T first() {
        return null;
    }

    // Find largest element that is less than or equal to x
    public T floor(T x) {
        return null;
    }

    // Return element at index n of list.  First element is at index 0.
    public T get(int n) {
        return null;
    }

    // Is the list empty?
    public boolean isEmpty() {
        return false;
    }

    // Iterate through the elements of list in sorted order
    public Iterator<T> iterator() {
        return null;
    }

    // Return last element of list
    public T last() {
        return null;
    }

    // Reorganize the elements of the list into a perfect skip list
    public void rebuild() {

    }

    // Remove x from list.  Removed element is returned. Return null if x not in list
    public T remove(T x) {
        prev = find(x);
        Entry<T> n = prev[0].next[0];
        if (n.element != x)
            return null;
        else {

            for (int i = 0; i <= maxLevel; i++) {
                if (prev[i].next[i].element == x) {
                    prev[i].next[i] = n.next[i];
                } else
                    break;
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
            for (int i = 0; i < node.level + 1; i++) {
                System.out.print(node.element + "\t");
            }
            for (int j = node.level + 1; j < maxLevel; j++) {
                System.out.print("|\t");
            }
            System.out.println();
            node = node.next[0];
        }
        System.out.println("----------END----------");
    }

    public static void main(String[] args) {
        SkipList<Integer> skipList = new SkipList<>();
        skipList.add(3);
        skipList.add(6);
        skipList.add(5);
        skipList.add(9);
        skipList.add(7);
        skipList.add(4);
        skipList.add(8);
        skipList.printList();
        skipList.remove(3);
        skipList.remove(5);
        skipList.printList();//remove没成功！！

        SkipList<Integer> sk2 = new SkipList<>();
        for (int i = 0; i < 100; i++)
            sk2.add(i);

        sk2.printList();
    }
}

