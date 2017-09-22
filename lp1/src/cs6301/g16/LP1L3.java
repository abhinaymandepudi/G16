// Starter code for Level 3 driver for lp1

// Change following line to your group number
package cs6301.g16;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LP1L3 {

    public static List<Num> store = new ArrayList<>(26);

    public LP1L3() {
        for (int i = 0; i < 26; i++) {
            store.add(null);
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        LP1L3 x = new LP1L3();
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

        ArrayList<Line> exe = new ArrayList<>(src.size());
        for (String[] l : src)
            exe.add(new Line(l, store));

        Num last = null;
        for (Line l : exe)
            System.out.println(last = l.execute());

        assert last != null;
        last.printList();

    }
}
