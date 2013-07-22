import java.util.ArrayList;

public class Expression {
	ArrayList <String> parsed = new ArrayList <String> ();

	//sends to our binary tree?
	public Expression(String x) {
		int length = x.length();
		for (int i = 0; i < length; i++){
			if (x.charAt(i) == '('){
				parsed.add("(");
			} else if (x.charAt(i) == '~'){
				parsed.add("~");
			} else if ((length - (i+2)) >= 0){
			//catches index out of bounds scenario
				if (x.substring(i, i+2).equals("=>")){
					parsed.add("=>");
				}
			} else if(x.charAt(i) == '|') {
	        	parsed.add("|");
	        } else if(x.charAt(i) == '&') {
	        	parsed.add("&");
	        } else if (x.charAt(i) == ')'){
	        	parsed.add(")");
	        }
	    }
	}
}
