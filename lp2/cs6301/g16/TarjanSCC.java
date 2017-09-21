package cs6301.g16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class TarjanSCC extends GraphAlgorithm<TarjanSCC.TarjanVertex> {

    public static class TarjanVertex {
        int dis; // discover time
        int low; // low link value
        int cno; // component number
        boolean onStack; // flag indicates whether the vertex is currently on stack

        TarjanVertex() {
            reset();
        }

        void reset() {
            dis = 0;
            low = 0;
            cno = 0;
            onStack = false;
        }
    }

    int VERBOSE;
    private int time;
    private int cno;

    public TarjanSCC(Graph g) {
        super(g);
        VERBOSE = 1;

        node = new TarjanVertex[g.size()];
        // Create array for storing vertex properties
        for (Graph.Vertex u : g) {
            node[u.getName()] = new TarjanVertex();
        }
    }

    public int scc() {
        // initialization
        time = 0;
        cno = 0;
        LinkedList<Graph.Vertex> stack = new LinkedList<>();
        for (Graph.Vertex v : g) {
            getVertex(v).reset();
        }

        // begin dfs
        for (Graph.Vertex v : g) {
            if (getVertex(v).dis == 0) {
                // unseen
                strongConnect(v, stack);
            }
        }
        return cno;
    }

    private void strongConnect(Graph.Vertex v, LinkedList<Graph.Vertex> stack) {
        // Set the depth index for v to the smallest unused index
        if(VERBOSE > 9) {
            System.out.println("Visit Vertex: " + v);
            System.out.println(stack);
        }
        TarjanVertex tv = getVertex(v);

        tv.dis = ++time;
        tv.low = time;

        stack.push(v);
        tv.onStack = true;

        // Consider successors of v
        for (Graph.Edge e : v) {
            Graph.Vertex w = e.otherEnd(v);
            TarjanVertex tw = getVertex(w);
            if (tw.dis == 0) {
                // Successor w has not yet been visited; recurse on it
                strongConnect(w, stack);
                tv.low = Math.min(tv.low, tw.low);
            } else if (tw.onStack) {
                // Successor w is in stack S and hence in the current SCC
                // Note: The next line may look odd - but is correct.
                // It says w.index not w.lowlink; that is deliberate and from the original paper
                tv.low = Math.min(tv.low, tw.dis);
            }
        }

        // If v is a root node, pop the stack and generate an SCC
        if (tv.low == tv.dis) {
            // start a new strongly connected component
            Graph.Vertex w;
            cno++;
            do {
                w = stack.pop();
                TarjanVertex tw = getVertex(w);
                tw.onStack = false;
                tw.cno = cno;
            }
            //add w to current strongly connected component
            while (w != v);
        }
    }

    public void setVERBOSE(int VERBOSE) {
        this.VERBOSE = VERBOSE;
    }

    public static void main(String[] args) throws FileNotFoundException {
         /*
        Test Graph 1: (the on we used in class)

        11 17
        1 11 1
        2 3 1
        2 7 1
        3 10 1
        4 1 1
        4 9 1
        5 4 1
        5 7 1
        5 8 1
        6 3 1
        7 8 1
        8 2 1
        9 11 1
        10 6 1
        11 3 1
        11 4 1
        11 6 1

         */

        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        Graph g = Graph.readGraph(in,true);

        System.out.println("\n==============\nInput Graph:");
        for (Graph.Vertex u : g) {
            System.out.print(u + ": ");
            for (Graph.Edge e : u)
                System.out.print(e + " ");
            System.out.println();
        }

        TarjanSCC scc = new TarjanSCC(g);
        scc.VERBOSE = 10;
        scc.scc();
        for(Graph.Vertex v:g)
            System.out.println(v.toString()+" "+scc.getVertex(v).cno);

        System.out.println("\n==============\nNumber of Strongly Connected Components:"+scc.cno);

    }
}
