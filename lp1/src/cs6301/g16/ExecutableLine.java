package cs6301.g16;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public interface ExecutableLine {

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

    class ExpressionLine implements ExecutableLine {

        private Expression.Var left;
        private Expression right = null;

        /**
         * Postfix expression constructor.
         *
         * @param line Array of token in String for a line.
         */
        public ExpressionLine(String[] line, List<Num> store) throws Exception {
            assert Tokenizer.tokenize(line[0]) == Tokenizer.Token.VAR;

            this.left = new Expression.Var(line[0].charAt(0), store);

            if (line.length == 1)
                return;

            assert line.length > 2;
            assert Tokenizer.tokenize(line[1]) == Tokenizer.Token.EQ;

            Deque<Expression> expressions = new ArrayDeque<>();
            for (int i = 2; i < line.length; i++) {
                if (line[i].matches("[a-z]"))
                    expressions.push(new Expression.Var(line[i].charAt(0), store));
                else if (line[i].matches("\\d+"))
                    expressions.push(new Expression.Number(line[i]));
                else {

                    assert Tokenizer.tokenize(line[i]) == Tokenizer.Token.OP;

                    if (line[i].equals("+"))
                        expressions.push(new Expression.Plus(expressions.pop(), expressions.pop()));
                    else if (line[i].equals("-"))
                        expressions.push(new Expression.Minus(expressions.pop(), expressions.pop()));
                    else if (line[i].equals("*"))
                        expressions.push(new Expression.Product(expressions.pop(), expressions.pop()));
                    else if (line[i].equals("/"))
                        expressions.push(new Expression.Divide(expressions.pop(), expressions.pop()));
                    else if (line[i].equals("%"))
                        expressions.push(new Expression.Mod(expressions.pop(), expressions.pop()));
                    else if (line[i].equals("^"))
                        expressions.push(new Expression.Power(expressions.pop(), expressions.pop()));
                    else if (line[i].equals("|"))
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

    class ControlLine implements ExecutableLine {
        private Expression.Var condition;
        private int pos;
        private int neg;

        public ControlLine(String[] line, List<Num> store) {
        }

        @Override
        public int execute() {
            return 0;
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
