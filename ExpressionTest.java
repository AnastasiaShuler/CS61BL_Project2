import junit.framework.TestCase;

public class ExpressionTest extends TestCase {

    public void testExpression(){
        Expression a = new Expression ("p");
        assertTrue(a.valid);
        Expression c = new Expression ("(p=>q)");
        assertTrue(c.valid);
        Expression d = new Expression ("(p=>(p=>q))");
        assertTrue(d.valid);
        Expression e = new Expression ("");
        //cannot have a null function!
        assertFalse(e.valid);
        Expression f = new Expression("(p)");
        assertFalse(f.valid);
    }
    
    public void testNot ( ) {
        Expression a = new Expression("~p");
        assertTrue(a.valid);
        Expression b = new Expression ("~(p=>q)");
        assertTrue (b.valid);
        Expression c = new Expression ("(~p=>q)");
        assertTrue(c.valid);
        Expression d = new Expression("(~~p=>p)");
        assertTrue(d.valid);
        Expression e = new Expression("((~p&~q)=>~(p|q))");
        //~ may appear in front of both p and q
        assertTrue(e.valid);
    }
    
    public void testSpace() {
      /*
    	 * Whitespace before and after an expression is ok,
    	 * but not in the middle of an expression
    	 */
    	Expression a = new Expression("~p ");
    	assertTrue(a.valid);
    	Expression b = new Expression ("(p =>q)");
    	assertFalse(b.valid);
    	Expression c = new Expression ("(p= >q)");
    	assertFalse(c.valid);
    	Expression d = new Expression("  (p=>q)   ");
    	assertTrue(d.valid);
    	Expression e = new Expression("( p=>q )");
    	assertFalse(e.valid);
    }
    
    public void testVariables() {
    	//These may be valid? Check these again
    	Expression a = new Expression("(p=>p)");
    	assertFalse(a.valid);
    	Expression b = new Expression("((p=>p)=>p))");
    	assertFalse(b.valid);
    }
    
    public void testParens( ) {
    	/*
    	 * Checks that the program parses the parentheses 
    	 * of nested expressions correctly
    	 */
    	Expression a = new Expression("(p=>q))");
    	assertFalse(a.valid);
    	Expression b = new Expression("((p=>q)=>q)=>((q=>p)=>p))");
    	assertFalse(b.valid);
    	Expression c = new Expression("(((p=>q)=>q)=>((q=>p)=>p))");
    	assertTrue(c.valid);
    	Expression d = new Expression("((a=>b)=>((b=>c)=>(a=>c)))");
    	assertFalse(d.valid);
    	Expression e = new Expression("(a=>(b=>(a&b)))");
    	assertTrue(e.valid);
    }
    
    public void testPatternMatching ( ) {
    	/* Check that more complicated expressions 
    	 * can be pattern-matched to p and q.
    	 */
    	Expression a = new Expression("((((r|s)=>(x|~y))&(~(r|s)=>(x|~y)))=>(x|~y))");
    	//(r|s) can be matched to p, (x|~y) can be matched to q
    	assertTrue(a.valid);
    	//check for more edge cases!
    			
    }
    
    public void testKeywords ( ){
    	/* Check that mp, co, ic, show, and assume are entered in the right format.
    	 */
    	Expression a = new Expression ("mp1.2.1 1.2.3(p=>q)");
    	//our method splits things by spaces; what if user neglects a space?
    	assertFalse(a.isValid);
    	}
    }
    //public void testNot() 
}
