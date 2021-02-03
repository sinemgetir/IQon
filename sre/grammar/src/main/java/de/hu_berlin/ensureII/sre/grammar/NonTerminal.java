package de.hu_berlin.ensureII.sre.grammar;

public class NonTerminal extends Symbol{
	
/*****************************************************************************
** Constructors
*****************************************************************************/
    
	/**
	 * Nonterminal object.
	 * 
	 * @param name
	 * 			The name of the nonterminal.
	 */
	public NonTerminal(String name){
		super(name);
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param toCopy
	 */
	public NonTerminal(NonTerminal toCopy) {
	    super(toCopy.name());
	}
	
/*****************************************************************************
** Override Symbol
*****************************************************************************/
	
	@Override
	public Symbol copy() {
	    return new NonTerminal(this);
	}
	
/*****************************************************************************
** Override Object
*****************************************************************************/
	
	@Override
	public String toString(){
		return name();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof NonTerminal) {
			return this.name().equals(((NonTerminal) obj).name());
		} else {
			return false;
		}
	}
}
