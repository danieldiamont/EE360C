import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge>{

	
	EdgeComparator(){
		
	}
	
	@Override
	public int compare(Edge o1, Edge o2) {
		
		if(o1.getTime() == o2.getTime() && o1.getCapacity() == o2.getCapacity() && o1.getNode().equals(o2.getNode())) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	

}
