/*
 * @author: Yiping Jin
 * A0057021W
 * National University of Singapore
 * This program performs sum product belief propagation for tree graph
 * 
 */
import java.util.ArrayList;
import java.util.LinkedList;


public class SumProduct
{

	 //perform the belief propagation algorithm	
	 public void beliefPropagation(ArrayList<Node> nodeList, ArrayList<Edge> edgeList, Node root){
		 LinkedList<Node> activeNodes = new LinkedList<Node>();
		 
		 //start passing message from leaves to root
		 for(int i=0;i<nodeList.size();i++){
			 if(nodeList.get(i).getChildren().size()==0){
				 activeNodes.add(nodeList.get(i));
			 }
		 }
		 
		 while(activeNodes.size()>0){
			 Node currentNode = activeNodes.pop();
			 System.out.println(currentNode.getIndex());
			 if(currentNode.getParent()!=null){
				 if(!activeNodes.contains(currentNode.getParent())){
					activeNodes.add(currentNode.getParent());
				 }
				 sendMessage(edgeList, currentNode, currentNode.getParent());
			 }
		 }
		 
		 //passing message from root to leaves
		 activeNodes.add(root);
		 
		 while(activeNodes.size()>0){
			 Node currentNode = activeNodes.pop();
			 //System.out.println(currentNode.getIndex());
			 if(currentNode.getChildren().size()>0){
				 for(int j=0;j<currentNode.getChildren().size();j++){
					activeNodes.add(currentNode.getChildren().get(j));
					sendMessage(edgeList, currentNode, currentNode.getChildren().get(j));
				 }
			 }			 
		 }
		 
		 //collect marginal probability for all nodes
		 for(int i=0;i<nodeList.size();i++){
			 collectMarginal(edgeList, nodeList.get(i));
		 }
	 }
	 
	 public void sendMessage(ArrayList<Edge> edgeList, Node sender, Node receiver){
		 //message passing upward
		 if(sender.getParent() == receiver){
			 System.out.println("passing message upward from " +  sender.index + " TO "+ receiver.index);
			 Edge cur_edge = Edge.getEdgeByNodes(edgeList, receiver, sender);
			 double[][] cur_edge_potential = cur_edge.potential;
			 double[] cur_node_potential = sender.potential;
			 
			 double[] message = new double[cur_edge_potential.length];
			 
			 for(int i=0;i<cur_edge_potential.length;i++){
				 double tempMsg = 0.0;
				 for(int j=0;j<cur_edge_potential.length;j++){  //sum loop

					 double product = 1.0;
					 if(sender.getChildren().size()!=0){  //if sender is a leaf
						 for(int k=0;k<sender.getChildren().size();k++){  //product loop
							 product *= Edge.getEdgeByNodes(edgeList, sender, sender.getChildren().get(k)).upwardMsg[j];
						 }
					 }
					 //update message
					 tempMsg += cur_edge_potential[j][i]*cur_node_potential[j]*product;
				 }
				 System.out.println("send message: " + tempMsg);
				 message[i] = tempMsg;
			 }
			 
			 cur_edge.upwardMsg = message;
			 
		 }else{
			 System.out.println("passing message downward from " +  sender.index + " TO "+ receiver.index);
			 Edge cur_edge = Edge.getEdgeByNodes(edgeList, sender, receiver);
			 double[][] cur_edge_potential = cur_edge.potential;
			 double[] cur_node_potential = sender.potential;
			 
			 double[] message = new double[cur_edge_potential.length];
			 
			 for(int i=0;i<cur_edge_potential.length;i++){
				 double tempMsg = 0.0;
				 for(int j=0;j<cur_edge_potential.length;j++){  //sum loop

					 double product = 1.0;
					 if(sender.getParent() != null){  //if sender is a root
							 product *= Edge.getEdgeByNodes(edgeList, sender.getParent(), sender).downwardMsg[j];
					 }
					 //update message
					 tempMsg += cur_edge_potential[j][i]*cur_node_potential[j]*product;
				 }
				 System.out.println("send message: " + tempMsg);
				 message[i] = tempMsg;
			 }		 
			 cur_edge.downwardMsg = message;	
		 }

	 }
	 
	 //collect the marginal probability of a given node
	 public double[] collectMarginal(ArrayList<Edge> edgeList, Node node){
		 double[] result = new double[node.potential.length];
		 double[] node_potential = node.potential;
		 
		 System.out.println("Collect marginal probability for " + node.index);
		 for(int i=0;i<node.potential.length;i++){  //for each outcome of the variable
			 double temp_result = 1.0;
			 if(node.getParent()!= null){
				 Edge edge = Edge.getEdgeByNodes(edgeList, node.getParent(), node);
				 temp_result *= edge.downwardMsg[i];
			 }
			 for(int k=0;k<node.getChildren().size();k++){
				 Edge edge = Edge.getEdgeByNodes(edgeList, node, node.getChildren().get(k));
				 temp_result *= edge.upwardMsg[i];				 
			 }
			 result[i] = temp_result * node_potential[i];
			 
		 }
		 double sum = 0.0;
		 for(int i=0;i<result.length;i++){
			 sum += result[i];
		 }
		 //normalize the probability
		 for(int i=0;i<result.length;i++){
			 result[i] = result[i]/sum;
			 System.out.println(result[i]);
		 }		 
		 return result;
	 }
 
	 public static void main(String[] args)
	 {
		
	    double[] nodePotentialOdd = {0.7,0.3};
	    double[] nodePotentialEven = {0.1,0.9};
	    double[][] edgePotential = {{1,0.5},{0.5,1}};
	    
	    //create the nodes
	    ArrayList<Node> nodeList = new ArrayList<Node>();
	    Node one = new Node(1,nodePotentialOdd);
	    Node two = new Node(2,nodePotentialEven);
	    Node three = new Node(3,nodePotentialOdd);
	    Node four = new Node(4,nodePotentialEven);
	    Node five = new Node(5,nodePotentialOdd);
	    Node six = new Node(6,nodePotentialEven);
	    nodeList.add(one);
	    nodeList.add(two);
	    nodeList.add(three);
	    nodeList.add(four);
	    nodeList.add(five);
	    nodeList.add(six);
	    
	    //add edges
	    ArrayList<Edge> edgeList = new ArrayList<Edge>();
	    
	    edgeList.add(new Edge(one, two, edgePotential));
	    edgeList.add(new Edge(one, three, edgePotential));
	    edgeList.add(new Edge(two, four, edgePotential));
	    edgeList.add(new Edge(two, five, edgePotential));
	    edgeList.add(new Edge(three, six, edgePotential));
	 
	    SumProduct mp = new SumProduct();
	    mp.beliefPropagation(nodeList, edgeList, one);
	   
	 }
}
