import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import javax.swing.plaf.synth.SynthStyle;

public class Graph {

	int infinity = Integer.MAX_VALUE;
	int t, nodes, edges,startNode,n;
	double[] distance;
	List<Edge>[] list;
	List<Edge>[] back;
	double [] BL;
	//הפונקציות של המטלה עפ סדר הן
	//////////////////////////////////////////////////////////////////////////////////////////
	public static double MinPrice(String nameGraph,int start,int end){
		Graph g = new Graph(nameGraph, start);
		g.findShortestPaths(start);
		return g.MinDistanceTwoNode(end);
	}
	public static String GetPath(String nameGraph,int start,int end){
		Graph g = new Graph(nameGraph, start);
		g.findShortestPaths(start);
		return g.getPath(end);
	}
	public static double[] GetMinPriceWithBL(String nameGraph,String BL,int start){
		Graph g = new Graph(nameGraph, start);
		g.BL(BL, nameGraph);
		return g.GETBL();
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		String name_file = "tinyEWG.txt";
		String name_file_BL = "BL.txt";
		int start = 0;
		int end = 6;

		System.out.println(MinPrice(name_file,start,end));
		System.out.println(GetPath(name_file,start,end));
		System.out.println(Arrays.toString(GetMinPriceWithBL(name_file,name_file_BL,start)));
	}


	///////////////////////////////////////////////////////////////////////////////////
	//בנאי מקבל את שם הקובץ של הגרף ונק התחלה
	public Graph(String name_file,int start){
		createGraph(name_file,start);
		this.startNode = start;
		//findShortestPaths();
		//		System.out.println("The shortest path to all nodes from node 0 is : ");
		//		for (int i = 0; i < nodes; i++) {
		//			System.out.println(i + " : " + distance[i]);
		//		}
	}

	//יוצר את הגרף מקבל את שם הגרף ונק התחלה
	private void createGraph(String Graph_name_file,int start) {
		String s = "";
		int from=0, to=0;
		double weight=0;
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
				from=0; to=0; weight=0;

				String x = "", y = "", w = "";
				int a = 0, b = 0, k=0;
				StringTokenizer st = new StringTokenizer(s);
				x = st.nextToken();
				y = st.nextToken();
				w = st.nextToken();

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
			System.err.println("!!!!!!!!!!!!" + from);
			e.printStackTrace();
		}
		this.distance = new double[nodes];
		this.startNode = start;
		for (int i = 0; i < nodes; i++) {
			distance[i] = infinity;
		}

		distance[start] = 0;
	}
	//רשימה שחורה מקבל את שם הקובץ המקורי ושם קובץ שחור לעידכון
	public void BL(String name_file_BL,String nameMainFail){
		FileReader in;
		String s = "",t="";
		Graph g = null;
		try {
			in = new FileReader(name_file_BL);
			BufferedReader bf = new BufferedReader(in);
			s = bf.readLine();
			this.n = Integer.valueOf(s);
			BL = new double[n];
			for(int i=0;i<BL.length;i++){
				s = bf.readLine();
				String x = "", y = "", k = "";
				int from = 0, to = 0, nemberBL = 0;
				StringTokenizer st = new StringTokenizer(s);
				x = st.nextToken();
				y = st.nextToken();
				k = st.nextToken();
				from = Integer.valueOf(x);
				to = Integer.valueOf(y) ;
				nemberBL = Integer.valueOf(k);


				g = new Graph(nameMainFail, from);
				for(int j=0;j<nemberBL;j++){
					t = st.nextToken();
					int index = Integer.valueOf(t);
					int size = g.list[index].size();

					if(size>0){
						for(int a =0;a<size;a++){
							g.list[index].get(a).setWeight(99999);
						}
					}
				}

				g.findShortestPaths2(from);
				BL[i]=g.distance[to];

			}
		}
		catch(Exception e ){}

	}
	public double [] GETBL(){
		return BL;
	}
	public double MinDistanceTwoNode(int end){
		return distance[end];
	}
	//מחזיר את המסלול הקצר ביותר לנק הסיום
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

	//אלגוריתם של דיקסטרה מציאת המסלול הקצר ביותר בגרף
	private void findShortestPaths(int start) {
		this.distance = new double[nodes];

		for (int i = 0; i < nodes; i++) {
			distance[i] = infinity;
		}

		distance[start] = 0;

		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(new Node(start, 0, -1));

		while (priorityQueue.size() > 0) {

			Node min = priorityQueue.poll();
			if ( list[min.node]!=null){
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
	}
	//פונקציה שמיועדת לרשימה השחורה, אחרי עידכון עליית המחירים בצלעות המבוקשות נפעיל שוב את האלגוריתם עם שינויים קלים
	private void findShortestPaths2(int start) {


		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(new Node(start, 0, -1));

		while (priorityQueue.size() > 0) {

			Node min = priorityQueue.poll();
			if ( list[min.node]!=null){
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
	}

	//צלע בדרף לכל צלע יש משקל ויעד
	static class Edge implements Comparable<Edge> {
		int to;
		double weight;

		public Edge(int to, double weight) {
			this.to = to;
			this.weight = weight;
		}

		public Edge(Edge e){
			this.to = e.to;
			this.weight = e.weight;
		} 

		public int getTo() {
			return to;
		}

		public void setTo(int to) {
			this.to = to;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge a) {
			return Double.compare(this.weight,a.weight );
		}
	}
	//קודקוד בגרף
	static class Node implements Comparable<Node> {

		int node;
		double shortestPathWeight, parent;

		public Node(int node, double shortestPathWeight, double parent) {
			this.node = node;
			this.shortestPathWeight = shortestPathWeight;
			this.parent = parent;
		}
		public Node(Node n) {
			this.node = n.node;
			this.shortestPathWeight = n.shortestPathWeight;
			this.parent = n.parent;
		}

		@Override
		public int compareTo(Node o) {
			return Double.compare(shortestPathWeight, o.shortestPathWeight);
		}

	}

}
