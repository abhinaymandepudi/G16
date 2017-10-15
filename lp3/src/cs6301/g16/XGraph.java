
/**
 * @author rbk
 * Ver 1.0: 2017/09/29
 * Example to extend Graph/Vertex/Edge classes to implement algorithms in which nodes and edges
 * need to be disabled during execution.  Design goal: be able to call other graph algorithms
 * without changing their codes to account for disabled elements.
 **/

package cs6301.g16;

import cs6301.g00.ArrayIterator;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.LinkedList;

public class XGraph extends Graph {
    public static class XVertex extends Vertex {

        boolean disabled = false;
        List<XEdge> xadj = new LinkedList<>();
        List<XEdge> xrevAdj = new LinkedList<>();
        Set<XVertex> shrinkedVs = null;

        XVertex(int n, Set<XVertex> vs) {
            super(n);
            this.shrinkedVs = vs;
        }

        XVertex(Vertex u) {
            super(u);
        }

        boolean isDisabled() {
            return disabled;
        }

        void disable() {
            disabled = true;
        }

        void enable() {
            disabled = false;
        }

        @Override
        public Iterator<Edge> iterator() {
            return new XVertexIterator(this);
        }

        class XVertexIterator implements Iterator<Edge> {
            XEdge cur;
            Iterator<XEdge> it;

            XVertexIterator(XVertex u) {
                this.it = u.xadj.iterator();
            }

            public boolean hasNext() {
                if (!it.hasNext()) {
                    return false;
                }
                cur = it.next();
                while (cur.isDisabled() && it.hasNext()) {
                    cur = it.next();
                }
                return !cur.isDisabled();
            }

            public Edge next() {
                return cur;
            }

            public void remove() {
            }
        }
    }

    static class XEdge extends Edge {
        XEdge superEdge = null;
        Edge origEdge = null;

        XEdge(XVertex from, XVertex to, XEdge e) {
            super(from, to, e.weight);
            superEdge = e;
        }

        XEdge(XVertex from, XVertex to, Edge e) {
            super(from, to, e.weight);
            origEdge = e;
        }

        boolean isDisabled() {
            XVertex xfrom = (XVertex) from;
            XVertex xto = (XVertex) to;
            return weight > 0 || xfrom.isDisabled() || xto.isDisabled();
        }
    }

    XVertex[] xv; // vertices of graph

    public XGraph(Graph g) {
        super(g);
        xv = new XVertex[2 * g.size()]; // Extra space is allocated in array for
        // nodes to be added later
        for (Vertex u : g) {
            xv[u.getName()] = new XVertex(u);
        }

        // Make copy of edges
        for (Vertex u : g) {
            for (Edge e : u) {
                Vertex v = e.otherEnd(u);
                XVertex x1 = getVertex(u);
                XVertex x2 = getVertex(v);
                XEdge xe = new XEdge(x1, x2, e);
                x1.xadj.add(xe);
                x2.xrevAdj.add(xe);
            }
        }
    }

    @Override
    public Iterator<Vertex> iterator() {
        return new XGraphIterator(this);
    }

    class XGraphIterator implements Iterator<Vertex> {
        Iterator<XVertex> it;
        XVertex xcur;

        XGraphIterator(XGraph xg) {
            this.it = new ArrayIterator<XVertex>(xg.xv, 0, xg.size() - 1); // Iterate
            // over
            // existing
            // elements
            // only
        }

        public boolean hasNext() {
            if (!it.hasNext()) {
                return false;
            }
            xcur = it.next();
            while (xcur.isDisabled() && it.hasNext()) {
                xcur = it.next();
            }
            return !xcur.isDisabled();
        }

        public Vertex next() {
            return xcur;
        }
    }

    @Override
    public Vertex getVertex(int n) {
        return xv[n - 1];
    }

    XVertex getVertex(Vertex u) {
        return Vertex.getVertex(xv, u);
    }

    void disable(int i) {
        XVertex u = (XVertex) getVertex(i);
        u.disable();
    }

    /**
     * Decrease weight for all edges by the minimum weight amount incoming edges
     * to the same node
     */
    void decreaseWeight() {
        for (Vertex v : this) {
            XVertex xv = (XVertex) v;
            int minWeight = Integer.MAX_VALUE;
            for (XEdge e : xv.xrevAdj) {
                if (!((XVertex) e.otherEnd(v)).disabled) { // don't use XEdge
                    // disabled since
                    // the weight could
                    // be zero
                    if (e.weight < minWeight)
                        minWeight = e.weight;
                    if (minWeight == 0)
                        break;
                }
            }

            // continue to next vertex if we found 0 weight incoming edge on
            // this vertex
            if (minWeight == 0)
                continue;

            for (XEdge e : xv.xrevAdj) {
                if (!((XVertex) e.otherEnd(v)).disabled) {
                    e.weight -= minWeight;
                }
            }
        }
    }

    /**
     * Shrink a set of vertices into a new vertex
     *
     * @param vs
     *            - list of vertices to shrink
     */
    void shrinkVertexes(Set<XVertex> vs) {
        if (n + 1 > xv.length) {
            System.out.println("Notice - graph vertexes array reach maximum capacity, allocate new array");
            XVertex[] nxv = new XVertex[xv.length * 2];
            for (int i = 0; i < xv.length; i++)
                nxv[i] = xv[i];
            xv = nxv;
        }

        XVertex c = new XVertex(n, vs);
        xv[n++] = c;
        Map<XVertex, XEdge> inMap = new HashMap<>();
        Map<XVertex, XEdge> outMap = new HashMap<>();
        for (XVertex v : vs) {
            // disable shrunken vertices
            v.disable();

            // find outgoing edge to keep after shrink
            for (XEdge outE : v.xadj) {
                XVertex other = (XVertex) outE.otherEnd(v);
                if (!vs.contains(other)) {
                    if ((!outMap.containsKey(other)) || outMap.get(other).weight > outE.weight) {
                        outMap.put(other, outE);
                    }
                }
            }

            // find incoming edge to keep after shrink
            for (XEdge inE : v.xrevAdj) {
                XVertex other = (XVertex) inE.otherEnd(v);
                if (!vs.contains(other)) {
                    if ((!inMap.containsKey(other)) || inMap.get(other).weight > inE.weight) {
                        inMap.put(other, inE);
                    }
                }
            }
        }

        for (XVertex v : inMap.keySet()) {
            XEdge superE = inMap.get(v);
            XEdge e = new XEdge(v, c, superE);
            v.xadj.add(e);
            c.xrevAdj.add(e);
        }

        for (XVertex v : outMap.keySet()) {
            XEdge superE = outMap.get(v);
            XEdge e = new XEdge(c, v, superE);
            c.xadj.add(e);
            v.xrevAdj.add(e);
        }
    }

    /**
     * Expand a shrunk vertex
     *
     * @param c
     */
    void uncontract(XVertex c) {
        if (c.shrinkedVs != null) {
            for (XVertex v : c.shrinkedVs) {
                // enable each shrunk vertex
                v.enable();
            }
            // disable the component vertex
            c.disable();
        }
    }

    public static void main(String[] args) {


    }

}

/*
 * Sample output:
 * 
 * Node : Dist : Edges 1 : 0 : (1,2)(1,3) 2 : 1 : (2,1)(2,4)(2,5) 3 : 1 :
 * (3,1)(3,6)(3,7) 4 : 2 : (4,2)(4,8) 5 : 2 : (5,2) 6 : 2 : (6,3) 7 : 2 :
 * (7,3)(7,9) 8 : 3 : (8,4) 9 : 3 : (9,7) Source: 1 Farthest: 8 Distance: 3
 * 
 * Disabling vertices 8 and 9 1 : 0 : (1,2)(1,3) 2 : 1 : (2,1)(2,4)(2,5) 3 : 1 :
 * (3,1)(3,6)(3,7) 4 : 2 : (4,2) 5 : 2 : (5,2) 6 : 2 : (6,3) 7 : 2 : (7,3)
 * Source: 1 Farthest: 4 Distance: 2
 * 
 */
