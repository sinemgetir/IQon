package de.hu_berlin.ensureII.sre.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import de.hu_berlin.ensureII.sre.grammar.NonTerminal;
import de.hu_berlin.ensureII.sre.grammar.ProductionRule;
import de.hu_berlin.ensureII.sre.grammar.Symbol;
import de.hu_berlin.ensureII.sre.grammar.Token;

/**
 * A parameterized sentence generator where the number of operands in the sentence can be specified.
 * 
 * @author Duc Anh Vu
 *
 */
public class ParamSentenceGenerator extends SentenceGenerator{

	/* Production rules */
	/*
	 * 0 S -> E
	 * 1 E -> E:E
	 * 2 E -> E*p
	 * 3 E -> E#p 
	 * 4 E -> (E) 
	 * 5 E -> (C) 
	 * 6 E -> action
	 * 7 C -> C+C
	 * 8 C -> E[w]
	 */

/*****************************************************************************
** Constructors
*****************************************************************************/
    
	public ParamSentenceGenerator(){
		super();
		
		nrOfConcats = 0;
		nrOfKleene = 0;
		nrOfPlusClos = 0;
		nrOfChoices = 0;
	}

/*****************************************************************************
** Derive sentence from grammar
*****************************************************************************/
	
	@Override
	public String generateSentence(){
		
		nrOfActions = 0;
		concatToGenerate = nrOfConcats;
		kleeneToGenerate = nrOfKleene;
		plusClosToGenerate = nrOfPlusClos;
		choiceToGenerate = nrOfChoices;
		
		List<Symbol> toDerive = new LinkedList<Symbol>();
		
		toDerive.add(start);
		
		return deriveSentence(toDerive);
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
	private String deriveSentence(List<Symbol> toDerive){
	
		List<Symbol> newToDerive = new LinkedList<Symbol>();
		newToDerive.addAll(toDerive);
		List<Symbol> derived = new LinkedList<Symbol>();
		
		
		boolean onlyTerminals = false;
		
		while(!onlyTerminals){
			onlyTerminals = true;
			for(Symbol symbol : newToDerive){
				if(symbol.getClass().equals(NonTerminal.class)){
					onlyTerminals = false;
					derived.addAll(deriveNonTerminal((NonTerminal)symbol));
				}else{
					derived.add(symbol);
				}
			}
			
			newToDerive.clear();
			newToDerive.addAll(derived);
			derived.clear();
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(Symbol symbol : newToDerive){
			sb.append(((Token)symbol).value());
		}
		
		return sb.toString();
		
	}
	
	/**
	 * Choose a production rule for the nonterminal considering the weights of each production.
	 * When is production is chosen, lower its weight for the next recursive derivations so that
	 * the choosing of the rules is balanced. We don't reduce for the rule E -> action, so that we
	 * eventually terminate.
	 * 
	 * @param nt
	 * 			The nonterminal for which we apply a production rule.
	 * @return
	 * 			A List of the symbols that result from applying a production rule on the nt.
	 */
	private List<Symbol> deriveNonTerminal(NonTerminal nt){
		ProductionRule production = chooseProduction(nt);
		return grammar.derive(production.id(), actionAlphabet);
	}
	
	/**
	 * Choose a production rule. 
	 * 
	 * @param nt
	 * 		The nonterminal to choose a production rule for.
	 */
	private ProductionRule chooseProduction(NonTerminal nt){
		
		ProductionRule chosenProduction;
		
		switch(nt.name()){
			case "S":
				chosenProduction = grammar.getProductionRule(0);
				break;
			case "E":
				chosenProduction = chooseWrtInput();
				break;
			case "C":
			    if(choiceToGenerate > 0) {
			        Random rand = new Random();
			        if(rand.nextBoolean()) {
			            chosenProduction = grammar.getProductionRule(7);
			            choiceToGenerate--;
			        }else {
			            chosenProduction = grammar.getProductionRule(8);
			        }
			    }else {
			        chosenProduction = grammar.getProductionRule(8);
			    }	
				break;
			default:
				//error
				chosenProduction = grammar.getProductionRule(0);
				System.out.println("error");
				System.exit(-1);
		}
		
		return chosenProduction;
	}
	
	private ProductionRule chooseWrtInput(){
		
		ProductionRule chosenProduction;
		int id;
		Random rand = new Random();
		
		if(concatToGenerate == 0 && kleeneToGenerate == 0 && plusClosToGenerate == 0 && choiceToGenerate == 0){
			chosenProduction = grammar.getProductionRule(6);
			nrOfActions++;
		}else{
			//small chance to generate parenthesis, just because
			int parenToGenerate = (concatToGenerate+kleeneToGenerate+plusClosToGenerate+choiceToGenerate)/40;
			
			//randomly choose an operation to generate
			id = rand.nextInt(concatToGenerate +
					kleeneToGenerate +
					plusClosToGenerate +
					choiceToGenerate +
					parenToGenerate);
			
			if(id < concatToGenerate) {
				id = 1;
			}else if(id < concatToGenerate + kleeneToGenerate) {
				id = 2;
			}else if(id < concatToGenerate + kleeneToGenerate + plusClosToGenerate) {
				id = 3;
			}else if(id < concatToGenerate + kleeneToGenerate + plusClosToGenerate + choiceToGenerate) {
				id = 5;
			}else if(id < concatToGenerate + kleeneToGenerate + plusClosToGenerate + choiceToGenerate + parenToGenerate) {
				id = 4;
			}
			
			switch(id){
				case 1:
					concatToGenerate--;
					break;
				case 2:
					kleeneToGenerate--;
					break;
				case 3:
					plusClosToGenerate--;
					break;
				case 5:
					//choiceToGenerate--;
					break;
				default:
			}
			
			chosenProduction = grammar.getProductionRule(id);
		}
		
		return chosenProduction;
	}
	
/*****************************************************************************
** Attributes, Setter and Getter
*****************************************************************************/
	
	public void setNumberOfConcat(int n){
		nrOfConcats = n;
	}
	
	public void setNumberOfKleene(int n){
		nrOfKleene = n;
	}
	
	public void setNumberOfPlusClos(int n){
		nrOfPlusClos = n;
	}
	
	public void setNumberOfChoice(int n){
		nrOfChoices = n;
	}
	
	/**
	 * Number of concatenations still needed to be generated
	 */
	private int concatToGenerate;
	
	/**
	 * Number of concatenations still needed to be generated
	 */
	private int kleeneToGenerate;
	
	/**
	 * Number of concatenations still needed to be generated
	 */
	private int plusClosToGenerate;
	
	/**
	 * Number of concatenations still needed to be generated
	 */
	private int choiceToGenerate;
	
}
