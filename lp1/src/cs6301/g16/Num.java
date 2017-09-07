
// Starter code for lp1.

// Change following line to your group number
package cs6301.g16;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Num  implements Comparable<Num> {

    private static int defaultBase = 10;  // This can be changed to what you want it to be.
    private int base = defaultBase;  // Change as needed
    private boolean sign = true;
    public List<Integer> numList = new LinkedList<>();

    /* Start of Level 1 */
    public Num() {}

    public Num(String s) {

        for (int i = s.length() - 1; i >= 1; i--) {
            numList.add(s.charAt(i) - '0');
        }
        if (s.charAt(0) == '-')
            sign = false;
        if (s.charAt(0) >= '0' && s.charAt(0) <= '9')
            numList.add(s.charAt(0) - '0');

    }

    public Num(long x) {

        sign = (x>=0);
        x = Math.abs(x);
        while(x!=0) {
            numList.add((int) (x%base));
            x = x/base;
        }

    }

    public Num(Num x) {
        base = x.base;
        sign = x.sign;
        numList = (List<Integer>) ((LinkedList<Integer>)x.numList).clone();
    }

    static Integer zeroNext(Iterator<Integer> it) {
        if(it.hasNext())
            return it.next();
        else
            return 0;
    }

    static Num add(Num a, Num b) {
        assert a.base == b.base;

        int base = a.base;
        Num result = new Num();
        result.base = a.base;
        if(a.sign == b.sign) {
            // same sign addition
            result.sign = a.sign;
            int carry = 0;
            Iterator<Integer> it1 = a.numList.iterator();
            Iterator<Integer> it2 = b.numList.iterator();
            while(it1.hasNext() || it2.hasNext() || carry > 0) {
                int sum = zeroNext(it1)+zeroNext(it2)+carry;
                result.numList.add(sum % base);
                carry = sum / base;
            }
        }
        else
        {
            // different sign addition
            int modCompareResult = a.modCompareTo(b);
            switch (modCompareResult)
            {
                case 0:
                {
                    result.numList.add(0);
                    break;
                }
                case 1:
                case -1:
                {
                    Num bigAbsNum = modCompareResult==1?a:b; // num with bigger abs
                    Num smallAbsNum = modCompareResult==-1?a:b; // num with smaller abs
                    result.sign = bigAbsNum.sign;

                    int borrow = 0;
                    Iterator<Integer> it1 = bigAbsNum.numList.iterator();
                    Iterator<Integer> it2 = smallAbsNum.numList.iterator();

                    int numOfLeadingZero = 0;

                    while(it1.hasNext() || it2.hasNext()) {
                        int sub = zeroNext(it1)-zeroNext(it2)-borrow;
                        borrow = sub<0?1:0;
                        if(sub<0) {
                            sub += base;
                            borrow = 1;
                        }
                        result.numList.add(sub);
                        if(sub == 0)
                            numOfLeadingZero++;
                        else
                            numOfLeadingZero = 0;
                    }

                    // remove leading zero - if the result is zero, we should keep one zero in the numList
                    for(int i=0;i<numOfLeadingZero&&result.numList.size()>1;i++)
                        result.numList.remove(result.numList.size()-1);

                    break;
                }
                default:
                    assert false; //error
            }
        }
        return result;
    }

    static Num subtract(Num a, Num b) {
        Num c = new Num(b);
        c.sign = !c.sign;
        return add(a,c);
    }

    public static Num standardSingleDigitProduct(Num a, int n) {
        Num ret = new Num();
        ret.sign = a.sign;
        ret.base = a.base;
        int carry = 0;
        for (Integer x : a.numList) {
            int product = x * n + carry;
            carry = product / ret.base;
            ret.numList.add(product % ret.base);
        }
        if (carry != 0)
            ret.numList.add(carry);
        return ret;
    }

    public static Num standardProduct(Num a, Num b) {
        Num ret = new Num("0");
        List<Integer> shift = new ArrayList<>(a.numList.size());
        Num singleDigitProduct;

        for (Integer n: a.numList) {
            singleDigitProduct = standardSingleDigitProduct(b, n);
            singleDigitProduct.numList.addAll(0, shift);
            ret = Num.add(ret, singleDigitProduct);
            shift.add(0);
        }
        return ret;
    }
    // Implement Karatsuba algorithm for excellence credit
    static Num product(Num a, Num b) {
	return null;
    }

    // Use divide and conquer
    static Num power(Num a, long n) {
	return null;
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
    static Num power(Num a, Num n) {
	return null;
    }

    static Num squareRoot(Num a) {
	return null;
    }
    /* End of Level 2 */


    // Utility functions
    public int modCompareTo(Num other) {
        int modCompareResult = -2;
        if(numList.size() == other.numList.size()) {
            Iterator<Integer> it1 = numList.iterator();
            Iterator<Integer> it2 = other.numList.iterator();
            modCompareResult = 0;
            while(it1.hasNext() || it2.hasNext()) {
                int elementCompareResult = zeroNext(it1).compareTo(zeroNext(it2));
                if (elementCompareResult!=0) // override compare result from lower digit
                    modCompareResult = elementCompareResult;
            }
        }
        else if(numList.size() > other.numList.size())
            modCompareResult = 1;
        else // numList.size() < other.numList.size()
            modCompareResult = -1;
        return modCompareResult;
    }

    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
        // todo - improve the logic to perform sign test first.
        assert base == other.base;

        boolean sameSign = (sign==other.sign);

        // handle zero
        if(numList.size()==1 && other.numList.size()==1 && numList.get(0)==0 && other.numList.get(0)==0)
            return 0;

        switch (modCompareTo(other))
        {
            case 0:
                return sameSign?0:(sign?1:-1);
            case 1:
                return sameSign?(sign?1:-1):(sign?-1:1);
            case -1:
                return sameSign?(sign?-1:1):(sign?1:-1);
            default:
                assert false; // error
                return 0;
        }
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

    public int base() { return base; }
}
