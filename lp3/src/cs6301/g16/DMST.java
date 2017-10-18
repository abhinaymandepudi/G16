package cs6301.g16;

import java.util.List;

import cs6301.g16.Graph.Edge;

public class DMST extends DFS{

	List<Graph.Edge> dmst;
	int startPoint;
	public DMST(Graph g, int start, List<Edge> dmst) {
		super(new DMSTGraph(g));
		this.dmst = dmst;
		this.startPoint = start;
	}
	
	public int findDirectedMST(){
		
		int weight = 0;
		for(Edge e:dmst)
			weight += e.weight;
		return weight;
	}

}
