import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.HoeffdingTree;
import weka.core.Instances;
import weka.datagenerators.classifiers.classification.RandomRBF;

public class IncrementalClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			IncrementalClass incremental = new IncrementalClass();
			
			Instances data = incremental.dataGeneration();	
			data.setClass(data.attribute("class"));
//			System.out.print(data);
			
			
//			Hoeffding Tree
			HoeffdingTree hTree = new HoeffdingTree();
			hTree.buildClassifier(data);
			Evaluation hTreeEval = new Evaluation(data);
			hTreeEval.crossValidateModel(hTree, data, 10, new Random(1));
			System.out.println("Hoeffding Tree " + hTreeEval.correct()/hTreeEval.numInstances());
			
//			iBK 
			IBk ibk = new IBk();
			ibk.buildClassifier(data);
			Evaluation ibkEval = new Evaluation(data);
			ibkEval.crossValidateModel(ibk, data, 10, new Random(1));
			System.out.println("IBk " + ibkEval.correct()/ibkEval.numInstances());
			
//			Naive Bayes
			NaiveBayes naiveBayes = new NaiveBayes();
			naiveBayes.buildClassifier(data); 
			Evaluation naiveBayesEval = new Evaluation(data);
			naiveBayesEval.crossValidateModel(naiveBayes, data, 10, new Random(1));
			System.out.println("Naive Bayes " + naiveBayesEval.correct()/naiveBayesEval.numInstances());
			
			
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	public Instances dataGeneration () throws Exception {
		RandomRBF rbf = new RandomRBF();
		rbf.setNumExamples(100000);
		
		Instances ins = rbf.defineDataFormat();
		ins = rbf.generateExamples();
		
		return ins;		
	}

}
