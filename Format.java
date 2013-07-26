import java.util.Stack;

public class Format {

	Stack charStack = new Stack ();

	/*
	 * checkFormat checks user input for overall validity. 
	 * For example, input beginning with "mp" must have
	 * four sections (mp, line number, line number, expression).
	 */

	public boolean checkFormat (String [ ] parts) {

		if (parts.length == 1) {
			/*
			 * if only 1 "part," then let it be checked by Expression.
			 */
			return true;
		}

		if (parts.length > 1) {
			/*
			 * if there are more than one parts in an expression, 
			 * the first part must be a keyword.
			 */

			if (parts[0].equals("mp") || parts[0].equals("co")) {
				/*
				 * These two keywords have the same format of
				 * "mp/co" [line number] [line number] [expression],
				 * so we can compound these into one check
				 */
				if (parts.length == 4) {
					return true;
				}
			}

			if (parts[0].equals("ic")) {
				if (parts.length == 3) {
					return true;
				}
			}

			if (parts[0].equals("show") 
			 || parts[0].equals("assume")
			 || parts[0] instanceof String) {
				if (parts.length == 2) {
					return true;
				}
			}
		}
		return false;
	}

	/* expressionValidity checks the expression section of user input for validity. */

	public boolean expressionValidity (Expression e) throws IllegalLineException {

		if (e.myString == null || e.myString.length() == 0){
			//checks an empty input
			throw new IllegalLineException ("You must extend the proof with " +
											"an expression, or mp, ic, co, show, assume, or a theorem name " +
											"followed by an expression.");

		} if (e.myString.length() == 1) {
			//if an expression has a length of 1, the only valid e.myStringession is a single variable
			if (Character.isLetter(e.myString.charAt(0))){
				return true;
			} else {
				throw new IllegalLineException("Single-character input must consist " +
												"of only one variable.");
			}

		} if (e.myString.length() == 2) {
			//if an expression has a length of 2, the only valid e.myStringession is ~variable
			if (e.myString.charAt(0) == '~' && Character.isLetter(e.myString.charAt(1))){
				return true;
			} else {
				throw new IllegalLineException("Two-character input must consist " +
												"of only ~ and a variable.");
			}

		} else {
			return expressionValidityHelper(e.myString);
		}
	}

	public boolean expressionValidityHelper(String expr) throws IllegalLineException {
		//DO WE NEED THIS NULL CHECK?!!!!!!!!!!!

			//checks if expression has an opening parenthesis at the second to last index or the very end 
			if (expr.charAt(expr.length() - 1) == '(' || expr.charAt(expr.length() - 2) == '('){
				throw new IllegalLineException("Your expression has a ( in an invalid place - " +
												"too late in the expression.");
			}

			//checks if expression has a closing parenthesis in the first or second index
			if (expr.charAt(0) == ')' || expr.charAt(1) == ')'){
				return false;
			}

			for (int k = 0; k < expr.length(); k++){
				//checks that we don't have a the following: ()
				if (expr.charAt(k) == '(' && (k + 1 < expr.length() && expr.charAt(k+1) == ')')){
					throw new IllegalLineException("You cannot enter an opening and closing " +
													"parentheses consecutively.");
				}

				//checks that all parentheses are matched
				if (expr.charAt(k) == '(') {
					charStack.push(expr.charAt(k));
				} else if (expr.charAt(k) == ')'){
					charStack.pop();
				}	
				if (k == expr.length() - 1 && !expr.isEmpty()) {
					throw new IllegalLineException("Your parentheses are unmatched.");
				}


							//checks conditions when you encounter variables
				if (Character.isLetter(expr.charAt(k))){
					if (k-1 >= 0){
						//checks possible characters that precede the current character
						if (expr.charAt(k-1) != '|' 
								|| expr.charAt(k-1) != '&'
								|| expr.charAt(k-1) != '~'
								|| expr.charAt(k-1) != '('
								//double check if we want to keep this paren check because we are substringing
								|| (expr.charAt(k-1) != '>' && k-2 >= 0 && expr.charAt(k-2) != '=')){
							throw new IllegalLineException ("There is an invalid character preceeding the variable."); 
						}
					if (k+1 < expr.length()){
						if (expr.charAt(k+1) != '|' 
								|| expr.charAt(k+1) != '&'
								|| expr.charAt(k+1) != ')'
								|| (expr.charAt(k+1) != '=' && k+2 < expr.length() && expr.charAt(k+2) != '>')){
							throw new IllegalLineException ("There is an invalid character following the variable.");
						}
					}
					
				//checks conditions when you encounter '&' or '|'
				if (expr.charAt(k) == '&' || expr.charAt(k) == '|'){
					if (k-1 >= 0){
						if (Character.isLetter(expr.charAt(k-1)) == false || expr.charAt(k-1) != ')'){
							throw new IllegalLineException("The only elements that may precede " + 
															"& or | are a variable or a ).");
						}
					}

					if (k+1 < expr.length()){
						if (expr.charAt(k+1) != '(' || Character.isLetter(expr.charAt(k+1)) == false){
							throw new IllegalLineException("The only elements that may follow " + 
															"& or | are a variable or a (.");
						}
					}
				}

				//checks condition when you encounter '~'
				if (expr.charAt(k) == '~'){
					if (k-1 >= 0){
						if (expr.charAt(k-1) != '(' || expr.charAt(k-1) != '&' || expr.charAt(k-1) != '|'
							|| expr.charAt(k-1) != '~') {
							//CHECK THIS
							throw new IllegalLineException("The only elements that may precede " +
														   "~ are (, &, ~. and |.");
						}
					}

					if (k+1 < expr.length()){
						if (expr.charAt(k+1) != '~' || Character.isLetter(expr.charAt(k+1)) == false){
							throw new IllegalLineException("The only elements that may follow " +
															"a ~ are ~, or a variable.");
						}
					}
				}
				/*				if (expr.charAt(k) == '~'){
					if (k-1 >= 0){
						//checks possible characters that precede the current character
						if (expr.charAt(k-1) != '|' 
								|| expr.charAt(k-1) != '&'
								|| expr.charAt(k-1) != '~'
								|| expr.charAt(k-1) != '('
								//double check if we want to keep this paren check because we are substringing
								|| (expr.charAt(k-1) != '>' && k-2 >= 0 && expr.charAt(k-2) != '=')){
							throw new IllegalLineException ("There is an invalid character preceeding ~."); 
						}
					if (k+1 < expr.length()){
						if (!Character.isLetter(expr.charAt(k+1)) 
								|| expr.charAt(k+1) != '~'){
							throw new IllegalLineException ("There is an invalid character following ~.");
						}
					}*/


				//checks condition when you encounter '=>'
				if (k+1 < expr.length() && (expr.charAt(k) == '=' && expr.charAt(k+1) == '>')){
					if (k-1 >= 0){
						if (expr.charAt(k-1) != ')' || Character.isLetter(expr.charAt(k+1)) == false){
							throw new IllegalLineException("The only elements that may precede " +
									   					"=> are a variable or ).");
						}
					}

					if (k+2 < expr.length()){
						if (expr.charAt(k+2) != '(' || Character.isLetter(expr.charAt(k+2)) == false){
							throw new IllegalLineException("The only elements that may follow " +
															"=> are a variable or (.");
						}
					}
				}
				
				
				//cases for ')' - must be followed by =, ), or be at the end of the expression
				//')' or a letter may precede ')'
				if (expr.charAt(k) == ')') {
					if (expr.charAt(k+1) != '=' || expr.charAt(k+1) != ')'
					 || k != expr.length()-1) {
						throw new IllegalLineException("The only elements that may follow "+
														"a ) are => or another ); otherwise," +
														"you must be at the end of the expression.");
					}
					if (Character.isLetter(expr.charAt(k-1)) == false || expr.charAt(k-1) != ')') {
						throw new IllegalLineException("The only elements that may precede "
													+ "a ) are a variable, or another ); otherwise, " +
													"you must be at the end of the expression.");
					}
				}
				if (expr.charAt(k) == '(') {
					//check recursion depth???
					return expressionValidityHelper(expr.substring(k, expr.length()));
				}
			}
			return true;
		}
	}
