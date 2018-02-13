/*
 * Name: <Daniel Diamont>
 * EID: <dd28977>
 */

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
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
        /**
         * Given a matching, for each pair in the matching, check to see if the Adviser
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
    	
    		//check the preference list of each student for the list of Advisers
    		for(int Adviser = 0; Adviser < student_matching.size(); Adviser++) {
    			
    			int possibleAdviser = marriage.getStudentPreference().get(student).get(Adviser);
    			
    			//if there exists an Adviser that is better than the current Adviser
    			if(marriage.getStudentPreference().get(student).indexOf(possibleAdviser) <
    					marriage.getStudentPreference().get(student).indexOf(currentAdviser)) {
    			
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
    					
    					//do nothing
    				}
    			}   				
    		}
    	}    				
		return true; //there does not exist an Adviser that currentStudent prefers more
    }

    /**
     * Determines a solution to the Stable Marriage problem from the given input
     * set. Study the project description to understand the variables which
     * represent the input to your solution.
     * 
     * @return A stable Matching.
     */
    public Matching stableMarriageGaleShapley(Matching marriage) {
    	
    	/*
    	 * Create student lists and adviser lists from file information.
    	 * 
    	 * Generate all the preference lists, stored in each Adviser and Student object
    	 */
    	ArrayList<Student> studentList = studentListMaker(marriage);
    	ArrayList<Adviser> adviserList = AdviserListMaker(marriage, studentList);
    	
    	/*
    	 * Begin modified Gale-Shapley algorithm
    	 */
    	boolean fullMatching = false;
    	while(!fullMatching) {
    		
    		int studentIndex = findFreeStudent(studentList);
    		
    		//choose a free student
    		Student student = studentList.get(studentIndex);
    		
    		Adviser adviser;
    		
    		int chooseAdviser = 0;
    		
    		while(student.isFree()) {
    			
    			//let adviser be the top adviser on student's list to whom student has not proposed to
    			 int adviserIndex = student.prefList.get(chooseAdviser); //get index of adviser
    			
    			if(student.proposition.get(adviserIndex) == false) { //check if we have proposed to that adviser
    				//if no
    				student.proposition.set(adviserIndex, true); //set proposal value to true
    				
    				//check if said adviser is free
    				adviser = adviserList.get(adviserIndex);
    				
    				if(adviser.isFree()) { //if adviser is free
    					//create pairing
    					adviser.linkWithStudent(student);
    					student.linkWithAdviser(adviser);
    				}
    				else { //if adviser is engaged
    					Student studentPrime = adviser.returnStudent(); //get adviser's current partner
    					
    					//check if adviser prefers student over studentPrime
    					if(adviser.prefList.indexOf(student) < adviser.prefList.indexOf(studentPrime)) {
    						//if so, create pairing
    						studentPrime.freeFromLink();
    						adviser.linkWithStudent(student);
    						student.linkWithAdviser(adviser);
    					}
    				}
        			
        		}
    			else {
    				//proposed to adviser already... try again
    				chooseAdviser++;    				
    			}
    		}
    		
    		if(findFreeStudent(studentList) == -1) {
    			fullMatching = true;
    		}
    	}
    	    		
    		
    	
    	ArrayList<Integer> resMatching = new ArrayList<Integer>(Collections.nCopies(marriage.getNumberOfStudents(), -1));
    	
    	for(Student student : studentList) {
    		resMatching.set(student.getNum(), student.returnAdviser().getNum());
    	}
    	
    	marriage.setResidentMatching(resMatching);
    	
        return marriage;
    	
    }
    
    
    public ArrayList<Student> studentListMaker(Matching matching){
    	
    	//create arrayList of students
    	ArrayList<Student> studentList = new ArrayList<Student>();
    	
    	for(int i = 0; i < matching.getNumberOfStudents(); i++) {
    		
    		Student student = new Student();
    		student.setGPA(matching.getStudentGPAs().get(i));
    		student.setLoc(matching.getStudentLocations().get(i));
    		student.freeFromLink();
    		student.setNum(i);
    		studentList.add(student);
    		
    		for(int j = 0; j < matching.getNumberOfAdvisers(); j++) {
				
				int advIndex = matching.getStudentPreference().get(i).get(j);	
				studentList.get(i).addToPrefList(advIndex);
				studentList.get(i).proposition.add(false);
    		}
    	}
    	
    	//Collections.sort(studentList, new StudentComparator());
    	
    	return studentList;
    }
    	
    public ArrayList<Adviser> AdviserListMaker(Matching matching, ArrayList<Student> unSortedList){    	
    	
    	ArrayList<Adviser> AdviserList = new ArrayList<Adviser>();
    	
    	for(int i = 0; i < matching.getNumberOfAdvisers(); i++) {
    	
    		Adviser adv = new Adviser();
    		
    		//set location
    		adv.setLoc(matching.getAdviserLocations().get(i));
   
    		//generate preference list
    		adv.creatPrefList(unSortedList);    		    		
    		adv.freeFromLink(); //set advisor linkage to false    		
    		adv.setNum(i);
    		
    		//add to adviserlist
    		AdviserList.add(adv);
    		
    	}    	
    	return AdviserList;	
    }
    
    public int findFreeStudent(ArrayList<Student> list) {
    	
    	for(int i = 0; i < list.size(); i++) {
    		if(list.get(i).isFree() == true) {
    			return i;
    		}
    	}
    	return -1; //there does not exist a free student
    }
    
    public boolean currentStudentIsCloser(Matching marriage, 
			int student, int possibleStudent, int possibleAdviser) {
	
    	Coordinate coord = marriage.getStudentLocations().get(student);
    	Point2D currentStudentLoc = new Point2D.Double(coord.x,coord.y);
    	
    	coord = marriage.getAdviserLocations().get(possibleAdviser);
    	Point2D possibleAdviserLoc = new Point2D.Double(coord.x,coord.y);
    	
    	coord = marriage.getStudentLocations().get(possibleStudent);
    	Point2D possibleStudentLoc = new Point2D.Double(coord.x,coord.y);
    	
    	double curStudentDist = possibleAdviserLoc.distance(currentStudentLoc);
    	double possibleStudentDist = possibleAdviserLoc.distance(possibleStudentLoc);
		
		if(curStudentDist < possibleStudentDist)
		{
			return true;
		}
		else
			return false;
    }
}
