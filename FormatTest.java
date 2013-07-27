import junit.framework.TestCase;

public class FormatTest extends TestCase {

	    public void testKeywords () {
	        Format a = new Format();
	        //all these should pass (and not hit the assertTrue(false) statement)
	        try { a.checkFormat("mp 2.1.3 2.3.3 (p=>q)"); fail();}
	        catch (IllegalLineException e) { assertTrue(false); }
	        
	        try { a.checkFormat("co 2.1.3 2.3.3 (p=>q)"); fail();}
	        catch (IllegalLineException e) { assertTrue(false); }
	        
	        try { a.checkFormat("ic 3.4 (a=>(a=>b))"); fail();}
	        catch(IllegalLineException e) { assertTrue(false); }
	        
	        try { a.checkFormat("show (p=>q)"); }
	        catch(IllegalLineException e) { assertTrue(false); }
	        
	        try { a.checkFormat("assume (p=>q)"); }
	        catch(IllegalLineException e) { assertTrue(false); }
	        
	        try { a.checkFormat("print"); }
	        catch(IllegalLineException e) { assertTrue(false); }
	        
	        //all these should fail
	        try { a.checkFormat("mp 2.1.3 2.3.3"); assertTrue(false); }
	        catch(IllegalLineException e) { assertTrue(true); }
	        
	        try { a.checkFormat("mt 2.1.3"); assertTrue(false); }
	        catch(IllegalLineException e) { assertTrue(true); }
	        
	        try { a.checkFormat("asfd"); assertTrue(false); }
	        catch(IllegalLineException e) { assertTrue(true); }
	        
	        try { a.checkFormat("ic3.4 7.9 (a=>(a=>b))"); assertTrue(false); }
	        catch(IllegalLineException e) { assertTrue(true); }
	        
	        try { a.checkFormat("antsy 3.4 7.9 (a=>(a=>b))"); assertTrue(false); }
	        catch(IllegalLineException e) { assertTrue(true); }
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
			fail();
		}
		
        String y = "~(p=>q)";
        try {
        	check.expressionValidity(y);
        }
		catch (IllegalLineException e) {
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
			fail();
		}
		
		String q = "(a=>(b=>(a&b)))";
		try {
			check.expressionValidity(q);
		}
		catch (IllegalLineException e) {
			fail();
		}
		
		String r = "((a=>b)=>((b=>c)=>(a=>c)))";
		try {
			check.expressionValidity(r);
		}
		catch (IllegalLineException e) {
			fail();
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
		
		String v = "(p=>(p=>q))";
		try {
			check.expressionValidity(v);
		}
		catch (IllegalLineException e) {
			fail();
		}
		
		String w = "";
		try {
			check.expressionValidity(w);
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String x = "(p)";
		try {
			check.expressionValidity(x);
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String ab = "(ab=>cd)";
		try {
			check.expressionValidity(ab);
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String ac = "((a=>b)=>(b=>a))";
		try {
			check.expressionValidity(ac);
		}
		catch (IllegalLineException e) {
			fail();
		}
		
		/*String ad = null;
		try {
			check.expressionValidity(ad);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}*/
		
		String ae = "(p=>=>q)";
		try {
			check.expressionValidity(ae);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String af = "((p=>q))";
		try {
			check.expressionValidity(af);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String ag = "((p=>q)))";
		try {
			check.expressionValidity(ag);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String ah = "(p)=>(q)";
		try {
			check.expressionValidity(ah);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String ai = "((p)=>(q))";
		try {
			check.expressionValidity(ai);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String aj = "((p=>)q)";
		try {
			check.expressionValidity(aj);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String ak = "(~~p=>p)";
		try {
			check.expressionValidity(ak);
		}
		catch (IllegalLineException e) {
			fail();			
		}
		
		String al = "((~p&~q)=>~(p|q))";
		try {
			check.expressionValidity(al);
		}
		catch (IllegalLineException e) {
			fail();
		}
		
		String am = " ~p   ";
		try {
			check.expressionValidity(am);
		}
		catch (IllegalLineException e) {
			fail();
		}
		
		String an = "(p=>q))";
		try {
			check.expressionValidity(an);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String ao = "(a=>(b=>(a&b)))";
		try {
			check.expressionValidity(ao);
		}
		catch (IllegalLineException e) {
			fail();
		}
		
		String ap = "((((r|s)=>(x|~y))&(~(r|s)=>(x|~y)))=>(x|~y))";
		try {
			check.expressionValidity(ap);
		}
		catch (IllegalLineException e) {
			fail();
		}
		
		String ar = "mp1.2.1 1.2.3(p=>q)";
		try {
			check.expressionValidity(ar);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String as = "^";
		try {
			check.expressionValidity(as);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String at = "(((p&q)=>(p|q))&&(p=>q))";
		try {
			check.expressionValidity(at);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
		
		String au = "(((p=>q)&(q=>|p))=>(r|q))";
		try {
			check.expressionValidity(au);
			fail();
		}
		catch (IllegalLineException e) {
			assert true;
		}
	}
}
