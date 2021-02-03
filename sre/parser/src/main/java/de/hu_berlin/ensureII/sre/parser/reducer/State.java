package de.hu_berlin.ensureII.sre.parser.reducer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hu_berlin.ensureII.sre.grammar.Symbol;


public class State {

	/**
	 * @author Duc Anh Vu
	 *
	 */
	
/*****************************************************************************
** Constructors
*****************************************************************************/
    
	/**
	 * A state for the automaton we use for the reduction of a handle.
	 * 
	 * @param reduction
	 * 			The nonterminal that is reduced to when we
	 * 			end in this state.
	 */
	public State(int ruleID){
		
		this.ruleID = ruleID;
		outTransitions = new ArrayList<Transition>();
		
	}
	
	/**
	 * Add a new outgoing transition to this state.
	 * 
	 * @param label
	 * 			The label of this transition.
	 * @param toState
	 * 			The state we reach after taking this transition.
	 */
	public void addTransition(Symbol label, State toState){
		
		assert(label != null);
		assert(toState != null);
		
		Transition t = new Transition(label, toState);
		outTransitions.add(t);
		
		assert(outTransitions.get(outTransitions.size()-1).getLabel().equals(label));
		assert(outTransitions.get(outTransitions.size()-1).getToState().equals(toState));
		
	}
	
	/**
	 * Do the transition in this state.
	 * 
	 * @param action
	 * 			The action we take.
	 * @return
	 * 			The new state we end up in after doing the transition.
	 */
	public State doTransition(Symbol action) throws NoSuchTransitionException{
		
		for(int i=0; i<outTransitions.size(); i++){
			//if(outTransitions.get(i).getLabel().equals(action)){
			if(outTransitions.get(i).getLabel().name().equals(action.name())){
				return outTransitions.get(i).getToState();
			}
		}

		throw(new NoSuchTransitionException(action.name()));
	}
	
	public boolean transitionAvailable(Symbol label) {
        boolean res = false;
        Transition t;
        for(int i=0; i<outTransitions.size(); i++) {
            t = outTransitions.get(i);
            if(t.getLabel().name().equals(label.name())) {
                res = true;
            }
        }
        return res;
    }
	
/*****************************************************************************
** Attributes, Setter and Getter
*****************************************************************************/
	
	/**
	 * The production rule id for this state.
	 */
	private int ruleID;
	
	public void setRuleID(int ruleID){
		this.ruleID = ruleID;
	}
	
	public int getRuleID(){
		return ruleID;
	}
	
	/**
	 * The outgoing transitions from this state.
	 */
	private List<Transition> outTransitions;
	
	public List<Transition> getOutTransitions(){
		return Collections.unmodifiableList(outTransitions);
	}
	
/*****************************************************************************
** Exceptions
*****************************************************************************/
	
	public static class NoSuchTransitionException extends Exception {
	    
        private static final long serialVersionUID = 1L;

        public NoSuchTransitionException(String action){
	        failedAction = action;
	    }
	    
	    private String failedAction;
	    
	    public String getFailedAction() {
	        return failedAction;
	    }
	}
}
