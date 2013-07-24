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
	
	
	public boolean theoremChecker(String thrmName, String input){
	}
	
	public boolean TheoremChecker(String thrmName, String input, TreeNode thrmRoot, TreeNode inputRoot){
		Hashtable<String, String> variableMatch = new Hashtable<String, String>();
		Expression thrmExpr = new Expression(thrmName);
		Expression inputExpr = new Expression(input);
		return false;
	}
}
