
import java.util.ArrayList;
import java.util.LinkedList;

public class ProofSoFar{
	ArrayList mainNums;
	
	public ProofSoFar(){
		mainNums = new ArrayList();
	}
	
	private static class ProofLineList{
		private ProofNode myHead;
		
		public ProofLineList(){
			myHead = null;
		}
		
		public ProofLineList(String lineNum, String s){
			myHead = new ProofNode(lineNum, s);
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

/*
public class ProofSoFar{
	public ProofNode myRoot;
	
	public ProofSoFar(){
		myRoot = null;
	}
	
	public void addNewLine(int lineNum){
		ProofNode curr = myRoot;
		while(lineNum > 1){
			curr = curr.myNextLine;
			lineNum --;
		}
		curr.myNextLine = new ProofNode();
	}
	
	public void addNewSubline(String lineNum){
		int count = 0;
		ProofNode curr = myRoot;
		while(2*count <= lineNum.length() - 2){
			String num = lineNum.substring(2*count, 2*count + 1);
			Integer line = Integer.parseInt(num);
			while(line > 1){
				curr = curr.myNextLine;
				line --;
			}
			curr = curr.mySublines;
		}
		if(lineNum.substring(2*count).equals("1")){
			curr.mySublines = new ProofNode();
		} else{
			Integer line = Integer.parseInt(lineNum.substring(2*count));
			while(line > 1){
				curr = curr.myNextLine;
			}
			curr.myNextLine = new ProofNode();
		}
		
	}
	
	public void add(String s, String lineNum){
		int count = 0;
		ProofNode curr = myRoot;
		if(lineNum.equals("1")){
			myRoot = new ProofNode(s);
			return;
		}
		while(2*count <= lineNum.length() - 2){
			String num = lineNum.substring(2*count, 2*count + 1);
			Integer line = Integer.parseInt(num);
			System.out.println(line);
			while(line > 1){
				curr = curr.myNextLine;
				line --;
			}
			count ++;
		}
		int last = Integer.parseInt(lineNum.substring(lineNum.length() -1));
		if(last == 1){
			curr.mySublines = new ProofNode(s);
		} else{
			curr = curr.mySublines;
			while(last > 2){
				curr = curr.mySublines;
				last --;
			}
			ProofNode toAdd = new ProofNode(s);
			curr.mySublines = toAdd;
		}
	}
	
	public String get(String lineNum){
		int count = 0;
		ProofNode curr = myRoot;
		while(2*count <= lineNum.length() - 2){
			String num = lineNum.substring(2*count, 2*count + 1);
			Integer line = Integer.parseInt(num);
			System.out.println(line);
			while(line > 1){
				curr = curr.myNextLine;
				line --;
			}
			count ++;
		}
		int last = Integer.parseInt(lineNum.substring(lineNum.length() -1));
		curr = curr.mySublines;
		while(last > 2){
			curr = curr.mySublines;
			last --;
		}
		return curr.myString;
	}
		
	
	private static class ProofNode{
		protected ProofNode myNextLine;
		protected ProofNode mySublines;
		public String myString;
		public ProofTree myProofTree;
		
		public ProofNode(){
			myNextLine = null;
			mySublines = null;
			myString = null;
			myProofTree = null;
		}
		
		public ProofNode(String expr){
			myNextLine = null;
			mySublines = null;
			myString = expr;
			myProofTree = ProofTree.createATree(expr);
		}
		
	}
}
*/