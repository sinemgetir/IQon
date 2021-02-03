package de.hu_berlin.ensureII.sre.grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class SRE_Grammar implements IOperatorPrecedenceGrammar{
	
	/**
	 * A formal grammar for stochastic regular expression.
	 *
	 */

/*****************************************************************************
** Constructors
*****************************************************************************/
    
	public SRE_Grammar(){
		buildProductionRules();
	}
	
/*****************************************************************************
** Implement IGrammar
*****************************************************************************/
	
	@Override
	public Symbol getStartSymbol() {
		return S;
	}
	
	@Override
	public ProductionRule getProductionRule(int ruleId){
	    
	    /****************************************************************************/
	    if(ruleExists(ruleId) == false){
            throw new IllegalArgumentException("No rule with id " + ruleId + ".");
	    }
	    /****************************************************************************/
		
		ProductionRule productionRule = productionRules.get(ruleId);
		
		/****************************************************************************/
		assert(productionRule.id() == ruleId);
		/****************************************************************************/
		
		return productionRule;
	}
	
	@Override
	public ArrayList<ProductionRule> getProductionRules(NonTerminal lhs){
	    
	    /****************************************************************************/
	    if(lhs == null) {
	        throw new IllegalArgumentException("Argument must be non null.");
	    }
	    /****************************************************************************/
		
		ArrayList<ProductionRule> productionRulesForLhs = new ArrayList<ProductionRule>();
		ProductionRule rule;
		
		for(int i=0; i<this.productionRules.size(); i++) {
			rule = this.productionRules.get(i);
			if(rule.lhs().equals(lhs)) {
				productionRulesForLhs.add(rule);
			}
		}
		
		/****************************************************************************/
		assert(sameLhs(lhs, productionRulesForLhs));
		/****************************************************************************/
		
		return productionRulesForLhs;
	}
	
	@Override
	public List<Symbol> derive(int ruleId){
		
	    /****************************************************************************/
        if(ruleExists(ruleId) == false){
            throw new IllegalArgumentException("No rule with id " + ruleId + ".");
        }
        /****************************************************************************/
		
		ArrayList<Symbol> derivation = new ArrayList<Symbol>();
		ProductionRule rule = productionRules.get(ruleId);
		
		for(Symbol s : rule.rhs()) {
			if(s.getClass().equals(NonTerminal.class)) {
				derivation.add(s);
			}else {
				//standard alphabet is small latin letters
				ArrayList<String> actionAlphabet = new ArrayList<>(Arrays.asList("abcdefghijklmnopqrstuvwxyz".split("")));
				derivation.add(generateToken(s.name(), actionAlphabet));
			}
		}
		
		/****************************************************************************/
        assert(sameRhs(productionRules.get(ruleId).rhs(), derivation));
        /****************************************************************************/
		
		return derivation;
	}
	
	/**
	 * 
	 * @param ruleId
	 * 		Rule to use for the derivation.
	 * @param actionAlphabet
	 * 		Alphabet that the actions are from.
	 * @return
	 * 		Derived list of symbols.
	 */
	@Override
	public List<Symbol> derive(int ruleId, ArrayList<String> actionAlphabet){
		
	    /****************************************************************************/
        if(ruleExists(ruleId) == false){
            throw new IllegalArgumentException("No rule with id " + ruleId + ".");
        }
        /****************************************************************************/
		
		ArrayList<Symbol> derivation = new ArrayList<Symbol>();
		ProductionRule rule = productionRules.get(ruleId);
		
		for(Symbol s : rule.rhs()) {
			if(s.getClass().equals(NonTerminal.class)) {
				derivation.add(s);
			}else {
				derivation.add(generateToken(s.name(), actionAlphabet));
			}
		}
		
		/****************************************************************************/
        assert(sameRhs(productionRules.get(ruleId).rhs(), derivation));
        /****************************************************************************/
		
		return derivation;
	}
	
/*****************************************************************************
** Implement IOperatorPrecedenceGrammar
*****************************************************************************/
	
	@Override
	public int getPrecedence(Token left, Token right){
        
	    /****************************************************************************/
	    if(left == null || right == null){
            throw new IllegalArgumentException("Arguments must be non null");
        }
        /****************************************************************************/
        
        int leftTablePosition = getOperatorPrecedenceTablePosition(left.name());
        int rightTablePosition = getOperatorPrecedenceTablePosition(right.name());
        
        int precedence = operatorPrecedenceTable[leftTablePosition][rightTablePosition];
        
        /****************************************************************************/
        assert(precedence >= -2 && precedence <= 3);
        /****************************************************************************/
        
        return precedence;
    }

/*****************************************************************************
** Generate Token
*****************************************************************************/
	    
	public Token generateToken(String type, ArrayList<String> alphabet) {

	    /****************************************************************************/
        if(alphabet == null){
            throw new IllegalArgumentException("Alphabet must be non null");
        }
        /****************************************************************************/
	    
	    Random rand = new Random();
	    String value;

	    switch(type) {
	    case "action":
	        value = String.valueOf(alphabet.get(rand.nextInt(alphabet.size())));
	        break;
	    case "probability":
	        //value = String.valueOf(rand.nextFloat());
	        value = String.format("%.2f", rand.nextFloat());
	        break;
	    case "weight":
	        value = String.valueOf(rand.nextInt(1000));
	        break;
	    case "lparen":
	        value = "(";
	        break;
	    case "rparen":
	        value = ")";
	        break;
	    case "lbracket":
	        value = "[";
	        break;
	    case "rbracket":
	        value = "]";
	        break;
	    case "concat":
	        value = ":";
	        break;
	    case "kleene":
	        value = "*";
	        break;
	    case "plusclos":
	        value = "#";
	        break;
	    case "choice":
	        value = "+";
	        break;
	    case "eof":
	        value = "";
	        break;
	    default:        
	        throw new IllegalArgumentException(
	                "Type must be one of the following: action, probability, weight, concat, kleene, plusclos, choice, eof,"
	                + " lparen, rparen, lbracket, rbracket.");
	    }
	    
	    Token token = new Token(type, value);
	    
	    /****************************************************************************/
        assert(token.name().equals(type));
        /****************************************************************************/

	    return token;
	}
	
/*****************************************************************************
** Setup
*****************************************************************************/
	
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
    private void buildProductionRules(){
        
        productionRules = new ArrayList<ProductionRule>();
        ArrayList<Symbol> rhs = new ArrayList<Symbol>();
        
        /* S -> E */
        rhs.clear();
        rhs.add(this.getE());
        productionRules.add(new ProductionRule(this.getS(), rhs, 0));
        
        /* E -> E:E */
        rhs.clear();
        rhs.add(this.getE());
        rhs.add(concatToken);
        rhs.add(this.getE());
        productionRules.add(new ProductionRule(this.getE(), rhs, 1));
        
        /* E -> E*p */
        rhs.clear();
        rhs.add(this.getE());
        rhs.add(kleeneToken);
        rhs.add(probabilityToken);
        productionRules.add(new ProductionRule(this.getE(), rhs, 2));
        
        /* E -> E#p */
        rhs.clear();
        rhs.add(this.getE());
        rhs.add(plusclosToken);
        rhs.add(probabilityToken);
        productionRules.add(new ProductionRule(this.getE(), rhs, 3));
        
        /* E -> (E) */
        rhs.clear();
        rhs.add(lparenToken);
        rhs.add(this.getE());
        rhs.add(rparenToken);
        productionRules.add(new ProductionRule(this.getE(), rhs, 4));
        
        /* E -> (C) */
        rhs.clear();
        rhs.add(lparenToken);
        rhs.add(this.getC());
        rhs.add(rparenToken);
        productionRules.add(new ProductionRule(this.getE(), rhs, 5));
        
        /* E -> action */
        rhs.clear();
        rhs.add(actionToken);
        productionRules.add(new ProductionRule(this.getE(), rhs, 6));
        
        /* C -> C+C */
        rhs.clear();
        rhs.add(this.getC());
        rhs.add(choiceToken);
        rhs.add(this.getC());
        productionRules.add(new ProductionRule(this.getC(), rhs, 7));
  
        /* C -> E[w] */
        rhs.clear();
        rhs.add(this.getE());
        rhs.add(lbracketToken);
        rhs.add(weightToken);
        rhs.add(rbracketToken);
        productionRules.add(new ProductionRule(this.getC(), rhs, 8));

    }
	
	private HashMap<String, Integer> createMap(){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("eof",0);
        map.put("lparen",1);
        map.put("rparen",2);
        map.put("lbracket",3);
        map.put("rbracket",4);
        map.put("concat",5);
        map.put("kleene",6);
        map.put("plusclos",7);
        map.put("choice",8);
        map.put("action",9);
        map.put("probability",10);
        map.put("weight",11);
        return map;
    }
	
/*****************************************************************************
** Attributes, Setter and Getter
*****************************************************************************/
	
	/**
	 * The precedence table for SRE.
	 * 13x13 since we have 13 tokens
	 * 1 = '>'
	 * -1 = '<'
	 * 2 = '='
	 * 0 = error
	 * 
	 * rows, columns: 
	 * EOF, LPAREN, RPAREN, LBRACKET, RBRACKET, CONCAT, KLEENE, PLUS, CHOICE, ACTION, PROB, WEIGHT 
	 */
	private int[][] operatorPrecedenceTable = new int[][]{
//EOF, LPAREN, RPAREN, LBRACKET, RBRACKET, CONCAT, KLEENE, PLUS, CHOICE, ACTION, PROB, WEIGHT
{1,		-1,		-1,		-1,		-1,		-1,		-1,		-1,		-1,		-1,		-1,		-1},//EOF
{1,		-1,		2,		-1,		0,		-1,		-1,		-1,		-1,		-1,		-1,		-1},//LPAREN
{1,		0,		1,		1,		0,		1,		1,		1,		0,		-1,		-1,		-1},//RPAREN
{1,		0,		0,		0,		2,		0,		0,		0,		0,		-1,		-1,		2},//LBRACKET
{1,		1,		1,		1,		1,		1,		1,		1,		1,		-1,		-1,		-1},//RBRACKET
{1,		-1,		1,		1,		0,		1,		-1,		-1,		0,		-1,		-1,		-1},//CONCAT
{1,		1,		1,		1,		0,		1,		1,		1,		0,		-1,		2,		-1},//KLEENE
{1,		1,		1,		1,		0,		1,		1,		1,		0,		-1,		2,		-1},//PLUS
{1,		-1,		1,		-1,		0,		-1,		-1,		-1,		1,		-1,		-1,		-1},//CHOICE
{1,		1,		1,		1,		1,		1,		1,		1,		1,		1,		1,		1},	//ACTION
{1,		1,		1,		1,		1,		1,		1,		1,		1,		1,		1,		1},	//PROB
{1,		1,		1,		1,		2,		1,		1,		1,		1,		1,		1,		1}	//WEIGHT
	};
	
	/*
	 * This table here is formally constructed.
	 * However we cannot use this table, because when we
	 * reduce, we push nonterminals on the stack, which we ignore
	 * when checking precedence, so two terminals would be next to
	 * each other who normally could not.
	 * Looking this up would yield a '0' in this table, so we use the 
	 * table above, where we filled the zeroes by hand with values 
	 * so that the parsing is correct again.
	 * 
	 */
	/*private int[][] operatorPrecedenceTable = new int[][]{
		{0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
		{0,1,-1,2,0,0,0,0,0,0,-1,0,0},
		{0,1,0,1,1,0,1,1,1,0,0,0,0},
		{0,1,0,0,0,2,0,0,0,0,0,0,-1},
		{0,1,0,1,0,0,0,0,0,1,0,0,0},
		{0,1,-1,0,0,0,0,0,0,0,-1,0,0},
		{0,1,0,0,0,0,0,0,0,0,0,-1,0},
		{0,1,0,0,0,0,0,0,0,0,0,-1,0},
		{0,1,-1,0,0,0,0,0,0,0,-1,0,0},
		{0,1,0,1,1,0,1,1,1,0,0,0,0},
		{0,1,0,1,1,0,1,1,1,0,0,0,0},
		{0,1,0,0,0,1,0,0,0,0,0,0,0}
	};*/
	
	public int getOperatorPrecedenceTablePosition(String tokenName) {
		if(!operatorPrecedenceTableTokenPositionMap.containsKey(tokenName)) {
			System.out.println("not a valid token!");
		}
		return operatorPrecedenceTableTokenPositionMap.get(tokenName);
	}
	
	/**
	 * The Nonterminals. S is the start symbol.
	 */
	private final NonTerminal E = new NonTerminal("E");
	public NonTerminal getE() { return this.E; }
	private final NonTerminal S = new NonTerminal("S");
	public NonTerminal getS() { return this.S; }
	private final NonTerminal C = new NonTerminal("C");
	public NonTerminal getC() { return this.C; }
	private final NonTerminal P = new NonTerminal("P");
	public NonTerminal getP() { return this.P; }
	private final NonTerminal W = new NonTerminal("W");
	public NonTerminal getW() { return this.W; }
	
	/**
	 * The production rules for stochastic regular expressions.
	 */
	private List<ProductionRule> productionRules;
	
	public List<ProductionRule> getProductionRules() {
		return Collections.unmodifiableList(productionRules);
	}
	
	private HashMap<String, Integer> operatorPrecedenceTableTokenPositionMap = createMap();
	
	
	
/*****************************************************************************
** Static Constants
*****************************************************************************/
	
	/**
	 * Dummy tokens for action, probability and weight. We use them for comparisons.
	 * -1 as the value means its a dummy token.
	 */
	public static final Token probabilityToken = new Token("probability", "-1");
	public static final Token actionToken = new Token("action", "-1");
	public static final Token weightToken = new Token("weight", "-1");
	
	public static final Token eofToken = new Token("eof","");
	public static final Token lparenToken = new Token("lparen","(");
	public static final Token rparenToken = new Token("rparen",")");
	public static final Token lbracketToken = new Token("lbracket","[");
	public static final Token rbracketToken = new Token("rbracket","]");
	public static final Token concatToken = new Token("concat",":");
	public static final Token kleeneToken = new Token("kleene","*");
	public static final Token plusclosToken = new Token("plusclos","#");
	public static final Token choiceToken = new Token("choice","+");
	
	/**
	 * Some regular expressions to check whether we matched correctly.
	 */
	public static final String actionRegEx = "[a-z][a-z0-9]*";
	public static final String probabilityRegEx = "(1|0)\\.[0-9]+";
	public static final String weightRegEx = "[0-9]+";
	
/*****************************************************************************
** Assertions
*****************************************************************************/
	
	public boolean ruleExists(int ruleId) {
	    return ruleId <= productionRules.size() && ruleId >= 0;
	}
	
	public static boolean sameLhs(Symbol lhs, List<ProductionRule> rules) {
	    boolean result = true;
	    
	    for(ProductionRule rule : rules) {
	        result = result && rule.lhs().equals(lhs);
	    }
	    
	    return result;
	}
	
	public static boolean sameRhs(List<Symbol> rhs, List<Symbol> toCheck) {
	    boolean result = true;
	    
	    if(rhs.size() != toCheck.size()) {
	        return false;
	    }
	    
	    for(int i=0; i<toCheck.size(); i++) {
	        result = result && (rhs.get(i).name().equals(toCheck.get(i).name()));
	    }
	    
	    return result;
	}
	
}
