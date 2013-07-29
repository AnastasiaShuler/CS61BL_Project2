import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.Test;


public class InferenceTest {
	//Create a Proof object so Proof.line references don't fail
	Proof p = new Proof(null);
	String s = p.nextLineNumber(); 	//Keep line.prev() from failing

	/**
	 *  testMP() Tests the mp() method of the Inference class.
	 **/
	@Test
	public void testMP() {
		String input = "mp 3.2.2.4 3.2.2.2 (~b=>~(a|b))";
		Expression E1 = new Expression("(~a=>(~b=>~(a|b)))");
		Expression E2 = new Expression("~a");
		p.exprs.put("3.2.2.4", E1);
		p.exprs.put("3.2.2.2", E2);
		try{
			assertTrue(Inference.mp(input));
		}catch (IllegalInferenceException e){
			fail();
		}
		
		input = "mp 3.2.2.3 3.2.2.5 ~(a|b)";
		Expression E3 = new Expression("~b");
		Expression E4 = new Expression("(~b=>~(a|b))");
		p.exprs.put("3.2.2.3", E3);
		p.exprs.put("3.2.2.5", E4);
		try{
			assertTrue(Inference.mp(input));
		} catch (IllegalInferenceException e){
			fail();
		}
		
		input = "mp 2 3.1 q";
		Expression E5 = new Expression("p");
		Expression E6 = new Expression("(p=>q)");
		p.exprs.put("2", E5);
		p.exprs.put("3.1", E6);
		try{
			assertTrue(Inference.mp(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "mp 3.1 3.2.2 (~q=>(~p&~q))";
		Expression E7 = new Expression("~p");
		Expression E8 = new Expression ("(~p=>(~q=>(~p&~q)))");
		p.exprs.put("3.1", E7);
		p.exprs.put("3.2.2", E8);
		try{
			assertTrue(Inference.mp(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "mp 3.2.3 3.2.1 (~p&~q)";
		Expression E9 = new Expression("(~q=>(~p&~q))");
		Expression E10 = new Expression("~q");
		p.exprs.put("3.2.3", E9);
		p.exprs.put("3.2.1", E10);
		try{
			assertTrue(Inference.mp(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "mp 3.2.4 3.2.5 ~(p|q)";
		Expression E11 = new Expression("(~p&~q)");
		Expression E12 = new Expression ("((~p&~q)=>~(p|q))");
		p.exprs.put("3.2.4", E11);
		p.exprs.put("3.2.5", E12);
		try{
			assertTrue(Inference.mp(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "mp 3.2 3.1 ~a";
		Expression E13 = new Expression("(~~~a=>~a)");
		Expression E14 = new Expression("~~~a");
		p.exprs.put("3.2", E13);
		p.exprs.put("3.1", E14);
		try{
			assertTrue(Inference.mp(input));
		}catch(IllegalInferenceException e){
			fail();
		}
		
		input = "mp 2 3.2.1 (b=>c)";
		Expression E15 = new Expression("(a=>(b=>c))");
		Expression E16 = new Expression("a");
		p.exprs.put("2", E15);
		p.exprs.put("3.2.1", E16);
		try{
			assertTrue(Inference.mp(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "mp 3.2.1 3.1 b";
		Expression E17 = new Expression ("(a=>b)");
		p.exprs.put("3.1", E17);
		try{
			assertTrue(Inference.mp(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "mp 3.2.3 3.2.2 c";
		Expression E18 = new Expression("b");
		Expression E19 = new Expression("(b=>c)");
		p.exprs.put("3.2.3", E18);
		p.exprs.put("3.2.2", E19);
		try{
			assertTrue(Inference.mp(input));
		} catch(IllegalInferenceException e){
			fail();
		}
				
		//failing test
		input = "mp 3.2.3 3.2.1 ~q";
		try{
			assertFalse(Inference.mp(input));
			fail();
		} catch(IllegalInferenceException e){
			assertTrue(true);
		}
		
		input = "mp 3.2.3 3.2.2 (~p&~q)";
		try{
			assertFalse(Inference.mp(input));
		} catch(IllegalInferenceException e){
			assertTrue(true);
		}
		
		input = "mp 3.2.3 3.2.2 ~a";
		try{
			assertFalse(Inference.mp(input));
			fail();
		} catch(IllegalInferenceException e){
			assertTrue(true);
		}
		
		input = "mp 3.2.3 3.2.1 (q=>q)";
		try{
			assertFalse(Inference.mp(input));
			fail();
		} catch(IllegalInferenceException e){
			assertTrue(true);
		}
		
	}
	
	/**
	 *  testMT() Tests the mt() method of the Inference class.
	 **/
	@Test
	public void testMT(){
		String input = "mt 3.2.1 2 a";
		Expression E1 = new Expression("(a|b)");
		Expression E2 = new Expression("(a=>q)");
		p.exprs.put("3.2.1", E1);
		p.exprs.put("2", E2);
		//Should fail because neither root is '~'
		try{
			assertFalse(Inference.mt(input));
			fail();
		} catch (IllegalInferenceException e){
			assertTrue(true);
		}
			
		String s= "mt 3.2.2.1 2 ~a";
		Expression E3 = new Expression("~q");
		p.exprs.put("3.2.2.1", E3);
		try{
			assertTrue(Inference.mt(s));
		} catch (IllegalInferenceException e){
			fail();
		}
		
		input  = "mt 3.2.2.1 2 a";
		p.exprs.put("2", E2);
		//Should fail because inference is wrong;
		try{
			assertFalse(Inference.mt(input));
			fail();
		} catch(IllegalInferenceException e){
			assertTrue(true);
		}
		
		input = "mt 3.1 3.2.2.1 ~b";
		Expression E4 = new Expression("(b=>q)");
		p.exprs.put("3.1", E4);
		try{
			assertTrue(Inference.mt(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		
		input = "mt 2 3.2.2 ~(p=>q)";
		Expression E5 = new Expression("((p=>q)=>q)");
		Expression E6 = new Expression("~q");
		p.exprs.put("2", E5);
		p.exprs.put("3.2.2", E6);
		try{
			assertTrue(Inference.mt(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "mt 3.2.2.1 3.1 ~b";
		Expression E7 = new Expression("~c");
		Expression E8 = new Expression("(b=>c)");
		p.exprs.put("3.2.2.1", E7);
		p.exprs.put("3.1", E8);
		try{
			assertTrue(Inference.mt(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "mt 2 3.2.2.2 ~a";
		Expression E9 = new Expression ("(a=>b)");
		Expression E10 = new Expression ("~b");
		p.exprs.put("2", E9);
		p.exprs.put("3.2.2.2", E10);
		try{
			assertTrue(Inference.mt(input));
		} catch(IllegalInferenceException e){
			fail();
		}
	}
	
	/**
	 *  testIC() Tests the ic() method of the Inference class.
	 **/
	@Test
	public void testIC(){
		String input = "ic 3.2.2 ((a|b)=>q)";
		Expression E1 = new Expression("q");
		p.exprs.put("3.2.2", E1);
		try{
			assertTrue(Inference.ic(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "ic 3.2 ((b=>q)=>((a|b)=>q))";
		p.exprs.put("3.2", new Expression("((a|b)=>q))"));
		try{
			assertTrue(Inference.ic(input));
		} catch (IllegalInferenceException e){
			fail();
		}
			
		
		input = "ic 3 ((a=>q)=>((b=>q)=>((a|b)=>q)))";
		p.exprs.put("3", new Expression("((b=>q)=>((a|b)=>q))"));
		try{
			assertTrue(Inference.ic(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		p.exprs.put("4", new Expression("(a|b)"));
		//Should fail because I made it up
		try{
			assertFalse(Inference.ic("ic 4 (a=>q)"));
			fail();
		} catch(IllegalInferenceException e){
			assertTrue(true);
		}
		
		input = "ic 3.2.2.4 (a=>c)";
		Expression E2 = new Expression("c");
		p.exprs.put("3.2.2.4", E2);
		try{
			assertTrue(Inference.ic(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "ic 3.2.2 (a=>c)";
		Expression E3 = new Expression("c");
		p.exprs.put("3.2.2", E3);
		try{
			assertTrue(Inference.ic(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "ic 3.2 ((b=>c)=>(a=>c))";
		Expression E4 = new Expression("(a=>c)");
		p.exprs.put("3.2", E4);
		try{
			assertTrue(Inference.ic(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "ic 3 ((a=>b)=>((b=>c)=>(a=>c)))";
		Expression E5 = new Expression ("((b=>c)=>(a=>c))");
		p.exprs.put("3", E5);
		try{
			assertTrue(Inference.ic(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "ic 3.2 ((p=>q)=>q)";
		Expression E6 = new Expression("q");
		p.exprs.put("3.2", E6);
		try{
			assertTrue(Inference.ic(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "ic 3 (p=>((p=>q)=>q))";
		Expression E7 = new Expression("((p=>q)=>q)");
		p.exprs.put("3", E7);
		try{
			assertTrue(Inference.ic(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
	}
	
	/**
	 *  TestCO() Tests the co() method of the Inference class.
	 **/
	@Test
	public void TestCO(){
		String input = "co 3.2.2.6 3.2.1 q";
		Expression E1 = new Expression("~(a|b)");
		Expression E2 = new Expression("(a|b)");
		p.exprs.put("3.2.2.6" ,E1);
		p.exprs.put("3.2.1", E2);
		try{
			assertTrue(Inference.co(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "co 3.2.2.6 3.2.1 ((a=>q)=>((b=>q)=>((a|b)=>q)))";
		p.exprs.put("3.2.2.6", new Expression("~(a|b)"));
		p.exprs.put("3.2.1", new Expression("(a|b)"));
		try{
			assertTrue(Inference.co(input));
		} catch (IllegalInferenceException e){
			fail();
		}
		
		input = "co 3.2.2.6 4 a";
		p.exprs.put("4", new Expression("(a=>b)"));
		//Should fail because I just made it up
		try{
			assertFalse(Inference.co(input));
			fail();
		} catch(IllegalInferenceException e){
			assertTrue(true);
		}
		
		input = "co 3.2.6 2 q";
		p.exprs.put("3.2.6", new Expression("~(p|q)"));
		p.exprs.put("2", new Expression("(p|q)"));
		try{
			assertTrue(Inference.co(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		input = "co 2 3.3 ~~a";
		p.exprs.put("2", new Expression("a"));
		p.exprs.put("3.3", new Expression("~a"));
		try{
			assertTrue(Inference.co(input));
		} catch(IllegalInferenceException e){
			fail();
		}
		
		//Tests that fail
		input = "co 3.2.6 2 a";
		try{
			assertFalse(Inference.co(input));
			fail();
		} catch(IllegalInferenceException e){
			assertTrue(true);
		}
		
		input = "co 4 3.2.6 q";
		try{
			assertFalse(Inference.co(input));
			fail();
		} catch(IllegalInferenceException e){
			assertTrue(true);
		}
		
		input = "co 3.2.6 2 a";
		try{
			assertFalse(Inference.co(input));
			fail();
		} catch(IllegalInferenceException e){
			assertTrue(true);
		}
	}

	/**
	 *  TestAssume() Tests the assume() method of the Infernece class
	 **/
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
		
		input = "assume (a=>(b=>c))";
		prevInput = "show ((a=>(b=>c))=>((a=>b)=>(a=>c)))";
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
	}
	
	
}
