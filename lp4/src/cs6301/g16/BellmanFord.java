/**
 * <h1>Fall 2017 Long Project 4</h1>
 * <p>
 * Extend BellmanFord Shortest Path to print all shortest path between 2 vertices
 * <p>
 *
 * @author Binhan Wang (bxw161330) / Hanlin He (hxh160630) / Zheng Gao (zxg170430)
 * @version 1.0
 * @since 2017-10-25
 */

package cs6301.g16;

import cs6301.g16.Graph.Edge;
import cs6301.g16.Graph.Vertex;

import java.util.*;

public class BellmanFord extends GraphAlgorithm<BellmanFord.BFVertex> {

    static class BFVertex {
        int dis, count;
        List<Edge> pe; // edge connect to parent
        boolean seen;

        private Vertex v;

        BFVertex(Vertex v) {
            super();
            this.v = v;
            reset();
        }

        void reset() {
            dis = Integer.MAX_VALUE;
            count = 0;
            pe = new LinkedList<>();
            seen = false;
        }

        Vertex getParent() {
            if (pe == null)
                return null;
            else
                return pe.get(0).otherEnd(v);
        }
    }

    private boolean negCycle;
    private Vertex start;

    public BellmanFord(Graph g) {
        super(g);
        node = new BFVertex[g.size()];
        for (Vertex v : g) {
            node[v.getName()] = new BFVertex(v);
        }
        start = null;
    }

    private void runBF(Vertex s) {

        for (int i = 0; i < node.length; i++) {
            node[i].reset();
        }
        negCycle = false;

        Queue<Vertex> q = new LinkedList<>();
        BFVertex bs = getVertex(s);
        bs.dis = 0;
        bs.seen = true;
        q.add(s);

        while (!q.isEmpty()) {
            Vertex u = q.remove();
            BFVertex bu = getVertex(u);
            bu.seen = false;
            bu.count += 1;
            if (bu.count >= g.size()) {
                negCycle = true;
                break;
            }
            for (Edge e : u) {
                Vertex v = e.otherEnd(u);
                BFVertex bv = getVertex(v);
                if (bv.dis > bu.dis + e.weight) {
                    bv.dis = bu.dis + e.weight;

                    bv.pe.clear();
                    bv.pe.add(e);

                    if (!bv.seen) {
                        q.add(v);
                        bv.seen = true;
                    }
                }
                else if (bv.dis == bu.dis + e.weight){
                    bv.pe.add(e);
                }
            }
        }

        start = s;
    }

    public boolean bellmanFord(Vertex s, Vertex u) {

        if (start != null && !start.equals(s)) {
            start = null;
        }

        if (start == null) {
            runBF(s);
        }

        if (negCycle) {
            System.out.printf("Negative cycle detected!\n");
            return false;
        } else {
            printAllShortestPaths(s, u, new StringBuilder());
            return true;
        }

    }

    private void printAllShortestPaths(Vertex s,Vertex u, StringBuilder path){
        path.append(u.toString());

        if(u.getName()==s.getName()){
            System.out.println(path.reverse().toString());
        }
        else{
            BFVertex bu = getVertex(u);
            if(bu.pe.isEmpty()){
                System.out.println("No path");
            }
            else {
                path.append(" ");
                for(Edge e:bu.pe){
                    Vertex p = e.otherEnd(u);
                    printAllShortestPaths(s, p, new StringBuilder(path));
                }
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("Graph:\n    5 6 \n" +
                "    1 2 2\n" +
                "    1 3 3\n" +
                "    2 4 5\n" +
                "    3 4 4\n" +
                "    4 5 1\n" +
                "    5 1 -7\n" +
                "    1 5\n");
        Scanner in = new Scanner("5 6 \n" +
                "    1 2 2\n" +
                "    1 3 3\n" +
                "    2 4 5\n" +
                "    3 4 4\n" +
                "    4 5 1\n" +
                "    5 1 -7\n" +
                "    1 5");
        Graph g = Graph.readDirectedGraph(in);
        List<List<Edge>> shortestPath = new LinkedList<>();
        BellmanFord bf = new BellmanFord(g);

        bf.bellmanFord(g.getVertex(1), g.getVertex(5));
    }

}
