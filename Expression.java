public class Expression {
	ProofTree myTree;
	String myString;
	
	/**
	 *   Expression() Initializes an Expression object.
	 *   There are two fields:
	 *   	myTree - ProofTree of the given expression.
	 *   	myString - String of the given expression.
	 *   
	 *   @param s String containing the expression
	 **/
	public Expression(String s){
		myTree = ProofTree.createATree(s);
		myString = s;
	}
	
	/**
	 *  equals() Checks the equality of two Expression objects.
	 *  Overrides the equals() method.
	 *  Returns true if:
	 *  	the myTree variables are equivalent 
	 *   
	 *  @param object Object to check for equality
	 *  @return boolean Result of the equality check.
	 **/
	public boolean isEquals(Object obj){
		if(!(obj instanceof Expression)){
			return false;	//returns false if obj is not an Expression object
		}
		//Return the equality of the trees;
		//Trees have been chosen over strings b/c of parentheses
		return myTree.equals(((Expression) obj).myTree);
	}
	
	/*
	public Expression(String x) {
		 for (int i = x.length() - 1; i >= 0; --i) {
	         if (x.substring(i, i+2) == "=>") {
	             //detects implies =>
	         }
	         else if(x.charAt(i) == '~') {
	        	 //detects not ~
	         }
	         
	         else if(x.charAt(i) == '|') {
	        	 //detects or |
	         }
	         else if(x.charAt(i) == '&') {
	        	 //detects and &
	         }
	         else if(x.charAt(i) == '(') {
	        	 //use recursion to create a new Expression object
	         }
	         else {
	        	 //detects a character
	         }
	    }
	}
	*/
}
