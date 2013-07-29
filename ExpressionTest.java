import junit.framework.TestCase;

public class ExpressionTest extends TestCase {

	/**
	 *  testExpressionInit() Tests the constructor of the Expression class.
	 *  Prints the myTree field to the screen for a visual check.
	 *  Checks that the myString field matches the input.
	 **/
	public void testExpressionInit(){
		//Check a basic expression
		String expr = "(p=>p)";
		Expression E = new Expression(expr);
		E.myTree.print();
		assertEquals(expr, E.myString);
		
		//Check an empty expression
		expr = "";
		E = new Expression(expr);
		E.myTree.print();
		assertEquals(expr, E.myString);
		
		//Check a very complicated expression
		expr= "((a=>(b=>c))=>((a=>b)=>(a=>c)))";
		E = new Expression(expr);
		E.myTree.print();
		assertEquals(expr, E.myString);
		
		//Check a ~ expression
		expr = "~a";
		E = new Expression(expr);
		E.myTree.print();
		assertEquals(expr, E.myString);
		
		//check multiple ~'s
		expr = "~~~~p";
		E = new Expression(expr);
		E.myTree.print();
		assertEquals(expr, E.myString);
	}
	
	/**
	 *  testEquals() Tests the equals() method of the Expression class.
	 *  Tests that the equality test returns the correct boolean.
	 **/
	public void testEquals(){
		//check a basic equality
		String expr = "(p=>p)";
		Expression E1 = new Expression(expr);
		Expression E2 = new Expression(expr);
		assertTrue(E1.equals(E2));
		assertTrue(E2.equals(E1));
		
		//check against an empty Expression
		E1 = new Expression("");
		assertFalse(E1.equals(E2));
		assertFalse(E2.equals(E1));
		
		//check two empty Expressions
		E2 = new Expression("");
		assertTrue(E1.equals(E2));
		assertTrue(E2.equals(E1));
		
		//check two non empty Expressions that fail
		E1 = new Expression("(p=>p)");
		E2 = new Expression("(q=>q)");
		assertFalse(E1.equals(E2));
		assertFalse(E2.equals(E1));
		
		//check some with ~
		E1 = new Expression("~a");
		E2 = new Expression("~a");
		assertTrue(E1.equals(E2));
		assertTrue(E2.equals(E1));
		
		//check some really weird ones
		E1 = new Expression("((a=>(b=>c))=>((a=>b)=>(a=>c)))");
		E2 = new Expression("(p=>((p=>q)=>q))");
		assertFalse(E1.equals(E2));
		assertFalse(E2.equals(E1));
	}
}