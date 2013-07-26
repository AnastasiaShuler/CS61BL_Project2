import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.Test;


public class InferenceTest {
	Proof p = new Proof(null);

	@Test
	public void testMP() {
		String input = "mp 3.2.2.4 3.2.2.2 (~b=>~(a|b))";
		Expression E1 = new Expression("(~a=>(~b=>~(a|b)))");
		Expression E2 = new Expression("~a");
		p.exprs.put("3.2.2.4", E1);
		p.exprs.put("3.2.2.2", E2);
		assertTrue(Inference.mp(input));
		
		input = "mp 3.2.2.3 3.2.2.5 ~(a|b)";
		Expression E3 = new Expression("~b");
		Expression E4 = new Expression("(~b=>~(a|b))");
		p.exprs.put("3.2.2.3", E3);
		p.exprs.put("3.2.2.5", E4);
		assertTrue(Inference.mp(input));
	}
	
	@Test
	public void testMT(){
		String input = "mt 3.2.1 2 a";
		Expression E1 = new Expression("(a|b)");
		Expression E2 = new Expression("(a=>q)");
		p.exprs.put("3.2.1", E1);
		p.exprs.put("2", E2);
		//Should fail because neither root is '~'
		assertFalse(Inference.mt(input));
		System.out.println();
			
		String s= "mt 3.2.2.1 2 ~a";
		Expression E3 = new Expression("~q");
		p.exprs.put("3.2.2.1", E3);
		assertTrue(Inference.mt(s));
		
		input  = "mt 3.2.2.1 2 a";
		p.exprs.put("2", E2);
		//Should fail because inference is wrong;
		assertFalse(Inference.mt(input));
		
		input = "mt 3.1 3.2.2.1 ~b";
		Expression E4 = new Expression("(b=>q)");
		p.exprs.put("3.1", E4);
		assertTrue(Inference.mt(input));
		
		
		input = "mt 2 3.2.2 ~(p=>q)";
		Expression E5 = new Expression("((p=>q)=>q)");
		Expression E6 = new Expression("~q");
		p.exprs.put("2", E5);
		p.exprs.put("3.2.2", E6);
		assertTrue(Inference.mt(input));
	}
	
	@Test
	public void testIC(){
		String input = "ic 3.2.2 ((a|b)=>q)";
		Expression E1 = new Expression("q");
		p.exprs.put("3.2.2", E1);
		assertTrue(Inference.ic(input));
		
		input = "ic 3.2 ((b=>q)=>((a|b)=>q))";
		p.exprs.put("3.2", new Expression("((a|b)=>q))"));
		assertTrue(Inference.ic(input));
		
		input = "ic 3 ((a=>q)=>((b=>q)=>((a|b)=>q)))";
		p.exprs.put("3", new Expression("((b=>q)=>((a|b)=>q))"));
		assertTrue(Inference.ic(input));
		
		p.exprs.put("4", new Expression("(a|b)"));
		//Should fail because I made it up
		assertFalse(Inference.ic("ic 4 (a=>q)"));
	}
	
	@Test
	public void TestCO(){
		String input = "co 3.2.2.6 3.2.1 q";
		Expression E1 = new Expression("~(a|b)");
		Expression E2 = new Expression("(a|b)");
		p.exprs.put("3.2.2.6" ,E1);
		p.exprs.put("3.2.1", E2);
		assertTrue(Inference.co(input));
		
		input = "co 3.2.2.6 3.2.1 ((a=>q)=>((b=>q)=>((a|b)=>q)))";
		p.exprs.put("3.2.2.6", new Expression("~(a|b)"));
		p.exprs.put("3.2.1", new Expression("(a|b)"));
		assertTrue(Inference.co(input));
		
		input = "co 3.2.2.6 4 a";
		p.exprs.put("4", new Expression("(a=>b)"));
		//Should fail because I just made it up
		assertFalse(Inference.co(input));
	}

	@Test
	public void TestAssume(){
		p.line.next(); //Keeps tests from failing b/c Inference.assume decrements line number
		
		String input = "assume q";
		String prevInput = "show (q=>q)";
		String currLine = "3.2";
		String prevLine = "3.1";
		Hashtable<String, String> steps = new Hashtable<String, String>();
		steps.put(prevLine, prevInput);
		steps.put(currLine, input);
		p.exprs.put(currLine, new Expression(input));
		p.exprs.put(prevLine, new Expression(prevInput));
		try{
			assertTrue(Inference.assume(input, prevInput));
		} catch(IllegalInferenceException exc){
			fail();
		}
		
		input = "assume (a|b)";
		prevInput = "show ((a|b)=>q)";
		currLine = "2";
		prevLine = "1";
		steps.put(prevLine, prevInput);
		steps.put(currLine, input);
		p.exprs.put(currLine, new Expression(input));
		p.exprs.put(prevLine, new Expression(prevInput));
		try{
			assertTrue(Inference.assume(input, prevInput));
		} catch(IllegalInferenceException exc){
			fail();
		}
		
		input = "assume ~q";
		prevInput = "show q";
		currLine = "2";
		prevLine = "1";
		steps.put(prevLine, prevInput);
		steps.put(currLine, input);
		p.exprs.put(currLine, new Expression(input));
		p.exprs.put(prevLine, new Expression(prevInput));
		try{
			assertTrue(Inference.assume(input, prevInput));
		} catch(IllegalInferenceException exc){
			fail();
		}
		
		input = "assume ~q";
		prevInput = "show (q=>q)";
		currLine = "2";
		prevLine = "1";
		steps.put(prevLine, prevInput);
		steps.put(currLine, input);
		p.exprs.put(currLine, new Expression(input));
		p.exprs.put(prevLine, new Expression(prevInput));
		try{
			assertTrue(Inference.assume(input, prevInput));
			fail();
		} catch(IllegalInferenceException exc){
			assertTrue(true);
		}
		
		input = "assume b";
		prevInput = "show ((b=>q)=>((a|b)=>q))";
		currLine = "2";
		prevLine = "1";
		steps.put(prevLine, prevInput);
		steps.put(currLine, input);
		p.exprs.put(currLine, new Expression(input));
		p.exprs.put(prevLine, new Expression(prevInput));
		try{
			assertTrue(Inference.assume(input, prevInput));
			fail();
		} catch(IllegalInferenceException exc){
			assertTrue(true);
		}
	}
}
