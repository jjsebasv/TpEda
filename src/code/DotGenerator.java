package code;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import minimax.NodeII;

public class DotGenerator {
	
	public static int nodeCounter;
	public static Map<NodeII,Integer> nodes;
	
	public DotGenerator(){
		this.nodeCounter = 0;
		nodes = new HashMap<NodeII,Integer>();
	}

	
	public static void export(NodeII node){
		File fr = openFile();
		
		try {
			 FileWriter fw = new FileWriter(fr);
	 	        fw.write("digraph {\n");
	 	        if ( nodes.containsKey(node)){
	 	        	nodes.put(node, nodeCounter);
	 	        	nodeCounter++;
	 	        } 	  
	 	        
	 	        fw.write(labelNode(node));
	 	        for (NodeII child : node.children) {
					if ( !nodes.containsKey(child) ){
						nodes.put(child, nodeCounter++);
						fw.write(labelNode(child));
					}
					fw.write(setRelation(node,child));
					fw.write(setColour(node,child));
					fw.write(setShape(child));
				}	
	 	        
	 	       fw.write("}\n");
	 	       fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
    
	private static String setColour(NodeII father, NodeII son){
		if (son.children == null || (son.children != null && son.children.size() == 0) ){
			return nodes.get(son)+ "[color=grey style=filled] "+"\n";
		}
		else if ( father.chosen.equals(son) ){
			return nodes.get(son)+ "[color=yellow style=filled] "+"\n";
		}else{
			return nodes.get(son)+ "[color=white style=filled] "+"\n";
		}
	}
	
	private static String setShape(NodeII node){
		switch (node.getTurn()) {
		case 1:
			return nodes.get(node)+ "[shape=box]"+"\n";
		case 2:
			return nodes.get(node)+ "[shape=ellipse]"+"\n";
		default:
			return "\n";
		}
	}
	
	private static String labelNode(NodeII node) {
		String coordenadas = "("+node.getFrom().getFila()+","+node.getFrom().getColumna()+")("+node.getTo().getFila()+","+node.getTo().getColumna()+")";
		String label = nodeCounter+" "+"[label="+coordenadas+"]"+"\n";
		return label;
	}

	private static String setRelation(NodeII father, NodeII son){
		String label = nodes.get(father)+" -> "+nodes.get(son)+"\n";
		return label;
	}
	
	private static File openFile(){
		File fr = null ;
		return fr;
	}
}
