public class Format {
	
	/*
	 * This class checks user input for overall validity. 
	 * For example, input beginning with "mp" must have
	 * four sections (mp, line number, line number, expression).
	 */

	public boolean checkFormat (String [ ] parts) {
		
		if (parts.length == 1) {
			/*
			 * if only 1 "part," then let it be checked by Expression.
			 */
			return true;
		}
		
		if (parts.length > 1) {
			/*
			 * if there are more than one parts in an expression, 
			 * the first part must be a keyword.
			 */
			if (parts[0].equals("mp") || parts[0].equals("co") || 
				parts[0].equals("ic") || parts[0].equals("show") || 
				parts[0].equals("assume")) { /* || !parts[0].equals("TheoremName" how to handle cases of theorem names?*/
				return true;
			}
			
			if (parts[0].equals("mp") || parts[0].equals("co")) {
				/*
				 * These two keywords should have the same format of
				 * "mp/co" [line number] [line number] [expression],
				 * so we can compound these into one check
				 */
				if (parts.length == 4) {
					return true;
				/*	
				 * Initially, I wanted to check the validity of each 
				 * section of the user input; however, I don't know if
				 * this is practical/possible.
				 * 
				 * parts[1] != VALID LINE NUMBER
				 || parts[2] != VALID LINE NUMBER
				 || parts[3] != VALID EXPRESSION) 
					return false;*/
				}
			}
			
			if (parts[0].equals("ic")) {
				if (parts.length == 3) {
					return true;
				}
			}
			
			if (parts[0].equals("show") 
			 || parts[0].equals("assume")
			 /*|| parts[0].equals("Theorem name") either check for 
			 existing theorem, or for just a string*/) {
				if (parts.length == 2) {
					return true;
				}
			}
		}
		return false;
	}
}
