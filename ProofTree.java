/**
 *  Authored By:
 * 	Anastasia Shuler
 * 	Iris Jang
 * 	Katherine Chao
 * 	Ji-hern Baek
 **/

import java.util.*;

/**
 *  ProofTree
 * 	The ProofTree class implements a binary tree to store the logical expressions of 
 * 	the proof checker.
 * 	**METHODS**
 * 		ProofTree() constructs an empty ProofTree.
 * 		print() Prints the tree in a visual format.
 * 		printInOrder() Prints the values stored in the tree in an inorder fashion.
 * 		createATree(expression) Creates a ProofTree of the given expression.
 * 		equals() Returns true if two ProofTree objects are equal.
 * 		isEqual() Returns true if two ProofTree subtrees are equal.
 * 		checkRoot() Checks if root is equivalent to argument.
 *		checkLeft() Checks to see if left subtree matches argument.
 *		checkRight() Checks to see if right subtree matches argument.
 *		checkRightST() Checks right subtrees for equality.
 *		rightIs() Returns a string of the right subtree.
 *		leftIs() Returns a string of the left subtree.
 *		isSimilar(ProofTree) Checks to see if two proofTrees are similar.
 * 
 *  TreeNode
 *  The TreeNode class implements tree nodes for the ProofTree class.
 *  **METHODS**
 *  	TreeNode(obj) Create a TreeNode with the given object.
 *  	TreeNode(obj, parent) Creates a TreeNode with the given object and given parent.
 *  	TreeNode(obj, parent, left, right) Creates a TreeNode with the given object,
 *  		 parent, and children.
 **/

public class ProofTree {
	//Class variables
	private static final String INDENT1 = "    ";
	//Instance variables
	protected TreeNode myRoot;
	
	//Constructors
	/**
	 *  ProofTree() Initiates a new, empty ProofTree.
	 **/
	public ProofTree(){
		myRoot = null;
	}
	
	//Methods
	/**
	 *  printInOrder() Prints the ProofTree using an inOrder traversal.
	 *  The result is of the form "bacd";
	 *  NOTE: does not print parenthesis
	 *  
	 *  @param x TreeNode to start the traversal from.
	 *  @return s String containing the inOrder traversal.
	 **/
	public String printInOrder(TreeNode x){
		String s = "";
		if(x != null){
			s += printInOrder(x.myLeft) + x.myItem + "" + printInOrder(x.myRight) ;
		}
		return s ;
	}
	
	
	/**
	 *  print() Prints the tree in a visual format
	 *  Uses the static helper printHelper() and println()
	 **/
	public void print(){
		if(myRoot != null){
			printHelper(myRoot, 0);
		}
	}
	
	/**
	 *  printHelper() The helper method for print().
	 *  Uses recursion to traverse the tree.
	 * 
	 *  @param root The TreeNode where recursion will start.
	 *  @indent the indent for the given root.
	 **/
	private static void printHelper(TreeNode root, int indent){
		if (root.myRight != null){
			ProofTree.printHelper(root.myRight, indent+1);
		}
		println(root.myItem, indent);
		if(root.myLeft != null){
			ProofTree.printHelper(root.myLeft, indent+1);
		}
	}
	
	/**
	 *  println() Helper method for print();
	 *  Provides the appropriate indentation for the print() method.
	 *  Prints to the screen.
	 *  
	 *  @obj Value of the tree to be printed.
	 *  @indent Indentation of the value to be printed.
	 **/
	public static void println(Object obj, int indent){
		for(int k=0; k <indent; k ++){
			System.out.print(INDENT1);
		}
		System.out.println(obj);
	}
	
	/**
	 *  createATree() Creates a ProofTree from a given string.
	 *  Unchecked Prerequisite: The given string is a valid expression 
	 *  
	 *  @param proof String expression to be turned into a tree.
	 *  @return result ProofTree of the given expression.
	 **/
	public static ProofTree createATree(String proof){
		ProofTree result = new ProofTree();
		result.myRoot = result.createATreeHelper(proof);
		return result;
	}
	
	/**
	 *  createATreeHelper() Helper method for createATree().
	 *  Recursively creates an expression tree from the given proof.
	 *  
	 *  @param proof Legal string for constructing the tree.
	 *  @return TreeNode Root of the created subtree.
	 **/
	private TreeNode createATreeHelper(String proof){
        String opnd1 = "";
        String opnd2 = "";
        String op = "";
        //if empty string, return empty node
        if(proof.equals("")){
        	return new TreeNode(proof);
        }
        //if first character is not '(', it's either a '~' or a variable
	    if (proof.charAt (0) != '('){
	    	if(proof.charAt(0) != '~') return new TreeNode(proof);
    		op = proof.substring(0, 1); //Store the ~ as the operator
    		opnd2 = proof.substring(1); //Store the remainder of the expression as right child
	    } else {
	        // proof is a parenthesized expression.
	        int nesting = 0; 	//Used to check for nested expressions
	        //Strip off beginning/ending parenthesis
	        for (int k=1; k<proof.length()-1; k++) {
	        	if(proof.charAt(k) == ')'){
	        		nesting --; //Decrements the nesting count for ')'
	        		continue;
	        	}
	        	if(proof.charAt(k) == '('){
	        		nesting ++ ; //increments the nesting count for'('
	        		continue; //jumps to top of loop
	        	}
	        	//Check for binary operators
	        	if(nesting == 0 && (proof.charAt(k) == '&' || proof.charAt(k) == '|')){
			        opnd1 = proof.substring (1, k); //creates the first operand token
			        opnd2 = proof.substring (k+1, proof.length()-1); //creates the second operand token
			        op = proof.substring (k, k+1); //creates the operator token
	        		break;
	        	}
	        	if(nesting == 0 && proof.charAt(k) == '='){
	        		op = proof.substring(k, k+2);
	        		opnd1 = proof.substring(1,k);
	        		opnd2 = proof.substring(k+2, proof.length() -1);
	        		break;
	        	}
	        }
       }
        //create the subtrees
        TreeNode left = createATreeHelper(opnd1); //create left subtree using first operand
        TreeNode right = createATreeHelper(opnd2); //create right subtree using second operand
        return new TreeNode(op, left, right); //return a subtree with operator as the root
	}
	
	/**
	 *  equals() checks two ProofTree objects for equality.
	 *  Two ProofTrees are equal if:
	 *  	The value contained by their roots are equal
	 *  	Their left subtrees are equal
	 *  	Their right subtrees are equal
	 *  
	 *  @param obj ProofTree object to check for equality.
	 *  @return boolean Result of the equality check.
	 **/
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof ProofTree)){
			return false;	//Returns false if obj is not a ProofTree
		}
		ProofTree checkTree = (ProofTree) obj;
		//Return true for two empty ProofTrees
		if(this.myRoot == null){
			return (checkTree).myRoot == null;
		} else{
			if(checkTree.myRoot == null) return false;
		}
		//Call the helper method isEqual to determine equality of two trees
		return ProofTree.isEqual(this.myRoot, checkTree.myRoot);
	}
	
	/**
	 *  isEqual() Checks two ProofTree objects for equality.
	 *  Helper method for equals() method.
	 *  Recursively determines the equality of two trees.
	 *  
	 *  @param root TreeNode of ProofTree 1
	 *  @param rootToCheck TreeNode of ProofTree 2
	 *  @return boolean Result of the equality check.
	 **/
	public static boolean isEqual(TreeNode root, TreeNode rootToCheck){
		if(rootToCheck == null) return false;
		if(!(root.myItem.equals(rootToCheck.myItem))){
			return false;
		}
		boolean left = true;
		boolean right = true;
		if(root.myLeft != null){
			if(rootToCheck.myLeft == null) return false;
			left = ProofTree.isEqual(root.myLeft, rootToCheck.myLeft);
		}
		if(root.myRight != null){
			if(rootToCheck.myRight == null) return false;
			right = ProofTree.isEqual(root.myRight, rootToCheck.myRight);
		}
		return !(left == false || right == false); 
	}
	
	/**
	 *  checkRoot() Checks the root for equality with the argument
	 *  
	 *  @param Object Object to check equality with.
	 *  @return Boolean Result of the check.
	 **/
	public boolean checkRoot(Object obj){
		if(myRoot == null){
			return false;
		}
		return myRoot.myItem.equals(obj);
	}
	
	/**
	 *  checkLeft() Checks if left subtree is equivalent to argument.
	 *  
	 *  @param t ProofTree to check left subtree against.
	 *  @return Boolean Result of the check.
	 **/
	public boolean checkLeft(ProofTree t){
		if(myRoot == null || myRoot.myLeft == null) return false;
		if(t == null || t.myRoot == null) return false;
		return ProofTree.isEqual(myRoot.myLeft, t.myRoot);
	}
	
	/**
	 *  checkRight() Checks if the right subtree is equivalent to argument.
	 *  
	 *  @param t ProofTree to check right subtree against.
	 *  @return Boolean Result of the check.
	 **/
	public boolean checkRight(ProofTree t){
		if(myRoot == null || myRoot.myRight == null){
			return false;
		}
		if(t == null || t.myRoot == null) return false;
		return ProofTree.isEqual(myRoot.myRight, t.myRoot);
	}
	
	/**
	 *  checkRightST() checks to see if two right subtrees are equal.
	 *  
	 *  @param t ProofTree to check for right s.t. equality.
	 *  @return Boolean Result of the check.
	 **/
	public boolean checkRightST(ProofTree t){
		if(myRoot == null || myRoot.myRight == null) return false;
		if(t.myRoot == null) return false;
		return ProofTree.isEqual(myRoot.myRight, t.myRoot.myRight);
	}
	
	/**
	 *  leftIs() Returns inorder traversal of the left subtree.
	 *	
	 *	@return String Inorder traversal of the left subtree.  
	 **/
	public String leftIs(){
		return printInOrder(myRoot.myLeft);
	}
	
	/**
	 *  rightIs() Returns inorder traversal of the right subtree.
	 *  
	 *  @return String Inorder traversal of the right subtree.
	 **/
	public String rightIs(){
		return printInOrder(myRoot.myRight);
	}
	
	/**
	 *  isSimlar() Checks for similarity between two trees.
	 *  Similar trees are defined as:
	 *  	all non-leaf nodes are identical
	 *  		-same value
	 *  		-same path from root
	 *  	all leaf nodes correspond between the trees
	 *  	(ie: everywhere tree 1 has node a, tree 2 has node b)
	 *  
	 *  @param t ProofTree to check for similarity
	 *  @return Boolean Result of the similarity check.
	 **/
	public boolean isSimilar(ProofTree t) throws IllegalInferenceException{
		if(myRoot == null && t.myRoot != null){
			throw new IllegalInferenceException("*** Bad theorem application");
		}
		Hashtable<String, String> ht= new Hashtable<String, String>();
		boolean result = isSimilarHelper(myRoot, t.myRoot, ht, t);
		if(!result){
			throw new IllegalInferenceException("*** Bad theorem application");
		}
		//Need to check that each value in hashtable is different
		//ie: a->b and c->b is an invalid relationship
		Set<String> keySet = ht.keySet();
		Object[] keys= keySet.toArray();
		for(int i=0; i<keys.length; i++){
			for(int j=i+1; j<keys.length; j++){
				if(ht.get(keys[i]).equals(ht.get(keys[j]))){
					//Two keys have the same value
					//Throw exception: *** Bad theorem application : a=b, c=b
					Proof.line.prev();
					throw new IllegalInferenceException("*** Bad theorem application: "  + 
							keys[i] + "=" + ht.get(keys[i]) + ", " + keys[j] + "=" + ht.get(keys[j]));
				}
			}
		}
		return true;
	}
	
	/**
	 *  isSimilarHelper() Checks for similarity between two trees;
	 *  Recursively checks for similarity between two trees.
	 *  
	 *  @param thrmRoot Root of the theorem subtree
	 *  @param inputRoot Root of the input subtree
	 *  @param relations Hash table of root correspondances
	 *  @param t ProofTree to check for similarity
	 *  @return boolean Result of the comparison.
	 **/
	public static boolean isSimilarHelper(TreeNode thrmRoot, TreeNode inputRoot, Hashtable<String, String> relations, ProofTree t)
		throws IllegalInferenceException{
		if(thrmRoot == null){
			if(inputRoot == null){
				return true;
			}
			return false;
		}
		if(thrmRoot.myLeft != null && thrmRoot.myRight != null){
			if(!(thrmRoot.myItem.equals(inputRoot.myItem))){
				return false;
			}
		} else{
			String key = (String) thrmRoot.myItem;
			String value = t.printInOrder(inputRoot);
			if(relations.get(key) == null){
				//Key is not already in the table
				relations.put(key, value);
			} else{
				String check = relations.get(key);
				if (!(check.equals(value))){
					Proof.line.prev();
					throw new IllegalInferenceException("*** Bad theorem application: " + 
							key + "=" + relations.get(key) + "," + key + "=" + value);
				}
			}
		}
		boolean right = true;
		boolean left = true;
		if(thrmRoot.myLeft != null){
			left = isSimilarHelper(thrmRoot.myLeft, inputRoot.myLeft, relations, t);
		}
		if(thrmRoot.myRight != null){
			right = isSimilarHelper(thrmRoot.myRight, inputRoot.myRight, relations, t);
		}
		if(left==false || right == false){
			return false;
		} else
		return true;
	} 
	
	//End of ProofTree methods
	
	/**
	 *  TreeNode
	 *  The TreeNode class implements tree nodes for the ProofTree class.
	 *  Instances contain:
	 *  	myItem - pointer to object stored in node
	 *  	myLeft - pointer to the left child
	 *  	myRight - pointer to the right child
	 *  	myParent - pointer to the parent node
	 **/
	private static class TreeNode{
		//Instance variables
		public Object myItem;
		private TreeNode myLeft;
		private TreeNode myRight;
		private TreeNode myParent;
		
		//Constructors
		/**
		 *  TreeNode() Initiates a tree node with only a myItem pointer.
		 *  
		 *  @param item Object to be stored in the node.
		 **/
		public TreeNode(Object item){
			myItem = item;
			myLeft = null;
			myRight = null;
			myParent = null;
		}
		
		/**
		 *  TreeNode() Initiates a tree node with pointers to myItem and myParent.
		 *  
		 *  @param item Object to be stored in the node.
		 *  @param parent The parent of the new TreeNode.
		 **/
		public TreeNode(Object item, TreeNode parent){
			myItem = item;
			myParent = parent;
			myLeft = null;
			myRight = null;
		}
		
		/**
		 *  TreeNode() Initiates a TreeNode with pointers to left and right children
		 *  
		 *  @param item Object to be stored in the node
		 *  @param left The left child of the new TreeNode.
		 *  @param right The right child of the new TreeNode.
		 **/
		public TreeNode(Object item, TreeNode left, TreeNode right){
			myItem = item;
			myParent = null;
			myLeft = left;
			myRight = right;
		}
		/**
		 *  TreeNode() initiates a tree node with pointers to myItem, myParent,
		 *    myLeft, and myRight.
		 *    
		 *    @param item Object to be stored in the node.
		 *    @param parent The parent of the new TreeNode.
		 *    @param left The left child of the new TreeNode.
		 *    @param right The right child of the new TreeNode.
		 **/
		public TreeNode(Object item, TreeNode parent, TreeNode left, TreeNode right){
			myItem = item;
			myParent = parent;
			myLeft = left;
			myRight = right;
		}
		
		//Methods
	}
}
