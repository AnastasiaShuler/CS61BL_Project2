
import java.util.Hashtable;
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
	 *	@param Proof.exprs Hashtable<String,Expression> object.
	 *	@return Boolean True if the inference is valid.
	 **/
	public static boolean mp(String input){
		//Input is of the form: mp [line#] [line#] [inference]
		String[] text = input.split("\\s");	//Split the input along spaces
		//text[0] will be mp;
		String line1 = text[1];				//Get LineNumber 1
		String line2 = text[2];				//Get LineNumber 2
		String inference = text[3];			//Get inference
		ProofTree E1 = Proof.exprs.get(line1).myTree;	//get the tree of line1
		Proof.exprs.get(line2);
		ProofTree E2 = Proof.exprs.get(line2).myTree;	//get the tree of line2
		//Check that E1 is a single expression
		//Check that E2 is of the form (E1=>E2)
		if(!(E1.checkLeft(E2))){
			if(!(E2.checkLeft(E1))){
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
		System.out.println(checkInference);
		System.out.println(inference);
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
	 *  @param Proof.exprs Hashtable<String,Expression> object.
	 *  @return Boolean Result of the check.
	 **/
	public static boolean mt(String input){
		String[] text = input.split("\\s");		//Split the input along spaces
		//text[0] will be "mt"
		String line1 = text[1];					//Get lineNumber 1
		String line2 = text[2];					//Get lineNumber 2
		String inference = text[3];				//Get inference string
		inference = inference.replaceAll("\\(", "");
		inference = inference.replaceAll("\\)", "");
		ProofTree E2 = Proof.exprs.get(line1).myTree;	//Get the tree of line1
		ProofTree E1 = Proof.exprs.get(line2).myTree;	//Get the tree of line2
		//Check that E1 or E2 has a root of '~'
		if(!(E2.checkRoot("~"))){
			if(!(E1.checkRoot("~"))){
				//Neither root was '~'; invalid usage
				return false;
			}else{
				//Switch the two inputs
				ProofTree temp = E1;
				E1 = E2;
				E2 = temp;
			}
		}
		//get right subtree of E2; will be everything but ~
		//Check that right s.t. of E2 is same as right s.t. of E1
		//Satisfies E2 and E1=>E2
		if(!(E2.checkRightST(E1))){
			return false;
		}
		String checkE1 = E1.leftIs();
		checkE1 = "~"+checkE1;
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
	 *  
	 *  @param input String of user input that calls the ic() method
	 *  @param Proof.exprs Hashtable<String,Expression> object
	 *  @return Boolen Result of the check
	 **/
	public static boolean ic(String input){
		String[] text = input.split("\\s");	//Split along spaces
		//text[0] contains "ic"
		String line = text[1];				//Get LineNumber
		String inference = text[2];			//Get inference
		String E1 = Proof.exprs.get(line).myString;	//Get string of line
		E1 = E1.replaceAll("\\)", "");
		E1 = E1.replaceAll("\\(", "");		//Remove the () from E1
		//Create a tree of inference
		ProofTree t1 = ProofTree.createATree(inference);
		String right = t1.rightIs();		//right is a string of _only_ the right subtree
		//return true if E1 is the same as the right side of the inference.
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
	public static boolean co(String input){
		String[] text = input.split("\\s");	//Split along spaces
		//text[0] contains "co"
		String line1 = text[1];				//Get the first lineNumber
		String line2 = text[2];				//Get the second lineNumber
		String inference = text[3];			//get the inference
		ProofTree E1 = Proof.exprs.get(line1).myTree;
		ProofTree E2 = Proof.exprs.get(line2).myTree;
		//Check to make sure one tree have a root of ~
		if(!(E1.checkRoot("~"))){
			if(!(E2.checkRoot("~"))){
				//neither tree has a root of ~
				return false;
			}
			//Switch the two inputs
			ProofTree temp = E1;
			E1 = E2;
			E2 = temp;
		}
		//Check to make sure the right sides of the tree are the same
		//Will check that two expressions are of the form E and ~E
		return E1.checkRight(E2);
		
	}
	
	/**
	 *  assume() Tests the validity of an assume statement
	 *  Checks that an assume directly follows a 'show' statement
	 *  Checks that the expression that is assumed is valid
	 *  
	 *  @param input Raw user input containing the assume statement
	 *  @param previousLine Raw user input of the previous line
	 *  @param exprs The Hashtable containing String/Expression pairs
	 **/
	public static boolean assume(String input, String previousLine)
				throws IllegalInferenceException{
		String[] usrInput = previousLine.split("\\s");
		if(!(usrInput[0].equals("show"))){
			Proof.line.prev();
			throw new IllegalInferenceException("*** Can only assume after a 'show' statement");
		}
		//The expression from the show statement
		String[] check = input.split("\\s");
		String assumption = check[1];
		Expression expr = new Expression(usrInput[1]);
		assumption = assumption.replaceAll("\\(", "");
		assumption = assumption.replaceAll("\\)", "");		//Extract assumption without parens
		String possAssum = "";
		possAssum = expr.myTree.leftIs();
		if(!(possAssum.equals(assumption))){
			possAssum = "~" + usrInput[1];
			if(!(possAssum.equals(assumption))){
				Proof.line.prev();
				throw new IllegalInferenceException("*** Illegal assumption");
			}
		}
		return true;
	}
}
