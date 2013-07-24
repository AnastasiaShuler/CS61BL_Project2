import java.util.Stack;


public class ParenCheck {
	//instance variables
	String[] parts;
	Stack<Character> charStack;
	
	public ParenCheck(){
		charStack = new Stack<Character>();
	}
	
	public boolean checkParens(String x){
		parts = x.split(" ");
		for (int i=0; i < parts.length; i++) {
			if(parts[i].charAt(0) == '(' || parts[i].charAt(0) == '~') { //new expression
				Expression myExpression = new Expression(parts[i]);	
				for (int c = 0; c < parts[i].length(); c++) {
						if (parts[i].charAt(c) == '(') {
							//push an opening paren onto stack
							charStack.push(parts[i].charAt(0));
						}
						if (parts[i].charAt(c) == ')') {
							//push closing paren off stack
							charStack.pop();
						}
				}
			}
			if (!charStack.isEmpty()) {
				//if string had same number of parens, stack should be empty at end
				//(same number of parens pushed on and popped off)
				return false;
			}
		}
		return true;
	}

}
