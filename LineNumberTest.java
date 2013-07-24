import static org.junit.Assert.*;

import org.junit.Test;


public class LineNumberTest {

	@Test
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
	}
}
