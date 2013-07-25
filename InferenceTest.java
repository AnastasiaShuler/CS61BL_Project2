import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.Test;


public class InferenceTest {

	@Test
	public void testMP() {
		String input = "mp 3.2.2.4 3.2.2.2 (~b=>~(a|b))";
		Hashtable<String, Expression>psf = new Hashtable<String, Expression>();
		Expression E1 = new Expression("(~a=>(~b=>~(a|b)))");
		Expression E2 = new Expression("~a");
		psf.put("3.2.2.4", E1);
		psf.put("3.2.2.2", E2);
		assertTrue(Inference.mp(input, psf));
		
		input = "mp 3.2.2.3 3.2.2.5 ~(a|b)";
		Expression E3 = new Expression("~b");
		Expression E4 = new Expression("(~b=>~(a|b))");
		psf.put("3.2.2.3", E3);
		psf.put("3.2.2.5", E4);
		assertTrue(Inference.mp(input,psf));
	}
	
	@Test
	public void testMT(){
		String input = "mt 3.2.1 2 a";
		Hashtable<String, Expression> psf = new Hashtable<String, Expression>();
		Expression E1 = new Expression("(a|b)");
		Expression E2 = new Expression("(a=>q)");
		psf.put("3.2.1", E1);
		psf.put("2", E2);
		//Should fail because neither root is '~'
		assertFalse(Inference.mt(input, psf));
		System.out.println();
			
		String s= "mt 3.2.2.1 2 ~a";
		Expression E3 = new Expression("~q");
		psf.put("3.2.2.1", E3);
		assertTrue(Inference.mt(s, psf));
		
		input  = "mt 3.2.2.1 2 a";
		psf.put("2", E2);
		//Should fail because inference is wrong;
		assertFalse(Inference.mt(input, psf));
		
		input = "mt 3.1 3.2.2.1 ~b";
		Expression E4 = new Expression("(b=>q)");
		psf.put("3.1", E4);
		assertTrue(Inference.mt(input, psf));
		
		
		input = "mt 2 3.2.2 ~(p=>q)";
		Expression E5 = new Expression("((p=>q)=>q)");
		Expression E6 = new Expression("~q");
		psf.put("2", E5);
		psf.put("3.2.2", E6);
		assertTrue(Inference.mt(input, psf));
	}
	
	@Test
	public void testIC(){
		String input = "ic 3.2.2 ((a|b)=>q)";
		Hashtable<String, Expression> psf = new Hashtable<String, Expression>();
		Expression E1 = new Expression("q");
		psf.put("3.2.2", E1);
		assertTrue(Inference.ic(input, psf));
		
		input = "ic 3.2 ((b=>q)=>((a|b)=>q))";
		psf.put("3.2", new Expression("((a|b)=>q))"));
		assertTrue(Inference.ic(input, psf));
		
		input = "ic 3 ((a=>q)=>((b=>q)=>((a|b)=>q)))";
		psf.put("3", new Expression("((b=>q)=>((a|b)=>q))"));
		assertTrue(Inference.ic(input, psf));
		
		psf.put("4", new Expression("(a|b)"));
		//Should fail because I made it up
		assertFalse(Inference.ic("ic 4 (a=>q)", psf));
	}
	
	@Test
	public void TestCO(){
		String input = "co 3.2.2.6 3.2.1 q";
		Hashtable<String, Expression> psf = new Hashtable<String, Expression>();
		Expression E1 = new Expression("~(a|b)");
		Expression E2 = new Expression("(a|b)");
		psf.put("3.2.2.6" ,E1);
		psf.put("3.2.1", E2);
		assertTrue(Inference.co(input, psf));
		
		input = "co 3.2.2.6 3.2.1 ((a=>q)=>((b=>q)=>((a|b)=>q)))";
		psf.put("3.2.2.6", new Expression("~(a|b)"));
		psf.put("3.2.1", new Expression("(a|b)"));
		assertTrue(Inference.co(input, psf));
		
		input = "co 3.2.2.6 4 a";
		psf.put("4", new Expression("(a=>b)"));
		//Should fail because I just made it up
		assertFalse(Inference.co(input, psf));
	}

	@Test
	public void TestAssume(){
		String input = "assume q";
		String prevInput = "show (q=>q)";
		String currLine = "3.2";
		String prevLine = "3.1";
		Hashtable<String, Expression> expr = new Hashtable<String, Expression>();
		Hashtable<String, String> steps = new Hashtable<String, String>();
		steps.put(prevLine, prevInput);
		steps.put(currLine, input);
		expr.put(currLine, new Expression(input));
		expr.put(prevLine, new Expression(prevInput));
		try{
			assertTrue(Inference.assume(input, prevInput, expr));
		} catch(IllegalInferenceException exc){
			fail();
		}
		
		input = "assume (a|b)";
		prevInput = "show ((a|b)=>q)";
		currLine = "2";
		prevLine = "1";
		steps.put(prevLine, prevInput);
		steps.put(currLine, input);
		expr.put(currLine, new Expression(input));
		expr.put(prevLine, new Expression(prevInput));
		try{
			assertTrue(Inference.assume(input, prevInput, expr));
		} catch(IllegalInferenceException exc){
			fail();
		}
		
		input = "assume ~q";
		prevInput = "show q";
		currLine = "2";
		prevLine = "1";
		steps.put(prevLine, prevInput);
		steps.put(currLine, input);
		expr.put(currLine, new Expression(input));
		expr.put(prevLine, new Expression(prevInput));
		try{
			assertTrue(Inference.assume(input, prevInput, expr));
		} catch(IllegalInferenceException exc){
			fail();
		}
		
		input = "assume ~q";
		prevInput = "show (q=>q)";
		currLine = "2";
		prevLine = "1";
		steps.put(prevLine, prevInput);
		steps.put(currLine, input);
		expr.put(currLine, new Expression(input));
		expr.put(prevLine, new Expression(prevInput));
		try{
			assertTrue(Inference.assume(input, prevInput, expr));
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
		expr.put(currLine, new Expression(input));
		expr.put(prevLine, new Expression(prevInput));
		try{
			assertTrue(Inference.assume(input, prevInput, expr));
			fail();
		} catch(IllegalInferenceException exc){
			assertTrue(true);
		}
	}
}
