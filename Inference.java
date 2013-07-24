
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
		//Input is of the form: mp [line#] [line#] [inference]
		String[] text = input.split("\\s");	//Split the input along spaces
		//text[0] will be mp;
		String line1 = text[1];				//Get LineNumber 1
		String line2 = text[2];				//Get LineNumber 2
		String inference = text[3];			//Get inference
		System.out.println("line1: " + line1 +"\nline2: " + line2 + "\ninference: " + inference);
		ProofTree E1 = psf.get(line1).myTree;	//get the tree of line1
		psf.get(line2);
		ProofTree E2 = psf.get(line2).myTree;	//get the tree of line2
		//Check that E1 is a single expression
		//Check that E2 is of the form (E1=>E2)
		if(!(E1.checkLeft(E2))){
			if(!(E2.checkLeft(E1))){
				System.out.println("I failed because my right side wasn't right");
				return false;
			} else{
				//Switch the two expressions
				ProofTree temp = E1;
				E1 = E2;
				E2 = temp;
			}
		}
		String checkE2 = E2.printInOrder(E2.myRoot);
		//will need to pull ()'s off of E2
		inference = inference.replaceAll("\\(", "");
		inference = inference.replaceAll("\\)", "");
		String checkInference = E1.rightIs();
		if(!(checkInference.equals(inference))){
			System.out.println("I failed because my inference doesn't match");
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
		String[] text = input.split("\\s");		//Split the input along spaces
		//text[0] will be "mt"
		String line1 = text[1];					//Get lineNumber 1
		String line2 = text[2];					//Get lineNumber 2
		String inference = text[3];				//Get inference string
		System.out.println("line1 is: " + line1);
		System.out.println("line2 is: " + line2);
		System.out.println("inference is: " + inference);
		ProofTree E2 = psf.get(line1).myTree;	//Get the tree of line1
		ProofTree E1 = psf.get(line2).myTree;	//Get the tree of line2
		System.out.println("E1 is");
		E1.print();
		System.out.println("E2 is");
		E2.print();
		//Check that E1 or E2 has a root of '~'
		if(!(E2.checkRoot("~"))){
			if(!(E1.checkRoot("~"))){
				//Neither root was '~'; invalid usage
				System.out.println("I've failed because neither root is ~");
				return false;
			}else{
				//Switch the two inputs
				System.out.println("I should have gotten in here");
				ProofTree temp = E1;
				E1 = E2;
				E2 = temp;
			}
		}
		//get right subtree of E2; will be everything but ~
		//Check that right s.t. of E2 is same as right s.t. of E1
		//Satisfies E2 and E1=>E2
		if(!(E2.checkRightST(E1))){
			System.out.println("I've failed here :(");
			return false;
		}
		String checkE1 = E1.leftIs();
		checkE1 = "~"+checkE1;
		if(!(checkE1.equals(inference))){
			System.out.println("this is where i fail");
			return false;
		}
		return true;
	}
	
	/**
	 *  ic() Checks that an ic inference is legal.
	 *  To be legal:
	 *  	First reference must be to E2.
	 *  	Inference must be E1=>E2 for any E1
	 *  
	 *  @param input String of user input that calls the ic() method
	 *  @param psf ProofSoFar object
	 *  @return Boolen Result of the check
	 **/
	public static boolean ic(String input, ProofSoFar psf) {
		String[] text = input.split("\\s");
		//text[0] contains "ic"
		String line = text[1];
		String inference = text[2];
		String E1 = psf.get(line).myString;
		ProofTree t1 = ProofTree.createATree(inference);
		String right = t1.rightIs();
		return E1.equals(right);
		
	}
	
	/**
	 *  co() Checks that a co inference is legal.
	 *  To be legal:
	 *  	First reference must be to E.
	 *  	Second reference must be to ~E.
	 *  	Inference can be anything.
	 *  
	 *  @param input String of user input that calls the co() method.
	 *  @return Boolean Result of the check.
	 **/
	public static boolean co(String input, ProofSoFar psf){
		String[] text = input.split("\\s");
		//text[0] contains "co"
		String line1 = text[1];
		String line2 = text[2];
		String inference = text[3];
		ProofTree E1 = psf.get(line1).myTree;
		ProofTree E2 = psf.get(line2).myTree;
		return E1.checkRight(E2);
		
	}
}
