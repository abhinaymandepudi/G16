package cs6301.g16;

import java.util.List;

public interface Expression {
    Num getValue();

    class Var implements Expression {

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

    class Number implements Expression {
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

    class Plus implements Expression {

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

    class Minus implements Expression {
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

    class Product implements Expression {

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

    class Divide implements Expression {

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

    class Mod implements Expression {

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

    class Power implements Expression {

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

    class SquareRoot implements Expression {

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
