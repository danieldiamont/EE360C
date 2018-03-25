
/**
 * Edge is the class to represent an edge between two nodes
 * node is the destination node this edge connected to
 * time is the travel time of this edge
 * capacity is the capacity of this edge
 * 
 * @author Daniel Diamont
 *
 */
public class Edge {

	//private fields
	private Node node;
	private int time;
	private int capacity;
	
	/**
	 * Constructor creates an edge object with the specified params
	 * 
	 * @param n unique ID of node at the end of this edge
	 * @param t the associated time cost of taking this edge
	 * @param c	the associated bottleneck capacity of this edge
	 */
	public Edge(Node n, int t, int c){
		this.node = n;
		this.time = t;
		this.capacity = c;
	}
	
	/**
	 * 
	 * @return node at the end of this edge
	 */
	public Node getNode() {
		return this.node;
	}
	
	/**
	 * Set the node at the end of this edge
	 * @param node object
	 */
	public void setNode(Node node) {
		this.node = node;
	}
	
	/**
	 * 
	 * @return the associated time cost of taking this edge
	 */
	public int getTime() {
		return this.time;
	}
	
	/**
	 * Set the associated time cost of taking this edge
	 * @param time
	 */
	public void setTime(int time) {
		this.time = time;
	}
	
	/**
	 * 
	 * @return the associated capacity of the edge
	 */
	public int getCapacity() {
		return this.capacity;
	}
	
	/**
	 * Set the capacity of the edge
	 * @param cap
	 */
	public void setCapacity(int cap) {
		capacity = cap;
	}
	
	/**
	 * Print out the edge
	 */
	public String toString() {
		return "" + node;
	}
}
