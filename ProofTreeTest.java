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
	Proof p = new Proof(null);
	String s  = p.line.next();

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
				
		//Test multiple ~'s
		expr = "~~a";
		t = ProofTree.createATree(expr);
		t.print();
		
		expr = "((a=>(b=>c))=>((a=>b)=>(a=>c)))";
		t = ProofTree.createATree(expr);
		t.print();
		
		expr = "((a=>(b=>c))=>((a=>b)=>(a=>c)))";
		t = ProofTree.createATree("((a=>(b=>c))=>((a=>b)=>(a=>c)))");
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
	
	/**
	 *  testCheckLeft() Test the checkLeft() method of ProofTree.
	 *  Checks if 
	 **/
	@Test
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
		
		//check what happens if null is passed in
		assertFalse(t2.checkLeft(null));
	}
	
	@Test
	/**
	 *  testCheckRight() Tests the checkRight() method of ProofTree.
	 **/
	public void testCheckRight(){
		//Test a simple case
		ProofTree t1 = ProofTree.createATree("~(a|b)");
		ProofTree t2 = ProofTree.createATree("(a|b)");
		assertTrue(t1.checkRight(t2));
		
		//Another simple case
		t1 = ProofTree.createATree("(a=>b)");
		t2 = ProofTree.createATree("b");
		assertTrue(t1.checkRight(t2));
		assertFalse(t2.checkRight(t1));
		
		//and another
		t1 = ProofTree.createATree("~a");
		t2 = ProofTree.createATree("a");
		assertTrue(t1.checkRight(t2));
		assertFalse(t2.checkRight(t1));
		
		//Test larger tree
		t1 = ProofTree.createATree("(a=>(p|q))");
		t2 = ProofTree.createATree("(p|q)");
		assertTrue(t1.checkRight(t2));
		assertFalse(t2.checkRight(t1));
		
		//another tree
		t1 = ProofTree.createATree("((a|b)=>(c&d))");
		t2 = ProofTree.createATree("(c&d)");
		assertTrue(t1.checkRight(t2));
		assertFalse(t2.checkRight(t1));
		
		//check an empty tree
		t1 = ProofTree.createATree("a");
		t2 = new ProofTree();
		assertFalse(t1.checkRight(t2));
		assertFalse(t2.checkRight(t2));
	}
	
	/**
	 *  testCheckRoot() Tests the checkRoot() method of ProofTree.
	 **/
	@Test
	public void testCheckRoot(){
		//Test a right-only tree
		ProofTree t1 = ProofTree.createATree("~q");
		assertTrue(t1.checkRoot("~"));
		
		//Test a root-only tree
		t1 = ProofTree.createATree("a");
		assertTrue(t1.checkRoot("a"));
		
		//Test a simple tree
		t1 = ProofTree.createATree("(a=>b)");
		assertTrue(t1.checkRoot("=>"));
		
		//Test an empty tree
		t1 = new ProofTree();
		assertFalse(t1.checkRoot("=>"));
		
		//Tests that should fail
		t1 = ProofTree.createATree("(a=>b)");
		assertFalse(t1.checkRoot("a"));
		assertFalse(t1.checkRoot("b"));
		assertFalse(t1.checkRoot(""));
		assertFalse(t1.checkRoot(null));
		assertFalse(t1.checkRoot("asdf"));
	}
	
	/**
	 *  testCheckRightST() Tests the checkRightST() method of the ProofTree class
	 **/
	@Test
	public void testCheckRightST(){
		//Test a simple tree
		ProofTree t1 = ProofTree.createATree("~q");
		ProofTree t2 = ProofTree.createATree("(a=>q)");
		assertTrue(t1.checkRightST(t2));
		assertTrue(t2.checkRightST(t1));
		
		//Test a more complex tree
		t1 = ProofTree.createATree("((a|b)=>(c&d))");
		t2 = ProofTree.createATree("((a=>b)=>(c&d))");
		assertTrue(t1.checkRightST(t2));
		assertTrue(t2.checkRightST(t1));
		
		//Test some empty trees
		t1 = new ProofTree();
		assertFalse(t1.checkRightST(t2));
		assertFalse(t2.checkRightST(t1));
		
		//Test another tree
		t1 = ProofTree.createATree("(a=>(p|q))");
		t2 = ProofTree.createATree("((a=>d)=>(p|q))");
		assertTrue(t1.checkRightST(t2));
		assertTrue(t2.checkRightST(t1));
			
		//Another tree
		t1 = ProofTree.createATree("(~a=>(~b=>~(b|b)))");
		t2 = ProofTree.createATree("~(~b=>~(b|b))");
		assertTrue(t1.checkRightST(t2));
		assertTrue(t2.checkRightST(t1));
		
		//Some that should fail
		t2 = ProofTree.createATree("a");
		assertFalse(t2.checkRightST(t1));
		assertFalse(t1.checkRightST(t2));
		
		t1 = ProofTree.createATree("~q");
		assertFalse(t2.checkRightST(t1));
		assertFalse(t1.checkRightST(t2));
		
	}
	
	@Test
	/**
	 *  testIsSubtree() Tests the isSubtree() method of ProofTree.
	 **/
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
		
		t1 = new ProofTree();
		try{
			assertFalse(t1.isSimilar(t2));
		} catch (IllegalInferenceException exc){
			assertTrue(true);
		}
		
		
	}
}
