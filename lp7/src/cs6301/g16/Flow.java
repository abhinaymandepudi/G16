// Starter code for LP7
package cs6301.g16;

import cs6301.g16.Graph.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Flow {

    Graph g;
    ResidualGraph gf;
    Vertex s, t;

    public Flow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity) {
        this.g = g;
        this.gf = new ResidualGraph(g, capacity, false);
        this.s = s;
        this.t = t;
    }

    // Return max flow found by Dinitz's algorithm
    public int dinitzMaxFlow() {

        List<List<Edge>> paths = new LinkedList<>();

        while (true) {
            BellmanFord bf = new BellmanFord(gf);
            boolean result = bf.computeShortestPaths(gf.getVertex(s), gf.getVertex(t), paths);
            if (!result) // t not reachable from s in residual graph.
                break;
            BellmanFord.printAllShortestPath(paths);
            paths.forEach(gf::augment);
        }

        int maxFlow = 0;
        for (Edge e : s.adj) {
            maxFlow += gf.getEdge(e).getFlow();
        }

        return maxFlow;
    }

    // Return max flow found by relabelToFront algorithm
    public int relabelToFront() {
        return 0;
    }

    // flow going through edge e
    public int flow(Edge e) {
        return 0;
    }

    // capacity of edge e
    public int capacity(Edge e) {
        return 0;
    }

    /* After maxflow has been computed, this method can be called to
       get the "S"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutS() {
        return null;
    }

    /* After maxflow has been computed, this method can be called to
       get the "T"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutT() {
        return null;
    }
}
