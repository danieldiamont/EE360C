import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

public class Adviser {
	
	private Point2D location;
	
	private boolean free;
	private int num;
	private Student std;
	
	ArrayList<Student> prefList;
	ArrayList<Integer> proposition = new ArrayList<Integer>();
	
	public Adviser() {
		
		this.location = null;
		this.prefList = null;
	}
	
	public Adviser(Point2D loc, ArrayList<Student> list) {
		this.location = loc;
		this.prefList = list;
	}
	
	public Point2D getLoc() {
		return this.location;
	}
	
	public void creatPrefList(ArrayList<Student> listOfStudents) {
				
		this.prefList = new ArrayList<Student>(listOfStudents);
		
		Collections.sort(this.prefList, new StudentComparator(this.location));
		Collections.reverse(this.prefList);
		
	}
	
	public void setLoc(Coordinate coord) {
		double x = coord.x;
		double y = coord.y;
		
		this.location = new Point2D.Double(x,y);
	}
	
	public boolean isFree() {
		return this.free;
	}
	
	public void linkWithStudent(Student student) {
		this.free = false;
		this.std = student;
	}
	
	public Student returnStudent() {
		return this.std;
	}
	
	public void freeFromLink() {
		this.free = true;
		this.std = null;
	}
	
	public int getNum() {
		return this.num;
	}
	
	public void setNum(int n) {
		this.num = n;
	}

}
