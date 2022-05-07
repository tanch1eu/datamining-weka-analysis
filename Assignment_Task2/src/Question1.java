import java.text.DecimalFormat;
import java.util.Random;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.CostMatrix;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.meta.CostSensitiveClassifier;
import weka.classifiers.rules.OneR;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Question1 {

	public static void main(String[] args) throws Exception {		
		// Specify input file
		DataSource inputFile = new DataSource("C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\datasets\\bank.arff");
		
		// Get dataset from input file
		Instances data = inputFile.getDataSet();
		// System.out.println(data);
		data.setClass(data.attribute("subscribed"));
		
		// Create question object, do Cost Sensitive Analysis using AttributeSelectedClassifier 
		// and implement Cost Sensitive Classifier for the chosen 4 algorithms (J48, NaiveBayes, OneR, PART)
		Question1 CSA = new Question1();
		CSA.doCostSensitiveAnalysis(data);
		CSA.doCostSensitiveClassifiers(data);
		
	}
	
	public void doClassification (Instances input_dataset, AbstractClassifier classifier, ASEvaluation evaluator, ASSearch search) 
			throws Exception {
		AttributeSelectedClassifier asc = new AttributeSelectedClassifier();
		
		asc.setClassifier(classifier);
		asc.buildClassifier(input_dataset); // build classifier
		asc.setEvaluator(evaluator);
		asc.setSearch(search);
		
		System.out.println("Building dataset");
		
		// Cost-Sensitive evaluation with “subscribed = y” is more important 
		String matlab = "[0.0 1.0; 5.0 0.0]";
		CostMatrix matrix = CostMatrix.parseMatlab(matlab);

		Evaluation eval = new Evaluation(input_dataset, matrix); // generate evaluation data
		
		eval.crossValidateModel(asc, input_dataset, 10, new Random(1)); // generate evaluation result
		
		// Print results
		DecimalFormat df = new DecimalFormat("##.###");
		System.out.println("Correctly Classified Instances: " + df.format(eval.correct()/eval.numInstances()*100) + "%"); 
		System.out.println("Total cost: " + eval.totalCost()); 		
	}
	
	public void doCostSensitiveAnalysis (Instances data) throws Exception {
		// Set search method
		Ranker ranking = new Ranker();
		ranking.setNumToSelect(-1); // default setting in Weka section

		// Attribute Selection Classifier using 3 algorithms with InfoGain evaluator and Ranker search method
		System.out.println("-------J48-------");
		doClassification(data, new J48(), new InfoGainAttributeEval(), ranking);
		
		System.out.println("\n---Naive Bayes---");
		doClassification(data, new NaiveBayes(), new InfoGainAttributeEval(), ranking);
		
		System.out.println("\n------OneR------");
		doClassification(data, new OneR(), new InfoGainAttributeEval(), ranking);
		
		System.out.println("\n------PART------");
		doClassification(data, new PART(), new InfoGainAttributeEval(), ranking);
	}
		
	public void CostSensitiveClassifier (Instances input_dataset, AbstractClassifier classifier, Boolean minExpCost ) 
			throws Exception {
		
			CostSensitiveClassifier csc = new CostSensitiveClassifier();
			
			csc.setClassifier(classifier);
			csc.buildClassifier(input_dataset);
			csc.setMinimizeExpectedCost(minExpCost);

			// Cost-Sensitive evaluation with “subscribed = y” is more important 
			String matlab = "[0.0 1.0; 5.0 0.0]";
			CostMatrix matrix = CostMatrix.parseMatlab(matlab);
			csc.setCostMatrix(matrix);
			csc.getCostMatrix();
			
			Evaluation eval = new Evaluation(input_dataset, matrix); // generate evaluation data
			
			eval.crossValidateModel(csc, input_dataset, 10, new Random(1)); // generate evaluation result
			
			// Print results
			System.out.println("Total cost: " + eval.totalCost()); 		
		}
	
	public void doCostSensitiveClassifiers (Instances data) throws Exception {
		
		System.out.println("\nMinimize total cost using Cost Sensitive Classifiers");
		
		// with minimizeExpectCost = True, Cost-Sensitive Learning method 	
		System.out.println("\n>>> Cost-Sensitive Learning method, minimizeExpectCost = TRUE");
		
		System.out.println("\n-------J48-------");
		CostSensitiveClassifier(data, new J48(), true);
		
		System.out.println("\n---Naive Bayes---");
		CostSensitiveClassifier(data, new NaiveBayes(), true);
		
		System.out.println("\n------OneR------");
		CostSensitiveClassifier(data, new OneR(), true);
		
		System.out.println("\n------PART------");
		CostSensitiveClassifier(data, new PART(), true);
		
		// with minimizeExpectCost = False, Cost-Sensitive Classification method 
		System.out.println("\n>>> Cost-Sensitive Classification method, minimizeExpectCost = FALSE");
		System.out.println("-------J48-------");
		CostSensitiveClassifier(data, new J48(), false);
		
		System.out.println("\n---Naive Bayes---");
		CostSensitiveClassifier(data, new NaiveBayes(), false);
		
		System.out.println("\n------OneR------");
		CostSensitiveClassifier(data, new OneR(), false);
		
		System.out.println("\n------PART------");
		CostSensitiveClassifier(data, new PART(), false);
	}
}