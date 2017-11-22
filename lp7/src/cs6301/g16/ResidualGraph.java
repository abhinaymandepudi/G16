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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
                while (!cur.isZeroResidualCapacity() && it.hasNext()) {
                    cur = it.next();
                }
                ready = true;
                return cur.isZeroResidualCapacity();
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
        if (weighted) {
            from.flowadj.add(new ResidualEdge(from, to, weight, name, capacity, 0));
            to.flowadj.add(new ResidualEdge(to, from, weight, name + this.m, capacity, capacity));
        } else {
            from.flowadj.add(new ResidualEdge(from, to, 1, name, capacity, 0));
            to.flowadj.add(new ResidualEdge(to, from, 1, name + this.m, capacity, capacity));
        }
    }

    @Override
    public Vertex getVertex(int n) {
        return fv[n - 1];
    }

    ResidualVertex getVertex(Vertex u) {
        return Vertex.getVertex(fv, u);
    }
}