package de.hu_berlin.ensureII.sre.parser.reducer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hu_berlin.ensureII.sre.grammar.ProductionRule;
import de.hu_berlin.ensureII.sre.grammar.SRE_Grammar;
import de.hu_berlin.ensureII.sre.grammar.Symbol;
import de.hu_berlin.ensureII.sre.parser.SRE_Parser;
import de.hu_berlin.ensureII.sre.parser.reducer.State.NoSuchTransitionException;


public class SRE_Reducer {
	
	/**
	 * @author Duc Anh Vu
	 *
	 */
	
/*****************************************************************************
** Constructors
*****************************************************************************/
    
	/**
	 * A deterministic finite automaton to choose the production
	 * rule for reducing a handle.
	 */
	public SRE_Reducer(){
		grammar = new SRE_Grammar();
		buildAutomaton();
	}
	
/*****************************************************************************
** Setup
*****************************************************************************/
	
	/**
	 * Build the automaton so its states and transitions encode the
	 * production rules of the SRE grammar. The actions for making a
	 * transition are the terminals and nonterminals. The end states 
	 * yield the the id of the production rule, whose rhs lead to this 
	 * end state.
	 */
	private void buildAutomaton(){
		List<ProductionRule> productions = grammar.getProductionRules();
		states = new ArrayList<State>();
		State current;
		
		State newStart = new State(-1);
		states.add(newStart);
		start = newStart;
		
		for(int i=0; i<productions.size(); i++){
			current = start;
			ProductionRule rule = productions.get(i);
			for(Symbol sym : rule.rhs()){
				if(!current.transitionAvailable(sym)){
					State s = new State(-1);
					states.add(s);
					current.addTransition(sym, s);
				}
				try{
				    current = current.doTransition(sym);
				}catch(NoSuchTransitionException e) {
				    System.err.print("This should not happen here. ");
				    System.err.println("Failed to make transition " + e.getFailedAction());
				    
				}
			}
			current.setRuleID(i);
		}
		
	}
	
/*****************************************************************************
** Reduce, i.e apply a production rule of the grammar on the handle
*****************************************************************************/
	
	/**
	 * Get the production rule for reducing the handle by traversing 
	 * the automaton with the symbols in the handle being the actions
	 * for the transition.
	 * 
	 * @param handle
	 * 			The list of symbols we want to reduce. 
	 * @return
	 * 			The id of the production rule for reducing the handle.
	 */
	public int getReductionRule(List<Symbol> handle) throws ReduceException{
		
	    /****************************************************************************/
        if(handle == null){
            throw new IllegalArgumentException("Handle must be non null.");
        }
        /****************************************************************************/
	    
		State current = start;
		Symbol nextSymbol;
		
		for(int i=0; i<handle.size(); i++){
			nextSymbol = handle.get(i);
			try{
			    current = current.doTransition(nextSymbol);
			}catch(NoSuchTransitionException e) {
			    throw new ReduceException(handle, e);
			}
		}
		int ruleId = current.getRuleID();
		
		if(grammar.ruleExists(ruleId) == false) {
		    throw new ReduceException(handle, new NoSuchTransitionException(""));
		}
		
		/****************************************************************************/
		assert(SRE_Grammar.sameRhs(grammar.getProductionRule(ruleId).rhs(), handle));
		/****************************************************************************/
		
		return ruleId;
	}
	
/*****************************************************************************
** Attributes, Setter and Getter
*****************************************************************************/
	
	/**
	 * Starting state of the automaton.
	 */
	private State start;
	
	
	/**
	 * List of all the states from the automaton.
	 */
	private List<State> states;
	
	
	/**
	 * The grammar for Stochastic Regular Expression 
	 */
	private SRE_Grammar grammar;
	
/*****************************************************************************
** Exception
*****************************************************************************/
	
	public static class ReduceException extends Exception {

        private static final long serialVersionUID = 1L;
        
        public ReduceException(List<Symbol> handle, NoSuchTransitionException e) {
	        this.handle = handle;
	        this.e = e;
	    }
	    
        public String errMsg() {
            String msg = "";
            msg += "Could not reduce the handle: ";
            msg += "{ ";
            for(int i=0; i<handle.size(); i++) {
               msg += handle.get(i);
                msg += " ";
            }
            msg += "}.";
            
            return msg;
        }
        
	    public void print() {
	        System.err.print(errMsg());
	    }
	    
	    private List<Symbol> handle;
	    private NoSuchTransitionException e;
	}
	
/*****************************************************************************
** Logging
*****************************************************************************/
	
	final Logger logger = LoggerFactory.getLogger(SRE_Reducer.class);
	
}
