/*
 * @author: Yiping Jin
 * A0057021W
 * National University of Singapore
 * This program performs sum product belief propagation for tree graph
 * 
 */
import java.util.ArrayList;

/*
 * data structure to store a single node 
 * It keeps track of its parent and a list of children
 * Besides, it also stores its node potential
 */
public class Node{
	  /*
	   * class attributes
	   * 
	   */
	  public ArrayList<Node> children;
	  public Node parent;
	  public double[] potential;
	  public int index;
	  
	  /*
	   * constructor, set the node potential
	   */
	  public Node(int index, double[] potential)
	  {
	   this.index = index;
	   this.potential = potential;
	   this.children = new ArrayList<Node>();
	  }
	  
	  /*
	   * test if the node has received message from all children
	   * @return true if it has
	   * @return false if not
	   */
	  public boolean is_ready(ArrayList<Edge> edgeList){
		  boolean is_ready = true;
		  for(int i=0;i<edgeList.size();i++){
			  if(edgeList.get(i).parent == this){
				  //haven't received the message from this edge yet
				  if(edgeList.get(i).getUpwardMsg() == null){ 
					  is_ready = false;
				  }
			  }
		  }
		  return is_ready;
	  }
	  
	  public int getIndex(){
		  return index;
	  }
	  public Node getParent()
	  {
	   return this.parent;
	  }
	  
	  public void setParent(Node parent)
	  {
	   this.parent = parent;
	  }
	  
	  public ArrayList<Node> getChildren()
	  {
	   return this.children;
	  }
	  
	  public void addChild(Node child)
	  {
	   this.children.add(child);
	  }
}
