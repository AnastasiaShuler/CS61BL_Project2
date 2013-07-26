	public static boolean isValidReference(String refLine, String currLine) {
	  
		try {
		int firstrefNum = Integer.parseInt(refLine.substring(0, 1));
		int firstcurrNum = Integer.parseInt(currLine.substring(0, 1));
		if(firstrefNum != firstcurrNum) {
			if(firstrefNum > firstcurrNum || refLine.length() != 1) {
				return false;
			}
		}
		
		if(refLine.length() == currLine.length()) {
			for(int i = 0; i < currLine.length() - 1; i+= 2) {
				int refCurrNum = Integer.parseInt(refLine.substring(i, i + 1));
				int currCurrNum = Integer.parseInt(currLine.substring(i, i + 1));
				if(refCurrNum != currCurrNum) {
					return false;
				}
			}
		}
		
		if(refLine.length() > currLine.length()) {
			return false;
		}
		
		if(refLine.length() < currLine.length()) {
			for(int i = 0; i < refLine.length() - 1; i+= 2) {
				int refCurrNum = Integer.parseInt(refLine.substring(i, i + 1));
				int currCurrNum = Integer.parseInt(currLine.substring(i, i + 1));
				if(refCurrNum > currCurrNum) {
					return false;
				}
			}
		}

		int refLastNum = Integer.parseInt(refLine.substring(refLine.length() - 1));
		int currLastNum = Integer.parseInt(currLine.substring(currLine.length() - 1));;
		if(currLastNum < refLastNum) {
			return false;
		}
		
		return true;
  }
	catch (NumberFormatException e) {
		System.out.println("Error in line number");
	}
	return false;
	}
