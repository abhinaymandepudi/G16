
// Starter code for lp1.

// Change following line to your group number
package cs6301.g16;

import java.util.*;

public class Num implements Comparable<Num> {

    private static int defaultBase = 10;

    private int base = defaultBase;

    /**
     * The number is store in LinkedList with most significant digit at the end of the LinkedList,
     * and the least significant digit at the beginning.
     */
    public List<Long> numList = new LinkedList<>();

    /**
     * The sign of the Num. -1 for negative, 0 for zero, 1 for positive number. Use int rather than
     * boolean could eliminate duplicate representation of zero. Final keyword requires the sign be
     * initialize in constructor.
     */
    private final int sign;

    /**
     * Constant for sign. sign of zero can be access as ZERO.sign.
     */
    private static final int SIGN_POSITIVE = 1;
    private static final int SIGN_NEGATIVE = -1;

    /**
     * Constant ZERO.
     */
    public static final Num ZERO = new Num(Collections.unmodifiableList(new ArrayList<>(Collections.singleton((long) 0))), 0);

    /**
     * Constant ONE.
     */
    public static final Num ONE = new Num(Collections.unmodifiableList(new ArrayList<>(Collections.singleton((long) 1))), 0);

    private static ArrayList<Long> buffer = new ArrayList<>();


    /* Start of Level 1 */

    /**
     * Construct Num from String. The String representation consists of an optional minus or plus
     * sign followed by a sequence of one or more digits in the default radix 10.
     *
     * @param s String representation of Num.
     */
    public Num(String s) {
        int sign = SIGN_POSITIVE, cursor = 0;
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
                sign = SIGN_NEGATIVE;
            }
        }

        // Skip leading zeros and compute number of digits in magnitude
        while (cursor < len && s.charAt(cursor) == '0') {
            cursor++;
        }

        if (cursor == len) {
            this.sign = ZERO.sign;
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
        this.sign = (list.size() == 0 ? ZERO.sign : sign);
        this.numList = list;
    }

    /**
     * Construct Num from long integer.
     *
     * @param x Long representation of Num.
     */
    public Num(long x) {
        if (x == 0) {
            this.sign = ZERO.sign;
            this.numList = ZERO.numList;
            return;
        }

        this.sign = x > 0 ? SIGN_POSITIVE : SIGN_NEGATIVE;

        x = Math.abs(x);
        while (x != 0) {
            numList.add((x % base));
            x = x / base;
        }
    }

//    public Num(Num x) {
//        base = x.base;
//        sign = x.sign;
//        numList = (List<Long>) ((LinkedList<Long>) x.numList).clone();
//    }

    private static Long zeroNext(Iterator<Long> it) {
        if (it.hasNext())
            return it.next();
        else
            return (long) 0;
    }

    /**
     * Return the Num with value of {@code (a + b)}.
     *
     * @param a First value in add operation.
     * @param b Second value in add operation.
     * @return {@code (a + b)}.
     */
    public static Num add(Num a, Num b) {
        assert a.base == b.base;

        if (b.sign == ZERO.sign)
            return a;
        if (a.sign == ZERO.sign)
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
        return result.subList(0, result.size() - numOfLeadingZero);
    }

    /**
     * Return the Num with value of {@code (a - b)}.
     *
     * @param a Value to be subtracted from.
     * @param b Value to be subtracted with.
     * @return {@code (a + b)}.
     */
    public static Num subtract(Num a, Num b) {
        if (b.sign == ZERO.sign)
            return a;
        if (a.sign == ZERO.sign)
            return b.negate();
        if (a.sign != b.sign)
            return new Num(add(a.numList, b.numList, a.base), a.sign);

        int cmp = a.modCompareTo(b);
        if (cmp == 0)
            return ZERO;

        List<Long> result = cmp > 0 ? subtract(a.numList, b.numList, a.base)
                : subtract(b.numList, a.numList, a.base);

        return new Num(result, cmp == a.sign ? SIGN_POSITIVE : SIGN_NEGATIVE);
    }

    /**
     * Returns a Num whose value is {@code (-this)}.
     *
     * @return {@code -this}
     */
    public Num negate() {
        return new Num(this.numList, -this.sign);
    }

    /**
     * Returns a Num whose value is {@code abs(this)}.
     *
     * @return {@code abs(this)}
     */
    public Num abs() {
        return new Num(this.numList, SIGN_POSITIVE);
    }

    /**
     * Return the Num with value {@code (a * n)}, where n be a long integer.
     *
     * @param a Num to be multiplied.
     * @param n Long value to be multiplied.
     * @return {@code (a * n)},
     */
    static Num SingleDigitProduct(Num a, long n) {
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
        return new Num(result, a.sign == Long.signum(n) ? SIGN_POSITIVE : SIGN_NEGATIVE);
    }

    /**
     * Standard n-squared algorithm for product. Return the Num with value {@code (a * b)}.
     *
     * @param a First Num in product.
     * @param b Second Num in product.
     * @return {@code (a * b)}.
     */
    public static Num standardProduct(Num a, Num b) {
        // For all function call, adjust the first Num to be a shorter (or less) one.
        if (a.numList.size() > b.numList.size())
            return standardProduct(b, a);

        // Return ZERO if a == 0.
        if (a.sign == 0)
            return ZERO;
        // After adjusting size, b shouldn't be zero at this point.
        assert b.sign != 0;

        // If a has only one element, directly call SingleDigitProduct(b, a).
        if (a.numList.size() == 1)
            return SingleDigitProduct(b, a.numList.get(0));

        // Otherwise, perform standard product with n square algorithm.
        Num ret = new Num("0");
        int shift = 0;
        Num singleDigitProduct;

        for (Long n : a.numList) {
            singleDigitProduct = SingleDigitProduct(b, n);
            singleDigitProduct.shift(shift);
            ret = Num.add(ret, singleDigitProduct);
            shift++;
        }
        return a.sign == b.sign ? new Num(ret.numList, SIGN_POSITIVE) : new Num(ret.numList, SIGN_NEGATIVE);
    }

    public static Num Karatsuba(Num a, Num b) {
        Num absRet = KaratsubaProduct(a.abs(), b.abs());
        return new Num(absRet.numList, a.sign * b.sign);
    }

    public static Num KaratsubaProduct(Num a, Num b) {
        assert a.numList.size() != 0;
        assert b.numList.size() != 0;

//        if (a.numList.size() <= 1 || b.numList.size() <= 1)
//            return standardProduct(a, b);
        if (a.numList.size() == 1)
            return SingleDigitProduct(b, a.numList.get(0));
        if (b.numList.size() == 1)
            return SingleDigitProduct(a, b.numList.get(0));
        int m = Integer.min(a.numList.size(), b.numList.size());
        int m2 = m / 2;
//        Num ha = new Num(), la = new Num(), hb = new Num(), lb = new Num();
        List<Long> alow = new LinkedList<>();
        List<Long> ahigh = new LinkedList<>();
        List<Long> blow = new LinkedList<>();
        List<Long> bhigh = new LinkedList<>();
        split(a.numList, alow, ahigh, m2);
        split(b.numList, blow, bhigh, m2);

        Num al = alow.size() == 0 ? ZERO : new Num(alow, SIGN_POSITIVE);
        Num ah = new Num(ahigh, SIGN_POSITIVE);
        Num bl = blow.size() == 0 ? ZERO : new Num(blow, SIGN_POSITIVE);
        Num bh = new Num(bhigh, SIGN_POSITIVE);

        assert al.numList.size() != 0;
        assert bl.numList.size() != 0;
        assert ah.numList.size() != 0;
        assert bh.numList.size() != 0;
//        split(a, ha, la, m2);
//        split(b, hb, lb, m2);
        Num z0 = KaratsubaProduct(al, bl);
        Num z2 = KaratsubaProduct(ah, bh);
        Num z1 = KaratsubaProduct(add(ah, al), add(bh, bl));

        Num z3 = subtract(subtract(z1, z2), z0);

        z3.shift(m2);
//        if (!z3.isZero())
//            for (int i = 0; i < m2; i++)
//                z3.numList.add(0, (long) 0);

        z2.shift(m2 * 2);
//        for (int i = 0; i < m2 * 2; i++)
//            z2.numList.add(0, (long) 0);

        return add(add(z2, z3), z0);
    }

    static Num product(Num a, Num b) {
        return Karatsuba(a, b);
    }

//    static void split(Num x, Num hx, Num lx, int m) {
//        if (m >= x.numList.size()) {
//            hx.numList.add((long) 0);
//            lx.numList.addAll(x.numList);
//        } else {
//            hx.numList.addAll(x.numList.subList(m, x.numList.size()));
//            hx.trim();
//            lx.numList.addAll(x.numList.subList(0, m));
//            lx.trim();
//        }
//    }

    /**
     * Split list x into two part, with first part in length k. Upon calling of this function, the k
     * should always be less then the length of x, i.e., split based on the shorted list when
     * perform production.
     *
     * @param x     The list to be split.
     * @param xlow  The fist part (lower significant part in Num) store.
     * @param xhigh The second part (higher significant part in Num) store.
     * @param k     The length of first part.
     */
    public static void split(List<Long> x, List<Long> xlow, List<Long> xhigh, int k) {
        int i = 0;
        long d;
        buffer.clear();
        Iterator<Long> it = x.iterator();
        while (i < k) {
            assert it.hasNext();

            i++;
            d = it.next();

            // Possible leading zero in lowe part, put in buffer first before another non-zero digit occured.
            if (d == 0) {
                buffer.add(d);
                continue;
            }

            // Non-zero digit, put all zero before to xlow, clear buffer and add current digit.
            xlow.addAll(buffer);
            buffer.clear();
            xlow.add(d);
        }
        buffer.clear();

        // No leading zero should appear with high part.
        while (it.hasNext())
            xhigh.add(it.next());

//        xlow.addAll(x.subList(0, k));
//        xhigh.addAll(x.subList(k, x.size()));
    }

    public void shift(int n) {
        if (this.isZero())
            return;
        if (n > 0) {
            for (int i = 1; i <= n; i++)
                this.numList.add(0, (long) 0);
            return;
        }
        if (n < 0) {
            for (int i = n; i < 0; i++)
                this.numList.remove(0);
        }
    }

    /**
     * Return {@code b^n}.
     *
     * @param b Base in format Num.
     * @param n Exponent in format long integer.
     * @return {@code b^n}.
     */
    static Num power(Num b, long n) {
        if (n == 0)
            return ONE;

        if (n == 1)
            return b;

        Num s = power(b, n / 2);
        // n is even
        if (n % 2 == 0)
            return product(s, s);
        else
            return product(s, product(s, b));
    }
    /* End of Level 1 */

    /* Start of Level 2 */
    static Num divide(Num a, Num b) {
        return null;
    }

    static Num mod(Num a, Num b) {
        return null;
    }

    /**
     * Return {@code b^n}.
     *
     * @param b Base in format Num.
     * @param n Exponent in format Num.
     * @return {@code b^n}.
     */
    public static Num power(Num b, Num n) {
        if (n.isZero())
            return ONE;

        if (n.numList.size() == 1)
            return power(b, n.numList.get(0));

        long a0 = n.numList.get(0);
        n.shift(-1);
        return product(power(power(b, n), n.base), power(b, new Num(a0)));
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
        int result = 0, dresult;
        while (it1.hasNext()) {
            dresult = zeroNext(it1).compareTo(zeroNext(it2));
            if (dresult != 0)
                result = dresult;
        }
        return result;
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

    public boolean isZero() {
        return this.sign == 0;
    }

    // Return number to a string in base 10
    public String toString() {
        return this.numList.toString();
    }

    public int base() {
        return base;
    }
}
