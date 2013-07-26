import java.util.*;

/**
 *  Theorem Set
 *  Stores user theorems for a proof.
 *  Implements a TheoremSet object as a Hashtable
 **/
public class TheoremSet {
	//Instance variables
	Hashtable<String, Expression> myTheorems;

	/**
	 *  TheoremSet() Initializes a TheoremSet object.
	 *  Creates new Hashtable for the TheoremSet object.
	 **/
	public TheoremSet ( ) {
		myTheorems = new Hashtable <String, Expression>();
	}
	
	/**
	 *  put() Adds a theorem to a TheoremSet object.
	 *  Uses the theorem name as a key and stores an Expression object
	 *  	as a value.
	 *  
	 *  @param thrmName Name of the theorem.
	 *  @param expr Expression corresponding to the theorem.
	 **/
	public void put (String thrmName, Expression expr) {
		myTheorems.put(thrmName, expr);
	}
	
	/**
	 *  get() Returns the Expression object for a given theorem name.
	 *  
	 *  @param thrmName Name of the desired theorem.
	 **/
	public Expression get(String thrmName){
		return myTheorems.get(thrmName);
	}
	
	/**
	 *  TheoremChecker() Checks the usage of a theorem.
	 *  Heavily relies on the methods of the ProofTree class.
	 *  Throws IllegalInferenceException.
	 *  
	 *  @param thrmName Name of the given theorem.
	 *  @param input The line of input utilizing the theorem.
	 **/
	public boolean theoremChecker(String thrmName, String inputExpr) throws IllegalInferenceException{
		Expression thrm = myTheorems.get(thrmName);
		if(thrm == null){
			Proof.line.prev();
			throw new IllegalInferenceException("*** Bad theorem name");
		}
		Expression check = new Expression(inputExpr);
		return thrm.myTree.isSimilar(check.myTree);
	}
}
