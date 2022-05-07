import java.io.FileNotFoundException;
import java.io.IOException;

import ca.pfv.spmf.algorithms.frequentpatterns.apriori_close.AlgoAprioriClose;
import ca.pfv.spmf.algorithms.frequentpatterns.charm.AlgoCharm_Bitset;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPMax;
import ca.pfv.spmf.algorithms.frequentpatterns.zart.AlgoZart;
import ca.pfv.spmf.input.transaction_database_list_integers.TransactionDatabase;
import ca.pfv.spmf.tools.resultConverter.ResultConverter;

public class Q3_Refactored {

	public static void main(String[] args) throws IOException,FileNotFoundException  {
		
		//Specify input dataset
		String input_dataset = "C:/Users/dchie/Documents/1. MIT 2020/2. SEM2-2021/3. Large scale Data mining - IFN645/Week 8 - Advanced pattern mining/dataset/Pasquier05_dataset.txt";
		
		//Specify output files
		String output_path = "C:/Users/dchie/Documents/1. MIT 2020/2. SEM2-2021/3. Large scale Data mining - IFN645/Week 8 - Advanced pattern mining/dataset/Q3/";
		
		// Specify minimum support
		double minsup = 0.3;
		try {
			// call method doFpMax() to generate maximal patterns
			Q3_Refactored pattern_mining = new Q3_Refactored();
			pattern_mining.doMaxFp(input_dataset, output_path, minsup);
			
			// call method doFP_FPGrowth
			pattern_mining.doFP_FPGrowth(input_dataset, output_path, minsup);
			
			// call method doFCP_AprioriClose
			pattern_mining.doFCP_AprioriClose(input_dataset, output_path, minsup);
						
			// call method doFCP_Charm
			pattern_mining.doFCP_Charm(input_dataset, output_path, minsup);
			
			// call method doFCPGen_Zart
			pattern_mining.doFCPGen_Zart(input_dataset, output_path, minsup);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void doMaxFp(String input_dataset, String output_path, double minsup) {
		// Specify output files
		String output = output_path + "fpMax.txt";
		String final_output = output_path + "final_fpMax.txt";
		
		try {
		// Object of AlgoFPMax
		AlgoFPMax algo_FpMax = new AlgoFPMax();
		algo_FpMax.runAlgorithm(input_dataset, output, minsup);
		algo_FpMax.printStats();	
		
		convert_output(input_dataset, output, final_output);
		
		} 
		catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	public static void convert_output(String input_dataset, String output, String final_output) {
		//Convert outputs to include item names
		ResultConverter output_converter = new ResultConverter();
		try {
			output_converter.convert(input_dataset, output, final_output, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void doFP_FPGrowth(String input_dataset, String output_path, double minsup) throws IOException, FileNotFoundException {
		// Specify the final output files which include item names
		String output_fp_Fpt = output_path + "fp_Fpt.txt";
		String final_output_fp_Fpt = output_path + "final_fp_Fpt.txt";
		
		AlgoFPGrowth algo_FPGrowth = new AlgoFPGrowth();
		algo_FPGrowth.runAlgorithm(input_dataset, output_fp_Fpt, minsup);
		algo_FPGrowth.printStats();
			
		//Convert outputs to include item names
		convert_output(input_dataset, output_fp_Fpt, final_output_fp_Fpt);
	}
	
	public static void doFCP_AprioriClose(String input_dataset, String output_path, double minsup) throws IOException, FileNotFoundException {
		// Specify the final output files which include item names
		String output_fcp_Apriori = output_path + "fcp_Apriori.txt";
		String final_output_fcp_Apriori = output_path + "final_fcp_Apriori.txt";
		
		AlgoAprioriClose algo_AprioriClose = new AlgoAprioriClose();
		algo_AprioriClose.runAlgorithm(minsup, input_dataset, output_fcp_Apriori);
		algo_AprioriClose.printStats();
			
		//Convert outputs to include item names
		convert_output(input_dataset, output_fcp_Apriori, final_output_fcp_Apriori);
	}
	public static void doFCP_Charm(String input_dataset, String output_path, double minsup) throws IOException, FileNotFoundException {
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
	
	public static void doFCPGen_Zart(String input_dataset, String output_path, double minsup)
	{
	String output = output_path + "fcpGen_Zart.txt";
	String final_output = output_path + "final_fcpGen_Zart.txt";
	try {
		AlgoZart algo_FCP_Zart = new AlgoZart();
		
		//AlgoZart requires transaction database
		TransactionDatabase tdb = new TransactionDatabase();
		tdb.loadFile(input_dataset);
		
		TransactionDatabase tdb_new = new TransactionDatabase();
		tdb_new.loadFile(input_dataset);
		
		algo_FCP_Zart.runAlgorithm(tdb_new, minsup);
		algo_FCP_Zart.saveResultsToFile(output);
		algo_FCP_Zart.printStatistics();
		convert_output(input_dataset, output, final_output);
	} 
	catch (IOException e) {
			e.printStackTrace();
		}
	}
}
