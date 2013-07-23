import java.util.ArrayList;

public class LineNumber {
	private ArrayList<Integer> numbers;
	private boolean beginProof;
	private boolean finishProof;
	
	public LineNumber(boolean beginProof, boolean finishProof) {
		this.beginProof = beginProof;
		this.finishProof = finishProof;
		numbers = new ArrayList<Integer>();
	}
	
	public void setbeginProof(boolean newBoolean) {
		this.beginProof = newBoolean;
	}
	
	public void setfinishProoof(boolean newBoolean) {
		this.finishProof = newBoolean;
	}
	
	public LineNumber next() {
		if(beginProof) {
			numbers.add(1);
		}
		else if(finishProof) {
			numbers.remove(numbers.size()-1);
		}
		else {
			Integer newNum = numbers.get(numbers.size()-1) + 1;
			numbers.set(numbers.size()-1, newNum);
		}
		return this;
	}
	
	public String toString() {
		String result = "";
		for(Integer num : numbers) {
			result = result + num + ".";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}
}
