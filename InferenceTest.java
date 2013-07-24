import static org.junit.Assert.*;

import org.junit.Test;


public class InferenceTest {

	@Test
	public void testMP() {
		String input = "mp 3.2.2.4 3.2.2.2 (~b=>~(a|b))";
		ProofSoFar psf = new ProofSoFar();
		Expression E1 = new Expression("(~a=>(~b=>~(a|b)))");
		Expression E2 = new Expression("~a");
		psf.add("3.2.2.4", E1);
		psf.add("3.2.2.2", E2);
		assertTrue(Inference.mp(input, psf));
		
		input = "mp 3.2.2.3 3.2.2.5 ~(a|b)";
		Expression E3 = new Expression("~b");
		Expression E4 = new Expression("(~b=>~(a|b))");
		psf.add("3.2.2.3", E3);
		psf.add("3.2.2.5", E4);
		assertTrue(Inference.mp(input,psf));
	}
	
	@Test
	public void testMT(){
		String input = "mt 3.2.1 2 a";
		ProofSoFar psf = new ProofSoFar();
		Expression E1 = new Expression("(a|b)");
		Expression E2 = new Expression("(a=>q)");
		psf.add("3.2.1", E1);
		psf.add("2", E2);
		//Should fail because neither root is '~'
		assertFalse(Inference.mt(input, psf));
		System.out.println();
			
		String s= "mt 3.2.2.1 2 ~a";
		Expression E3 = new Expression("~q");
		psf.add("3.2.2.1", E3);
		assertTrue(Inference.mt(s, psf));
		
		input  = "mt 3.2.2.1 2 a";
		psf.add("2", E2);
		//Should fail because inference is wrong;
		assertFalse(Inference.mt(input, psf));
		
		input = "mt 3.1 3.2.2.1 ~b";
		Expression E4 = new Expression("(b=>q)");
		psf.add("3.1", E4);
		assertTrue(Inference.mt(input, psf));
	}
	
	@Test
	public void testIC(){
		String input = "ic 3.2.2 ((a|b)=>q)";
		ProofSoFar psf = new ProofSoFar();
		Expression E1 = new Expression("q");
		psf.add("3.2.2", E1);
		assertTrue(Inference.ic(input, psf));
		
		input = "ic 3.2 ((b=>q)=>((a|b)=>q))";
		psf.add("3.2", new Expression("((a|b)=>q))"));
		assertTrue(Inference.ic(input, psf));
		
		input = "ic 3 ((a=>q)=>((b=>q)=>((a|b)=>q)))";
		psf.add("3", new Expression("((b=>q)=>((a|b)=>q))"));
		assertTrue(Inference.ic(input, psf));
		
		psf.add("4", new Expression("(a|b)"));
		//Should fail because I made it up
		assertFalse(Inference.ic("ic 4 (a=>q)", psf));
	}
	
	@Test
	public void TestCO(){
		String input = "co 3.2.2.6 3.2.1 q";
		ProofSoFar psf = new ProofSoFar();
		Expression E1 = new Expression("~(a|b)");
		Expression E2 = new Expression("(a|b)");
		psf.add("3.2.2.6" ,E1);
		psf.add("3.2.1", E2);
		assertTrue(Inference.co(input, psf));
		
		input = "co 3.2.2.6 3.2.1 ((a=>q)=>((b=>q)=>((a|b)=>q)))";
		psf.add("3.2.2.6", new Expression("~(a|b)"));
		psf.add("3.2.1", new Expression("(a|b)"));
		assertTrue(Inference.co(input, psf));
		
		input = "co 3.2.2.6 4 a";
		psf.add("4", new Expression("(a=>b)"));
		//Should fail because I just made it up
		assertFalse(Inference.co(input, psf));
		
	}

}
