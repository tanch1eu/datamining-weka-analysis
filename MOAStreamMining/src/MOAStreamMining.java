import moa.classifiers.trees.HoeffdingTree;
import moa.streams.generators.RandomRBFGenerator;
import com.yahoo.labs.samoa.instances.Instance;

public class MOAStreamMining {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			int numOfInstances = 100000;
			int numberOfCorrectSampleInstances = 0;
			int totalNumberOfSampleInstances = 0;
						
			RandomRBFGenerator rbfGenerator = new RandomRBFGenerator();
			rbfGenerator.prepareForUse();
			
			HoeffdingTree htree = new HoeffdingTree();
			htree.setModelContext(rbfGenerator.getHeader());
			htree.prepareForUse();
			
			boolean isTesting = true;
			while (rbfGenerator.hasMoreInstances() && totalNumberOfSampleInstances < numOfInstances)
			{
				Instance data =rbfGenerator.nextInstance().getData();
				if (isTesting)
				{
					if(htree.correctlyClassifies(data))
						numberOfCorrectSampleInstances++;
				}
				
				totalNumberOfSampleInstances++;
				
				htree.trainOnInstance(data);
			}
			
			double accuracy = 100* (double)numberOfCorrectSampleInstances/(double) totalNumberOfSampleInstances;
			System.out.println(totalNumberOfSampleInstances + " instances processed with "+ accuracy);
				
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

}
