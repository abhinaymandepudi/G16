
/**
 * Starter code for Red-Black Tree
 */
package cs6301.g16;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class RedBlackTree<T extends Comparable<? super T>> extends BST<T> {
    static class Entry<T> extends BST.Entry<T> {

        static Entry NIL = new Entry();

        boolean isRed;

        Entry() {
            super();
            isRed = false;
        }

        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            isRed = true;
        }

        /**
         * Return the sibling of given {@code Entry} instance.
         *
         * @param t A child of current {@code Entry}.
         *
         * @return The sibling of {@code t} if t is a  child of current {@code Entry}, {@code null}
         * otherwise.
         */
        Entry<T> siblingOf(Entry<T> t) {
            if (t == left)
                return (Entry<T>) right;
            if (t == right)
                return (Entry<T>) left;
            return null;
        }

        /**
         * Override super class to return a {@code RedBlackTree.Entry.NIL}.
         *
         * @return {@code RedBlackTree.Entry.NIL}.
         */
        @Override
        @SuppressWarnings("unchecked")
        public Entry<T> getNIL() {
            return (Entry<T>) NIL;
        }

    }

    private Entry<T> pop() {
        if (stack == null || stack.isEmpty())
            return new Entry<T>().getNIL();

        assert stack.peek() instanceof Entry;
        return (Entry<T>) stack.pop();
    }

    private Entry<T> peek() {
        if (stack == null || stack.isEmpty())
            return new Entry<T>().getNIL();

        assert stack.peek() instanceof Entry;
        return (Entry<T>) stack.peek();
    }

    /**
     * Override super class to generate a new {@code RedBlackTree.Entry<T>} instance.
     *
     * @param x Element in the new entry.
     *
     * @return New {@code RedBlackTree.Entry<T>} instance.
     */
    @Override
    public BST.Entry<T> newEntry(T x) {
        if (x == null)
            return new Entry<T>().getNIL();
        return new Entry<>(x, null, null);
    }

    RedBlackTree() {
        super();
    }

    @Override
    public boolean add(T x) {
        boolean added = super.add(x);

        if (!added)
            return false;

        if (!peek().isRed)
            return true;

        Entry<T> newEntry = pop();

        repair(newEntry);

        // After repair returns, the root node is colored Black.
        ((Entry<T>) root).isRed = false;

        return true;
    }

    private void repair(Entry<T> t) {
        Entry<T> p_t, g_t, u_t;

        if (root == peek())
            return;

        p_t = pop();
        g_t = pop();
        u_t = g_t.siblingOf(p_t);
        while (t.isRed) {

            // if t is root or p_t is root or p_t is Black then return
            if (root == t || root == p_t || !p_t.isRed)
                return;

            // Case 1: if u_t is colored Red
            if (u_t.isRed) {

                // set color of p_t and u_t to Black
                p_t.isRed = u_t.isRed = false;

                // set color of g_t to Red
                g_t.isRed = true;

                // t <- g_t
                t = g_t;

                // continue in next iteration
                p_t = pop();
                g_t = pop();
                u_t = g_t.siblingOf(p_t);
                continue;
            }

            // Case 2: if u_t is Black and
            // Case 2(a): g_t -> p_t -> t is LL
            if (g_t.left == p_t && p_t.left == t) {

                // Rotate [R] at g_t
                if (peek().isNil())
                    root = rotateRight(g_t);
                else {
                    if (g_t == peek().left)
                        peek().left = rotateRight(g_t);
                    else // if (g_t == peek().right)
                        peek().right = rotateRight(g_t);
                }

                // Recolor p_t to Black and g_t to Red
                p_t.isRed = false;
                g_t.isRed = true;

                // return
                return;
            }

            // Case 2(b): g_t -> p_t -> t is RR
            if (g_t.right == p_t && p_t.right == t) {

                // Rotate [R] at g_t
                if (peek().isNil())
                    root = rotateLeft(g_t);
                else {
                    if (g_t == peek().left)
                        peek().left = rotateLeft(g_t);
                    else // if (g_t == peek().right)
                        peek().right = rotateLeft(g_t);
                }

                // Recolor p_t to Black and g_t to Red
                p_t.isRed = false;
                g_t.isRed = true;

                // return
                return;
            }

            // Case 3: if u_t is Black and
            // Case 3(a): g_t -> p_t -> t is LR
            if (g_t.left == p_t && p_t.right == t) {
                // Rotate [L] at p_t and apply Case 2a
                g_t.left = rotateLeft(p_t);

                // Switch reference of t and p_t to restore the g_t -> p_t -> t sequence.
                Entry<T> tmp = t;
                t = p_t;
                p_t = tmp;

                // Continue on the iteration with switched  g_t -> p_t -> t sequence.
                // This time, should apply Case 2a
                assert g_t.left == p_t && p_t.left == t;
                continue;
            }

            // Case 3(b): g_t -> p_t -> t is RL
            if (g_t.right == p_t && p_t.left == t) {
                // Rotate [L] at p_t and apply Case 2a
                g_t.right = rotateRight(p_t);

                // Switch reference of t and p_t to restore the g_t -> p_t -> t sequence.
                Entry<T> tmp = t;
                t = p_t;
                p_t = tmp;

                // Continue on the iteration with switched  g_t -> p_t -> t sequence.
                // This time, should apply Case 2b
                assert g_t.right == p_t && p_t.right == t;
                continue;
            }
        }

    }

    public static void main(String[] args) {
        BST<Integer> t = new RedBlackTree<>();
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

    @Override
    void printTree(BST.Entry<T> node) {
        if (!node.isNil()) {
            printTree(node.left);
            System.out.print(" " + node.element + " " + ((Entry<T>) node).isRed);
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