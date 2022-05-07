import java.util.Random;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GainRatioAttributeEval;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.OneRAttributeEval;
import weka.attributeSelection.Ranker;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Filters {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			DataSource source = new DataSource("C:\\Program Files\\Weka-3-8-5\\data\\ionosphere.arff");
			
			Filters filters = new Filters();
			
			Instances data = source.getDataSet();
			//	System.out.println(data);
			
			data.setClass(data.attribute("class"));
			
			BestFirst bf = new BestFirst();
			NaiveBayes nb = new NaiveBayes();
			filters.doFilterClassification(data, nb, new CfsSubsetEval(), bf);
			
			WrapperSubsetEval wrapper = new WrapperSubsetEval();
			wrapper.setClassifier(new NaiveBayes());
			filters.doFilterClassification(data, nb, wrapper, bf);
			
			Ranker ranking = new Ranker();
			ranking.setNumToSelect(7);
			filters.doFilterClassification(data, nb, new OneRAttributeEval(), ranking);
			filters.doFilterClassification(data, nb, new InfoGainAttributeEval(), ranking);
			filters.doFilterClassification(data, nb, new GainRatioAttributeEval(), ranking);
			
		}catch (Exception e)
		{
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	public static void doFilterClassification (Instances data ) 
			throws Exception 
	{
		AttributeSelectedClassifier asc = new AttributeSelectedClassifier();
		
		asc.buildClassifier(data); // build classifier
		
		Evaluation eval = new Evaluation(data); // generate evaluation data
		
		eval.crossValidateModel(asc, data, 10, new Random(1)); // generate evaluation result
		
		System.out.println("ASC cross validation " + eval.correct()/eval.numInstances()); // print result
	}
	
	public static void doFilterClassification (Instances data, AbstractClassifier classifier, 
			ASEvaluation evaluator, ASSearch search ) 
			throws Exception 
	{
		AttributeSelectedClassifier asc = new AttributeSelectedClassifier();
		
		asc.setClassifier(classifier);
		asc.buildClassifier(data); // build classifier
		asc.setEvaluator(evaluator);
		asc.setSearch(search);
		
		System.out.println("Building dataset");
		
		Evaluation eval = new Evaluation(data); // generate evaluation data
		
		eval.crossValidateModel(asc, data, 10, new Random(1)); // generate evaluation result
		
		System.out.println("ASC cross validation " + eval.correct()/eval.numInstances()); // print result
	}
}
