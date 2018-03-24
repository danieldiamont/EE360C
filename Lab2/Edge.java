
/**
 * // Edge is the class to represent an edge between two nodes
	// node is the destination node this edge connected to
	// time is the travel time of this edge
	// capacity is the capacity of this edge
 * @author Daniel Diamont
 *
 */
public class Edge {

	private Node node;
	private int time;
	private int capacity;
	
	public Edge(Node n, int t, int c){
		this.node = n;
		this.time = t;
		this.capacity = c;
	}
	
	public Node getNode() {
		return this.node;
	}
	
	public void setNode(Node node) {
		this.node = node;
	}
	
	public int getTime() {
		return this.time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public void setCapacity(int cap) {
		capacity = cap;
	}
	
	// prints out an Edge.
	public String toString() {
		return "" + node;
	}
}
