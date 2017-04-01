
public class Main {

	public static void main(String[] args) {
		
		WikiCrawler w = new WikiCrawler("/wiki/Complexity_theory", 100, "WikiCS.txt");
		
		w.crawl();

	}

}
