package de.hu_berlin.ensureII.sre.model.transformation;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import de.hu_berlin.ensureII.sre.model.conversion.Tra2EmfConverter;
import transitiongraph.State;
import transitiongraph.TransitionGraph;

public class SCCTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tra2EmfConverter converter = new Tra2EmfConverter();
		TransitionGraph tg = converter.convert(new File("src/main/resources/tra/herman/herman3.tra"), "PA", false);
		SCCComputer sccc = new SCCComputer(tg);
		sccc.computeSCC();
		
		HashMap<Integer, List<State>> map = sccc.getSCCs();
		for(int i: map.keySet()) {
			System.out.print("SCC " + i + ": ");
			for(int j=0; j<map.get(i).size(); j++) {
				System.out.print(map.get(i).get(j).getId() + " ");
			}
			System.out.println("");
		}
		
		HashMap<Integer, String> map2 = sccc.getSCCProperties();
		for(int i: map2.keySet()) {
			System.out.println("SCC " + i + ": " + map2.get(i));
		}
		
		System.out.println(sccc.getSCCtransitionGraphs().get(1).getTransitions());
	}

}
