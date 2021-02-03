package de.hu_berlin.ensureII.sre.grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductionRule {

    
/*****************************************************************************
** Constructors
*****************************************************************************/
    
	/**
	 * Constructor for a class representing the production rule of a formal grammar.
	 * 
	 * @param lhs 
	 * 			The left hand side of the production rule.
	 * @param rhs 
	 * 			The right hand side of the production rule
	 * @param id 
	 * 			An integer to identify the production rule.
	 */
	public ProductionRule(NonTerminal lhs, List<Symbol> rhs, int id){
		this.lhs = new NonTerminal(lhs);
		this.rhs = new ArrayList<Symbol>();
		for(Symbol s : rhs) {
		    this.rhs.add(s.copy());
		}
		this.id = id;
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param toCopy
	 */
	public ProductionRule(ProductionRule toCopy) {
	    this.lhs = toCopy.lhs();
	    this.rhs = toCopy.rhs();
	    this.id = toCopy.id();
	}

/*****************************************************************************
** Override Object
*****************************************************************************/
	    
	@Override
	public String toString(){
	    StringBuilder sb = new StringBuilder();

	    sb.append(lhs.name());
	    sb.append(" -> ");

	    for(Symbol s : rhs){
	        sb.append(s.name());
	        sb.append(',');
	    }
	    return sb.toString();
	}
	
/*****************************************************************************
** Attributes
*****************************************************************************/
	
	private List<Symbol> rhs;
	
	private NonTerminal lhs;
	
	private int id;
	
/*****************************************************************************
** Setter and Getter
*****************************************************************************/

	/**
	 * 
	 * @return
	 *     copy of the lhs of the rule.
	 */    
	public NonTerminal lhs(){
        return new NonTerminal(lhs);
    }
	
	/**
     * @return
     *     rhs on the rule. read only
     */
    public List<Symbol> rhs(){
        return Collections.unmodifiableList(rhs);
    }
    
    /**
     * 
     * @return
     *      id of the rule
     */
    public int id(){
        return id;
    }
	
}
