import java.io.IOException;

import ca.pfv.spmf.algorithms.frequentpatterns.apriori.AlgoApriori;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import ca.pfv.spmf.tools.dataset_converter.TransactionDatabaseConverter;
import ca.pfv.spmf.tools.resultConverter.ResultConverter;

public class Q4_ARFF {

	public static void main(String[] args) throws IOException {
		String input_file = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\diabetes.arff";
		String output_fp_Apriori = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\diabetes_fp_Apriori.txt";
		String output_fp_Fpt = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\diabetes_fp_Fpt.txt";
		
		// Specify a file for the converted file
		String input_converted = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\diabetes.txt";

		//Create an object of TransactionDatabaseConverter
		TransactionDatabaseConverter converter = new TransactionDatabaseConverter();
		
		// Convert the ARFF file to text file
		converter.convertARFFandReturnMap(input_file, input_converted, Integer.MAX_VALUE);
		
		// Create objects of pattern mining algorithms
		AlgoApriori algo_Apri = new AlgoApriori();
		AlgoFPGrowth algo_FPGrowth = new AlgoFPGrowth();
		
		// Set a maximum size for patterns
		algo_Apri.setMaximumPatternLength(5);
		algo_FPGrowth.setMaximumPatternLength(5);
		
		// Specify minimum support
		double minsup = 0.1;
		
		// Run algorithms to generate patterns
		algo_Apri.runAlgorithm(minsup, input_converted, output_fp_Apriori);
		algo_Apri.printStats();
		algo_FPGrowth.runAlgorithm(input_converted, output_fp_Fpt, minsup);
		algo_FPGrowth.printStats();
		
		// Specify the final output files
		String final_output_fp_apri = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\final_diabetes_apriori.txt";
		String final_output_fp_fpt = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\final_diabetes_fpt.txt";
		
		//Convert outputs to include the original names for the items
		ResultConverter output_converter = new ResultConverter();
		output_converter.convert(input_converted, output_fp_Apriori, final_output_fp_apri, null);
		output_converter.convert(input_converted, output_fp_Fpt, final_output_fp_fpt, null);
	}

}
