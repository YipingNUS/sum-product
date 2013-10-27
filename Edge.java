/*
 * @author: Yiping Jin
 * A0057021W
 * National University of Singapore
 * This program performs sum product belief propagation for tree graph
 * 
 */
import java.util.ArrayList;

public class Edge {
    public double[][] potential;
    public Node parent;
    public Node child;
   
    double[] upwardMsg;
    double[] downwardMsg;
 
    public Edge(Node parent, Node child, double[][] potential){
    	this.parent = parent;
    	this.child = child;
    	this.potential = potential;
    	this.parent.addChild(child);
    	this.child.setParent(parent);
    }

    public static Edge getEdgeByNodes(ArrayList<Edge> edgeList, Node parent, Node child){
        for(int i=0;i<edgeList.size();i++){
        	if(edgeList.get(i).parent == parent && edgeList.get(i).child == child){
        		return edgeList.get(i);
        	}
        }
        return null;
    }
    
    public double[] getUpwardMsg(){
        return upwardMsg;
    }
   
    public void setUpwardMsg(double[] upwardMsg){
    	this.upwardMsg = upwardMsg;
    }
    public double[] getDownwardMsg(){
        return downwardMsg;
    }
   
    public void setDownwardMsg(double[] downwardMsg){
    	this.downwardMsg = downwardMsg;
    } 
}
