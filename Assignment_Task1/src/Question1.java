import ca.pfv.spmf.algorithms.frequentpatterns.apriori.AlgoApriori;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import ca.pfv.spmf.tools.dataset_converter.TransactionDatabaseConverter;

public class Question1 {

	public static void main(String[] args) throws Exception {
		// Specify input-output files
		String inputFile = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\datasets\\bank.arff";
		String inputFile_converted = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\outputs\\bank_converted.txt";
		String output_path = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\outputs\\Q1\\output_";
		
		// Create object of TransactionDatabaseConverter
		TransactionDatabaseConverter converter = new TransactionDatabaseConverter();
		
		// Convert the ARFF file to text file
		converter.convertARFFandReturnMap(inputFile, inputFile_converted, Integer.MAX_VALUE);
		
		// Specify minimum support
		double minSupport = 0.6;
				
		// Create question object, and do 2 algorithms
		Question1 pattern_mining = new Question1();
		pattern_mining.doApriori(inputFile_converted, output_path, minSupport);
		pattern_mining.doFPGrowth(inputFile_converted, output_path, minSupport);
	}
	
	public void doApriori (String input_dataset, String output_path, double minsup) throws Exception {
		// Specify output file
		String output = output_path + "fp_Apriori.txt";

		// Create object of Apriori pattern mining algorithm
		AlgoApriori algo_Apri = new AlgoApriori();		

		// Run algorithm to generate patterns
		algo_Apri.runAlgorithm(minsup, input_dataset, output);
		algo_Apri.printStats();
	}
	
	public void doFPGrowth (String input_dataset, String output_path, double minsup) throws Exception {
		// Specify output file
		String output = output_path + "fp_FPGrowth.txt";

		// Create object of FP-Growth pattern mining algorithm
		AlgoFPGrowth algo_FPGrowth = new AlgoFPGrowth();		

		// Run algorithm to generate patterns
		algo_FPGrowth.runAlgorithm(input_dataset, output, minsup);
		algo_FPGrowth.printStats();
	}

}
