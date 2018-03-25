import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a port (a node in the graph).
 * A port has a list of waterways (edges) connecting it to other ports.
 * Additionally, a port has a distance field used for Dijkstra's Algorithm
 * distance estimation. Each node also has a unique identifier called a 
 * portID. Lastly, a port has a predecessor field used for
 * backtracing paths.
 * 
 * @author Daniel Diamont
 *
 */
public class Node {
	
	//constants used to define distances (time/capacity)
	public static final Integer NEG_INFINITY = Integer.MIN_VALUE;
	public static final Integer INFINITY = Integer.MAX_VALUE;

	//adjacency list
	private List<Edge> adj;
	private List<Node> adjNode;
	
	private int dist;	
	private Node predecessor;	
	private Integer portID;
	
	
	/**
	 * Creates a node with an empty adj list, infinite distance,
	 * null predecessor, and stores the unique ID of the node
	 * @param portID unique ID of the port
	 */
 	public Node(int portID) {
		this.adj = new ArrayList<Edge>();
		this.adjNode = new ArrayList<Node>();
		this.dist = INFINITY;
		this.predecessor = null;
		this.portID = new Integer(portID);
	}
	
	/**
	 * Adds an edge to the node's adjacency list
	 * @param edge
	 */
	public void addToAjdList(Edge edge) {
		adj.add(edge);
	}
	
	/**
	 * 
	 * @return the list of nodes that are adjacent to this node.
	 */
	public List<Node> getNodes(){
		return this.adjNode;
	}
	
	/**
	 * Adds a node to the adjacency list of this node
	 * @param n
	 */
	public void addNode(Node n) {
		adjNode.add(n);
	}
	
	/**
	 * 
	 * @return the list of edges that belong to this node
	 */
	public List<Edge> getAdjList(){
		return this.adj;
	}
	
	/**
	 * 
	 * @param dist set the distance of this node
	 */
	public void setDist(int dist) {
		this.dist = dist;
	}
	
	/**
	 * 
	 * @return the distance of this node
	 */
	public int getDist() {
		return this.dist;
	}
	
	/**
	 * Set this node's predecessor
	 * @param node object
	 */
	public void setPredecessor(Node node) {
		this.predecessor = node;
	}
	
	/**
	 * 
	 * @return this node's predecessor.
	 * 
	 * Will return null if the node has no
	 * predecessor.
	 */
	public Node getPredecessor() {
		return this.predecessor;
	}
	
	/**
	 * 
	 * @return the unique identifier of this node
	 */
	public int getID() {
		return this.portID;
	}
}
