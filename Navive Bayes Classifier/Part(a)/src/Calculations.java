
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Omkar Nibandhe Mar 5, 2017 https://www.linkedin.com/in/omkarnibandhe
 */
public class Calculations {
	public void createProbability(Map<String, Integer> counts, Map<String, Integer> labels) {

		for (String newLabel : labels.keySet()) {
			if (!(newLabel.equalsIgnoreCase("yes") || newLabel.equalsIgnoreCase("no"))) {
				int yesValue = counts.get(newLabel + "_yes");
				int noValue = counts.get(newLabel + "_no");
				counts.putIfAbsent(newLabel + "_probability", yesValue + noValue);
			} else {
				int yesValue = counts.get("yes_yes");
				int noValue = counts.get("no_no");
				counts.putIfAbsent(newLabel + "_probability", yesValue + noValue);
			}
			// System.out.println(yesValue+noValue+" Value: "+newLabel);

		}
	}

	/**
	 * 
	 * @param counts
	 * @param labels
	 * @return
	 */
	public Map<String, Double> displayProbability(Map<String, Integer> counts, Map<String, Integer> labels) {
		Map<String, Double> probability = new HashMap<>();
		for (String newLabel : labels.keySet()) {
			if (!(newLabel.equalsIgnoreCase("yes") || newLabel.equalsIgnoreCase("no"))) {
				probability.put(newLabel + " | yes", ((double) counts.get(newLabel + "_yes") / counts.get("yes_yes")));

				probability.put(newLabel + " | no", ((double) counts.get(newLabel + "_no") / counts.get("no_no")));
			}
		}
		for (String newLabel : probability.keySet()) {
			System.out.println("P ( " + newLabel + " ) : " + probability.get(newLabel));
		}
		return probability;
	}

	/**
	 * 
	 * @param dataList
	 * @param counts
	 * @param label
	 */
	public void dataEntry(Map<String, Double> dataList, Map<String, Integer> counts, String label) {
		dataList.putIfAbsent(label + "_yes", (double) counts.get(label + "_yes") / counts.get("yes_yes"));
		dataList.putIfAbsent(label + "_no", (double) counts.get(label + "_no") / counts.get("no_no"));

	}

	/**
	 * 
	 * @param labels
	 * @return
	 */
	public static String[] inputValues(Map<String, Integer> labels) {
		String[] inputs = { " ", " ", " ", " " };
		InputStreamReader in = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(in);
		String newline = null;
		for (int i = 0; i < 4; i++) {
			try {
				newline = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			inputs[i] = newline.trim().toLowerCase();
			if (!labels.containsKey(inputs[i])) {
				System.out.println(inputs[i] + " label not found. Re-enter");
				i--;
			}

		}

		return inputs;
	}

	/**
	 * 
	 * @param counts
	 * @param labels
	 * @param probability
	 * @return
	 */
	public boolean myQuerry(Map<String, Integer> counts, Map<String, Integer> labels, Map<String, Double> probability) {
		for (String newString : labels.keySet()) {
			if (!(newString.equalsIgnoreCase("yes") | newString.equalsIgnoreCase("no")))
				System.out.print(newString + "\t");
		}
		String[] querryLabel = inputValues(labels);

		double[] yes_probability = new double[5];
		double[] no_probability = new double[5];

		for (int i = 0; i < yes_probability.length; i++) {
			yes_probability[i] = 1.0;
			no_probability[i] = 1.0;
		}
		double sum1 = 1.0, sum2 = 1.0;
		for (int i = 0; i < querryLabel.length; i++) {

			yes_probability[i] = probability.get(querryLabel[i] + " | yes");

			sum1 *= yes_probability[i];

			no_probability[i] = probability.get(querryLabel[i] + " | no");

			sum2 *= no_probability[i];

		}

		if (sum1 >= sum2) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * @param counts
	 * @param labels
	 */
	public void staticQuerry(Map<String, Integer> counts, Map<String, Integer> labels) {

		/*
		 * Outlook = rain, Temperature = cool, Humidity = high, and Wind =
		 * Strong
		 * 
		 * Hard-coded case.
		 */
		Map<String, Double> dataList = new HashMap<>();
		for (String newLabel : labels.keySet()) {
			dataEntry(dataList, counts, newLabel);
		}
		for (String newLabel : dataList.keySet()) {
			System.out.println("P ( " + newLabel + " ) = " + dataList.get(newLabel));
		}
		int x = counts.get("yes_yes");
		int y = counts.get("no_no");

		double yes_value = (dataList.get("rain_yes") * dataList.get("cool_yes") * dataList.get("high_yes")
				* dataList.get("strong_yes"));

		double no_value = (dataList.get("rain_no") * dataList.get("cool_no") * dataList.get("high_no")
				* dataList.get("strong_no"));
		System.out.println("P ( play tennis | yes ) = " + yes_value + " ( " + x + " ) / ( " + (x + y) + " )");
		System.out.println("P ( play tennis | no  ) = " + no_value + " ( " + y + ") / ( " + (x + y) + " )");

		yes_value = yes_value * x / (x + y);
		no_value = no_value * y / (x + y);

		if (yes_value > no_value) {
			System.out.println("P ( play tennis | yes ) = " + yes_value + "\n The event will happen.");
		} else {
			System.out.println("P ( play tennis | no ) = " + no_value + "\n The event will not happen.");
		}

	}
}