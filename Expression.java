public class Expression {
	//check if theorem name, or if a reason, etc. create an arraylist, if it's any of those words like "show" etc. return true;
	boolean valid; 	
	
	
	public Expression (String x){
		if (checkValidity(x)){
			valid = true;
		} else {
			valid = false;
		}
	}
	
	public Boolean checkValidity(String expression) {
		if (expression.charAt (0) != '('){
			//there are two cases if the expression, doesn't begin with a parenthesis: variable or ~variable
			if (expression.length() == 1){
				if (Character.isLetter(expression.charAt(0))){
					//checks variable
					return true;
				}
			} else if (expression.length() == 2){
				if (expression.charAt(0) == '~' && Character.isLetter(expression.charAt(1))){
					//checks ~variable
					return true;
				} 
			} else {
				return false;
			}
			
		} else {

			for (int k = 0; k < expression.length(); k++){
				if (k <= expression.length()-1 && k+1 <= expression.length()-1){
					if (expression.charAt(k) == '(' && expression.charAt(k+1) == ')'){
						return false;
					}
				}
			}
			
			
		
		//check that opening paranthesis is not right next to closing parenthesis
			
		}
	}
}
		/*

	            if(expr.length() == 3){
	                TreeNode left = new TreeNode (expr.charAt(0));
	                TreeNode operation = new TreeNode (expr.charAt(1));
	                TreeNode right = new TreeNode (expr.charAt(2));
	                return new TreeNode(operation, left, right);
	            }
	            //return new TreeNode(exprTreeHelper(expr), new TreeNode(expr.charAt(0)), new TreeNode(expr.charAt(expr.length())));
	        } else {
	            // expr is a parenthesized expression.
	            // Strip off the beginning and ending parentheses,
	            // find the main operator (an occurrence of + or * not nested
	            // in parentheses, and construct the two subtrees.
	            int nesting = 0;
	            int opPos = 0;
	            for (int k=1; k<expr.length()-1; k++) {
	                if (expr.charAt(k) == '(') {
	                    nesting++;
	                } if (expr.charAt(k) == ')') {
	                    nesting--;
	                } if (nesting == 0 && (expr.charAt(k) == '+' || expr.charAt(k) == '*')) {
	                    opPos = k;
	                }  
	            }
	            String opnd1 = expr.substring (1, opPos);
	            String opnd2 = expr.substring (opPos+1, expr.length()-1);
	            String op = expr.substring (opPos, opPos+1);
	            System.out.println ("expression = " + expr);
	            System.out.println ("operand 1  = " + opnd1);
	            System.out.println ("operator   = " + op);
	            System.out.println ("operand 2  = " + opnd2);
	            System.out.println ( );
	            return new TreeNode(op, exprTreeHelper(opnd1), exprTreeHelper(opnd2));
	        	}
	        return null;
	    }  */
