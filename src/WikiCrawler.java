/**
 * @author Sander VanWilligen
 * @author Zackery Lovisa
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * This class will have methods that can be used to crawl Wiki
 */
public class WikiCrawler {

	public static final String BASE_URL = "https://en.wikipedia.org";

	private String seedURL; //relative address of the seed URL within the wiki domain.
	private int max; //maximum number of pages to be crawled.
	private String fileName; //name of the file the graph will be written to.
	private int connections = 0;
	/*
	 * Constructor for the WikiCrawler Class
	 */

	public WikiCrawler(String seedURL, int max, String fileName) {
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
	public ArrayList<String> extractLinks(String doc) {

		ArrayList<String> results = new ArrayList<String>();
		String pattern = "href=\"\\/wiki\\/(.[^#:]*?)\"";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(doc);
		while (m.find()) {
			results.add("/wiki/" + m.group(1));
		}

		return results;
	}

	/*
	 * This method should construct the web graph over following pages: Consider the first
	 * max many pages that are visited when you do a BFS with seedUrl. Your program should construct
	 * the web graph only over those pages. and writes the graph to the file fileName.
	 */
	public void crawl() {
		//Create a regex pattern to match everything after the first <p> tag only
		String pattern = "<p>(.*)";
		Pattern r = Pattern.compile(pattern);
		String url = BASE_URL + seedURL;
		Matcher m;
		ArrayList<String> nodes = new ArrayList<String>();
		ArrayList<String> edges = new ArrayList<String>();
		
		
		nodes.add(seedURL);
		
		ArrayList<String> temp;

		//Navigate the continuously appended list of links.
		for (int i = 0; i < nodes.size(); i++) {
			seedURL = nodes.get(i);
			url = BASE_URL + seedURL;
			m = r.matcher(getHTML(url));
			m.find();
			//Extract all the links for the new page
			temp = extractLinks(m.group(0));
			String link;
			//Navigate those links
			for (int j = 0; j < temp.size(); j++) {
				
				link = temp.get(j);
				if (!seedURL.equals(link)) {
					//Add all the new links to Nodes (If we haven't hit the max)
					if (!nodes.contains(link) && nodes.size() < max)
						nodes.add(link);
					//Add all the new directions to Edges (if it exists in Nodes)
					if (nodes.contains(link) && !edges.contains(seedURL + " " + link))
						edges.add(seedURL + " " + link);
				}
			}
		}
		
		PrintWriter out = null;
		try {
			out = new PrintWriter(fileName);
			out.println(max);
			for(int i = 0; i < edges.size(); i++){
				out.println(edges.get(i));
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private String getHTML(String urlPath) {
		try {
			//create the base node url, based on the constructor
			URL url = new URL(urlPath);

			//create an input stream, and read every line of the page into a string
			InputStream is = url.openStream();
			//Timeout for 3 seconds after every 100 requests.
			connections++;
			if(connections >= 99){
				TimeUnit.SECONDS.sleep(3);
				connections = 0;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			String page = "";
			while (line != null) {
				line = br.readLine();
				page = page + line;
			}
			return page;

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return "failed";
		}
	}

}
