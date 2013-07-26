import java.util.*;

/**
 *  Proof
 **/
public class Proof {
	//instance variables
	boolean beginProof, finishProof, firstShow;
	static LineNumber line;
	ArrayList<Step> soFar;
=======
	LineNumber line;
	ArrayList<String> soFar;
>>>>>>> a5ce51d009cfbf6e2a05995674770adbeb5643ba
	Hashtable<String, Expression> exprs;
	Hashtable<String, String> inputs;
	TheoremSet myTheorems;
	

	public Proof (TheoremSet theorems) {
		//Need to figure out what to do with theorems
		beginProof = true;
		finishProof = false;
		line = new LineNumber(beginProof, finishProof);
		soFar = new ArrayList<String>();
		inputs = new Hashtable<String, String>();
		exprs = new Hashtable<String, Expression>();
		firstShow = true;
		myTheorems = theorems;
	}

	public String nextLineNumber(){
		String lineNum = line.next();
		return lineNum;
	}
	
	public void extendProof (String x) throws IllegalLineException, IllegalInferenceException {
		//This will replace all groups of spaces with a single space
		x = x.trim().replaceAll(" +", " ");
		inputs.put(line.getCurrent(), x);
		
		//add the line to the arraylist
		if(soFar.size() == 0 || line.getCurrent() != soFar.get(soFar.size() - 1)) {
			soFar.add(line.getCurrent());
		}
		
		ParenCheck pc = new ParenCheck();
		if(!(pc.checkParens(x))){
			throw new IllegalLineException("Your parenthesis don't match up");
		}
		//Another call to a syntax checker
		
		String[] parts = x.split("\\s"); //Split the input around spaces
		
		//evaluating inferences
		Boolean  validInference = true;
		//mp inference
		if(parts[0].equals("mp")){
			validInference = Inference.mp(x, exprs);
		}
		//mt inference
		else if(parts[0].equals("mt")) {
			validInference= Inference.mt(x, exprs);
		}
		//ic inference
		else if(parts[0].equals("ic")) {
			validInference= Inference.ic(x, exprs);
		}
		else if(parts[0].equals("co")) {
			validInference = Inference.co(x, exprs);
		}
		//assume statement
		else if(parts[0].equals("assume")) {
			String prevLine = line.getPrevious();	//get previous line number
			String previousLine = inputs.get(prevLine);	//get previous line input
			Inference.assume(x, previousLine,exprs);
			exprs.put(line.getCurrent(), new Expression(parts[1]));
		}
		//show statement 
		else if(parts[0].equals("show")) {
			if(firstShow){
				beginProof = firstShow = false;
			} else{
				beginProof = true;
			}
			line.setBeginProof(beginProof);
			String lineNum = line.getCurrent();
			exprs.put(lineNum, new Expression(parts[1]));
		}
		//repeat statement
		else if(parts[0].equals("repeat")) {
			String lineNum = line.getCurrent();		//find the current line number
			Expression expr = exprs.get(parts[1]); 	//get the Expression to be repeated
			exprs.put(lineNum, expr);				//add to the exprs table
		}
		//print statement
		else if(parts[0].equals("print")) {
			String lineNum = line.getCurrent();
			exprs.put(lineNum, new Expression(parts[0]));
			line.prev();
			//System.out.println(toString());
		}
		//Must be a theorem or invalid if nothing else got it
		else{
			String theoremName = parts[0];
			String inputExpr = parts[1];
			myTheorems.theoremChecker(theoremName, inputExpr);
			
		}
		if(validInference){
			exprs.put(line.getCurrent(), new Expression(parts[parts.length -1]));
			
		} else{
			line.prev();
			throw new IllegalInferenceException("*** Bad inference");
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
		String result = "";
		Iterator<String> iter = soFar.iterator();
		while(iter.hasNext()) {
			String current = iter.next();
			result = result + current + " " + inputs.get(current) + "\n";
		}
		return result;
		/*
		String result = "print\n";
		Iterator<Step> iter = soFar.iterator();
		while(iter.hasNext()) {
			result = result + iter.next().getLineNumber().toString() + " " + iter.next().getInput() + "\n";
		}
		return result;*/
	}
	
	/*
	//gets the step from a given line number
	public Step getStep(LineNumber line) {
		for(int i = 0; i < soFar.size(); i++) {
			if(soFar.get(i).getLineNumber().equals(line)) {
				return soFar.get(i);
			}
		}
		//TO DO: might want to throw an error here if the line number they want doesn't exist, instead of returning null
		return null;

	}*/
	

	/*
	public static void main(String[] args) {
		Proof test = new Proof(null);
		test.extendProof("show (((p=>q)=>q)=>((q=>p)=>p))");
		test.extendProof("print");
	}*/

	public boolean isComplete ( ) {
		if(line.getCurrent().equals("1")){
			return false;
		}
		else if(line.size() == 1){
			
			//Check that the it matches the first line.
			String check = exprs.get("1").myString;
			String input = exprs.get(line.getCurrent()).myString;
			return (check.equals(input));
			
		} else{
			//check that it matches the first line with same
			//number of subproofs
			String currentLine = line.getCurrent();
			String checkLine = currentLine.substring(0, currentLine.length() -2);
			String input = exprs.get(currentLine).myString;
			String check = exprs.get(checkLine).myString;
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
