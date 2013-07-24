import junit.framework.TestCase;

public class FormatTest extends TestCase {
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
		assertFalse(b.checkFormat(ex4));
	}
	
	public void testTheoremName() {
		Format c = new Format();
		String [] ex1 = new String [] {"patsy", "(~p=>q)"};
		assertTrue(c.checkFormat(ex1));
	}
}
