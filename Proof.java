import java.util.*;

/**
 *  Proof
 **/
public class Proof {
	//instance variables
	boolean beginProof;
	boolean finishProof;
	LineNumber line;
	ArrayList<Step> soFar;
	static ProofSoFar psf;	//Static because we only want one Hashtable
	boolean firstShow;
	TheoremSet myTheorems;
	

	public Proof (TheoremSet theorems) {
		//Need to figure out what to do with theorems
		beginProof = true;
		finishProof = false;
		line = new LineNumber(beginProof, finishProof);
		soFar = new ArrayList<Step>();
		psf = new ProofSoFar();
		firstShow = true;
		myTheorems = theorems;
	}

	public String nextLineNumber(){
		String lineNum = line.next();
		return lineNum;
	}
	
	public void extendProof (String x) throws IllegalLineException, IllegalInferenceException {
		System.out.println(x);
		ParenCheck pc = new ParenCheck();
		if(!(pc.checkParens(x))){
			throw new IllegalLineException("Your parenthesis don't match up");
		}
		//Another call to a syntax checker
		
		String[] parts = x.split("\\s"); //Split the input around spaces
		//evaluating inferences
		Boolean  validAssumption= true;
		if(parts[0].equals("mp")) { // something about modus ponens
			validAssumption = Inference.mp(x, psf);
		}
		else if(parts[0].equals("mt")) {
			validAssumption= Inference.mt(x, psf);
		}
		else if(parts[0].equals("ic")) {
			validAssumption= Inference.ic(x, psf);
		}
		else if(parts[0].equals("co")) {
			validAssumption = Inference.co(x, psf);
		}
		//evaluating reasons
		else if(parts[0].equals("assume")) {
			String nextLine = line.getCurrent();
			psf.add(nextLine, new Expression(parts[1]));
		}
		else if(parts[0].equals("show")) {
			if(firstShow){
				beginProof = false;
				firstShow = false;
			} else{
			beginProof = true;
			}
			line.setBeginProof(beginProof);
			String lineNum = line.getCurrent();
			psf.add(lineNum, new Expression(parts[1]));
		}
		//misc stuff
		else if(parts[0].equals("repeat")) {
			//something about repeat
			String lineNum = line.getCurrent();
			Expression expr = psf.get(parts[1]);
			psf.add(lineNum, expr);
		}
		else if(parts[0].equals("print")) {
			String lineNum = line.getCurrent();
			psf.add(lineNum, new Expression(parts[0]));
			//System.out.println(toString());
		}
		else{
			String theoremName = parts[0];
			//myTheorems.get(theoremName);
		}
		if(validAssumption){
			psf.add(line.getCurrent(), new Expression(parts[parts.length -1]));
			
		} else{
			throw new IllegalInferenceException("*** Invalid Assumption");
		}
			
			/*
			//once you've checked that the syntax and logic is right, 
			//add the step to the array of steps completed so far
			Step newStep = new Step(line.next(), x);
			soFar.add(newStep);
			*/
			
			//You've passed all of the tests. be Printed.
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
	
	/*
	public static void main(String[] args) {
		Proof test = new Proof(null);
		test.extendProof("show (p=>q)");
		test.extendProof("show (p=>q)");
		test.extendProof("show ((p=>q)=>q)");
		test.extendProof("assume ((p=>q)=>q)");
		test.extendProof("print");
	}
	*/

	public boolean isComplete ( ) {
		if(line.getCurrent().equals("1")){
			return false;
		}
		else if(line.size() == 1){
			
			//Check that the it matches the first line.
			String check = psf.get("1").myString;
			String input = psf.get(line.getCurrent()).myString;
			return (check.equals(input));
			
		} else{
			//check that it matches the first line with same
			//number of subproofs
			String currentLine = line.getCurrent();
			String checkLine = currentLine.substring(0, currentLine.length() -2);
			String input = psf.get(currentLine).myString;
			String check = psf.get(checkLine).myString;
			 if(check.equals(input)){
				 line.setFinishProof(true);
			 }
			 return false;
		}
		/*
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
		*/
	}
	
}
