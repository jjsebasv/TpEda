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
	 	        fw.write(labelNode(node));
	 	        //for (NodeII node : node.children) {
				//	
				//}	
	 	        
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
    
	
	private static String labelNode(NodeII node) {
		String coordenadas = "("+node.getFrom().getFila()+","+node.getFrom().getColumna()+")("+node.getTo().getFila()+","+node.getTo().getColumna()+")";
		String label = nodeCounter+" "+"[label="+coordenadas+"]";
		nodeCounter++;
		return label;
	}

	private static String labelSon(NodeII father, NodeII son){
		
	}
	
	private static File openFile(){
		File fr = null ;
		return fr;
	}
}
