package erez_Ex0;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;



public class Graph {

	final int infinity = Integer.MAX_VALUE;
	int t, nodes, edges,startNode;
	double[] distance;
	List<Edge>[] list;
	List<Edge>[] back;
	Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		Graph G = new Graph("tinyEWG.txt",4);
	//	Graph G = new Graph("erezTest",4);
		System.out.println(G.getPath(7));
		//Graph_algo g = new Graph_algo();
	//	System.out.println(g.MinDistNodes("erezTest", 0, 5));
	}

	public Graph(String name_file,int start){
		createGraph(name_file);
		this.startNode = start;
		findShortestPaths(startNode);
		System.out.println("The shortest path to all nodes from node 0 is : ");
		for (int i = 0; i < nodes; i++) {
			System.out.println(i + " : " + distance[i]);
		}
	}
	private void createGraph(String Graph_name_file) {
		String s = "";

		FileReader in;
		try {
			in = new FileReader(Graph_name_file);
			BufferedReader bf = new BufferedReader(in);
			s = bf.readLine();
			nodes = Integer.valueOf(s);
			list = new ArrayList[nodes];
			back = new ArrayList[nodes];
			s = bf.readLine();
			edges = Integer.valueOf(s);

			for (int i = 0; i < edges; i++) {
				s = bf.readLine();
				int from, to;
				double weight;
				String x = "", y = "", w = "";
				int a = 0, b = 0;
				for (int p = 0; p < s.length(); p++) {
					if (s.charAt(p) == ' ' && x.equalsIgnoreCase("")) {
						x = s.substring(0, p);
						a = p + 1;
					} else if (s.charAt(p) == ' ' && a != 0) {
						y = s.substring(a, p);
						b = p + 1;
					} else if (a != 0 && b != 0) {
						w = s.substring(b, s.length());
						break;
					}
				}
				double temp = Double.valueOf(w);
				if (temp < 0) {
					try {
						throw new Exception("you hava a nember less then zreo! Error!!");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				from = Integer.valueOf(x);
				to = Integer.valueOf(y) ;
				weight = temp;

				if (list[from] == null) {
					list[from] = new ArrayList<Edge>();
				}

				list[from].add(new Edge(to, weight));
				
			
				if (back[to] == null) {
					back[to] = new ArrayList<Edge>();
				}

				back[to].add(new Edge(from, weight));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//	public static void printPath (WeightedGraph G, int [] pred, int s, int e) {
	//		       final java.util.ArrayList path = new java.util.ArrayList();
	//		       int x = e;
	//		       while (x!=s) {
	//		         path.add (0, G.getLabel(x));
	//		          x = pred[x];
	//		       }
	//		        path.add (0, G.getLabel(s));
	//		       System.out.println (path);
	//		    }

	public double MinDistanceTwoNode(int end){
		return distance[end];
	}
	public String getPath(int end){
		int start=end;
		String s="";
		s=s+end;
		Iterator<Edge> iterator ;
		
		while (start!=startNode){
			iterator = back[start].iterator();
			boolean b=true;
			while (iterator.hasNext()&&b) {
				Edge curr = iterator.next();
				
				if (Math.abs((this.distance[start]-curr.weight)-(this.distance[curr.to]))<0.00001)
				{
					start=curr.to;
					s=curr.to+"->"+s;
					b=false;
				}
			}	
		}
		return s;
	}


	private void findShortestPaths( int start) {
		this.distance = new double[nodes];

		for (int i = 0; i < nodes; i++) {
			distance[i] = infinity;
		}

		distance[start] = 0;

		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(new Node(start, 0, -1));

		while (priorityQueue.size() > 0) {
			Node min = priorityQueue.poll();
			Iterator<Edge> iterator = list[min.node].iterator();

			while (iterator.hasNext()) {
				Edge curr = iterator.next();

				if (distance[min.node] + curr.weight < distance[curr.to]) {
					distance[curr.to] = distance[min.node] + curr.weight;
					priorityQueue.add(new Node(curr.to, distance[curr.to], min.node));

				}
			}
		}
	}

	static class Edge implements Comparable<Edge> {
		int to;
		double weight;

		public Edge(int to, double weight) {
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge a) {
	      return Double.compare(this.weight,a.weight );
		}
	}

	static class Node implements Comparable<Node> {

		int node;
		double shortestPathWeight, parent;

		public Node(int node, double shortestPathWeight, double parent) {
			this.node = node;
			this.shortestPathWeight = shortestPathWeight;
			this.parent = parent;
		}

		@Override
		public int compareTo(Node o) {
			return Double.compare(shortestPathWeight, o.shortestPathWeight);
		}

	}

}


