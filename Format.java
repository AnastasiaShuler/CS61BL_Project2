import java.util.Stack;
 
public class Format {
 
    Stack<Character> charStack;
	String specialChars = "()|&~=>";
	
    /*
     * checkFormat checks user input for overall validity. 
     * For example, input beginning with "mp" must have
     * four sections (mp, line number, line number, expression).
     */
 
public boolean checkFormat (String wholeInput) throws IllegalLineException {
        /* This method implements a preliminary "sanity check" on the overall 
         * format of user input. If checkFormat(string) returns true, then call
         * expressionValidity(expressionString) to check the validity of 
         * solely the expression part of user input.
         */
        String [ ] parts = wholeInput.split(" ");
 
        if (parts.length == 1) {
            /*
             * if only 1 "part," then print is the only valid expression
             */
            if(parts[0] != "print") {
                throw new IllegalLineException("*** bad expression:" + parts[0]);
            }
        }
 
        if (parts.length > 1) {
            /*
             * if there are more than one parts in an expression, 
             * the first part must be a keyword.
             */
 
            if (parts[0].equals("mp") || parts[0].equals("co") || parts[0].equals("mt")) {
                /*
                 * These two keywords have the same format of
                 * "mp/co/mt" [line number] [line number] [expression],
                 * so we can compound these into one check
                 */
                if (parts.length != 4) {
                    throw new IllegalLineException("*** bad inference format");
                }
            }
 
            else if (parts[0].equals("ic") || parts[0].equals("repeat")) {
                if (parts.length != 3) {
                    throw new IllegalLineException("*** not enough arguments");
                }
            }
 
            else { //if (parts[0].equals("show") || parts[0].equals("assume") || parts[0] instanceof String) {
                if (parts.length != 2) {
                    throw new IllegalLineException("*** not enough arguments");
                }
            }
        }
        
        //once you've checked that there are the right number of arguments, send it to the expression checker
        if(parts[0] != "print") {
            return expressionValidity(parts[parts.length - 1]);
        }
        
        return true;
    } 
    public boolean expressionValidity (String expr) throws IllegalLineException {
    	
    	int parenBalanced = 0;
    	expr = expr.trim();
        
        /* expressionValidity checks the expression section of user input for validity. */
    	
    	for (int i=0; i<expr.length(); i++) {
        	//check for characters that don't belong
    		boolean isSpecialChar = specialChars.contains(String.valueOf(expr.charAt(i)));
    		boolean isLetter = Character.isLetter(expr.charAt(i));
        	if (isSpecialChar == false && isLetter == false) {
        		throw new IllegalLineException("There is at least one invalid character in your input.");
            }
    	}
 
        if (expr == null || expr.length() == 0) {
            //checks an empty input
            throw new IllegalLineException ("You must extend the proof with an expression. " +
                                            "Alternately, you may reason with mp, ic, co, show, assume, " +
                                            "or a theorem name followed by an expression.");
        }
      
        if (expr.length() == 1) {
            //if an expression has a length of 1, the only valid expression is a single variable
            if (Character.isLetter(expr.charAt(0))) {
                return true;
            } else {
                throw new IllegalLineException ("Single-character input must consist of only one variable.");
            }
        }
        
        if (expr.length() == 2) {
            //if an expression has a length of 2, the only valid expression is ~variable
            if (expr.charAt(0) == '~' && Character.isLetter(expr.charAt(1))) {
                return true;
            } else {
                throw new IllegalLineException("Two-character input must consist of only ~ and a variable.");
            }
        }
        
        if (expr.length() > 2) {
	        for (int k = 0; k < expr.length(); k++) {
	 
	            //checks that all parentheses are matched through a stack
	            if (expr.charAt(k) == '(') {
	                parenBalanced++;
	            } 
	            if (expr.charAt(k) == ')') {
	            	parenBalanced--;
	            }   
	        } // end for loop
	        
	        if (parenBalanced != 0) {
	            throw new IllegalLineException ("Your parentheses are unmatched."); 
	        } 
	        
	        if (expr.charAt(0) != '(') {
	        	if (expr.charAt(0) != '~') {
	        		throw new IllegalLineException ("Expressions may begin only with ~ or (.");
	        	}
	        }
	        return expressionValidityHelper(expr);
        } 
       return true;
    }
 
    public boolean expressionValidityHelper(String expr) throws IllegalLineException {
    	
    	//only one =>, &, or | may be seen per "parentheses level"
    	int impliesSeen = 0;
    	int andSeen = 0;
    	int orSeen = 0;
    	int parenLevel = 0;
    	int operatorsSeen = 0;
    	
    	//boolean trueState = false;
    	
    	for (int k = 0; k < expr.length(); k++) {
                
                //increment parenLevel if you see an opening parentheses.
                if (expr.charAt(k) == '(') {
                	parenLevel++;
                }
                
                if (expr.charAt(k) == ')') {
                	parenLevel--;
                }
                
                //checks conditions when encountering '('
                //preceeding: (, >, &, |, ~
                //following: letter, ~, (
                if (expr.charAt(k) == '(' ) {
                	if ((k-1>0) && expr.charAt(k-1) != '(' && expr.charAt(k-1) != '>' && expr.charAt(k-1) != '&'
                		&& expr.charAt(k-1) != '|' && expr.charAt(k-1) != '~') {
	                    throw new IllegalLineException("Only (, =>, &, |, and ~ may precede " +
	                                                    "an opening parentheses.");
                	}

                	if (expr.charAt(k+1) != '~' && Character.isLetter(expr.charAt(k+1)) == false 
                		&& expr.charAt(k+1) != '(') {
                		throw new IllegalLineException("Only a letter or ~ may follow an opening parentheses.");
                	}
                }
                
         
                //checks conditions when you encounter variables
                //can't have single char in parens, e.g. (p)
                if (Character.isLetter(expr.charAt(k))) {
                	if (specialChars.contains(expr.substring(k+1, k+2)) == false
                	 && specialChars.contains(expr.substring(k-1, k)) == false) {
                		throw new IllegalLineException("Variables must be surrounded by other special characters.");
                	}
                		
                	if (expr.charAt(k+1) == ')' && expr.charAt(k-1) == '(') {
                		throw new IllegalLineException("You cannot enter a single character surrounded " +
                										"by parentheses.");

                	}
                }
 
                //checks conditions when you encounter '&' or '|'
                //preceeding: ), letter, 
                //following: (, letter, ~
 
                if (expr.charAt(k) == '&' || expr.charAt(k) == '|') {
                	
                	operatorsSeen++;
                	
                	if (expr.charAt(k) == '&') {
                		andSeen++;
                	}
                	if (expr.charAt(k) == '|') {
                		orSeen++;
                	}
                	if ((andSeen > parenLevel) || (orSeen > parenLevel)) {
                		throw new IllegalLineException ("Incorrect number of &s or |s you may only " +
                				"have one & or | per pair of parentheses.");
                	}
                	if (k-1 >= 0) {
                        if (Character.isLetter(expr.charAt(k-1)) == false && expr.charAt(k-1) != ')') {
                            throw new IllegalLineException("The only elements that may precede " + 
                                                            "& or | are a variable or a ).");
                        }
                    }
 
                    if (k+1 <= expr.length()) {
                        if (expr.charAt(k+1) != '(' && Character.isLetter(expr.charAt(k+1)) == false
                         && expr.charAt(k+1) != '~') {
                            throw new IllegalLineException("The only elements that may follow " + 
                                                            "& or | are a variable or a (.");
 
                        }
                    }
                }
 
                //checks condition when you encounter '~'
                //preceeding: &, |, (, ~, >
                //following: letter, ~, (,
                if (expr.charAt(k) == '~') {
                    if (k > 0) {
                        if (expr.charAt(k-1) != '(' && expr.charAt(k-1) != '&' && expr.charAt(k-1) != '|'
                         && expr.charAt(k-1) != '~' && expr.charAt(k-1) != '>') {
                            throw new IllegalLineException("The only elements that may precede " +
                                                           "~ are (, &, ~, >, and |.");
                        }
                    }
 
                    if (k+1 < expr.length()) {
                        if (expr.charAt(k+1) != '~' && Character.isLetter(expr.charAt(k+1)) == false
                         && expr.charAt(k+1) != '(') {
                            throw new IllegalLineException("The only elements that may follow " +
                                                            "a ~ are ~, (, or a variable.");
                        }
                    }
                }
 
                //checks condition when you encounter '=>'
                //only one => per paren pair
                //preceeding: ), letter
                //following: (, letter, ~

               if (k+1 < expr.length() && ((expr.charAt(k) == '=' || expr.charAt(k+1) == '>'))){
            	   
                	operatorsSeen++;
                	impliesSeen++;
                	
                	if (impliesSeen > parenLevel) {
                		throw new IllegalLineException("Only one => may be used inside a pair of parentheses.");
                	}
                	
                    if (k-1 >= 0) {
                        if (expr.charAt(k-1) != ')' && (Character.isLetter(expr.charAt(k-1)) == false)) {
	                            throw new IllegalLineException("The only elements that may precede " +
	                                                        "=> are a variable or ).");
                        }
                    }
                    
                    if (k+2 < expr.length()) {
                        if (expr.charAt(k+2) != '(' && Character.isLetter(expr.charAt(k+2)) == false
                         && expr.charAt(k+2) != '~') {
                            throw new IllegalLineException("The only elements that may follow " +
                                                            "=> are a variable, ~, or (.");
                        }
                    }
                }
 
                //cases for ')' - must be followed by =, ), or be at the end of the expression
                //')' or a letter may precede ')'
                if (expr.charAt(k) == ')') {
                	
                	operatorsSeen--;
                	//if (parenLevel - impliesSeen > 1) {
                	//	throw;
                	//}
                	impliesSeen -= 1;
                	//if (parenLevel - andSeen > 1) {
                	//	throw;
                	//}
                	andSeen -= 1;
                	//if (parenLevel - orSeen > 1)
                	orSeen -= 1;
                	
                	if (k-1 > 0) {
			            if (Character.isLetter(expr.charAt(k-1)) == false && expr.charAt(k-1) != ')') {
		                    throw new IllegalLineException("The only elements that may precede "
		                    							+ "a ) are a variable or another ).");
		                    }
                	}
                	if (k+1 < expr.length()) {
	                	if (expr.charAt(k+1) != '=' && expr.charAt(k+1) != ')' 
	                	 && expr.charAt(k+1) != '&' && expr.charAt(k+1) != '|') {
	                		throw new IllegalLineException("The only elements that may follow a ) are =>, &, | or another ); "+
	                									   "otherwise, you must be at the end of the expression.");
		                	}
		                }
                	}
	          	}
    	if (operatorsSeen != 0) {
    		throw new IllegalLineException("Illegal number of =>s, |s, or &s.");
    	}
    		return true;
    }
    
		public static void main(String[] args) throws IllegalLineException {
			 Format myFormat = new Format();
			 String d = new String ("((~p&~q)=>~(p|q))");
			 myFormat.expressionValidity(d);
		 }
    }
