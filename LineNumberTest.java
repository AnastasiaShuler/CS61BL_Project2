import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;


public class LineNumberTest extends TestCase {

	public void testNext(){
		LineNumber line = new LineNumber(true, false);
		assertEquals("1", line.next());
		assertEquals("2", line.next());
		line.setBeginProof(true);
		assertEquals("2.1", line.next());
		assertEquals("2.2", line.next());
		line.setBeginProof(true);
		assertEquals("2.2.1", line.next());
		line.setFinishProof(true);
		assertEquals("2.3", line.next());
		assertEquals("2.4", line.next());
		line.setFinishProof(true);
		assertEquals("3", line.next());
		assertEquals("4", line.next());
	}
	
	public void testPrev() {
		LineNumber line = new LineNumber(true, false);
		assertEquals("1", line.next());
		assertEquals("2", line.next());
		assertEquals("1", line.prev());
		assertEquals("2", line.next());
		line.setBeginProof(true);
		line.next();
		assertEquals("2.1", line.prev());
		line.next();
		assertEquals("2.1", line.prev());
		assertEquals("2.2", line.next());
		assertEquals("2.1", line.prev());
		assertEquals("2.2", line.next());
		assertEquals("2.1", line.prev());
		assertEquals("2.1", line.prev());
		line.next();
		line.next();
		line.next();
		assertEquals("2.3", line.prev());
		assertEquals("2.2", line.prev());
		
	}
	
	public void testGetLastNum() {
		LineNumber line = new LineNumber(true, false);
		assertEquals("1", line.next());
		assertEquals("2", line.next());
		assertEquals(2, line.getLastNum());
		line.setBeginProof(true);
		assertEquals("2.1", line.next());
		assertEquals("2.2", line.next());
		assertEquals(2, line.getLastNum());
		line.setBeginProof(true);
		assertEquals("2.2.1", line.next());
		assertEquals(1, line.getLastNum());
		line.setFinishProof(true);
		assertEquals("2.3", line.next());
		assertEquals(3, line.getLastNum());
		assertEquals("2.4", line.next());
		assertEquals(4, line.getLastNum());
		line.setFinishProof(true);
		assertEquals("3", line.next());
		assertEquals("4", line.next());
		assertEquals(4, line.getLastNum());
	}
	
	public void testReferences(){
		assertTrue(LineNumber.isValidReference("1", "1.1.1"));
		assertFalse(LineNumber.isValidReference("2", "1.1.1"));
		assertTrue(LineNumber.isValidReference("1", "3.2.4"));
		assertTrue(LineNumber.isValidReference("2", "3.2.4"));
		assertTrue(LineNumber.isValidReference("3.1", "3.2.4"));
		assertTrue(LineNumber.isValidReference("3.2.1", "3.2.4"));
		assertTrue(LineNumber.isValidReference("3.2.2", "3.2.4"));
		assertTrue(LineNumber.isValidReference("3.2.3", "3.2.4"));
		assertFalse(LineNumber.isValidReference("1.1", "3.2.4"));
	}
}
