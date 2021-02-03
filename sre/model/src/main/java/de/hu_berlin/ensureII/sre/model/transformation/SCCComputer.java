package de.hu_berlin.ensureII.sre.model.transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import transitiongraph.State;
import transitiongraph.Transition;
import transitiongraph.TransitionGraph;

public class SCCComputer {

/*****************************************************************************
** Constructors
*****************************************************************************/
    
	public SCCComputer(TransitionGraph tg) {
		this.tg = tg;
	}

/*****************************************************************************
** Methods
*****************************************************************************/
	
	/**
     * Compute strongly connected components for a transition graph +
     * computes if the states in each SCC are essential or inessential.
     * 
     */
	public void computeSCC() {
		tags = new ArrayList<NodeTag>();
		for(int i=0; i<tg.getStates().size(); i++) {
			tags.add(new NodeTag());
		}
		
		SCCs = new HashMap<Integer, List<State>>();
		nodeStack = new Stack<State>();
		maxSCCId = INVALID_SCC_ID;

		for(State state : tg.getStates()) {
			if(tags.get(state.getId()).wasVisited() == false) {
				DFS(state);
			}
		}
		computeSCCIds();

		SCCProperties = new HashMap<Integer, String>();
		computeEssentialStates();
		computeSCCtransitionGraphs();
	}

	/**
	 * depth first search
	 * @param state
	 */
	private void DFS(State state) {
		tags.get(state.getId()).setVisited();
		State next;

		for (Transition transition : state.getOutgoing()) {
			if(transition.getProbability() == 0.0) {
				continue;
			}
			
			next = transition.getTarget();
			if (tags.get(next.getId()).wasVisited() == false) {
				DFS(next);
			}
		}
		
		nodeStack.push(state);
	}
	
	private void computeSCCIds() {
		State state;
		
		while(nodeStack.empty() == false) {
			state = nodeStack.pop();
			if(tags.get(state.getId()).getSCCId() == INVALID_SCC_ID) {
				maxSCCId++;
				SCCs.put(maxSCCId, new ArrayList<State>());

				DFSTranspose(state);
			}
		}
	}
	
	/**
	 * depth first search on the transpose graph
	 * @param state
	 */
	private void DFSTranspose(State state) {
		
		if(tags.get(state.getId()).getSCCId() == INVALID_SCC_ID) {
			//state is not member of a strongly connected component yet, 
			//so it has to be member of the current maxSCCId
			tags.get(state.getId()).setSCCId(maxSCCId);
			SCCs.get(maxSCCId).add(state);

			for (Transition t : state.getIncoming()) {
				if(t.getProbability() == 0.0) {
					continue;
				}
				DFSTranspose(t.getSource());
			}
		}
	}

	/**
	 * A state is essential if it can always return to itself. 
	 * Therefore the states in an SCC are essential if they can not
	 * reach other SCCs.
	 * 
	 * Because suppose a state in an SCC can reach another SCC, and still return
	 * to itself. That means the other state must then have been in the same SCC.
	 * 
	 */
	private void computeEssentialStates() {
		String property;
		
		for(int currentSCCId : SCCs.keySet()) {
			property = "essential";
			for(State state : SCCs.get(currentSCCId)) {
				if(otherSCCReachable(state, currentSCCId)) {
					property = "inessential";
					break;
				}
			}
			SCCProperties.put(currentSCCId, property);
		}
		
	}
	
	private boolean otherSCCReachable(State state, int currentSCCId) {
		boolean otherSCCReachable = false;
		State neighbour;
	
		for(Transition transition : state.getOutgoing()) {
			neighbour = transition.getTarget();
			if(tags.get(neighbour.getId()).getSCCId() != currentSCCId) {
				otherSCCReachable = true;
				break;
			}
		}
		
		return otherSCCReachable;
	}
	
	private void computeSCCtransitionGraphs() {
	    SCCtransitionGraphs = new HashMap<Integer, TransitionGraph>();

	    for(Integer SCCId : SCCs.keySet()) {
	        
	        List<State> states = SCCs.get(SCCId);
	        List<Integer> initialIds = new ArrayList<Integer>();
	        List<Integer> finalIds = new ArrayList<Integer>();
	        List<Transition> transitions = new ArrayList<Transition>();
	        
	        for(State sccState : SCCs.get(SCCId)) {
	            
	            /* every state in SCC is final */
	            finalIds.add(sccState.getId());
	            
	            if(sccState.isIsInitial()) {
	                initialIds.add(sccState.getId());
	            }
	            
	            /* if state s has incoming transition from outside the scc we define  
	             * it as an initial state for this scc
	             * else add the transition to the new graph
	             */
	            for(Transition sccTransition : sccState.getIncoming()) {
	                if(sameSCC(sccState, sccTransition.getSource())) {
	                    transitions.add(sccTransition);
	                }else {
	                    initialIds.add(sccState.getId());
	                }
	            }
	            
	        }
	        TransitionGraph transitionGraph = TransitionGraphBuilder.build(states, initialIds, finalIds, transitions);
	        SCCtransitionGraphs.put(SCCId, transitionGraph);
	    }
	}
	
	private boolean sameSCC(State state1, State state2) {
	    return tags.get(state1.getId()).getSCCId() == tags.get(state2.getId()).getSCCId();
	}
	
/*****************************************************************************
** Attributes
*****************************************************************************/
	
	private TransitionGraph tg;

	private Stack<State> nodeStack;

	private ArrayList<NodeTag> tags;
	
	private HashMap<Integer, List<State>> SCCs;
	
	private HashMap<Integer, TransitionGraph> SCCtransitionGraphs;
	
	private int maxSCCId;
	
	private HashMap<Integer, String> SCCProperties;
	
	public static final int INVALID_SCC_ID = -1;


	
/*****************************************************************************
** Getter
*****************************************************************************/
	
	public ArrayList<NodeTag> getTags(){
		return tags;
	}
	
	public HashMap<Integer, List<State>> getSCCs(){
		return SCCs;
	}
	
	public HashMap<Integer, TransitionGraph> getSCCtransitionGraphs(){
        return SCCtransitionGraphs;
    }
	
	public HashMap<Integer, String> getSCCProperties(){
		return SCCProperties;
	}
	
/*****************************************************************************
** nested class
*****************************************************************************/
	
	 /**
     *  This class is used for tagging visited states in a depth first search.
     *  Also tags the states with the id of the strongly connected component
     *  they belong to. 
     *
     *
     */
    class NodeTag {
        private boolean visited = false;
        private int sccId = INVALID_SCC_ID;

        public void setVisited() {
            visited = true;
        }
        
        public void setSCCId(int scc) {
            this.sccId = scc;
        }

        public boolean wasVisited() {
            return visited;
        }

        public int getSCCId() {
            return sccId;
        }
    }
}
