import java.util.*;

public class Proof {

	public Proof (TheoremSet theorems) {
	}

	public LineNumber nextLineNumber ( ) {
		return null;
	}

	public void extendProof (String x) throws IllegalLineException, IllegalInferenceException {
		String[] parts = x.split(" ");
		Stack charStack = new Stack();
		
		for(int i=0; i < parts.length; i++) {
			if(parts[i].charAt(0) == '(' || parts[i].charAt(0) == '~') { //new expression
				Expression myExpression = new Expression(parts[i]);
				if (parts[i].charAt(0) == '(') {
					//push an opening paren onto stack
					charStack.push(parts[i].charAt(0));
				}
				if (parts[i].charAt(0) == ')') {
					//push closing paren off stack
					charStack.pop();
				}
			}
			
			//concerns: how to check if parens are in right place? probably through the tree

			if (charStack.empty() == false) {
				//if string had same number of parens, stack should be empty at end
				//(same number of parens pushed on and popped off)
				throw new IllegalLineException("Your parentheses do not match up.");
			}
			
			//evaluating inferences
			else if(parts[0].equals("mp")) {
				// something about modus ponens
				Inference.mp();
			}
			else if(parts[0].equals("mt")) {
				// somethin about modus tollens
				Inference.mt();
			}
			else if(parts[0].equals("ic")) {
				// something about implied construction
				Inference.ic();
			}
			else if(parts[0].equals("co")) {
				// something about contradiction
				Inference.co();
			}
			
			//evaluating reasons
			else if(parts[0].equals("assume")) {
				//something about assume
			}
			
			else if(parts[0].equals("show")) {
				//something about show
			}
			
			//misc stuff
			else if(parts[0].equals("repeat")) {
				//something about repeat
			}
			
			else if(parts[0].equals("print")) {
				
			}
		}
	}

	public String toString ( ) {
		return "";
	}

	public boolean isComplete ( ) {
		return true;
	}
}
