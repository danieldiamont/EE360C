
//Name: Daniel Diamont
//EID: dd28977


public class Program3 {

    EconomyCalculator calculator;
    VibraniumOreScenario vibraniumScenario;    

    public Program3() {
        this.calculator = null;
        this.vibraniumScenario = null;
    }

    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3 for Part 1.
     */
    public void initialize(EconomyCalculator ec) {
        this.calculator = ec;
    }

    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3 for Part 2.
     */
    public void initialize(VibraniumOreScenario vs) {
        this.vibraniumScenario = vs;
    }

    /*
     * This method returns an integer that is maximum possible gain in the Wakandan economy
     * given a certain amount of Vibranium
     */
    //TODO: Complete this function
    public int computeGain() {
    	
    	int numProjects = calculator.getNumProjects();
    	int numVibranium = calculator.getNumVibranium();
    	
    	System.out.println("num projects: " + numProjects);
    	System.out.println("num vib: " + numVibranium);
    	
    	//initialize OPT
    	int [][] OPT = new int [numProjects][numVibranium+1];
    	
    	for(int i = 0; i <= numVibranium; i++) {
    		OPT[numProjects-1][i] = calculator.calculateGain(numProjects-1,	i);
    	}
    	
    	for(int i = numProjects-2; i >= 0; i--) {
    		for(int j = 0; j <= numVibranium; j++) {
    			int bestBoost = 0;
    			
    			for(int k = 0; k <= j; k++) {
    				int gain = calculator.calculateGain(i, k);
    				int thisBoost = gain + OPT[i+1][j-k];
    				if(thisBoost > bestBoost) {
    					bestBoost = thisBoost;
    				}
    			}
    			OPT[i][j] = bestBoost;
    		}
    	}
    	
    	
    	for(int i = 0; i < numProjects;i++) {
    		System.out.println();
    		for(int j = 0; j < numVibranium+1;j++) {
    			System.out.print(", " + OPT[i][j]);
    		}
    	}
    	System.out.println();
        return OPT[0][numVibranium];
    }

    /*
     * This method returns an integer that is the maximum possible dollar value that a thief 
     * could steal given the weight and volume capacity of his/her bag by using the 
     * VibraniumOreScenario instance.
     */
     //TODO: Complete this method
     public int computeLoss() {
        
        return 0;
     }
}


