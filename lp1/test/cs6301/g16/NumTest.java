package cs6301.g16;

import org.junit.Test;

import java.util.Timer;

import static org.junit.Assert.*;

public class NumTest {
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

    static final Num primeNum       = new Num(prime);
    static final Num primeNumMinus  = new Num("-" + prime);
    static final Num piNum          = new Num(pi);
    static final Num piNumMinus     = new Num("-" + pi);

    @Test
    public void karatsubaProduct() throws Exception {
        assertEquals(0, Num.Karatsuba(piNum, primeNum).compareTo(Num.standardProduct(piNum, primeNum)));
//        assertArrayEquals(Num.Karatsuba(primeNum, new Num("999672729978799149856801")).numList.toArray(), Num.standardProduct(primeNum, new Num("999672729978799149856801")).numList.toArray());
//        System.out.println(Num.Karatsuba(new Num("10000000001231"), new Num(99)));
//        assertEquals(0, Num.Karatsuba(primeNumMinus, piNum).compareTo(Num.standardProduct(primeNumMinus, piNum)));
    }

    @Test
    public void standardProduct() throws Exception {
        assertEquals(0, new Num("999672729978799149856801").compareTo(Num.standardProduct(primeNum, primeNum)));
//        assertEquals(0, new Num("35525853783581721469376019321731").compareTo(Num.standardProduct(am, bm)));
//        assertEquals(0, new Num("-35525853783581721469376019321731").compareTo(Num.standardProduct(a, bm)));
//        assertEquals(0, new Num("-35525853783581721469376019321731").compareTo(Num.standardProduct(am, b)));

        // Test reflexivity.
        assertEquals(0, Num.standardProduct(primeNumMinus, piNum).compareTo(Num.standardProduct(piNum, primeNumMinus)));
    }

    @Test
    public void standardSingleDigitProduct() throws Exception {
        Num a = new Num("123456789");
        Integer[] expecteda = {1,0,1,1,1,1,1,1,1,1};
        Integer[] reta = new Integer[expecteda.length];
        assertArrayEquals(expecteda, Num.standardSingleDigitProduct(a, 9).numList.toArray(reta));

        Num b = new Num("43142341235323425658679");
        Integer[] expectedb = {5,9,3,3,9,2,8,2,1,7,1,6,6,7,1,6,0,7,1,1,7,5,1,2};
        Integer[] retb = new Integer[expectedb.length];
        assertArrayEquals(expectedb, Num.standardSingleDigitProduct(b, 5).numList.toArray(retb));
    }

    @Test
    public void add() throws Exception {
        assertEquals(0,Num.add(new Num("1"),new Num("-100")).compareTo(new Num("-99")));
        assertEquals(0,Num.add(new Num("100"),new Num("-100")).compareTo(new Num("0")));
        assertEquals(0,Num.subtract(new Num("100"),new Num("-100")).compareTo(new Num("200")));
    }

    @Test
    public void subtract() throws Exception {
        Num a = new Num("823456789");
        Num am = new Num("-823456789");
        Num b = new Num("43142341235323425658679");
        Num bm = new Num("-43142341235323425658679");

        assertEquals(0,Num.subtract(a, b).compareTo(new Num("-43142341235322602201890")));
        assertEquals(0,Num.subtract(am, bm).compareTo(new Num("43142341235322602201890")));
        assertEquals(0,Num.subtract(am, b).compareTo(new Num("-43142341235324249115468")));
        assertEquals(0,Num.subtract(a, bm).compareTo(new Num("43142341235324249115468")));
    }

    @Test
    public void product() throws Exception {
        long start, end, k, s;
        Num a = new Num(pi+pi+pi+pi+pi+pi+pi+pi+pi+pi+pi+pi+pi+pi+pi);
        Num b = new Num(pi+pi+pi+pi+pi+pi+pi+pi+pi+pi);
        start = System.currentTimeMillis();
        Num kr = Num.Karatsuba(a, b);
        end = System.currentTimeMillis();
        k = end - start;

        start = System.currentTimeMillis();
        Num sr = Num.standardProduct(a, b);
        end = System.currentTimeMillis();
        s = end - start;

        System.out.println(k +" " + s);
        assertEquals(0, kr.compareTo(sr));
        assertTrue(s < k);
    }

    @Test
    public void power() throws Exception {
        Num a = new Num("100");
        long b = 30;
        System.out.println(Num.power(a,b));
    }

    @Test
    public void divide() throws Exception {
    }

    @Test
    public void mod() throws Exception {
    }

    @Test
    public void power1() throws Exception {
    }

    @Test
    public void squareRoot() throws Exception {
    }

    @Test
    public void compareTo() throws Exception {

        Num a = new Num((long)123456789);
        Num b = new Num("43142341235323425658679");
        Num c = new Num((long)-123456789);
        Num cp = new Num("-123456789");
        Num d = new Num("-43142341235323425658679");
        Num dp = new Num("-43142341235323425658679");

        assertEquals(0, a.compareTo(a));
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