
// change following line to your group number
package cs6301.g16;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

public class Euler extends GraphAlgorithm<Euler.EulerVertex> {

    static class EulerVertex {
        Iterator<Graph.Edge> it;
        List<Graph.Edge> tour;

        EulerVertex(Graph.Vertex v) {
            reset(v);
        }
        void reset(Graph.Vertex v) {
            it = v.iterator();
            tour = null;
        }
        boolean hasNext(){
            return it.hasNext();
        }
        Graph.Edge next() {
            return it.next();
        }
    }

    int VERBOSE;
    List<Graph.Edge> tour;
    Graph.Vertex start;

    // Constructor
    Euler(Graph g, Graph.Vertex start) {
        super(g);
        this.start = start;
        VERBOSE = 1;
        tour = new LinkedList<>();
        node = new EulerVertex[g.size()];
        // Create parallel vertex array
        for (Graph.Vertex u : g) {
            node[u.getName()] = new EulerVertex(u);
        }
    }

    // To do: function to find an Euler tour
    public List<Graph.Edge> findEulerTour() {
        findTours();
        if (VERBOSE > 9) {
            printTours();
        }
        stitchTours();
        return tour;
    }

    /* To do: test if the graph is Eulerian.
     * If the graph is not Eulerian, it prints the message:
     * "Graph is not Eulerian" and one reason why, such as
     * "inDegree = 5, outDegree = 3 at Vertex 37" or
     * "Graph is not strongly connected"
     */
    boolean isEulerian() {

        // Test if the graph is directed
        if(!g.directed) {
            System.out.println("Graph is not Eulerian");
            System.out.println("Reason: Graph is not directed");
            return false;
        }

        // Test whether inDegree and outDegree of each vertex are equivalent
        for (Graph.Vertex v : g) {
            int outDegree = v.adj.size();
            int inDegree = v.revAdj.size();
            if(inDegree!=outDegree) {
                System.out.println("Graph is not Eulerian");
                System.out.printf("inDegree = %d, outDegree = %d at Vertex %d",inDegree,outDegree,v.name);
                return false;
            }
        }

        // Test whether the graph is strongly connected
        TarjanSCC sccAlg = new TarjanSCC(g);
        if( sccAlg.scc() != 1) {
            System.out.println("Graph is not Eulerian");
            System.out.println("Reason: Graph is not strongly connected");
            return false;
        }

        // when all the tests are passed, the graph is eulerian
        return true;

    }

    // Helper function - find a tour from vertex u
    void findSubTour(Graph.Vertex u, List<Graph.Edge> tour) {
        while (getVertex(u).hasNext()) {
            Graph.Edge e = getVertex(u).next();
            tour.add(e);
            u = e.otherEnd(u);
        }
    }

    // Find tours starting at vertices with unexplored edges
    void findTours() {
        for(Graph.Vertex v:g) {
            getVertex(v).reset(v);
        }

        if(start!=null){
            List<Graph.Edge> subTour = new LinkedList<>();
            getVertex(start).tour = subTour;
            findSubTour(start,subTour);
        }

        for(Graph.Vertex v : g) {
            if(getVertex(v).hasNext()) {
                List<Graph.Edge> subTour = new LinkedList<>();
                getVertex(v).tour = subTour;
                findSubTour(v,subTour);
            }
        }
    }

    /* Print tours found by findTours() using following format:
     * Start vertex of tour: list of edges with no separators
     * Example: lp2-in1.txt, with start vertex 3, following tours may be found.
     * 3: (3,1)(1,2)(2,3)(3,4)(4,5)(5,6)(6,3)
     * 4: (4,7)(7,8)(8,4)
     * 5: (5,7)(7,9)(9,5)
     *
     * Just use System.out.print(u) and System.out.print(e)
     */
    void printTours() {
        for(Graph.Vertex v : g) {
            EulerVertex ev = getVertex(v);
            if(ev.tour!=null) {
                Graph.Vertex u = v;
                Iterator<Graph.Edge> it = ev.tour.iterator();
                System.out.print(u+": ");
                while(it.hasNext()) {
                    Graph.Edge e = it.next();
                    System.out.print(e);
                }
                System.out.println();
            }
        }
    }

    // Stitch tours into a single tour using the algorithm discussed in class
    void stitchTours() {

        tour.clear();
        if(start!=null)
            explore(start);
        else {
            for(Graph.Vertex v : g){
                if(getVertex(v).tour!=null) {
                    explore(v);
                    break;
                }
            }
        }
    }

    // Helper function for Stitch tours
    private void explore(Graph.Vertex v) {
        if(getVertex(v).tour==null) {
            System.out.println("Error - explore vertex without sub tour");
            return;
        }
        Graph.Vertex tmp = v;
        for(Graph.Edge e:getVertex(v).tour) {
            this.tour.add(e);
            tmp = e.otherEnd(tmp);
            if(getVertex(tmp).tour!=null && tmp!=v)
                explore(tmp);
        }
    }

    void setVerbose(int v) {
        VERBOSE = v;
    }
}
