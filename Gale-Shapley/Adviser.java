import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

public class Adviser {
	
	private Point2D location;
	
	private boolean free;
	
	private Student std;
	
	private ArrayList<Student> prefList = new ArrayList<Student>();
	
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
		
		Collections.sort(listOfStudents, new StudentComparator(this.location));
		
		this.prefList = listOfStudents; //prefList is now sorted list
	}
	
	public void setLoc(Coordinate coord) {
		this.location.setLocation(coord.x, coord.y);
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

}
