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
	public boolean isEquals(Object obj){
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


//checkValidity - Class Details
/*
import java.util.*;

public class Expression {
	//check if theorem name, or if a reason, etc. create an arraylist, if it's any of those words like "show" etc. return true;
	protected boolean valid; 	
	private Stack <Object >charStack = new Stack <Object> ();
	private ArrayList <String> keyWords = new ArrayList <String> ();
	
	public Expression (String x){
		
		if (checkValidity(x)){
			valid = true;
		} else {
			valid = false;
		}
		//find better way to fill in this ArrayList
		keyWords.add("mp");
		keyWords.add("mt");
		keyWords.add("ic");
		keyWords.add("ct");
		keyWords.add("show");
		keyWords.add("assume");
	}
	
	public Boolean checkValidity(String expression) {
		if (expression == null || expression.length() == 0){
			//checks a null input
			System.out.println("Input is null");
			return false;
		} else if (expression.length() == 1){
			//if an expression has a length of 1, the only valid expression is a single variable
			if (Character.isLetter(expression.charAt(0))){
				return true;
			} 
			return false;
		} else if (expression.length() == 2){
			//if an expression has a length of 2, the only valid expression is ~variable
			if (expression.charAt(0) == '~' && Character.isLetter(expression.charAt(1))){
				return true;
			} 
			return false;
		} else if (keyWords.contains(expression)){
			//checks key words
			return true;
		} else{
			return checkLarge(expression);
		}
		
	}
	
	public boolean checkLarge(String expression){
		
		//checks if expression has an opening parenthesis at the second to last index or the very end 
		if (expression.charAt(expression.length() - 1) == '(' || expression.charAt(expression.length() - 2) == '('){
			return false;
		}
		
		//checks if expression has a closing parenthesis in the first or second index
		if (expression.charAt(0) == '(' || expression.charAt(1) == '('){
			return false;
		}
		
		for (int k = 0; k < expression.length(); k++){
			//checks that we don't have a the following: ()
			if (expression.charAt(k) == '(' && (k + 1 < expression.length() && expression.charAt(k+1) == ')')){
				return false;
			}
			
			//checks that all parentheses are matched
			if (expression.charAt(k) == '('){
				charStack.push(expression.charAt(k));
			} else if (expression.charAt(k) == ')'){
				charStack.pop();
			}	
			
			if (k == expression.length() - 1 && !charStack.isEmpty()){
				return false;
			}
			
		
			//checks conditions when you encounter variables
			if (Character.isLetter(expression.charAt(k))){
				if (k-1 >= 0){
					//the possible predecessors of a variable include: ~, (, =>
					if (expression.charAt(k-1) != '~' || expression.charAt(k-1) != '(' 
							|| (k-2 < 0 || (expression.charAt(k-1) != '>' && expression.charAt(k-2) != '='))){
						return false;
					}
				}
				
				if (k+1 < expression.length()){
					if ((k+2 >= expression.length() || (expression.charAt(k+1) != '=' && expression.charAt(k+2) != '>'))
							|| expression.charAt(k+1) != ')'){
						return false;
					}
				}
			}
			
			//checks conditions when you encounter '&'
			if (expression.charAt(k) == '&'){
				if (k-1 >= 0){
					if (Character.isLetter(expression.charAt(k-1)) == false || expression.charAt(k-1) != ')'){
						return false;
					}
				}
				
				if (k+1 < expression.length()){
					if (expression.charAt(k+1) != '(' || Character.isLetter(expression.charAt(k+1)) == false){
						return false;
					}
				}
			}
			
			//checks conditions when you encounter '|'
			if (expression.charAt(k) == '|'){
				if (k-1 >= 0){
					if (Character.isLetter(expression.charAt(k-1)) == false || expression.charAt(k-1) != ')'){
						return false;
					}
				}
				
				if (k+1 < expression.length()){
					if (expression.charAt(k+1) != '(' || Character.isLetter(expression.charAt(k+1)) == false){
						return false;
					}
				}
			}
			
			//checks condition when you encounter '~'
			if (expression.charAt(k) == '~'){
				if (k-1 >= 0){
					if (expression.charAt(k-1) != '(' || expression.charAt(k-1) != '&' || expression.charAt(k-1) != '|'){
						return false;
					}
				}
				
				if (k+1 < expression.length()){
					if (expression.charAt(k+1) != '~' || Character.isLetter(expression.charAt(k+1)) == false){
						return false;
					}
				}
			}
			
			//checks condition when you encounter '=>'
			if (k+1 < expression.length() && (expression.charAt(k) == '=' && expression.charAt(k+1) == '>')){
				if (k-1 >= 0){
					if (expression.charAt(k-1) != ')' || Character.isLetter(expression.charAt(k+1)) == false){
						return false;
					}
				}
				
				if (k+2 < expression.length()){
					if (expression.charAt(k+2) != '(' || Character.isLetter(expression.charAt(k+2)) == false){
						return false;
					}
				}
			}
		}
		return true;
	}
}
*/	
	


