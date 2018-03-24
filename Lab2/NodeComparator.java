import java.util.Comparator;

public class NodeComparator implements Comparator<Node>{

	boolean time;
	
	NodeComparator(boolean b){
		time = b;
	}
	@Override
	public int compare(Node a, Node b) {
		
		if(time) {
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
		else {
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
