import java.util.Iterator;

/**
 * @author Sander VanWilligen
 * @author Zackery Lovisa
 */

public class Main {

	public static void main(String[] args) {
	
		long startTime = System.currentTimeMillis();
		//WikiCrawler w = new WikiCrawler("/wiki/Computer_Science", 500, "WikiCS.txt");
		
		//w.crawl();
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		
		System.out.println(totalTime);
		
		GraphProcessor G = new GraphProcessor("wikiCS.txt");
		int outdegree = 0;
		String outVertex = "";
		Iterator<GraphNode> iter = G.graph.values().iterator();
		while(iter.hasNext()){
			String temp = iter.next().getNode();
			if(G.outDegree(temp) > outdegree){
				outVertex = temp;
				outdegree = G.outDegree(outVertex);
			}
		}
		System.out.println("Vertex with highest degree = " + outVertex + ", degree = " + outdegree);
		System.out.println("Number of components = " + G.numComponents());
		System.out.println("Size of the largest component = " + G.largestComponent());
		
	}

}
