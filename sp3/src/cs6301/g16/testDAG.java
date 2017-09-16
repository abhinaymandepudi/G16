/**
 * <h1>Fall 2017 Short Project 3-4</h1>
 * <p>
 * Is a given directed graph a DAG (directed, acyclic graph)?
 * Solve the problem by running DFS on the given graph, and checking
 * if there are any back edges.
 *
 * boolean isDAG(Graph g) { ... }
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-11
 */

package cs6301.g16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class testDAG{

    public static boolean isDAG(Graph g){
        if(!g.isDirected())
            return false;

        DFS dfSearch = new DFS(g);
        dfSearch.dfs();

        if(dfSearch.isCyclic)
            return false;
        else
            return true;
    }

    public static void main(String[] args) throws FileNotFoundException {
        /*
        Test Graph 1: (not DAG)

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

        Test Graph 2: (not DAG)
        3 3
        1 2 1
        2 3 1
        3 1 1

        Test Graph 3: (not DAG)
        5 7
        1 2 1
        2 3 1
        3 4 1
        4 5 1
        1 5 1
        2 5 1
        2 4 1
         */

        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        Graph g = Graph.readGraph(in, true);

        System.out.println("\n==============\nInput Graph:");
        for (Graph.Vertex u : g) {
            System.out.print(u + ": ");
            for (Graph.Edge e : u)
                System.out.print(e + " ");
            System.out.println();
        }

        System.out.println("\n==============\nWhether the graph is DAG: " + isDAG(g));

    }
}
