//For data source
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instances;

import java.util.Random;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.J48;

public class FirstWekaClassifier {

	public static void main(String[] args) {
		try {
			/*
			 * DataSource source = new
			 * DataSource("C:\\Program Files\\Weka-3-8-5\\data\\glass.arff"); Instances data
			 * = source.getDataSet(); data.setClass(data.attribute("Type"));
			 * System.out.println(data);
			 * 
			 * J48 j48tree = new J48(); // new instance of tree
			 * j48tree.buildClassifier(data); // build classifier
			 * 
			 * Evaluation j48eval = new Evaluation(data);
			 * j48eval.crossValidateModel(j48tree, data, 10, new Random(1));
			 * System.out.println("J48 Pruned Tree " +
			 * j48eval.correct()/j48eval.numInstances());
			 * 
			 * J48 j48Utree = new J48(); // new instance of tree String[] j48Uoptions = new
			 * String[1]; j48Uoptions[0] = "-U"; j48Utree.setOptions(j48Uoptions); // set
			 * the options j48Utree.buildClassifier(data); // build classifier
			 * 
			 * Evaluation j48Ueval = new Evaluation(data); // Evaluate
			 * j48Ueval.crossValidateModel(j48Utree, data, 10, new Random(1));
			 * System.out.println("J48 Unpruned Tree " +
			 * j48Ueval.correct()/j48Ueval.numInstances());
			 * 
			 * // Using classifier OneR OneR oneR = new OneR(); oneR.buildClassifier(data);
			 * 
			 * Evaluation oneReval = new Evaluation(data); oneReval.crossValidateModel(oneR,
			 * data, 10, new Random(1)); System.out.println("oneR " +
			 * oneReval.correct()/oneReval.numInstances());
			 * 
			 * // Using classifier IBK IBk iBk = new IBk(); iBk.buildClassifier(data);
			 * Evaluation iBkeval = new Evaluation(data);
			 * 
			 * iBkeval.crossValidateModel(iBk, data, 10, new Random(1));
			 * System.out.println("IBk " + iBkeval.correct()/iBkeval.numInstances());
			 * 
			 * // Using classifier NaiveBayes NaiveBayes naiveBayes = new NaiveBayes();
			 * naiveBayes.buildClassifier(data); Evaluation naiveBayesEval = new
			 * Evaluation(data);
			 * 
			 * naiveBayesEval.crossValidateModel(naiveBayes, data, 10, new Random(1));
			 * System.out.println("Native Bayes " +
			 * naiveBayesEval.correct()/naiveBayesEval.numInstances());
			 * 
			 * // Using classifier SMO SMO smo = new SMO(); smo.buildClassifier(data);
			 * Evaluation smoEval = new Evaluation(data);
			 * 
			 * smoEval.crossValidateModel(smo, data, 10, new Random(1));
			 * System.out.println("SMO " + smoEval.correct()/smoEval.numInstances());
			 */
			
			doAllClassification();
			
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}		
	}	

	public static void doClassification(AbstractClassifier classifier, Instances data, String Name) throws 
	Exception {
		classifier.buildClassifier(data); // build classifier
		Evaluation eval = new Evaluation(data); // generate evaluation data
		// generate evaluation result
		eval.crossValidateModel(classifier, data, 10, new Random(1));
		// print result
		System.out.println(eval.correct()/eval.numInstances() + " for " + Name);
	}
	public static void doAllClassification() {
		try {
		DataSource source = new DataSource("C:\\Program Files\\Weka-3-8-5\\data\\glass.arff");
		Instances data = source.getDataSet();
		data.setClass(data.attribute("Type"));
//		System.out.println(data);
		
		// Call method doClassification() to complete multiple classifications
		doClassification(new J48(), data, "J48");
		doClassification(new OneR(), data, "OneR");
		doClassification(new IBk(), data, "IBK");
		doClassification(new SMO(), data, "SMO");
		doClassification(new NaiveBayes(), data, "Naive Bayes");
		} catch (Exception e) {
		System.out.println("Error");
		e.printStackTrace();
		}
	}
}

