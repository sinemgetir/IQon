package de.hu_berlin.ensureII.evolution.specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hu_berlin.ensureII.sre.model.DTMC;
import de.hu_berlin.ensureII.sre.model.DTMC.Edge;
import de.hu_berlin.ensureII.sre.model.DTMC.Node;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETree;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode;
import de.hu_berlin.ensureII.sre.parser.sretree.SRETreeNode.Type;
import de.hu_berlin.ensureII.sre.parser.sretree.Tuple;
import de.hu_berlin.ensureII.sre.parser.sretree.builder.SRETreeBuilder;
import de.hu_berlin.ensureII.sre.parser.sretree.edit.SRETreeEdit;

public class ChangePAandSRE {
	  public DTMC pa;
	  public HashMap<String, SRETreeNode>  edgeIdToSreIdMap;
	  public SRETreeBuilder builder = new SRETreeBuilder();
	    public SRETreeEdit edit = new SRETreeEdit();
	    public SRETree sre;


	public ChangePAandSRE(DTMC pa, SRETree sre, HashMap<String, SRETreeNode> edgeIdToSreIdMap) {
	        this.edgeIdToSreIdMap = edgeIdToSreIdMap;
	        this.pa =pa;
	        this.sre = sre;
	        
	         }
	 
	
	public long addTask(int sourceId, String event2add1, String event2add2, int targetId) {
		
		//PA
		
		Collection<Edge> edges = pa.getEdges(pa.getNodeByName(String.valueOf(sourceId)), pa.getNodeByName(String.valueOf(targetId)));
		
		if ((edges.size() <2) && (edges.size() >0) ) {
			Node newNode = pa.addNode();
			Edge edge= edges.iterator().next();
			System.out.print(edge.toString());
					
			pa.addEdge(pa.getNodeByName(String.valueOf(sourceId)), newNode, event2add1,1.0);
			pa.addEdge(newNode, pa.getNodeByName(String.valueOf(targetId)), event2add2,1.0);
			
	
		
		/**SRE**/
	  
		
//	    SRETreeNode nodeToReplace = edgeIdToSreIdMap.get(edge);
//	    System.out.println(nodeToReplace.toString());
//	    SRETreeNode concat2 = builder.buildConcat(concat, nodeToReplace.clone());
	    SRETreeNode node2Replace = edgeIdToSreIdMap.get(edge.character);
	    System.out.print(node2Replace.getId());
	    long current = System.currentTimeMillis();
	    SRETreeNode event1 = builder.buildAction(event2add1);
	    SRETreeNode event2 = builder.buildAction(event2add2);
	    SRETreeNode concat = builder.buildConcat(event1, event2);

        edit.replaceSubtree(sre, 11000, concat);
		long passed = System.currentTimeMillis();
		
		pa.removeEdge(edge);			
        return passed-current;
		 
		}
		
		return 0;
	
	}
	
	 public long updateDistribution(int sourceId, List<Tuple<Integer, Double>> targetIdsAndProbabilities) {
	        
	        /**SRE**/
	        //nodes need to have the same choice parent
		 	Tuple<Integer, Double> targets = targetIdsAndProbabilities.get(0);
		 	int targetId = targets.x;
		 	double rate = targets.y;
		 	
			Collection<Edge> edges = pa.getEdges(pa.getNodeByName(String.valueOf(sourceId)), pa.getNodeByName(String.valueOf(targetId)));
		
			SRETreeNode node  = edgeIdToSreIdMap.get(edges.iterator().next().character);
        
			System.out.println(node.getType());
		 	long current = System.currentTimeMillis();
	        edit.updateRate(sre, node.getId(), (int) (rate* 1000));
	            
	        
	        /*******/
	        
//	        /**PA***/
//	        ArrayList<Double> probabilities = new ArrayList<Double>(pa.getNrOfStates());
//	        for(int i=0; i<pa.getNrOfStates(); i++) {
//	            probabilities.add(0.0);
//	        }
//	        for(Tuple<Integer, Double> tuple : targetIdsAndProbabilities) {
//	            probabilities.set(tuple.x, tuple.y);
//	        }
//	        pa.updateDistribution(sourceId, probabilities);
//	        /*******/
	        
	        return System.currentTimeMillis()-current;
	    }


	    

 

}
