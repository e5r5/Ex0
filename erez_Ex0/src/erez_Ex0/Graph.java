package erez_Ex0;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;



public class Graph {

	static final int infinity = Integer.MAX_VALUE;
	static int t, nodes, edges;
	static double[] distance;
	static List<Edge>[] list;
	static Scanner in = new Scanner(System.in);


	public static void main(String[] args) {
		String name = "tinyEWD.txt";
		System.out.print("Enter the name of file:  ");

		createGraph(name);
		findShortestPaths(0);
		System.out.println("The shortest path to all nodes from node 0 is : ");
		for (int i = 0; i < nodes; i++) {
			System.out.println(i + " : " + distance[i]);
		}

	}

	static void createGraph(String Graph_name_file) {
		String s = "";
		list = new ArrayList[nodes];
		FileReader in;
		try {
			in = new FileReader(Graph_name_file);
			BufferedReader bf = new BufferedReader(in);
			s = bf.readLine();
			nodes = Integer.valueOf(s);
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				from = Integer.valueOf(x) - 1;
				to = Integer.valueOf(y) - 1;
				weight = temp;
				System.out.println(from);
				if (list[from] == null) {
					list[from] = new ArrayList<>();
				}

				list[from].add(new Edge(to, weight));

				if (list[to] == null) {
					list[to] = new ArrayList<>();
				}

				list[to].add(new Edge(from, weight));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void findShortestPaths(int node) {
		distance = new double[nodes];

		for (int i = 0; i < nodes; i++) {
			distance[i] = infinity;
		}

		distance[node] = 0;

		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(new Node(node, 0, -1));

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

	static class Edge {
		int to;
		double weight;

		public Edge(int to, double weight) {
			this.to = to;
			this.weight = weight;
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


