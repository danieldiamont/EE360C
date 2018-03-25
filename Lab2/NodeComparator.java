import java.util.Comparator;

/**
 * This class is a blueprint for a Comparator to implement a Priority Queue in the
 * form of a min-heap or a max-heap where the nodes in the priority queue are of 
 * type Node, and they are keyed by their individual distance fields.
 * 
 * @author Daniel Diamont
 *
 */
public class NodeComparator implements Comparator<Node>{

	boolean min_heap; //flag to check if we want to create min-heap (true) or max-heap (false)
	
	/**
	 * Sets min_heap flag to true (min-heap) or false (max-heap)
	 * @param b flag
	 */
	NodeComparator(boolean b){
		min_heap = b;
	}
	@Override
	public int compare(Node a, Node b) {
		
		if(min_heap) {
			if(a.getDist() < b.getDist()) {
				return -1;
			}
			else if(a.getDist() == b.getDist()) {
				return 0;
			}
			else {
				return 1;
			}
		}
		else { //comparator for a max-heap
			if(a.getDist() > b.getDist()) {
				return -1;
			}
			else if(a.getDist() == b.getDist()) {
				return 0;
			}
			else {
				return 1;
			}
		}
	}

}
