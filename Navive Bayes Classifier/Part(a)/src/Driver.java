import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author Omkar Nibandhe
 * Mar 5, 2017 	
 * https://www.linkedin.com/in/omkarnibandhe
 */
public class Driver {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FileProcessor fp = new FileProcessor();
		fp.checker(args);
		Map<String, Integer> counts = new HashMap<>();
		Map<String, Integer> labels = new HashMap<>();

		int numberOfEntities = fp.readInputFile(args[0], counts, labels);
		if (numberOfEntities > 0) {
			Calculations calc = new Calculations();
			calc.createProbability(counts, labels);
			Map<String, Double> probability = calc.displayProbability(counts, labels);

			System.out.println("Enter labels from the following to calculate probability:");
			if (calc.myQuerry(counts, labels, probability)) {
				System.out.println("Yes. Tennis will be played.");
			} else {
				System.out.println("No. Tennis won't be played.");
			}
		}
	}
}
