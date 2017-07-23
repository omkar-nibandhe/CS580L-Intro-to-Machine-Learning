/**
 * 
 */
package driver;

import java.io.File;
import java.io.FileNotFoundException;

import MLlogic.Logic;

/**
 * @author Omkar
 * @category machine learning
 */
public class Driver {

	/**
	 * arguments: inputFileName k randomSeed
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Driver d = new Driver();
		d.checkArguments(args);
		Logic l = new Logic(args[1], args[2], 0.00001);
		Float[] inputArray = l.readIntoArray(args[0]);
		l.generateRandom(args[1]);
		int i = 0;
		do {
			l.computeMatrix1(inputArray);
			l.computeMatrix2();
			l.computeMatrix3(inputArray);
			l.calculateNewKvalues();
			if (i < 5) {
				l.printKvalues(Integer.toString(i));
			}
			i++;
		} while (l.stoppingCondition());
		l.printKvalues(Integer.toString(i));
	}

	/**
	 * Checks the argument and exists with message if something is wrong.
	 * 
	 * @param args
	 */
	private void checkArguments(String[] args) {
		if (args.length != 3) {
			System.err.println("Invalid arguments\nExpected : driver inputFile.txt k randomSeed");
			System.exit(1);
		}
		File f = new File(args[0]);
		if (!f.exists()) {
			System.err.println(args[0] + " does not exist.");
			System.exit(1);
		}
		if (Integer.parseInt(args[1]) < 1) {
			System.err.println("Invalid value of k.");
			System.exit(1);
		}
	}
}
