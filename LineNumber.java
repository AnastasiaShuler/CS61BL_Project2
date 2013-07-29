import java.util.ArrayList;

/**
 *  LineNumber
 *  Produces the line numbers of the proof
 *  Each line number forms its own ArrayList
 **/
public class LineNumber {
	//Instance variables
	private ArrayList<Integer> numbers;
	private boolean beginProof;
	private boolean finishProof;
	private boolean justStarting;
	
	/**
	 *  LineNumer Initiates a new LineNumber object.
	 *  Creates an ArrayList to store all of the line numbers.
	 *  
	 *  @param beginProof
	 *  @param finishProof
	 **/
	public LineNumber(boolean beginProof, boolean finishProof) {
		this.beginProof = beginProof;
		this.finishProof = finishProof;
		numbers = new ArrayList<Integer>();
		justStarting = true;
	}
	
	/**
	 *  setBeginProof() Sets the beginProof field;
	 *  
	 *  @param newBoolean What to set beginProof to
	 **/
	public void setBeginProof(boolean newBoolean) {
		this.beginProof = newBoolean;
	}
	
	/**
	 *  setFinishProof() Sets the finishProof field.
	 *  
	 *  @param newBoolean What to set finishProof to.
	 **/
	public void setFinishProof(boolean newBoolean) {
		this.finishProof = newBoolean;
	}
	
	/**
	 *  next() Creates the next line number.
	 *  
	 *  @return LineNumber the LineNumber object
	 **/
	public String next() {
		if(justStarting){
			numbers.add(1);
			justStarting = false;
			beginProof = false;
		}
		else if(beginProof){
			numbers.add(1);
			beginProof = false;
		}
		else if(finishProof) {
			numbers.remove(numbers.size()-1);
			numbers.set(numbers.size()-1, numbers.get(numbers.size() -1) + 1);
			finishProof = false;
		}
		else {
			Integer newNum = numbers.get(numbers.size()-1) + 1;
			numbers.set(numbers.size()-1, newNum);
		}
		return this.toString();
	}
	
	/**
	 *  getCurrent() Returns the current lineNumber without modifying the LineNumber object
	 *  
	 *  @return String Next line number
	 **/
	public String getCurrent(){
		return this.toString();
	}
	
	public String getPrevious(){
		String result = "";
		if(numbers.size() != 1){
			result = "" + numbers.get(0);
			for(int i=1; i<numbers.size() - 1; i++){
				result = result + "." + numbers.get(i);
			}
			int last = numbers.get(numbers.size()-1);
			if(last != 1){
				result += "." + (last-1);
			}
		} else{
			result += ((numbers.get(numbers.size()-1))-1);
		}
		return result;
	}
	/**
	 *  size() returns the size of the line object
	 *  
	 *  @return int Size of LineNumber
	 **/
	public int size(){
		return numbers.size();
	}
	
	public int getLastNum() {
		int lastIndex = numbers.size()-1;
		return numbers.get(lastIndex);
	}
	
	/**
	 *  prev() Decrements the line number;
	 **/
	public String prev(){
		if(this.getLastNum() != 1) {
			Integer newNum = numbers.get(numbers.size()-1) - 1;
			numbers.set(numbers.size()-1, newNum);
		}
		return this.toString();
	}
	
	/**
	 *  toString() Returns the string representation of a LineNumber
	 *  
	 *  @return result String representation of the LineNumber
	 **/
	public String toString() {
		String result = "" + numbers.get(0);
		for(int i=1; i<numbers.size(); i++){
			result = result + "." + numbers.get(i);
		}
		return result;
	}
	
	/**
	 *  isValidReference() Checks if a given line number can be referenced.
	 *  Ensures lines are only referenced inside their own subproof.
	 *  Criteria for valid line numbers:
	 *  	-cannot access line number of greater depth or yourself
	 *  	-if depth matches, all digits up to the last digit must match
	 *  		-last digit must always be less than corresponding digit in
	 *  			currLine
	 *  	-if depth of refLine is less than that of currLine, all digits
	 *  		must match until the last digit;
	 *  		-last digit must always be less than corresponding digit in
	 *  			currLine
	 *  
	 *  @param refLine Line being referenced.
	 *  @param currLine Current line number of the proof.
	 *  @return boolean Result of the validitity check.
	 **/
	public static boolean isValidReference(String refLine, String currLine) {
		//Cannot access line from deeper subproof or yourself
		if(refLine.length() > currLine.length() || refLine.equals(currLine)){
			return false;
		}
		for(int i=0; i<refLine.length()-2; i+=2){
			//check that it matches up until the last number
			if(refLine.charAt(i) != currLine.charAt(i)){
				return false;
			}
		}
		//Check that the last digit of the refLine is less than
		//or equal to matching currLine number.
		try{
			int k = refLine.length()-1;
			Integer x = Integer.parseInt(refLine.substring(k));
			Integer y = Integer.parseInt(currLine.substring(k, k+1));
			if(x >y) return false;
		} catch(NumberFormatException exc){
			//If one of the characters was not an integer
			return false;
		}
		return true;
	}
}
