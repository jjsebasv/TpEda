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
		fr = new File("graph.txt");
		try {
			fw = new FileWriter(fr);
			fw.write("digraph {\n");
		} catch (IOException e) {
			System.out.println("-------- NO PUDE ESCRIBIR ------------");
			e.printStackTrace();
		}
		
	}

	
	public static void export(NodeII node){
		
		if ( node == null ){
			System.out.println("node era null");
			return;
		}
		
		try {
	 	     
				//System.out.println(node.getMove());
	 	        if ( !nodes.containsKey(node)){
	 	        	System.out.println("no contiene la key");
	 	        	nodes.put(node, nodeCounter);
	 	        	fw.append(labelNode(node));
	 	        	nodeCounter++;
	 	        } 	  

	 	        for (NodeII child : node.children) {
					if ( !nodes.containsKey(child) ){
						nodes.put(child, nodeCounter++);
						fw.append(labelNode(child));
					}
					System.out.println(child.getMove());
					fw.append(setRelation(node,child));
					fw.append(setColour(node,child));
					fw.append(setShape(child));
				}	
	 	        
	 	       //fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(nodes);
		//System.out.println(nodes.size());
 	}
    
	private static String setColour(NodeII father, NodeII son){
		return nodes.get(son)+ "[color=white style=filled] "+"\n";
	}
	
	private static String setShape(NodeII node){
		return nodes.get(node)+ "[shape="+node.getShape()+"]"+"\n";
	}
	
	private static String labelNode(NodeII node) {
		if ( node.getMove() == null || nodeCounter == 0){
			return "START";
		}
		
		String coordenadas = "("+node.getMove().getIfrom()+","+node.getMove().getJfrom()+")("+node.getMove().getIto()+","+node.getMove().getJTo()+")";
		//System.out.println("coordenadas"+coordenadas);
		String label = nodeCounter+" "+"[label="+coordenadas+"]"+"\n";
		//System.out.println("label: "+label);
		return label;
	}

	private static String setRelation(NodeII father, NodeII son){
		String label = nodes.get(father)+" -> "+nodes.get(son)+"\n";
		return label;
	}
	
}
