
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
    public int computeGain() {
    	
    	int numProjects = calculator.getNumProjects();
    	int numVibranium = calculator.getNumVibranium();
    	
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
    	
        return OPT[0][numVibranium];
    }

    /*
     * This method returns an integer that is the maximum possible dollar value that a thief 
     * could steal given the weight and volume capacity of his/her bag by using the 
     * VibraniumOreScenario instance.
     */
     //TODO: Complete this method
     public int computeLoss() {
        
    	 int numOres = vibraniumScenario.getNumOres();
    	 int maxWeight = vibraniumScenario.getWeightCapacity();
    	 int maxVolume = vibraniumScenario.getVolumeCapacity();
    	 
    	 System.out.println("num ores: " + numOres);
    	 System.out.println("max weight: " + maxWeight);
    	 System.out.println("max Volume: " + maxVolume);
    	 //initialize table
    	 int OPT[][][] = new int [numOres][maxWeight][maxVolume];
    	 
    	 System.out.println("\nFunction Table");
    	 for(int i = 0; i < numOres; i++) {
    		 VibraniumOre ore = vibraniumScenario.getVibraniumOre(i);
    		 System.out.println(ore.getPrice() + ", " + ore.getWeight() + ", " + ore.getVolume()); 
    	 }
    	 System.out.println();
    	 
    	 for(int w = 0; w < maxWeight; w++) {
    		 for(int v = 0; v < maxVolume; v++) {
    			 VibraniumOre ore = vibraniumScenario.getVibraniumOre(0);
    			 if(ore.getWeight() > w || ore.getVolume() > v) {
					 OPT[0][w][v] = 0;
				 }
    			 else {
    				 OPT[0][w][v] = ore.getPrice(); 
    			 }
    		 }
    	 }
    	 
    	 for(int i = 0; i < numOres; i++) {
    		 System.out.println();
    		 for(int w = 0; w < maxWeight; w++) {
    			 System.out.println();
    			 for(int v = 0; v < maxVolume; v++) {
    				System.out.print(OPT[i][w][v] + ","); 
    			 }
    		 }
    	 }
    	 System.out.println();
    	 
    	 for(int i = 1; i < numOres; i++) {
    		 for(int w = 0; w < maxWeight; w++) {
    			 for(int v = 0; v < maxVolume; v++) {
    				 VibraniumOre ore = vibraniumScenario.getVibraniumOre(i);
    				    				 
    				 try {
    					 if(ore.getWeight() > w || ore.getVolume() > v) {
        					 OPT[i][w][v] = OPT[i-1][w][v];
        				 }
        				 else {
        					 int a = OPT[i-1][w][v];
        					 int b = ore.getPrice() 
        							 + OPT[i-1][w-ore.getWeight()][v-ore.getVolume()];
        					 OPT[i][w][v] = (a < b) ?  b : a;     		
        				 }
    				 }
    				 catch(ArrayIndexOutOfBoundsException e) {
    					 e.printStackTrace();
    				 }
    			 }
    		 }
    	 }
    	 
    	 for(int i = 0; i < numOres; i++) {
    		 System.out.println();
    		 for(int w = 0; w < maxWeight; w++) {
    			 System.out.println();
    			 for(int v = 0; v < maxVolume; v++) {
    				System.out.print(OPT[i][w][v] + ","); 
    			 }
    		 }
    	 }
    	 System.out.println();
    	 
        return OPT[numOres-1][maxWeight-1][maxVolume-1];
     }
}


