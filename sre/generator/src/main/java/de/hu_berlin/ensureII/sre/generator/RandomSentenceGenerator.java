package de.hu_berlin.ensureII.sre.generator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import de.hu_berlin.ensureII.sre.grammar.NonTerminal;
import de.hu_berlin.ensureII.sre.grammar.ProductionRule;
import de.hu_berlin.ensureII.sre.grammar.Symbol;
import de.hu_berlin.ensureII.sre.grammar.Token;

public class RandomSentenceGenerator extends SentenceGenerator{
	
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
** Derive sentence from grammar
*****************************************************************************/
	
	@Override
	public String generateSentence(){
		
		nrOfConcats = 0;
		nrOfKleene = 0;
		nrOfPlusClos = 0;
		nrOfChoices = 0;
		nrOfActions = 0;
		
		List<Symbol> toDerive = new LinkedList<Symbol>();
		
		toDerive.add(start);
		
		return deriveSentence(toDerive, initialProductionWeights);
	}
	
	/**
	 * For every symbol provided, apply production rules until only terminals are left
	 * and generate the final string.
	 * 
	 * @param toDerive
	 * 			The list of symbols to choose production rules for.
	 * @param productionWeights
	 * 			The weights for every production rule.
	 * @return
	 * 			The string after deriving each symbol.
	 */
	private String deriveSentence(List<Symbol> toDerive, float productionWeights[]){
		StringBuilder sb = new StringBuilder();
		for(Symbol s : toDerive){
			sb.append(deriveSymbol(s, productionWeights));
		}
		return sb.toString();
	}
	
	/**
	 * Randomly choose a production rule for the symbol provided, considering the weights 
	 * for each rule.
	 * 
	 * @param symbol
	 * 			The symbol to choose a production rule for.
	 * @param productionWeights
	 * 			The weights for every production rule.
	 * @return
	 * 			The string after deriving the symbol.
	 */
	private String deriveSymbol(Symbol symbol, float productionWeights[]){
		
		if(symbol.getClass().equals(NonTerminal.class)){
			return deriveNonTerminal((NonTerminal)symbol, productionWeights);
		}else{
			return ((Token)symbol).value();
		}
	}
	
	/**
	 * Choose a production rule for the nonterminal considering the weights of each production.
	 * When is production is chosen, lower its weight for the next recursive derivations so that
	 * the choosing of the rules is balanced. We don't reduce for the rule E -> action, so that we
	 * eventually terminate.
	 * 
	 * @param nt
	 * 			The nonterminal for which we apply a production rule.
	 * @param productionWeights
	 * 			The weights of the productions.
	 * @return
	 * 			The string after deriving the nonterminal.
	 */
	private String deriveNonTerminal(NonTerminal nt, float productionWeights[]){
		float newWeights[] = productionWeights.clone();
		ArrayList<ProductionRule> productions = grammar.getProductionRules(nt);
		Random rand = new Random();
		ProductionRule production;
		float sum = 0;
		int chosenProduction = 0;
		
		for(int i=0; i<productions.size(); i++){
			production = productions.get(i);
			sum += newWeights[production.id()];
		}
		
		sum = sum * rand.nextFloat();
		
		for(int i=0; i<productions.size(); i++){
			production = productions.get(i);
			sum -= newWeights[production.id()];
			if(sum <= 0){
				chosenProduction = i;
				
				//debug
				//System.out.println(nt.getName() + " -> " + 
				//		productions.get(i).toString());
				
				break;
			}
		}
		
		/*
		 * lower the weight of chosen production except when its E -> action, 
		 * so that we always produce terminals at some point
		 */
		production = productions.get(chosenProduction);
		if(production.id() == 7){
			//E -> action, do nothing
		}else{
			//reduce the weight of the chosen production
			newWeights[production.id()] = newWeights[production.id()] / 2;
		}
		
		/* count stuff */
		/*
		 * 1 S -> E
		 * 2 E -> E:E
		 * 3 E -> E*p
		 * 4 E -> E#p 
		 * 5 E -> (E) 
		 * 6 E -> (C) 
		 * 7 E -> action
		 * 8 C -> C+C
		 * 9 C -> E[w]
		 */
		switch(production.id()){
			case 2:	
				nrOfConcats++;
				break;
			case 3:
				nrOfKleene++;
				break;
			case 4:
				nrOfPlusClos++;
				break;
			case 5:
			case 6:
				break;
			case 7:
				nrOfActions++;
				break;
			case 8:
				nrOfChoices++;
				break;
			case 9:
				break;
			default:
		}
		
		List<Symbol> derivation = grammar.derive(production.id(), actionAlphabet);
		
		assert(derivation.size() > 0);
		
		return deriveSentence(derivation, newWeights);
	}
	
/*****************************************************************************
** Attributes, Setter and Getter
*****************************************************************************/
	
	private float initialProductionWeights[] = {
			1.0f,		//S -> E
			1.0f,		//E -> E:E
			1.0f,		//E -> E*p
			1.0f,		//E -> E#p
			1.0f,		//E -> (E)
			1.0f,		//E -> (C)
			0.1f,		//E -> action ,reduce the weight here to get longer sentences
			1.0f,		//C -> C+C
			1.0f};		//C -> E[w]
}
