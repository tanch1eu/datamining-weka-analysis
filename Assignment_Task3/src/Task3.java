import java.util.Random;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.HoeffdingTree;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.stemmers.LovinsStemmer;
import weka.core.stopwords.Rainbow;
import weka.core.tokenizers.AlphabeticTokenizer;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class Task3 {

	public static void main(String[] args) throws Exception {
		// Loading the dataset
		DataSource source = new DataSource("C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\datasets\\News.arff");
		Instances data = source.getDataSet();
		
		Task3 task3 = new Task3();
		task3.doFilteredClassification(data);
	}
	
	public void doFilteredClassification(Instances data) throws Exception {
		// Set the class index of the dataset
		data.setClassIndex(1);
		
		// Create a StringToWordVector filter
		StringToWordVector swFilter = new StringToWordVector();
		
		//Specify range of attributes to act on
		swFilter.setAttributeIndices("first-last");
		
		// Configure the filter and run classification
		swFilter.setIDFTransform(true);
		swFilter.setTFTransform(false);
		swFilter.setNormalizeDocLength(
		new SelectedTag(StringToWordVector.FILTER_NORMALIZE_ALL,
		StringToWordVector.TAGS_FILTER));
		swFilter.setOutputWordCounts(true);
		swFilter.setStemmer(new LovinsStemmer());
		swFilter.setStopwordsHandler(new Rainbow());
		swFilter.setTokenizer(new AlphabeticTokenizer());
		swFilter.setWordsToKeep(100);
		
		FilteredClassifier fc = new FilteredClassifier();
		fc.setFilter(swFilter);
			
		doAllClassifications(fc, data);
	}
	
	public void doClassification(FilteredClassifier filteredClassifier , AbstractClassifier classifier, Instances data, String Name) throws 
	Exception {
		// Create classifier and run filtered classification
		// Time efficiency indicator included.
		long startTime = System.nanoTime();
		filteredClassifier.setClassifier(classifier);
		filteredClassifier.buildClassifier(data);
		Evaluation eval = new Evaluation(data); // generate evaluation data
		
		eval.crossValidateModel(filteredClassifier, data, 10, new Random(1)); // generate evaluation result
		long endTime = System.nanoTime();
		double time = endTime - startTime;
		time = time/ 1000000000;
		System.out.println("------------" + Name + "------------");
		System.out.println(eval.toSummaryString());
		System.out.println(eval.toClassDetailsString());
		System.out.println("Executing time of the algorithm- " + Name + ": " + time + " seconds\n");
	}
	
	public void doAllClassifications (FilteredClassifier filteredClassifier, Instances data) throws Exception {
		// Perform classification task for 4 algorithms: IBk, SMO, J48 and Hoeffding Tree.
		doClassification(filteredClassifier, new IBk(), data, "IBk");
		doClassification(filteredClassifier, new SMO(), data, "SMO");
		doClassification(filteredClassifier, new J48(), data, "J48");
		doClassification(filteredClassifier, new HoeffdingTree(), data, "Hoeffding Tree");
	}

}
