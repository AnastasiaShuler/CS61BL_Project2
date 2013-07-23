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
}
