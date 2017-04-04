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
		
		startTime = System.currentTimeMillis();
		System.out.println("Vertex with highest degree = /wiki/Computer_Science" + ", degree = " + G.outDegree("/wiki/Computer_Science"));
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println(totalTime);
		startTime = System.currentTimeMillis();
		System.out.println("Number of components = " + G.numComponents());
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println(totalTime);
		startTime = System.currentTimeMillis();
		System.out.println("Size of the largest component = " + G.largestComponent());
		endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		System.out.println(totalTime);
	}

}
