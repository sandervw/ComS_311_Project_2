import java.io.*;
import java.util.*;


/**
 * @author Sander VanWilligen
 * @author Zackery Lovisa
 */

/**
 * 
 * GraphProcessor that will read a graph stored in a file, implements Strongly
 * Connected Components (SCC) algorithm discussed in lectures, build appropriate
 * data structures to answer following queries efficiently:
 *
 */
public class GraphProcessor {

	private int numComponents = 0; //number of SCCs
	private int largestComponent = 0; //size of the largest SCC
	private Map<Integer, GraphNode> graph; //store the entire graph is a hashmap, to get constant access time
	//store the SCCs in an arraylist of hashmaps
	//Note that we do not store edges in these maps, since they are not needed for the methods specified
	private ArrayList<HashMap<Integer, String>> SCCs; 				

	/*
	 * Contructor for the GraphProcessor class
	 */
	public GraphProcessor(String graphData) {

		graph = new HashMap<Integer, GraphNode>();
		SCCs = new ArrayList<HashMap<Integer, String>>();
		//contruct a graph from the text file
		constructGraph(graphData);
		//create the SCCs using this method
		this.getSCC();

	}

	/*
	 * Return the out degree of the specified vertex
	 */
	public int outDegree(String v) {

		//compute the hash value for the node
		int hashValue = v.hashCode();
		//get the number of out connections to the node
		int degree = graph.get(hashValue).getConnections().size();
		return degree;

	}

	/*
	 * Returns true if u and v belong to the same SCC; otherwise returns false.
	 */
	public boolean sameComponent(String u, String v) {

		//Iterate through the while loop until the first node is found
		int i=0;
		while(SCCs.get(i).containsKey(u.hashCode())==false){
			i++;
		}
		//once the first node is found, if the SCC also contains the second node return true
		if(SCCs.get(i).containsKey(v.hashCode())) return true;
		//else return false
		return false;

	}

	/*
	 * Return all the vertices that belong to the same Strongly Connected
	 * Component of v (including v). Returns an arraylist of strings
	 */
	public ArrayList<String> componentVertices(String v) {

		//Iterate through the while loop until the first node is found
		int i=0;
		while(SCCs.get(i).containsKey(v.hashCode())==false){
			i++;
		}
		//Once the SCC is found, convert the map to a collection and add the values to an arraylist
		Iterator<String> temp = SCCs.get(i).values().iterator();
		ArrayList<String> results = new ArrayList<String>();
		while(temp.hasNext()){
			results.add(temp.next());
		}
		
		return results;

	}

	/*
	 * Returns the size of the largest component.
	 */
	public int largestComponent() {

		return this.largestComponent;

	}

	/*
	 * Returns the number of strongly connect components.
	 */
	public int numComponents() {

		return this.numComponents;

	}

	/*
	 * Returns the BFS path from u to v. This method returns an array list of
	 * strings that represents the BFS path from u to v. Note that this method
	 * must return an array list of Strings. First vertex in the path must be u
	 * and the last vertex must be v. If there is no path from u to v, then this
	 * method returns an empty list.
	 */
	public ArrayList<String> bfsPath(String u, String v) {
		
		//initialize arraylist of results to return
		ArrayList<String> results = new ArrayList<String>();
		
		//Store the results in a tree temporarily to get the shortest path easily
		//Store in hashmap for constant access time
		HashMap<Integer, TreeNode> tempTree = new HashMap<Integer, TreeNode>();
		TreeNode treeNode = new TreeNode(u);
		tempTree.put(treeNode.getData().hashCode(), treeNode);
		
		//initialize Q and visited for BFS
		//visited is stored in a hashmap to get constant search time
		Stack<String> Q = new Stack<String>();
		HashMap<Integer, String> visited = new HashMap<Integer, String>();
		//add u to visited
		Q.add(u);
		visited.put(u.hashCode(), u);
		//while Q is not empty
		while(!Q.isEmpty()){
			String tempString = Q.pop();
			
			treeNode = tempTree.get(tempString.hashCode());
			//if v is found, add it to results and return
			if(tempString.equals(v)){
				while(treeNode.hasParent()){
					results.add(treeNode.getData());
					treeNode = treeNode.getParent();
				}
				results.add(treeNode.getData());
				return results;
			}
			//convert the string to a node
			int tempHash = tempString.hashCode();
			GraphNode tempNode = graph.get(tempHash);
			ArrayList<String> connections = tempNode.getConnections();
			TreeNode child;
			for(int i=0; i<connections.size(); i++){
				if(!visited.containsKey(connections.get(i).hashCode())){
					Q.add(connections.get(i));
					visited.put(connections.get(i).hashCode(), connections.get(i));
					//add the node to the tree
					child = new TreeNode(connections.get(i));
					child.addParent(treeNode);
					tempTree.put(child.getData().hashCode(), child);
				}
			}
		}
		
		//if no path from u to v was found, return empty array
		return new ArrayList<String>();

	}

	/////////////////////////CUSTOM METHODS/////////////////////////

	// function to create a graph inside of a hashmap using the input string
	private void constructGraph(String input) {

		// add the lines to an array
		Scanner s = null;
		try {
			s = new Scanner(new File(input));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		s.nextLine();
		while (s.hasNextLine()) {
			String temp = s.nextLine();
			String[] splited = temp.split("\\s+");
			int key0 = splited[0].hashCode();
			if (!graph.containsKey(key0)) {
				GraphNode G = new GraphNode(splited[0]);
				graph.put(key0, G);
			}
			if (splited.length > 1) {
				int key1 = splited[1].hashCode();
				graph.get(key0).addConnection(splited[1]);
				if (!graph.containsKey(key1)) {
					GraphNode G = new GraphNode(splited[1]);
					graph.put(key1, G);
				}
			}

		}
		s.close();

	}

	// a recursive function to print a DFS search starting from the specified
	// node
	private void DFSUtil(String v, Map<Integer, GraphNode> dfsGraph, HashMap<Integer, String> results) {
		
		// Mark the current node as visited and print it
		int hashValue = v.hashCode();
		dfsGraph.get(hashValue).setVisited(true);
		results.put(v.hashCode(), v);

		String n;

		ArrayList<String> connections = dfsGraph.get(hashValue).getConnections();
		for (int i = 0; i < connections.size(); i++) {
			n = connections.get(i);
			int tempHash = n.hashCode();
			if (dfsGraph.get(tempHash).getVisited() == false) {
				DFSUtil(n, dfsGraph, results);
			}
		}

	}

	private Map<Integer, GraphNode> getTranspose() {

		Map<Integer, GraphNode> graphT = new HashMap<Integer, GraphNode>();
		Iterator<GraphNode> g = graph.values().iterator();
		while (g.hasNext()) {

			GraphNode tempNode = g.next();
			ArrayList<String> tempList = tempNode.getConnections();
			for (int i = 0; i < tempList.size(); i++) {

				String value0 = tempList.get(i);
				int key0 = value0.hashCode();
				if (!graphT.containsKey(key0)) {
					GraphNode G = new GraphNode(value0);
					graphT.put(key0, G);
				}
				int key1 = tempNode.getNode().hashCode();
				GraphNode tempConnect = graphT.get(key0);
				tempConnect.addConnection(tempNode.getNode());
				if (!graphT.containsKey(key1)) {
					GraphNode G = new GraphNode(tempNode.getNode());
					graphT.put(key1, G);
				}

			}

		}

		return graphT;

	}

	//fill the specified stack from the root note v
	private void fillOrder(String v, Stack<GraphNode> stack) {

		// Mark the current node as visited and print it
		int hashValue = v.hashCode();
		graph.get(hashValue).setVisited(true);

		String n;
		int tempHash;
		
		//Recur for all vertices adjacent to this matrix
		ArrayList<String> connections = graph.get(hashValue).getConnections();
		for (int i = 0; i < connections.size(); i++) {
			n = connections.get(i);
			tempHash = n.hashCode();
			if (graph.get(tempHash).getVisited() == false) {
				fillOrder(n, stack);
			}
		}
		stack.push(new GraphNode(v));

	}
	
	private void getSCC(){
		
		Stack<GraphNode> stack = new Stack<GraphNode>();
		
		//Mark all vertices as not visited
		Iterator<GraphNode> g = graph.values().iterator();
		while (g.hasNext()) {
			
			g.next().setVisited(false);
			
		}
		
		//Fill the vertices in the stack according to finish times
		g = graph.values().iterator();
		while(g.hasNext()){
			GraphNode temp = g.next();
			if(temp.getVisited() == false){
				fillOrder(temp.getNode(), stack);
			}
		}
		
		//create the transpose graph
		Map<Integer, GraphNode> graphT = getTranspose();
		
		//Mark all the vertices as not visited
		g = graphT.values().iterator();
		while (g.hasNext()) {
			
			g.next().setVisited(false);
			
		}
		
		HashMap<Integer, String> results;
		
		//now process all vertices in order defined by stack
		while(stack.empty() == false){
			
			GraphNode temp = (GraphNode)stack.pop();
			int hashValue = temp.getNode().hashCode();
			if(graphT.get(hashValue).getVisited() == false){
				results = new HashMap<Integer, String>();
				DFSUtil(temp.getNode(), graphT, results);
				if(results.size()>this.largestComponent) largestComponent = results.size();
				SCCs.add(results);
				numComponents++;
			}
			
		}
		
	}

}
