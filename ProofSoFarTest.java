import static org.junit.Assert.*;

import org.junit.Test;


public class ProofSoFarTest {

	@Test
	public void testAdd() {
		ProofSoFar psf = new ProofSoFar();
		psf.add("1", "1");
		assertEquals("1", psf.find("1"));
		psf.add("2", "2");
		assertEquals("2", psf.find("2"));
		assertEquals("1", psf.find("1"));
		psf.add("1.1", "a");
		assertEquals("a", psf.find("1.1"));
		assertEquals("1", psf.find("1"));
		
		
		/*
		psf = new ProofSoFar();
		psf.add("1", "a");
		assertEquals("a", psf.find("1"));
		psf.add("1.1", "b");
		assertEquals("b", psf.find("1.1"));
		psf.add("1.1.2", "asdf");
		assertEquals("asdf", psf.find("1.1.2"));
		psf.add("2", "This is a test");
		assertEquals("This is a test", psf.find("2"));
		System.out.println(psf.toString());
		
		psf = new ProofSoFar();
		psf.add("1", "a");
		psf.add("2", "b");
		psf.add("1.1", "c");
		assertEquals("a", psf.find("1"));
		String s =("1    a" + "\n" + "1.1    c" + "\n" + "2    b \n");
		System.out.println(psf.toString());
		assertEquals(s, psf.toString());
		*/
		
	}
}