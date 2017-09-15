package cs6301.g16;

/**
 * <h1>Fall 2017 Short Project 3</h1>
 * <p>
 *     Helper class extends GraphAlgorithm to provide functionality of DFS in Graph object.
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-09-11
 */

import cs6301.g00.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class DFS extends GraphAlgorithm<DFS.DFSVertex> {

    // Class to store information about a vertex in this algorithm
    static class DFSVertex {
        boolean seen;
        int fin; //finish time
        int dis; //discover time
        int top; // topological order
        int cno;
        Graph.Vertex parent;
        DFSVertex(Graph.Vertex u) {
            reset();
        }
        void reset() {
            seen = false;
            fin = 0;
            dis = 0;
            top = 0;
            cno = 0;
            parent = null;
        }
    }

    int topNum;
    int time;
    int cno;
    List<Graph.Vertex> decFinList;

    public DFS(Graph g) {
        super(g);

        node = new DFSVertex[g.size()];
        // Create array for storing vertex properties
        for(Graph.Vertex u: g) {
            node[u.getName()] = new DFSVertex(u);
        }
    }

    public void dfs() {
        topNum = g.size();
        time = 0;
        cno = 0;
        decFinList = new LinkedList<>();

        for(Graph.Vertex u: g) {
            DFSVertex du = getVertex(u);
            du.reset();
        }

        Iterator<Graph.Vertex> it = g.iterator();

        while (it.hasNext()) {
            Graph.Vertex u = it.next();
            if(!seen(u)){
                cno++;
                dfsVisit(u);
            }
        }

    }

    void dfsVisit(Graph.Vertex v) {
        DFSVertex dv = getVertex(v);
        dv.seen = true;
        dv.dis = ++time;
        dv.cno = cno;
        for(Graph.Edge edge : v) {
            Graph.Vertex u = edge.otherEnd(v);
            if(!seen(u)) {
                getVertex(u).parent = v;
                dfsVisit(u);
            }
        }
        dv.fin = ++time;
        dv.top = topNum--;
        decFinList.add(0,v);
    }

    boolean seen(Graph.Vertex u) {
        return getVertex(u).seen;
    }

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

        for (Graph.Vertex u : g) {
            System.out.print(u + ": ");
            for (Graph.Edge e : u)
                System.out.print(e + " ");
            System.out.println();
        }

        if (g.size()>0) {
            DFS dfSearch = new DFS(g);
            dfSearch.dfs();
            System.out.println(dfSearch.decFinList);
        }
    }
}
