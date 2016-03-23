
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import javax.crypto.CipherInputStream;

public class Graph {

	final double infinity =Double.POSITIVE_INFINITY;
	int t, nodes, edges,startNode,n;
	double[] distance;
	List<Edge>[] list;
	List<Edge>[] back;
	double [] BL;


	/**
	* This function calculates the minimum distance between vertex "start" to vertex "end"
	* @param nameGraph the name of the file
	* @param start the start vertex
	* @param end the end vertex
	* @return the distance between tow vertex
	*/
		public static double MinPrice(String nameGraph,int start,int end){
			Graph g = new Graph(nameGraph);
			g.findShortestPaths(start);
			return g.MinDistanceTwoNode(end);
		}
		/**
		* This function calculates the path between tow vertex
		* @param nameGraph the name of the file
		* @param start the start vertex
		* @param end the end vertex
		* @return string of the path
		*/
		public static String GetPath(String nameGraph,int start,int end){
			Graph g = new Graph(nameGraph);
			g.findShortestPaths(start);
			return g.getPath(start,end);
		}

		/**
		* This function calculates the minimum distance between vertex "start" to vertex "end" without pass between some vertax
		* @param nameGraph the name of the file of the graph
		* @param BL the name of the file of the black list
		* @param start the start vertex
		* @return the distance between tow vertex
		*/
		public static double[] GetMinPriceWithBL(String nameGraph,String BL){
			Graph g = new Graph(nameGraph);
			g.BL(BL);
			return g.GETBL();
		}
		public static boolean is_eqFile(String name1,String name2){
			FileReader f1;
			FileReader f2;
			try {
				f1 = new FileReader(name1);
				f2 = new FileReader(name2);


				BufferedReader bf1 = new BufferedReader(f1);
				BufferedReader bf2 = new BufferedReader(f2);
				String s1 = "";
				String s2 = "";
				while(s1!=null || s2!=null){

					if(!s1.equalsIgnoreCase(s2)) return false;
					s1 = bf1.readLine();
					s2 = bf2.readLine();
				}
				if((s1==null&& s2!=null) || (s1!=null&& s2==null)) return false;
				else return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;

			}

		}
		public static void printToFile(String BLname,double [] arr){
			FileReader f;
			File fww ;
			String a =   " AnsTo"+BLname;
			try {
				f = new FileReader(BLname);
				BufferedReader bf = new BufferedReader(f);
				fww= new File(a);
				fww.createNewFile();
				FileWriter fw = new FileWriter(fww);
				String s = "";
				int i=0;
				s = bf.readLine();
				fw.write(s + "\n");
				s = bf.readLine();
				while(s!=null && i<arr.length){
                               
					if(arr[i]==Double.POSITIVE_INFINITY){
						fw.write(s +"  " + "inf = no path!\n");
						i++;
						//continue;
					}
					else{
						fw.write(s + "  " + arr[i++] +"\n");	
					}
					s = bf.readLine();
				}
				bf.close();
				f.close();
				fw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		//end static Fanction


		///////////////////////////////////////////////////////////////////////////////////
		//constructor of the graph, get the name of the file and a start vertex
		public Graph(String name_file){
			createGraph(name_file);
			//this.startNode = start;

		}

		//create the graph, get name of the file and a start vertex
		private void createGraph(String Graph_name_file) {
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
						throw new Exception("you hava a nember less then zreo! Error!!");
					}

					from = Integer.valueOf(x);
					to = Integer.valueOf(y) ;
					weight = temp;
					if(from <0 || to < 0)
						throw new Exception("the V is negative! ");

					if (list[from] == null) {
						list[from] = new ArrayList<Edge>();
					}

					list[from].add(new Edge(to, weight));


					if (back[to] == null) {
						back[to] = new ArrayList<Edge>();
					}

					back[to].add(new Edge(from, weight));
				}
				in.close();
			} catch (Exception e) {
				System.err.println("!!!!!!!!!!!!" + from);
				e.printStackTrace();
			}
			this.distance = new double[nodes];
			//this.startNode = start;
			for (int i = 0; i < nodes; i++) {
				distance[i] = Double.POSITIVE_INFINITY;
			}

			//distance[start] = 0;

		}
		//black list. get the names of the files, one for the graph, the other for the black list
		public void BL(String name_file_BL){
			FileReader in;
			String s = "",t="",m="";
			//Graph g  = new Graph(nameMainFail);
			try {
				in = new FileReader(name_file_BL);
				BufferedReader bf = new BufferedReader(in);
				s = bf.readLine();

				this.n = Integer.valueOf(s);
				BL = new double[n];

				for(int i=0;i<BL.length;i++){
					s = bf.readLine();
					m=s;
					String x = "", y = "", k = "";
					int from = 0, to = 0, nemberBL = 0;
					StringTokenizer st = new StringTokenizer(s);
					StringTokenizer stm = new StringTokenizer(m);
					x = st.nextToken();
					y = st.nextToken();
					k = st.nextToken();
					from = Integer.valueOf(x);
					to = Integer.valueOf(y) ;
					nemberBL = Integer.valueOf(k);
					if (nemberBL < 0 || from <0 || to < 0) {				
						throw new Exception("you hava a nember less then zreo! Error!!");
					}

					//double [] beforUp = new double[];
					ArrayList<Double> beforUp = new ArrayList<Double>();
					for(int j=0;j<nemberBL;j++){
						t = st.nextToken();
						int index = Integer.valueOf(t);
						int size = list[index].size();
						if(size>0){
							for(int a =0;a<size;a++){
								beforUp.add(list[index].get(a).getWeight());//save
								list[index].get(a).setWeight(Double.POSITIVE_INFINITY);//remove
							}
						}
					}

					findShortestPaths2(from);
					BL[i]=distance[to];
					//clear distance
					for(int c=0;c<distance.length;c++){
						distance[c]=infinity;
					}
					stm.nextToken();
					stm.nextToken();
					stm.nextToken();
					for(int j=0;j<nemberBL;j++){
						t = stm.nextToken();
						int index = Integer.valueOf(t);
						int size = list[index].size();
						if(size>0){
							for(int a =0;a<size;a++){
								double temp= beforUp.get(a);//list[index].get(a).getWeight();//save
								list[index].get(a).setWeight(temp);//return the val
							}
						}
					}
				}
				in.close();
			}

			catch(Exception e ){}

			printToFile(name_file_BL,BL);
		}

		public double [] GETBL(){
			return BL;
		}
		public double MinDistanceTwoNode(int end){
			return distance[end];
		}

		// return string of the shortest path
		public String getPath(int st,int end){
			findShortestPaths(st);
			if(distance[end]==Double.POSITIVE_INFINITY)
				return "inf = no path!";	
			String s="";
			int start = end;
			s=s+end;
			Iterator<Edge> iterator ;

			while (start!=startNode ){
				
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
		// dijkstra, find the shortest path. fill the array "distance"
		private void findShortestPaths(int start) {
			this.startNode=start;
			this.distance = new double[nodes];

			for (int i = 0; i < nodes; i++) {
				distance[i] = Double.POSITIVE_INFINITY;
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
		// function of the black list, update the price of the edges
		private void findShortestPaths2(int start) {

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

		// edge of the graph, to all the edge there is a a vertex and weight
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
		// vertex in the graph
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