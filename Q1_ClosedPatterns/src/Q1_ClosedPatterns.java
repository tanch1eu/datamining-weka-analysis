import java.io.FileNotFoundException;
import java.io.IOException;

import ca.pfv.spmf.algorithms.frequentpatterns.apriori_close.AlgoAprioriClose;
import ca.pfv.spmf.algorithms.frequentpatterns.charm.AlgoCharm_Bitset;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPClose;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPMax;
import ca.pfv.spmf.input.transaction_database_list_integers.TransactionDatabase;
import ca.pfv.spmf.tools.resultConverter.ResultConverter;


public class Q1_ClosedPatterns {

	public static void main(String[] args) throws Exception {
		String input_dataset = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 8 - Advanced pattern mining\\dataset\\Pasquier05_dataset.txt";
		// Specify output files
		String output_path = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 8 - Advanced pattern mining\\dataset\\Q1\\";
				
		String output_fp_Fpt = output_path + "fp_Fpt.txt";
		String output_fcp_Apriori = output_path + "fcp_Apriori.txt";
		String output_fcp_Fpt = output_path + "fcp_Fpt.txt";
		String output_fcp_Charm = output_path + "fcp_Charm.txt";
		
		// Specify minimum support
		double minsup = 0.3;
		
		// Create objects of pattern mining algorithms
		AlgoFPGrowth algo_FPGrowth = new AlgoFPGrowth();
		AlgoAprioriClose algo_AprioriClose = new AlgoAprioriClose();
		AlgoFPClose algo_FCP_Growth = new AlgoFPClose();
		AlgoCharm_Bitset algo_FCP_Charm = new AlgoCharm_Bitset();
		
		try {
			algo_FPGrowth.runAlgorithm(input_dataset, output_fp_Fpt, minsup);
			algo_FPGrowth.printStats();
			algo_AprioriClose.runAlgorithm(minsup, input_dataset, output_fcp_Apriori);
			algo_AprioriClose.printStats();
			algo_FCP_Growth.runAlgorithm(input_dataset, output_fcp_Fpt, minsup);
			algo_FCP_Growth.printStats();
			
			//AlgoCharm_Bitset requires transaction database
			TransactionDatabase tdb = new TransactionDatabase();
			tdb.loadFile(input_dataset);
			algo_FCP_Charm.runAlgorithm(output_fcp_Charm, tdb, minsup, false, 10000);
			algo_FCP_Charm.printStats();
			
			// Specify the final output files which include item names
			String final_output_fp_Fpt = output_path + "final_fp_Fpt.txt";
			String final_output_fcp_Apriori = output_path + "final_fcp_Apriori.txt";
			String final_output_fcp_Fpt = output_path + "final_fcp_Fpt.txt";
			String final_output_fcp_Charm = output_path + "final_fcp_Charm.txt";
			//Convert outputs to include item names
			ResultConverter output_converter = new ResultConverter();
			output_converter.convert(input_dataset, output_fp_Fpt, final_output_fp_Fpt, null);
			output_converter.convert(input_dataset, output_fcp_Apriori, final_output_fcp_Apriori, null);
			output_converter.convert(input_dataset, output_fcp_Fpt, final_output_fcp_Fpt, null);
			output_converter.convert(input_dataset, output_fcp_Charm, final_output_fcp_Charm, null);
			
			// call method doFpMax() to generate maximal patterns
			Q1_ClosedPatterns pattern_mining = new Q1_ClosedPatterns();
			pattern_mining.doMaxFp(input_dataset, output_path, minsup);
			
			} catch (FileNotFoundException e) {
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
		
		//Convert outputs to include item names
		ResultConverter output_converter = new ResultConverter();
		output_converter.convert(input_dataset, output, final_output, null);
		} 
		catch (IOException e) {
				e.printStackTrace();
		}
	}
}
