import weka.clusterers.AbstractClusterer;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.clusterers.HierarchicalClusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;

public class WekaClustering_iris {

	public static void main(String[] args) {
		try {
			DataSource source = new DataSource("C:\\Program Files\\Weka-3-8-5\\data\\iris.arff");
			Instances data = source.getDataSet();
//			System.out.println(data);
			
			data.setClassIndex(data.numAttributes()-1);
			weka.filters.unsupervised.attribute.Remove filter = new
			 weka.filters.unsupervised.attribute.Remove();
			filter.setAttributeIndices("" + (data.classIndex() + 1));
			filter.setInputFormat(data);
			Instances dataCluster = Filter.useFilter(data, filter);
			
//			WekaClustering_iris WekaCluster = new WekaClustering_iris();
//			
//			WekaCluster.doKmeans(data, dataCluster);
			System.out.println("*******KMEANS********");
			doKmeans(data, dataCluster);
			System.out.println("******* EM ********");
			doEM(data, dataCluster);
			System.out.println("*******Hierarchical********");
			doHierarchical(data, dataCluster);
			
//			SimpleKMeans kmeans = new SimpleKMeans();
//			kmeans.setNumClusters(3);
//			kmeans.buildClusterer(dataCluster);
//			
//			ClusterEvaluation kmeansEval = new ClusterEvaluation();
//			kmeansEval.setClusterer(kmeans);
//			kmeansEval.evaluateClusterer(data);
//			System.out.println(kmeansEval.clusterResultsToString());

	} catch (Exception e) {
		System.out.println("Error");
		e.printStackTrace();
	}
	}
	
	public static void doKmeans (Instances data, Instances dataClusterer) throws Exception {
		SimpleKMeans kmeans = new SimpleKMeans();
		kmeans.setNumClusters(3);
		kmeans.buildClusterer(dataClusterer);
		doEvaluation(kmeans,data);
	}
	
	public static void doEM (Instances data, Instances dataClusterer) throws Exception {
		EM em = new EM();
		em.setNumClusters(3);
		em.buildClusterer(dataClusterer);
		doEvaluation(em,data);
	}
	
	public static void doHierarchical (Instances data, Instances dataClusterer) throws Exception {
		HierarchicalClusterer hier = new HierarchicalClusterer();
		hier.setNumClusters(3);
		hier.setLinkType(new SelectedTag(2, HierarchicalClusterer.TAGS_LINK_TYPE));
		hier.buildClusterer(dataClusterer);
		doEvaluation(hier,data);
	}
	
	public static void doEvaluation (AbstractClusterer abs, Instances data) throws Exception {
		ClusterEvaluation kmeansEval = new ClusterEvaluation();
		kmeansEval.setClusterer(abs);
		kmeansEval.evaluateClusterer(data);
		System.out.println(kmeansEval.clusterResultsToString());
	}
}
