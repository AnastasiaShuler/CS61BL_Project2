import java.util.ArrayList;

/**
 *  ProofSoFar
 *  Data structure to hold the proof as it accumulates as a result of user input.
 *  Structured as an ArrayList of specialty LinkedLists
 *  **METHODS**
 *  	add(lineNum, s) Adds new node containing line number and string s
 *  	find(lineNum) Returns the string associated to lineNum.
 *  	toString() Returns the enumeration of all elements in the structure.
 *  	
 **/
public class ProofSoFar{
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
	
	private static class ProofLineList{
		private ProofNode myHead;
		private ProofNode myTail;
		
		public ProofLineList(){
			myHead = null;
			myTail = null;
		}
		
		public ProofLineList(String lineNum, String s){
			myHead = new ProofNode(lineNum, s);
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
		protected ProofNode next;
		
		public ProofNode(){
			myLineNumber = null;
			myString = null;
			next = null;
		}
		
		public ProofNode(String lineNum, String s){
			myLineNumber = lineNum;
			myString = s;
			next = null;
		}
		
		public ProofNode(String lineNum, String s, ProofNode n){
			myLineNumber = lineNum;
			myString = s;
			next = n;
		}
	}
	
}