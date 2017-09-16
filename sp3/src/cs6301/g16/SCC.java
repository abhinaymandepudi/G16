/**
 * <h1>Fall 2017 Short Project 3-1</h1>
 * <p>
 * Strongly connected components of a directed graph.  Implement the
 * algorithm for finding strongly connected components of a directed
 * graph (see page 617 of Cormen et al, Introduction to algorithms,
 * 3rd ed.).  Run DFS on G and create a list of nodes in decreasing
 * finish time order.  Find G^T, the graph obtained by reversing all
 * edges of G.  Note that the Graph class has a field revAdj that is
 * useful for this purpose.  Run DFS on G^T, but using the order of
 * the list output by the first DFS.  Each DSF tree in the second DFS
 * is a strongly connected component.
 *
 * int stronglyConnectedComponents(Graph g) { ... }
 * Each node is marked with a component number, and the function returns
 * the number of strongly connected components of G.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-11
 */

package cs6301.g16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class SCC extends DFS {

    public SCC(Graph g) {
        super(g);
    }

    public int getStronglyConnectedComponents() {
        // reference for input graph
        Graph tmpGraph = g;
        // perform the algorithm on a copied graph object, avoid modifying the input graph
        this.g = new Graph(g);
        // init parallel array on the new graph copy
        initParallelArray();
        this.dfs();

        // get the decreasing finish time list
        List<Graph.Vertex> firstDecFinList = this.decFinList;

        // reverse the graph
        this.g.reverseGraph();
        // perform 2nd DFS in the descending finish time order of 1st DFS
        this.dfs(firstDecFinList.iterator());

        // restore the original graph object
        this.g = tmpGraph;
        return cno;
    }

    public static int stronglyConnectedComponents(Graph g) {
        SCC SCC = new SCC(g);
        return SCC.getStronglyConnectedComponents();
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

        System.out.println("\n==============\nNumber of Strongly Connected Components:"+stronglyConnectedComponents(g));

    }
}
