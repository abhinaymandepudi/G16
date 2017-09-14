
// Starter code for lp1.

// Change following line to your group number
package cs6301.g16;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Num implements Comparable<Num> {

    private static int defaultBase = 10;  // This can be changed to what you want it to be.

    private int base = defaultBase;  // Change as needed

    /**
     * The sign of the Num. -1 for negative, 0 for zero, 1 for positive number. Use int rather than
     * boolean could eliminate duplicate representation of zero. Final keyword requires the sign be
     * initialize in constructor.
     */
    private final int sign;
    public List<Long> numList = new LinkedList<>();

    public static final Num ZERO = new Num(new LinkedList<Long>(Arrays.asList((long) 0)), 0);

    /* Start of Level 1 */
//    public Num() {}

    /**
     * Construct Num from String. The String representation consists of an optional minus or plus
     * sign followed by a sequence of one or more digits in the default radix 10.
     *
     * @param s String representation of Num.
     */
    public Num(String s) {
        int sign = 1, cursor = 0;
        final int len = s.length();

        // Leading sign, if exists, must appear at beginning.
        int plusIndex = s.lastIndexOf('+');
        int minusIndex = s.lastIndexOf('-');
        if (plusIndex > 0 || minusIndex > 0)
            throw new NumberFormatException("Sign character in the middle.");

        // If leading sign exist, set cursor to 1.
        if (plusIndex >= 0 || minusIndex >= 0) {
            cursor = 1;

            // "-" at front, otherwise keep default value 1.
            if (minusIndex == 0) {
                sign = -1;
            }
        }

        // Skip leading zeros and compute number of digits in magnitude
        while (cursor < len && s.charAt(cursor) == '0') {
            cursor++;
        }

        if (cursor == len) {
            this.sign = 0;
            numList = ZERO.numList;
            return;
        }

        this.sign = sign;

        for (int i = cursor; i <= s.length() - 1; i++) {
            numList.add(0, (long) (s.charAt(i) - '0'));
        }
    }

    /**
     * Internal constructor used to create Num with same numList and specific sign, such as abs() or
     * constant ZERO. Note that, the numList inside Num should be immutable in general. Operations
     * on the numList inside Num often leads to new Num with newly allocated numList (for example
     * add/substract/product). Thus, copying the reference to the numList would be sufficient.
     *
     * @param list List allocated already.
     * @param sign New sign to the number.
     */
    private Num(List<Long> list, int sign) {
        this.sign = (list.size() == 0 ? 0 : sign);
        this.numList = list;
    }

    public Num(long x) {

        if (x == 0)
            this.sign = 0;
        else if (x > 0)
            this.sign = 1;
        else
            this.sign = -1;

        x = Math.abs(x);

        while (x != 0) {
            numList.add((x % base));
            x = x / base;
        }

    }

    public Num(Num x) {
        base = x.base;
        sign = x.sign;
        numList = (List<Long>) ((LinkedList<Long>) x.numList).clone();
    }

    static Long zeroNext(Iterator<Long> it) {
        if (it.hasNext())
            return it.next();
        else
            return (long) 0;
    }

    static Num add(Num a, Num b) {
        assert a.base == b.base;

        if (b.sign == 0)
            return a;
        if (a.sign == 0)
            return b;

        if (a.sign == b.sign) {
            return new Num(add(a.numList, b.numList, a.base), a.sign);
        }

        int cmp = a.modCompareTo(b);
        if (cmp == 0)
            return ZERO;

        List<Long> result = cmp > 0 ? subtract(a.numList, b.numList, a.base)
                : subtract(b.numList, a.numList, a.base);

        return new Num(result, cmp == a.sign ? 1 : -1);
    }

    /**
     * Internal implementation of two numList add with specific base. Return a newly allocated
     * LinkedList of the result.
     */
    private static List<Long> add(List<Long> a, List<Long> b, long base) {

        Iterator<Long> it1 = a.iterator();
        Iterator<Long> it2 = b.iterator();

        long carry = 0;

        List<Long> result = new LinkedList<>();

        while (it1.hasNext() || it2.hasNext() || carry > 0) {
            long sum = zeroNext(it1) + zeroNext(it2) + carry;
            result.add(sum % base);
            carry = sum / base;
        }

        return result;
    }

    /**
     * Internal implementation of two numList substract with specific base. Return a newly allocated
     * LinkedList of the result. Note that first numList a must be larger than b.
     */
    private static List<Long> subtract(List<Long> a, List<Long> b, long base) {
        int numOfLeadingZero = 0;

        Iterator<Long> it1 = a.iterator();
        Iterator<Long> it2 = b.iterator();

        int borrow = 0;

        List<Long> result = new LinkedList<>();

        while (it1.hasNext() || it2.hasNext()) {
            long sub = zeroNext(it1) - zeroNext(it2) - borrow;
            borrow = sub < 0 ? 1 : 0;
            if (sub < 0) {
                sub += base;
                borrow = 1;
            }
            result.add(sub);
            if (sub == 0)
                numOfLeadingZero++;
            else
                numOfLeadingZero = 0;
        }

        // remove leading zero - if the result is zero, we should keep one zero in the numList
//        for (int i = 0; i < numOfLeadingZero && result.size() > 1; i++)
//            result.remove(result.size() - 1);

        return result.subList(0, result.size() - numOfLeadingZero);
    }

    public static Num subtract(Num a, Num b) {
        if (b.sign == 0)
            return a;
        if (a.sign == 0)
            return b.negate();
        if (a.sign != b.sign)
            return new Num(add(a.numList, b.numList, a.base), a.sign);

        int cmp = a.modCompareTo(b);
        if (cmp == 0)
            return ZERO;

        List<Long> result = cmp > 0 ? subtract(a.numList, b.numList, a.base)
                : subtract(b.numList, a.numList, a.base);

        return new Num(result, cmp == a.sign ? 1 : -1);
    }

    /**
     * Returns a Num whose value is {@code (-this)}.
     *
     * @return {@code -this}
     */
    public Num negate() {
        return new Num(this.numList, -this.sign);
    }

    public static Num standardSingleDigitProduct(Num a, long n) {
        if (n == 0)
            return ZERO;
        List<Long> result = new LinkedList<>();
        long carry = 0;
        long product;
        for (Long x : a.numList) {
            product = x * n + carry;
            carry = product / a.base;
            result.add(product % a.base);
        }
        if (carry != 0)
            result.add(carry);
        return new Num(result, a.sign == Long.signum(n) ? 1 : -1);
    }

    public static Num standardProduct(Num a, Num b) {
        Num ret = new Num("0");
        List<Long> shift = new LinkedList<>();
        Num singleDigitProduct;

        for (Long n : a.numList) {
            singleDigitProduct = standardSingleDigitProduct(b, n);
            singleDigitProduct.numList.addAll(0, shift);
            ret = Num.add(ret, singleDigitProduct);
            shift.add((long) 0);
        }
        return a.sign == b.sign ? ret : ret.negate();
    }
//
//    public static Num Karatsuba(Num a, Num b) {
//        Num ret = KaratsubaProduct(a, b);
//        ret.sign = a.sign == b.sign;
//        return ret;
//    }
//
//    public static Num KaratsubaProduct(Num a, Num b) {
//        if (a.numList.size() <= 1 || b.numList.size() <= 1)
//            return standardProduct(a, b);
//        int m = Integer.max(a.numList.size(), b.numList.size());
//        int m2 = m / 2;
//        Num ha = new Num(), la = new Num(), hb = new Num(), lb = new Num();
//        split(a, ha, la, m2);
//        split(b, hb, lb, m2);
//        Num z0 = KaratsubaProduct(la, lb);
//        Num z1 = KaratsubaProduct(add(ha, la), add(hb, lb));
//        Num z2 = KaratsubaProduct(ha, hb);
//
//        Num z3 = subtract(subtract(z1, z2), z0);
//        for (int i = 0; i < m2; i++)
//            z3.numList.add(0, (long) 0);
//
//        for (int i = 0; i < m2 * 2; i++)
//            z2.numList.add(0, (long) 0);
//
//        return add(add(z2, z3), z0).trim();
//    }

    static Num product(Num a, Num b) {
        return null;
    }

    static void split(Num x, Num hx, Num lx, int m) {
        if (m >= x.numList.size()) {
            hx.numList.add((long) 0);
            lx.numList.addAll(x.numList);
        } else {
            hx.numList.addAll(x.numList.subList(m, x.numList.size()));
            hx.trim();
            lx.numList.addAll(x.numList.subList(0, m));
            lx.trim();
        }
    }

    Num trim() {
        if (this.numList.size() > 1)
            for (int i = this.numList.size() - 1; this.numList.get(i) == 0 && i > 0; i--)
                this.numList.remove(i);
        return this;
    }

    //     Use divide and conquer
    static Num power(Num a, long n) {
        if (n == 0) {
            return new Num("1");
        }
        if (n == 1) {
            return a;
        }
        if (n % 2 == 0) {    // n is even
            return standardProduct(power(a, n / 2), power(a, n / 2));
        } else
            return standardProduct(power(a, n / 2), standardProduct(power(a, n / 2), a));

    }
    /* End of Level 1 */

    /* Start of Level 2 */
    static Num divide(Num a, Num b) {
        return null;
    }

    static Num mod(Num a, Num b) {
        return null;
    }

    // Use divide and conquer
    public static Num power(Num a, Num n) {
        return null;
    }

    static Num squareRoot(Num a) {
        return null;
    }
    /* End of Level 2 */


    /**
     * Compare the numList with other Num, ignoring sign.
     *
     * @param other The other numList to be compared to.
     * @return -1, 0 or 1 as this numList is less than, equal to or greater than the other numList.
     */
    private int modCompareTo(Num other) {
        if (this.numList.size() > other.numList.size())
            return 1;
        if (this.numList.size() < other.numList.size())
            return -1;

        Iterator<Long> it1 = this.numList.iterator();
        Iterator<Long> it2 = other.numList.iterator();
        int result;
        while (it1.hasNext()) {
            result = zeroNext(it1).compareTo(zeroNext(it2));
            if (result != 0)
                return result;
        }
        return 0;
    }

    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
        assert base == other.base;

        if (this.sign == other.sign) {
            switch (this.sign) {
                case 1:
                    return modCompareTo(other);
                case -1:
                    return other.modCompareTo(this);
                default:
                    return 0;
            }
        }
        return this.sign > other.sign ? 1 : -1;
    }

    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    void printList() {
    }

    // Return number to a string in base 10
    public String toString() {
        return this.numList.toString();
    }

    public int base() {
        return base;
    }
}
