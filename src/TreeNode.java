/**
 * @author Sander VanWilligen
 * @author Zackery Lovisa
 */

public class TreeNode {

    private String data;
    private TreeNode parent;

    protected TreeNode(String data) {
        this.data = data;
        this.parent = null;
    }

    protected void addParent(TreeNode parent) {
        this.parent = parent;
    }
    
    protected TreeNode getParent(){
    	return this.parent;
    }
    
    protected String getData(){
    	return this.data;
    }
    
    protected boolean hasParent(){
    	if(this.parent!=null) return true;
    	return false;
    }
    
}