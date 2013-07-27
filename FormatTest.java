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

	public void testCheckExpressions () {
		Format check = new Format();

		//checks null input
		//Expression a = new Expression(null);
		//assertFalse(check.expressionValidity(a));

		//checks expression with a length of one
		String b = new String("p");
		try {
			check.expressionValidity(b);
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		//Strings with a length of one can only be variables
		String c = new String("8");
		try {
			check.expressionValidity(c);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		//checks String with a length of 2
		String d = new String ("~p");
		try {
			check.expressionValidity(d);
		//Strings with a length of 2 can only be in the format: '~variable'
		}
		catch (IllegalLineException e) {
			System.out.println(e.getMessage());
			fail();
		}
		
		String z = new String ("89");
		try {
			check.expressionValidity(z);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String f = new String ("~8");
		try {
			check.expressionValidity(f);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String g = new String("~~");
		try {
			check.expressionValidity(g);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String h = new String("8t");
		try {
			check.expressionValidity(h);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String i = new String ("t~");
		try {
			check.expressionValidity(i);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
	
		String j = new String ("(p=>q)");
		try {
			check.expressionValidity(j);
		}
		catch (IllegalLineException e) {
			System.out.println(e.getMessage());
			fail();
		}
		
        String y = "~(p=>q)";
        try {
        	check.expressionValidity(y);
        }
		catch (IllegalLineException e) {
			System.out.println(e.getMessage());
			fail();
			assert true;
		}
       
		String k = new String ("(p=>q=>p)");
		//can't have more than one implies per parens
		try {
			check.expressionValidity(k);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String l = new String ("(p=>)q");
		//We match up parens, but never check that there IS a closing paren
		//in the right spot
		//Only things that may follow a paren: => or )
		try {
			check.expressionValidity(l);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String m = new String("(p=>(q))");
		try {
			check.expressionValidity(m);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
	
 		String n = new String("(p(=>q))");
 		try {
 			check.expressionValidity(n);
 			fail();
 		}
		catch (IllegalLineException e) {
			assert true;
		}
 		
		String o = new String("(p= >q)");
		try {
			check.expressionValidity(o);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String p = "((~p&~q)=>~(p|q))";
		try {
			check.expressionValidity(p);
		}
		catch (IllegalLineException e) {
			System.out.println(e.getMessage());
			fail();
		}
		
		String q = "(a=>(b=>(a&b)))";
		try {
			check.expressionValidity(q);
		}
		catch (IllegalLineException e) {
			System.out.println(e.getMessage());
			fail();
		}
		
		String r = "((a=>b)=>((b=>c)=>(a=>c)))";
		try {
			check.expressionValidity(r);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String s = "mp(p=>q)";
		try {
			check.expressionValidity(s);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String t = "(((((p)))))";
		try {
			check.expressionValidity(t);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String u = "ab";
		try {
			check.expressionValidity(u);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
	}
}
