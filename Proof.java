import java.util.*;

public class Proof {

	public Proof (TheoremSet theorems) {
	}

	public LineNumber nextLineNumber ( ) {
		return null;
	}

	public void extendProof (String x) throws IllegalLineException, IllegalInferenceException {
		String[] parts = x.split(" ");
		if(parts.length < 2) {
			throw new IllegalLineException();
		}
	}

	public String toString ( ) {
		return "";
	}

	public boolean isComplete ( ) {
		return true;
	}
}
