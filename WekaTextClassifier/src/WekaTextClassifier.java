import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.stemmers.LovinsStemmer;
import weka.core.stopwords.Rainbow;
import weka.core.tokenizers.AlphabeticTokenizer;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class WekaTextClassifier {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DataSource source = new DataSource("C:/Users/dchie/Documents/1. MIT 2020/2. SEM2-2021/3. Large scale Data mining - IFN645/Week 9 - Text Mining/Movie/movie_reviews/movie_reviews.arff");
		Instances data = source.getDataSet();
		
		WekaTextClassifier wtc = new WekaTextClassifier();
		wtc.doFilteredClassification(data);
		
	}
	
	public void doFilteredClassification(Instances data) throws Exception {
		//Sets the class index of the dataset
		data.setClassIndex(1);
		// Create a StringToWordVector filter
		StringToWordVector swFilter = new StringToWordVector();
		//Specify range of attributes to act on
		swFilter.setAttributeIndices("first-last");
		
		// Configure the filter
		swFilter.setIDFTransform(true);
		swFilter.setTFTransform(true);
		swFilter.setNormalizeDocLength(
		new SelectedTag(StringToWordVector.FILTER_NORMALIZE_ALL,
		StringToWordVector.TAGS_FILTER));
		swFilter.setOutputWordCounts(true);
		swFilter.setStemmer(new LovinsStemmer());
		swFilter.setStopwordsHandler(new Rainbow());
		swFilter.setTokenizer(new AlphabeticTokenizer());
		swFilter.setWordsToKeep(200);
		
		FilteredClassifier fclassifier = new FilteredClassifier();
		fclassifier.setFilter(swFilter);
		
		NaiveBayes nb = new NaiveBayes();
		fclassifier.setClassifier(nb);
		fclassifier.buildClassifier(data);
		
		Evaluation eval = new Evaluation(data);
		eval.crossValidateModel(fclassifier, data, 10, new Random(1));
		System.out.println(eval.toSummaryString());
		System.out.println(eval.toClassDetailsString());
		
	}

}
