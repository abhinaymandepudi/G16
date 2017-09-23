package cs6301.g16;

import java.util.*;

public interface ExecutableLine {

    public enum LineType {POSFIX, INFIX, CTRL}

    /**
     * Execute current line and return the next line number. If no specific line number, return -1.
     *
     * @return Next line number.
     */
    int execute();

    Num getNum();

    /**
     * Print current line result to standard output.
     */
    void print();

    class PosFixExpressionLine implements ExecutableLine {

        private Expression.Var left;
        private Expression right = null;

        /**
         * Postfix expression constructor.
         *
         * @param line Array of token in String for a line.
         */
        public PosFixExpressionLine(ArrayList<String> line, List<Num> store, long base) throws Exception {
            assert Tokenizer.tokenize(line.get(0)) == Tokenizer.Token.VAR;

            this.left = new Expression.Var(line.get(0).charAt(0), store);

            if (line.size() == 1)
                return;

            assert line.size() > 2;
            assert Tokenizer.tokenize(line.get(1)) == Tokenizer.Token.EQ;

            Deque<Expression> expressions = new ArrayDeque<>();
            for (int i = 2; i < line.size(); i++) {
                if (line.get(i).matches("[a-z]"))
                    expressions.push(new Expression.Var(line.get(i).charAt(0), store));
                else if (line.get(i).matches("\\d+"))
                    expressions.push(new Expression.Number(line.get(i), base));
                else {

                    assert Tokenizer.tokenize(line.get(i)) == Tokenizer.Token.OP;

                    if (line.get(i).equals("+"))
                        expressions.push(new Expression.Plus(expressions.pop(), expressions.pop()));
                    else if (line.get(i).equals("-"))
                        expressions.push(new Expression.Minus(expressions.pop(), expressions.pop()));
                    else if (line.get(i).equals("*"))
                        expressions.push(new Expression.Product(expressions.pop(), expressions.pop()));
                    else if (line.get(i).equals("/"))
                        expressions.push(new Expression.Divide(expressions.pop(), expressions.pop()));
                    else if (line.get(i).equals("%"))
                        expressions.push(new Expression.Mod(expressions.pop(), expressions.pop()));
                    else if (line.get(i).equals("^"))
                        expressions.push(new Expression.Power(expressions.pop(), expressions.pop()));
                    else if (line.get(i).equals("|"))
                        expressions.push(new Expression.SquareRoot(expressions.pop()));
                }
            }
            assert expressions.size() == 1;

            this.right = expressions.pop();
        }

        @Override
        public Num getNum() {
            return left.getValue();
        }

        @Override
        public int execute() {
            if (this.right != null)
                this.left.assign(this.right.getValue());
            this.print();
            return -1;
        }

        @Override
        public void print() {
            assert this.left != null;
            System.out.println(this.left.getValue());
        }
    }

    class InFixExpressionLine implements ExecutableLine {

        private Expression.Var left;
        private Expression right = null;
        private Map<Integer, Integer> lineno;

        /**
         * Postfix expression constructor.
         *
         * @param line Array of token in String for a line.
         */
        public InFixExpressionLine(ArrayList<String> line, List<Num> store, Map<Integer, Integer> lineno, int actLine, long base) throws Exception {

            assert Tokenizer.tokenize(line.get(0)) == Tokenizer.Token.NUM;
            this.lineno = lineno;
            this.lineno.put(Integer.valueOf(line.get(0)), actLine);

            assert Tokenizer.tokenize(line.get(1)) == Tokenizer.Token.VAR;
            this.left = new Expression.Var(line.get(1).charAt(0), store);

            assert line.size() > 2;
            assert Tokenizer.tokenize(line.get(2)) == Tokenizer.Token.EQ;

            List<String> posfix = ShuntingYard.InfixToPostfix(line.subList(3, line.size()));

            Deque<Expression> expressions = new ArrayDeque<>();

            for (String e : posfix) {
                if (e.matches("[a-z]"))
                    expressions.push(new Expression.Var(e.charAt(0), store));
                else if (e.matches("\\d+"))
                    expressions.push(new Expression.Number(e, base));
                else {

                    assert Tokenizer.tokenize(e) == Tokenizer.Token.OP;

                    if (e.equals("+"))
                        expressions.push(new Expression.Plus(expressions.pop(), expressions.pop()));
                    else if (e.equals("-"))
                        expressions.push(new Expression.Minus(expressions.pop(), expressions.pop()));
                    else if (e.equals("*"))
                        expressions.push(new Expression.Product(expressions.pop(), expressions.pop()));
                    else if (e.equals("/"))
                        expressions.push(new Expression.Divide(expressions.pop(), expressions.pop()));
                    else if (e.equals("%"))
                        expressions.push(new Expression.Mod(expressions.pop(), expressions.pop()));
                    else if (e.equals("^"))
                        expressions.push(new Expression.Power(expressions.pop(), expressions.pop()));
                    else if (e.equals("|"))
                        expressions.push(new Expression.SquareRoot(expressions.pop()));
                }
            }

            assert expressions.size() == 1;

            this.right = expressions.pop();
        }

        @Override
        public Num getNum() {
            return left.getValue();
        }

        @Override
        public int execute() {
            if (this.right != null)
                this.left.assign(this.right.getValue());
//            System.out.println(this.right);
            return -1;
        }

        @Override
        public void print() {
            assert this.left != null;
            System.out.println(this.left.getValue());
        }
    }

    class ControlLine implements ExecutableLine {
        private Expression.Var condition;
        private int pos;
        private int neg = -1;
        private Map<Integer, Integer> lineno;

        public ControlLine(ArrayList<String> line, List<Num> store, Map<Integer, Integer> lineno, int actLine) throws Exception {
//            7 y ? 5 ;
            assert Tokenizer.tokenize(line.get(0)) == Tokenizer.Token.NUM;
            this.lineno = lineno;
            this.lineno.put(Integer.valueOf(line.get(0)), actLine);

            assert Tokenizer.tokenize(line.get(1)) == Tokenizer.Token.VAR;
            this.condition = new Expression.Var(line.get(1).charAt(0), store);

            assert Tokenizer.tokenize(line.get(2)) == Tokenizer.Token.CTRL;
            assert line.size() >= 4;
            this.pos = Integer.valueOf(line.get(3));

            if (line.size() > 5) {
                assert line.get(4).equals(":");
                this.neg = Integer.valueOf(line.get(5));
            }
        }

        @Override
        public int execute() {
            if (this.condition.getValue().compareTo(Num.ZERO) != 0)
                return this.lineno.get(this.pos);
            return this.lineno.get(this.neg);
        }

        @Override
        public Num getNum() {
            return condition.getValue();
        }

        @Override
        public void print() {

        }
    }
}
