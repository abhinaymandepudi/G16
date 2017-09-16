/**
 * <h1>Fall 2017 Short Project 3-1</h1>
 * <p>
 * Topological ordering of a DAG.
 * Implement two algorithms for ordering the nodes of a DAG topologically.
 * Both algorithms should return null if the given graph is not a DAG.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-11
 */

package cs6301.g16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class TopologicalOrder {

    /** Algorithm 1. Remove vertices with no incoming edges, one at a
     *  time, along with their incident edges, and add them to a list.
     */

    public static List<Graph.Vertex> toplogicalOrder1(Graph g) {
        // the graph need to be directed
        if(!g.isDirected())
            return null;

        Graph tmpG = new Graph(g); // make copy of the input graph
        List<Graph.Vertex> topList = new LinkedList<>();
        Queue<Graph.Vertex> q = new LinkedList<>(); // Queue for vertex with in-degree of 0
        for(Graph.Vertex v : tmpG)
            if(v.inDegree()==0)
                q.add(v);

        while (!q.isEmpty()) {
            Graph.Vertex u = q.remove();
            topList.add(u);
            List<Graph.Vertex> affectedVertexes = tmpG.removeVertex(u);
            for(Graph.Vertex affectedV : affectedVertexes) {
                if (affectedV.inDegree()==0)
                    q.add(affectedV);
            }
        }

        if(topList.size()!=g.getN())
            return null;
        else
            return topList;
    }

    /** Algorithm 2. Run DFS on g and add nodes to the front of the output list,
     *  in the order in which they finish.  Try to write code without using global variables.
     */
     public static List<Graph.Vertex> toplogicalOrder2(Graph g) {
        // the graph need to be directed
        if(!g.isDirected())
            return null;

        DFS dfSearch = new DFS(g);
        dfSearch.dfs();

        // the graph is not a DAG if it is cyclic
        if(dfSearch.isCyclic)
            return null;

        return dfSearch.decFinList;
    }

    /**
     * Main function for testing
     * @param args
     */
    public static void main(String[] args) throws FileNotFoundException {

         /*
        Test Graph 1:
        9 12
        1 2 1
        1 3 1
        1 4 1
        2 3 1
        2 6 1
        3 5 1
        3 6 1
        4 3 1
        4 7 1
        5 8 1
        6 8 1
        6 9 1
        7 9 1

        Test Graph 2:
        6 9
        1 3 1
        2 4 1
        3 1 1
        4 6 1
        1 2 1
        2 3 1
        3 4 1
        4 5 1
        5 6 1
         */

        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        Graph g = Graph.readGraph(in,true);

        System.out.println("\nResult 1:\n"+toplogicalOrder1(g));
        System.out.println("\nResult 2:\n"+toplogicalOrder2(g));

    }
}
