import ca.pfv.spmf.algorithms.frequentpatterns.apriori.AlgoApriori;
import ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth.AlgoFPGrowth;

import java.io.IOException;
public class FirstPatternMiner {

	public static void main(String[] args) throws IOException {
		String input_file = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q2_input_itemNames.txt";
		String output_fp_Apriori = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q2_output_Apriori.txt";
		String output_fp_Fpt = "C:\\Users\\dchie\\Documents\\1. MIT 2020\\2. SEM2-2021\\3. Large scale Data mining - IFN645\\Week 7 - Association Mining\\dataset\\Q2_output_FP_growth.txt";
		
		// Specify minimum support
		double minsup = 0.4;
		
		// Create objects of pattern mining algorithms
		AlgoApriori algo_Apri = new AlgoApriori();
		AlgoFPGrowth algo_FPGrowth = new AlgoFPGrowth();
		
		// Set a maximum size for patterns
		algo_Apri.setMaximumPatternLength(3);
		algo_FPGrowth.setMaximumPatternLength(3);
		// Set a minimum size for patterns
		algo_FPGrowth.setMinimumPatternLength(1);
		
		algo_Apri.runAlgorithm(minsup, input_file, output_fp_Apriori);
		algo_Apri.printStats();
		
		algo_FPGrowth.runAlgorithm(input_file, output_fp_Fpt, minsup);
		algo_FPGrowth.printStats();
	}

}
