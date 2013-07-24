import java.util.ArrayList;
import java.util.Hashtable;

/**
 *  ProofSoFar
 *  Data structure to hold the proof as it accumulates as a result of user input.
 *  Its a Hashtable
 *  **METHODS**
 *  	add(lineNum, expr) Adds new node containing line number and string s
 *  	get(lineNum) Returns the string associated to lineNum.
 **/
public class ProofSoFar{
	Hashtable<String,Expression> exprs;
	
	public ProofSoFar(){
		exprs = new Hashtable<String, Expression>();
	}
	
	public void add(String line, Expression expr){
		exprs.put(line, expr);
	}
	
	public Expression get(String line){
		return exprs.get(line);
	}
	/*
	ArrayList<ProofLineList> mainNums;
	
	public ProofSoFar(){
		mainNums = new ArrayList<ProofLineList>();
	}
	
	public void add(String lineNum, String s){
		if(lineNum.length() == 1){
			int line = Integer.parseInt(lineNum);
			mainNums.add(new ProofLineList(lineNum, s));
		} else{
			int line = Integer.parseInt(lineNum.substring(0,1));
			mainNums.get(line-1).add(lineNum, s);
		}
		
	}
	
	public String find(String lineNum){
		int mainLine = Integer.parseInt(lineNum.substring(0,1));
		return mainNums.get(mainLine-1).find(lineNum);
	}
	
	public String toString(){
		int mainNum = 0;
		String result = "";
		while(mainNum < mainNums.size()){
			result += mainNums.get(mainNum).toString();
			mainNum ++;
		}
		return result;
	}
	
	public static class ProofLineList{
		private ProofNode myHead;
		private ProofNode myTail;
		
		public ProofLineList(){
			myHead = null;
			myTail = null;
		}
		
		public ProofLineList(String lineNum, String s){
			myHead = new ProofNode(lineNum, s);
			myTail = last();
		}
		
		public ProofNode getmyHead() {
			return myHead;
		}
		
		public ProofNode getmyTail() {
			return myTail;
		}
		
		public void add(String lineNum, String s){
			if(myTail != null){
				myTail.next = new ProofNode(lineNum, s);
				myTail = myTail.next;
			} else{
				myHead = new ProofNode(lineNum, s);
				myTail = myHead;
			}
			
		}
		
		private ProofNode last() {
			ProofNode pointer = myHead;
			while(pointer.next != null) {
				pointer = pointer.next;
			}
			return pointer;
		}
		
		public String find(String lineNum){
			ProofNode curr = myHead;
			while(curr != null){
				if(curr.myLineNumber.equals(lineNum)){
					return curr.myString;
				} else
					curr=curr.next;
			}
			return null;
		}
		
		public String toString(){
			String result = "";
			ProofNode curr = myHead;
			while(curr != null){
				System.out.println("Current result is " + result);
				result += curr.myLineNumber + "    " + curr.myString + "\n";
				curr = curr.next;
			}
			return result;
		}
		
	}
	
	private static class ProofNode{
		protected String myLineNumber;
		protected String myString;
		protected Expression myExpression;
		protected ProofNode next;
		
		public ProofNode(){
			myLineNumber = null;
			myString = null;
			next = null;
			myExpression = null;
		}
		
		public ProofNode(String lineNum, String s){
			myLineNumber = lineNum;
			myString = s;
			next = null;
			myExpression = findexpr(s);
		}
		
		public ProofNode(String lineNum, String s, ProofNode n){
			myLineNumber = lineNum;
			myString = s;
			next = n;
			myExpression = findexpr(s);
		}
		
		public ProofNode getNext() {
			return next;
		}
		
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
		
		public Expression getExpression() {
			return myExpression;
		}
	}
	*/
	
}
