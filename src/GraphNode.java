import java.util.ArrayList;

public class GraphNode {
	
	private ArrayList<String> outConnections;
	private String nodeValue;
	
	public GraphNode(String nodeValue){
		
		this.nodeValue = nodeValue;
		outConnections = new ArrayList<String>();
		
	}
	
	protected String getNode(){
		
		return this.nodeValue;
		
	}
	
	protected ArrayList<String> getConnections(){
		
		return outConnections;
		
	}
	
	protected void addConnection(String node){
		
		outConnections.add(node);
		
	}

}
