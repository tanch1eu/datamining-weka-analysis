import java.util.Random;

import weka.classifiers.CostMatrix;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class WekaClassifiersEffectiveness {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
			WekaClassifiersEffectiveness wCE = new WekaClassifiersEffectiveness();
			wCE.doClassification();
			
	}
	
	public void doClassification() throws Exception {
		DataSource src = new DataSource("C:\\Program Files\\Weka-3-8-5\\data\\diabetes.arff");
		Instances data = src.getDataSet();
		data.setClass(data.attribute("class"));
		//System.out.println(data);
		
		NaiveBayes nb = new NaiveBayes();
		nb.buildClassifier(data);
		
//		System.out.println("Classification Accuracy: ");
//		for (int i=1; i<10; i++) {
//			Evaluation eval = new Evaluation(data);
//			Random r = new Random(i);
//			eval.crossValidateModel(nb, data, 10, r);
//			System.out.println(eval.correct() / eval.numInstances());		
//			}
		String matlab = "[0.0 1.0; 5.0 0.0]";
		CostMatrix matrix = CostMatrix.parseMatlab(matlab);
		
		System.out.println("Classification Accuracy and Cost");
		for(int i =1; i< 10; i++) {
			Evaluation eval2 = new Evaluation(data, matrix);
			Random r = new Random(i);
			eval2.crossValidateModel(nb, data, 10, r);
			System.out.println(eval2.correct() / eval2.numInstances() + " " + eval2.totalCost());
		}
		
	}
}
