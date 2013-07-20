import java.util.ArrayList;

public class LineNumber {
  private ArrayList<Integer> numbers;
	
	public LineNumber() {
		numbers = new ArrayList<Integer>();
	}
	
	public String toString() {
		String result = "";
		for(Integer num : numbers) {
			result = result + num + ".";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}
}
