package de.hu_berlin.ensureII.sre.model.conversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hu_berlin.ensureII.sre.model.transformation.SCCComputer;
import transitiongraph.State;
import transitiongraph.Transition;
import transitiongraph.TransitionGraph;
import transitiongraph.TransitiongraphFactory;

public class Tra2EmfConverter {

	public TransitionGraph convert(File traFile, String kind, boolean uniform) {

		this.id2state = new HashMap<Integer, State>();

		this.tg = TransitiongraphFactory.eINSTANCE.createTransitionGraph();

		// creates transitions (lazyly creates states)
		try {
			int lineCounter = 0;

			FileReader fileReader = new FileReader(traFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			this.finalIds = new ArrayList<Integer>();
			while ((line = bufferedReader.readLine()) != null) {
				if (line.trim().equals("")){
					break;
				}
				
				lineCounter++;
				
				if(lineCounter == 1) {
					//first line has only info about nr of states and transitions
					continue;
				}

				int srcId = getSrcId(line);
				int tgtId = getTgtId(line);
				String label = getLabel(line);
				double probability = getProbability(line);

				Transition t = TransitiongraphFactory.eINSTANCE.createTransition();
				t.setSource(getState(srcId));
				t.setTarget(getState(tgtId));
				t.setLabel(label);
				t.setProbability(probability);
				
				tg.getTransitions().add(t);

			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		calculateInitialStates();
		for (Integer id : initialIds) {
			id2state.get(id).setIsInitial(true);
		}
		
		calculateFinalStates();
		for (Integer id : finalIds) {
			id2state.get(id).setIsFinal(true);
		}

		return tg;
	}

	private State getState(int id) {
		if (id2state.containsKey(id)) {
			return id2state.get(id);
		}

		State s = TransitiongraphFactory.eINSTANCE.createState();
		s.setId(id);
		s.setIsInitial(false);
		s.setIsFinal(false);

		this.tg.getStates().add(s);
		this.id2state.put(id, s);

		return s;
	}

	private static int getSrcId(String line) {
		String[] fragments = line.split(" ");
		return Integer.parseInt(fragments[0]);
	}

	private static int getTgtId(String line) {
		String[] fragments = line.split(" ");
		return Integer.parseInt(fragments[1]);
	}

	private static double getProbability(String line) {
		String[] fragments = line.split(" ");
		return Double.parseDouble(fragments[2]);
	}

	private static String getLabel(String line) {
		String[] fragments = line.split(" ");
		return "s" + fragments[1];
	}
	
	/**
	 * all states with no or only loops as incoming transitions are initial states
	 */
	private void calculateInitialStates() {
		initialIds = new ArrayList<Integer>();
		boolean isInitial;
		
		for(State state : tg.getStates()) {
			isInitial = true;
			for(Transition transition : state.getIncoming()) {
				if(transition.getSource().equals(state) == false) {
					isInitial = false;
					break;
				}
			}
			if(isInitial) {
				initialIds.add(state.getId());
			}
		}
		
		//if no state meets the requirements to be initial, e.g. all states are
		//in a cycle, then the first state is initial
		if(initialIds.isEmpty()){
		    id2state.get(0).setIsInitial(true);
		    initialIds.add(0);
		}
		
	}
	
	/**
	 * all essential states are final
	 */
	private void calculateFinalStates() {
		SCCComputer sccc = new SCCComputer(tg);
		sccc.computeSCC();
		for(int sccID : sccc.getSCCProperties().keySet()) {
			if(sccc.getSCCProperties().get(sccID).equals("essential")) {
				for(State s : sccc.getSCCs().get(sccID)) {
					finalIds.add(s.getId());
				}
			}
		}
	}

	private ArrayList<Integer> initialIds;
    
	private ArrayList<Integer> finalIds;

    private Map<Integer, State> id2state;

    private TransitionGraph tg;
	
}
