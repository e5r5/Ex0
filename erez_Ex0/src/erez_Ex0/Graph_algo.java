package erez_Ex0;

public class Graph_algo {
	Graph G;

	public Graph_algo() {
	}
	public double MinDistNodes(String name_file,int start,int end){
		Graph g = new Graph(name_file,start);
		return g.MinDistanceTwoNode(end);
	}
//	public double MinDistNodesWithBL(String name_file,int start,int end){
//		
//	}
}