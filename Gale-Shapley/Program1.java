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
    		for(int i = 0; i < student_matching.size(); i++) {
    			
    			int possibleAdviser = marriage.getStudentPreference().get(student).get(i);    			
    			
    			//if there exists an Adviser that is better than the current Adviser
    			if(marriage.getStudentPreference().get(student).indexOf(possibleAdviser) <
    					marriage.getStudentPreference().get(student).indexOf(currentAdviser)) {
    				//check if that Adviser prefers the current student over possibleStudent
    					//need to track GPA and location of current student
    					//need to track professor location
    				double currentStudentGPA = marriage.getStudentGPAs().get(student);
    				
    				int possibleStudent = student_matching.indexOf(possibleAdviser);
    				double possibleStudentGPA = marriage.getStudentGPAs().get(possibleStudent);
    				
    				if(currentStudentGPA > possibleStudentGPA) {    					
    					return false;
    				}
    				else if(Double.compare(currentStudentGPA, possibleStudentGPA) == 0) {
    					
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
    	
    	int numAdvisers = marriage.getNumberOfAdvisers();
    	int numStudents = marriage.getNumberOfStudents();
    	
    	//declare adviser preference list
    	ArrayList<ArrayList<Integer>> adviserPreferenceList = new ArrayList<ArrayList<Integer>>();    	
    	ArrayList<Boolean> adviserEngaged = new ArrayList<Boolean>(Collections.nCopies(numAdvisers, false));    	
    	ArrayList<Boolean> studentEngaged = new ArrayList<Boolean>(Collections.nCopies(numStudents, false));
    	ArrayList<ArrayList<Boolean>> adviserProposalList = new ArrayList<ArrayList<Boolean>>();
    	for(int i = 0; i < numAdvisers; i++) {
    		ArrayList<Boolean> list = new ArrayList<Boolean>(Collections.nCopies(numAdvisers, false));
    		adviserProposalList.add(i, list);
    	}
    	ArrayList<Integer> new_student_matching = new ArrayList<Integer>(Collections.nCopies(numStudents, -1));
    	
    	//sort students by GPA
    	ArrayList<Double> studentList = new ArrayList<Double>();    	
    	for (int i = 0; i < marriage.getNumberOfStudents(); i++) {
    		studentList.add(i, marriage.getStudentGPAs().get(i));
    	}    	
    	Collections.sort(studentList);
    	Collections.reverse(studentList);
    	
    	//for each adviser
    	for(int i = 0; i < marriage.getNumberOfAdvisers(); i++) {
    		ArrayList<Double> calcList = new ArrayList<Double>(studentList);
    		ArrayList<Integer> personalizedStudentList = new ArrayList<Integer>();    		
    		
    		//for each student
    		for(int j = 0; j < marriage.getNumberOfStudents(); j++) {
    			
    			//check if we have ties
    			int firstIndex = calcList.indexOf(marriage.getStudentGPAs().get(j));
    			int lastIndex = calcList.lastIndexOf(marriage.getStudentGPAs().get(j));
    			
    			if(firstIndex != lastIndex) {
    				//if this case is true, we have ties
    				int numTies = lastIndex - firstIndex + 1;
    				
    				//create a queue, add to front index of closer student, add to back index of farthest student
    				LinkedList<Integer> queue = new LinkedList<Integer>();
    				
    				int index = firstIndex + 1;
    				
    				for(int counter = 0; counter < numTies; counter++) {
    					
    					int max = firstIndex;
    					
    					if(studentIsCloser(marriage, index,max,i)) {
    						max = index;
    					}
    				}
    				
    				//add all these guys to the adviser's personalized list
    			}
    			else {
    				personalizedStudentList.add(j);
    			}
    		}
    		adviserPreferenceList.add(i, personalizedStudentList);
    	}
    	
    	/*
    	 * begin GS algo
    	 */
    	boolean fullMatching = false;
    	
    	while(!fullMatching) {
    		
    		for(int adviser = 0; adviser < marriage.getNumberOfAdvisers(); adviser++)
    		{
    			int studentIndex = 0;
    			
    			while(adviserEngaged.get(adviser) == false) //if adviser is free
    			{
    				//chose a student
    				int student = adviserPreferenceList.get(adviser).get(studentIndex); //get the student at the top of the preference list
    				
    				if(adviserProposalList.get(adviser).get(studentIndex) == false) { //if adviser has not proposed to student
    					
    					adviserProposalList.get(adviser).add(studentIndex, true);
    					
    					if(studentEngaged.get(student) == false) { //if student is free
    						//become pairing
    						studentEngaged.set(student, true);
    						adviserEngaged.set(adviser, true);
    						new_student_matching.set(student, adviser);
    					}
    					else { //student is already engaged
    						
    						int curAdviser = new_student_matching.get(student);
    						
    						//if student prefers this adviser over his current adviser
    						if(marriage.getStudentPreference().get(student).indexOf(adviser) <
    								marriage.getStudentPreference().get(student).indexOf(curAdviser)) {
    							
    							//break off current engagement and make new engagement
    							adviserEngaged.set(curAdviser, false);
    							studentEngaged.set(student,true);
    							adviserEngaged.set(adviser,true);
    							new_student_matching.set(student,adviser);
    							
    						}
    						else {
    							studentIndex++;
    						}
    					}
    					
    				}
    				else {
    					//if adviser proposed to student already, pick another student
    					studentIndex++;
    				}
    				
    			}
    		}
    		//for all advisers, if at least one adviser is free, we haven't finished the algorithm
			fullMatching = true;
			for(int i = 0; i < marriage.getNumberOfAdvisers(); i++) {
				if(adviserEngaged.get(i) == false)
					fullMatching = false;
			}
    	}
    	
    	marriage.setResidentMatching(new_student_matching);
    	
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
    	Collections.reverse(studentList); //sorted list in descending order
    	
    	for(int i= 0; i < matching.getNumberOfStudents(); i++) {
    		studentList.get(i).setNum(i);
    	}
    	
    	return studentList;
    }
    	
    public ArrayList<Adviser> AdviserListMaker(Matching matching, ArrayList<Student> unSortedList){    	
    	
    	ArrayList<Adviser> AdviserList = new ArrayList<Adviser>();
    	
//    	for(int i = 0; i < matching.getNumberOfAdvisers(); i++) {
//    	
//    		Adviser adv = new Adviser();
//    		
//    		//set location
//    		adv.setLoc(matching.getAdviserLocations().get(i));
//   
//    		//generate preference list
//    		adv.creatPrefList(unSortedList);    		    		
//    		adv.freeFromLink(); //set advisor linkage to false    		
//    		adv.setNum(matching.getStudentPreference().get(arg0));
//    		
//    		//add to adviserlist
//    		AdviserList.add(adv);
//    		
//    	}    	
    	for(Integer adviserIndex : matching.getStudentPreference().get(0)) {
    		Adviser adv = new Adviser();
    		
    		//set location
    		adv.setLoc(matching.getAdviserLocations().get(adviserIndex));
   
    		//generate preference list
    		adv.creatPrefList(unSortedList);    		    		
    		adv.freeFromLink(); //set advisor linkage to false    		
    		adv.setNum(adviserIndex);
    		
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
    	
    	for(Student student : list) {
    		if(student.isFree() == true) {
    			return student.getNum();
    		}
    	}
    	return -1; //there does not exist a free student
    }
    
    public int findFreeAdviser(ArrayList<Adviser>list) {
    	for(Adviser adviser: list) {
    		if(adviser.isFree() == true) {
    			return adviser.getNum();
    		}
    	}
    	return -1;
    }
    
    
    public boolean studentIsCloser(Matching marriage, 
			int student, int possibleStudent, int possibleAdviser) {
	
    	Coordinate curStudentCoord = marriage.getStudentLocations().get(student);
    	Point2D currentStudentLoc = new Point2D.Double(curStudentCoord.x,curStudentCoord.y);
    	
    	Coordinate adviserCoord = marriage.getAdviserLocations().get(possibleAdviser);
    	Point2D possibleAdviserLoc = new Point2D.Double(adviserCoord.x,adviserCoord.y);
    	
    	Coordinate studPrimeCoord = marriage.getStudentLocations().get(possibleStudent);
    	Point2D possibleStudentLoc = new Point2D.Double(studPrimeCoord.x,studPrimeCoord.y);
    	
    	double curStudentDist = Math.abs(possibleAdviserLoc.distance(currentStudentLoc));
    	double possibleStudentDist = Math.abs(possibleAdviserLoc.distance(possibleStudentLoc));
		
		if(curStudentDist < possibleStudentDist)
		{
			return true;
		}
		else
			return false;
    }
}
