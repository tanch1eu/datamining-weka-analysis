import java.io.IOException;

import ca.pfv.spmf.algorithms.associationrules.TopKRules_and_TNR.AlgoTopKRules;
import ca.pfv.spmf.algorithms.associationrules.TopKRules_and_TNR.Database;
import ca.pfv.spmf.algorithms.frequentpatterns.apriori.AlgoApriori;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import ca.pfv.spmf.tools.dataset_converter.TransactionDatabaseConverter;
import ca.pfv.spmf.tools.resultConverter.ResultConverter;

public class Q6_TopK_Rules {

	public static void main(String[] args) throws IOException {
		String input_file = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\mushroom.arff";
		String output_fp_Apriori = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q6\\mushroom_fp_Apriori.txt";
		String output_fp_Fpt = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q6\\mushroom_fp_Fpt.txt";
		String output_rules = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q6\\mushroom_topK_rules.txt";

		// Specify a file for the converted file
		String input_converted = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q6\\mushroom.txt";
		
		// Specify the final output files
		String final_output_fp_apri = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q6\\final_mushroom_apriori.txt";
		String final_output_fp_fpt = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q6\\final_mushroom_fpt.txt";
		String final_output_rules = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q6\\final_mushroom_topK_rules.txt";

		//Create an object of TransactionDatabaseConverter
		TransactionDatabaseConverter converter = new TransactionDatabaseConverter();		
		// Convert the ARFF file to text file
		converter.convertARFFandReturnMap(input_file, input_converted, Integer.MAX_VALUE);
		

		// Create objects of pattern mining algorithms
		AlgoApriori algo_Apri = new AlgoApriori();
		AlgoFPGrowth algo_FPGrowth = new AlgoFPGrowth();
		AlgoTopKRules algo_rules = new AlgoTopKRules();
		
		
		// Set a maximum size for patterns
		//algo_Apri.setMaximumPatternLength(5);
		//algo_FPGrowth.setMaximumPatternLength(5);
		
		
		// Specify minimum support
		double minsup = 0.6;
		
		algo_Apri.runAlgorithm(minsup, input_converted, output_fp_Apriori);
		algo_Apri.printStats();
		algo_FPGrowth.runAlgorithm(input_converted, output_fp_Fpt, minsup);
		algo_FPGrowth.printStats();
		
		// To generate rules, we need to create a database from the converted text input
		Database db = new Database();
		db.loadFile(input_converted);
		// Top-k and minimum confidence
		int top_k = 100;
		double minconf = 0.5;
		
		algo_rules.runAlgorithm(top_k, minconf, db);
		algo_rules.printStats();
		algo_rules.writeResultTofile(output_rules);
		
		//Convert outputs to include the original names for the items
		ResultConverter output_converter = new ResultConverter();
		output_converter.convert(input_converted, output_fp_Apriori, final_output_fp_apri, null);
		output_converter.convert(input_converted, output_fp_Fpt, final_output_fp_fpt, null);
		output_converter.convert(input_converted, output_rules, final_output_rules, null);

	}

}
