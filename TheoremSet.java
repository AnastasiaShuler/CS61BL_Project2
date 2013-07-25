import java.util.*;

public class TheoremSet {
	Hashtable<String, Expression> myTheorems;


	public TheoremSet ( ) {
		myTheorems = new Hashtable <String, Expression>();
	}

	public Expression put (String s, Expression e) {
		myTheorems.put(s, e);
		return myTheorems.get(s);
	}
	
	
	public boolean theoremChecker(String thrmName, String input) throws IllegalInferenceException{
		Expression thrm = new Expression(thrmName);
		Expression check = new Expression(input);
		return thrm.myTree.isSimilar(check.myTree);
	}
}
