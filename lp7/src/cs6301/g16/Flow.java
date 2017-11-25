// Starter code for LP7
package cs6301.g16;

import cs6301.g16.Graph.*;

import java.util.*;

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
        initialize();
        LinkedList<Vertex> L = new LinkedList<>();
        g.forEach(v -> {
            if (v.getName() != s.getName() && v.getName() != t.getName())
                L.add(v);
        });
        System.out.println(L);
        boolean done = false;
        while (!done) {
            Iterator<Vertex> it = L.iterator();
            done = true;
            Vertex u = null;
            while (it.hasNext()) {
                u = it.next();
                ResidualGraph.ResidualVertex ru = gf.getVertex(u);
                if (ru.getExcess() == 0)
                    continue;
                int oldHeight = ru.getHeight();
                gf.discharge(ru);
                System.out.println(2);
                if (ru.getHeight() != oldHeight) {
                    done = false;
                    break;
                }
            }
            if (!done) {
                it.remove();
                L.addFirst(u);
            }
            System.out.println(1);
        }

        gf.forEach(vertex -> vertex.forEach(System.out::println));
        int maxFlow = 0;
        for (Edge e : s.adj) {
            maxFlow += gf.getEdge(e).getFlow();
        }

        return maxFlow;
    }


    private void initialize() {
//        for u ∈ V do
//            u.height ← 0; u.excess ← 0
//        for all edges e of G do
//            f( e ) ← 0
//        s.height ← |V|
//        for edge e = ( s, u ) out of s do
//            f( e ) ← c( e )
//            s.excess ← s.excess − c( e )
//            u.excess ← u.excess + c( e )
        gf.getVertex(s).setHeight(g.size());
        s.forEach(edge -> gf.push(edge, capacity(edge)));
    }


    // flow going through edge e
    public int flow(Edge e) {
        return gf.getEdge(e).getFlow();
    }

    // capacity of edge e
    public int capacity(Edge e) {
        return gf.getEdge(e).getCapacity();
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
