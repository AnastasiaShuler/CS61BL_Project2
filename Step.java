
/**
 *  Step
 **/
public class Step {
  private LineNumber lineNum;
	private String input;
	private Expression expr;
	
	public Step(LineNumber lineNum, String input) {
		this.expr = findexpr(input);
		this.input = input;
		this.lineNum = lineNum;
	}
	
	public String toString() {
		String result = lineNum.toString() + " " + input;
		return result;
	}
	
	public Expression getExpression() {
		return this.expr;
	}
	
	public LineNumber getLineNumber() {
		return this.lineNum;
	}
	
	public String getInput() {
		return this.input;
	}
	
	//finds the expression part of the string input and returns it
	public Expression findexpr(String s) {
		String[] parts = s.split(" ");
		String firstWord = parts[0]; //get the first word
		int index = 0;
		if(firstWord == "show" || firstWord == "assume") { //the expression always follows directly after these 2 words
			index = 1;
		}
		if(firstWord == "mp" || firstWord =="mt" || firstWord =="co") { //the expression is always 3 spaces away
			index = 3;
		}
		if(firstWord == "ic" || firstWord == "repeat") {
			index = 2;
		}
		// TO DO: detect a theorem name and set index to 1
		return new Expression(parts[index]);
	}
}
