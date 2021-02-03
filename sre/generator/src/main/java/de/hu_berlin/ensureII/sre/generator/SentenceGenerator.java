package de.hu_berlin.ensureII.sre.generator;

import java.util.ArrayList;
import java.util.Arrays;

import de.hu_berlin.ensureII.sre.grammar.SRE_Grammar;
import de.hu_berlin.ensureII.sre.grammar.Symbol;

/**
 * A generator for words from stochastic regular expression language.
 * 
 * @author Duc Anh Vu
 *
 */
public abstract class SentenceGenerator {

	/* Production rules */
	/*
	 * 0 S -> E
	 * 1 E -> E:E
	 * 2 E -> E*p
	 * 3 E -> E#p 
	 * 4 E -> (E) 
	 * 5 E -> (F) 
	 * 6 E -> action
	 * 7 F -> C+C
	 * 8 C -> E[w]
	 */

/*****************************************************************************
** Constructors
*****************************************************************************/
    
	public SentenceGenerator(){
		grammar = new SRE_Grammar();
		start = grammar.getStartSymbol();
		this.actionAlphabet = new ArrayList<>(Arrays.asList("abcdefghijklmnopqrstuvwxyz".split("")));
	}

/*****************************************************************************
** Derive sentence from grammar
*****************************************************************************/
	
	/**
	 * Derive a sentence from the grammar by starting with the start symbol
	 * and applying production rules until only terminals are left.
	 * 
	 * @return
	 * 		A sentence derived from the grammar.
	 */
	public abstract String generateSentence();
	
/*****************************************************************************
** Attributes, Setter and Getter
*****************************************************************************/
	
	protected SRE_Grammar grammar;
	
	protected Symbol start;
	
	public Symbol getStart(){
		return start;
	}
	
	/**
	 * action alphabet
	 */
	protected ArrayList<String> actionAlphabet;
	
	public void setActionAlphabet(ArrayList<String> actionAlphabet) {
	    this.actionAlphabet.clear();
	    this.actionAlphabet.addAll(actionAlphabet);
	}
	
	/**
	 * Number of the concatenation operator in the generated string
	 */
	protected int nrOfConcats;
	
	/**
	 * Number of the kleene start operator in the generated string
	 */
	protected int nrOfKleene;
	
	/**
	 * Number of the plus closure operator in the generated string
	 */
	protected int nrOfPlusClos;
	
	/**
	 * Number of the choice operator in the generated string
	 */
	protected int nrOfChoices;
	
	/**
	 * Number of actions in the generated string
	 */
	protected int nrOfActions;
	
	public int getNrOfConcats() {
		return nrOfConcats;
	}

	public int getNrOfKleene() {
		return nrOfKleene;
	}

	public int getNrOfPlusClos() {
		return nrOfPlusClos;
	}

	public int getNrOfChoices() {
		return nrOfChoices;
	}
	
	public int getNrOfActions() {
		return nrOfActions;
	}
	
	public int getActionAlphabetLength() {
		return actionAlphabet.size();
	}

}
