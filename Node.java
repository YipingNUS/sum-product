/*
 * @author: Yiping Jin
 * A0057021W
 * National University of Singapore
 * This program performs sum product belief propagation for tree graph
 * 
 */
import java.util.ArrayList;

public class Node{
	  /*
	   * class attributes
	   * 
	   */
	  public ArrayList<Node> children;
	  public Node parent;
	  public double[] potential;
	  public int index;
	  //TODO: define the message here
	  
	  /*
	   * constructor, set the node potential
	   */
	  public Node(int index, double[] potential)
	  {
	   this.index = index;
	   this.potential = potential;
	   this.children = new ArrayList<Node>();
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
