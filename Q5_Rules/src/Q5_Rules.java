import java.io.IOException;

import ca.pfv.spmf.algorithms.associationrules.agrawal94_association_rules.AlgoAgrawalFaster94;
import ca.pfv.spmf.algorithms.frequentpatterns.apriori.AlgoApriori;
import ca.pfv.spmf.patterns.itemset_array_integers_with_count.Itemsets;
import ca.pfv.spmf.tools.dataset_converter.TransactionDatabaseConverter;
import ca.pfv.spmf.tools.resultConverter.ResultConverter;

public class Q5_Rules {

	public static void main(String[] args) throws IOException {

		String input_file = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\diabetes.arff";
		String output_fp_Apriori = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q5\\diabetes_fp_Apriori.txt";
		String output_rules = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q5\\diabetes_faster_rules.txt";

		// Specify a file for the converted file
		String input_converted = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q5\\diabetes.txt";
		
		//Create an object of TransactionDatabaseConverter
		TransactionDatabaseConverter converter = new TransactionDatabaseConverter();
				
		// Convert the ARFF file to text file
		converter.convertARFFandReturnMap(input_file, input_converted, Integer.MAX_VALUE);
		
		// Create object of AlgoApriori
		AlgoApriori algo_Apri = new AlgoApriori();
		
		// Specify minimum support
		double minsup = 0.2;
		
		// Create an object of Itemsets, give a name, “diabetes”, to the pattern set
		Itemsets frequentItemsets = new Itemsets("diabetes");
		
		// Generate a set of patterns
		// Making the last argument ‘null’, the method will return the set of patterns.
		frequentItemsets = algo_Apri.runAlgorithm(minsup, input_converted, null);
		algo_Apri.printStats();
		
		// Create an object of AlgoAgrawalFaster94
		AlgoAgrawalFaster94 rules_Apri = new AlgoAgrawalFaster94();
		// Specify minimum confidence
		double minconf = 0.2;
		//get the number of transactions in the input dataset
		int dataset_size = algo_Apri.getDatabaseSize();
		System.out.println("dataset size=" + dataset_size);
		// Generate association rules using the AlgoAgrawalFaster94 algorithm
		rules_Apri.runAlgorithm(frequentItemsets, output_rules, dataset_size, minconf);
		rules_Apri.printStats();
		
		
		// Specify the final output files
		String final_output_fp_apri = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q5\\final_diabetes_apriori.txt";
		String final_output_rules = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q5\\final_diabetes_faster_rules.txt";
				
		//Convert outputs to include the original names for the items
		ResultConverter output_converter = new ResultConverter();
		output_converter.convert(input_converted, output_fp_Apriori, final_output_fp_apri, null);
		output_converter.convert(input_converted, output_rules, final_output_rules, null);	
		
	}

}
