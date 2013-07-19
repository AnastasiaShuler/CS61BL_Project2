import java.util.*;

public class Proof {

	public Proof (TheoremSet theorems) {
	}

	public LineNumber nextLineNumber ( ) {
		return null;
	}

	public void extendProof (String x) throws IllegalLineException, IllegalInferenceException {
		String[] parts = x.split(" ");
		for(int i=0; i < parts.length; i++) {
			if(parts[i].charAt(0) == '(' || parts[i].charAt(0) == '~') { //new expression
				Expression myExpression = new Expression(parts[i]);
			}
			
			//evaluating inferences
			else if(parts[0].equals("mp")) {
				// something about modus ponens
				return do_mp();
			}
			else if(parts[0].equals("mt")) {
				// somethin about modus tollens
				return do_mt();
			}
			else if(parts[0].equals("ic")) {
				// something about implied construction
				return do_ic();
			}
			else if(parts[0.equals("co")) {
				// something about contradiction
				return do_co();
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
