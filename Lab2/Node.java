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
	
	public static final Integer NEG_INFINITY = Integer.MIN_VALUE;
	public static final Integer INFINITY = Integer.MAX_VALUE;

	private List<Edge> adj;
	private List<Node> adjNode;
	
	private int dist;
	
	private Node predecessor;
	
	private Integer portID;
	
	
	//standard parameter-less constructor
 	public Node(int portID) {
		this.adj = new ArrayList<Edge>();
		this.adjNode = new ArrayList<Node>();
		this.dist = INFINITY;
		this.predecessor = null;
		this.portID = new Integer(portID);
	}
	//constructor
	public Node(List<Edge> adj, int dist, Node predecessor) {
		
		this.adj = adj;
		this.dist = dist;
		this.predecessor = predecessor;
	}
	
	public void addToAjdList(Edge edge) {
		adj.add(edge);
	}
	
	public List<Node> getNodes(){
		return this.adjNode;
	}
	
	public void addNode(Node n) {
		adjNode.add(n);
	}
	
	public void setAdjList(List<Edge> adj) {
		this.adj = adj;
	}
	
	public List<Edge> getAdjList(){
		return this.adj;
	}
	
	public void setDist(int dist) {
		this.dist = dist;
	}
	
	public int getDist() {
		return this.dist;
	}
	
	public void setPredecessor(Node node) {
		this.predecessor = node;
	}
	
	public Node getPredecessor() {
		return this.predecessor;
	}
	
	public int getID() {
		return this.portID;
	}
}
