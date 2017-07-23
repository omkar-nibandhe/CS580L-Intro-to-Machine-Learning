/**
 * 
 */
package MLlogic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Omkar
 *
 */
public class Logic {

	private float minValue = Float.MAX_VALUE;
	private float maxValue = Float.MIN_VALUE;
	private int k = 0;
	private int numberOfElements = 0;
	Random randomVal = null;
	private int variance = 5;
	private long seedValue = 0;
	private float accuracyValue = 0;
	private float[][] matrix1;
	private float[][] matrix2;
	private float[][] matrix3;
	private float[] randomK;
	private float[] newK;

	/**
	 * Parameterised Constructor
	 * 
	 * @param kIn
	 * @param seedValueIn
	 * @param accuracyValueIn
	 */
	public Logic(String kIn, String seedValueIn, double accuracyValueIn) {
		k = Integer.parseInt(kIn);
		seedValue = Long.parseLong(seedValueIn);

		randomVal = new Random(seedValue);
		accuracyValue = (float) accuracyValueIn;
		randomK = new float[k];
		newK = new float[k];

	}

	/**
	 * Getter method for private data member.
	 * 
	 * @return number of elements
	 */
	public int getnumberOfElements() {
		return numberOfElements;
	}

	/**
	 * read data into matrix from file.
	 * 
	 * @param inputFileName
	 * @return data from file in input matrix
	 * @throws FileNotFoundException
	 */
	public Float[] readIntoArray(String inputFileName) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(inputFileName));
		List<Float> temps = new ArrayList<Float>();
		float token;
		while (sc.hasNext()) {
			token = sc.nextFloat();
			temps.add(token);
			if (token < minValue) {
				minValue = token;
			}
			if (token > maxValue) {
				maxValue = token;
			}
		}
		System.out.println(minValue +" "+ maxValue);
		sc.close();
		Float[] inputArray = temps.toArray(new Float[0]);
		buildMatrices(inputArray.length);
		return inputArray;
	}

	/**
	 * init all matrix sizes
	 * 
	 * @param length
	 */
	private void buildMatrices(int length) {
		numberOfElements = length;
		matrix1 = new float[length + 1][k + 1];
		matrix2 = new float[length + 1][k + 1];
		matrix3 = new float[length + 1][k + 1];
	}

	/**
	 * generate random numbers
	 * 
	 * @param kIn
	 */
	public void generateRandom(String kIn) {
		ArrayList<Float> list = new ArrayList<Float>();
		int count = 0;
		float temp;
		float mean = (minValue + maxValue) / 2;
		while (count != k) {
			temp = (float) (mean + (float) randomVal.nextGaussian() * variance);
			if (temp > minValue && temp < maxValue && !list.contains(temp)) {
				list.add(temp);
				count++;
			}
		}
		for (int i = 0; i < Integer.parseInt(kIn); i++) {
			randomK[i] = list.get(i);
		}
	}

	/**
	 * method to compute the 1st matrix.
	 * 
	 * @param inputArray
	 */
	public void computeMatrix1(Float[] inputArray) {
		float sumofCol[] = new float[k];

		for (int i = 0; i < numberOfElements; i++) {
			float sumofRow = 0;
			for (int j = 0; j < k; j++) {
				matrix1[i][j] = getExponentialValue(inputArray[i], randomK[j]);
				sumofRow = sumofRow + matrix1[i][j];
				sumofCol[j] = sumofCol[j] + matrix1[i][j];
			}
			matrix1[i][k] = sumofRow;
		}
		for (int j = 0; j < k; j++) {
			matrix1[numberOfElements][j] = sumofCol[j];

		}

	}

	/**
	 * method to get the e^value
	 * 
	 * @param float1
	 * @param f
	 * @return
	 */
	private float getExponentialValue(float float1, float f) {
		float temp = (float) Math.exp((-0.5) * Math.pow(float1 - f, 2));
		return temp;
	}

	/**
	 * method to calculate the summation AiXi
	 */
	public void computeMatrix2() {
		float sumofCol[] = new float[k];

		for (int i = 0; i < numberOfElements; i++) {
			float sumofRow = 0;
			for (int j = 0; j < k; j++) {
				matrix2[i][j] = (matrix1[i][j] / addfloats(matrix1[i]));
				sumofRow = (sumofRow + matrix2[i][j]);
				sumofCol[j] = (sumofCol[j] + matrix2[i][j]);
			}
			matrix2[i][k] = sumofRow;
		}
		for (int j = 0; j < k; j++) {
			matrix2[numberOfElements][j] = sumofCol[j];
		}

	}

	/**
	 * returns added float values
	 * 
	 * @param fs
	 * @return
	 */
	private float addfloats(float[] fs) {
		float temp = 0;
		for (int i = 0; i < k; i++) {
			temp = (temp + fs[i]);
		}
		return temp;
	}

	/**
	 * method to compute the final matrix summation AiXi / summation Ai
	 * 
	 * @param inputArray
	 */
	public void computeMatrix3(Float[] inputArray) {
		float sumofCol[] = new float[k];
		for (int i = 0; i < numberOfElements; i++) {
			for (int j = 0; j < k; j++) {
				matrix3[i][j] = (matrix2[i][j] * inputArray[i]);
				sumofCol[j] = (sumofCol[j] + matrix3[i][j]);
			}
		}
		for (int j = 0; j < k; j++) {
			matrix3[numberOfElements][j] = sumofCol[j];

		}
	}

	/**
	 * compute the new K values
	 */
	public void calculateNewKvalues() {
		for (int i = 0; i < k; i++) {

			newK[i] = (addfloatColumn(matrix3, i) / addfloatColumn(matrix2, i));

		}
	}

	/**
	 * column summation
	 * 
	 * @param matrix
	 * @param columnNumber
	 * @return
	 */
	private float addfloatColumn(float[][] matrix, int columnNumber) {
		float temp = 0;
		for (int i = 0; i < numberOfElements; i++) {
			temp = (temp + matrix[i][columnNumber]);
		}
		return temp;
	}

	/**
	 * main condition to stop the do while loop. checks with the accuracy
	 *
	 * @return
	 */
	public boolean stoppingCondition() {

		for (int i = 0; i < k; i++) {
			if (Math.abs(newK[i] - randomK[i]) > accuracyValue) {
				resetKvalues();
				return true;
			}
		}

		return false;
	}

	/**
	 * if not accurate then reset the value of K
	 */
	private void resetKvalues() {
		for (int i = 0; i < k; i++) {
			randomK[i] = newK[i];
			newK[i] = 0;
		}
	}

	/**
	 * print out function
	 * 
	 * @param s
	 */
	public void printKvalues(String s) {
		System.out.print("iteration :" + s);
		for (int i = 0; i < k; i++) {
			System.out.print("\tk[ " + i + " ] = " + newK[i]);
		}
		System.out.println();
	}

}
