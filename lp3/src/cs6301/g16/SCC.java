package cs6301.g16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SCC extends DFS{

    private boolean isFirstDFS=false; // true-first DFS, false-second DFS
    List<Graph.Vertex> topOrder; // list of vertices in topological order
    List<Set<Graph.Vertex>> sccSets; // list of SSCs

    public SCC(Graph g) {
        super(g);
    }

    /**
     * Override extension point functions
     */
    @Override
	void beforeDFS() {
		super.beforeDFS();
		if(isFirstDFS)
			topOrder = new LinkedList<>();
		else
			sccSets = new LinkedList<>();
	}
    @Override
    void outerLoop(Graph.Vertex v){
        super.outerLoop(v);
        if(!isFirstDFS){
            sccSets.add(0, new HashSet<Graph.Vertex>());
        }
    }
    @Override
    void beforeVisitVertex(Graph.Vertex v){
        super.beforeVisitVertex(v);
        if(!isFirstDFS){
			sccSets.get(0).add(v);
		}
    }
    
    @Override
    void finishVisitVertex(Graph.Vertex v){
    	if(isFirstDFS)
    		topOrder.add(0, v);
    }

    /**
     * Get number of strongly connected components in the graph
     * @return num of SCC
     */
    public int scc() {
        // perform first DFS to get topOrderList
    	isFirstDFS=true;
        dfs();

        // get the topological order from the first DFS
        List<Graph.Vertex> topOrder = this.topOrder;

        // perform 2nd DFS in the topological order on the reversed graph
        isFirstDFS=false;
        dfs(topOrder.iterator(), true);

        // print SCCs found in graph
        printSCCs();
        return sccSets.size();
    }

    /**
     * Debug helper - print out SCCs found in the graph
     */
    private void printSCCs() {
    	System.out.println(sccSets);
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

        SCC scc = new SCC(g);
        scc.scc();

    }
}
