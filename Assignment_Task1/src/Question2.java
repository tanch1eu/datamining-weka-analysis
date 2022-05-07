import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import ca.pfv.spmf.tools.dataset_converter.TransactionDatabaseConverter;
import ca.pfv.spmf.tools.resultConverter.ResultConverter;

public class Question2 {

	public static void main(String[] args) throws Exception {
		// Specify input files, output path
		String input_Yes = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\datasets\\bank_yes.arff";
		String input_No = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\datasets\\bank_no.arff";
		String input_Yes_converted = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\outputs\\bank_yes_converted.txt";
		String input_No_converted = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\outputs\\bank_no_converted.txt";
		String output_path = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\outputs\\Q2\\";

		//Create an object of TransactionDatabaseConverter
		TransactionDatabaseConverter converter = new TransactionDatabaseConverter();		
		// Convert the ARFF file to text file
		converter.convertARFFandReturnMap(input_Yes, input_Yes_converted, Integer.MAX_VALUE);
		converter.convertARFFandReturnMap(input_No, input_No_converted, Integer.MAX_VALUE);

		// Specify minimum support
		double minSupport = 0.4;
		
		// Create question object and run algorithm
		Question2 pattern_mining = new Question2();
		pattern_mining.doFPGrowth(input_Yes_converted, output_path + "classYes_", minSupport);
		pattern_mining.doFPGrowth(input_No_converted, output_path + "classNo_", minSupport);

	}
	
	public void doFPGrowth (String input_dataset, String output_path, double minsup) throws Exception {
		// Specify output files
		String output = output_path + "FpG.txt";
		String final_output = output_path + "final_FpG.txt";

		// Create object of FP-Growth pattern mining algorithm
		AlgoFPGrowth algo_FPGrowth = new AlgoFPGrowth();
		
		// Set size 3 for pattern item sets
		algo_FPGrowth.setMaximumPatternLength(3);
		algo_FPGrowth.setMinimumPatternLength(3);		

		// Run algorithm to generate patterns
		algo_FPGrowth.runAlgorithm(input_dataset, output, minsup);
		algo_FPGrowth.printStats();
		
		// Convert results to get final outputs
		convert_output(input_dataset, output, final_output);	
	}
	
	public void convert_output(String input_dataset, String output, String final_output) throws Exception {
		//Convert outputs to include the original names for the items
		ResultConverter output_converter = new ResultConverter();
		output_converter.convert(input_dataset, output, final_output, null);
	}

}
