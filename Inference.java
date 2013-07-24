
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
	 *	@param input The user input that calls the mp() method.
	 *	@param psf ProofSoFar object.
	 *	@return Boolean True if the inference is valid.
	 **/
	public static boolean mp(String input, ProofSoFar psf){
		String[] text = input.split("\\s");
		//text[0] will be mp;
		String line1 = text[1];
		String line2 = text[2];
		String inference = text[3];
		//Input is of the form: mp [line#] [line#] [inference]
		//get will return an Expression object
		ProofTree E1 = psf.get(line1).myTree;
		ProofTree E2 = psf.get(line2).myTree;
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
	
	/**
	 *  mt() Checks that the mt inference was legal.
	 *  To be legal:
	 *  	First reference must be to a ~E2
	 *  	Second reference must be to (E1=>E2)
	 *  	Inference must be ~E1
	 *  
	 *  @param input The user input that calls the mt() method.
	 *  @param psf ProofSoFar object.
	 *  @return Boolean Result of the check.
	 **/
	public static boolean mt(String input, ProofSoFar psf){
		String[] text = input.split("\\s");
		//text[0] will be "mt"
		String line1 = text[1];
		String line2 = text[2];
		String inference = text[3];
		//get() returns expression objects
		ProofTree E2 = psf.get(line1).myTree;
		ProofTree E1 = psf.get(line2).myTree;
		if(!(E2.checkRoot("~"))){
			return false;
		}
		//get right subtree of E2; will be everything but ~
		//Check that right s.t. of E2 is same as right s.t. of E1
		if(!(E2.checkRightST(E1))){
			return false;
		}
		String checkE1 = E1.leftIs();
		checkE1 = "~ " + checkE1;
		if(!(checkE1.equals(inference))){
			return false;
		}
		return true;
	}
	
	/**
	 *  ic() Checks that an ic inference is legal.
	 *  To be legal:
	 *  	First reference must be to E2.
	 *  	Inference must be E1=>E2 for any E1
	 *  @param line LineNumber of the reference.
	 *  
	 **/
	public static void ic() {
		
	}
	
	public static void co() {
		
	}
}
