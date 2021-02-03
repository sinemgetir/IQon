package de.hu_berlin.ensureII.sre.parser.reducer;

import de.hu_berlin.ensureII.sre.grammar.Symbol;


public class Transition {
	
	/**
	 * @author Duc Anh Vu
	 *
	 */
	
	/**
	 * The transition class for the automaton.
	 * 
	 * @param label
	 * 			The label of this transition.
	 * @param toState
	 * 			The state we reach when taking this transition.
	 */
	public Transition(Symbol label, State toState){
		this.label = label;
		this.toState = toState;
	}
	
	/**
	 * The label of this transition.
	 */
	private Symbol label;
	
	public Symbol getLabel(){
		return label;
	}
	
	/**
	 * The state we reach when taking this transition.
	 */
	private State toState;
	
	public State getToState(){
		return toState;
	}
}
