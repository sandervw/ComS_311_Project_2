import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Sander VanWilligen
 * @author Zackery Lovisa
 */

/*
 * This class will have methods that can be used to crawl Wiki
 */
public class WikiCrawler {
	
	public static final String BASE_URL = "https://en.wikipedia.org";
	
	private String seedURL;		//relative address of the seed URL within the wiki domain.
	private int max;			//maximum number of pages to be crawled.
	private String fileName;	//name of the file the graph will be written to.
	
	/*
	 * Constructor for the WikiCrawler Class
	 */
	public WikiCrawler(String seedURL, int max, String fileName){
		this.seedURL = seedURL;
		this.max = max;
		this.fileName = fileName;
	}
	
	/*
	 * This method gets a string (that represents contents of a .html
	 * page as parameter. This method should return an array list (of Strings) consisting of links from doc.
	 * Type of this method is ArrayList<String>. You may assume that the html page is the source
	 * (html) code of a wiki page.
	 */
	public ArrayList<String> extractLinks(String doc){
		
		ArrayList<String> results = new ArrayList<String>();
		return results;
		//TODO
		
	}
	
	/*
	 * This method should construct the web graph over following pages: Consider the rst
	 * max many pages that are visited when you do a BFS with seedUrl. Your program should construct
	 * the web graph only over those pages. and writes the graph to the le fileName.
	 */
	public void crawl(){
		URL url;
		try {
			url = new URL(BASE_URL+seedURL);
			InputStream is = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			String page = "";
			while (line != null){
				line = br.readLine();
				page = page + line;
			}
			extractLinks(page);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
