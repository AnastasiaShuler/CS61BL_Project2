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
 * 		ProofTree(obj) Creates a ProofTree with the given object as the root node.
 * 		add(obj, parentItem, childSide) Adds a child node to desired parent on the desired side.
 * 		contains(obj) Searches the tree for a given value.
 * 		print() Prints the tree in a visual format.
 * 		printInOrder() Prints the values stored in the tree in an inorder fashion.
 * 		height(node) Returns the height of a given node.
 * 		createATree(expression) Creates a ProofTree of the given expression.
 * 
 *  TreeNode
 *  The TreeNode class implements tree nodes for the ProofTree class.
 *  **METHODS**
 *  	TreeNode(obj) Create a TreeNode with the given object.
 *  	TreeNode(obj, parent) Creates a TreeNode with the given object and given parent.
 *  	TreeNode(obj, parent, left, right) Creates a TreeNode with the given object, parent, and children.
 * 
 *  ProofTreeIterator
 *  The ProofTreeIterator class implements the Iterator interface;
 *  Allows for iteration through the proof tree using a preorder traversal;
 *  **METHODS**
 *  	ProofTreeIterator() Initializes an iterator.
 *  	hasNext() Returns true if there more TreeNodes to be traversed.
 *  	next() Returns the next TreeNode in the iteration.
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
	
	/**
	 *  ProofTree() Initializes a new ProofTree with one node.
	 *  
	 *  @param obj Object to be added to the tree.
	 **/
	public ProofTree(Object obj){
		myRoot = new TreeNode(obj);
	}
	
	//Methods
	/**
	 *  add() Adds a single node to the ProofTree.
	 *  Behavior for a non-existing parent node is currently undefined
	 *  
	 *  @param obj Object to be added to the ProofTree.
	 *  @param parentItem Parent of the node to be added.
	 *  @param childSide String (either "left" or "right").
	 **/
	public void add(Object obj, Object parentItem, String childSide){
		Iterator<TreeNode> iter = ProofTreeIterator(myRoot);
		while(iter.hasNext()){
			TreeNode curr = iter.next();
			if(curr.myItem.equals(parentItem)){
				TreeNode toAdd = new TreeNode(obj, curr);
				if(childSide.equals("left")){
					curr.myLeft = (toAdd);
				} else if (childSide.equals("right")){
					curr.myRight = (toAdd);
				}

			}
		}
		//Element has not been found in the tree; throw an exception??
	}
	
	/**
	 *  contains() Searches the tree for a given Object;
	 *  Returns true if the given value is found in the ProofTree.
	 *  
	 *  @param obj Object to search for in the tree.
	 *  @return found Boolean; true if given value is found in tree.
	 **/
	public boolean contains(Object obj){
		//Set up tree iterator
		Iterator<TreeNode> iter = this.preOrderIterator();
		//iterate and check each node for correct value
		while(iter.hasNext()){
			if(iter.next().myItem.equals(obj)) return true;
		}
		return false;
	}
	
	/**
	 *  preOrderIterator() Creates a new preorder iterator.
	 * 	
	 * 	@param root TreeNode where to Iterator will start.
	 *  @return A preOrderIterator for the given ProofTree. 
	 **/
	public Iterator<TreeNode> preOrderIterator(){
		return new PreOrderTreeIterator(myRoot);
	}
	
	/**
	 *  printInOrder() Prints the ProofTree using an inOrder traversal.
	 *  The result is of the form "b a c d ";
	 *  NOTE: does not print parenthesis
	 *  
	 *  @param x TreeNode to start the traversal from.
	 *  @return s String containing the inOrder traversal.
	 **/
	public String printInOrder(TreeNode x){
		String s = "";
		if(x != null){
			s += printInOrder(x.myLeft) + x.myItem + " " + printInOrder(x.myRight) ;
		}
		return s ;
	}
	
	public Iterator<TreeNode> ProofTreeIterator(TreeNode x){
		return new PreOrderTreeIterator(x);
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
	 *  height() Returns the height of a given TreeNode in tree.
	 *  
	 *  @param x TreeNode to find the height of.
	 *  @return Height of the given TreeNode.
	 **/
	public int height(TreeNode x){
		if(x == null){
			return 0;
		}
		else{
			int leftHeight = height(x.myLeft) + 1;
			int rightHeight = height(x.myRight) + 1;
			return Math.max(leftHeight, rightHeight);
		}
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
	        		k++; //Increments k to the next character
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

	
	/**
	 *  PreOrderTreeIterator implements Iterator<TreeNode>
	 *  Creates an iterator for a ProofTree. Implements a PreOrder traversal.
	 **/
	public class PreOrderTreeIterator implements Iterator<TreeNode>{
		//Instance variables
		private Stack<TreeNode> nodes;
		
		/**
		 *  ProofTreeIterator() Initializes a new ProofTree iterator.
		 *  
		 *  @param x TreeNode to start the iteration from.
		 **/
		public PreOrderTreeIterator(TreeNode x){
			nodes = new Stack<TreeNode>();
			nodes.push(x);
		}
		
		/**
		 *  hasNext() returns true if there are more TreeNodes to be returned
		 *  
		 *  @return Boolean corresponding to hasNext() state.
		 **/
		public boolean hasNext(){
			return (!(nodes.isEmpty()));
		}
		
		/**
		 *  next() returns the next TreeNode in the tree.
		 *  
		 *  @return toReturn The next TreeNode in the iteration.
		 **/
		public TreeNode next(){
			TreeNode toReturn = nodes.pop();
			if(toReturn.myRight != null){
			nodes.push(toReturn.myRight);
			}
			if(toReturn.myLeft != null){
				nodes.push(toReturn.myLeft);
			}
			return toReturn;
		}
		
		/**
		 *  remove() Removes the current element from the tree.
		 *  Will not implement (at least for now).
		 **/
		public void remove(){
			//May or may not implement.
		}
	}
}
