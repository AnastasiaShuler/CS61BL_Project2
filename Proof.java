
import java.util.*;

public class Proof {
	boolean beginProof;
	boolean finishProof;
	LineNumber line;
	LinkedList<String> proofSoFar;

	public Proof (TheoremSet theorems) {
		beginProof = true;
		finishProof = false;
		LineNumber line = new LineNumber(beginProof, finishProof);
		LinkedList<String> proofSoFar = new LinkedList<String>();
	}

	public LineNumber nextLineNumber ( ) {
		return line.next();
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
				beginProof = true;
				//something about show
			}
			
			//misc stuff
			else if(parts[0].equals("repeat")) {
				//something about repeat
			}
			
			else if(parts[0].equals("print")) {
				
			}
			
			//once you've checked that the syntax and logic is right
			String formattedLine = line.toString() + x;
			proofSoFar.push(formattedLine);
		}
	}

	public String toString ( ) {
		String result = "";
		for(String lineInProof : proofSoFar) {
			result = result + lineInProof + "\n";
		}
		return result;
	}

	public boolean isComplete ( ) {
		return true;
	}
}
