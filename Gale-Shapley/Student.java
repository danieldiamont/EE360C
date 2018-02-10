import java.awt.geom.Point2D;

public class Student {
	
	private double gpa;
	
	private Point2D location;
	
	public Student() {
		this.gpa = -100;
		this.location = null;
	}
	public Student(double gpa, Point2D location) {
		
		this.gpa = gpa;
		this.location = location;
	}
	
	public double getGPA() { 
		return gpa;
	}
	
	public Point2D getLoc() {
		return location;
	}	

}
