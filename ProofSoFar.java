

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
			curr.mySublines = new ProofNode(s);
		} else{
			Integer line = Integer.parseInt(lineNum.substring(2*count));
			while(line > 1){
				curr = curr.myNextLine;
			}
			curr.myNextLine = new ProofNode(s);
		}
		
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