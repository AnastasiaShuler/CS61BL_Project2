/**
 *  Created By:
 *  	Anastasia Shuler
 *  	Iris Jang
 *  	Ji-hern Baek
 *  	Katherine Chao
 **/

import static org.junit.Assert.*;

import org.junit.Test;

public class ProofTreeTest {

	@Test
	/**
	 *  testInit() Tests the constructor for the ProofTree class.
	 **/
	public void testInit() {
		//Test the 0 argument constructor
		ProofTree tree = new ProofTree();
		String s = tree.printInOrder(tree.myRoot);
		assertEquals("", s);
	}
	
	/**
	 *  testCreateATree() Tests the createATree() method of the ProofTree class.
	 **/
	@Test
	public void testCreateATree(){
		//Test simplest expression
		String expr = "(a=>a)";
		ProofTree t = ProofTree.createATree(expr);
		System.out.println("Tree for (a=>a)");
		t.print();
		System.out.println();
		System.out.println();
		
		//Test more complex expression: has ~ operator
		expr =  "(p=>(~p=>q))";
		t = ProofTree.createATree(expr);
		System.out.println("Tree for (p=>(~p=>1))");
		t.print();
		System.out.println();
		System.out.println(t.printInOrder(t.myRoot));
		System.out.println();
		System.out.println();
		
		//test more complex expression: has & and |
		expr = "(((p&q)=>a)=>(b|c))";
		t = ProofTree.createATree(expr);
		System.out.println("Tree for (((p&q)=>a)=>(b|c))");
		t.print();
		System.out.println();
		System.out.println();
		System.out.println();
		
		//Test most complex expression: has ~, &, and |
		expr = "((~a=>q)=>((b=>q)=>((a|b)=>q)))";
		t = ProofTree.createATree(expr);
		System.out.println("Tree for ((~a=>q)=>((b=>q)=>((a|b)=>q)))");
		t.print();
		
		//Test a single character expression
		expr = "a";
		t = ProofTree.createATree(expr);
		t.print();
				
	}
	
	/**
	 *  testPrintInOrder() Tests the printInOrder() method of the ProofTree class.
	 **/
	@Test
	public void testPrintInOrder(){
		//Test the most simple tree
		ProofTree t = ProofTree.createATree("a");
		String s = t.printInOrder(t.myRoot);
		assertEquals(s, "a");
		
		//test an empty tree
		t = ProofTree.createATree("");
		s = t.printInOrder(t.myRoot);
		assertEquals(s, "");
		
		//test a simple 3 node tree
		t = ProofTree.createATree("(a=>a)");
		s = t.printInOrder(t.myRoot);
		assertEquals(s, "a=>a");
		
		//Test a tree with more left children
		t = ProofTree.createATree("((a|b)=>q)");
		s = t.printInOrder(t.myRoot);
		assertEquals(s, "a|b=>q");
		
		//Test a tree with more right children
		t = ProofTree.createATree("(a=>(a|b))");
		s = t.printInOrder(t.myRoot);
		assertEquals(s, "a=>a|b");
		
		//Test a balanced deeper tree
		t = ProofTree.createATree("((a|b)=>(c&d))");
		s = t.printInOrder(t.myRoot);
		assertEquals(s, "a|b=>c&d");
		
		//Test a deeper tree
		t = ProofTree.createATree("((~a=>q)=>((b=>q)=>((a|b)=>q)))");
		s = t.printInOrder(t.myRoot);
		assertEquals(s, "~a=>q=>b=>q=>a|b=>q");
		
		//Test a right only tree
		t = ProofTree.createATree("~a");
		s = t.printInOrder(t.myRoot);
		assertEquals(s, "~a");
	}
	
	/**
	 *  testEquals() Tests the equals method of ProofTree class.
	 **/
	@Test
	public void testEquals(){
		//Test basic trees
		String expr = "(a=>b)";
		ProofTree t1 = ProofTree.createATree(expr);
		ProofTree t2 = ProofTree.createATree(expr);
		assertEquals(t1, t2);
		assertEquals(t2, t1);
		
		//Test more complicated trees
		expr = "((a|b)=>((~c&d)|b))";
		t1 = ProofTree.createATree(expr);
		t2 = ProofTree.createATree(expr);
		assertEquals(t1, t2);
		assertEquals(t2, t1);
		
		//Test empty trees;
		t1 = new ProofTree();
		t2 = new ProofTree();
		assertEquals(t1, t2);
		
		//Test an empty tree against a nonempty tree
		t2 = ProofTree.createATree(expr);
		assertFalse(t1.equals(t2));
		assertFalse(t2.equals(t1));
		
		//Test two non-equal trees
		expr = "(a=>b)";
		t1 = ProofTree.createATree(expr);
		assertFalse(t1.equals(t2));
		assertFalse(t2.equals(t1));
	}
	
	@Test
	/**
	 *  testCheckLeft() Test the checkLeft() method of ProofTree.
	 *  Checks if 
	 **/
	public void testCheckLeft(){
		//Test most simple case
		ProofTree t1 = ProofTree.createATree("(a=>b)");
		ProofTree t2 = ProofTree.createATree("a");
		assertTrue(t1.checkLeft(t2));
		assertFalse(t2.checkLeft(t1));
		
		//Test a more complicated tree
		t1 = ProofTree.createATree("(~a=>(~b=>~(b|b)))");
		t2 = ProofTree.createATree("~a");
		assertTrue(t1.checkLeft(t2));
		assertFalse(t2.checkLeft(t1));
		
		//Test another tree
		t1 = ProofTree.createATree("((a|b)=>b)");
		t2 = ProofTree.createATree("(a|b)");
		assertTrue(t1.checkLeft(t2));
		assertFalse(t2.checkLeft(t1));
		
		//Test a tree with no left subtree
		t1 = ProofTree.createATree("~a");
		assertFalse(t1.checkLeft(t2));
		assertFalse(t2.checkLeft(t1));
		
		//check an empty tree;
		t1 = new ProofTree();
		assertFalse(t1.checkLeft(t2));
		assertFalse(t2.checkLeft(t1));
	}
	
	@Test
	public void testCheckRight(){
		ProofTree t1 = ProofTree.createATree("~(a|b)");
		t1.print();
		ProofTree t2 = ProofTree.createATree("(a|b)");
		t2.print();
		assertTrue(t1.checkRight(t2));
	}
	@Test
	public void testCheckRoot(){
		ProofTree t1 = ProofTree.createATree("~q");
		t1.print();
		assertTrue(t1.checkRoot("~"));
		
	}
	
	@Test
	public void testCheckRightST(){
		ProofTree t1 = ProofTree.createATree("~q");
		ProofTree t2 = ProofTree.createATree("(a=>q)");
		assertTrue(t1.checkRightST(t2));
	}
	
	@Test
	public void testIsSubtree(){
		
		ProofTree t1 = new ProofTree();
		ProofTree t2 = new ProofTree();
		try{
			assertTrue(t1.isSimilar(t2));
		} catch (IllegalInferenceException exc){
			fail();
		}
		
		t1 = ProofTree.createATree("(q=>q)");
		t2 = ProofTree.createATree("(q=>q)");
		try{
			assertTrue(t1.isSimilar(t2));
		} catch (IllegalInferenceException exc){
			fail();
		}	
		
		t2 = ProofTree.createATree("((a|b)=>(a|b))");
		try{
			assertTrue(t1.isSimilar(t2));
		} catch(IllegalInferenceException exc){
			fail();
		}
		
		t2 = ProofTree.createATree("((q=>q)=>(q=>q))");
		t2.print();
		try{
			assertTrue(t1.isSimilar(t2));
		} catch(IllegalInferenceException exc){
			fail();
		}
		
		t2 = ProofTree.createATree("(a=>(q=>q))");
		try{
			assertFalse(t1.isSimilar(t2));
			assertFalse(true);
		} catch(IllegalInferenceException exc){
			System.out.println(exc.getMessage());
			assertTrue(true);
		}
		
		t1 = ProofTree.createATree("(~p=>(~q=>~(p|q)))");
		t2 = ProofTree.createATree("(~a=>(~b=>~(a|b)))");
		try{
			assertTrue(t1.isSimilar(t2));
		} catch(IllegalInferenceException exc){
			fail();
		}
		
		t1 = ProofTree.createATree("(b=>q)");
		t2 = ProofTree.createATree("(z=>z)");
		try{
			assertFalse(t1.isSimilar(t2));
			assertFalse(true);
		} catch(IllegalInferenceException exc){
			System.out.println(exc.getMessage());
			assertTrue(true);
		}
		
		
	}
}
