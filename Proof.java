import java.util.*;

public class Proof {
	boolean beginProof;
	boolean finishProof;
	LineNumber line;
	ProofSoFar soFar;

	public Proof (TheoremSet theorems) {
		beginProof = true;
		finishProof = false;
		line = new LineNumber(beginProof, finishProof);
		soFar = new ProofSoFar();
	}

	public LineNumber nextLineNumber ( ) {
		return line.next();
	}

	public void extendProof (String x) throws IllegalLineException, IllegalInferenceException {
		String[] parts = x.split(" ");
		Stack charStack = new Stack();
		
		for (int i=0; i < parts.length; i++) {
			if(parts[i].charAt(0) == '(' || parts[i].charAt(0) == '~') { //new expression
				Expression myExpression = new Expression(parts[i]);	
			for (int c = 0; c < x.length(); c++) {
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
			//throw IllegalLineException("Your parentheses do not match up.");
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
			soFar.add(line.toString(), x);
		}
	}

	public String toString ( ) {
		String result = "";
		for(String lineInProof : soFar) {
			result = result + lineInProof + "\n";
		}
		return result;
	}

	public boolean isComplete ( ) {
		if(soFar.mainNums.size() == 1 && soFar.mainNums.get(0).getmyHead().getNext() == null) { //only one line of proof has been done
			return false;
		}
		else {
			Expression startExpression = soFar.mainNums.get(0).getmyHead().getExpression();
			Expression endExpression = soFar.mainNums.get(soFar.mainNums.size()-1).last().getExpression();
			if(startExpression.equals(endExpression)) {
				return true;
			}
			else return false;
		}
	}
}
