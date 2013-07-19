public class Expression {
	
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
}
