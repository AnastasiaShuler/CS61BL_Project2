import java.util.*;

/**
 *  Proof
 **/
public class Proof {
	//instance variables
	boolean beginProof, finishProof, firstShow;
	static LineNumber line;
	ArrayList<String> soFar;
	static Hashtable<String, Expression> exprs;
	Hashtable<String, String> inputs;
	TheoremSet myTheorems;
	

	/**
	 *  Proof() Initiates a Proof object.
	 *  
	 *  @param theorems The TheoremSet object that contains the user's theorems.
	 **/
	public Proof (TheoremSet theorems) {
		beginProof = true;
		finishProof = false;
		line = new LineNumber(beginProof, finishProof);
		soFar = new ArrayList<String>();
		inputs = new Hashtable<String, String>();
		exprs = new Hashtable<String, Expression>();
		firstShow = true;
		myTheorems = theorems;
	}

	/**
	 *  nextLineNumber() Returns the next line number in the iteration.
	 *  Makes a call the next() method of the LineNumber class.
	 *  
	 *  @return lineNum String representation of the next line number.
	 **/
	public String nextLineNumber(){
		String lineNum = line.next();
		return lineNum;
	}
	
	/**
	 *  extendProof() Processes user input to check for legality.
	 *  Throws IllegalLineException, IllegalInferenceException.
	 *  Makes extensive calls to other classes' methods, namely the Interface class.
	 *  
	 *  @param x String of user input
	 **/
	public void extendProof (String x) throws IllegalLineException, IllegalInferenceException {
		Format check = new Format();
		try {
			check.checkFormat(x);
			//This will replace all groups of spaces with a single space
			x = x.trim().replaceAll(" +", " ");
			System.out.println(x);
			String[] parts = x.split("\\s"); //Split the input around spaces
			
			//Add the input to Hashtables inputs and exprs
			inputs.put(line.getCurrent(), x);
			exprs.put(line.getCurrent(), new Expression(parts[parts.length -1]));
			
			//add the line to the arraylist; used for the 'print' command
			if(soFar.size() == 0 || line.getCurrent() != soFar.get(soFar.size() - 1)) {
				soFar.add(line.getCurrent());
			}
			
			//evaluating inferences
			Boolean  validInference = true;
			//mp inference
			if(parts[0].equals("mp")){
				validInference = Inference.mp(x);
			}
			//mt inference
			else if(parts[0].equals("mt")) {
				validInference= Inference.mt(x);
			}
			//ic inference
			else if(parts[0].equals("ic")) {
				validInference= Inference.ic(x);
			}
			else if(parts[0].equals("co")) {
				validInference = Inference.co(x);
			}
			//assume statement
			else if(parts[0].equals("assume")) {
				String prevLine = line.getPrevious();	//get previous line number
				String previousLine = inputs.get(prevLine);	//get previous line input
				Inference.assume(x, previousLine);
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
				System.out.println(toString());
			}
			//Must be a theorem or invalid if nothing else got it
			else{
				String theoremName = parts[0];
				String inputExpr = parts[1];
				myTheorems.theoremChecker(theoremName, inputExpr);
			}
		}
		catch(IllegalLineException e) {
			line.decrement();
			throw new IllegalLineException(e.getMessage());
		}
	}

	public String toString ( ) {
		String result = "";
		Iterator<String> iter = soFar.iterator();
		while(iter.hasNext()) {
			String current = iter.next();
			result = result + current + " " + inputs.get(current) + "\n";
		}
		return result;
	}

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
	}
	
}
