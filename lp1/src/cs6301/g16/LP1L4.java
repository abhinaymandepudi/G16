// Starter code for Level 4 driver for lp1

// Change following line to your group number
package cs6301.g16;

import java.util.*;

public class LP1L4 {

    public static void main(String[] args) throws Exception {
        long base = 10;
        if (args.length > 0) {
            base = Integer.parseInt(args[0]);
            // Use above base for all numbers (except I/O, which is in base 10)
        }

        List<Num> store = new ArrayList<>(26);
        ArrayList<ExecutableLine> exe = new ArrayList<>();
        Map<Integer, Integer> lineno = new HashMap<>();
        lineno.put(-1, -1);

        for (int i = 0; i < 26; i++) {
            store.add(null);
        }

        Scanner in = new Scanner(System.in);
        ArrayList<String[]> src = new ArrayList<>();

        boolean newLine = Boolean.TRUE;
        ArrayList<String> line = null;
        String word;
        while (in.hasNext()) {

            if (newLine) {
                newLine = Boolean.FALSE;
                line = new ArrayList<>();
            }

            word = in.next();

            if (word.equals(";")) {
                if (line.size() == 0)
                    break;

                newLine = Boolean.TRUE;

                switch (getType(line)) {
                    case CTRL:
                        exe.add(new ExecutableLine.ControlLine(line, store, lineno, exe.size() - 1));
                        continue;
                    case INFIX:
                        exe.add(new ExecutableLine.InFixExpressionLine(line, store, lineno, exe.size() - 1, base));
                        continue;
                    default:
                        exe.add(new ExecutableLine.PosFixExpressionLine(line, store, base));
                        continue;
                }

            } else
                line.add(word);
        }

        for (int i = 0; i < exe.size(); i++) {
            ExecutableLine l = exe.get(i);

            int next = l.execute();

            if (next == -1)
                continue;

            i = next;
        }

        exe.get(exe.size() - 1).getNum().printList();
    }

    public static ExecutableLine.LineType getType(ArrayList<String> l) throws Exception {
        // First token is line number.
        if (Tokenizer.tokenize(l.get(0)) == Tokenizer.Token.NUM) {
            // Third token is "=".
            if (Tokenizer.tokenize(l.get(2)) == Tokenizer.Token.EQ)
                return ExecutableLine.LineType.INFIX;
                // Otherwise, the token must be "?"
            else {
                assert l.get(2).equals("?");
                return ExecutableLine.LineType.CTRL;
            }
        } else { // POSFIX Expression.
            assert Tokenizer.tokenize(l.get(0)) == Tokenizer.Token.VAR;
            return ExecutableLine.LineType.POSFIX;
        }

    }
}
