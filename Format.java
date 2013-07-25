<<<<<<< HEAD
public class Format {
	
=======
import java.util.Stack;

public class Format {

	Stack charStack = new Stack ();

>>>>>>> a3130b935956f222a994261bfe0718da7f7c050f
	/*
	 * This class checks user input for overall validity. 
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
			if (parts[0].equals("mp") || parts[0].equals("co") || 
				parts[0].equals("ic") || parts[0].equals("show") || 
				parts[0].equals("assume")) { /* || !parts[0].equals("TheoremName" how to handle cases of theorem names?*/
				return true;
			}
			
			if (parts[0].equals("mp") || parts[0].equals("co")) {
				/*
				 * These two keywords should have the same format of
				 * "mp/co" [line number] [line number] [expression],
				 * so we can compound these into one check
				 */
				if (parts.length == 4) {
					return true;
				/*	
				 * Initially, I wanted to check the validity of each 
				 * section of the user input; however, I don't know if
				 * this is practical/possible.
				 * 
				 * parts[1] != VALID LINE NUMBER
				 || parts[2] != VALID LINE NUMBER
				 || parts[3] != VALID EXPRESSION) 
					return false;*/
				}
			}
			
			if (parts[0].equals("ic")) {
				if (parts.length == 3) {
					return true;
				}
			}
			
			if (parts[0].equals("show") 
			 || parts[0].equals("assume")
			 /*|| parts[0].equals("Theorem name") either check for 
			 existing theorem, or for just a string*/) {
				if (parts.length == 2) {
					return true;
				}
			}
		}
		return false;
	}
<<<<<<< HEAD
=======

		
	public boolean expressionValidity (Expression e){
		if (expressionValidityHelper(e.myString)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean expressionValidityHelper(String expr){
		//DO WE NEED THIS NULL CHECK?!!!!!!!!!!!
		if (expr == null || expr.length() == 0){
			//checks an empty input
			return false;
			
		} else if (expr.length() == 1){
			//if an expression has a length of 1, the only valid expression is a single variable
			if (Character.isLetter(expr.charAt(0))){
				return true;
			} else {
			return false;
			}
			
		} else if (expr.length() == 2){
			//if an expression has a length of 2, the only valid expression is ~variable
			if (expr.charAt(0) == '~' && Character.isLetter(expr.charAt(1))){
				return true;
			} else {
			return false;
				}
		
		} else {

			//checks if expression has an opening parenthesis at the second to last index or the very end 
			if (expr.charAt(expr.length() - 1) == '(' || expr.charAt(expr.length() - 2) == '('){
				return false;
			}
			
			//checks if expression has a closing parenthesis in the first or second index
			if (expr.charAt(0) == ')' || expr.charAt(1) == ')'){
				return false;
			}
			
			for (int k = 0; k < expr.length(); k++){
				//checks that we don't have a the following: ()
				if (expr.charAt(k) == '(' && (k + 1 < expr.length() && expr.charAt(k+1) == ')')){
					return false;
				}
				
				//checks that all parentheses are matched
				if (expr.charAt(k) == '('){
					charStack.push(expr.charAt(k));
				} else if (expr.charAt(k) == ')'){
					charStack.pop();
				}	
				
				if (k == expr.length() - 1 && !expr.isEmpty()){
					return false;
				}
				
			
				//checks conditions when you encounter variables
				if (Character.isLetter(expr.charAt(k))){
					if (k-1 >= 0){
						//the possible predecessors of a variable include: ~, (, =>
						if (expr.charAt(k-1) != '~' || expr.charAt(k-1) != '(' 
								|| (k-2 < 0 || (expr.charAt(k-1) != '>' && expr.charAt(k-2) != '='))){
							return false;
						}
					}
					
					if (k+1 < expr.length()){
						if ((k+2 >= expr.length() || (expr.charAt(k+1) != '=' && expr.charAt(k+2) != '>'))
								|| expr.charAt(k+1) != ')'){
							return false;
						}
					}
				}
				
				//checks conditions when you encounter '&'
				if (expr.charAt(k) == '&'){
					if (k-1 >= 0){
						if (Character.isLetter(expr.charAt(k-1)) == false || expr.charAt(k-1) != ')'){
							return false;
						}
					}
					
					if (k+1 < expr.length()){
						if (expr.charAt(k+1) != '(' || Character.isLetter(expr.charAt(k+1)) == false){
							return false;
						}
					}
				}
				
				//checks conditions when you encounter '|'
				if (expr.charAt(k) == '|'){
					if (k-1 >= 0){
						if (Character.isLetter(expr.charAt(k-1)) == false || expr.charAt(k-1) != ')'){
							return false;
						}
					}
					
					if (k+1 < expr.length()){
						if (expr.charAt(k+1) != '(' || Character.isLetter(expr.charAt(k+1)) == false){
							return false;
						}
					}
				}
				
				//checks condition when you encounter '~'
				if (expr.charAt(k) == '~'){
					if (k-1 >= 0){
						if (expr.charAt(k-1) != '(' || expr.charAt(k-1) != '&' || expr.charAt(k-1) != '|'){
							return false;
						}
					}
					
					if (k+1 < expr.length()){
						if (expr.charAt(k+1) != '~' || Character.isLetter(expr.charAt(k+1)) == false){
							return false;
						}
					}
				}
				
				//checks condition when you encounter '=>'
				if (k+1 < expr.length() && (expr.charAt(k) == '=' && expr.charAt(k+1) == '>')){
					if (k-1 >= 0){
						if (expr.charAt(k-1) != ')' || Character.isLetter(expr.charAt(k+1)) == false){
							return false;
						}
					}
					
					if (k+2 < expr.length()){
						if (expr.charAt(k+2) != '(' || Character.isLetter(expr.charAt(k+2)) == false){
							return false;
						}
					}
				}
			}
			return true;
		}
	}
>>>>>>> a3130b935956f222a994261bfe0718da7f7c050f
}
