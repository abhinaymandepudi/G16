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

public class ResidualGraph extends Graph {
    public static class ResidualVertex extends Graph.Vertex {
        List<ResidualEdge> flowadj;

        public ResidualVertex(Vertex u) {
            super(u);
            flowadj = new LinkedList<>();
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
                this.it = u.flowadj.iterator();
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
        final int capacity;
        int flow;

        public ResidualEdge(Vertex u, Vertex v, int w, int n, int capacity, int flow) {
            super(u, v, w, n);
            this.capacity = capacity;
            this.flow = flow;
        }

        public boolean isZeroResidualCapacity() {
            return capacity == flow;
        }

        public int getRemainCapacity() {
            return capacity - flow;
        }

        public int getFlow() {
            return flow;
        }

        public void augment(int f) {
            flow += f;
        }

        @Override
        public String toString() {
            return "(" + from + "," + to + ": " + flow + "/" + capacity + ")";
        }
    }

    ResidualVertex[] fv;
    ResidualEdge[] fe;
    boolean weighted;

    public ResidualGraph(Graph g, HashMap<Edge, Integer> capacity, boolean weighted) {
        super(g);
        this.fv = new ResidualVertex[g.size()];
        this.fe = new ResidualEdge[g.edgeSize() * 2];
        this.weighted = weighted;
        for (Vertex u : g) {
            fv[u.getName()] = new ResidualVertex(u);
        }

        // Make copy of edges.
        // Each edge would generate two edges between two node, each with different direction.
        // Initial flow is 0 for original direction, and full capacity for reverse direction.
        for (Vertex u : g) {
            u.adj.forEach(e -> {
//                Vertex v = e.otherEnd(u);
//                ResidualVertex x1 = getVertex(u);
//                ResidualVertex x2 = getVertex(v);
//                addResidualEdge(x1, x2, e.weight, e.name, capacity.get(e));
                addResidualEdge(getVertex(u), getVertex(e.otherEnd(u)), e.weight, e.name, capacity.get(e));
            });
        }
    }

    private void addResidualEdge(ResidualVertex from, ResidualVertex to, int weight, int name, int capacity) {

        // Let index of original edge be the name.
        final int oriEdgeIndex = name - 1;

        // Let index of reversed edge be the name + number of edges.
        final int revEdgeIndex = name + m - 1;

        if (weighted) {
            fe[oriEdgeIndex] = new ResidualEdge(from, to, weight, oriEdgeIndex + 1, capacity, 0);
            fe[revEdgeIndex] = new ResidualEdge(to, from, weight, revEdgeIndex + 1, capacity, capacity);
            from.flowadj.add(fe[oriEdgeIndex]);
            to.flowadj.add(fe[revEdgeIndex]);
        } else {
            fe[oriEdgeIndex] = new ResidualEdge(from, to, 1, oriEdgeIndex + 1, capacity, 0);
            fe[revEdgeIndex] = new ResidualEdge(to, from, 1, revEdgeIndex + 1, capacity, capacity);
            from.flowadj.add(fe[oriEdgeIndex]);
            to.flowadj.add(fe[revEdgeIndex]);
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

        if (minCapacity > 0) {
            System.out.println("Augmenting path: " + path);
            for (Edge e : path)
                getEdge(e).augment(minCapacity);
        } else
            System.out.println("Augmenting path: " + path + " failed.");
    }

    @Override
    public Vertex getVertex(int n) {
        return fv[n - 1];
    }

    ResidualVertex getVertex(Vertex u) {
        return Vertex.getVertex(fv, u);
    }

    ResidualEdge getEdge(Edge e) {
        return fe[e.getName() - 1];
    }
}