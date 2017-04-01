import java.io.*;
import java.util.*;

//http://www.geeksforgeeks.org/strongly-connected-components/

/**
 * @author Sander VanWilligen
 * @author Zackery Lovisa
 */

/**
 * 
 * GraphProcessor that will read a graph stored in
 * a file, implements Strongly Connected Components (SCC) algorithm discussed in lectures, build
 * appropriate data structures to answer following queries efficiently:
 *
 */
public class GraphProcessor {
	
	private int numVertices;
	private Map<Integer, GraphNode> graph;
	
	/*
	 * Contructor for the GraphProcessor class
	 */
	public GraphProcessor(String graphData){
		
		numVertices = 0;
		graph = new HashMap<Integer, GraphNode>();
		constructGraph(graphData);
		
	}
	
	/*
	 * Return the out degree of the specified vertex
	 */
	public int outDegree(String v){
		
		int hashValue = v.hashCode();
		int degree = graph.get(hashValue).getConnections().size();
		return degree;
		
	}
	
	/*
	 * Returns true if u and v belong to the same SCC; otherwise 
	 * returns false.
	 */
	public boolean sameComponent(String u, String v){
		
		//TODO
		return true;
		
	}
	
	/*
	 * Return all the vertices that belong to the same Strongly Connected
	 * Component of v (including v). Returns an arraylist of strings
	 */
	public ArrayList<String> componentVertices(String v){
		
		//TODO
		ArrayList<String> results = new ArrayList<String>();
		return results;
		
	}
	
	/*
	 * Returns the size of the largest component.
	 */
	public int largestComponent(){
		
		//TODO
		return 0;
		
	}
	
	/*
	 * Returns the number of strongly connect components.
	 */
	public int numComponents(){
		
		//TODO
		return 0;
		
	}
	
	/*
	 * Returns the BFS path from u to v. This method returns an array
	 * list of strings that represents the BFS path from u to v. Note that this method must return an
	 * array list of Strings. First vertex in the path must be u and the last vertex must be v. If there is no
	 * path from u to v, then this method returns an empty list.
	 */
	public ArrayList<String> bfsPath(String u, String v){
		
		//TODO
		ArrayList<String> results = new ArrayList<String>();
		return results;
		
	}
	
	//function to create a graph inside of a hashmap using the input string
	private void constructGraph(String input){
		
		//add the lines to an array
		Scanner s = null;
		try {
			s = new Scanner(new File(input));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		s.nextLine();
		while (s.hasNextLine()){
			String temp = s.nextLine();
			String[] splited = temp.split("\\s+");
			int key0 = splited[0].hashCode();
			if(!graph.containsKey(key0)){
				GraphNode G = new GraphNode(splited[0]);
				graph.put(key0, G);
				numVertices++;
			}
			if(splited.length > 1){
				int key1 = splited[1].hashCode();
				GraphNode tempNode = graph.get(key0);
				tempNode.addConnection(splited[1]);
				if(!graph.containsKey(key1)){
					GraphNode G = new GraphNode(splited[1]);
					graph.put(key1, G);
					numVertices++;
				}
			}
			
		}
		s.close();
		
	}
	
	//a recursive function to print a DFS search starting from the specified node
	private void DFSUtil(String v, Map<Integer, Boolean> visited){
		
		// Mark the current node as visited and print it
		int hashValue = v.hashCode();
		visited.remove(hashValue);
		visited.put(hashValue, true);
		System.out.println(v + " ");
		
		String n;
		
		ArrayList<String> connections = graph.get(hashValue).getConnections();
        for(int i=0; i<connections.size(); i++){
        	n = connections.get(i);
        	int tempHash = n.hashCode();
        	if(visited.get(tempHash) == false){
        		DFSUtil(n,visited);
        	}
        }
		
	}
	
	private Map<Integer, GraphNode> getTranspose(){
		
		Map<Integer, GraphNode> graphT = new HashMap<Integer, GraphNode>();
		Iterator<GraphNode> g = graph.values().iterator();
		while(g.hasNext()){
			
			GraphNode tempNode = g.next();
			ArrayList<String> tempList = tempNode.getConnections();
			for(int i=0; i< tempList.size(); i++){
				
				String value0 = tempList.get(i);
				int key0 = value0.hashCode();
				if(!graphT.containsKey(key0)){
					GraphNode G = new GraphNode(value0);
					graphT.put(key0, G);
				}
				int key1 = tempNode.getNode().hashCode();
				GraphNode tempConnect = graph.get(key0);
				tempConnect.addConnection(tempNode.getNode());
				if(!graph.containsKey(key1)){
					GraphNode G = new GraphNode(tempNode.getNode());
					graph.put(key1, G);
				}
				
			}
			
		}
		
		return graphT;
		
	}
	
	

}
