import java.util.Random;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaClassifiersEfficiency {

	public static void main(String[] args) {
		doAllClassification();
	}
	
	public static void doClassification(AbstractClassifier classifier, Instances data, String Name) throws 
	Exception {
		classifier.buildClassifier(data); // build classifier
		Evaluation eval = new Evaluation(data); // generate evaluation data
		
		eval.crossValidateModel(classifier, data, 10, new Random(1)); // generate evaluation result
		System.out.println(eval.correct()/eval.numInstances() + " for " + Name);
	}
	
	public static void doAllClassification() {
		try {
			long startSource = System.nanoTime();
			DataSource source = new DataSource("C:\\Program Files\\Weka-3-8-5\\data\\diabetes.arff");
			long endSource = System.nanoTime();
			double elapsedSource = endSource - startSource;
			elapsedSource = elapsedSource/ 1000000000;
			System.out.println("Inputing time from source: " + elapsedSource + " seconds\n");
			
			long startData = System.nanoTime();
			Instances data = source.getDataSet();
			data.setClass(data.attribute("class"));
	//		System.out.println(data);
			long endData = System.nanoTime();
			double elapsedData = endData - startData;
			elapsedData = elapsedData/ 1000000000;
			System.out.println("Inputing time from data: " + elapsedData + " seconds\n");
			
			// Call method doClassification() to complete multiple classifications
			
			long startJ48 = System.nanoTime();
			doClassification(new J48(), data, "J48");
			long endJ48 = System.nanoTime();
			double elapsedJ48 = endJ48 - startJ48;
			elapsedJ48 = elapsedJ48/ 1000000000;
			System.out.println("Executing time from J48: " + elapsedJ48 + " seconds\n");
			
			long startOneR = System.nanoTime();
			doClassification(new OneR(), data, "OneR");
			long endOneR = System.nanoTime();
			double elapsedOneR = endOneR - startOneR;
			elapsedOneR = elapsedOneR/ 1000000000;
			System.out.println("Executing time from OneR: " + elapsedOneR + " seconds\n");
			
			long startIBk = System.nanoTime();
			doClassification(new IBk(), data, "IBK");
			long endIBk = System.nanoTime();
			double elapsedIBk = endIBk - startIBk;
			elapsedIBk = elapsedIBk/ 1000000000;
			System.out.println("Executing time from IBk: " + elapsedIBk + " seconds\n");
			
			long startSMO = System.nanoTime();
			doClassification(new SMO(), data, "SMO");
			long endSMO = System.nanoTime();
			double elapsedSMO = endSMO - startSMO;
			elapsedSMO = elapsedSMO/ 1000000000;
			System.out.println("Executing time from SMO: " + elapsedSMO + " seconds\n");
			
			long startNaiveBayes = System.nanoTime();
			doClassification(new NaiveBayes(), data, "Naive Bayes");
			long endNaiveBayes = System.nanoTime();
			double elapsedNaiveBayes = endNaiveBayes - startNaiveBayes;
			elapsedNaiveBayes = elapsedNaiveBayes/ 1000000000;
			System.out.println("Executing time from NaiveBayes: " + elapsedNaiveBayes + " seconds\n");
			
			} catch (Exception e) {
				System.out.println("Error");
				e.printStackTrace();
		}
	}

}
