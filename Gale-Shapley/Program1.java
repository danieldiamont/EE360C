/*
 * Name: <Daniel Diamont>
 * EID: <dd28977>
 */

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the
     * Stable Marriage problem. Study the description of a Matching in the
     * project documentation to help you with this.
     */
    public boolean isStableMatching(Matching marriage) {
        /*
         * Given a matching, for each pair in the matching, check to see if the advisor
         * and the student could be better off
         * 
         * Algorithm:
         * 	for each pair in the matching, check to see if there exists an s' better than current
         * s. If this is true, check if there exists an a' that s prefers more than current a. If so,
         * then we have an instability where the pairs (a,s') and (a',s) would form given the opportunity...
         * 
         * Time Complexity: O(n^2)
         */
    	
    	ArrayList<Integer>student_matching = marriage.getStudentMatching();
    	
    	//for each student in the student_matching arraylist...
    	for(int student = 0; student < student_matching.size(); student++)
    	{
    		int currentAdviser = student_matching.get(student);
    	
    		//check the preference list of each student for the list of advisors
    		for(int adviser = 0; adviser < student_matching.size(); adviser++) {
    			
    			int possibleAdviser = marriage.getStudentPreference().get(student).get(adviser);
    			
    			//if there exists an advisor that is better than the current advisor
    			if(possibleAdviser < currentAdviser) {
    				//check if that advisor prefers the current student over possibleStudent
    					//need to track GPA and location of current student
    					//need to track professor location
    				double currentStudentGPA = marriage.getStudentGPAs().get(student);
    				
    				int possibleStudent = student_matching.indexOf(possibleAdviser);
    				double possibleStudentGPA = marriage.getStudentGPAs().get(possibleStudent);
    				
    				if(currentStudentGPA > possibleStudentGPA) {
    					return false;
    				}
    				else if(possibleStudentGPA == currentStudentGPA) {
    					if(currentStudentIsCloser(marriage,student,
    							possibleStudent,possibleAdviser)) {
    						/*
    						 * return false:
    						 * 
    						 * because possibleAdviser prefers currentStudent to his
    						 * possibleStudent
    						 * 
    						 * and currentStudent prefers possibleAdviser over currentAdviser
    						 *
    						 */
    						return false;
    					}
    				}
    				else {
    					/*
    					 * return true because possibleAdvisor does not prefer currentStudent
    					 */
    					return true;
    				}
    			}   				
    		}
    	}    				
		return true; //there does not exist an advisor that currentStudent prefers more
    }

    /**
     * Determines a solution to the Stable Marriage problem from the given input
     * set. Study the project description to understand the variables which
     * represent the input to your solution.
     * 
     * @return A stable Matching.
     */
    public Matching stableMarriageGaleShapley(Matching marriage) {
        /* TODO implement this function */
    	
    	//ensure kids are sorted by ascending GPA
    	
        return null; /* TODO remove this line */
    }
    
    public boolean currentStudentIsCloser(Matching marriage, 
    			int student, int possibleStudent, int possibleAdviser) {
    	
    	Coordinate currentStudentLoc 
		= marriage.getStudentLocations().get(student);
		Coordinate possibleAdviserLoc 
			= marriage.getAdviserLocations().get(possibleAdviser);
		
		Coordinate possibleStudentLoc 
			= marriage.getStudentLocations().get(possibleStudent);
		
		double curStudentDist_x 
			= Math.abs(currentStudentLoc.x-possibleAdviserLoc.x);
		double curStudentDist_y 
			= Math.abs(currentStudentLoc.y-possibleAdviserLoc.y);
		double curStudentDist_d 
			= Math.sqrt(Math.pow(curStudentDist_x, 2)
				+ Math.pow(curStudentDist_y, 2));
		
		double possibleStudentDist_x 
			= Math.abs(possibleStudentLoc.x-possibleAdviserLoc.x);
		double possibleStudentDist_y 
			= Math.abs(possibleStudentLoc.y-possibleAdviserLoc.y);
		double possibleStudentDist_d 
			= Math.abs(Math.pow(possibleStudentDist_x, 2)
				+ Math.pow(possibleStudentDist_y, 2));
		
		if(curStudentDist_d < possibleStudentDist_d)
		{
			return true;
		}
		else
			return false;
    }
}
