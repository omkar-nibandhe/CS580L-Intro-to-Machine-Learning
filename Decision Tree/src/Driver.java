import java.util.HashMap;
import java.util.Map;

/*
 *	Driver class to calulate Gain Ratio
 *	and decide the first split.
 * @author Omkar Nibandhe
 * Mar 5, 2017 	
 * https://www.linkedin.com/in/omkarnibandhe
 */
public class Driver {

	public static void main(String[] args) {
		FileProcessor fp = new FileProcessor();
		fp.checker(args);
		Map<String, Integer> counts = new HashMap<>();
		Map<String, Integer> labels = new HashMap<>();
		int numberOfEntities = fp.readInputFile(args[0], counts, labels);
		if (numberOfEntities > 0) {
			Calculations calc = new Calculations();
			calc.go(numberOfEntities, counts);
		}
	}
}
