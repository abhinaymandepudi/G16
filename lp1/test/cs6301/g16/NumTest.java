package cs6301.g16;

import org.junit.Test;

import static org.junit.Assert.*;

public class NumTest {
    @Test
    public void standardProduct() throws Exception {
        Num a = new Num("823456789");
        Num b = new Num("43142341235323425658679");
        System.out.println(Num.standardProduct(a, b).numList);
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
    }

    @Test
    public void product() throws Exception {
        Num a = new Num("1234567891011");
        Num b = new Num("43142341235323425658679");
    }

    @Test
    public void power() throws Exception {
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
        Num d = new Num("-43142341235323425658679");

        assertEquals(0, a.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(0, d.compareTo(d));

        assertEquals(-1, a.compareTo(b));
        assertEquals(1, b.compareTo(a));
        assertEquals(1, a.compareTo(c));
        assertEquals(1, a.compareTo(d));
        assertEquals(1, c.compareTo(d));
        assertEquals(-1, d.compareTo(c));
    }

}