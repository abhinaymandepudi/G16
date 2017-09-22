// Starter code for Level 3 driver for lp1

// Change following line to your group number
package cs6301.g16;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LP1L3 {

    public static void main(String[] args) throws Exception {

        List<Num> store = new ArrayList<>(26);

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
                src.add(line.toArray(new String[line.size()]));
            } else
                line.add(word);
        }

        ArrayList<ExecutableLine> exe = new ArrayList<>(src.size());
        for (int i = 0; i < src.size(); i++) {
            String[] l = src.get(i);
            exe.add(new ExecutableLine.ExpressionLine(l, store));
        }

        for (int i = 0; i < exe.size(); i++) {
            ExecutableLine l = exe.get(i);

            int next = l.execute();

            if (next == 0)
                continue;
        }

        exe.get(exe.size() - 1).getNum().printList();


        /**
         * test case:

         a = 999 ;
         b = 8 ;
         c = a b ^ a b * - ;
         d = c 99999 / | | ;
         x = d 1234 % ;
         ;


         +999
         +8
         +992027944069944027984009
         +56121
         +591
         10: 1 9 5
         */
    }
}
