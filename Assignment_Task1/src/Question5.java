import ca.pfv.spmf.algorithms.associationrules.TopKRules_and_TNR.AlgoTopKClassRules;
import ca.pfv.spmf.algorithms.associationrules.TopKRules_and_TNR.Database;
import ca.pfv.spmf.tools.resultConverter.ResultConverter;

public class Question5 {

	public static void main(String[] args) throws Exception {
		// Specify input-output files
		String inputFile = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\outputs\\bank_converted.txt";
		String output_path_yes = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\outputs\\Q5\\yesClass";
		String output_path_no = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\outputs\\Q5\\noClass";
	
		// Specify minimum confidence
		double minconf = 0.3;
		
		// Top 10 most frequent association rules
		int top_k = 10;
		
		// The item to be used as consequent for generating rules
		// For yes-class (subscribed=yes), item = 42.
		int[] itemYesConsequent = new int[] {42};				
		// For no-class (subscribed=no), item = 11. 
		int[] itemNoConsequent = new int[] {11};
		
		// Call functions to generate top 10 association rules for both classes
		Question5 rule_mining = new Question5();
		rule_mining.doTopK_classRules(inputFile, output_path_yes, minconf, top_k, itemYesConsequent);
		rule_mining.doTopK_classRules(inputFile, output_path_no, minconf, top_k, itemNoConsequent);

	}
	
	public void doTopK_classRules (String input_dataset, String output_path, double minconf, int top_k, int[] itemToBeUsedAsConsequent) throws Exception {
		//Specify output files
		String output = output_path + "topK_classRules.txt" ;
		String final_output = output_path + "final_topK_classRules.txt" ;
		
		// To generate rules, we need to create a database from the input dataset
		Database db = new Database();
		db.loadFile(input_dataset);
		
		// Create an object of rule mining algorithm
		AlgoTopKClassRules topK_classRules = new AlgoTopKClassRules();
		
		// Generate association rules
		topK_classRules.runAlgorithm(top_k, minconf, db, itemToBeUsedAsConsequent);
		topK_classRules.printStats();
		topK_classRules.writeResultTofile(output);
		
		//Convert outputs to include the original names for the items
		convert_output(input_dataset, output, final_output);
	}
	public void convert_output(String input_dataset, String output, String final_output) throws Exception {
		//Convert outputs to include the original names for the items
		ResultConverter output_converter = new ResultConverter();
		output_converter.convert(input_dataset, output, final_output, null);
	}
}
