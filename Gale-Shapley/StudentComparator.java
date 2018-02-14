import java.awt.geom.Point2D;
import java.util.Comparator;

/**
 * StudentComparator implements the comparator interface such that
 * any two students can be compared directly, or using a particular
 * adviser's location as a reference.
 * 
 * @author Daniel Diamont
 *
 */
public class StudentComparator implements Comparator<Student> {

	 Point2D p;
	 boolean enableLoc;

	 /**
	  * Constructor takes an adviser's location to use as a
	  * reference.
	  * @param p
	  */
     StudentComparator(Point2D p) {

          this.p = p;
          this.enableLoc = true;
     }
     
     /**
      * Default constructor
      */
     StudentComparator(){
    	 this.p = null;
    	 enableLoc = false;
     }
     
	@Override
	public int compare(Student s1, Student s2) {
		// TODO Auto-generated method stub
		if(s1.getGPA() > s2.getGPA()) {
			return 1;
		}
		else if(s1.getGPA() < s2.getGPA()) {
			return -1;
		}
		else if(s1.getGPA() == s2.getGPA() && !enableLoc) {
			return 0;
		}
		else { //their GPA's are equal and we are sorting with location enabled as a parameter
			
			double distanceS1 = Math.abs(this.p.distance(s1.getLoc()));
			double distanceS2 = Math.abs(this.p.distance(s2.getLoc()));
			
			if(distanceS1 < distanceS2) {
				return 1;
			}
			else if(distanceS2 < distanceS1) {
				return -1;
			}
			else {
				//should not reach this case
				return 0;
			}
		}
	}

}
