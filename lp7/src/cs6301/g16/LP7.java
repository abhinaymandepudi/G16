
package cs6301.g16;

import cs6301.g16.Graph.Vertex;
import cs6301.g16.Graph.Edge;

public class LP7 {
    static int VERBOSE = 0;

    public static void main(String[] args) {
        if (args.length > 0) {
            VERBOSE = Integer.parseInt(args[0]);
        }
        java.util.Scanner in = new java.util.Scanner(System.in);
        Graph g = Graph.readDirectedGraph(in);
        Timer timer = new Timer();
        int s = in.nextInt();
        int t = in.nextInt();
        java.util.HashMap<Graph.Edge, Integer> capacity = new java.util.HashMap<>();
        for (Vertex u : g) {
            for (Edge e : u) {
                capacity.put(e, 1);
            }
        }
        Flow f = new Flow(g, g.getVertex(s), g.getVertex(t), capacity);
        int value = f.relabelToFront();

        System.out.println(value);

        if (VERBOSE > 0) {
            for (Vertex u : g) {
                System.out.print(u + " : ");
                for (Edge e : u) {
                    System.out.print(e + ":" + f.flow(e) + "/" + f.capacity(e) + " | ");
                }
                System.out.println();
            }
        }

        System.out.println(timer.end());
    }
}

