import ca.pfv.spmf.algorithms.frequentpatterns.apriori_close.AlgoAprioriClose;
import ca.pfv.spmf.algorithms.frequentpatterns.charm.AlgoCharm_Bitset;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPClose;
import ca.pfv.spmf.input.transaction_database_list_integers.TransactionDatabase;
import ca.pfv.spmf.tools.resultConverter.ResultConverter;

public class Question4 {

	public static void main(String[] args) throws Exception {
		// Specify input file, output path
		String inputFile = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\outputs\\bank_converted.txt";
		String output_path = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\1. Major Assignment\\outputs\\Q4\\";
		
		// Specify minimum support, trying to run with different minimum support in range [0.3, 0.8]
		double minsup = 0.8;

		// Create question object and do 3 algorithms
		Question4 pattern_mining = new Question4();
		pattern_mining.doFCP_AprioriClose(inputFile, output_path, minsup);
		pattern_mining.doFCP_Growth(inputFile, output_path, minsup);
		pattern_mining.doFCP_Charm(inputFile, output_path, minsup);
	}
	
	public void doFCP_Growth(String input_dataset, String output_path, double minsup) throws Exception {
		// Specify the final output files which include item names
		String output_fcp_Fpt = output_path + "fcp_Fpt.txt";
		String final_output_fcp_Fpt = output_path + "final_fcp_Fpt.txt";
		
		AlgoFPClose algo_FCP_Growth = new AlgoFPClose();
		algo_FCP_Growth.runAlgorithm(input_dataset, output_fcp_Fpt, minsup);
		algo_FCP_Growth.printStats();
		
		//Convert outputs to include item names
		convert_output(input_dataset, output_fcp_Fpt, final_output_fcp_Fpt);		
	}
	
	public void doFCP_AprioriClose(String input_dataset, String output_path, double minsup) throws Exception {
		// Specify the final output files which include item names
		String output_fcp_Apriori = output_path + "fcp_Apriori.txt";
		String final_output_fcp_Apriori = output_path + "final_fcp_Apriori.txt";
		
		AlgoAprioriClose algo_AprioriClose = new AlgoAprioriClose();
		algo_AprioriClose.runAlgorithm(minsup, input_dataset, output_fcp_Apriori);
		algo_AprioriClose.printStats();
			
		//Convert outputs to include item names
		convert_output(input_dataset, output_fcp_Apriori, final_output_fcp_Apriori);
	}
	
	public void doFCP_Charm(String input_dataset, String output_path, double minsup) throws Exception {
		// Specify the final output files which include item names
		String output_fcp_Charm = output_path + "fcp_Charm.txt";
		String final_output_fcp_Charm = output_path + "final_fcp_Charm.txt";
				
		AlgoCharm_Bitset algo_FCP_Charm = new AlgoCharm_Bitset();
		
		//AlgoCharm_Bitset requires transaction database
		TransactionDatabase tdb = new TransactionDatabase();
		tdb.loadFile(input_dataset);
		
		algo_FCP_Charm.runAlgorithm(output_fcp_Charm, tdb, minsup, false, 10000);
		algo_FCP_Charm.printStats();
		
		//Convert outputs to include item names
		convert_output(input_dataset, output_fcp_Charm, final_output_fcp_Charm);
	}
	
	public void convert_output(String input_dataset, String output, String final_output) throws Exception {
		//Convert outputs to include the original names for the items
		ResultConverter output_converter = new ResultConverter();
		output_converter.convert(input_dataset, output, final_output, null);
	}
}
