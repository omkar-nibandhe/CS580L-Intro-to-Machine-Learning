
import java.util.Map;

public class Calculations {

	/*
	 * Calculates the log base 2 values checks for NaN values
	 * 
	 * @param numerator
	 * 
	 * @param denominator
	 * 
	 * @return
	 */
	public double logBase2(int numerator, int denominator) {
		double result = 0.0;
		if (numerator == 0 || denominator == 0) {
			return 0.0;
		}
		result = Math.log((double) numerator / denominator) / Math.log(2);
		return result;
	}

	/*
	 * Standard function to calculate the entropy
	 * 
	 * @param pos
	 * 
	 * @param neg
	 * 
	 * @param counts
	 * 
	 * @return
	 */
	public double calculateEntropy(int pos, int neg, Map<String, Integer> counts) {
		// System.out.println(pos+" "+neg+" ");
		double result = 0.0;
		int total = pos + neg;
		double p = (double) pos;
		double n = (double) neg;
		double t = p + n;
		result = -(p / t) * logBase2(pos, total) - (n / t) * logBase2(neg, total);
		return result;
	}

	/*
	 * Function to calculate the entropy by passing the label.
	 * 
	 * @param y
	 * 
	 * @param s
	 * 
	 * @param counts
	 * 
	 * @return
	 */
	public double calculateE(int y, String s, Map<String, Integer> counts) {
		int x = counts.get(s + "_yes") + counts.get(s + "_no");
		double results = 0.0;
		results = (double) ((double) x / (double) y) * (double) logBase2(x, y);
		return results;
	}

	/*
	 * Conditional Entropy calculator.
	 * 
	 * @param subSample
	 * 
	 * @param numberofEntities
	 * 
	 * @param counts
	 * 
	 * @return
	 */
	public double calculateConditionalEntropy(String subSample, int numberofEntities, Map<String, Integer> counts) {
		double results = 0.0;
		double x = (double) counts.get(subSample + "_yes") + counts.get(subSample + "_no");
		results = (x / (double) numberofEntities)
				* calculateEntropy(counts.get(subSample + "_yes"), counts.get(subSample + "_no"), counts);
		return results;
	}

	/*
	 * Calculates the main calculations.
	 * 
	 * @param numberOfEntities
	 * 
	 * @param counts
	 */
	public void go(int numberOfEntities, Map<String, Integer> counts) {
		Calculations calc = new Calculations();
		double sampleEntropy = calc.calculateEntropy(counts.get("yes_yes"), counts.get("no_no"), counts);

		double sunnyEntropy = calc.calculateConditionalEntropy("sunny", numberOfEntities, counts);
		double rainEntropy = calc.calculateConditionalEntropy("rain", numberOfEntities, counts);
		double outlookEntropy = calc.calculateConditionalEntropy("overcast", numberOfEntities, counts);

		double hotEntropy = calc.calculateConditionalEntropy("hot", numberOfEntities, counts);
		double mildEntropy = calc.calculateConditionalEntropy("mild", numberOfEntities, counts);
		double coolEntropy = calc.calculateConditionalEntropy("cool", numberOfEntities, counts);
		double highEntropy = calc.calculateConditionalEntropy("high", numberOfEntities, counts);
		double normalEntropy = calc.calculateConditionalEntropy("normal", numberOfEntities, counts);
		double strongEntropy = calc.calculateConditionalEntropy("strong", numberOfEntities, counts);
		double weakEntropy = calc.calculateConditionalEntropy("weak", numberOfEntities, counts);

		double gain_outlook = 0.0, gain_ratio_outlook = 0.0;
		double gain_temperature = 0.0, gain_ratio_temperature = 0.0;
		double gain_humidity = 0.0, gain_ratio_humidity = 0.0;
		double gain_wind = 0.0, gain_ratio_wind = 0.0;

		gain_outlook = (sampleEntropy - sunnyEntropy - rainEntropy - outlookEntropy);

		gain_temperature = (sampleEntropy - hotEntropy - mildEntropy - coolEntropy);

		gain_humidity = (sampleEntropy - highEntropy - normalEntropy);

		gain_wind = (sampleEntropy - strongEntropy - weakEntropy);

		/*
		 * 
		 * Gain Ratio Calculations
		 * 
		 */
		sunnyEntropy = calculateE(numberOfEntities, "sunny", counts);
		rainEntropy = calculateE(numberOfEntities, "rain", counts);
		outlookEntropy = calculateE(numberOfEntities, "overcast", counts);
		gain_ratio_outlook = -(gain_outlook / (sunnyEntropy + rainEntropy + outlookEntropy));
		System.out.println(gain_outlook + " GAIN outlook\n" + gain_ratio_outlook + " GAIN RATIO outlook\n");

		hotEntropy = calculateE(numberOfEntities, "hot", counts);
		mildEntropy = calculateE(numberOfEntities, "mild", counts);
		coolEntropy = calculateE(numberOfEntities, "cool", counts);
		gain_ratio_temperature = -(gain_temperature / (hotEntropy + mildEntropy + coolEntropy));
		System.out.println(
				gain_temperature + " GAIN temperature\n" + gain_ratio_temperature + " GAIN RATIO temperature\n");

		highEntropy = calculateE(numberOfEntities, "high", counts);
		normalEntropy = calculateE(numberOfEntities, "normal", counts);
		gain_ratio_humidity = -(gain_humidity / (highEntropy + normalEntropy));
		System.out.println(gain_humidity + " GAIN humidity\n" + gain_ratio_humidity + " GAIN RATIO humidity\n");

		strongEntropy = calculateE(numberOfEntities, "strong", counts);
		weakEntropy = calculateE(numberOfEntities, "weak", counts);
		gain_ratio_wind = -(gain_wind / (strongEntropy + weakEntropy));
		System.out.println(gain_wind + " GAIN wind\n" + gain_ratio_wind + " GAIN RATIO wind\n");

		/*
		 * Final result printing the split value.
		 */
		String maxLabel = "Outlook";
		double maxValue = gain_ratio_outlook;

		if (maxValue < gain_ratio_humidity) {
			maxValue = gain_ratio_humidity;
			maxLabel = "humidity";
		}
		if (maxValue < gain_ratio_temperature) {
			maxValue = gain_ratio_temperature;
			maxLabel = "temperature";
		}
		if (maxValue < gain_ratio_wind) {
			maxValue = gain_ratio_wind;
			maxLabel = "wind";
		}

		System.out.println(maxLabel + " : " + maxValue + " is the first split.");
	}
}
