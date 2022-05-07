import java.io.FileNotFoundException;
import java.io.IOException;

import ca.pfv.spmf.tools.dataset_converter.TransactionDatabaseConverter;

public class Q4_Application {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		Q3_Refactored pattern_mining = new Q3_Refactored();
		
		//Specify input dataset
		String input_dataset = "C:/Users/dchie/Documents/1. MIT 2020/2. SEM2-2021/3. Large scale Data mining - IFN645/Week 8 - Advanced pattern mining/dataset/Pasquier05_dataset.txt";
				
		//Specify output files
		String output_path = "C:/Users/dchie/Documents/1. MIT 2020/2. SEM2-2021/3. Large scale Data mining - IFN645/Week 8 - Advanced pattern mining/dataset/Q4/";
				
		// Specify minimum support
		double minsup = 0.3;
		
//		pattern_mining.doFP_FPGrowth(input_dataset, output_path, minsup);
		
		patternsInMushroom(minsup);
		
	}
	
	public static void patternsInMushroom (double minsup) throws IOException {
		//Specify input dataset
		String input_file = "C:/Users/dchie/Documents/1. MIT 2020/2. SEM2-2021/3. Large scale Data mining - IFN645/Week 8 - Advanced pattern mining/dataset/mushroom.arff";
		
		//Specify output files
		String input_converted = "C:/Users/dchie/Documents/1. MIT 2020/2. SEM2-2021/3. Large scale Data mining - IFN645/Week 8 - Advanced pattern mining/dataset/mushroom_converted.txt";
		String output_path = "C:/Users/dchie/Documents/1. MIT 2020/2. SEM2-2021/3. Large scale Data mining - IFN645/Week 8 - Advanced pattern mining/dataset/Q4/";
		
		//Create an object of TransactionDatabaseConverter
		TransactionDatabaseConverter converter = new TransactionDatabaseConverter();
		
		// Convert the ARFF file to text file
		converter.convertARFFandReturnMap(input_file, input_converted, Integer.MAX_VALUE);
	
		Q3_Refactored pattern_mining = new Q3_Refactored();
		pattern_mining.doFP_FPGrowth(input_converted, output_path, minsup);
		pattern_mining.doFCP_AprioriClose(input_converted, output_path, minsup);
		pattern_mining.doFCP_Charm(input_converted, output_path, minsup);
		pattern_mining.doFCPGen_Zart(input_converted, output_path, minsup);
		pattern_mining.doMaxFp(input_converted, output_path, minsup);
	}

}
