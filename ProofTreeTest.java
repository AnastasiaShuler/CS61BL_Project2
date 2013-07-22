import static org.junit.Assert.*;

import org.junit.Test;

public class ProofTreeTest {

	@Test
	/**
	 *  testInit() Tests the constructor for the ProofTree class.
	 **/
	public void testInit() {
		ProofTree tree = new ProofTree();
		String s = tree.printInOrder(tree.myRoot);
		assertEquals("", s);
		
		tree = new ProofTree(1);
		s = tree.printInOrder(tree.myRoot);
		assertEquals("1 ", s);
	}
	
	/**
	 *  testAdd() Tests the add method of the ProofTree class.
	 **/
	@Test
	public void testAdd(){
		ProofTree tree = new ProofTree(1);
		tree.add(2,1, "left");
		tree.add(3,1, "right");
		String s = tree.printInOrder(tree.myRoot);
		assertEquals("2 1 3 ", s);
		tree.add(4,2,"left");
		tree.add(5,2,"right");
		tree.add(6,3,"left");
		tree.add(7,3,"right");
		s = tree.printInOrder(tree.myRoot);
		assertEquals("4 2 5 1 6 3 7 ", s);
	}
	
	/**
	 *  testPrintInOrder() Tests the printInOrder() method of the ProofTree class.
	 **/
	@Test
	public void testPrintInOrder(){
		ProofTree tree = new ProofTree('a');
		//Test a one-node tree
		String s = tree.printInOrder(tree.myRoot);
		assertEquals("a ", s);
		tree.add('b', 'a', "left");
		tree.add('c', 'a', "right");
		//Test balanced 3-node tree
		s = tree.printInOrder(tree.myRoot);
		assertEquals("b a c ", s);
		tree.add('d', 'b', "left");
		tree.add('e', 'b', "right");
		tree.add('f', 'c', "left");
		tree.add('g', 'c', "right");
		//Test a balanced tree of depth=3
		s = tree.printInOrder(tree.myRoot);
		assertEquals("d b e a f c g ", s);
		tree.add('h', 'd', "left");
		//Test unbalanced tree;
		s = tree.printInOrder(tree.myRoot);
		assertEquals("h d b e a f c g ", s);
		tree.add('i', 'g', "right");
		//Test unbalanced tree;
		s = tree.printInOrder(tree.myRoot);
		assertEquals("h d b e a f c g i ", s);
		tree.add('j', 'f', "left");
		//Test random single child in middle
		s = tree.printInOrder(tree.myRoot);
		assertEquals("h d b e a j f c g i ", s);
		tree = new ProofTree();
		//Tests an emptyp tree;
		s = tree.printInOrder(tree.myRoot);
		assertEquals("", s);
	}
	
	/**
	 *  testCreateATree() Tests the createATree() method of the ProofTree class.
	 **/
	@Test
	public void testCreateATree(){
		String expr = "(a=>a)";
		ProofTree t = ProofTree.createATree(expr);
		System.out.println("Tree for (a=>a)");
		t.print();
		System.out.println();
		System.out.println();
		expr =  "(p=>(~p=>q))";
		t = ProofTree.createATree(expr);
		System.out.println("Tree for (p=>(~p=>1))");
		t.print();
		System.out.println();
		System.out.println(t.printInOrder(t.myRoot));
		System.out.println();
		System.out.println();
		expr = "(((p&q)=>a)=>(b|c))";
		t = ProofTree.createATree(expr);
		System.out.println("Tree for (((p&q)=>a)=>(b|c))");
		t.print();
		System.out.println();
		System.out.println();
		System.out.println();
		expr = "((a=>q)=>((b=>q)=>((a|b)=>q)))";
		t = ProofTree.createATree(expr);
		System.out.println("Tree for ((a=>q)=>((b=>q)=>((a|b)=>q)))");
		t.print();
				
	}
	
	/**
	 *  testContains() Tests the contains() method of the ProofTree class.
	 **/
	@Test
	public void testContaions(){
		ProofTree t = new ProofTree('A');
		t.add('B', 'A', "left");
		t.add('C', 'A', "right");
		t.add('D', 'B', "left");
		t.add('E', 'B', "right");
		t.add('F', 'C', "left");
		t.add('G', 'C', "right");
		String s = t.printInOrder(t.myRoot);
		assertEquals("D B E A F C G ", s);
		assertTrue(t.contains('G'));	//check for deepest right child
		assertTrue(t.contains('A'));	//check for root
		assertTrue(t.contains('F'));	//check for left child
		assertTrue(t.contains('C'));	//check for right child
		assertTrue(t.contains('D'));	//check for deepest left child
		assertFalse(t.contains('Z'));	//check for a value not in the tree
		
		String expr = "((a|b)=>((~c&d)|b))";
		t = ProofTree.createATree(expr);
		assertTrue(t.contains("a"));	//check for different values 
		assertTrue(t.contains("c"));
		assertTrue(t.contains("~"));	//check for an operator
		assertTrue(t.contains("b"));
		assertFalse(t.contains("f"));	//check for a value not in the tree
	}
	
}
