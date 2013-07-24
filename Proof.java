import java.util.*;

public class Proof {
	boolean beginProof;
	boolean finishProof;
	LineNumber line;
	ArrayList<Step> soFar;

	public Proof (TheoremSet theorems) {
		beginProof = true;
		finishProof = false;
		line = new LineNumber(beginProof, finishProof);
		soFar = new ArrayList<Step>();
	}

	public LineNumber nextLineNumber ( ) {
		return line.next();
	}

	public void extendProof (String x) { // throws IllegalLineException, IllegalInferenceException { ***COMMENTED OUT TO TEST***
		String[] parts = x.split(" ");
		Stack charStack = new Stack();
		
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
			//throw IllegalLineException("Your parentheses do not match up.");
		}
			
		/* **********COMMMENTED OUT TO TEST *************
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
			*/
			
			//evaluating reasons
			else if(parts[0].equals("assume")) {
				//something about assume
			}
			
			else if(parts[0].equals("show")) {
				beginProof = true;
				line.setbeginProof(beginProof);
				//something about show
			}
			
			//misc stuff
			else if(parts[0].equals("repeat")) {
				//something about repeat
			}
			
			else if(parts[0].equals("print")) {
				System.out.println(toString());
			}
			
			//once you've checked that the syntax and logic is right, 
			//add the step to the array of steps completed so far
			Step newStep = new Step(line.next(), x);
			soFar.add(newStep);
		}
	}


	public String toString ( ) {
		String result = "print\n";
		Iterator<Step> iter = soFar.iterator();
		while(iter.hasNext()) {
			result = result + iter.next().getLineNumber().toString() + " " + iter.next().getInput() + "\n";
		}
		return result;
	}
	
	//gets the step from a given line number
	public Step getStep(LineNumber line) {
		for(int i = 0; i < soFar.size(); i++) {
			if(soFar.get(i).getLineNumber().equals(line)) {
				return soFar.get(i);
			}
		}
		//TO DO: might want to throw an error here if the line number they want doesn't exist, instead of returning null
		return null;

	}
	
	public static void main(String[] args) {
		Proof test = new Proof(null);
		test.extendProof("show (p=>q)");
		test.extendProof("show (p=>q)");
		test.extendProof("show ((p=>q)=>q)");
		test.extendProof("assume ((p=>q)=>q)");
		test.extendProof("print");
	}

	public boolean isComplete ( ) {
		if(soFar.size() == 1 && soFar.get(0) == null && soFar.get(1) == null) { //only one line of proof has been done
			return false;
		}
		else {
			Expression startExpression = soFar.get(0).getExpression();
			Expression endExpression = soFar.get(soFar.size()-1).getExpression();
			if(startExpression.equals(endExpression)) {
				return true;
			}
			else return false;
		}
	}
}
