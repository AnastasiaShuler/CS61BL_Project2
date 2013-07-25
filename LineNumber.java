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
	
	/**
	 *  prev() Decrements the line number;
	 **/
	public String prev(){
		Integer newNum = numbers.get(numbers.size()-1) - 1;
		numbers.set(numbers.size()-1, newNum);
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
}
