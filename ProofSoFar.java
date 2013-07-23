
import java.util.ArrayList;
import java.util.LinkedList;

public class ProofSoFar{
	ArrayList<ProofLineList> mainNums;
	
	public ProofSoFar(){
		mainNums = new ArrayList<ProofLineList>();
	}
	
	public void add(String lineNum, String s){
		System.out.println(lineNum);
		if(lineNum.length() == 1){
			int line = Integer.parseInt(lineNum);
			mainNums.add(new ProofLineList(lineNum, s));
		} else{
			int line = Integer.parseInt(lineNum.substring(0,1));
			System.out.println(line);
			mainNums.get(line-1).add(lineNum, s);
		}
		
	}
	
	public String find(String lineNum){
		int mainLine = Integer.parseInt(lineNum.substring(0,1));
		return mainNums.get(mainLine-1).find(lineNum);
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
				if(curr.myLineNumer.equals(lineNum)){
					return curr.myString;
				} else
					curr=curr.next;
			}
			return null;
		}
		
	}
	
	private static class ProofNode{
		protected String myLineNumer;
		protected String myString;
		protected ProofNode next;
		
		public ProofNode(){
			myLineNumer = null;
			myString = null;
			next = null;
		}
		
		public ProofNode(String lineNum, String s){
			myLineNumer = lineNum;
			myString = s;
			next = null;
		}
		
		public ProofNode(String lineNum, String s, ProofNode n){
			myLineNumer = lineNum;
			myString = s;
			next = n;
		}
	}
	
}