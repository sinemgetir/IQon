package de.hu_berlin.ensureII.sre.grammar;

public class Token extends Symbol{
	
/*****************************************************************************
** Constructors
******************************************************************************/
    
	/**
	 * Token object. Name of the token is the type.
	 * 
	 * @param name
	 * 			The name of the token which is also the type.
	 * @param value
	 * 			The character sequence represented by this token.
	 */
	public Token(String name, String value){
		super(name);
		this.value = value;
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param token
	 */
	public Token(Token toCopy) {
	    super(toCopy.name());
	    this.value = toCopy.value();
	}

/*****************************************************************************
** Override Symbol
*****************************************************************************/	
	
	@Override
	public Symbol copy() {
	    return new Token(this);
	}
	
/*****************************************************************************
** Override Object
*****************************************************************************/
	    
    @Override
    public String toString(){
        return value;
    }
	
/*****************************************************************************
** Attributes
*****************************************************************************/
	
	/**
	 * The character sequence represented by this token.
	 */
	private String value;
	
/*****************************************************************************
** Setter and Getter
*****************************************************************************/
	
	/**
	 * 
	 * @return
	 *     value of this token.
	 */
	public String value(){
        return value;
    }
	
}
