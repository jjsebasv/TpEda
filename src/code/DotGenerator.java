package code;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import minimax.NodeII;

public class DotGenerator {
	
	private static int nodeCounter;
	private static Map<NodeII,Integer> nodes;
	private static File fr;
	private static FileWriter fw;
	
	public DotGenerator(){
		this.nodeCounter = 0;
		nodes = new HashMap<NodeII,Integer>();
		fr = new File("tree.dot");
		try {
			fw = new FileWriter(fr);
			fw.write("digraph {\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	public static int getNodeCounter() {
		return nodeCounter;
	}


	public static void setNodeCounter(int nodeCounter) {
		DotGenerator.nodeCounter = nodeCounter;
	}


	public static Map<NodeII, Integer> getNodes() {
		return nodes;
	}


	public static void setNodes(Map<NodeII, Integer> nodes) {
		DotGenerator.nodes = nodes;
	}


	public static File getFr() {
		return fr;
	}


	public static void setFr(File fr) {
		DotGenerator.fr = fr;
	}


	public static FileWriter getFw() {
		return fw;
	}


	public static void setFw(FileWriter fw) {
		DotGenerator.fw = fw;
	}


	public static void export(NodeII node){
		
		if ( node == null ){
			return;
		}
		
		try {
	 	     
	 	        if ( !nodes.containsKey(node)){
	 	        	nodes.put(node, nodeCounter);
	 	        	fw.append(labelNode(node));
	 	        	nodeCounter++;
	 	        } 	  

	 	        for (NodeII child : node.children) {
					if ( !nodes.containsKey(child) ){
						nodes.put(child, nodeCounter++);
						fw.append(labelNode(child));
					}
					fw.append(setRelation(node,child));
					fw.append(setColour(node,child));
					fw.append(setShape(child));
				}	
	 	        
	 	       //fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
    
	private static String setColour(NodeII father, NodeII son){
		String color = (son.getColour()==null)? "white" : son.getColour();
		return nodes.get(son)+ "[color="+color+" style=filled] "+"\n";
	}
	
	private static String setShape(NodeII node){
		String shape = "";
		switch (node.getMove().getTurn()) {
		case 1:
			shape = "box";
			break;
		case 2:
			shape = "ellipse";
			break;
		default:
			shape = "OTRO CASO";
			
		}

		return nodes.get(node)+ "[shape="+shape+"]"+"\n";
	}
	
	private static String labelNode(NodeII node) {
		if ( node.getMove() == null || nodeCounter == 0){
			return "START";
		}
		
		String coordenadas = "("+node.getMove().getIfrom()+","+node.getMove().getJfrom()+")("+node.getMove().getIto()+","+node.getMove().getJTo()+")";
		String label = nodeCounter+" "+"[label="+coordenadas+"]"+"\n";
		return label;
	}

	private static String setRelation(NodeII father, NodeII son){
		String label = nodes.get(father)+" -> "+nodes.get(son)+"\n";
		return label;
	}
	
}
