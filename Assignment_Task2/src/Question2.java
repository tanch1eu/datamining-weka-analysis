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
import weka.classifiers.rules.PART;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;

public class Question2 {

	public static void main(String[] args) throws Exception {
		// Specify input file
		DataSource inputFile = new DataSource("C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\datasets\\bank.arff");
		
		// Get dataset from input file
		Instances data = inputFile.getDataSet();
		
		// Set number of attributes = 4, so that we need to reduce the attributes by 6
		data.setClassIndex(data.numAttributes() - 6);
		weka.filters.unsupervised.attribute.Remove filter = new weka.filters.unsupervised.attribute.Remove();
		filter.setAttributeIndices("1,2,3,4,5,6");
		filter.setInputFormat(data);
		Instances dataFilter = Filter.useFilter(data, filter);
		// System.out.println(dataCluster);
		dataFilter.setClass(dataFilter.attribute("subscribed"));
		
		// Create question object, do Cost Sensitive Analysis for the chosen 4 algorithms (J48, NaiveBayes, OneR, PART)
		Question2 CSA = new Question2();
		CSA.doCostSensitiveAnalysis(dataFilter);

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
		DecimalFormat df = new DecimalFormat("##.##");
		System.out.println("Correctly Classified Instances: " + df.format(eval.correct()/eval.numInstances()*100) + "%"); 
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
		
		System.out.println("\n------PART------");
		doClassification(data, new PART(), new InfoGainAttributeEval(), ranking);
	}

}
