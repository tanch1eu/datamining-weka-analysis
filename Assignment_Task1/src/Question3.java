import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPMax;
import ca.pfv.spmf.tools.resultConverter.ResultConverter;

public class Question3 {

	public static void main(String[] args) throws Exception {
		// Specify input files and output path
		String input_classYes = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\outputs\\bank_yes_converted.txt";
		String input_classNo = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\outputs\\bank_no_converted.txt";	
		String output_path = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\outputs\\Q3\\";
		
		// Specify minimum support
		double minSupport = 0.5;
		
		// Create question object and run algorithm
		Question3 pattern_mining = new Question3();
		pattern_mining.doMaxFP(input_classYes, output_path + "classYes_", minSupport);
		pattern_mining.doMaxFP(input_classNo, output_path + "classNo_", minSupport);
	}
	
	public void doMaxFP (String input_dataset, String output_path, double minsup) throws Exception {
		// Specify output files
		String output = output_path + "fpMax.txt";
		String final_output = output_path + "final_fpMax.txt";

		// Object of AlgoFPMax
		AlgoFPMax algo_FpMax = new AlgoFPMax();
		algo_FpMax.runAlgorithm(input_dataset, output, minsup);
		algo_FpMax.printStats();	
		
		// Convert results to get final outputs
		convert_output(input_dataset, output, final_output);	
	}
	
	public void convert_output(String input_dataset, String output, String final_output) throws Exception {
		//Convert outputs to include the original names for the items
		ResultConverter output_converter = new ResultConverter();
		output_converter.convert(input_dataset, output, final_output, null);
	}
}
