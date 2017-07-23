import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Driver {
	/**
	 * 
	 * @param args
	 */
	static Evaluation eval = null;

	public static void main(String[] args) {
		argumentCheck(args);
		Instances trainInstances = null;
		trainInstances = loadDataSet(args, trainInstances, true);
		Instances testInstances = null;
		testInstances = loadDataSet(args, testInstances, false);
		// loadDataSet(args, trainInstances, testInstances);
		J48 J48model = new J48();
		NaiveBayes NBCmodel = new NaiveBayes();
		// Evaluation eval = null;
		Integer[] values = makeJ48Model(J48model, trainInstances);
		makeNBCModel(NBCmodel, trainInstances);
		printResults(J48model, values, NBCmodel, testInstances);
	}

	/**
	 * 
	 * @param j48model
	 * @param values
	 * @param nBCmodel
	 * @param testInstances
	 */
	private static void printResults(J48 j48model, Integer[] values, NaiveBayes nBCmodel, Instances testInstances) {
		Evaluation e1, e2;

		try {
			e1 = new Evaluation(testInstances);
			e2 = new Evaluation(testInstances);
			e1.crossValidateModel(j48model, testInstances, 10, new Random());
			e2.crossValidateModel(nBCmodel, testInstances, 10, new Random());
			System.out.println("------------------------------------------------------");
			System.out.println(" 1. Mean absolute error for J48 :" + e1.meanAbsoluteError());
			System.out.println(" 2. Mean absolute error for NBC :" + e2.meanAbsoluteError());
			if (e1.meanAbsoluteError() < e2.meanAbsoluteError()) {
				System.out.println(" 3. Prefer J48 ( C4.5 Decision Tree Algorithm)");
				System.out.println(" 4. MinNumObj :" + values[0] + " NumFolds : " + values[1]);
				System.out.println(e1.toSummaryString());
				System.out.println(" 5. Test Error : " + (e1.incorrect() / e1.numInstances()));
			} else {
				System.out.println(" 3. Prefer Naive Bayes Classifier");
				System.out.println(e2.toSummaryString());
				System.out.println(" 4. Test Error : " + (e2.incorrect() / e2.numInstances()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * 
	 * @param NBCmodel
	 * @param trainInstances
	 * @param eval
	 */
	private static void makeNBCModel(NaiveBayes NBCmodel, Instances trainInstances) {
		Evaluation eval;
		try {
			eval = new Evaluation(trainInstances);
			eval.crossValidateModel(NBCmodel, trainInstances, 10, new Random());
			NBCmodel.buildClassifier(trainInstances);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param j48model
	 * @param trainInstances
	 * @param eval
	 * @return
	 */
	private static Integer[] makeJ48Model(J48 j48model, Instances trainInstances) {

		int[] value = getRangeFromUser();
		Integer[] returnValues = new Integer[2];
		int minNumObj = 0, numFold = 0;
		double MAE = 999.99;
		try {
			eval = new Evaluation(trainInstances);
			j48model.setUnpruned(false);
			j48model.setReducedErrorPruning(true);
			for (int i = value[0]; i < value[1]; i++) {
				for (int j = value[2]; j < value[3]; j++) {
					j48model.setMinNumObj(i);
					j48model.setNumFolds(j);
					eval.crossValidateModel(j48model, trainInstances, 10, new Random());
					if (eval.meanAbsoluteError() < MAE) {
						minNumObj = i;
						numFold = j;
						MAE = eval.meanAbsoluteError();
					}
				}
			}
			j48model.setMinNumObj(minNumObj);
			j48model.setNumFolds(numFold);
			returnValues[0] = minNumObj;
			returnValues[1] = numFold;
			eval.crossValidateModel(j48model, trainInstances, 10, new Random());
			j48model.buildClassifier(trainInstances);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {

		}
		return returnValues;
	}

	/**
	 * 
	 * @return
	 */
	private static int[] getRangeFromUser() {
		int[] value = new int[4];
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter minNumObj (lower Bound):\t");
		value[0] = sc.nextInt();
		System.out.println("Enter minNumObj (Upper Bound):\t");
		value[1] = sc.nextInt();
		System.out.println("Enter numFold (lower Bound) :\t");
		value[2] = sc.nextInt();
		System.out.println("Enter numFold (Upper Bound):\t");
		value[3] = sc.nextInt();
		sc.close();
		// TODO Auto-generated method stub
		return value;
	}

	/**
	 * 
	 * @param args
	 * @param trainInstances
	 * @param testInstances
	 */
	private static Instances loadDataSet(String[] args, Instances instance, boolean isTraining) {
		BufferedReader trainingData = null;
		BufferedReader testData = null;
		try {
			if (isTraining) {
				trainingData = new BufferedReader(new FileReader(args[0]));
				instance = new Instances(trainingData);
				if (instance.classIndex() == -1)
					instance.setClassIndex(0);
			} else {
				testData = new BufferedReader(new FileReader(args[1]));
				instance = new Instances(testData);

				if (instance.classIndex() == -1)
					instance.setClassIndex(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			try {
				if (isTraining) {
					trainingData.close();
				} else {
					testData.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	/**
	 * 
	 * @param args
	 */
	private static void argumentCheck(String[] args) {
		System.out.println("Test");
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}
		if (args.length != 2) {
			System.out.println("Error in arguments.\nExpected: Driver.java trainingData.arff testData.arff");
			System.exit(1);
		}
		File trainingData = new File(args[0]);
		File testData = new File(args[1]);
		if (!trainingData.exists()) {
			System.out.println(args[0] + " does not exists.");
			System.exit(1);
		}
		if (!testData.exists()) {
			System.out.println(args[1] + " does not exists.");
			System.exit(1);
		}
	}
}
