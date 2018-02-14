import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * This class is a template for creating student objects, which contain
 * all of the information a student given in the original problem
 * @author Daniel Diamont
 *
 */
public class Student {
	
	private double gpa;
	
	private boolean free;
	
	private Point2D location;
	
	private Adviser adv;
	private int num;
	
	ArrayList<Boolean> proposition = new ArrayList<Boolean>();
	
	ArrayList<Integer> prefList = new ArrayList<Integer>();
	
	
	/**
	 * Default constructor
	 */
	public Student() {
		this.gpa = -100;
		this.location = null;
	}
	
	/**
	 * User defined constructor
	 * @param gpa passed as a double
	 * @param location (x,y) where x and y are of type double
	 */
	public Student(double gpa, Point2D location) {
		
		this.gpa = gpa;
		this.location = location;
	}
	
	/**
	 * 
	 * @return student's GPA
	 */
	public double getGPA() { 
		return this.gpa;
	}
	
	/**
	 * 
	 * @return student's location
	 */
	public Point2D getLoc() {
		return location;
	}
	
	/**
	 * Set the student's GPA
	 * @param gpa
	 */
	public void setGPA(Double gpa) {
		this.gpa = gpa;
	}
	
	/**
	 * Set the student's location
	 * @param coord
	 */
	public void setLoc(Coordinate coord) {
		
		double x = coord.x;
		double y = coord.y;
		
		this.location = new Point2D.Double(x,y);
	}
	
	/**
	 * Add the index of an adviser to the student's preference list.
	 * @param adv
	 */
	public void addToPrefList(Integer adv) {
		this.prefList.add(adv);
	}
	
	/**
	 * 
	 * @return status of whether a student has been matched.
	 */
	public boolean isFree() {
		return this.free;
	}
	
	/**
	 * Links the studet with a particular adviser
	 * @param adv
	 */
	public void linkWithAdviser(Adviser adv) {
		this.free = false;
		this.adv = adv;
	}
	
	/**
	 * 
	 * @return the adviser to which the student is matched
	 */
	public Adviser returnAdviser() {
		return this.adv;
	}
	
	/**
	 * frees the student from a pair
	 */
	public void freeFromLink() {
		this.free = true;
		this.adv = null;
	}
	
	/**
	 * 
	 * @return the student's index (specified in the original problem)
	 */
	public int getNum() {
		return this.num;
	}
	
	/**
	 * set the student's index (specified in the original problem)
	 * @param n
	 */
	public void setNum(int n) {
		this.num = n;
	}
    		
		

}
