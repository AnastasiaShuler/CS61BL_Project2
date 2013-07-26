import java.util.*;

/**
 *  Proof
 **/
public class Proof {
	//instance variables
	boolean beginProof, finishProof, firstShow;
	LineNumber line;
	ArrayList<String> soFar;
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
		Boolean  validAssumption= true;
		if(parts[0].equals("mp")) { // something about modus ponens
			validAssumption = Inference.mp(x, exprs);
		}
		else if(parts[0].equals("mt")) {
			validAssumption= Inference.mt(x, exprs);
		}
		else if(parts[0].equals("ic")) {
			validAssumption= Inference.ic(x, exprs);
		}
		else if(parts[0].equals("co")) {
			validAssumption = Inference.co(x, exprs);
		}
		//evaluating reasons
		else if(parts[0].equals("assume")) {
			String prevLine = line.getPrevious();	//get previous line number
			String previousLine = inputs.get(prevLine);	//get previous line input
			try{
				Inference.assume(x, previousLine,exprs);
			} catch(IllegalInferenceException exc){
				line.prev();	//Need to decrement the line number;
				//Now throw the exception again
				throw new IllegalInferenceException(exc.getMessage());
			}
			exprs.put(line.getCurrent(), new Expression(parts[1]));
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
			exprs.put(lineNum, new Expression(parts[1]));
		}
		//misc stuff
		else if(parts[0].equals("repeat")) {
			//something about repeat
			String lineNum = line.getCurrent();
			Expression expr = exprs.get(parts[1]);
			exprs.put(lineNum, expr);
		}
		else if(parts[0].equals("print")) {
			String lineNum = line.getCurrent();
			exprs.put(lineNum, new Expression(parts[0]));
			System.out.println(toString());
		}
		else{
			String theoremName = parts[0];
			//myTheorems.get(theoremName);
		}
		if(validAssumption){
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
