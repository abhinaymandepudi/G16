/**
 * <h1>Fall 2017 Short Project 1</h1>
 * <p>
 * Implement breadth-first search (BFS), and solve the problem of finding the diameter of a tree
 * that works as follows: Run BFS, starting at an arbitrary node as root. Let u be a node at maximum
 * distance from the root. Run BFS again, with u as the root. Output diameter: path from u to a node
 * at maximum distance from u.
 *
 * @author Binhan Wang / Hanlin He / Zheng Gao
 * @version 0.1
 * @since 2017-08-25
 */

package cs6301.g16;

import cs6301.g00.Graph;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.Iterator;

import java.util.Set;
import java.util.Map;
import java.util.Queue;

import java.util.LinkedList;
import java.util.HashSet;
import java.util.HashMap;

public class BFS {

    /**
     * Helper function: Return a longest path from the vertex - startPoint.
     *
     * @param startPoint
     * @return A list containing the vertexes in the longest path.
     */
    static LinkedList<Graph.Vertex> longestPath(Graph.Vertex startPoint) {
        LinkedList<Graph.Vertex> longestPath = new LinkedList<>();

        // Set to store visited Vertex
        Set<Graph.Vertex> visitedSet = new HashSet<Graph.Vertex>();
        // Queue of vertexes about to visit
        Queue<Graph.Vertex> aboutToVisitQueue = new LinkedList<Graph.Vertex>();
        // Map from a vertex to its preceding vertex, used to find the path
        Map<Graph.Vertex,Graph.Vertex> precedingMap = new HashMap<Graph.Vertex,Graph.Vertex>();
        precedingMap.put(startPoint,null);

        // Start BFS
        aboutToVisitQueue.add(startPoint);

        Graph.Vertex lastVertex = null;
        while(aboutToVisitQueue.size()>0) {
            Graph.Vertex s = aboutToVisitQueue.poll();
            for (Iterator<Graph.Edge> it = s.iterator(); it.hasNext(); ) {
                Graph.Edge e = it.next();
                Graph.Vertex v = e.otherEnd(s);
                if(!visitedSet.contains(v)) {
                    aboutToVisitQueue.add(v);
                    precedingMap.put(v,s);
                }
            }
            visitedSet.add(s);
            lastVertex = s;
        }

        while(lastVertex!=null) {
            longestPath.addFirst(lastVertex);
            lastVertex = precedingMap.get(lastVertex);
        }

        return longestPath;
    }

    /**
     * Return a longest path in g.  Algorithm is correct only if g is a tree.
     *
     * @param g
     * @return A list containing the vertexes in the longest path.
     */

    /* Graph.Vertex in Graph.java is not public, cannot access from outside. If no modification
     * the source file is not allowed, need to add Graph.java into this package. */
    static LinkedList<Graph.Vertex> diameter(Graph g) {

        // start from one of the vertex in the tree and find a longest
        LinkedList<Graph.Vertex> d1 = longestPath(g.iterator().next());

        // the last Vertex in the diameter is a leaf in the tree
        Graph.Vertex leaf = d1.get(d1.size()-1);

        // find the longest path start from a leaf, it should be a diameter of the tree
        return longestPath(leaf);

    }
    
    public static void main(String[] args) throws FileNotFoundException {

        /*
        Test Tree:
        8 7 1 2 1 1 3 1 1 4 1 2 5 1 2 6 1 4 7 1 7 8 1
         */

		Scanner in;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		Graph g = Graph.readGraph(in);

		for (Graph.Vertex u : g) {
			System.out.print(u);
			String graph = u.toString();
            for (Iterator<Graph.Edge> it = u.iterator(); it.hasNext(); ) {
                Graph.Edge e = it.next();
                Graph.Vertex v = e.otherEnd(u);
                System.out.print(e + " ");
            }
			System.out.println();
		}

		LinkedList<Graph.Vertex> d = diameter(g);
        System.out.println("A diameter in the tree is:");
		System.out.print(d);
	}
}
