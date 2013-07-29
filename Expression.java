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
	 *  	the myString variables are equivalent
	 *   
	 *  @param object Object to check for equality
	 *  @return boolean Result of the equality check.
	 **/
	public boolean equals(Object obj){
		if(!(obj instanceof Expression)){
			return false;	//returns false if obj is not an Expression object
		}
		//Return the equality of the trees;
		boolean tree = myTree.equals(((Expression) obj).myTree);
		//Returns the equality of the strings;
		boolean string = myString.equals(((Expression) obj).myString);
		//Returns false if either tree or string is false
		return !(tree == false || string == false);
		
	}
}
