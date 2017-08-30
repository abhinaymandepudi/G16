/**
 * <h1>Fall 2017 Short Project 1</h1>
 * <p>
 * Implement breadth-first search (BFS), and solve the problem of finding the diameter of a tree
 * that works as follows: Run BFS, starting at an arbitrary node as root. Let u be a node at maximum
 * distance from the root. Run BFS again, with u as the root. Output diameter: path from u to a node
 * at maximum distance from u.
 *
 * @author Binhan Wang / Hanlin He / Zheng Gao
 * @version 1.0
 * @since 2017-08-25
 */

package cs6301.g16;

import cs6301.g00.Graph;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

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
     * @param startPoint : Specific vertex to start BFS.
     * @return A list containing the vertexes in the longest path.
     */
    private static LinkedList<Graph.Vertex> longestPath(Graph.Vertex startPoint) {
        LinkedList<Graph.Vertex> longestPath = new LinkedList<>();

        // Set to store visited Vertex
        Set<Graph.Vertex> visitedSet = new HashSet<>();
        // Queue of vertexes about to visit
        Queue<Graph.Vertex> aboutToVisitQueue = new LinkedList<>();
        // Map from a vertex to its preceding vertex, used to find the path
        Map<Graph.Vertex, Graph.Vertex> precedingMap = new HashMap<>();
        precedingMap.put(startPoint, null);

        // Start BFS, add startPoint to queue.
        aboutToVisitQueue.add(startPoint);

        Graph.Vertex lastVertex = null;
        while (aboutToVisitQueue.size() > 0) {
            // Take (p, v) from queue.
            Graph.Vertex s = aboutToVisitQueue.poll();
            for (Graph.Edge e : s) {
                // For each edge s->v, if v is not marked, put v in queue, and put (v, s) to map.
                Graph.Vertex v = e.otherEnd(s);
                if (!visitedSet.contains(v)) {
                    aboutToVisitQueue.add(v);
                    precedingMap.put(v, s);
                }
            }
            visitedSet.add(s);
            lastVertex = s;
        }

        // generate longest path backward using preceding map
        while (lastVertex != null) {
            longestPath.addFirst(lastVertex);
            lastVertex = precedingMap.get(lastVertex);
        }

        return longestPath;
    }

    /**
     * Return a longest path in g.  Algorithm is correct only if g is a tree.
     *
     * @param g : The graph to be processed.
     * @return A list containing the vertexes in the longest path.
     */

    /* Graph.Vertex in Graph.java is not public, cannot access from outside. If no modification
     * the source file is not allowed, need to add Graph.java into this package. */
    public static LinkedList<Graph.Vertex> diameter(Graph g) {

        // handle corner case: when the graph has no vertex
        if(!g.iterator().hasNext())
            return new LinkedList<>();

        // start from one of the vertex in the tree and find a longest path
        LinkedList<Graph.Vertex> d1 = longestPath(g.iterator().next());

        // the last Vertex in the longest path should be a leaf in the tree
        Graph.Vertex leaf = d1.get(d1.size() - 1);

        // longest path start from a leaf should be a diameter of the tree
        return longestPath(leaf);

    }
    
    public static void main(String[] args) throws FileNotFoundException {

        /* Test Tree:
        8 7
        1 2 1
        1 3 1
        1 4 1
        2 5 1
        2 6 1
        4 7 1
        7 8 1
         */

        /* Test Tree:
        22 21
        8   4   1
        4   21  1
        7   1   1
        5   1   1
        6   1   1
        21  3   1
        21  2   1
        2   9   1
        15  9   1
        16  9   1
        17  16  1
        22  3   1
        10  22  1
        11  10  1
        11  12  1
        13  11  1
        14  13  1
        18  22  1
        18  20  1
        19  18  1
        21  1   1

        Diameter: [14, 13, 11, 10, 22, 3, 21, 2, 9, 16, 17]
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
            System.out.print(u + ": ");
            for (Graph.Edge e : u)
                System.out.print(e + " ");
            System.out.println();
        }

		LinkedList<Graph.Vertex> d = diameter(g);
        System.out.println("A diameter in the tree is:");
		System.out.print(d);
	}
}
