/*
 * <h1>Fall 2017 Short Project 7 - 1</h1>
 * <p>
 * Implement binary search trees (BST), with the following public methods:
 * contains, add, remove, iterator.  Concentrate on elegance of code
 * (especially, iterator), and reusability of code in extended BST classes
 * (AVL, Red Black, and, Splay Trees).
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-15
 */

package cs6301.g16;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;


public class BST<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;

        Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }

        public T getData() {
            return element;
        }

        public Entry<T> getLeft() {
            return left;
        }

        public Entry<T> getRight() {
            return right;
        }

    }

    private Entry<T> root;
    private int size;
    private Stack<Entry<T>> stack = new Stack<>();  //Store ancestors.

    public BST() {   //Initial BST
        root = null;
        size = 0;
    }

    /**
     * Find x in tree.
     * @param x The element we find.
     * @return Node where search ends.
     */
    public Entry<T> find(T x) {
        stack.push(null);
        return find(root, x);
    }

    public Entry<T> find(Entry<T> t, T x) {
        if (t == null || t.element == x)
            return t;
        while (true) {
            if (x.compareTo(t.element) < 0) {
                if (t.getLeft() == null)
                    break;
                else {
                    stack.push(t);
                    t = t.left;
                }
            } else if (x.compareTo(t.element) == 0) {
                break;
            } else {
                if (t.right == null)
                    break;
                else {
                    stack.push(t);
                    t = t.right;
                }
            }
        }
        return t;
    }

    /**
     * Whether there are element x in the tree.
     * @param x The element we find.
     * @return If find out,return true. Otherwise,return false.
     */
    public boolean contains(T x) {
        Entry<T> t = find(x);
        return t != null && t.element == x;
    }

    /**
     * TO DO: Is there an element that is equal to x in the tree?
     * Element in tree that is equal to x is returned, null otherwise.
     */
    public T get(T x) {
        Entry<T> t = find(x);
        if (t!=null && t.element==x)
            return t.element;
        return null;
    }

    //Call when t has at most one child.
    public void bypass(Entry<T> t) {
        Entry<T> pt = stack.peek(); //Get ancestor of t.
        Entry<T> c = t.left == null ? t.right : t.left;
        if (pt == null) {   //pt is root.
            root = c;
        } else if (pt.left == t) {
            pt.left = c;
        } else {
            pt.right = c;
        }
    }

    /**
     * TO DO: Add x to tree.
     * If tree contains a node with same key, replace element by x.
     * Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
        if (root == null) {
            root = new Entry<>(x, null, null);
            size = 1;
            return true;
        }
        Entry<T> t = find(x);
        if (x == t.element) {
            t.element = x; // If duplicate,replace to the new one.
            return false;
        } else if (x.compareTo(t.element) < 0) {
            t.left = new Entry<>(x, null, null);
        } else {
            t.right = new Entry<>(x, null, null);
        }
        size++;
        return true;
    }

    /**
     * TO DO: Remove x from tree.
     * Return x if found, otherwise return null
     */
    public T remove(T x) {
        if (root == null)
            return null;
        Entry<T> t = find(x);
        if (t.element != x)
            return null;
        T result = t.element;
        if (t.left == null || t.right == null)   //t has 0 or 1 child
            bypass(t);
        else {   //t has 2 children
            stack.push(t);
            Entry<T> minRight = find(t.right, t.element);
            t.element = minRight.element;
            bypass(minRight);
        }
        size--;
        return result;

    }

    protected Entry<T> rotateLeft(Entry<T> P) {
        Entry<T> Q = P.right;
        P.right = Q.left;
        Q.left = P;
        return Q;
    }

    protected Entry<T> rotateRight(Entry<T> P) {
        Entry<T> Q = P.left;
        P.left = Q.right;
        Q.right = P;
        return Q;
    }

    protected Entry<T> rotateLR(Entry<T> P) {
        P.left = rotateLeft(P.left);
        return rotateRight(P);
    }

    protected Entry<T> rotateRL(Entry<T> P) {
        P.right = rotateRight(P.right);
        return rotateLeft(P);
    }

    //Get the maximum element of BST.
    private T Max() {
        if (root == null)
            return null;
        Entry<T> t = root;
        while (t.getRight() != null)
            t = t.getRight();
        return t.getData();
    }

    //Get the minimum element of BST.
    private T Min(Entry<T> n) {
        if (root == null)
            return null;
        Entry<T> t = root;
        while (t.getLeft() != null)
            t = t.getLeft();
        return t.getData();
    }

    /**
     * TO DO: Iterate elements in sorted order of keys
     */
    public Iterator<T> iterator() {
        return new InOrderItr();
    }


    private class InOrderItr implements Iterator<T> {
        private final Stack<Entry<T>> stackTwo = new Stack<>();
        Entry<T> current = null;

        public InOrderItr() {
            current = root;
        }

        @Override
        public boolean hasNext() {
            return !stackTwo.isEmpty() || current != null;
        }

        @Override
        public T next() {
            while (current != null) {
                stackTwo.push(current);
                current = current.left;
            }
            Entry<T> t = stackTwo.pop();
            current = t.right;
            return t.element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Unsupported operation for in-order iterator.");
        }

    }


    public static void main(String[] args) {
        BST<Integer> t = new BST<>();
        Scanner in = new Scanner(System.in);
        System.out.println("Test 1.Test add(),remove(),toArray()");
        while (in.hasNext()) {
            int x = in.nextInt();
            if (x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if (x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for (int i = 0; i < t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                break;
            }
        }
        System.out.println("Test 2.Test Iterator()");
        Iterator<Integer> itr = null;
        itr = t.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
    }


    public Comparable[] toArray() {
    /* write code to place elements in array here */
        Comparable[] arr = new Comparable[size];
        if (root != null)
            toArrayHelper(arr, 0, root);
        return arr;
    }

    //The helper function of toArray()
    private int toArrayHelper(Comparable[] arr, int i, Entry<T> node) {
        if (node.left != null) {
            i = toArrayHelper(arr, i, node.left);
        }
        arr[i++] = node.element;
        if (node.right != null) {
            i = toArrayHelper(arr, i, node.right);
        }
        return i;
    }


    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/
