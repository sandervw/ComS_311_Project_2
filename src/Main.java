
public class Main {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		WikiCrawler w = new WikiCrawler("/wiki/Computer_Science", 100, "WikiCS.txt");
		
		w.crawl();
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		
		System.out.println(totalTime);

	}

}
