package de.hu_berlin.ensureII.sre.parser.attributes.data;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import org.apache.commons.collections.keyvalue.MultiKey;
//import org.apache.commons.collections.map.MultiKeyMap;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import de.hu_berlin.ensureII.sre.parser.attributes.NodeCounter;




public class SRENodeData {
	private Map<List<String>,Double> probabilityMap = new HashMap<List<String>, Double>();
	private Map<List<String>,Double> containmentMap = new HashMap<List<String>, Double>();
	private Map<List<String>,Double> prefixMap = new HashMap<List<String>, Double>();
	private Map<List<String>,Double> suffixMap = new HashMap<List<String>, Double>();
	private Map<Integer,Double> lengthMap = new HashMap<Integer,Double>();
//	private Map<List<String>, Map<Integer, Double>> InclusionWlengthMap = new HashMap<List<String>, Map<Integer,Double>>();
	//private MultiKeyMap InclusionWlengthMap = MultiKeyMap.decorater(new HashMap());
	//private Map<MultiKey, Double> InclusionWlengthMap = new HashMap<MultiKey,Double>();
	private Table<List<String>,Integer,Double> InclusionWlengthMap = HashBasedTable.create();
	private String terminal;
	
	private List<String> searchedMatching = new LinkedList<String>();
	
	private int searchedLength;
	//for bounded reachbility
	private int n;
	
	private double basicProbability = 0.0;
	
	private double choiceRate = 0.0;
	
	private String serialized = "";
	
	private int depth = 0;
	
	private List<List<String>> instances = new LinkedList<List<String>>();
	//private DTMC dtmc = null;
	private  int choiceCount;
	private  int concatCount;
	private  int kleeneCount;
	private  int plusClosureCount;
	private  int actionCount;
	
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		//s.append("SRENodeData{\n");
	//	s.append("\tserialized: "+serialized+"\n");
		s.append("\texact prob: "+probabilityMap+"\n");
		s.append("\tprefix prob: "+prefixMap+"\n");
		s.append("\tsuffix prob: "+suffixMap+"\n");
		s.append("\tprob containment: "+containmentMap+"\n");
		s.append("\tprob length map: "+lengthMap+"\n");
		s.append("\tprob containment length map: "+InclusionWlengthMap+"\n");
		//s.append("\tdepth: "+depth+"\n");
		//s.append("\tsome instances: "+instances+"\n");
		//s.append("}\n");
		return s.toString();
	}

	public Map<List<String>, Double> getProbabilityMap() {
		return probabilityMap;
	}

	public void setProbabilityMap(Map<List<String>, Double> probabilityMap) {
		this.probabilityMap = probabilityMap;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public List<String> getSearchedMatching() {
		return searchedMatching;
	}

	public void setSearchedMatching(List<String> searchedMatching) {
		this.searchedMatching = searchedMatching;
	}

	public double getBasicProbability() {
		return basicProbability;
	}

	public void setBasicProbability(double basicProbability) {
		this.basicProbability = basicProbability;
	}

	public double getChoiceRate() {
		return choiceRate;
	}

	public void setChoiceRate(double choiceRate) {
		this.choiceRate = choiceRate;
	}

	public String getSerialized() {
		return serialized;
	}

	public void setSerialized(String serialized) {
		this.serialized = serialized;
	}

	public int getDepth() {
		return depth;
	}
	

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public List<List<String>> getInstances() {
		return instances;
	}

	public void setInstances(List<List<String>> instances) {
		this.instances = instances;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof SRENodeData){
			return getSerialized().equals(((SRENodeData) o).getSerialized());
		}
		return false;
	}

//	public DTMC getDtmc() {
//		return dtmc;
//	}
//
//	public void setDtmc(DTMC dtmc) {
//		this.dtmc = dtmc;
//	}

	public Map<List<String>,Double> getContainmentMap() {
		return containmentMap;
	}

	public void setContainmentMap(Map<List<String>,Double> containmentMap) {
		this.containmentMap = containmentMap;
	}

	public Map<List<String>,Double> getPrefixMap() {
		return prefixMap;
	}

	public void setPrefixMap(Map<List<String>,Double> prefixMap) {
		this.prefixMap = prefixMap;
	}

	public Map<List<String>,Double> getSuffixMap() {
		return suffixMap;
	}

	public void setSuffixMap(Map<List<String>,Double> suffixMap) {
		this.suffixMap = suffixMap;
	}

	public Map<Integer,Double> getLengthMap() {
		return lengthMap;
	}

	public void setLengthMap(Map<Integer,Double> lengthMap) {
		this.lengthMap = lengthMap;
	}

	public int getSearchedLength() {
		return searchedLength;
	}

	public void setSearchedLength(int searchedLength) {
		this.searchedLength = searchedLength;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public Table<List<String>,Integer,Double> getInclusionWlengthMap() {
		return InclusionWlengthMap;
	}

	public void setInclusionWlengthMap(Table<List<String>,Integer,Double> inclusionWlengthMap) {
		InclusionWlengthMap = inclusionWlengthMap;
	}

	public int getChoiceCount() {
		return choiceCount;
	}

	public void setChoiceCount(int choiceCount) {
		this.choiceCount = choiceCount;
	}

	public int getConcatCount() {
		return concatCount;
	}

	public void setConcatCount(int concatCount) {
		this.concatCount = concatCount;
	}

	public int getPlusClosureCount() {
		return plusClosureCount;
	}

	public  void setPlusClosureCount(int plusClosureCount) {
		this.plusClosureCount = plusClosureCount;
	}

	public int getActionCount() {
		return actionCount;
	}

	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}

	public int getKleeneCount() {
		return kleeneCount;
	}

	public void setKleeneCount(int kleeneCount) {
		this.kleeneCount = kleeneCount;
	}

	
//	public int getDepthMap(ConcreteVisitor visitor, ASTNode root) {
//		
//		List<ASTNode> kids = root.getChildren();
//		int size = kids.size();
//			
//			for (ASTNode ast : kids) {
//				if (visitor.getValue(ast) != null) {
//					System.out.print(" "+visitor.getValue(ast).getResult().getDepth()+" ");
//					getDepthMap(visitor, ast);
//				}
//				else{
//					System.out.print("leaf");
//				}
//				
//				
//			}
//			
//			System.out.println();
//			
//		
//		return 0;
//	}

}
