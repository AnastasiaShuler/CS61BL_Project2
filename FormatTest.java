import junit.framework.TestCase;

public class FormatTest extends TestCase {
	
	public void testSingleExprs() {
		Format a = new Format();
		String [] ex1 = new String[] {"(p=>q)"};
		assertTrue(a.checkFormat(ex1));
	}
	
	public void testKeywords () {
		Format a = new Format();
		String [] ex1 = new String [] {"mp", "2.1.3", "2.3.3", "(p=>q)"};
		assertTrue(a.checkFormat(ex1));
		String [] ex2 = new String [] {"co", "1", "5.1", "~p"};
		assertTrue(a.checkFormat(ex2));
		String [] ex3 = new String [] {"ic", "3.4", "7.9", "(a=>(a=>b))"};
		assertTrue(a.checkFormat(ex3));
		String [] ex4 = new String [] {"ic3.4", "7.9", "(a=>(a=>b))"};
		assertFalse(a.checkFormat(ex4));
		String [] ex5 = new String [] {"antsy", "3.4", "7.9", "(a=>(a=>b))"};
		assertFalse(a.checkFormat(ex5));
	}
	
	public void testShowAssume() {
		Format b = new Format();
		String [] ex1 = new String [] {"show", "~q"};
		assertTrue(b.checkFormat(ex1));
		String [] ex2 = new String [] {"assume", "(p=>q)"};
		assertTrue(b.checkFormat(ex2));
		String [] ex3 = new String [] {"Beavis", "(p=>q)"};
		assertFalse(b.checkFormat(ex3));
		String [] ex4 = new String [] {"Beavis(p=>q)"};
		//technically not true, but since only one part, this case is checked by Expression.
		assertTrue(b.checkFormat(ex4));
	}
	
	public void testTheoremName() {
		Format c = new Format();
		String [] ex1 = new String [] {"patsy", "(~p=>q)"};
		assertTrue(c.checkFormat(ex1));
	}
<<<<<<< HEAD
=======
	
	public void testCheckExpressions(){
		Format check = new Format();
		//checks empty input
		Expression a = new Expression("");
		assertFalse(check.expressionValidity(a));
		//checks null input
		try{
			Expression nullTry = new Expression(null);
			assertFalse(check.expressionValidity(nullTry));
			//aware that the assertFalse is not necessary 
			//because the error emerges when null is input into Expression object
		} catch (NullPointerException e){
			System.out.println("Expression cannot be null.");
		}
		//checks expression with a length of one
		Expression b = new Expression("p");
		assertTrue(check.expressionValidity(b));
		//expressions with a length of one can only be variables
		Expression c = new Expression("8");
		assertFalse(check.expressionValidity(c));
		//checks expression with a length of 2
		Expression d = new Expression ("~p");
		assertTrue(check.expressionValidity(d));
		//expressions with a length of 2 can only be in the format: '~variable'
		Expression e = new Expression ("89");
		assertFalse(check.expressionValidity(e));
		Expression f = new Expression ("~8");
		assertFalse(check.expressionValidity(f));
		Expression g = new Expression("~~");
		assertFalse(check.expressionValidity(g));
		Expression h = new Expression("8t");
		assertFalse(check.expressionValidity(h));
		Expression i = new Expression ("t~");
		assertFalse(check.expressionValidity(i));
		
		//checking larger expressions
		Expression one = new Expression ("(p=>q)");
		assertTrue(check.expressionValidity(one));
		Expression two = new Expression("(q=>q)");
		assertTrue(check.expressionValidity(two));
		Expression three = new Expression("(~(p=>q)");
		assertTrue(check.expressionValidity(three));
		//Expression two = new Expression ("");
		//assertTrue(check.expressionValidity(two));
	}
	
>>>>>>> a3130b935956f222a994261bfe0718da7f7c050f
}
