import java.util.*;


public class Expression {
	//check if theorem name, or if a reason, etc. create an arraylist, if it's any of those words like "show" etc. return true;
	boolean valid; 	
	Stack <Object >charStack = new Stack <Object> ();
	ArrayList <String> keyWords = new ArrayList <String> ();
	
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
		if (expression.charAt (0) != '('){
			//there are two cases if the expression, doesn't begin with a parenthesis: variable or ~variable
			if (expression.length() == 1){
				if (Character.isLetter(expression.charAt(0))){
					//checks variable
					return true;
				}
			} else if (expression.length() == 2){
				if (expression.charAt(0) == '~' && Character.isLetter(expression.charAt(1))){
					//checks ~variable
					return true;
				} 
			} else {
				return false;
			}
			
		} else if(keyWords.contains(expression)){ 
			return true;
			
		} else {
			//checks if opening and closing parentheses are right next to each.
			for (int k = 0; k < expression.length(); k++){
				if (k <= expression.length()-1 && k+1 <= expression.length()-1){
					if (expression.charAt(k) == '(' && expression.charAt(k+1) == ')'){
						return false;
					}
				}
			}
			
			//checks parentheses are all matched (confirm with group if we want this check here or in proof)
			for (int l = 0; l < expression.length(); l++){
				if (expression.charAt(l) == '('){
					charStack.push(expression.charAt(l));
				} else if (expression.charAt(l) == ')'){
					charStack.pop();
				}	
			}
			
			if (!charStack.isEmpty()){
				return false;
			}
			
			//check the implies
			for (int m = 0; m < expression.length(); m++){
				if (expression.charAt(m) == '='){
					if ((m+1 <= expression.length()-1) && expression.charAt(m+1) != '>'){
						return false;
					}
				}
			}
		}
		return true;
	}
}
