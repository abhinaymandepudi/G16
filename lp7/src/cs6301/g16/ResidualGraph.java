/*
 * Copyright (c) 2017.
 *
 * @author Hanlin He (hxh160630@utdallas.edu) ,
 *         Binhan Wang (bxw161330@utdallas.edu),
 *         Zheng Gao (zxg170430@utdallas.edu)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cs6301.g16;

import java.util.*;

/**
 * A residual graph class extending {@code Graph} using parallel array.
 */
public class ResidualGraph extends Graph {
    public static class ResidualVertex extends Graph.Vertex {
        List<ResidualEdge> residualAdj;

        public ResidualVertex(Vertex u) {
            super(u);
            residualAdj = new LinkedList<>();
        }

        @Override
        public Iterator<Edge> iterator() {
            return new ResidualVertexIterator(this);
        }

        class ResidualVertexIterator implements Iterator<Edge> {
            ResidualEdge cur;
            Iterator<ResidualEdge> it;
            boolean ready;

            ResidualVertexIterator(ResidualVertex u) {
                this.it = u.residualAdj.iterator();
                ready = false;
            }

            public boolean hasNext() {
                if (ready) {
                    return true;
                }
                if (!it.hasNext()) {
                    return false;
                }
                cur = it.next();
                while (cur.isZeroResidualCapacity() && it.hasNext()) {
                    cur = it.next();
                }
                ready = true;
                return !cur.isZeroResidualCapacity();
            }

            public Edge next() {
                if (!ready) {
                    if (!hasNext()) {
                        throw new java.util.NoSuchElementException();
                    }
                }
                ready = false;
                return cur;
            }

            public void remove() {
                throw new java.lang.UnsupportedOperationException();
            }
        }
    }

    public static class ResidualEdge extends Graph.Edge {
        private final int capacity;
        private int flow;

        public ResidualEdge(Vertex u, Vertex v, int w, int n, int capacity, int flow) {
            super(u, v, w, n);
            this.capacity = capacity;
            this.flow = flow;
        }

        public boolean isZeroResidualCapacity() {
            return capacity == flow;
        }

        private int getRemainCapacity() {
            return capacity - flow;
        }

        public int getFlow() {
            return flow;
        }

        /**
         * Private interface to increase the flow by specific size, used only by {@code augment}
         * method in {@code ResidualGraph} class.
         *
         * @param f Flow size to increase.
         */
        private void increase(int f) {
            flow += f;
        }

        /**
         * Private interface to decrease the flow by specific size, used only by {@code augment}
         * method in {@code ResidualGraph} class.
         *
         * @param f Flow size to decrease.
         */
        private void decrease(int f) {
            flow -= f;
        }

        @Override
        public String toString() {
            return "(" + from + "," + to + ": " + flow + "/" + capacity + ")";
        }
    }

    /**
     * Parallel array of vertices.
     */
    private ResidualVertex[] fv;

    /**
     * Parallel array of edges with index [0, m-1] as the {@code ResidualEdge} instance of the
     * original edges, and [m, 2m-1] as the {@code ResidualEdge} instance of the edges with opposite
     * direction.
     */
    private ResidualEdge[] fe;

    /**
     * Determine whether the residual graph is weighted. If not, all edges have weight of 1.
     */
    private boolean weighted;

    public ResidualGraph(Graph g, HashMap<Edge, Integer> capacity, boolean weighted) {
        super(g);
        this.fv = new ResidualVertex[g.size()];
        this.fe = new ResidualEdge[g.edgeSize() * 2];
        this.weighted = weighted;

        // Create parallel array for vertices.
        g.forEach(u -> this.fv[u.getName()] = new ResidualVertex(u));

        // Make copy of edges.
        // Each edge would generate two edges between two node, each with different direction.
        // Initial flow is 0 for original direction, and full capacity for reverse direction.
        g.forEach(u -> u.forEach(e -> addResidualEdge(u, e.otherEnd(u), e.weight, e.name, capacity.get(e))));
    }

    private void addResidualEdge(Vertex fromVertex, Vertex toVertex, int weight, int name, int capacity) {

        ResidualVertex from = getVertex(fromVertex);
        ResidualVertex to = getVertex(toVertex);

        // Let index of original edge be the name.
        final int oriEdgeIndex = name - 1;

        // Let index of reversed edge be the name + number of edges.
        final int revEdgeIndex = name + m - 1;

        if (weighted) {
            fe[oriEdgeIndex] = new ResidualEdge(from, to, weight, oriEdgeIndex + 1, capacity, 0);
            fe[revEdgeIndex] = new ResidualEdge(to, from, weight, revEdgeIndex + 1, capacity, capacity);
            from.residualAdj.add(fe[oriEdgeIndex]);
            to.residualAdj.add(fe[revEdgeIndex]);
        } else {
            fe[oriEdgeIndex] = new ResidualEdge(from, to, 1, oriEdgeIndex + 1, capacity, 0);
            fe[revEdgeIndex] = new ResidualEdge(to, from, 1, revEdgeIndex + 1, capacity, capacity);
            from.residualAdj.add(fe[oriEdgeIndex]);
            to.residualAdj.add(fe[revEdgeIndex]);
        }
    }

    @Override
    public Iterator<Vertex> iterator() {
        return new ResidualGraphIterator(this);
    }

    class ResidualGraphIterator implements Iterator<Vertex> {
        Iterator<ResidualVertex> it;
        ResidualVertex xcur;

        ResidualGraphIterator(ResidualGraph rg) {
            this.it = new ArrayIterator<>(rg.fv, 0, rg.size() - 1);  // Iterate over existing elements only
        }


        public boolean hasNext() {
            if (!it.hasNext()) {
                return false;
            }
            xcur = it.next();
            return true;
        }

        public Vertex next() {
            return xcur;
        }

        public void remove() {
        }

    }

    public void augment(List<Edge> path) {
        int minCapacity = Integer.MAX_VALUE;

        for (Edge e : path) {
            final int remainCapacity = getEdge(e).getRemainCapacity();
            if (remainCapacity < minCapacity)
                minCapacity = remainCapacity;
        }

        final int flow = minCapacity;

        if (flow > 0)
            path.forEach(e -> augment(e, flow));
    }

    private void augment(Edge e, int flow) {
        getEdge(e).increase(flow);
        getOtherEdge(e).decrease(flow);
    }

    @Override
    public Vertex getVertex(int n) {
        return fv[n - 1];
    }

    ResidualVertex getVertex(Vertex u) {
        return Vertex.getVertex(fv, u);
    }

    /**
     * Get the {@code ResidualEdge} instance from the original {@code Edge} instance.
     *
     * @param e {@code Edge} instance.
     *
     * @return Corresponding {@code ResidualEdge} instance.
     */
    public ResidualEdge getEdge(Edge e) {
        return fe[e.getName() - 1];
    }

    /**
     * Get the {@code ResidualEdge} instance of edge which has reversed direction of the original
     * {@code Edge} instance.
     *
     * @param e {@code Edge} instance.
     *
     * @return Corresponding {@code ResidualEdge} instance.
     */
    public ResidualEdge getOtherEdge(Edge e) {
        final int name = e.getName();
        return fe[name <= m ? name + m - 1 : name - m - 1];
    }
}