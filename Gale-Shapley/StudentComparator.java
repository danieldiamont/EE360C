import java.awt.geom.Point2D;
import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {

	 Point2D p;

     StudentComparator(Point2D p) {

          this.p = p;

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
		else { //their GPA's are equal
			
			double distanceS1 = p.distance(s1.getLoc());
			double distanceS2 = p.distance(s2.getLoc());
			
			if(distanceS1 < distanceS2) {
				return 1;
			}
			else if(distanceS2 < distanceS1) {
				return -1;
			}
			else {
				//we done goofed
				return 0;
			}
		}
		return 0;
	}

}
