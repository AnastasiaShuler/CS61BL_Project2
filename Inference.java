
/**
 *  Inference
 *  Contains methods to check the validity of inferences
 *  **METHODS**
 *  	mp() Checks that the mp inference was legal.
 *  	mt() Checks that the mt inference was legal.
 *  	ic() Checks that the ic inference was legal.
 *  	co() Checks that the co inference was legal.
 **/
public class Inference {
	
	/**
	 *  mp() Checks that the mp inference is legal.
	 *  To be legal:
	 *  	First reference must be to E1
	 *  	Second reference must be to (E1=>E2).
	 *  	Inference must be E2.
	 *	
	 *	@param line1 Line number of the first reference.
	 *	@param line2 Line number of the second reference.  
	 *	@param inference Inference the user claims is true
	 *	@return Boolean True if the inference is valid.
	 **/
	public static boolean mp(String line1, String line2, String inference){
		//get will return an Expression object
		ProofTree E1 = ProofSoFar.get(line1).myTree;
		ProofTree E2 = ProofSoFar.get(line2).myTree;
		//Check that E1 is a single expression
		//Check that E2 is of the form (E1=>E2)
		if(!(E2.checkLeft(E1))){
			return false;
		}
		String checkE2 = E2.rightIs();
		//will need to pull ()'s off of E2
		if(!(checkE2.equals(E2))){
			return false;
		}
		return true;
	}
	
	public static void mt() {
		
	}
	
	public static void ic() {
		
	}
	
	public static void co() {
		
	}
}
