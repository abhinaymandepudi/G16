
/** Starter code for Red-Black Tree
 */
package cs6301.g00;

import java.util.Comparator;

public class RedBlackTree<T extends Comparable<? super T>> extends BST<T> {
    static class Entry<T> extends BST.Entry<T> {
        boolean isRed;
        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            isRed = true;
        }
    }

    RedBlackTree() {
	super();
    }
}

