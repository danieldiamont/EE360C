import java.awt.geom.Point2D;

/**
 * This class is a blueprint for Adviser objects to help organize the data
 * given in a Matching
 * @author Daniel Diamont
 *
 */
public class Adviser {
	
	private Point2D location;
	
	private boolean free;
	private int num;
	private Student std;

	/**
	 * Default constructor
	 */
	public Adviser() {
		
		this.location = null;
	}
	
	/**
	 * constructor creates an adviser object with a location specified
	 * @param loc contains (x,y) coordinates, where x and y are of type double.
	 */
	public Adviser(Point2D loc) {
		this.location = loc;
	}
	
	/**
	 * 
	 * @return returns location of adviser
	 */
	public Point2D getLoc() {
		return this.location;
	}
	
	/**
	 * Sets the location of adviser
	 * @param coord
	 */
	public void setLoc(Coordinate coord) {
		double x = coord.x;
		double y = coord.y;
		
		this.location = new Point2D.Double(x,y);
	}
	
	/**
	 * 
	 * @return status of whether adviser has been matched
	 */
	public boolean isFree() {
		return this.free;
	}
	
	/**
	 * matches the adviser with a student
	 * @param student
	 */
	public void linkWithStudent(Student student) {
		this.free = false;
		this.std = student;
	}
	
	/**
	 * returns the student to which the adviser is matched with
	 * @return
	 */
	public Student returnStudent() {
		return this.std;
	}
	
	/**
	 * frees adviser from a matching
	 */
	public void freeFromLink() {
		this.free = true;
		this.std = null;
	}
	
	/**
	 * 
	 * @return the index of the adviser (specified in the original problem)
	 */
	public int getNum() {
		return this.num;
	}
	
	/**
	 * Sets the index of the adviser (specified in the original problem
	 * @param n
	 */
	public void setNum(int n) {
		this.num = n;
	}

}
