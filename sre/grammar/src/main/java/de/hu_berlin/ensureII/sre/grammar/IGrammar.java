package de.hu_berlin.ensureII.sre.grammar;

import java.util.ArrayList;
import java.util.List;

public interface IGrammar {

	/**
	 * Interface for a formal context free grammar.
	 */
	
	/**
	 * 
	 * @return 
	 *     This grammar's start symbol
	 */
	public Symbol getStartSymbol();
	
	/**
	 * 
	 * @param id
	 *     The production rule's id
	 * @return 
	 *     The production rule with this id
	 */
	public ProductionRule getProductionRule(int ruleID);
	
	/**
	 * 
	 * @param lhs
	 *     The left hand side of the requested production rules
	 * @return 
	 *     A list with all production rules with the matching left hand side
	 */
	public List<ProductionRule> getProductionRules(NonTerminal lhs);
	
	/**
	 * Generate a derivation of the given production rule. Use this if you want the tokens to have
	 * other values than the default ones where possible. Otherwise just take the rhs of the production rule.
	 * 
	 * @param ruleID
	 *     Id of the production rule.
	 * @return 
	 *     The rhs of the production rule where the tokens have generated values.
	 */
	public List<Symbol> derive(int ruleID);
	
	/**
     * 
     * @param ruleID
     *      Rule to use for the derivation.
     * @param actionAlphabet
     *      Alphabet that the actions are from.
     * @return
     *      The rhs of the production rule where the tokens have generated values from
     *      actionAlphabet.
     */
    public List<Symbol> derive(int ruleID, ArrayList<String> actionAlphabet);
	
}
