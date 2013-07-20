import java.util.*;

/**
 *  ProofTree
 * 	The ProofTree class implements a binary tree to store the logical expressions of 
 * 	the proof checker.
 * 	**METHODS**
 * 		ProofTree() constructs an empty ProofTree.
 * 		ProofTree(obj) Creates a ProofTree with the given object as the root node.
 * 		add(obj, parentItem, childSide) Adds a child node to desired parent on the desired side.
 * 		*addExpression() Adds an expression to the ProofTree.
 * 		print() Prints the tree in a visual format.
 * 		printInOrder() Prints the values stored in the tree in an inorder fashion.
 * 		height(node) Returns the height of a given node.
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
 *  	hasNext() Returns true if there more elements to be traversed.
 *  	next() Returns the next element in the iteration.
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
	 *  addExpression() Adds an expression to the ProofTree.
	 *  
	 *  @param operand1 The first operand of the expression.
	 *  @param operand2 The second operand of the expression.
	 *  @param operator The operator for the expression.
	 *  @param parent The parent node for the expression.
	 **/
	public void addExpression(Object operand1, Object operand2, Object operator, TreeNode parent){
		TreeNode child1 = new TreeNode(operand1);
		TreeNode child2 = new TreeNode(operand2);
		TreeNode toAdd = new TreeNode(operator, parent, child1, child2);
		child1.myParent = (toAdd);
		child2.myParent = (toAdd);
	}
	
	/**
	 *  printInOrder() Prints the ProofTree using an inOrder traversal.
	 *  The result is of the form "b a c d ";
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
	 *  ProofTreeIterator implements Iterator<TreeNode>
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
