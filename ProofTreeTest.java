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

}
