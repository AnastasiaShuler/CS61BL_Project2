import junit.framework.TestCase;

public class FormatTest extends TestCase {

	public void testSingleExprs() {
		Format a = new Format();
		String ex1 = "(p=>q)";
		assertTrue(a.checkFormat(ex1));
	}

	public void testKeywords () {
		Format a = new Format();
		String ex1 = "mp 2.1.3 2.3.3 (p=>q)";
		assertTrue(a.checkFormat(ex1));
		String ex2 = "co 1 5.1 ~p";
		assertTrue(a.checkFormat(ex2));
		String ex3 = "ic 3.4 7.9 (a=>(a=>b))";
		assertFalse(a.checkFormat(ex3));
		String ex4 = "ic3.4 7.9 (a=>(a=>b))";
		assertFalse(a.checkFormat(ex4));
		String ex5 = "antsy 3.4 7.9 (a=>(a=>b))";
		assertFalse(a.checkFormat(ex5));
	}

	public void testShowAssume() {
		Format b = new Format();
		String ex1 = "show ~q" ;
		assertTrue(b.checkFormat(ex1));
		String ex2 = "assume (p=>q)";
		assertTrue(b.checkFormat(ex2));
	}

	public void testTheoremName() {
		Format c = new Format();
		String ex1 = "patsy (~p=>q)";
		assertTrue(c.checkFormat(ex1));
		String ex2 = "Beavis (p=>q)";
		assertTrue(c.checkFormat(ex2));
		String ex3 = "Beavis(p=>q)";
		//technically not true, but since only one part, this case is checked by Expression.
		assertTrue(c.checkFormat(ex3));
	}

	public void testCheckExpressions(){
		Format check = new Format();

		//checks null input
		//Expression a = new Expression(null);
		//assertFalse(check.expressionValidity(a));

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
		Expression j = new Expression ("(p=>q)");
		assertTrue(check.expressionValidity(j));
		Expression k = new Expression ("(p=>q=>p)");
		//cant have more than one implies per parens
		assertTrue(check.expressionValidity(k));
		Expression l = new Expression ("(p=>)q");
		//We match up parens, but never check that there IS a closing paren
		//in the right spot
		//Only things that may follow a paren: => or )
		assertFalse(check.expressionValidity(l));
		Expression m = new Expression("(p=>(q))");
		assertFalse(check.expressionValidity(m));
		Expression n = new Expression("(p(=>q))");
		assertFalse(check.expressionValidity(n));
		Expression o = new Expression("(p= >q)");
		assertFalse(check.expressionValidity(o));
	}

}
