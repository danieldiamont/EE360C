/*
 * Name: Daniel Diamont
 * EID: dd28977
 * EE360C (Section: 15775)
 */

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;

/**
 * This class creates a graphical representation of ports and rivers
 * for a shipping company. The nodes is initially provided as a set of
 * integers, and then is converted to Node objects (specified in Node.java).
 * The nodes objects are stored in a hashtable for fast integer-Node lookup.
 * Each Node object has its own adjacency list of edges incident to the node.
 * The Edge object's contain the Node which resides at the opposite end of
 * the edge.
 * 
 * @author Daniel Diamont
 *
 */
public class Graph implements Program2{
	// n is the number of ports
	private int n;

	//stores the nodes in the graph
	private Hashtable<Integer, Node> nodes;

	/**
	 * Initializes the Hashtable of nodes to 2*|n| with a load factor of 75%
	 * to avoid hash collisions and have amortized node lookup in O(1)
	 * @param x is the number of nodes in the graph
	 */
	public Graph(int x) {
		n = x;
		
		this.nodes = new Hashtable<Integer,Node>(2*this.n);
	}
	
	/**
	 *  This function is called by the Driver program repeatedly such that
	 *  each call to inputEdge will create nodes for port1 and port2, should
	 *  they not exist in the list of nodes, and will create an edge between
	 *  them with the specified time cost and with the specified bottleneck
	 *  capacity.
	 *  
	 *  @param port1 is the unique ID of a node to be in the graph
	 *  @param port2 is the unique ID of a node to be in the graph
	 *  @param time is the associated time cost of edge (port1,port2)
	 *  @param capacity is the associated capacity of edge (port1,port2)
	 */
	public void inputEdge(int port1, int port2, int time, int capacity) {
		//construct Nodes port1 and port 2 and add to nodes list (hashtable)
		Node A, B;
		
		/*
		 * check if nodes exist already in the nodes Hashtable.
		 * If not, create the nodes and add them to nodes.
		 */
		if(nodes.containsKey(port1)) {
			A = nodes.get(port1);
		}
		else {
			A = new Node(port1);
		}
		
		if(nodes.containsKey(port2)) {
			B = nodes.get(port2);
		}
		else {
			B = new Node(port2);
		}
		
		
		/*
		 * Check if the edge already exists in each node's
		 * adjacency list. If not, then create a new Edge
		 * with the specified time and capacity params
		 * and add to each nodes adjacency list.
		 */
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
				

		/*
		 * Add/replace the nodes in the node list
		 */
		if(nodes.containsKey(port1)) {
			nodes.replace(port1, A);
		}
		else {
			nodes.put(port1, A);
		}
		
		if(nodes.containsKey(port2)) {
			nodes.replace(port2, B);
		}
		else {
			nodes.put(port2, B);
		}

		return;
	}

	/**
	 * This function is the solution for the Shortest Path problem.
	 * 
	 * @param sourcePort is the unique ID of the source vertex
	 * @param destPort is the unique ID of the destination vertex
	 * @return int which is the shortest travel time from source port to destination port
	 */
	public int findTimeOptimalPath(int sourcePort, int destPort) {
		
		//initialization
		for(Integer i : nodes.keySet()) {
			Node v = nodes.get(i);
			v.setDist(Node.INFINITY);
			v.setPredecessor(null);
		}
		
		//source port
		Node src = nodes.get(sourcePort);
		//dest port
		Node sink = nodes.get(destPort);
		
		//distance from source to source
		src.setDist(0);
		
		//add all nodes to priority queue (min-heap)
		PriorityQueue<Node> queue = new PriorityQueue<Node>(this.n, new NodeComparator(true));
		queue.addAll(nodes.values());
		
		//main loop
		while (!queue.isEmpty()) {
			Node u = queue.peek();

			
			if(u.getDist() == Node.INFINITY)
				break;
			
			queue.remove(u);
			
			if(u.equals(sink))
				break;
			
			//for each neighbor v of u:
			for(Edge edge : u.getAdjList()) {

				Node v = edge.getNode();
				if(queue.contains(v)) {
					
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
		return sink.getDist();
	}

	/**
	 * This function is the solution for the Widest Path problem.
	 * 
	 * @param sourcePort is the unique ID of the source vertex
	 * @param destPort is the unique ID of the destination vertex
	 * 
	 * @return int which is the maximum capacity from source port to destination port
	 */
	public int findCapOptimalPath(int sourcePort, int destPort) {
		
		for(Integer i : nodes.keySet()) {
			Node v = nodes.get(i);
			v.setDist(Node.NEG_INFINITY);
			v.setPredecessor(null);
		}
		
		//source port
		Node src = nodes.get(sourcePort);
		//dest port
		Node sink = nodes.get(destPort);
		
		//bottleneck capacity from source to source
		src.setDist(Node.INFINITY); //theoretically we can send an infinite # of ships from s-> s
		
		//add all nodes to priority queue (max-heap)
		PriorityQueue<Node> queue = new PriorityQueue<Node>(this.n, new NodeComparator(false));
		queue.addAll(nodes.values());
		
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
					
					int weight = edge.getCapacity();
					
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
		
		return sink.getDist();
	}

	/**
	 * This function returns the neighboring ports of node.
	 * 
	 * @param node is the unique ID of the node
	 * 
	 * @return an list of unique ID's of the nodes that are
	 * immediately adjacent to the node passed as a
	 * parameter.
	 */
	public ArrayList<Integer> getNeighbors(int node) {
		ArrayList<Integer> edges = new ArrayList<Integer>();
		
		Node src = nodes.get(node);
		
		//get all nodes in the adjacency list of the specified node
		for(Edge edge : src.getAdjList()) {
			edges.add(edge.getNode().getID());
		}

		return edges;
	}

	/**
	 * @return the number of ports in the Graph object
	 */
	public int getNumPorts() {
		return n;
	}
}
