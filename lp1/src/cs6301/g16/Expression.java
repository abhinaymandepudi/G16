package cs6301.g16;

import java.util.List;

public interface Expression {
    public Num getValue();

    public abstract class Operation implements Expression {
    }

    public class Var implements Expression {

        private char varname;
        private List<Num> store = null;

        public Var(char varname, List<Num> store) {
            this.varname = varname;
            this.store = store;
        }

        public void assign(Num right) {
            store.set(varname - 'a', right);
        }

        @Override
        public Num getValue() {
            return this.store.get(this.varname - 'a');
        }
    }

    public class Number implements Expression {
        cs6301.g16.Num num;

        public Number(String s) {
            this.num = new Num(s);
        }

        public Number(String s, long radix) {
            this.num = new Num(s, radix);
        }

        @Override
        public Num getValue() {
            return this.num;
        }
    }

    public class Plus extends Operation {

        Expression a;
        Expression b;

        public Plus(Expression b, Expression a) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Num getValue() {
            return Num.add(a.getValue(), b.getValue());
        }
    }

    public class Minus extends Operation {
        Expression a;
        Expression b;

        public Minus(Expression b, Expression a) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Num getValue() {
            return Num.subtract(a.getValue(), b.getValue());
        }
    }

    public class Product extends Operation {

        Expression a;
        Expression b;

        public Product(Expression a, Expression b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Num getValue() {
            return Num.product(a.getValue(), b.getValue());
        }
    }

    public class Divide extends Operation {

        Expression a;
        Expression b;

        public Divide(Expression b, Expression a) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Num getValue() {
            return Num.divide(a.getValue(), b.getValue());
        }
    }

    public class Mod extends Operation {

        Expression a;
        Expression b;

        public Mod(Expression b, Expression a) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Num getValue() {
            return Num.mod(a.getValue(), b.getValue());
        }
    }

    public class Power extends Operation {

        Expression b;
        Expression n;

        public Power(Expression n, Expression b) {
            this.b = b;
            this.n = n;
        }

        @Override
        public Num getValue() {
            return Num.power(b.getValue(), n.getValue());
        }
    }

    public class SquareRoot extends Operation {

        Expression a;

        public SquareRoot(Expression a) {
            this.a = a;
        }

        @Override
        public Num getValue() {
            return Num.squareRoot(a.getValue());
        }
    }
}
