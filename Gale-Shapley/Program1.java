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
    			if(possibleAdviser < currentAdviser) {
    				//check if that Adviser prefers the current student over possibleStudent
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
    					 * return true because possibleAdviser does not prefer currentStudent
    					 */
    					return true;
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
    	StudentPreferenceListMaker(studentList, adviserList, marriage);
    	
    	for(int i = 0; i < adviserList.size(); i++) {
    		System.out.println("\n\nAdvisor: " + i);
    		for(int j = 0; j < adviserList.get(i).prefList.size(); j++) {
    			System.out.print(adviserList.get(i).prefList.get(j).getNum() + " ");
    		}
    		
    	}
    	
    	for(int i = 0; i < studentList.size(); i++) {
    		System.out.println("\n\nStudent: " + i);
    		for(int j = 0; j < studentList.get(i).prefList.size(); j++) {
    			System.out.print(studentList.get(i).prefList.get(j).getNum() + " ");
    		}
    		System.out.println("");
    	}
    	
    	/*
    	 * Begin modified Gale-Shapley algorithm
    	 */
    	int studentIndex = findFreeStudent(studentList);
    	int adviserIndex = 0;
    	
    	while(studentIndex != -1)
    	{
    		Student student = studentList.get(studentIndex);
    	
    		
    		while(!student.prefList.get(adviserIndex).isFree()) {
    			
    			adviserIndex++;
    		}
    		
    		Adviser adv = student.prefList.get(adviserIndex);
			
			student.linkWithAdviser(adv);
			
			adv.linkWithStudent(student);
			
			studentIndex = findFreeStudent(studentList); //find another free student    	
			
			adviserIndex = 0;//reset adviser index
    		
    	}
    	
    	ArrayList<Integer> resMatching = new ArrayList<Integer>();
    	
    	for(int i = 0; i < studentList.size(); i++) {
    		
    		resMatching.add(adviserList.indexOf(studentList.get(i).returnAdviser()));
    	}
    	
    	marriage.setResidentMatching(resMatching);
    	
    	for(int i= 0; i < studentList.size(); i++)
    	{
    		System.out.println("Matching : Student: " + i + "// " + " Advisor: " + studentList.get(i).returnAdviser().getNum());    		
    	}
    	
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
    		
    		studentList.add(student);
    	}
    	
    	Collections.sort(studentList, new StudentComparator()); //sort student list in ascending GPA order
    	
    	for(int i= 0; i < matching.getNumberOfStudents(); i++) {
    		studentList.get(i).setNum(i);
    	}
    	
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
    		
    		adv.freeFromLink();
    		
    		adv.setNum(i);
    		//add to adviserlist
    		AdviserList.add(adv);
    		
    	}
    	
    	return AdviserList;
    		
    }
    
    public void StudentPreferenceListMaker(ArrayList<Student> studentList, ArrayList<Adviser> advList, Matching matching) {
    	
    	for(int i = 0; i < matching.getNumberOfStudents(); i++) {
			
			for(int j = 0; j < matching.getNumberOfAdvisers(); j++) {
				
				int advIndex = matching.getStudentPreference().get(i).get(j);	
				studentList.get(i).addToPrefList(advList.get(advIndex));

			}
			
		}
    	
    }
    
    public int findFreeStudent(ArrayList<Student> list) {
    	
    	for(int i = 0; i < list.size(); i++)
    	{
    		if(list.get(i).isFree() == true) {
    			return i;
    		}
    	}
    	return -1; //there does not exist a free student
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
