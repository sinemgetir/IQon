package de.hu_berlin.ensureII.sre.grammar;

public interface IOperatorPrecedenceGrammar extends IGrammar {

	/**
	 * Look up the precedence between two tokens. The precedence may
	 * be different if the left token and right token swap positions.
	 * precedence is defined as follow:
	 * 1 = '>'
     * -1 = '<'
     * 2 = '='
     * 0 = error
	 * 
	 * @param left
	 * 			The left token.
	 * @param right
	 * 			The right token.
	 * @return
	 * 			The precedence relation between the symbols.
	 */
	public int getPrecedence(Token left, Token right);
}
