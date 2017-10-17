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

        public void setData(T theNewValue) {
            element = theNewValue;
        }

        public Entry<T> getLeft() {
            return left;
        }

        public Entry<T> getRight() {
            return right;
        }

        public void setLeft(Entry<T> theNewLeft) {
            left = theNewLeft;
        }

        public void setRight(Entry<T> theNewRight) {
            right = theNewRight;
        }
    }

    private Entry<T> root;
    private int size;

    public BST() {   //Initial BST
        root = null;
        size = 0;
    }


    /**
     * TO DO: Is x contained in tree?
     */
    public boolean contains(T x) {
        Entry<T> cur = root;
        while (cur != null) {
            if (cur.element == x) {
                return true;
            } else if (cur.element.compareTo(x) > 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return false;
    }

    /**
     * TO DO: Is there an element that is equal to x in the tree?
     * Element in tree that is equal to x is returned, null otherwise.
     */
    public T get(T x) {
        if (contains(x))
            return x;
        return null;
    }

    /**
     * TO DO: Add x to tree.
     * If tree contains a node with same key, replace element by x.
     * Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
        int oldSize = size;
        root = addHelper(root, x);
        return oldSize != size;
    }

    private Entry<T> addHelper(Entry<T> n, T val) {
        if (n == null) {
            size++;
            return new Entry<T>(val, null, null);
        }
        int diff = val.compareTo(n.element);
        if (diff < 0) {
            n.setLeft(addHelper(n.getLeft(), val));  //Recur to left
        } else if (diff > 0) {
            n.setRight(addHelper(n.getRight(), val));   //Recur to right
        }
        return n;
    }

    /**
     * TO DO: Remove x from tree.
     * Return x if found, otherwise return null
     */
    public T remove(T x) {
        int oldSize = size;
        root = removeHelper(root, x);
        if (size == oldSize)    //Not find x.
            return null;
        return x;    //Find x.
    }

    private Entry<T> removeHelper(Entry<T> n, T val) {
        if (n != null) {
            {
                int diff = val.compareTo(n.getData());
                if (diff < 0)
                    n.setLeft(removeHelper(n.getLeft(), val));
                else if (diff > 0)
                    n.setRight(removeHelper(n.getRight(), val));
                else //if value is found
                {
                    size--;
                    if (n.getLeft() == null && n.getRight() == null) //If node is a leaf, set null.
                        n = null;
                    else if (n.getRight() == null)
                        n = n.getLeft();
                    else if (n.getLeft() == null)
                        n = n.getRight();
                    else //Node has two not null children, replace data with max of left subtree
                    {
                        n.setData(getMax(n.getLeft()));
                        n.setLeft(removeHelper(n.getLeft(), n.getData()));
                        size++;
                    }
                }
            }
        }
        return n;
    }

    //Get the maximum element of BST.
    private T getMax(Entry<T> n) {
        while (n.getRight() != null)
            n = n.getRight();
        return n.getData();
    }

    /**
     * TO DO: Iterate elements in sorted order of keys
     */
    public Iterator<T> iterator() {
        return new InOrderItr();
    }


    private class InOrderItr implements Iterator<T> {
        private final Stack<Entry<T>> stack = new Stack<>();
        Entry<T> current = null;

        public InOrderItr() {
            current = root;
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty() || current != null;
        }

        @Override
        public T next() {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            Entry<T> t = stack.pop();
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
        if (node.left != null)
            i = toArrayHelper(arr, i, node.left);
        arr[i++] = node.element;
        if (node.right != null)
            i = toArrayHelper(arr, i, node.right);
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
