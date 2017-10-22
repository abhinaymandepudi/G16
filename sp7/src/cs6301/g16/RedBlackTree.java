
/**
 * Starter code for Red-Black Tree
 */
package cs6301.g16;

import java.util.Comparator;

public class RedBlackTree<T extends Comparable<? super T>> extends BST<T> {
    static class Entry<T> extends BST.Entry<T> {

        static Entry NIL = new Entry();

        boolean isRed;

        Entry() {
            super();
            isRed = false;
        }

        @Override @SuppressWarnings("unchecked")
        public Entry<T> getNIL() {
            return (Entry<T>) NIL;
        }

        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            isRed = true;
        }
    }

    RedBlackTree() {
        super();
    }
}

