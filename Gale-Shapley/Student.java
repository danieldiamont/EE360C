import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Student {
	
	private double gpa;
	
	private boolean free;
	
	private Point2D location;
	
	private Adviser adv;
	private int num;
	
	ArrayList<Adviser> proposition = new ArrayList<Adviser>();
	
	ArrayList<Adviser> prefList = new ArrayList<Adviser>();
	
	public Student() {
		this.gpa = -100;
		this.location = null;
	}
	public Student(double gpa, Point2D location) {
		
		this.gpa = gpa;
		this.location = location;
	}
	
	public double getGPA() { 
		return this.gpa;
	}
	
	public Point2D getLoc() {
		return location;
	}
	
	public void setGPA(Double gpa) {
		this.gpa = gpa;
	}
	
	public void setLoc(Coordinate coord) {
		
		double x = coord.x;
		double y = coord.y;
		
		this.location = new Point2D.Double(x,y);
	}
	
	public void addToPrefList(Adviser adv) {
		this.prefList.add(adv);
	}
	
	public boolean isFree() {
		return this.free;
	}
	
	public void linkWithAdviser(Adviser adv) {
		this.free = false;
		this.adv = adv;
	}
	
	public Adviser returnAdviser() {
		return this.adv;
	}
	
	public void freeFromLink() {
		this.free = true;
		this.adv = null;
	}
	
	public int getNum() {
		return this.num;
	}
	
	public void setNum(int n) {
		this.num = n;
	}
    		
		

}
