import junit.framework.TestCase;

public class FormatTest extends TestCase {

	public void testKeywords () {
		Format a = new Format();
		//all these should pass (and not hit the assertTrue(false) statement)
		try { a.checkFormat("mp 2.1.3 2.3.3 (p=>q)"); }
		catch(IllegalLineException e) { fail(); }
		
		try { a.checkFormat("co 2.1.3 2.3.3 (p=>q)"); }
		catch(IllegalLineException e) { fail(); }
		
		try { a.checkFormat("ic 3.4 (a=>(a=>b))"); }
		catch(IllegalLineException e) { fail(); }
		
		try { a.checkFormat("show (p=>q)"); }
		catch(IllegalLineException e) { fail(); }
		
		try { a.checkFormat("assume (p=>q)"); }
		catch(IllegalLineException e) { fail(); }
		
		try { a.checkFormat("print"); }
		catch(IllegalLineException e) { fail(); }
		
		//all these should fail
		try { a.checkFormat("mp 2.1.3 2.3.3"); fail(); }
		catch(IllegalLineException e) { assertTrue(true); }
		
		try { a.checkFormat("mt 2.1.3"); fail(); }
		catch(IllegalLineException e) { assertTrue(true); }
		
		try { a.checkFormat("asfd"); fail(); }
		catch(IllegalLineException e) { assertTrue(true); }
		
		try { a.checkFormat("ic3.4 7.9 (a=>(a=>b))"); fail(); }
		catch(IllegalLineException e) { assertTrue(true); }
		
		try { a.checkFormat("antsy 3.4 7.9 (a=>(a=>b))"); fail(); }
		catch(IllegalLineException e) { assertTrue(true); }
		
	}

	public void testShowAssume() {
		Format a = new Format();
		
		//valid
		try { a.checkFormat("show ~q"); }
		catch(IllegalLineException e) { assertTrue(false); }
		
		try { a.checkFormat("assume (p=>q)"); }
		catch(IllegalLineException e) { assertTrue(false); }
	}
	
	public void testCompleteInput() {
		Format a = new Format();
		
		//Right number of args and valid expression
		try { a.checkFormat("co 2.1.3 2.3.3 ((~p&~q)=>~(p|q))"); }
		catch(IllegalLineException e) { fail(); }
		
		//Right number of args but invalid expression
		try { a.checkFormat("co 2.1.3 2.3.3 (p=>)q"); fail(); }
		catch(IllegalLineException e) { assertTrue(true); }
		
		//Wrong number of args but invalid expression
		try { a.checkFormat("co 2.1.3 ((~p&~q)=>~(p|q))"); fail(); }
		catch(IllegalLineException e) { assertTrue(true); }
	}

	public void testTheoremName() {
		Format a = new Format();
		
		//valid
		try { a.checkFormat("patsy (~p=>q)"); }
		catch(IllegalLineException e) { assertTrue(false); }
		
		try { a.checkFormat("Beavis (p=>q)"); }
		catch(IllegalLineException e) { assertTrue(false); }
	}

	public void testCheckExpressions () {
		Format check = new Format();

		//checks null input
		//Expression a = new Expression(null);
		//assertFalse(check.expressionValidity(a));

		
		//VALID
		//checks expression with a length of one
		try { check.expressionValidity("p"); }
		catch (IllegalLineException e) { fail(); }
		
		//Strings with a length of 2 can only be in the format: '~variable'
		try { check.expressionValidity("~p"); }
		catch (IllegalLineException e) { fail(); }
		
		try { check.expressionValidity("(p=>q)"); }
		catch (IllegalLineException e) { fail(); }
		
		try { check.expressionValidity("~(p=>q)"); }
		catch (IllegalLineException e) { fail(); }
		
		try { check.expressionValidity("(a=>(b=>(a&b)))"); }
		catch (IllegalLineException e) { fail(); }
		
		try { check.expressionValidity("(~~p=>p)"); }
		catch (IllegalLineException e) { fail(); }
		
		try { check.expressionValidity("((a=>b)=>((b=>c)=>(a=>c)))"); }
		catch (IllegalLineException e) { fail(); }
		
		try { check.expressionValidity("(p=>(p=>q))"); }
		catch (IllegalLineException e) { fail(); }
		
		try { check.expressionValidity("((a=>b)=>(b=>a))"); }
		catch (IllegalLineException e) { fail(); }
		
		try { check.expressionValidity("((~p&~q)=>~(p|q))"); }
		catch (IllegalLineException e) { fail(); }
		
		try { check.expressionValidity(" ~p   "); }
		catch (IllegalLineException e) { fail(); }
		
		try { check.expressionValidity("(a=>(b=>(a&b)))"); }
		catch (IllegalLineException e) { fail(); }

		try { check.expressionValidity("((((r|s)=>(x|~y))&(~(r|s)=>(x|~y)))=>(x|~y))"); }
		catch (IllegalLineException e) { fail(); }
				
		//INVALID
		//Strings with a length of one can only be variables
		try { check.expressionValidity("8"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("89"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("~8"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("~~"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("8t"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("t~"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		//can't have more than one implies per parens
		try { check.expressionValidity("(p=>q=>p)"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		//We match up parens, but never check that there IS a closing paren
		//in the right spot
		//Only things that may follow a paren: => or )
		try { check.expressionValidity("(p=>)q"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("((p=>(q))"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("(p(=>q))"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("((p= >q)"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("((~p&~q)=>~(p|q)))"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("mp(p=>q)"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("(((((p)))))"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("ab"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("(ab)"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }

		try { check.expressionValidity(""); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("(p)"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		/*
		try { check.expressionValidity("(ab=>cd)"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		*/
		
		try { check.expressionValidity("(p=>=>q)"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("((p=>q))"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("(p)=>(q)"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("((p)=>(q))"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("((p=>)q)"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("(p=>q))"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("mp1.2.1 1.2.3(p=>q)"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("^"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
		
		try { check.expressionValidity("(((p&q)=>(p|q))&&(p=>q))"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }

		try { check.expressionValidity("(((p=>q)&(q=>|p))=>(r|q))"); fail(); }
		catch (IllegalLineException e) { assertTrue(true); }
	}
}
