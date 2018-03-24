/*
 * Name: Daniel Diamont
 * EID: dd28977
 */

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 
 * @author Daniel Diamont
 *
 */
public class Graph implements Program2{
	// n is the number of ports
	private int n;


	// Here you have to define your own data structure that you want to use
	// to represent the graph
	// Hint: This include an ArrayList or many ArrayLists?
	// ....
	private Hashtable<Integer, Node> graph;

	// This function is the constructor of the Graph. Do not change the parameters
	// of this function.
	//Hint: Do you need other functions here?
	public Graph(int x) {
		n = x;
		
		this.graph = new Hashtable<Integer,Node>(2*this.n);
	}
	
	/**
	 * 
	 */
	public void inputEdge(int port1, int port2, int time, int capacity) {
		//construct Nodes port1 and port 2 and add to graph (hashtable)
		Node A, B;
		
		if(graph.containsKey(port1)) {
			A = graph.get(port1);
		}
		else {
			A = new Node(port1);
		}
		
		if(graph.containsKey(port2)) {
			B = graph.get(port2);
		}
		else {
			B = new Node(port2);
		}
		
//		Edge edgeA = new Edge(B, time, capacity);
//		Edge edgeB = new Edge(A, time, capacity);
//		
//		A.addToAjdList(edgeA);
//		B.addToAjdList(edgeB);
//		
//		graph.put(port1, A);
//		graph.put(port2, B);
		
		if(!A.getNodes().contains(B)) {
			A.addNode(B);
			Edge edge = new Edge(B, time, capacity);
			A.addToAjdList(edge);			
		}
		if(!B.getNodes().contains(A)) {
			B.addNode(A);
			Edge edge = new Edge(A, time, capacity);
			B.addToAjdList(edge);
		}		
				
		
//		A.addToAjdList(edgeA);
//		B.addToAjdList(edgeB);
		if(graph.containsKey(port1)) {
			graph.replace(port1, A);
		}
		else {
			graph.put(port1, A);
		}
		
		if(graph.containsKey(port2)) {
			graph.replace(port2, B);
		}
		else {
			graph.put(port2, B);
		}
//		
		return;
	}

	// This function is the solution for the Shortest Path problem.
	// The output of this function is an int which is the shortest travel time from source port to destination port
	// Do not change its parameters or return type.
	public int findTimeOptimalPath(int sourcePort, int destPort) {
		
		//initialization
		for(Integer i : graph.keySet()) {
			Node v = graph.get(i);
			v.setDist(Node.INFINITY);
			v.setPredecessor(null);
		}
		
//		System.out.println("Num nodes in graph: " + graph.values().size());
//		for(Node node : graph.values()) {
//			System.out.println("Node: " + node.getID());
//			System.out.println("Neighbors: ");
//			for(Edge edge : node.getAdjList()) {
//				System.out.print(edge.getNode().getID() + " ");
//			}
//			System.out.println();
//		}
		
		//source port
		Node src = graph.get(sourcePort);
		//dest port
		Node sink = graph.get(destPort);
		
		//distance from source to source
		src.setDist(0);
		
		//add all nodes to priority queue
		PriorityQueue<Node> queue = new PriorityQueue<Node>(this.n, new NodeComparator(true));
		queue.addAll(graph.values());
		
//		System.out.println("Head: " + queue.peek().getID());
		
//		for(Node node : queue) {
//			System.out.print(node.getID() + " ");
//		}
		
		//main loop
		while (!queue.isEmpty()) {
			Node u = queue.peek();
//			System.out.println("\nHead: " + queue.peek().getID());
			
			if(u.getDist() == Node.INFINITY)
				break;
//			
			queue.remove(u);
			
			if(u.equals(sink))
				break;
			
//			System.out.println(u.getAdjList());
			
			
//			for(Node node : queue) {
//				System.out.print(node.getID() + " ");
//			}
//			System.out.println();
			
			//for each neighbor v of u:
			for(Edge edge : u.getAdjList()) {
//				System.out.println(edge);
				Node v = edge.getNode();
				if(queue.contains(v)) {
					
//					System.out.println(v.getID() + ": dist : " + v.getDist());
//					System.out.println("alt dist : " + (u.getDist() + edge.getTime()));
					int alt = u.getDist() + edge.getTime();
					
					if(alt < v.getDist()) {
						v.setDist(alt);
						v.setPredecessor(u);
						
						//decrease key v in Q
						queue.remove(v);
						queue.add(v);
						
						
						
					}
				}
			}
			
		}
//		System.out.println("Min Time: " + sink.getDist());
		return sink.getDist();
	}

	// This function is the solution for the Widest Path problem.
	// The output of this function is an int which is the maximum capacity from source port to destination port 
	// Do not change its parameters or return type.
	public int findCapOptimalPath(int sourcePort, int destPort) {
		
//		System.out.println("\n\n\n");
		for(Integer i : graph.keySet()) {
			Node v = graph.get(i);
			v.setDist(Node.NEG_INFINITY);
			v.setPredecessor(null);
		}
		
		//source port
		Node src = graph.get(sourcePort);
		//dest port
		Node sink = graph.get(destPort);
		
		//distance from source to source
		src.setDist(Node.INFINITY);
		
		//add all nodes to priority queue
		PriorityQueue<Node> queue = new PriorityQueue<Node>(this.n, new NodeComparator(false));
		queue.addAll(graph.values());
		
		//main loop
		while (!queue.isEmpty()) {
			Node u = queue.peek();
			
			if(u.getDist() == Node.NEG_INFINITY)
				break;
			
			queue.remove(u);
			
			if(u.equals(sink))
				break;
			
			//for each neighbor v of u:
			for(Edge edge : u.getAdjList()) {
				Node v = edge.getNode();
				if(queue.contains(v)) {
//					System.out.println(v.getID() + ": cap : " + v.getDist());
//					System.out.println("alt cap : " + (u.getDist() + edge.getCapacity()));
					int weight = edge.getCapacity();
//					int alt = u.getDist() + weight;
					
					if(u.getDist() < weight) {
						if(v.getDist() < u.getDist()) {
							v.setDist(u.getDist());
						}
						
					}
					else {
						if(v.getDist() < weight) {
							v.setDist(weight);
						}
					}
					
						v.setPredecessor(u);
						
						//decrease key v in Q
						queue.remove(v);
						queue.add(v);
												
					}
				}
			}
		
		return Math.abs(sink.getDist());
	}

	// This function returns the neighboring ports of node.
	// This function is used to test if you have contructed the graph correct.
	public ArrayList<Integer> getNeighbors(int node) {
		ArrayList<Integer> edges = new ArrayList<Integer>();
		
		Node src = graph.get(node);
		
		for(Edge edge : src.getAdjList()) {
			edges.add(edge.getNode().getID());
		}

		return edges;
	}

	public int getNumPorts() {
		return n;
	}
}
