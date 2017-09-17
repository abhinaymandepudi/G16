package cs6301.g16;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NumTest {
    @Test
    public void split() throws Exception {
        Long[] al = {(long) 1, (long) 2, (long) 3, (long) 4, (long) 0, (long) 0, (long) 6, (long) 7, (long) 8, (long) 9};
        Long[] alowl = {(long) 1, (long) 2, (long) 3, (long) 4};
        Long[] ahighl = {(long) 0, (long) 6, (long) 7, (long) 8, (long) 9};

        List<Long> a = new ArrayList<>(Arrays.asList(al));
        List<Long> alow = new LinkedList<>();
        List<Long> ahigh = new LinkedList<>();
        Num.split(a, alow, ahigh, 5);
        assertArrayEquals(alowl, alow.toArray(new Long[alow.size()]));
        assertArrayEquals(ahighl, ahigh.toArray(new Long[ahigh.size()]));

        Long[] alp = {(long) 0, (long) 0, (long) 0, (long) 4, (long) 0, (long) 0, (long) 6, (long) 0, (long) 8, (long) 9};
        Long[] alowlp = {(long) 0, (long) 0, (long) 0, (long) 4};
        Long[] ahighlp = {(long) 0, (long) 6, (long) 0, (long) 8, (long) 9};
        List<Long> ap = new ArrayList<>(Arrays.asList(alp));
        List<Long> alowp = new LinkedList<>();
        List<Long> ahighp = new LinkedList<>();
        Num.split(ap, alowp, ahighp, 5);
        assertArrayEquals(alowlp, alowp.toArray(new Long[alowp.size()]));
        assertArrayEquals(ahighlp, ahighp.toArray(new Long[ahighp.size()]));
    }

    static final String pi =
            "314159265358979323846264338327950288419716939937510582097494459230781640628620" +
                    "899862803482534211706798214808651328230664709384460955058223172535940812848111" +
                    "745028410270193852110555964462294895493038196442881097566593344612847564823378" +
                    "678316527120190914564856692346034861045432664821339360726024914127372458700660" +
                    "631558817488152092096282925409171536436789259036001133053054882046652138414695" +
                    "194151160943305727036575959195309218611738193261179310511854807446237996274956" +
                    "735188575272489122793818301194912983367336244065664308602139494639522473719070" +
                    "217986094370277053921717629317675238467481846766940513200056812714526356082778";
    static final String prime = "999836351599";

    static final Num primeNum = new Num(prime);
    static final Num primeNumMinus = new Num("-" + prime);
    static final Num piNum = new Num(pi);
    static final Num piNumMinus = new Num("-" + pi);
    static final String as = "1024";
    static final String bs = "1919";
    static final String cs = "123456789";
    static final long al = 1024;
    static final long bl = 1919;
    static final long cl = 123456789;

    @Test
    public void karatsubaProduct() throws Exception {
        assertEquals(0, Num.Karatsuba(piNum, primeNum).compareTo(Num.standardProduct(piNum, primeNum)));
        assertEquals(0, Num.Karatsuba(primeNumMinus, piNum).compareTo(Num.Karatsuba(primeNum, piNumMinus)));
        assertEquals(0, Num.Karatsuba(primeNumMinus, piNum).compareTo(Num.standardProduct(primeNum, piNumMinus)));
    }

    @Test
    public void standardProduct() throws Exception {
        assertEquals(0, new Num("999672729978799149856801").compareTo(Num.standardProduct(primeNum, primeNum)));
        assertEquals(0, new Num(al * bl).compareTo(Num.standardProduct(new Num(as), new Num(bs))));
        assertEquals(0, new Num(al * cl).compareTo(Num.standardProduct(new Num(as), new Num(cs))));
        assertEquals(0, new Num(0).compareTo(Num.standardProduct(new Num(0), new Num((long) 898765432))));
        assertEquals(0, new Num(0).compareTo(Num.standardProduct(new Num(391203485), new Num(0))));
    }

    @Test
    public void standardSingleDigitProduct() throws Exception {
        Num a = new Num("43142341235323425658679");
        assertEquals(0, new Num("388281071117910830928111").compareTo(Num
                .SingleDigitProduct(a, 9)));
        assertEquals(0, new Num("301996388647263979610753").compareTo(Num
                .SingleDigitProduct(a, 7)));
        assertEquals(0, new Num("172569364941293702634716").compareTo(Num
                .SingleDigitProduct(a, 4)));
    }

    @Test
    public void add() throws Exception {
        assertEquals(0, Num.add(new Num("388281071117910830928111"), new Num("301996388647263979610753")).compareTo(new Num("690277459765174810538864")));
        assertEquals(0, Num.add(new Num("1"), new Num("-100")).compareTo(new Num("-99")));
        assertEquals(0, Num.add(new Num("100"), new Num("-100")).compareTo(new Num("0")));
        assertEquals(0, Num.add(new Num("99999999"), new Num("1")).compareTo(new Num("100000000")));
        System.out.println(Num.add(new Num("999"), new Num("9990")));
    }

    @Test
    public void subtract() throws Exception {
        Num a = new Num("823456789");
        Num am = new Num("-823456789");
        Num b = new Num("43142341235323425658679");
        Num bm = new Num("-43142341235323425658679");

        assertEquals(0, Num.subtract(a, b).compareTo(new Num("-43142341235322602201890")));
        assertEquals(0, Num.subtract(am, bm).compareTo(new Num("43142341235322602201890")));
        assertEquals(0, Num.subtract(am, b).compareTo(new Num("-43142341235324249115468")));
        assertEquals(0, Num.subtract(a, bm).compareTo(new Num("43142341235324249115468")));
        assertEquals(0, Num.subtract(b, b).compareTo(new Num("0")));
        assertEquals(0, new Num(80).compareTo(Num.subtract(new Num(81), new Num(1))));
        assertEquals(0, new Num(16).compareTo(Num.subtract(new Num(80), new Num(64))));
        assertEquals(0, new Num(16).compareTo(Num.subtract(Num.subtract(new Num(81), new Num(1)), new Num(64))));

    }

    @Test
    public void product() throws Exception {
        long start, end, k, s;
        StringBuilder str1 = new StringBuilder(pi);
        str1.append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi);
        StringBuilder str2 = new StringBuilder(pi);
        str2.append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi)
                .append(pi).append(pi).append(pi).append(pi).append(pi).append(pi).append(pi);
        Num a = new Num(str1.toString());
        Num b = new Num(str2.toString());

        start = System.currentTimeMillis();
        Num sr = Num.standardProduct(a, b);
        end = System.currentTimeMillis();
        s = end - start;

        start = System.currentTimeMillis();
        Num kr = Num.Karatsuba(a, b);
        end = System.currentTimeMillis();
        k = end - start;

        System.out.println(k +" " + s);
        assertEquals(0, kr.compareTo(sr));
        assertTrue(s < k);
    }

//    @Test
//    public void power() throws Exception {
//        Num a = new Num("100");
//        long b = 30;
//        System.out.println(Num.power(a,b));
//    }
//
//    @Test
//    public void divide() throws Exception {
//    }
//
//    @Test
//    public void mod() throws Exception {
//    }
//
//    @Test
//    public void power1() throws Exception {
//    }
//
//    @Test
//    public void squareRoot() throws Exception {
//    }

    @Test
    public void compareTo() throws Exception {

        Num a = new Num((long) 123456788);
        Num ap = new Num((long) 123556789);
        Num app = new Num((long) 123546999);
        Num b = new Num("43142341235323425658679");
        Num c = new Num((long) -123456789);
        Num cp = new Num("-123456789");
        Num d = new Num("-43142341235323425658679");
        Num dp = new Num("-43142341235323425658679");

        assertEquals(-1, a.compareTo(ap));
        assertEquals(1, ap.compareTo(app));
        assertEquals(0, b.compareTo(b));
        assertEquals(0, c.compareTo(cp));
        assertEquals(0, d.compareTo(dp));

        assertEquals(-1, a.compareTo(b));
        assertEquals(1, b.compareTo(a));
        assertEquals(1, a.compareTo(c));
        assertEquals(1, a.compareTo(d));
        assertEquals(1, c.compareTo(d));
        assertEquals(-1, d.compareTo(c));
    }

}